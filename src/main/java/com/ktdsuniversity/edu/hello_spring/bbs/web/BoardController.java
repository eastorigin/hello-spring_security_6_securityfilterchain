package com.ktdsuniversity.edu.hello_spring.bbs.web;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktdsuniversity.edu.hello_spring.bbs.service.BoardService;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.SearchBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteBoardVO;
import com.ktdsuniversity.edu.hello_spring.common.beans.FileHandler;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.PageNotFoundException;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class BoardController {
	
	public static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private FileHandler fileHandler;

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list") // http://localhost:8080/board/list?pageNo=1&listSize=10
	public String viewBoardList(Model model, SearchBoardVO searchBoardVO) {
		
		BoardListVO boardListVO = this.boardService.getAllBoard(searchBoardVO);
		
		model.addAttribute("boardListVO", boardListVO);
		model.addAttribute("searchBoardVO", searchBoardVO);
		
		return "board/boardlist";
	}
	
	@GetMapping("/board/write")
	public String viewBoardWritePage() {
		return "board/boardwrite";
	}
	
	@PostMapping("/board/write")
	public String doCreateNewBoard(@Valid WriteBoardVO writeBoardVO // @Valid WriteBoardVO의 Validation Check 수행
								,BindingResult bindingResult // @Valid의 실패 결과만 할당받는다
								, Model model
								, @SessionAttribute(value = "_LOGIN_USER", required = false) MemberVO loginMemberVO
								// MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER"); 완벽하게 대체
								) { 
		HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
		writeBoardVO.setIp(request.getRemoteAddr());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("writeBoardVO", writeBoardVO);
			return "board/boardwrite";
		}
		
		// MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER");
		/*
		 * session에서 가져온 MemberVO 인스턴스는 로그인 여부에 따라 NULL 혹은 인스턴스가 할당되어 있다.
		 * memberVO NULL이라면, 로그인이 안 되어있는 것으로 로그인을 유도시켜야 한다.
		 */
		
		if(loginMemberVO == null) {
			return "redirect:/member/login";
		}
		
		writeBoardVO.setEmail(loginMemberVO.getEmail());
		
		boolean isCreate = this.boardService.createNewBoard(writeBoardVO);
		if(logger.isDebugEnabled()) {
			logger.debug("게시글 등록 결과: " + isCreate);			
		}
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/board/view")
	public String viewBoard(Model model, @RequestParam int id) {
		
		BoardVO boardVO = this.boardService.selectOneBoard(id, true);
		
		model.addAttribute("boardVO", boardVO);
		
		return "board/boardview";
	}
	
	@GetMapping("/board/modify/{id}")
	public String viewBoardModifyPage(Model model, @PathVariable int id, @SessionAttribute("_LOGIN_USER") MemberVO memberVO) {
		
		BoardVO boardVO = this.boardService.selectOneBoard(id, false);
		
		if(!boardVO.getEmail().equals(memberVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다");
		}
		
		model.addAttribute("boardVO", boardVO);
		
		return "board/boardmodify";
	}
	
	@PostMapping("/board/modify/{id}")
	public String doModifyOneBoard(@Valid ModifyBoardVO modifyBoardVO, BindingResult bindingResult, @PathVariable int id, Model model, @SessionAttribute(value = "_LOGIN_USER", required = false) MemberVO loginMemberVO ) {
		// ModifyBoardVO에는 id가 없어서 따로 전달
		
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("modifyBoardVO", modifyBoardVO);
			return "board/boardmodify";
		}
		
		if(loginMemberVO == null) {
			return "redirect:/member/login";
		}
		
		modifyBoardVO.setEmail(loginMemberVO.getEmail());
		
		// set ID
		modifyBoardVO.setId(id);
		boolean isUpdated = this.boardService.updateOneBoard(modifyBoardVO);
		
		// post update process
		if(isUpdated) {
			// 성공적으로 수정했다면, 수정한 게시글의 상세조회 페이지로 이동시킨다.
			
			return "redirect:/board/view?id="+id;
		}else {
			// 사용자가 작성했던 내용을 JSP에 그대로 보내준다.
			model.addAttribute("boardVO", modifyBoardVO);
			return "board/boardmodify";
		}
	}

	
	@GetMapping("/board/delete/{id}") //PostMapping form을 이용해서 전송할 때만 쓴다. GetMapping url이 바뀔 때 사용
	public String doDeleteOneBoard(@PathVariable int id, @SessionAttribute("_LOGIN_USER") MemberVO memberVO) {
		
		DeleteBoardVO deleteBoardVO = new DeleteBoardVO();
		deleteBoardVO.setId(id);
		deleteBoardVO.setEmail(memberVO.getEmail());
		
		boolean isDeleted = this.boardService.deleteOneBoard(deleteBoardVO);
		
		if(isDeleted) {
			return "redirect:/board/list";
		}else {
			return "redirect:/board/view?id=" + id;
		}

	}
	
	@GetMapping("/board/file/download/{id}")
	public ResponseEntity<Resource> doDownloadFile(@PathVariable int id) {
		
		// 1. 다운로드 할 파일의 이름을 알기 위해 게시글을 조회한다.
		BoardVO boardVO = this.boardService.selectOneBoard(id, false);
		
		return this.fileHandler.downloadFile(boardVO.getFileName(), boardVO.getOriginFileName());
	}
	
	@GetMapping("/board/excel/download")
	public ResponseEntity<Resource> doDownloadExcel() {
		
		// 1. Workbook (엑셀 워크시트) 생성
		Workbook workbook = new SXSSFWorkbook(-1); // .xlsx 포멧의 워크북 생성
		
		// 2. Workbook에 Sheet 만들기
		Sheet sheet = workbook.createSheet("게시글 목록"); // ss 선택해서 import
		
		// 3. Sheet에 Row 만들기
		Row row = sheet.createRow(0);
		
		// 4. Row에 Cell 만들기
		Cell cell = row.createCell(0);
		cell.setCellValue("번호");
		
		cell = row.createCell(1);
		cell.setCellValue("제목");
		
		cell = row.createCell(2);
		cell.setCellValue("첨부파일명");
		
		cell = row.createCell(3);
		cell.setCellValue("작성자 이메일");
		
		cell = row.createCell(4);
		cell.setCellValue("조회수");
		
		cell = row.createCell(5);
		cell.setCellValue("등록일");
		
		cell = row.createCell(6);
		cell.setCellValue("수정일");
		
		BoardListVO boardListVO = this.boardService.getAllBoard(null);
		List<BoardVO> boardList = boardListVO.getBoardList();
		
		int rowIndex = 1;
		for(BoardVO boardVO : boardList) {
			// Sheet에 Row만들기
			row = sheet.createRow(rowIndex++);
			
			// Row에 Cell 만들기
			cell = row.createCell(0);
			cell.setCellValue(boardVO.getId() + ""); // id: 112 ==> "112"
			
			cell = row.createCell(1);
			cell.setCellValue(boardVO.getSubject());
			
			cell = row.createCell(2);
			cell.setCellValue(boardVO.getFileName());
			
			cell = row.createCell(3);
			cell.setCellValue(boardVO.getEmail());
			
			cell = row.createCell(4);
			cell.setCellValue(boardVO.getViewCnt() + "");
			
			cell = row.createCell(5);
			cell.setCellValue(boardVO.getCrtDt());
			
			cell = row.createCell(6);
			cell.setCellValue(boardVO.getMdfyDt());
		}
		
		// 5. Workbook을 File로 생성
		String excelFileName = this.fileHandler.createXlsxFile(workbook);
		
		// 6. File을 다운로드
		
		
		return this.fileHandler.downloadFile(excelFileName, "게시글 목록.xlsx");
	}
}
