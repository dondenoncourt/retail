<%@ page import="com.kettler.domain.item.share.Dealer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dealer.label', default: 'Dealer')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="index"><g:message code="default.home.label"/></g:link></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Creating Dealer for Customer: ${custName}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${dealerInstance}">
            <div class="errors">
                <g:renderErrors bean="${dealerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
              <g:hiddenField name="custId" value="${custId}" />
              <g:hiddenField name="custName" value="${custName}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="website"><g:message code="dealer.website.label" default="Website" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'website', 'errors')}">
                                    <g:textField name="website" value="${dealerInstance?.website}" size="50" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="division">Division</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'division', 'errors')}">
                                    <g:select name="division.id" from="${com.kettler.domain.item.share.WebDivision.retail.list()}" optionKey="id" value="${dealerInstance?.division?.id}"  />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="trackInventory"><g:message code="dealer.trackInventory.label" default="Track Inventory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'trackInventory', 'errors')}">
                                    <g:checkBox name="trackInventory" value="${dealerInstance?.trackInventory}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="web">Web Dealer</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'web', 'errors')}">
                                    <g:checkBox name="web" value="${dealerInstance?.web}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
