<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quest Game</title>
</head>
<body>
<h1>Welcome!<br></h1>
<p>There's a text-based quest game. Every next step depends on your current answer. Only one way to win. Want to try? :)</p>
<form action="${pageContext.request.contextPath}/game" method="post">
    <button type="submit" class="button start">Start!</button>
</form>
</body>
</html>