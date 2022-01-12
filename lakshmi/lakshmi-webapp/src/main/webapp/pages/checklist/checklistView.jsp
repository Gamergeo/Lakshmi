<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/checklist/checklist.css' />" rel="stylesheet" media="all" type="text/css">

<l:pageInfos title="Checklist"/>

<div id="checklistDate">

	<div id="lastDate">
		<span class="label" id="lastDateLabel">Date de création: </span>
		<span id="lastDateValue">${checklist.creationDate}</span>
	</div>
	
	<div id="otherDates">
		<span id="otherDatesLink">Autres dates</span>
	</div>
</div>

<div id="checklist">

	<!-- Header -->
	<div class="checklistStepLine">
		<div>
			Démarré
		</div>
		<div>
			Fini
		</div>
		<div>
			Label
		</div>
	</div>
	
	<c:forEach items="${checklist.checklistSteps}" var="checklistStep">
		
		<!-- Line -->
		<div>
			<c:set var="checklistStep" value="${checklistStep}" scope="request" />
			<jsp:include page="checklistStepView.jsp" />
		</div>
	</c:forEach>

</div>