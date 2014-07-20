
<%@ page import="com.kettler.domain.item.share.ItemMasterExt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'jiraissue.label', default: 'Jiraissue')}" />
        <title>Shopping Filtering Tests</title>
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
            <ul>
                <li><g:link action="list" params="[division:'tennisTables']">Tennis Tables</g:link></li>
                <li><g:link action="list" params="[division:'patio']">Patio</g:link></li>
                <li><g:link action="list" params="[division:'fitAndBike']">Fit & Bikes</g:link></li>  
                <li><g:link action="list" params="[division:'toys']">Toys</g:link></li>
            </ul>
            <br/>
            <% if (params.division == 'tennisTables') { %>
	            <ul>
	                <li><g:link action="list" params="[division:params.division, category:'tablesIndoor']">Indoor Tables</g:link></li>
                    <li><g:link action="list" params="[division:params.division, category:'tablesOutdoor']">Outdoor Tables</g:link></li>
	            </ul>
            <% } else if (params.division == 'toys') { %>
                <ul>
                    <li><g:link action="list" params="[division:params.division, category:'balance bikes']">Balance Bikes</g:link></li>
                    <li><g:link action="list" params="[division:params.division, category:'tricycles']">Trikes</g:link></li>
                </ul>
            <% } %>
            <div>
			    <g:form url='[controller: "filter", action: "search"]' id="searchableForm" name="searchableForm" method="get">
			        <g:textField name="q" value="${params.q}" size="50"/> <input type="submit" value="Search" />
			    </g:form>
            </div>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>Item No</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${items}" status="i" var="item">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${item.compCode}:${item.itemNo}</td>
                            <td>${fieldValue(bean: item, field: "desc")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${totalItems}" />
            </div>
        </div>
    </body>
</html>
