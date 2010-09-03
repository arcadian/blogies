<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head>
	<title>Big Apple</title>
 	<script type="text/javascript" src="static/js/jquery-1.4.2.js"></script>
 	<script type="text/javascript" src="static/js/basic.js"></script>
 	<script type="text/javascript">
 		$(document).ready(function() {
				//alert("Add something");
 				});
                                 
	</script>                            
	<link type="text/css" rel="stylesheet" href="static/css/theme.css">
</head>
<body>
	<fmrt:hello/>
	<div id="errorMsgContainer" class="errorMsg">
		<c:if test="${not empty errorMsg}"><c:out value="${errorMsg}"/></c:if>
	</div>
	<div>
	<form:form name="input" action="addcomment.htm" method="POST">
		<div>
			<div>Comment: <textarea name="text" cols=40 rows=6></textarea></div>
			<input type="hidden" name="parentId" value="${parentId}" />
			<input type="hidden" name="blogId" value="${blogId}" />
			<input type="submit" value="Reply" />
		</div>
	</form:form>
	</div> 
</body>
</html>

