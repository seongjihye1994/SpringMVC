<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // request, response는 import 없이 그냥 사용 가능
    // httpServletRequest, httpServletResponse 서블릿으로 자동 변환되어 사용
    // -> 기존 jsp 없이 서블릿으로 했었던 service() 로직이 자동으로 호출된다고 생각하면 됨.
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);

    memberRepository.save(member);
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%= member.getId() %></li>
    <li>username=<%= member.getUsername() %></li>
    <li>age=<%= member.getAge() %></li>
</ul>
<a href="/index.html"/>메인</a>
</body>
</html>

<!-- 스클립틀릿이 없는 부분은 전부 response.getWriter.write(이 부분에 담긴다.) -->