<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Change Ship To</title>
        <meta name="layout" content="yourAccount" />
        <link href="${createLinkTo(dir:'css',file:'consumer.css')}" rel="stylesheet" type="text/css" /> 
    </head>
    <body>
        <div   id="yourAccountContent">
            <h1>Change New Ship To</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${shipTo}">
            <div class="errors">
                <g:renderErrors bean="${shipTo}" as="list" />
            </div>
            </g:hasErrors>
            <div id="custInfoHeader">
              <dl>
                  <dt><g:message code="consumer.name.label" default="Name" /></dt>
                  <dd>${fieldValue(bean: consumer, field: "name")}</dd>
                  <dt><g:message code="consumer.email.label" default="Email" /></dt>
                  <dd>${fieldValue(bean: consumer, field: "email")}</dd>
              </dl>
            </div>
            <br clear="left" />
            <h2>Ship To:</h2>
          <g:form name="shipToChange" action="stUpdate">
            <g:hiddenField name="shipToId" value="${shipTo.id}"/>
            <g:hiddenField name="consumerId" value="${shipTo.consumer.id}"/>
            <div class="inputTitle"><label for="Name">Name</label></div>
            <br clear="left" />
            <div class="inputBoxL ${hasErrors(bean:cmd,field:'name','errors')}"><g:textField name="name" id="name" value="${shipTo.name}" size="35" maxlength="40" /><span class="required">*</span></div>
            <br clear="left" />
            <div class="inputTitle"><label for="addr1">Address</label></div>
            <br clear="left" />
            <div class="inputBoxL ${hasErrors(bean:cmd,field:'addr1','errors')}"><g:textField name="addr1" id="addr1" value="${shipTo.addr1}" maxlength="50" /><span class="required">*</span></div>
            <br clear="left" />
            <div class="inputBoxL"><g:textField name="addr2" id="addr2" value="${shipTo.addr2}" maxlength="50" /></div>
            <br clear="left" />
            <div class="inputTitle"><label for="city">City</label></div>
            <div class="inputTitleS"><label for="state">State</label></div>
            <div class="inputTitle"><label for="zipCode">Zip</label></div>
            <br clear="left" />
            <div class="inputBox ${hasErrors(bean:cmd,field:'addr1','errors')}"><g:textField name="city" id="city" value="${shipTo.city}" size="15"  maxlength="20" /><span class="required">*</span></div>
            <div class="inputBoxS ${hasErrors(bean:cmd,field:'state','errors')}">
                 <g:select name="state" from="${shipTo.constraints.state.inList}"  
                        value="${shipTo.state}" noSelection="['':'']"></g:select>
                <span class="required">*</span>
            </div>
            <div class="inputBox ${hasErrors(bean:cmd,field:'zipCode','errors')}"><g:textField name="zipCode" value="${shipTo.zipCode}" size="10" maxlength="10" /><span class="required">*</span></div>
            <br clear="left" />
            <div class="buttons">
                <g:submitButton name="change" value="Change" class="clickme" />
            </div>
          </g:form>
        </div>
    </body>
</html>
