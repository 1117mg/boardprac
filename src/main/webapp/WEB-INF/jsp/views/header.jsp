<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<c:if test="${empty sessionScope.userId}">
    <input type="button" class="Btn" value="로그인" onclick="location.href='/login'" />
    <input type="button" class="Btn" value="회원가입" onclick="location.href='/join'" />
</c:if>
<c:if test="${not empty sessionScope.userId}">
    <strong>${sessionScope.username} 님</strong>
    <button id="logout" onClick="location.href='/logout'">로그아웃</button>
</c:if>
</body>

<script>
    $(function() {
        $('.Btn').click(function() {
            console.log("로그인 버튼 클릭.");
            location.href = '<c:url value="/login"/>';
        })
    })
</script>
</html>