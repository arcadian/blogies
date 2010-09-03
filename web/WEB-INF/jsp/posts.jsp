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
		<frmt:linkpaginate container="${posts}" pageNum="${pageNum}"/>
		<a href="create.htm">New Entry...</a>
    </div>
    <div id="content">
    	<frmt:postpaginate container="${posts}" pageNum="${pageNum}"/>
	</div>
</body>
</html>