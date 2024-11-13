package com.ktdsuniversity.edu.hello_spring.bbs.service;

import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.SearchBoardVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteBoardVO;

public interface BoardService {

	public BoardListVO getAllBoard(SearchBoardVO searchBoardVO);
	
	public boolean createNewBoard(WriteBoardVO writeBoardVO);
	
	public BoardVO selectOneBoard(int id, boolean isIncrease);
	
	public boolean updateOneBoard(ModifyBoardVO modifyBoardVO);
	
	public boolean deleteOneBoard(DeleteBoardVO deleteBoardVO);
}
