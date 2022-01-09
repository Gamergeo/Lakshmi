<html>

	<head>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
		<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		
		<script type="text/javascript" src="<c:url value='/webjars/jquery/3.4.1/jquery.min.js' />"></script>
		<script type="text/javascript" src="<c:url value='/webjars/jquery-ui/1.12.1/jquery-ui.min.js' />"></script>
		
		<script type="text/javascript">
		
			function generateCsv() {
				$.get({
					url: '/mscomptes/main/generate.do',
					success : function(resp) {
						alert(resp);
					}
				})
			}
			
		
		</script>
		
		<link href="<c:url value='/css/main.css' />" rel="stylesheet" media="all" type="text/css">
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