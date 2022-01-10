$.fn.refreshContent = function(options) {
	
	return $(this).each(function() {
		
		let content = $(this).closest('.content');
		
		// On ne rafraichit le content que si cela est possible
		if (content.length != 0) {
			
			let title = content.find('.contentTitle');
			let body = content.find('.contentBody');
			
			// On efface le message d'erreur actuel
			content.children(".errorMessage").text('');
			content.children(".errorMessage").hide();
			
			let refreshTitle = function () {
				title.html(body.find(".pageTitle").val());
			}
			
			options.success = $.mergeFunction(options.success, refreshTitle);
			
			body.postHtml(options);
		}
	});
}