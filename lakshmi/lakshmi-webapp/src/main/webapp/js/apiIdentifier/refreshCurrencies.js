/**
 * Refresh la liste des currencies pour un asset, et met  à jour le select correspondant
 */
$.fn.refreshCurrencies = function() {
	
	return $(this).each(function() {
		
		let form = $(this).closestForm();
		let api = form.find("select[name='apiIdentifier.api']").val();

		// Si on n'est pas dans le cas cryptowatch, on ne fait rien
		if ("CRYPTOWATCH" != api) {
			return $(this);
		}
		
		// On récupère l'isin
		let isin = form.find("input[name='isin']").val();
		let market = form.find("select[name='apiIdentifier.market']").val();
		let oldCurrencyId = form.find("input[name='oldCurrencyId']").val();
		
		let options = new Object();
		options.url = "apiIdentifier/getCurrencies.do?isin="+isin+"&market="+market;
		options.success = (result) => {
			// On met à jour le select
			let currencySelect = form.find("select[name='apiIdentifier.currency.id']");
			currencySelect.find('option').remove();
			currencySelect.append($('<option />'));
			
			$.each(result, function (i, resultItem) {
			    currencySelect.append($('<option>', { 
			        value: resultItem.id,
			        text : resultItem.label,
					selected : resultItem.id == oldCurrencyId
			    }));
			});
		};
		
		$.getJson(options);
	});
}