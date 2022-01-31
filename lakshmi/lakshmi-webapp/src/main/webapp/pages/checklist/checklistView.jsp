<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<link href="<c:url value='/css/checklist/checklist.css' />" rel="stylesheet" media="all" type="text/css">

<script type="text/javascript">
  
  	function uncheckAll() {
  		$(".checklistStepLine").find("input[type='checkbox']").prop("checked", false);
  		$(".checklistStepLine").find("input[type='checkbox']").submitForm();
  	}

</script>

<l:pageInfos title="Checklist"/>

<div id="resetAll">
	<l:button size="menu" label="Uncheck all" onclick="uncheckAll()" />
</div>
<div id="checklist">

	<c:forEach items="${checklistSteps}" var="checklistStep">
		
		<!-- Line -->
		<div>
			<c:set var="checklistStep" value="${checklistStep}" scope="request" />
			<jsp:include page="checklistStepView.jsp" />
		</div>
	</c:forEach>

</div>