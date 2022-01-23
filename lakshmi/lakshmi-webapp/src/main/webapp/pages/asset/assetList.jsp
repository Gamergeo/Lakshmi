<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/asset/asset.css' />" rel="stylesheet" media="all" type="text/css">

<script type="text/javascript">
  
  	$(document).ready(function() {
  		let options = {
  	  		'sortColumns' : [1, 2],
  			'searchColumn' : 1,
  			'pagination' : true,
  		}
  		
  		$('.assetTable').datatable(options);
  	});

</script>

<l:pageInfos title="Asset management"/>

<div class="assetTable">
	<!-- Header line -->
	<div>
	 	<!-- Header cell -->
		<div> 
			<div class="smallButton addButton" onclick="$(this).addLine('asset/editLine.do');">&nbsp;</div>
			<div class="headerLabel">Label</div>
		</div>
		<!-- Header cell -->
		<div>Isin</div> 
		<!-- Header cell -->
		<div>Api</div> 
		<!-- Header cell -->
		<div>Api identifier</div>
		<!-- Header cell -->
		<div>Lien</div>
		<!-- Header cell -->
		<div>&nbsp;</div>
		<!-- Header cell -->
		<div>&nbsp;</div>
	</div>

	<c:forEach items="${assets}" var="asset">
		
		<!-- Line -->
		<div>
			<c:set var="asset" value="${asset}" scope="request" />
			<jsp:include page="assetViewLine.jsp" />
		</div>
	</c:forEach>
</div>
