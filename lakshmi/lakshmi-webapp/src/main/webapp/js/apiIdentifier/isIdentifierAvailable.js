/**
 * Refresh tous identifiants cryptowatch
 */
$.fn.isIdentifierAvailable = function() {
	
	return $(this).each(function() {
		
		let form = $(this).closestForm();
		
		// On récupère l'id'
		let id = form.find("input[name='id']").val();
	
		let options = new Object();
		options.url = "apiIdentifier/isIdentifierAvailable.do?id="+id;
		options.success = (result) => {
			if (result.message == "true") {
				form.find(".apiIdentifier").css("color", "green");
			} else {
				form.find(".apiIdentifier").css("color", "red");
			}
		}
		$.getJson(options);
	});
}