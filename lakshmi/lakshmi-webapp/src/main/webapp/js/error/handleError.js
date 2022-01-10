$.handleError = function(errorMessage) {
	return $('#mainErrorMessage').handleError(errorMessage);
}

$.fn.handleError = function(errorMessage) {
	
	return $(this).each(function() {
		let errorContainer = $(this).closest('.contentBody').siblings('.errorMessage');
		
		if (!errorContainer.length) {
			errorContainer = $('#mainErrorMessage');
		}
		
		errorContainer.show();
		errorContainer.text(errorMessage.responseText);
	})
	
}