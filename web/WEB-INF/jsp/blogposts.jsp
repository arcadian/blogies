<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head>
	<title>Big Apple</title>
 	<script type="text/javascript" src="static/js/jquery-1.4.2.js"></script>
 	<script type="text/javascript" src="static/js/basic.js"></script>
 	<script type="text/javascript">
 		$(document).ready(function() {});                                    
	</script>                            
	<link type="text/css" rel="stylesheet" href="static/css/theme.css">
</head>
<body>
	<div id="navigator">
		Recent Posts:
		<frmt:navigator posts="${posts}"/>
		<a href="create.htm">New Entry...</a>
    </div>
    <div id="content">
    	<div id="posttitle">
    		<h1>Thoughs on programming</h1>
    		<p><c:if test="${not empty posts}"><c:out value="${posts[0].title}"/></c:if></p>
    	</div>
    	<div id="blogEntryArea">
    		<c:out value="${blogEntry.body}"/>
    	</div>
    	<div id="test" onclick="return callserver(this);">Click here</div>
	    <frmt:comments comments="${blogEntry.comments}"/>
	</div>
</body>
</html>