<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- 상대경로 사용, [현재 URL이 속한 계층 경로 + /save] -->
<!-- 절대경로 사용 시 : http://localhost:8080/save-->
<!-- 상대경로 사용 시 : http://localhost:8080/servlet-mvc/members/save-->
<!-- 다른 파일에서도 사용하기 위해 상대경로 사용 -->
<form action="save" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>

</body>
</html>
