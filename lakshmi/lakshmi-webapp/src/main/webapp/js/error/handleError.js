/**
 * Classic error handling, must be overriden
 */

$.fn.handleError = function(errorMessage) {
	
	return $(this).each(function() {
		let errorContainer = $(this).closest('#contentContainer');
		
		errorContainer.text(errorMessage.responseText);
	})
	
}

/**
 * Classic error handling, must be overriden
 */

$.handleError = function(errorMessage) {

	return $('#contentContainer').handleError(errorMessage);
}