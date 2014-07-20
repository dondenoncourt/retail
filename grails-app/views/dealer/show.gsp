<%@ page import="com.kettler.domain.item.share.Dealer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dealer.label', default: 'Dealer')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="index"><g:message code="default.home.label"/></g:link></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dealer.id.label" default="Id" /></td>
                            <td valign="top" class="value">${fieldValue(bean: dealerInstance, field: "id")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dealer.website.label" default="Website" /></td>
                            <td valign="top" class="value">${fieldValue(bean: dealerInstance, field: "website")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dealer.customer.label" default="Customer" /></td>
                            <td valign="top" class="value">${dealerInstance?.customer?.name?.encodeAsHTML()}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Division</td>
                            <td valign="top" class="value">${dealerInstance?.division?.name?.encodeAsHTML()}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Phone</td>
                            <td valign="top" class="value">${dealerInstance?.phone}</td>
                        </tr>
                        <tr class="prop">
                           <td valign="top" class="name">Logo</td>
                           <td valign="top" class="value">
                             <g:if test="${dealerInstance.logo}">
                               <img src="<g:createLink action='renderLogo' id='${dealerInstance?.id}'/>" alt="Dealer Logo">
                             </g:if>
                           </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dealer.locations.label" default="Locations" /></td>
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${dealerInstance.locations}" var="l">
                                    <li><g:link controller="dealerLocation" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="dealer.trackInventory.label" default="Track Inventory" /></td>
                            <td valign="top" class="value"><g:formatBoolean boolean="${dealerInstance?.trackInventory}" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Web Dealer</td>
                            <td valign="top" class="value"><g:formatBoolean boolean="${dealerInstance?.web}" /></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${dealerInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
