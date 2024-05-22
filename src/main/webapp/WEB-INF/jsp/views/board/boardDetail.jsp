<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>글 상세페이지</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<style>
    #container {
        width: 1000px;
        margin: 20px auto;
    }
</style>
<body>
<%@ include file="../header.jsp"%>
<!-- main -->
<main id= "main">
    <div id="slider">
        <div class="imageWrap1"></div>
    </div>

    <!-- section -->
    <section id="contents">
        <!-- noticeboard -->
        <div class="noticeboard">
            <div class="title" style="margin:0px;">
                <div class="vline"></div>
                <div class="container2">
                    <h3>공지사항 게시판 &#10095</h3>
                </div>
            </div>


            <div class="post-info">
                <p class="post-title">
                    <strong>${post.title}</strong>
                </p>
                <p class="post-metadata" style="margin-bottom: 3px;">
                   <span class="post-info-text"> 작성자:
                      <strong>${post.userId}</strong>
                   </span>
                </p>
                <p style="font-size:14px; color:gray;">
                    <fmt:formatDate value="${post.regdate}" pattern="yyyy-MM-dd HH:mm" />
                    <span class="post-info-text" style="margin-left:10px;"> 조회&nbsp;
                      ${post.hit}
                   </span>
                </p>

                <div class="line"></div>

                <!-- 게시물 내용 표시 -->
                <div class="post-content" id="post-content" style="height: 500px;">${post.content}</div>

                <!-- 이미지 파일 표시 -->
                <c:forEach items="${file}" var="file" varStatus="status">
                    <c:choose>
                        <c:when test="${not empty file.uuid}">
                            <c:forEach var="filepath" items="${file.uuid}">
                                <div class="image-container">
                                    <img src="/image/${file.uuid}_${file.fileName}" alt="게시물 이미지">
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <h1></h1>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


                <div class="line" style="height:2px;"></div>
            </div>



            <!-- 버튼들 -->
            <div class="post-buttons">
                <button type="button" class="btn btn-primary listBtn" style="margin-right:30px;">목록</button>
                <c:if test="${sessionScope.userId eq post.userId}">
                    <button type="submit" class="btn btn-primary goBtn updateBtn">수정</button>
                    <button type="button" class="btn btn-primary goBtn delbtn">삭제</button>
                </c:if>
            </div>
        </div>
    </section>



</main>


</body>

<script>
    $(document).ready(function() {
// 글수정
        $('.updateBtn').click(function() {

            var postId = ${post.bno};

            axios.get('/postDetail/'+postId)
                .then(function(response){
                    var post=response.data;

                    location.href='<c:url value='/write'/>' + '?bno=' + postId;
                })
                .catch(function(error){
                    console.error('Error fetching post details:', error);
                    alert("게시글 정보를 가져오는 데 실패했습니다.")
                })
        })

        $('.listBtn').click(function() {
            location.href = '<c:url value="/main"/>';
        })

        // 삭제 버튼 클릭 이벤트를 처리합니다.
        $(".delbtn").click(function(e)
        {
            e.preventDefault();
            // 알림을 통해 사용자에게 삭제 여부를 확인할 수 있습니다.
            var isConfirmed = confirm("게시글을 삭제하시겠습니까?");
            if (isConfirmed)
            {
                // 액시오스를 사용하여 서버로 DELETE 요청을 보냅니다.
                axios.delete('/deletepost/' + ${post.bno})
                    .then(function (response)
                    {
                        console.log(response.data);
                        alert("게시글이 삭제되었습니다.");
                        // 게시글 목록 페이지로 이동합니다.
                        window.location.href = '/main';
                    })
                    .catch(function (error)
                    {
                        console.error('Error during board deletion:', error);
                        alert("게시글 삭제 실패");
                    });
            }
        });
    });

    /* aside가 (/Announcement/)url이 같은 페이지로 인식되도록 작성함 */
    var currentPageUrl = "/main";
    var menuItems = document.querySelectorAll('.menubar .list');
    menuItems.forEach(function(item) {
        if (item.getAttribute('href') === currentPageUrl) {
            item.classList.add('active');
        }
    });

</script>
</html>
