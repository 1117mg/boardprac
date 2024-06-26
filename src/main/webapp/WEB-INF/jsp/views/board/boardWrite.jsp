<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
  <title>글작성</title>

  <script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/ckeditor.js"></script>
  <script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js"></script>

</head>
<style>
  #container {
    width: 1000px;
    margin: 20px auto;
  }
  .ckeditor{
    max-width:500px;
    min-height:300px;
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

    <div class="noticeboard">
      <div class="title">
        <div class="vline"></div>
        <div class="container2">
          <h3>공지사항 게시판 &#10095</h3>
        </div>
      </div>
      <div class="title1" style="margin-left: 60px;">
        <div class="vline"></div>
        <h2>글 작성</h2>
      </div>

      <form action="insertpost" method="post" style="margin: 30px 60px 30px 60px;">
        <table class="board-table">
          <colgroup>
            <col width="15%">
            <col width="35%">
            <col width="15%">
            <col width="35%">
          </colgroup>
          <tbody>
          <input type="hidden" name="bno" id="bno" value="${post.bno}">
          <tr>
            <th scope="row" bgcolor="#F9F9F9">제목</th>
            <td colspan="4" style="text-align: left; padding-left:10px;">
              <input type="text" class="form-control1" name="title"
                     id="title" value="${post.title}" placeholder="제목을 입력하세요. " required>
            </td>
          </tr>
          <tr>
            <th scope="row" bgcolor="#F9F9F9">작성자</th>
            <td scope="row" style="text-align: left; padding-left:10px;">
              ${sessionScope.username}
            </td>
          </tr>
          <tr>
            <th scope="row" bgcolor="#F9F9F9">내용</th>
            <td colspan="4" style="padding: 10px;">
                            <textarea class="form-control " name="content" id="ckeditor" rows="6"
                                      placeholder="내용을 입력하세요. ">${post.content}</textarea>
            </td>
          </tr>
          <tr>
            <th scope="row" bgcolor="#F9F9F9">첨부파일</th>
            <td>
              <input type="file" name="imageFiles" id="imageFiles" multiple="multiple" onchange="uploadFile(this)">
              <div id="uploadDiv">
                <c:forEach items="${getFile}" var="file" varStatus="status">
                  <div class="uploadResult">
                    <span>${status.count}. </span><a href="/downloadFile?uuid=${file.uuid}&fileName=${file.fileName}" download>${file.fileName}</a>
                    <input type="hidden" name="uuids" value="${file.uuid}">
                    <input type="hidden" name="fileNames" value="${file.fileName}">
                    <input type="hidden" name="contentTypes" value="${file.contentType}">
                    <label class="delBtn">◀ 삭제</label>
                  </div>
                </c:forEach>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <br><br><br>
        <div class=button-container>
          <input type="hidden" value="${post.bno}" name="bno">
          <button type="submit">글 작성</button>
          <button type="button" class="btn btn-primary CancleBtn">취&nbsp; 소</button>
        </div>

      </form>
    </div>

  </section>
  <!-- //section -->
</main>
<!-- //main -->

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">

  // delBtn 개별 삭제 버튼
  $(function () {
    delBtnFile();
  });

  function delBtnFile() {
    $(".delBtn").on("click",function () {
      $(this).parent().remove();
      stCnt();
    });
  }

  // status.count ajax사용시 다시 그려주기
  function stCnt() {
    $('.uploadResult').each(function(index, item){
      $(this).children('span').html(index+1+'. ');
    });
  }

  // 업로드 수행
  function uploadFile(obj) {
    let files = obj.files;
    let formData = new FormData();

    for (var i = 0; i < files.length; i++){
      formData.append("files", files[i]);
    }

    $.ajax({
      type: 'post',
      enctype: 'multipart/form-data',
      url: '/ajaxFile',
      data: formData,
      processData: false,
      contentType: false,
      success: function(data) {
        console.log(data);
        let result = "";
        let cnt = $('.uploadResult').length;
        for (var i = 0; i < data.length; i++){
          result += '<div class="uploadResult">';
          result += '<span>' +(cnt + i + 1)  +  '. </span><a href="/downloadFile?uuid=' + data[i].uuid + '&fileName=' + data[i].fileName + '" download>' + data[i].fileName + '</a>';
          result += '<input type="hidden" name="uuids" value="' + data[i].uuid + '">';
          result += '<input type="hidden" name="fileNames" value="' + data[i].fileName + '">';
          result += '<input type="hidden" name="contentTypes" value="' + data[i].contentType + '">';
          result += '<label type="button" class="delBtn"> ◀ 삭제</label>';
          result += '</div>';
        }
        $('#uploadDiv').append(result);
        delBtnFile();
      }
    });

  }

  $('.CancleBtn').click(function() {
    location.href = "<c:url value='/main'/>";
  })

  ClassicEditor.create(document.querySelector('#ckeditor'), {
    toolbar: ['bold', 'italic', '|',  'link','undo','redo'],
    language:'ko'
  }).then(editor => {
    window.editor = editor;
  }).catch( error => {
    console.error( error );
  });
</script>

</body>