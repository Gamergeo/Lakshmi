<%@include file="headOnlyMetadata.jsp" %>

<!-- Style -->
	<!--  jquery ui -->
		<link href="<c:url value='/webjars/jquery-ui/1.12.1/jquery-ui.min.css' />" rel="stylesheet" media="all" type="text/css">
		<script type="text/javascript" src="<c:url value='/webjars/jquery/3.4.1/jquery.min.js' />"></script>
		<!--  Plugin -->
		<link href="<c:url value='/webjars/plugin/datatable/plugin.css' />" rel="stylesheet" media="all" type="text/css">
<!-- Script -->
	<!-- Plugins -->
		<script type="text/javascript" src="<c:url value='/webjars/plugin/util/plugin.js' />"></script>
		<script type="text/javascript" src="<c:url value='/webjars/plugin/ajax/plugin.js' />"></script>
		<script type="text/javascript" src="<c:url value='/webjars/plugin/form/plugin.js' />"></script>
		<script type="text/javascript" src="<c:url value='/webjars/plugin/datatable/plugin.js' />"></script>
		
	<!-- Application -->
		<script type="text/javascript" src="<c:url value='/js/application/applicationStart.js' />"></script>
		
	<!-- Ajax error -->
		<script type="text/javascript" src="<c:url value='/js/error/handleError.js' />"></script>
		
	<!-- Content -->
		<script type="text/javascript" src="<c:url value='/js/content/refreshContent.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/content/refreshMainContent.js' />"></script>

	<!-- ApiIdentifier -->
		<script type="text/javascript" src="<c:url value='/js/apiIdentifier/refreshMarkets.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/apiIdentifier/refreshCurrencies.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/apiIdentifier/refreshApiIdentifier.js' />"></script>
		<script type="text/javascript" src="<c:url value='/js/apiIdentifier/isIdentifierAvailable.js' />"></script>
		
	<!-- PriceTracker -->	
		<script type="text/javascript" src="<c:url value='/js/priceTracker/priceTracker.js' />"></script>
		