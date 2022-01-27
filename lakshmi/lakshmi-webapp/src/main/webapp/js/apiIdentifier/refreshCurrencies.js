/**
 * Refresh la liste des currencies pour un asset, et met  à jour le select correspondant
 */
$.fn.refreshCurrencies = function() {
	
	return $(this).each(function() {
		
		let form = $(this).closestForm();
		let api = form.find("select[name='apiIdentifier.api']").val();

		// Si on n'est pas dans le cas cryptowatch ou kucoin, on ne fait rien
		if ("CRYPTOWATCH" != api && "KUCOIN" != api ) {
			return $(this);
		}
		
		// On récupère l'isin
		let isin = form.find("input[name='isin']").val();
		let market = form.find("select[name='apiIdentifier.market']").val();
		let oldCurrencyIsin = form.find("input[name='oldCurrencyIsin']").val();
		
		let options = new Object();
		options.url = "apiIdentifier/getCurrencies.do?isin="+isin+"&api="+api;
		
		// On precise le market dans le cas cryptowatch
		if ("CRYPTOWATCH" == api) {
			options.url += "&market="+market;
		}
		
		options.success = (result) => {
			// On met à jour le select
			let currencySelect = form.find("select[name='apiIdentifier.currency.isin']");
			currencySelect.find('option').remove();
			currencySelect.append($('<option />'));
			
			$.each(result, function (i, resultItem) {
			    currencySelect.append($('<option>', { 
			        value: resultItem,
			        text : resultItem,
					selected : resultItem == oldCurrencyIsin
			    }));
			});
		};
		
		$.getJson(options);
	});
}