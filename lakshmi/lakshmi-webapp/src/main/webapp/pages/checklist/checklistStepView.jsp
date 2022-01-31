<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<form:form method="post" action="checklist/save.do" modelAttribute="checklistStep">

	<form:hidden path="id" />
	<form:hidden path="label" />
	<form:hidden path="advice" />
	<div class="checklistStepLine">
		<div>
			<form:checkbox path="done" onclick="$(this).submitForm();"/>
		</div>
		<div>
			<div>${checklistStep.label}</div>
		</div>
		<div>
			<div>${checklistStep.advice}</div>
		</div>
	</div>
</form:form>

