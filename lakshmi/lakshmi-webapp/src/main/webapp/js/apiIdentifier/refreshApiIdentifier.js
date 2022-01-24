/**
 * Refresh la liste des market pour un asset, et met  à jour le select correspondant
 */
$.fn.refreshApiIdentifier = function() {
	
	return $(this).each(function() {
		
		let form = $(this).closestForm();
		// On récupère l'api'
		let api = form.find("select[name='apiIdentifier.api']").val();
		
		// Api cryptowatch, on affiche que la partie cryptowatch et on vide le reste
		if ("CRYPTOWATCH" == api) {
			form.find(".cryptowatchInfos").show();
			form.find(".yahooInfos").hide();
			form.find("input[name='apiIdentifier.symbol']").val("");
			$(this).refreshMarkets();
		} else if ("YAHOO" == api) {
			form.find(".cryptowatchInfos").hide();
			form.find(".yahooInfos").show();
			form.find("select[name='apiIdentifier.market']").val("");
			form.find("select[name='apiIdentifier.currency.id']").val("");
		} else if ("NONE" == api) { // Cas vide, on vide tout
			form.find(".cryptowatchInfos").hide();
			form.find(".yahooInfos").hide();
			form.find("select[name='apiIdentifier.market']").val("");
			form.find("select[name='apiIdentifier.currency.id']").val("");
			form.find("select[name='apiIdentifier.symbol']").val("");
		}
	});
}