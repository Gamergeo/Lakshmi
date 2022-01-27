<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>

<%@ page import="com.project.lakshmi.model.operation.importer.OperationImporterOrigin" %>

<link href="<c:url value='/css/operation/importer/operationImporter.css' />" rel="stylesheet" media="all" type="text/css">

<l:pageInfos title="Import Order"/>

<script type="text/javascript">
  
	function submitForm() {
		if (confirm("Lancer upload")) {
			options = new Object();
			options.success = (result) => {
				alert(result.message);
			}
			
			$("#importForm").submitForm(options);
		}
	}
	
	function originChange() {
		
		if ($("#originBinance").is(":checked")) {
			$("#feeFile").hide();
		} else {
			$("#feeFile").show();
		}
	}

</script>

<form id="importForm" method="post" action="operationImporter/import.do" enctype="multipart/form-data" >

	<div id="origin">
		<span>
	  		<input onchange="originChange();" type="radio" id="originBinance" name="origin" value="<%=OperationImporterOrigin.BINANCE%>">
	  		<label for="originBinance">Binance</label>
	  	</span>
	  
		<span>
	  		<input onchange="originChange();" type="radio" id="originKucoin" name="origin" value="<%=OperationImporterOrigin.KUCOIN%>">
	  		<label for="originKucoin">Kucoin</label>
	  	</span>
	</div>
	
	<div id="file">
		<input type = "file" name = "file" size = "50" />
	</div>
	
	<div id="feeFile">
		<span>Fee file :</span>
		<input type = "file" name = "feeFile" size = "50" />
	</div>
	
	<div id="submit">
		<l:button size="menu" onclick="submitForm()" label="Upload file"/>
	</div>

</form>
