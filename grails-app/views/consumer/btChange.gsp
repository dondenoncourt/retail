<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Change Bill To</title>
        <meta name="layout" content="yourAccount" />
        <link href="${createLinkTo(dir:'css',file:'consumer.css')}" rel="stylesheet" type="text/css" /> 
    </head>
    <body>
        <div   id="yourAccountContent">
            <h1>Change Bill To</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${billTo}">
            <div class="errors">
                <g:renderErrors bean="${billTo}" as="list" />
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
            <h2>Bill Tos:</h2>
          <g:form name="billToChange" action="btUpdate">
              <g:hiddenField name="billToId" value="${billTo.id}"/>
              <g:hiddenField name="consumerId" value="${billTo.consumer?.id}"/>
              <div class="inputTitle"><label for="name">Name</label></div>
              <br clear="left" />
              <div class="inputBoxL ${hasErrors(bean:billTo,field:'name','errors')}"><g:textField name="name" value="${billTo.name}" size="30" maxlength="30" /><span class="required">*</span></div>
              <br clear="left" />
              <div class="inputTitle"><label for="addr1">Address</label></div>
              <br clear="left" />
              <div class="inputBoxL ${hasErrors(bean:billTo,field:'addr1','errors')}"><g:textField name="addr1" value="${billTo.addr1}" maxlength="30" /><span class="required">*</span></div>
              <br clear="left" />
              <div class="inputBoxL"><g:textField name="addr2" value="${billTo.addr2}" maxlength="30" /></div>
              <br clear="left" />
              <div class="inputTitle"><label for="city">City</label></div>
              <div class="inputTitleS"><label for="state">State</label></div>
              <div class="inputTitle"><label for="zipCode">Zip</label></div>
              <br clear="left" />
              <div class="inputBox ${hasErrors(bean:billTo,field:'city','errors')}"><g:textField name="city" value="${billTo.city}" size="15"  maxlength="20" /><span class="required">*</span></div>
              <div class="inputBoxS ${hasErrors(bean:billTo,field:'state','errors')}">
                   <g:select name="state" from="${billTo.constraints.state.inList}"  
                        value="${billTo.state}" noSelection="['':'']"></g:select>
                <span class="required">*</span>
              </div>
              <div class="inputBox ${hasErrors(bean:billTo,field:'zipCode','errors')}"><g:textField name="zipCode" value="${billTo.zipCode}" size="10" maxlength="10" /><span class="required">*</span></div>
              <br/><br/>
              <div>
                  <g:radio name="cardType" value="visa"            class="radioLink" checked="${(billTo.cardType=='visa')}"/>
                  <a href="#"><img class="radioLink${((billTo.cardType=='visa')?'Selected':'')}" src="${resource(dir:"images/")}visa.gif" alt="visa"  /></a>
                  <g:radio name="cardType" value="american express" class="radioLink" checked="${(billTo.cardType=='american express')}"/>
                  <a href="#"><img class="radioLink${((billTo.cardType=='american express')?'Selected':'')}" src="${resource(dir:"images/")}americanexpress.gif" alt="American Express"  /></a>
                  <g:radio name="cardType" value="master card"      class="radioLink" checked="${(billTo.cardType=='master card')}"/>
                  <a href="#"><img class="radioLink${((billTo.cardType=='master card')?'Selected':'')}" src="${resource(dir:"images/")}mastercard.gif" alt="Master Card"  /></a>
                  <g:radio name="cardType" value="discover"        class="radioLink" checked="${(billTo.cardType=='discover')}"/>
                  <a href="#"><img class="radioLink${((billTo.cardType=='discover')?'Selected':'')}" src="${resource(dir:"images/")}discover.gif" alt="Discover"  /></a>
              </div>
              <div id="cardInfo${billTo.id}">
                  <div class="inputTitle"><label for="cardNo">Card No</label></div>
                  <div class="inputTitleS"><label for="ccid">CCID</label>
                  <a href="#"><img class="radioLink" src="${resource(dir:"images/")}question16x16.png"  /></a></div>
                  <div class="inputTitleS"><label for="expMonth">Month</label></div>
                  <div class="inputTitleS"><label for="expYear">Year</label></div>
                  <br clear="left" />
                  <div class="inputBox ${hasErrors(bean:billTo,field:'cardNo','errors')}"><g:textField name="cardNo" size="16" maxlength="16"  value="${billTo?.cardNo?:''}" /><span class="required">*</span></div>
                  <div class="inputBoxS ${hasErrors(bean:billTo,field:'ccid','errors')}"><g:textField name="ccid" size="4" maxlength="4" value="${billTo?.ccid?:0}"/><span class="required">*</span></div>
                  <div class="inputBoxS"><g:select name="expMonth" from="${1..12}"  value="${billTo?.expMonth}"/><span class="required">*</span></div>
                  <div class="inputBoxS"><g:select name="expYear" from="${2010..2020}" value="${billTo?.expYear}"/><span class="required">*</span></div>
              </div>

              <br clear="left" />
            <div class="buttons">
                <a href="#" onclick="history.go(-1);return false;" class="button">Back</a>
                <g:submitButton name="change" value="Change" class="clickme" />
            </div>
          </g:form>
        </div>
		<g:javascript>
		    $('img[src$=visa.gif]').click(function(){
		        $('[value=visa]').attr('checked', true);
		    });
		    $('img[src$=americanexpress.gif]').click(function(){
		        $('[value=american express]').attr('checked', true);
		    });
		    $('img[src$=mastercard.gif]').click(function(){
		        $('[value=master card]').attr('checked', true);
		    });
		    $('img[src$=discover.gif]').click(function(){
		        $('[value=discover]').attr('checked', true);
		    });
		</g:javascript>
    </body>
</html>
