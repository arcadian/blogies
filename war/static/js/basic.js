
function hidecomment(id,linkref){
    $('#com-cont-' + id).toggle()
    thelink = $(linkref)
    thelink.text() == '[-]' ? thelink.text('[+]') : thelink.text('[-]')
}

function callserver(elem){
	$("#test").load("ajax.htm");
}

function showpost(someid){
	$("#blogEntryArea").load("blog.htm", {'blogid': someid} );
}

function loadposts(){
	$("#navigator").load("blogposts.htm");
}
/*$(document).ready(function() {
	$("input").click(function() {
		alert("Hello world!");
	}); 	  	   
     
	function hidecomment(elem){
		var t=$(elem).thing();
  		t.hide().find(".noncollapsed:first, .midcol:first").hide().end().show().find(".entry:first .collapsed").show();
  		if(t.hasClass("message")){
  			$.request("collapse_message",{"id":$(t).thing_id()});
  		}else{
  			t.find(".child:first").hide();
  		}
  	}
	
	
});  
*/	

