<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>


<c:if test="${asset.apiIdentifier != null}">
	<script type="text/javascript">
  
	  	$(document).ready(function() {
	  		$('#assetForm-${asset.id}').isIdentifierAvailable();
	  	});
	
	</script>
</c:if>

 <form:form id="assetForm-${asset.id}" modelAttribute="asset" action="asset/save.do" data-namespace="asset">
	<form:hidden path="id" value="${asset.id}" />
	<!-- Cell -->
	<div>${asset.label}</div>
	<!-- Cell -->
	<div>${asset.isin}</div>
	<!-- Cell -->
	<div>
		<c:if test="${asset.apiIdentifier != null}">
			${asset.apiIdentifier.api}
		</c:if>
	</div>
	<!-- Cell -->
	<div class="apiIdentifier">
		<c:if test="${asset.apiIdentifier != null}">
			${asset.apiIdentifier.displayedSymbol}
		</c:if>
	</div>
	<div>
		<c:if test='${asset.link != null && asset.link != ""}'>
			<a href="${asset.link}">
				Lien
			</a>
		</c:if>
	</div>
	
	<!-- Cell -->
	<div class="buttonCell">
		<div onclick="$(this).loadEditLine()" class="centered normalButton editButton">&nbsp;</div>
	</div>
	<!-- Cell -->
	<div class="buttonCell">
		<div onclick="$(this).deleteLine()" class="centered normalButton deleteButton">&nbsp;</div>
	</div>
</form:form>