<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>
<%@ page import="com.project.lakshmi.model.api.Api" %>

<form:form method="post" action="asset/save" modelAttribute="asset" autocomplete="off" data-namespace="asset">

	<form:hidden path="id" />
	<!-- Cell-->
	<div>
		<form:input path="label"/>
	</div>
	<!-- Cell-->
	<div>
		<form:input path="isin"/>
	</div>
	<!-- Cell -->
	<div>
		<form:select path="apiIdentifier.api">
			<option value=""></option>
			<form:options items="<%=Api.values()%>" itemLabel="code" itemValue="code"/>
		</form:select>
	</div>
	<!-- Cell -->
	<div>
		a
	</div>
	<!-- Cell -->
	<div>
		a
	</div>
	<!-- Cell -->
	<div class="buttonCell">
		<div onclick="$(this).saveLine()" class="centered normalButton saveButton">&nbsp;</div>
	</div>
	<!-- Cell -->
	<div class="buttonCell">
		<div onclick="$(this).cancelLine()" class="centered normalButton cancelButton">&nbsp;</div>
	</div>
</form:form>