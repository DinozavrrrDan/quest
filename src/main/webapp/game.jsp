<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Text-based Quest Game</title>
</head>
<body>
<p>${message}</p>
<form action="${pageContext.request.contextPath}/game" method="post">
    <c:forEach var="option" items="${options}">
        <button type="submit" name="answer" value="${option}" class="btn">
        ${option}</button>
    </c:forEach>
</form>
<c:if test="${showRestartButton}">
    <form action="${pageContext.request.contextPath}/game?reset=true" method="post">
        <button type="submit" name="answer" value="restart" class="btn restart"><Span>Restart</Span></button>
    </form>
</c:if>

</body>
</html>