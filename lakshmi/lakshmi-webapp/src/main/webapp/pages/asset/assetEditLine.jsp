<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>
<%@ page import="com.project.lakshmi.model.api.Api" %>

<script type="text/javascript">
  
  	$(document).ready(function() {
  		// On met en place les possibilités market / code
//   		alert("#assetForm-${asset.id}");
  		
  		$('#assetForm-${asset.id}').refreshApiIdentifier();
  		$('#assetForm-${asset.id}').refreshMarkets();
  		$('#assetForm-${asset.id}').refreshCurrencies();
  	});

</script>

<form:form id="assetForm-${asset.id}" method="post" action="asset/save" modelAttribute="asset" autocomplete="off" data-namespace="asset">

	<form:hidden path="id" />
	<c:if test="${asset.apiIdentifier != null}">
		<form:hidden path="apiIdentifier.id" />
	</c:if>
	
	<!-- Cell-->
	<div>
		<form:input path="label"/>
	</div>
	<!-- Cell-->
	<div>
		<form:input path="isin" onchange="$(this).refreshMarkets()"/>
	</div>
	<!-- Cell -->
	<div>
		<form:select path="apiIdentifier.api" onchange="$(this).refreshApiIdentifier()">
			<option value="NONE"></option>
			<form:options items="<%=Api.values()%>" itemLabel="code" itemValue="code"/>
		</form:select>
	</div>
	<!-- Cell -->
	<div>
		<div class="yahooInfos">
			<form:input path="apiIdentifier.symbol"/>
		</div>
	
		<div class="cryptowatchInfos">
			<input type="hidden" name="oldMarket" value="${asset.apiIdentifier.market}">
			<form:select class="marketSelect" path="apiIdentifier.market" onchange="$(this).refreshCurrencies()">
				<option value=""></option>
			</form:select>
			-
			<input type="hidden" name="oldCurrencyId" value="${asset.apiIdentifier.currency.id}">
			<form:select class="currencySelect" path="apiIdentifier.currency.id">
				<option value=""></option>
			</form:select>
		</div>
	</div>
	<!-- Cell-->
	<div>
		<form:input path="link"/>
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