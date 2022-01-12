<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<form:form method="post" action="checklist/save.do" modelAttribute="checklistStep" autocomplete="off">

	<form:hidden path="id" />
	<form:hidden path="checklist.id" />
	<form:hidden path="checklistStepInfos.id" />
	<div class="checklistStepLine">
		<div>
			<form:checkbox path="started" onclick="$(this).submitForm();"/>
		</div>	
		<div>
			<form:checkbox path="ended"/>
		</div>
		<div>
			<div>${checklistStep.checklistStepInfos.label}</div>
		</div>	
	</div>
</form:form>

