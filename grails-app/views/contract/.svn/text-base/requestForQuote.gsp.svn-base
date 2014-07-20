
<h1>Request for Quote</h1> 
<g:hasErrors bean="${cmd}"><div class="errors"><g:renderErrors bean="${cmd}" as="list" /></div></g:hasErrors>
<g:form  action="rfq">
    <dl>
        <dt>Business Name:</dt>              <dd><g:textField name="businessName" size="40" value="${cmd?.businessName}" /><span class="required">*</span></dd>
        <dt>Business Type:</dt>              <dd><g:textField name="businessType" size="10" value="${cmd?.businessType}" /></dd>
        <dt>First &amp; Last Name:</dt>            <dd><g:textField name="firstName" size="15" value="${cmd?.firstName}" /><g:textField name="lastName" size="40" value="${cmd?.lastName}" /><span class="required">*</span></dd>
        <dt>Address 1:</dt>                  <dd><g:textField name="addr1" size="40" value="${cmd?.addr1}"  /><span class="required">*</span></dd>
        <dt>Address 2:</dt>                  <dd><g:textField name="addr2" size="40" value="${cmd?.addr2}"  /></dd>
        <dt>City, State Zip:</dt>            <dd>
                                                <g:textField name="city" value="${cmd?.city}" />, 
                                                <g:textField name="state" size="2" value="${cmd?.state}"/>
                                                <g:textField name="zip" size="9" value="${cmd?.zip}"/>
                                                <span class="required">*</span>
                                             </dd>
    </dl>
    <dl>
        <dt>Phone:</dt>                      <dd><g:textField name="phone" size="10" value="${cmd?.phone}" /><span class="required">*</span></dd>
        <dt>Fax:</dt>                        <dd><g:textField name="fax" size="10" value="${cmd?.fax}" /></dd>
        <dt>email:</dt>                      <dd><g:textField name="email" size="50" value="${cmd?.email}" /><span class="required">*</span></dd>
    </dl>
    <dl>
        <% if (cmd?.itemNo) { %>
        <dt >Item No:</dt>                   <dd>${cmd?.itemNo}<g:hiddenField name="itemNo" value="${cmd?.itemNo}"/></dd>
        <dt >Desc:</dt>                      <dd>${cmd?.desc}<g:hiddenField name="desc" value="${cmd?.desc}" /></dd>
        <% } %>
        <dt id="comment">Comment:</dt>       <dd><span class="required">*</span></dd>
    </dl>
    <g:textArea name="comment" value="${cmd?.comment}" rows="10" cols="100"/>
    <dl>
        <dt></dt>                            <dd><g:submitButton name="submit" value="Submit" /></dd>
    </dl>
    <br/>

<%--TODO:
Item number, description C (use shopping cart to choose items)
Quantity of each item R (use shopping cart to choose items)
--%>    
    
</g:form>