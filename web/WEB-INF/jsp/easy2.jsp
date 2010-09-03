<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title>Big Apple</title>
  	<script type="text/javascript" src="static/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="static/js/basic.js"></script>
  	<script type="text/javascript">
  	$(document).ready(function() {
  	}                                    
    </script>                            
  	<link type="text/css" rel="stylesheet" href="static/css/theme.css">
  </head>
  <body>
    <h1>Big Apple</h1>
    <p>BlogTitle: <c:out value="${blogEntry.title}"/></p>
     <tt:hello/>
    <div id="content">
    	<div id="blogEntryArea">
    		<c:out value="${blogEntry.body}" />
    	</div>
	    <div id="commentArea">
	    	<div id="commentContainer">
			    <c:forEach items="${blogEntry.comments}" var="commentEntry">
					<c:set var="nestedLevel" value=""/>
					<c:forEach var="it" begin="1" end="${commentEntry.level}" step="1">
						<c:set var="nestedLevel" value="${nestedLevel}.."/>
					</c:forEach>
					<tt:nest input="XX00XX" times="${commentEntry.level}"/>
					<div class="commentinfo">
						<a name="${commentEntry.commentId}"></a>
						<p><a href="#">${commentEntry.userId}</a>${commentEntry.commentDate}
							<a onclick="hidecomment('${commentEntry.commentId}',this);" href="#">[-]</a>
						</p>
					</div>
					<div class="commentcontents collapsed" id="com-cont-${commentEntry.commentId}"><c:out value="${nestedLevel} ${commentEntry.text}"/></div>
						<c:if test="${not empty commentEntry.parentId}" >
							<div><a href="#${commentEntry.parentId}">parent</a></div>
						</c:if>
					</div>
			    </c:forEach>
			</div>
	    </div>
    </div>
   
  </body>
</html>