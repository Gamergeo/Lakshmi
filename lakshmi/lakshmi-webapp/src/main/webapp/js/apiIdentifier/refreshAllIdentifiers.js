/**
 * Refresh tous identifiants cryptowatch
 */
$.refreshAllIdentifiers = function() {
	
	let options = new Object();
	options.url = "apiIdentifier/refreshAllIdentifiers.do";
	options.success = (result) => {
		alert(result.message);
	}
	$.getJson(options);
}