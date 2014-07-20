<head> 
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="layout" content="store" />
    <meta name="description" content="KETTLER Retail Outlet Store for ${keywords}"/> 
    <meta name="keywords" content="KETTLER, kettlerstore.com, ${keywords} "/>     
    <title>Kettler Store Virginia Beach: Request for Quote</title> 
</head> 

<br/>
<g:hasErrors bean="${cmd}"><div class="errors"><g:renderErrors bean="${cmd}" as="list" /></div></g:hasErrors>
<g:form  action="rfq">
    <dl>
        <dt>Name:</dt>                       <dd><g:textField name="name"  size="40" value="${cmd?.name}" /><span class="required">*</span></dd>
        <dt>e-mail:</dt>                     <dd><g:textField name="email" size="40" value="${cmd?.email}"  /><span class="required">*</span></dd>
    </dl>
    <dl>
        <dt>Phone:</dt>                      <dd><g:textField name="phone" size="20" value="${cmd?.phone}" /><span class="required">*</span></dd>
        <dt>email:</dt>                      <dd><g:textField name="email" size="40" value="${cmd?.email}" /><span class="required">*</span></dd>
        <% if (cmd?.itemNo) { %>
        <dt >Item No:</dt>                   <dd>${cmd?.itemNo}<g:hiddenField name="itemNo" value="${cmd?.itemNo}"/></dd>
        <dt >Desc:</dt>                      <dd>${cmd?.desc}<g:hiddenField name="desc" value="${cmd?.desc}" /></dd>
        <% } %>
        <dt id="comment">Comment:</dt>       <dd><span class="required">*</span></dd>
    </dl>
    <g:textArea name="comment" value="${cmd?.comment}" rows="10" cols="100"/>
    <dl>        
        <dt></dt><dd><g:submitButton name="submit" value="Submit" /></dd>
    </dl>
    
    
</g:form>
<g:javascript>
    $('#requestForQuote').addClass('current');
</g:javascript>
