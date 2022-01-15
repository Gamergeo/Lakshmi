<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<%@ page import="com.project.lakshmi.model.operation.importer.OperationImporterOrigin" %>

<l:pageInfos title="Import Order"/>

<form method="post" action="operationImporter/import.do" enctype="multipart/form-data" >

	<div>
		<select name="origin">
		    <option value="">--Please choose an option--</option>
		    <option value="<%=OperationImporterOrigin.BINANCE%>">Binance</option>
		</select>
	</div>
	<div>
		<input type = "file" name = "file" size = "50" />
	</div>
	
	<div>
		<div onclick="$(this).submitForm()">Upload file</div>
<!-- 		<input type = "submit" value = "Upload File" /> -->
	</div>

</form>
