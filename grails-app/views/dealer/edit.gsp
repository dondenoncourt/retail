

<%@ page import="com.kettler.domain.item.share.Dealer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dealer.label', default: 'Dealer')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="index"><g:message code="default.home.label"/></g:link></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${dealerInstance}">
            <div class="errors">
                <g:renderErrors bean="${dealerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" enctype="multipart/form-data" >
                <g:hiddenField name="id" value="${dealerInstance?.id}" />
                <g:hiddenField name="version" value="${dealerInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="website"><g:message code="dealer.website.label" default="Website" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'website', 'errors')}">
                                    <g:textField name="website" value="${dealerInstance?.website}" size="50"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phone">Phone</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'phone', 'errors')}">
                                    <g:textField name="phone" value="${dealerInstance?.phone}" size="10"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label><g:message code="dealer.customer.label" default="Customer" /></label>
                                </td>
                                <td valign="top" class="value">
                                    ${dealerInstance?.customer?.name?.encodeAsHTML()}
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
                                  <label>Current Logo</label>
                                </td>
                                <td valign="top" class="value">
                                  <g:if test="${dealerInstance.logo}">
                                    <img src="<g:createLink action='renderLogo' id='${dealerInstance?.id}'/>" alt="Dealer Logo">
                                  </g:if>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label><g:message code="dealer.logo.label" default="Logo" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'logo', 'errors')}">
                                    <input type="file" name="logo"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="locations"><g:message code="dealer.locations.label" default="Locations" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dealerInstance, field: 'locations', 'errors')}">
                                    
<ul>
<g:each in="${dealerInstance?.locations}" var="l">
    <li><g:link controller="dealerLocation" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="dealerLocation" action="create" params="['dealer.id': dealerInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'dealerLocation.label', default: 'DealerLocation')])}</g:link>

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
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
