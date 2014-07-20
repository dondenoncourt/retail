<html xmlns="http://www.w3.org/1999/xhtml"> 
<%@ page import="com.kettler.controller.retail.EmailUsCommand"%>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta name="layout" content="${(['contract','store'].find{it == params.mode})?params.mode:'kettlerusa'}" />
	<title>Kettler USA Online shopping for Bikes, Fitness, Patio, Table Tennis, &amp; Toys</title> 
</head> 
<body> 
<div id="content" class="articleContent"> 

<h1>Call Us:</h1>

<p>We enjoy hearing from our customers or potential customers.  
If you need to order a <b>replacement part</b>, please use the following contacts.</p>
<dl>
<% if (params.mode == 'canada' || request.serverName ==~ /www.kettlercanada.com/) { %>
    <dt>Phone:</dt><dd>866.804.0440 available Monday thru Friday from 9:00 a.m.- 4:30 p.m. EST</dd>
    <dt>Fax:</dt><dd>888.205.1775</dd>

<% } else { %>
	<dt>Phone:</dt><dd>866.804.0440 available Monday thru Friday from 9:00 a.m.- 4:30 p.m. EST</dd>
	<dt>Fax:</dt><dd>757.563.9273 </dd>
<% } %>
</dl>


<dl>
<% if (params.mode == 'canada' || request.serverName ==~ /www.kettlercanada.com/) { %>
    <p>For all other questions or inquiries on <b>Bicycles, Fitness, Table Tennis and Toys</b>, please contact us by one of the options below:</p> 
    <dt>Phone:</dt><dd>800.205.9831 available Monday thru Friday from 9:00 a.m.- 5:00 p.m. EST</dd>
    <dt>Fax:</dt><dd>613.475.3395</dd>
        <p>For all other questions or inquiries on <b>Patio in all provinces except BC</b>, please contact us by one of the options below:</p>
            <dt>Phone:</dt><dd>204.230.7757 available Monday thru Friday from 9:00 a.m.- 5:00 p.m. EST</dd>
                    <p>For all other questions or inquiries on <b>BC only</b>, please contact us by one of the options below:</p>
            <dt>Phone:</dt><dd>503.887.7383 available Monday thru Friday from 9:00 a.m.- 5:00 p.m. PST</dd>


<% } else { %>
<p>For all other questions or inquiries, please contact us by one of the options below:</p> 
	<dt>Phone:</dt><dd>757.427.2400 available Monday thru Friday from 9:00 a.m.- 5:00 p.m. EST</dd>
	<dt>Fax:</dt><dd>757.427.0183</dd>
<% } %>
</dl>
<% if (params.mode != 'canada') { %>
<% } %>

<h1>e-Mail Us:</h1>

<g:hasErrors bean="${cmd}"><div class="errors"><g:renderErrors bean="${cmd}" as="list" /></div></g:hasErrors>
<g:form  action="emailUs">
    <dl>
        <dt>Type:</dt>                       <dd>
                                                <select name="emailType"> 
                                                    <g:each var="typ" in="${EmailUsCommand.TYPES}">
                                                        <option value="${typ.key}" <%= typ.key==cmd?.emailType?'selected':''%>>${typ.value}</option>
                                                    </g:each>
                                                </select>
                                                <span class="required">*</span>
                                             </dd>
        <dt>Name:</dt>                       <dd><g:textField name="name"    size="40" value="${cmd?.name}" /><span class="required">*</span></dd>
        <dt>e-mail:</dt>                     <dd><g:textField name="email"   size="40" value="${cmd?.email}"  /><span class="required">*</span></dd>
        <dt>Phone:</dt>                      <dd><g:textField name="phone"   size="20" value="${cmd?.phone}" />
        <dt>Company:</dt>                    <dd><g:textField name="company" size="20" value="${cmd?.phone}" />

        <dt>Address 1:</dt>                  <dd><g:textField name="addr1" size="40" value="${cmd?.addr1}"  />
        <dt>Address 2:</dt>                  <dd><g:textField name="addr2" size="40" value="${cmd?.addr2}"  /></dd>
        <dt>City, State Zip:</dt>            <dd>
                                                <g:textField name="city" value="${cmd?.city}" />
                                                <g:textField name="state" size="2" value="${cmd?.state}"/>
                                                <g:textField name="zip" size="5" value="${cmd?.zip}"/>
                                             </dd>
        <dt id="comment">Comment:</dt>       <dd><span class="required">*</span></dd>
    </dl>
    <g:textArea name="comment" value="${cmd?.comment}" rows="10" cols="100"/>
        <dt></dt><dd><g:submitButton name="submit" class="button" value="Submit" /></dd>
    </dl>
</g:form>
</div>
<br/>
</body> 
</html>
