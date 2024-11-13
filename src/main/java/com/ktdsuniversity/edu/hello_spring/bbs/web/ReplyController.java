package com.ktdsuniversity.edu.hello_spring.bbs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.hello_spring.bbs.service.ReplyService;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.DeleteReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ModifyReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.hello_spring.bbs.vo.WriteReplyVO;
import com.ktdsuniversity.edu.hello_spring.common.utils.ErrorMapUtil;
import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

import jakarta.validation.Valid;

@RestController
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/board/reply/{boardId}")
	public Map<String, Object> selectAllReplies(@PathVariable int boardId) {
		List<ReplyVO> replyList = replyService.selectAllReplies(boardId);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("count", replyList.size());
		resultMap.put("replies", replyList);
		
		return resultMap;
	}
	
	@PostMapping("/board/reply/{boardId}")
	public Map<String, Object> doInsertNewReplies(@PathVariable int boardId, @Valid WriteReplyVO writeReplyVO, BindingResult bindingResult, @SessionAttribute("_LOGIN_USER") MemberVO memberVO) {
		
		if(bindingResult.hasErrors()) {
			return ErrorMapUtil.getErrorMap(bindingResult);
		}
		
		writeReplyVO.setBoardId(boardId);
		writeReplyVO.setEmail(memberVO.getEmail());
		
		boolean isSuccess = replyService.insertNewReply(writeReplyVO);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", isSuccess);
		return resultMap;
	}
	
	@GetMapping("/board/reply/delete/{replyId}")
	public Map<String, Object> doDeleteReplies(@PathVariable int replyId, DeleteReplyVO deleteReplyVO, @SessionAttribute("_LOGIN_USER") MemberVO memberVO) {
		
		deleteReplyVO.setReplyId(replyId);
		deleteReplyVO.setEmail(memberVO.getEmail());
		
		boolean isSuccess = replyService.deleteOneReply(deleteReplyVO);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", isSuccess);
		return resultMap;
	}
	
	@PostMapping("/board/reply/modify/{replyId}")
	public Map<String, Object> doUpdateReplies(@PathVariable int replyId, ModifyReplyVO modifyReplyVO, @SessionAttribute("_LOGIN_USER") MemberVO memberVO) {
		
		modifyReplyVO.setReplyId(replyId);
		modifyReplyVO.setEmail(memberVO.getEmail());
		
		boolean isSuccess = replyService.updateOneReply(modifyReplyVO);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", isSuccess);
		return resultMap;
	}
	
	@GetMapping("/board/reply/recommend/{replyId}")
	public Map<String, Object> doRecommendReplies(@PathVariable int replyId, @SessionAttribute("_LOGIN_USER") MemberVO memberVO) {
		
		boolean isSuccess = replyService.recommendOneReply(replyId, memberVO.getEmail());
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", isSuccess);
		return resultMap;
	}
}
