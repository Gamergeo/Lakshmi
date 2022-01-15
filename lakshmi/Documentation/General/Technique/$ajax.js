/**
 * similar to get html
 */
//$.fn.load = function(ajaxData) {
//	
//	return this.each(function() {
//		$(this).getHtml(ajaxData);
//		return this;
//	});
//};

$.fn.getHtml = function(ajaxData) {
	$.get({
		url: ajaxData.url,
		dataType : "html",
		data: ajaxData.data,
		success: (htmlResponse) => {
			this.html(htmlResponse);
			ajaxData.success(htmlResponse);
		}
	});
}

$.fn.postHtml = function(ajaxData) {
	$.post({
		url: ajaxData.url,
		dataType : "html",
		data: ajaxData.data,
		success: (htmlResponse) => {
			this.html(htmlResponse);
			ajaxData.success(htmlResponse);
		}
	});
}

$.getJson = function(ajaxData) {
	
	$.get({
		url: ajaxData.url,
		dataType : "json",
		data: ajaxData.data,
		success: (jsonResponse) => {
			ajaxData.success(jsonResponse);
		},
	});
}

$.postJson = function(ajaxData) {
	$.post({
		url: ajaxData.url,
		dataType : "json",
		contentType : "application/json",
		data: JSON.stringify(ajaxData.data),
		success: (jsonResponse) => {
			ajaxData.success(jsonResponse);
		},
	});
}

//			    headers: { 
//        'Accept': 'application/json',
//        'Content-Type': 'application/json' 
//    },
