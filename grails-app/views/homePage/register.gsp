<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="${(['contract', 'store'].find {it == params.mode}) ? params.mode : 'kettlerusa'}"/>
    <title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title>
    <style type="text/css">
    	#content {margin-left: 5px;}
		dl dt { width: 95px; }
		div.right {
			width: 40%;
			display: inline;
		    float: right;
		    clear: right;
		    margin-left: 10px;
	    }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#itemNo").autocomplete({
                source: function(request, response) {
                    $.ajax({
                        url: "${createLink(action:'autoCompleteProduct')}", // remote datasource
                        data: request,
                        success: function(data) {
                            response($.map(data, function(item) {
                                return {
                                    label: item.name,
                                    value: item.id,
                                    imgSrc: item.imageTag,
                                    itemNo: item.itemNo
                                }
                            }));
                        }
                    });
                },
                minLength: 1, // triggered only after minimum 1 characters have been entered.
                select: function(event, ui) { // event handler when user selects a company from the list.
                    $("#productImage").attr("src",ui.item.imgSrc)
                    $("#productImage").attr("alt",ui.item.label)
                    $("#productImage").attr("title",ui.item.label)
                    $("#itemNo").val(ui.item.itemNo);
                    $("#itemMasterId").val(ui.item.value); // update the hidden field.
                    $("#itemDesc").html(ui.item.label);
                    this.value = ui.item.itemNo;
		            $('#content').height($('#content').height()+$('#productImage').height());
                    return false;
                }
            });
        });
    </script>
</head>

<body>
<g:javascript library="jquery" plugin="jquery"/>
<link href="${createLinkTo(dir:'css',file:'jquery-ui.css')}" rel="stylesheet" type="text/css" />
<g:javascript src="jquery-ui.min.js"/>

<div id="content" class="body">
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${registerCmd}">
        <div id="pageErrors" class="errors">
            <g:renderErrors bean="${registerCmd}" as="list"  />
        </div>
    </g:hasErrors>

    <h1>Product Registration</h1>

    <p>We know you are going to enjoy your Kettler product and thank you for your interest in registering it.</p>
    <g:if test="${session.consumer}">
        <p>We have filled in some of the fields below with information from your account.</p>
    </g:if>
    <g:else>
        <p>If you have an account with us, we can fill in some of the fields below for you.  Please login first.</p>
    </g:else>

    <g:hasErrors bean="${cmd}">
        <div class="errors">
            <g:renderErrors bean="${cmd}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="registerProduct" name="registerProduct" method="post">
        <g:hiddenField name="division" value="${params?.division}"/>
        <g:hiddenField name="itemMasterId"/>
        <fieldset>
            <legend>Please enter your product information below:</legend>
            <div class="left">
	            <dl>
	                <dt><label for="itemNo">item no:</label></dt>
	                <dd class="${hasErrors(bean: cmd, field: 'itemNo', 'errors')}">
	                    <input type="text" name="itemNo"
	                           id="itemNo"
	                           value="${cmd?.itemNo}"/>
	                           <span id="itemDesc"></span>
	                </dd>
	                <dt><label for="datePurchased">date purchased:</label></dt>
	                <dd class="${hasErrors(bean: cmd, field: 'datePurchased', 'errors')}">
	                <kettler:jqDatePicker type="text" name="datePurchased" id="datePurchased" title="Format: MM/DD/YYYY"
	                    value="${g.formatDate(date:cmd.datePurchased)}" format='MM/dd/yyyy')}" class="date"/> </dd>
	                <dt><label for="quantity">quantity:</label></dt>
	                <dd class="${hasErrors(bean: cmd, field: 'quantity', 'errors')}">
	                    <input type="text" name="quantity" id="quantity" title="Number of items purchased"
	                    value="${cmd?.quantity}"/> </dd>
	            </dl>
            </div>
            <div class="right">
                <img id="productImage" src="" alt=""  title=""/>
            </div>
        </fieldset>
        <fieldset>
            <legend>Please enter your information below:</legend>
            <dl>
                <dt><label for="name">name:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'name', 'errors')}">
                    <input type="text"
                           name="name"
                           id="name"
                           maxlength="30"  
                           value="${cmd?.name}"/>
                </dd>
                <dt><label for="addr1">address 1:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'addr1', 'errors')}">
                    <input type="text"
                           name="addr1"
                           id="addr1"
                           maxlength="30"  
                           value="${cmd?.addr1}"/>
                </dd>
                <dt><label for="addr2">address 2:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'addr2', 'errors')}">
                    <input type="text"
                           name="addr2"
                           id="addr2"
                           maxlength="30"  
                           value="${cmd?.addr2}"/>
                </dd>
                <dt><label for="city">city:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'city', 'errors')}">
                    <input type="text" name="city"
                           id="city"
                           size="15" maxlength="15"  
                           value="${cmd?.city}"/>
                </dd>
                <dt><label for="state">state:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'state', 'errors')}">
                    <input type="text"
                           name="state"
                           id="state"
                           size="2" maxlength="2"  
                           value="${cmd?.state}"/>
                </dd>
                <dt><label for="zipCode">zip code:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'zipCode', 'errors')}">
                    <input type="text"
                           name="zipCode"
                           id="zipCode"
                           size="9" maxlength="10"  
                           value="${cmd?.zipCode}"/>
                </dd>
                <dt><label for="phone">phone:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'phone', 'errors')}">
                    <input type="text"
                           name="phone"
                           id="phone"
                           value="${cmd?.phone}"/>
                </dd>
                <dt><label for="email">email:</label></dt>
                <dd class="${hasErrors(bean: cmd, field: 'email', 'errors')}">
                    <input type="text"
                           name="email"
                           id="email"
                           value="${cmd?.email}"/>
                </dd>
				<dt>
					<g:checkBox name="marketing" checked="true"/>
				</dt>
				<dd>
				Would you like to receive future email communications from KETTLER? 
				</dd>
                <dt>&nbsp;</dt>
                <dd>
                    <input type="submit" class="button clickme" value="Register Product"/>
                </dd>

            </dl>
        </fieldset>
    </g:form>

</div>
<br/>
<g:javascript>
	$('#registerProduct').submit(function () { 
        if ($("#itemNo").val().trim().length < 5) {
			alert('Please enter a valid Product Name or manually enter the ItemNo');
			return false;
		}
        if ($("#datePurchased").val().trim().length == 0) {
			alert('Please enter a purchased date');
			return false;
		}
	});
</g:javascript>
</body>
</html>

<p></p>





