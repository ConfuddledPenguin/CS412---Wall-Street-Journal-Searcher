/**
 * Created by Tom on 23/11/2015.
 */

var lastrequest = {};

var resultIndex = 0;

var resultsState = [];
var resultStore =[];


$(document).ready(function(){

	$('#searchForm').submit(function(){

		var request = {
			searchString: document.getElementById("s").value, //'bank',
			author: document.getElementById("s").value,
			title: null,
			date: null,
			startAt: 1,
			perPage: 10
		};

		search(request);
		return false;
	});


	function search(request){

		lastrequest = request;

		var apiURL = 'http://localhost:8080/api/search.json';

		$.ajax({
			type: 'POST',
			url: apiURL,
			data: JSON.stringify( request),
			success: function(data){
				displayPaginationControls(data.meta);
				handleCorrections(data.result.corrections);
				handleResult(data.result.results);
			},
			dataType: 'json',
			contentType: 'application/json'
		});
	}

	function displayPaginationControls(meta){

		$('#pagination-page-number').text(meta.pageNumber);
		$('#pagination-number-of-pages').text('an undefined number');

		$('#pagination').removeClass('no-display');

		if(meta.pageNumber === 1){
			$('#pagination-back').addClass('no-display');
		}else{
			$('#pagination-back').removeClass('no-display');
		}
	}

	$('#pagination-back').click(function(){

		lastrequest.startAt -= 1;

		if(lastrequest.startAt < 1) lastrequest.startAt = 1;

		search(lastrequest);

	});

	$('#pagination-forward').click(function(){

		lastrequest.startAt += 1;

		search(lastrequest);

	});

	function handleCorrections(corrections){

		if(corrections.length === 0){
			$('#search-corrections').addClass('no-display');
		}else{
			$('#search-corrections').removeClass('no-display');

			var container = $('#search-corrections-container');
			container.empty();
			corrections.map(function(item){
				container.append('<p>' + item + '</p>');
			});

			container.children('p').click(function(event){

				var text;
				if($(event.target).is('span')){
					text = $(event.target).parent().text();
				}else{
					text = $(event.target).text();
				}

				document.getElementById("s").value = text;
				lastrequest.searchString = text;

				search(lastrequest);

			})
		}
	}

	function handleResult(results){

		var source   = $("#result-template").html();
		var template = Handlebars.compile(source);

		var resultBox = $('#search-results');
		resultBox.empty();

		results.map(function(item){

			item.index = resultIndex++;

			var html    = template(item);

			resultBox.append(html);
		});

		$('[id=search-result]').click(function(event){

			var result = $(event.target).parents('#search-result');

			if(resultsState[result.attr('index')]){

				result.empty();
				result.html( resultStore[result.attr('index')]);
				resultsState[result.attr('index')] = false;

			}else{

				$.get('http://localhost:8080/' + result.attr('url'))
					.then(function(data){

						resultStore[result.attr('index')] = result.html();
						result.empty();
						result.html( '<div id="article-container">' + data + '</div>');
						resultsState[result.attr('index')] = true;

					})
					.fail(function(){
						alert("It all broke, sorry I guess. \n (Error: failed to get article partial");
					});
			}
		});
	}

});
