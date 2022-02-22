<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
<%--
    JSP <%= %> 스크립틀릿 사용 시
    <li>id=<%= ((Member)request.getAttribute("member")).getId() %></li>
    <li>username=<%= ((Member)request.getAttribute("member")).getUsername() %></li>
    <li>age=<%= ((Member)request.getAttribute("member")).getAge() %></li>
--%>

    <%-- JSP EL 표현식 사용 시 --%>
    <li>id=${ member.id }</li>
    <li>username=${ member.username }</li>
    <li>age=${ member.age }</li>
    <%--
        JSP 표현식 사용시 조회 : getId 로 자동으로 갑 가져옴
        JSP 표현식 사용시 등록 : setId 로 자동으로 값 넣어줌
    --%>

</ul>
<a href="/index.html"/>메인</a>
</body>
</html>
