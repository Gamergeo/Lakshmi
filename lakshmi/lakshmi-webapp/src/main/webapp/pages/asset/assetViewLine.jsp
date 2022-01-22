<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

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
	<div>
		<c:if test="${asset.apiIdentifier != null}">
			${asset.apiIdentifier.symbol}
		</c:if>
	</div>
	<!-- Cell -->
	<div>
		<c:if test="${asset.apiIdentifier != null}">
			${asset.apiIdentifier.market}
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