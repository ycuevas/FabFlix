/**
 * Generic functions for project 2.
 */

// I know polluting the global namespace is bad but this is a project for school
// :D
// Gets the value of a query parameter in the URI
// eg: http://localhost:8080/index.html?error=yes
// To get the value of yes do getParameterByName('error')


function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}



$(document).ready(function(){
	var buttonVal;
	var rowId;
	var buttonName;
	var form;
	
	$('table tbody tr').each(function() {
		rowId = $(this).attr('id');
		$(this).qtip({
		content:{
			text: 'Loading...',	
			ajax: {
            url: "/project4/servlet/SingleMovie?movieId="+rowId, 
            type: 'GET',
            datatype: "html",
            success: function(data){
            		var response = $(data);
            		response.find('H1').remove();
            		response.find('H3').replaceWith(function() {
                        return '<H4>' +$(this).text() + '</H4>'});
            		response.find("#buttons").remove();
            		$(response.get(5)).attr('id', 'movie-container');
            		$(response.find('div').get(3)).removeClass('col-lg-2');
            		$(response.find('div').get(3)).removeClass('col-md-2');
            		$(response.find('div').get(3)).removeClass('col-sm-1');
            		buttonVal = $(response.find('input').get(-1)).attr('value');
            		buttonName = $(response.find('input')).attr('id');
            		form = $(response.find('form'));    		
            		form.submit(function () {
            			 $.ajax({
            			     type: 'POST',
            			     url: "/project4/servlet/AddToCart",
            			     data: {'movie_id' : buttonVal}
            			 });
            			 
            		return false;
//            			    
            		});

            		return response;}}},
			position:{
				at:'center',
				viewport: $(window)},
			hide: {fixed: true},
	        style: { classes: 'myCustomClass'}
			});});
	
	
	$("#srch-term").keyup(function(){
		var unordered = $('#suggestions');
		$.ajax({
			type: "GET",
			dataType: "json",
			url: "/project4/servlet/AutoComplete",
			data: {"searchtext": $("#srch-term").val()},
			success: function(data){
				$(unordered).empty();
				$.each(data, function(key, value) {
//					
					
					unordered.append('<li title ="'+value+'"><a href="/project4/servlet/SingleMovie?movieId='+key+'">'+value+'</li>');
				})
				

			},
			fail:function(){console.log('fail')}
		});
});
});


//$('tr').hover(function() {
//
//
// 
// $(this).addClass('highlight');
// var element = $(this);
// 
//		
//	
//}, 
//function() {
// $(this).removeClass('highlight');
//
// }
//);

