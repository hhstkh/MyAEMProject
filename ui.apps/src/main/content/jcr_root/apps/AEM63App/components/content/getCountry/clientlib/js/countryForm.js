$(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	var form = $("#countryForm");

	$(form).submit(function(e) {
		e.preventDefault();
		$("#countryResult").empty();
		var formData = new FormData(this);
		$.ajax({
			type : 'POST',
			processData: false,
            contentType: false,
        	cache: false,
			url : '/bin/getCountry',
			data: formData,
			success: function(data) {
				var jsonData = JSON.stringify(data);
				$("#countryResult").html(jsonData);
			},
			error: function (msg) {
				console.log(msg);
			}
		});
	})

})


