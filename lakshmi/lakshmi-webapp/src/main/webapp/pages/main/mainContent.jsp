<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/main/mainContent.css' />" rel="stylesheet" media="all" type="text/css">

<div id="mainContentTitle" class="contentTitle"></div>
<div id="mainErrorMessage" class="errorMessage" onclick='$(this).hide()'></div>
<div id="mainContentBody" class="contentBody"></div>

<!-- <div id="mainContentButtons"> -->
<!-- 	<div id="mainFullscreenButton" class="smallButton fullscreenButton" onclick="$.fullscreen(true);"> </div> -->
<!-- </div> -->
<!-- <div id="mainErrorMessage" class="errorMessage" onclick='$(this).hide()'></div> -->
<!-- <div id="mainContentBody" class="contentBody"></div> -->