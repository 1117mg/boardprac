<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<style>
    #container {
        width: 1000px;
        margin: 20px auto;
    }
</style>
<body>
<%@ include file="../header.jsp"%>
    <h1>공지사항 게시판</h1>
    <table>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일자</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${postList}" var="post">
            <tr>
                <td style="font-size:11px;">${post.bno}</td>
                <td style="text-align:left; padding-left:20px;"><a
                        href="<c:url value='/postDetail/${post.bno}'/>">${post.title}
                </a></td>
                <td style="color:gray;">${post.userId}</td>
                <td style="color:gray;"><fmt:formatDate value="${post.regdate}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td style="color:gray;">${post.hit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 글작성 -->

    <div style="margin-top: 10px; margin-right: 50px;">
        <c:if test="${not empty sessionScope.userId}">
            <button type="button" class="btn btn-primary WriteBtn" style="margin-left: 10px;">글작성</button>
        </c:if>
        <button type="button" class="btn btn-primary goBtn">목록</button>
    </div>

    <!-- 페이징 -->

    <div class="paging">
        <c:forEach begin="1" end="${totalPage}" var="pageNum">
            <c:choose>
                <c:when test="${pageNum == currentPage}">
                    <li class="page-item">
                        <span class="page-link current-page">${pageNum}</span>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="?page=${pageNum}&query=${param.query}">${pageNum}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <form action="<c:url value='/postList/search'/>"
          style="text-align: center;">
        <input type="text" name="query"
               class="form-control search-input" value="${param.query}"
               style="width: 300px;" placeholder="검색어를 입력하세요">
        <button type="submit" class="btn btn-primary search-btn"
        >검색</button>
    </form>
</body>

<script>
    $(function() {
        $('.WriteBtn').click(function() {
            console.log("글쓰기 버튼이 클릭되었습니다.");
            location.href = '<c:url value="/write"/>';
        })

        $('.goBtn').click(function() {
            console.log("목록으로 가기 버튼이 클릭되었습니다.");
            location.href = '<c:url value="/main"/>';
        })
    })


</script>
</html>
