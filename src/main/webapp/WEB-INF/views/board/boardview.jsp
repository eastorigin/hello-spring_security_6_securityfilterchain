<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 내용 조회</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/board/reply/reply.js"></script>
  </head>
  <body>
    <div class="membermenu">
      <jsp:include page="../member/membermenu.jsp"></jsp:include>
    </div>
    <h1>게시글 조회</h1>
    <div class="gird grid-view-board" data-board-id="${boardVO.id}">
      <label for="subject">제목</label>
      <div>${boardVO.subject}</div>
    </div>
    <br />
    <label for="email">이메일</label>
    <div>${boardVO.memberVO.name} (${boardVO.memberVO.email})</div>
    <br />
    <label for="viewCnt">조회수</label>
    <div>${boardVO.viewCnt}</div>
    <br />
    <label for="originFileName">첨부파일</label>
    <div>
      <a href="/board/file/download/${boardVO.id}">${boardVO.originFileName}</a>
    </div>
    <br />
    <label for="crtDt">등록일</label>
    <div>${boardVO.crtDt}</div>
    <br />
    <label for="mdfyDt">수정일</label>
    <div>${boardVO.mdfyDt}</div>
    <br />
    <label for="content">내용</label>
    <div>${boardVO.content}</div>
    <br />

    <div class="replies">
      <!-- 댓글의 목록이 노출된다 -->
      <div class="reply-items"></div>
      <!-- 댓글을 작성하는 양식을 보여준다 -->
      <div class="write-reply">
        <textarea class="txt-reply"></textarea>
        <button id="btn-save-reply">등록</button>
        <button id="btn-cancel-reply">취소</button>
      </div>
    </div>

    <div class="btn-group">
      <c:if test="${sessionScope._LOGIN_USER.email eq boardVO.email}">
        <div class="right-align">
          <a href="/board/modify/${boardVO.id}">수정</a>
          <a href="/board/delete/${boardVO.id}">삭제</a>
        </div>
      </c:if>
    </div>

    <template id="reply-template">
      <div
        class="reply"
        data-reply-id="{replyId}"
        style="padding-left: {paddingLeft}rem"
      >
        <div class="author">{authorName} ({authorEmail})</div>
        <div class="recommend-count">추천수: {recommendCount}</div>
        <div class="datetime">
          <span class="crtdt">등록: {crtDt}</span>
          <!-- 등록날짜 및 시간과 수정날짜 및 시간이 다를 때에만 수정을 노출 -->
          <span class="mdfydt">수정: {mdfyDt}</span>
        </div>
        <!-- 내용물을 그대로 노출시킨다 -->
        <pre class="content">{content}</pre>
        <!-- 로그인한 사용자가 작성한 댓글일 경우에만 노출 -->
        <div class="my-reply">
          <span class="modify-reply">수정</span>
          <span class="delete-reply">삭제</span>
          <span class="re-reply">답변하기</span>
        </div>
        <!-- 다른 사용자가 작성한 댓글일 경우에만 노출 -->
        <div class="other-reply">
          <span class="recommend-reply">추천하기</span>
          <span class="re-reply">답변하기</span>
        </div>
      </div>
    </template>
  </body>
</html>
