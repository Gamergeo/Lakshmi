<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<%@include file="/pages/head/head.jsp" %>
		
		<link href="<c:url value='/css/main.css' />" rel="stylesheet" media="all" type="text/css">

		<script type="text/javascript">
		
			function backToMain() {
				
				$("#mainContainer").getHtml({
					url: '/lakshmi/main/main.do',
				})
			}
		
			function selectButton() {
				
				$("#contentContainer").getHtml({
					url: '/lakshmi/generateCsv/view.do',
				})
			}
			
		
		</script>
	</head>
	
	<body>
	
		<div id="mainContainer">
			<div id="titleContainer" onclick="backToMain()">
				<span class="title">Lakshmi</span>
			</div>
			
			<div id="contentContainer">
				<div id="buttonContainer">
			
	   				<c:url var="checklistUrl" value="/checklist/view.do"/>
					<div id="checklistButton" class="mainButton" onclick="selectButton()">
						<span>Checklist</span>
					</div>
					
	   				 <c:url var="importCsvUrl" value="/importCsv/view.do"/>
					<div id="importCsvButton" class="mainButton" onclick="window.location.href='${importCsvUrl}'">
						<span>Import CSV</span>
					</div>
			
	   				 <c:url var="checklistUrl" value="/priceTracker/view.do"/>
					<div id="priceTrackerButton" class="mainButton" onclick="window.location.href='${checklistUrl}'">
						<span>Price Tracker</span>
					</div>
				</div>
			</div>
		</div>
	
	</body>
</html>