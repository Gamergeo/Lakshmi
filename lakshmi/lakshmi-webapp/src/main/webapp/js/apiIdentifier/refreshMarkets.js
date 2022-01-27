/**
 * Refresh la liste des market pour un asset, et met  à jour le select correspondant
 */
$.fn.refreshMarkets = function() {
	
	return $(this).each(function() {
		
		let form = $(this).closestForm();
		let api = form.find("select[name='apiIdentifier.api']").val();
		
		// Si on n'est pas dans le cas cryptowatch, on ne fait rien
		if ("CRYPTOWATCH" != api) {
			return $(this);
		}
		
		// On récupère l'isin
		let isin = form.find("input[name='isin']").val();
		let oldMarket = form.find("input[name='oldMarket']").val();
		
		let options = new Object();
		options.url = "apiIdentifier/getMarkets.do?isin="+isin+"&api="+api;
		options.success = (result) => {
			// On met à jour le select
			let marketSelect = form.find("select[name='apiIdentifier.market']");
			marketSelect.find('option').remove();
			marketSelect.append($('<option />'));
			
			$.each(result, function (i, resultItem) {
			    marketSelect.append($('<option>', { 
			        value: resultItem,
			        text : resultItem,
					selected : resultItem == oldMarket
			    }));
			});
			
			$(this).refreshCurrencies();
		};
		
		$.getJson(options);
	});
}