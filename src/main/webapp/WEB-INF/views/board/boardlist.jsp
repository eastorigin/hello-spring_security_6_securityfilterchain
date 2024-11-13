<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!-- JSTL을 위한 Directive 선언 -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 상황판</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/board/boardlist.js"></script>
  </head>
  <body>
    <div>
      <div class="membermenu">
        <jsp:include page="../member/membermenu.jsp"></jsp:include>
      </div>
      <div class="right-align">
        총 ${boardListVO.boardCnt}건의 게시글이 검색되었습니다
      </div>
      <div class="table-box">
        <table class="table">
          <thead>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>이메일</th>
              <th>조회수</th>
              <th>등록일</th>
              <th>수정일</th>
            </tr>
          </thead>
          <tbody>
            <!--
            BoardListVO boardListVO = new BoardListVO();
            List<BoardVO> boadList = boardListVO.getBoardList();
            for (BoardVO board : boardList){				
              ....
            }
          -->
            <!--
          ${boardListVO.boardList}가 비어있지 않다면
          foreach를 통해 목록을 보여주고 그렇지 않다면
          "게시글이 없습니다"를 보여준다
          -->
            <c:choose>
              <c:when test="${not empty boardListVO.boardList}">
                <c:forEach items="${boardListVO.boardList}" var="board">
                  <tr>
                    <td>${board.id}</td>
                    <td>
                      <a href="/board/view?id=${board.id}">${board.subject}</a>
                    </td>
                    <td>${board.memberVO.name} (${board.memberVO.email})</td>
                    <td>${board.viewCnt}</td>
                    <td>${board.crtDt}</td>
                    <td>${board.mdfyDt}</td>
                  </tr>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <tr>
                  <td colspan="6">게시글이 없습니다</td>
                </tr>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>
      </div>
      <div>
        <form class="search-form">
          <input
            type="hidden"
            name="pageNo"
            class="page-no"
            value="${searchBoardVO.pageNo}"
          />

          <select name="listSize" class="list-size">
            <option value="10" ${"10" eq searchBoardVO.listSize ? "selected" : ""}>10개</option>
            <option value="20" ${"20" eq searchBoardVO.listSize ? "selected" : ""}>20개</option>
            <option value="30" ${"30" eq searchBoardVO.listSize ? "selected" : ""}>30개</option>
            <option value="40" ${"40" eq searchBoardVO.listSize ? "selected" : ""}>40개</option>
            <option value="50" ${"50" eq searchBoardVO.listSize ? "selected" : ""}>50개</option>
            <option value="100" ${"100" eq searchBoardVO.listSize ? "selected" : ""}>100개</option>
          </select>

          <select class="search-type" name="searchType">
            <option value="boardId" ${"boardId" eq searchBoardVO.searchType ? "selected" : ""}>글번호</option>
            <option value="subject" ${"subject" eq searchBoardVO.searchType ? "selected" : ""}>제목</option>
            <option value="content" ${"content" eq searchBoardVO.searchType ? "selected" : ""}>내용</option>
            <option value="subject+content" ${"subject+content" eq searchBoardVO.searchType ? "selected" : ""}>제목 + 내용</option>
            <option value="name" ${"name" eq searchBoardVO.searchType ? "selected" : ""}>작성자 이름</option>
            <option value="email" ${"email" eq searchBoardVO.searchType ? "selected" : ""}>작성자 이메일</option>
          </select>
          <input type="text" class="search-keyword" name="searchKeyword" value="${searchBoardVO.searchKeyword}"/>
          <button type="button" class="search-btn">검색</button>
        </form>
        <ul class="page-nav">
          <c:if test="${searchBoardVO.hasPrevGroup}">
            <li>
              <a href="javascript:movePage(0)">처음</a>
            </li>
            <li>
              <a
                href="javascript:movePage(${searchBoardVO.prevGroupStartPageNo});"
                >이전</a
              >
            </li>
          </c:if>

          <c:forEach
            begin="${searchBoardVO.groupStartPageNo}"
            end="${searchBoardVO.groupEndPageNo}"
            step="1"
            var="p"
          >
            <li class="${p eq searchBoardVO.pageNo ? 'active' : ''}">
              <a href="javascript:movePage(${p});">${p + 1}</a>
            </li>
          </c:forEach>

          <c:if test="${searchBoardVO.hasNextGroup}">
            <li>
              <a
                href="javascript:movePage(${searchBoardVO.nextGroupStartPageNo});"
                >다음</a
              >
            </li>
            <li>
              <a href="javascript:movePage(${searchBoardVO.pageCount -1});"
                >끝</a
              >
            </li>
          </c:if>
        </ul>
      </div>
      <c:if test="${not empty sessionScope._LOGIN_USER}">
        <div class="right-align">
          <a href="/board/excel/download">엑셀 다운로드</a>
          <a href="/board/write">게시글 등록</a>
        </div>
      </c:if>
    </div>
  </body>
</html>
