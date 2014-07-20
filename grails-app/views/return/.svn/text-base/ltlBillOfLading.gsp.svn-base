<html>
<head>
    <link href="${createLinkTo(dir:'css',file:'consumer.css')}" rel="stylesheet" type="text/css" />
    <link href="${createLinkTo(dir:'css',file:'ltlBillOfLading.css')}" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="billOfLading">
    Date:<g:formatDate format="MM-dd-yy" date="${new Date()}"/>
    <h1>KETTLER<sup>&#174;</sup> Bill of Lading</h1>
	    <div id="topLeft">
	       <div class="address">
	           <h2>Ship From:</h2>
	           <dl>
	               <dt>Name:</dt><dd>${shipFrom.name}</dd>
                   <dt>Address:</dt><dd>${shipFrom.addr1}</dd>
                   <% if (shipFrom.addr2?.trim()) { %>
                        <dt>&#160;</dt><dd>${shipFrom.addr2}</dd>
                   <% } %>
                   <dt>&#160;</dt><dd>${shipFrom.city}, ${shipFrom.state} ${shipFrom.zipCode}</dd>
	           </dl>
               <dl>
                   <dt>Phone:</dt>
                   <dd>${(kettler.formatPhone(phone:cart.phone))}</dd>
               </dl>
	       </div> 
           <div class="nextBox address">
               <h2>Ship To:</h2>
               <dl>
                   <dt>Name:</dt><dd>KETTLER Virginia Beach</dd>
                   <dt>Address:</dt><dd>1355 London Bridge Road</dd>
                   <dt>&#160;</dt><dd>Virginia Beach, VA 23453</dd>
               </dl>
               <dl>
                   <dt>Phone:</dt>
                   <dd>757.427.2400</dd>
               </dl>
           </div> 
           <div class="nextBox address">
               <h2>Sold To:</h2>
               <dl>
                   <dt>Name:</dt><dd>${soldTo.name}</dd>
                   <dt>Address:</dt><dd>${soldTo.addr1}</dd>
                   <% if (soldTo.addr2?.trim()) { %>
                        <dt>&#160;</dt><dd>${soldTo.addr2}</dd>
                   <% } %>
                   <dt>&#160;</dt><dd>${soldTo.city}, ${soldTo.state} ${soldTo.zipCode}</dd>
               </dl>
               <dl>
                   <dt>Phone:</dt>
                   <dd>${(kettler.formatPhone(phone:cart.phone))}</dd>
               </dl>
           </div> 
	    </div>
	    <div id="topRight">
            <div>
		        <dl>
		            <dt class="wide">Bill of Lading No:</dt><dd>RA${raId}</dd>
		        </dl>
	       </div>
           <div class="nextBox">
               <h2>Carrier:</h2>
               <dl>
                   <dt>Name:</dt><dd>${carrier?.desc}</dd>
                   <dt>Code:</dt><dd>${manifest.shipperNo}</dd>
                   <dt>Phone No:</dt><dd>${carrier?.phoneNo}</dd>
               </dl>
           </div> 
           <div id="proNumber" class="nextBox">
               <h2>Put Pro Number Sticker Here</h2>
           </div> 
           <div class="nextBox">
                <dl>
                    <dt class="wide">Freight Charge Terms:</dt><dd>Collect</dd>
                </dl>
           </div> 
        </div>
        <table id="packageContents">
            <thead><tr><th>Quantity</th><th>Weight</th><th>Description</th><th>NMFC#</th></tr></thead>
            <tbody>
		        <g:each in="${cart?.items}" var="item">
		            <tr>
                           <td class="number">
                               ${item.qty} 
                           </td>
                           <td class="number">
                               <g:formatNumber number="${item.qty * item.item.unitWeight}" format="#####0" />(lbs)
		                </td>
		                <td>
		                     ${item.item.category.name}
		                </td>
                           <td>
                                ${(item.item.nmfcNo?:item.item.itemNo)}
                           </td>
		            </tr>
		        </g:each>
            </tbody>
        </table>
        <div id="signatures">
             <dl>
                 <dt class="wide">TOTAL WEIGHT:</dt>
                 <dd>
                     <g:formatNumber number="${manifest.weightBillable}" format="#####0" />(lbs) FOR: ${manifest.boxesInput} PACKAGES
                 </dd>
             </dl>
            <p>ABOVE ITEMS RECEIVED IN APPARENT GOOD ORDER - EXCEPTIONS NOTED</p>
            <table>
                <tbody>
	                <tr>
                        <td class="prompt">Customer signature:</td><td class="underline signature"/>
                        <td class="prompt">Date:</td><td class="underline date"/>
	                </tr>
                    <tr>
                        <td class="prompt">Carrier signature:</td><td class="underline signature"/>
                        <td class="prompt">Date:</td><td class="underline date"/>
                    </tr>
                </tbody>
            </table>
            <p>SHIPPER PROVIDED SHORT FORM BILL OF LADING -- NOT NEGOTIABLE</p>
        </div>
        <div class="fineprint">
	        <p>All parties herto and their assigns are familiar with, and agree 
	        that this bill of landing is subject to (1) the terms and conditions 
	        of the Uniform and straight Bill of landing as set forth in the 
	        National Motor Freight Classification, and (2) the individually 
	        determined rates and contracts agreed upon in writing between the 
	        carrier and shipper, if applicable, otherwise the rates, classifications 
	        and rules that have been established by the carrier and are available 
	        to the shipper, on request, which are in effect on the date of this shipment.
	        </p>
        </div>
</div>

</body>
</html>