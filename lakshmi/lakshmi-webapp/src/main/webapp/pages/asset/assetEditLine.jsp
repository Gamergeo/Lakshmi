<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@include file="/pages/head/headOnlyMetadata.jsp" %>
<%@ page import="com.project.lakshmi.model.api.Api" %>

<script type="text/javascript">
  
  	$(document).ready(function() {
  		$('#assetForm-${asset.id}').refreshApiIdentifier();
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
	
		<div class="symbol">
			<form:input path="apiIdentifier.symbol"/>
		</div>
	
		<div class="market">
			<input type="hidden" name="oldMarket" value="${asset.apiIdentifier.market}">
			<form:select class="marketSelect" path="apiIdentifier.market" onchange="$(this).refreshCurrencies()">
				<option value=""></option>
			</form:select>
			-
		</div>
		<div class="currency">
			<input type="hidden" name="oldCurrencyIsin" value="${asset.apiIdentifier.currency.isin}">
			<form:select class="currencySelect" path="apiIdentifier.currency.isin">
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