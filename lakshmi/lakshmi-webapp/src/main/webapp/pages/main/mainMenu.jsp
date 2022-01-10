<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/main/mainMenu.css' />" rel="stylesheet" media="all" type="text/css">

<div id="menu">

	<span class="menuItem">
		<l:button label="Accueil" onclick="selectButton();"/>
	</span>

	<span class="menuItem">
		<l:button label="Checklist" onclick="selectButton();"/>
	</span>

	<span class="menuItem">
		<l:button label="Import CSV" onclick="selectButton();"/>
	</span>

	<span class="menuItem">
		<l:button label="Generer prix" onclick="selectButton();"/>
	</span>

</div>



<%-- <c:url var="checklistUrl" value="/checklist/view.do"/> --%>
<!-- <div id="checklistButton" class="button mainButton" onclick="selectButton()"> -->
<!-- 	<span>Checklist</span> -->
<!-- </div> -->

<%-- <c:url var="importCsvUrl" value="/importCsv/view.do"/> --%>
<!-- <div id="importCsvButton" class="button mainButton" onclick="selectButton()"> -->
<!-- 	<span>Import CSV</span> -->
<!-- </div> -->

<%-- <c:url var="checklistUrl" value="/priceTracker/view.do"/> --%>
<!-- <div id="priceTrackerButton" class="button mainButton" onclick="selectButton()"> -->
<!-- 	<span>Price Tracker</span> -->
<!-- </div> -->