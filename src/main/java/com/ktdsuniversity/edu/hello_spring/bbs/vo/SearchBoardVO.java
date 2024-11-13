package com.ktdsuniversity.edu.hello_spring.bbs.vo;

import com.ktdsuniversity.edu.hello_spring.common.vo.PaginationVO;

public class SearchBoardVO extends PaginationVO{

	private String searchType;
	private String searchKeyword;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
}
