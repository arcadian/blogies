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
	<div id="content">
    	<div id="post-title" class="posttitel">
    		<h1>Thoughs on programming</h1>
    		<p><c:if test="${not empty blogEntry}"><c:out value="${blogEntry.title}"/></c:if></p>
    	</div>
    	<div id="blog-entry-area" class="blogentryarea">
    		<c:if test="${not empty blogEntry}"><c:out value="${blogEntry.body}"/></c:if>
    	</div>
    	<frmt:printnestedcomments blogEntry="${blogEntry}"/>
	</div>
</body>
</html>