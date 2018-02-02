$(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	var form = $("#importForm");


	$(form).submit(function(e) {
		e.preventDefault();
		var formData = new FormData(this);
		$.ajax({
			type : 'POST',
    		processData: false,
            contentType: false,
        	cache: false,
			url : '/bin/myImportJob',
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


