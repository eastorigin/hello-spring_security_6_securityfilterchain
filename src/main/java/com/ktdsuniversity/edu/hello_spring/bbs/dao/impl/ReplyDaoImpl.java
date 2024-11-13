package com.ktdsuniversity.edu.hello_spring.bbs.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.hello_spring.bbs.dao.ReplyDao;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteReplyVO;

@Repository
public class ReplyDaoImpl extends SqlSessionDaoSupport implements ReplyDao{

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<ReplyVO> selectAllReplies(int boardID) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllReplies", boardID);
	}
	
	@Override
	public ReplyVO selectOneReply(int replyId) {
		return this.getSqlSession().selectOne(NAMESPACE + ".selectOneReply", replyId);
	}
	
	@Override
	public int insertNewReply(WriteReplyVO writeReplyVO) {
		return this.getSqlSession().insert(NAMESPACE + ".insertNewReply", writeReplyVO);
	}
	
	@Override
	public int deleteOneReply(DeleteReplyVO deleteReplyVO) {
		return this.getSqlSession().delete(NAMESPACE + ".deleteOneReply", deleteReplyVO);
	}
	
	@Override
	public int updateOneReply(ModifyReplyVO modifyReplyVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateOneReply", modifyReplyVO);
	}
	
	@Override
	public int recommendOneReply(int replyId) {
		return this.getSqlSession().update(NAMESPACE + ".recommendOneReply", replyId);
	}
}
