<%@ page import="com.kettler.domain.item.share.Dealer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dealer.label', default: 'Dealer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="index"><g:message code="default.home.label"/></g:link></span>
            <span class="menuButton"><g:link class="create" action="index"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'dealer.id.label', default: 'Id')}" />
                            <g:sortableColumn property="website" title="${message(code: 'dealer.website.label', default: 'Website')}" />
                            <th>Phone</th>
                            <th><g:message code="dealer.customer.label" default="Customer" /></th>
                            <th>Division</th>
                            <g:sortableColumn property="trackInventory" title="${message(code: 'dealer.trackInventory.label', default: 'Track Inventory')}" />
                            <th>Web Dealer</th>
                            <th>Logo</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${dealerInstanceList}" status="i" var="dealerInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${dealerInstance.id}">${fieldValue(bean: dealerInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: dealerInstance, field: "website")}</td>
                            <td>${dealerInstance.phone}</td>
                            <td>${fieldValue(bean: dealerInstance, field: "customer")}</td>
                            <td>${fieldValue(bean: dealerInstance, field: "division")}</td>
                            <td><g:formatBoolean boolean="${dealerInstance.trackInventory}" /></td>
                            <td><g:formatBoolean boolean="${dealerInstance.web}" /></td>
                            <td>
                              <g:if test="${dealerInstance.logo}">
                                    <img src="<g:createLink action='renderLogo' id='${dealerInstance?.id}'/>" alt="Dealer Logo">
                             </g:if> 
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${dealerInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
