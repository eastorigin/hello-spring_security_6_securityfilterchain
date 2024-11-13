package com.ktdsuniversity.edu.hello_spring.bbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.hello_spring.bbs.dao.BoardDao;
import com.ktdsuniversity.edu.hello_spring.bbs.service.BoardService;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.SearchBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteBoardVO;
import com.ktdsuniversity.edu.hello_spring.common.beans.FileHandler;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.PageNotFoundException;
import com.ktdsuniversity.edu.hello_spring.common.vo.StoreResultVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	// application.yml 파일에서 "app.multipart.base-dir" 설정 값을 가져온다
	// @Value는 Spring Bean에서만 사용이 가능
	// Spring Bean: @Controller, @Service, Repository
	//	@Value("${app.multipart.base-dir}")
	//	private String baseDirectory;

	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public BoardListVO getAllBoard(SearchBoardVO searchBoardVO) {
		// 게시글 목록 화면에 데이터를 전송해주기 위해서 게시글의 건수와 게시글의 목록을 조회해 반환시킨다
		
		// 1. 게시글의 건수를 조회한다.
		int boardCount = this.boardDao.selectBoardAllCount(searchBoardVO);
		if (boardCount == 0) {
			// 3. BoardListVO를 만들어서 게시글의 건수와 목록을 할당한다.
			BoardListVO boardListVO = new BoardListVO();
			boardListVO.setBoardCnt(0);
			boardListVO.setBoardList(new ArrayList<>());
			
			// 4. BoardListVO 인스턴스를 반환한다.
			return boardListVO;
		}
		
		// 2. 게시글의 목록을 조회한다.
		List<BoardVO> boardList = null;
		// 엑셀 다운로드를 위한 게시글 조회
		if(searchBoardVO == null) {
			boardList = this.boardDao.selectAllBoard();
		}
		// 페이지네이션을 위한 게시글 조회
		else {
			boardList = this.boardDao.selectAllBoard(searchBoardVO);
			// 총 페이지 개수를 구한다
			searchBoardVO.setPageCount(boardCount);
		}
		
		// 3. BoardListVO를 만들어서 게시글의 건수와 목록을 할당한다.
		BoardListVO boardListVO = new BoardListVO();
		boardListVO.setBoardCnt(boardCount);
		boardListVO.setBoardList(boardList);
		
		// 4. BoardListVO 인스턴스를 반환한다.
		return boardListVO;
	}
	
	@Transactional // Service 코드가 동작하는 중에 예외가 발생하면, Rollback 해준다. 예외가 발생하지 않으면 Commit 해준다.
	@Override
	public boolean createNewBoard(WriteBoardVO writeBoardVO) {
		
		// 파일 업로드 처리
		MultipartFile file = writeBoardVO.getFile();
		
		StoreResultVO storeResultVO = this.fileHandler.storeFile(file);
		if(storeResultVO != null) {
			writeBoardVO.setFileName(storeResultVO.getObfuscatedFileName());
			writeBoardVO.setOriginFileName(storeResultVO.getOriginFilename());
		}
		
		int numberCreation = this.boardDao.inserNewBoard(writeBoardVO);
		
		// 강제로 예외 발생시키기 : transaction 적용여부를 알기 위해
		// @Transactional이 적용되어 있지 않았을 때, Rollback이 되지 않아야 한다
		// Integer.parseInt("safsafasffsa");
		
		return numberCreation > 0;
	}

	@Transactional // update, insert, delete가 있으면 해준다
	@Override
	public BoardVO selectOneBoard(int id, boolean isIncrease) {
		if(isIncrease) {
			int updateCount = boardDao.updateViewCount(id);
			if(updateCount == 0) {
				throw new PageNotFoundException("잘못된 정보입니다");
			}
		}
		BoardVO boardVO = boardDao.selectOneBoard(id);
		if(boardVO == null) {
			throw new PageNotFoundException("잘못된 접근입니다");
		}
		return boardVO;
	}
	
	@Transactional
	@Override
	public boolean updateOneBoard(ModifyBoardVO modifyBoardVO) {
		
		// 기존의 파일을 삭제하기 위해서 업데이트 하기 전 게시글의 정보를 조회한다
		BoardVO boardVO = this.boardDao.selectOneBoard(modifyBoardVO.getId());
		
		
		MultipartFile file = modifyBoardVO.getFile();
		
		StoreResultVO storeResultVO = this.fileHandler.storeFile(file);
		if(storeResultVO != null) {
			modifyBoardVO.setFileName(storeResultVO.getObfuscatedFileName());
			modifyBoardVO.setOriginFileName(storeResultVO.getOriginFilename());
		}
		
		int updateCount = boardDao.updateOneBoard(modifyBoardVO);
		
		if(updateCount > 0) {
			this.fileHandler.deleteFile(boardVO.getFileName());
		}
		
		return updateCount > 0;
	}
	
	@Transactional
	@Override
	public boolean deleteOneBoard(DeleteBoardVO deleteBoardVO) {
		// 기존의 파일을 삭제하기 위해서 업데이트 하기 전 게시글의 정보를 조회한다
		BoardVO boardVO = this.boardDao.selectOneBoard(deleteBoardVO.getId());
		
		int deleteCount = boardDao.deleteOneBoard(deleteBoardVO);
		
		if(deleteCount > 0) {
			this.fileHandler.deleteFile(boardVO.getFileName());
		}
		return deleteCount > 0;
	}
}
