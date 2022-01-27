/**
 * Refresh la liste des market pour un asset, et met  à jour le select correspondant
 */
$.fn.refreshApiIdentifier = function() {
	
	return $(this).each(function() {
		
		let form = $(this).closestForm();
		// On récupère l'api'
		let api = form.find("select[name='apiIdentifier.api']").val();
		
		// Api Yahoo : on affiche le symbol
		if ("YAHOO" == api) {
			form.find(".symbol").show();
			
			form.find(".market").hide();
			form.find(".currency").hide();
			form.find("select[name='apiIdentifier.market']").val("");
			form.find("select[name='apiIdentifier.currency.isin']").val("");
		}
		
		// Api kucoin : on affiche la currency
		if ("KUCOIN" == api) {
			form.find(".currency").show();
			
			form.find(".symbol").hide();
			form.find(".market").hide();
			form.find("input[name='apiIdentifier.symbol']").val("");
			form.find("select[name='apiIdentifier.market']").val("");
			
			$(this).refreshCurrencies();
		} 
		
		// Api cryptowatch, on affiche le market et la currency
		if ("CRYPTOWATCH" == api) {
			form.find(".currency").show();
			form.find(".market").show();
			
			form.find(".symbol").hide();
			form.find("input[name='apiIdentifier.symbol']").val("");
			
			$(this).refreshMarkets();
		} 
		
		// Cas vide, on vide tout
		if ("NONE" == api) { 
			form.find(".symbol").hide();
			form.find(".market").hide();
			form.find(".currency").hide();
			
			form.find("select[name='apiIdentifier.market']").val("");
			form.find("select[name='apiIdentifier.currency.isin']").val("");
			form.find("select[name='apiIdentifier.symbol']").val("");
		}
	});
}