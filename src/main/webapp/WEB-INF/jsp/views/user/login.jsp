<%--
  Created by IntelliJ IDEA.
  User: lucia
  Date: 2024-05-16
  Time: 오후 3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<% if (request.getAttribute("errorMsg") != null) { %>
<script>
    alert("<%= request.getAttribute("errorMsg") %>");
</script>
<% } %>

<!-- wrap -->
<div id="wrap">

    <!-- main -->
    <main id="main">

        <!-- section -->
        <section id="contents">
            <div class="login-box">

                <div class="loginform">
                    <!-- form -->
                    <form action="/login" method="post">
                        <div class="form-group">
                            <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요" required>
                        </div>
                        <div class="form-group">
                            <input type="password" id="pw" name="pw" placeholder="비밀번호를 입력하세요" required>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="login-button">로그인</button>
                        </div>
                    </form>
                    <!-- // form -->

                    <!-- 회원가입(링크) -->
                    <div class="you-join">
                        <div class="item"><a href="/join">회원가입을 하시겠습니까?</a></div>
                    </div>
                </div>
            </div>
        </section>
        <!-- //section -->
    </main>
    <!-- //main -->

</div>
</body>
</html>
