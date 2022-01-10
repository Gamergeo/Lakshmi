<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<%@include file="/pages/head/head.jsp" %>
		
		<link href="<c:url value='/css/main/main.css' />" rel="stylesheet" media="all" type="text/css">
		<link href="<c:url value='/css/priceTracker/priceTracker.css' />" rel="stylesheet" media="all" type="text/css">

		<script type="text/javascript">
		
			function generateCsv() {
				$.get({
					url: '/lakshmi/main/generate.do',
					success : function(resp) {
						alert(resp);
					}
				})
			}
			
		
		</script>
		
	</head>
	
	<body>	
		
		<div id="button">
			<button onclick="generateCsv()">Generer csv</button>
		</div>
		
		<div id="cryptoAsset">
			<span class="title">Cryptos gérées : </span>
			<c:forEach items="${cryptoList}" var="asset">
				<span>${asset.name} (${asset.isin})</span>
			</c:forEach>
		</div>
		
		<div id="stockAsset">
			<span class="title">Stock gérés : </span>
			<c:forEach items="${stockList}" var="asset">
				<span>${asset.name} (${asset.isin})</span>
			</c:forEach>
		</div>
		
		<div id="nonManagedAsset">
			<span class="title">Non gérés : </span>
			<c:forEach items="${notManagedList}" var="asset">
				<span>
					<a href="${asset.link}">
						${asset.name} (${asset.isin})
					</a>
				</span>
			</c:forEach>
		</div>
	
	</body>
</html>