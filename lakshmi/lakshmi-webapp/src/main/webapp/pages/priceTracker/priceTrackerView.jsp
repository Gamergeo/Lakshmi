<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/priceTracker/priceTracker.css' />" rel="stylesheet" media="all" type="text/css">

<l:pageInfos title="Price tracker"/>
		
<div id="generateButton">
	<l:button size="menu" onclick="$.priceTracker()" label="Generer csv"/>
</div>

<div id="cryptoAsset">
	<span class="title">Cryptos gérées : </span>
	<c:forEach items="${cryptoList}" var="asset">
		<span>${asset.label} (${asset.isin})</span>
	</c:forEach>
</div>

<div id="stockAsset">
	<span class="title">Stock gérés : </span>
	<c:forEach items="${stockList}" var="asset">
		<span>${asset.label} (${asset.isin})</span>
	</c:forEach>
</div>

<div id="nonManagedAsset">
	<span class="title">Non gérés : </span>
	<c:forEach items="${notManagedList}" var="asset">
		<span>
<%-- 			<a href="${asset.link}"> --%>
				${asset.label} (${asset.isin})
<!-- 			</a> -->
		</span>
	</c:forEach>
</div>