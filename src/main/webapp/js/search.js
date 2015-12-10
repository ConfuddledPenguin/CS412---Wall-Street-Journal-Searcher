/**
 * Created by Tom on 23/11/2015.
 */

var lastrequest = {};

var resultIndex = 0;

var resultsState = [];
var resultStore =[];


$(document).ready(function(){

	$('#searchForm').submit(function(){

		/*var request = {
			searchString: ($('#s').val() === "") ? null : $('#s').val(),
			author: ($('#author').val() === "") ? null : $('#author').val(),
			title: ($('#title').val() === "") ? null : $('#title').val(),
			date: null,
            startDate: ($('#sdate').val() === "") ? null : $('#sdate').val(),
            endDate: ($('#edate').val() === "") ? null : $('#edate').val(),
			startAt: 1,
			perPage: 10
		};*/
		var track = "";
		if (!($('#author').val() === "")){
			track = $('#author').val();
		} else if (!($('#title').val() === "")){
			track = $('#title').val();
		} else if (!($('#s').val() === "") && !($('#sdate').val() === "") && !($('#edate').val() === "")){
			track = $('#s').val();
		}

		var request = {
			searchString: ($('#s').val() === "") ? track : $('#s').val(),
			author: ($('#author').val() === "") ? null : $('#author').val(),
			title: ($('#title').val() === "") ? null : $('#title').val(),
			date: null,
			startDate: ($('#sdate').val() === "") ? null : $('#sdate').val(),
			endDate: ($('#edate').val() === "") ? null : $('#edate').val(),
			startAt: 1,
			perPage: 10
		};

		search(request);
		return false;
	});

    $('#advanced-search-reveal').click(function(){

        if($('#advanced-search-form').hasClass('no-display')){
            $('#advanced-search-reveal').text("Hide Advanced Search");
        }else{
            $('#advanced-search-reveal').text("Show Advanced Search");
        }


        $('#advanced-search-form').toggleClass('no-display');

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
