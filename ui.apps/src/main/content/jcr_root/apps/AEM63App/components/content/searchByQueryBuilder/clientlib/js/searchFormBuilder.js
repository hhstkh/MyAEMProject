$(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	var form = $("#searchForm");

	$(form).submit(function(e) {
		e.preventDefault();
		var formData = new FormData(this);
		$.ajax({
			type : 'POST',
			url : '/bin/mySearchQueryBuilder',
			data: formData,
			success: function(msg) {
				console.log(msg); // display the data returned by the servlet
			},
			error: function (msg) {
				console.log(msg);
			}
		});
	})
})
