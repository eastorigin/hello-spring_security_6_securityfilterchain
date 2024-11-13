package com.ktdsuniversity.edu.hello_spring.bbs.dao;

import java.util.List;

import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteReplyVO;

public interface ReplyDao {
	
	public String NAMESPACE = "com.ktdsuniversity.edu.hello_spring.bbs.dao.ReplyDao";

	/**
	 * 게시글의 모든 댓글 조회
	 * @param boardID 조회할 게시글의 번호
	 * @return 게시글에 등록된 모든 댓글 목록
	 */
	public List<ReplyVO> selectAllReplies(int boardID);
	
	/**
	 * 댓글 하나 조회
	 * @param replyId 조회할 댓글의 번호
	 * @return replyId에 해당하는 댓글 정보
	 */
	public ReplyVO selectOneReply(int replyId);
	
	/**
	 * 게시글 댓글 등록
	 * @param writeReplyVO 등록할 댓글의 정보
	 * @return 댓글을 등록한 개수
	 */
	public int insertNewReply(WriteReplyVO writeReplyVO);
	
	/**
	 * 댓글 하나 삭제
	 * @param deleteReplyVO 삭제할 댓글의 정보
	 * @return
	 */
	public int deleteOneReply(DeleteReplyVO deleteReplyVO);
	
	/**
	 * 댓글 내용 수정
	 * @param modifyReplyVO 수정할 댓글의 정보
	 * @return 수정된 댓글의 개수
	 */
	public int updateOneReply(ModifyReplyVO modifyReplyVO);
	
	/**
	 * 댓글 추천수 1 증가
	 * @param replyId 추천수를 1 증가시킬 댓글 번호
	 * @return 추천한 댓글의 개수
	 */
	public int recommendOneReply(int replyId);
}
