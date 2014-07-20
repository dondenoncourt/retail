	<% if (collections) { %>
		<div class="divCollections">
	<%--		<h1>Collections</h1>  --%>
			
				<g:each in="${collections}" var="category">
					<g:each in="${category}" var="cols">
						<g:each in="${cols.value}" var="collection">
							<a href="${kettler.resource(contextPath:'/shop/collection',absolute:true)}?collection=${collection?.trim()}&category=${category.key?.trim()}&division=${currentDivision?.trim()}">
								<div class="divCollectionsEntry">
									<div class="divCollectionsEntryBorder">
										${collection}
									</div>
								</div>
							</a>
						</g:each> 
					</g:each> 
				</g:each>
			
		</div>
	<% } %>
