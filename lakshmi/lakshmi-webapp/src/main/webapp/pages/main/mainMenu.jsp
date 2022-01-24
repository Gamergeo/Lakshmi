<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/main/mainMenu.css' />" rel="stylesheet" media="all" type="text/css">

<div id="menu">

	<span class="menuItem">
		<l:button size="menu" label="Accueil" onclick="$.refreshMainContent({'url' : 'main/startContent.do'});"/>
	</span>

	<span class="menuItem">
		<l:button size="menu"  label="Checklist" onclick="$.refreshMainContent({'url' : 'checklist/view.do'});"/>
	</span>

	<span class="menuItem">
		<l:button size="menu" label="Import CSV" onclick="$.refreshMainContent({'url' : 'operationImporter/view.do'});"/>
	</span>

	<span class="menuItem">
		<l:button size="menu" label="Generer prix" onclick="$.refreshMainContent({'url' : 'priceTracker/view.do'});"/>
	</span>
	
	<span class="menuItem">
		<l:button size="menu" label="Asset" onclick="$.refreshMainContent({'url' : 'asset/list.do'});"/>
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