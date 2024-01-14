<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/config/config.css' />" rel="stylesheet" media="all" type="text/css"> 

<l:pageInfos title="Config"/>

<form:form id="configForm" method="post" action="config/save.do" modelAttribute="config">

	<form:hidden path="id" />
	<div id="yahooKey">
		<span id="currentYahooKey"><a href="https://financeapi.net/dashboard">Voir clé</a></span>
		<span>
			<form:input path="yahooKey" id="yahooKeyInput"/>
	  	</span>
	</div>
		
	<div id="submit">
		<l:button size="menu" onclick="$(this).submitForm();" label="Save"/>
	</div>
</form:form>
