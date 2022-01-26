$.priceTracker = function() {
	
	let options = new Object();
	options.url = "priceTracker/generate.do";
	options.success = (result) => {
		alert(result.message);
	}
	
	if (confirm("Lancer PriceTracker'")) {
		$.getJson(options);
	}
	
	return $;
}