/**
 * Created by Tom on 23/11/2015.
 */


$(document).ready(function(){

	$('#searchForm').submit(function(){

		var request = {
			searchString: document.getElementById("s").value, //'bank',
			author: null,
			title: null,
			date: null
		};

		search(request);
		return false;
	});


	function search(request){

		var apiURL = 'http://localhost:8080/api/search.json';

		$.ajax({
			type: 'POST',
			url: apiURL,
			data: JSON.stringify( request),
			success: function(data){
				handleResult(data)
			},
			dataType: 'json',
			contentType: 'application/json'
		});
	}

	function handleResult(data){
		console.log(data);

		var corrections = data.result.corrections;
		var results = data.result.results;

		var source   = $("#result-template").html();
		var template = Handlebars.compile(source);

		var resultBox = $('#search-results');

		results.map(function(item){

			console.log(item);

			var html    = template(item);

			resultBox.append(html);
		})
	}

});
