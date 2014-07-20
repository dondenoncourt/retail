
<%@ page import="com.kettler.domain.orderentry.share.Consumer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'consumer.label', default: 'Consumer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'consumer.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'consumer.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'consumer.email.label', default: 'Email')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'consumer.password.label', default: 'Password')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'consumer.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'consumer.lastUpdated.label', default: 'Last Updated')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${consumerList}" status="i" var="consumer">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${consumer.id}">${fieldValue(bean: consumer, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: consumer, field: "name")}</td>
                        
                            <td>${fieldValue(bean: consumer, field: "email")}</td>
                        
                            <td>${fieldValue(bean: consumer, field: "password")}</td>
                        
                            <td><g:formatDate date="${consumer.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${consumer.lastUpdated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${consumerTotal}" />
            </div>
        </div>
    </body>
</html>
