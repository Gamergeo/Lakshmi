$.applicationStart = function() {
	
//	Freyr_application.startNavigation();
//	$("#mainMenu").accordeonMenu();
	$('#mainContent').refreshContent({'url' : "main/startContent.do"});
	
	return $;
}

//class Freyr_application {
//	
//	static startNavigation() {
//
//		$("#navigation").navigation();
//		
//		let refreshMainContent =  function() {
//			let navigationPage = $.navigationPage();
//			$.refreshMainContent(navigationPage.url, navigationPage.data, true);
//		};
//		
//		$("#navigationPrevious").on('click', refreshMainContent);
//		$("#navigationNext").on('click', refreshMainContent);
//	}
//}