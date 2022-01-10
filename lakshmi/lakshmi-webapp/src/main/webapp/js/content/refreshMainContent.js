$.refreshMainContent = function(options) {
	
	// On rafraichit la largeur du content
//	let refreshMainContentWidth = function () {
//		
//		let contentWidth = $(" #mainContentBody").find(".pageWidth");
//		if (contentWidth.length) {
//			$("#mainContent").width(contentWidth.val());									
//		} else {
//			$("#mainContent").width("75%");		
//		}
//	};
//	options.success = refreshMainContentWidth;
	
	// On met  à jour la navigation
//	if (!preventNavRefresh) {
//		let navigationRefresh = function () {
//			$.navigationAdd(options);
//		}
//		
//		options.success = $.mergeFunction(refreshMainContentWidth, navigationRefresh);
//	}
	
	$('#mainContent').refreshContent(options);
	
	return $;
}