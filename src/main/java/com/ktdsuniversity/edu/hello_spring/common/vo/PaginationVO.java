package com.ktdsuniversity.edu.hello_spring.common.vo;

public class PaginationVO {

	/**
	 * 조회할 페이지의 번호
	 */
	private int pageNo;
	
	/**
	 * 한 페이지에 노출시킬 게시글의 개수
	 */
	private int listSize;
	
	/**
	 * 생성될 페이지의 총 개수
	 * 올림처리(게시글의 총 개수 / 한 페이지에 노출시킬 게시글의 개수)
	 */
	private int pageCount;
	
	/************페이지네이션 그룹 속성****************/
	
	/**
	 * 한 페이지 그룹에 보여줄 페이지 번호의 개수
	 */
	private int pageCountInGroup;
	
	/**
	 * 총 페이지 그룹의 개수
	 * 올림처리(총 페이지의 수 / 한 페이지 그룹에 보여줄 페이지 번호의 개수)
	 */
	private int groupCount;
	
	/**
	 * 현재 보여주고 있는 페이지 번호의 페이지 그룹 번호
	 * 페이지 번호 / 한 페이지 그룹에 보여줄 페이지 번호의 개수
	 */
	private int groupNo;
	
	/**
	 * 현재 페이지 그룹의 시작 페이지 번호
	 * 현재 그룹 번호 * 한 페이지 그룹에 보여줄 페이지 번호의 개수
	 */
	private int groupStartPageNo;
	
	/**
	 * 현재 페이지 그룹의 끝 페이지 번호
	 * (현재 그룹 번호 + 1) * 한 페이지 그룹에 보여줄 페이지 번호의 개수 - 1
	 */
	private int groupEndPageNo;
	
	/**
	 * 다음 그룹 존재 여부
	 * (현재 그룹 번호 + 1) < 페이지 그룹의 개수 ==> 다음 그룹이 존재함
	 */
	private boolean hasNextGroup;
	
	/**
	 * 이전 그룹 존재 여부
	 * 현재 그룹 번호 > 0 ==> 이전 그룹이 존재함
	 */
	private boolean hasPrevGroup;
	
	/**
	 * 다음 페이지 그룹의 시작 페이지 번호
	 * 현재 페이지 그룹의 마지막 페이지 번호 + 1
	 */
	private int nextGroupStartPageNo;
	
	/**
	 * 이전 페이지 그룹의 시작 페이지 번호
	 * 현재 페이지 그룹의 시작 페이지 번호 - 한 페이지 그룹에 보여줄 페이지 번호의 개수
	 */
	private int prevGroupStartPageNo;
	
	public PaginationVO() {
		// 기본으로 노출시킬 개시글의 개수를 10개로 설정
		this.listSize = 10;
		// 기본으로 노출시킬 페이지 그룹의 페이지 번호 개수를 10개로 설정
		this.pageCountInGroup = 10;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 올림처리(게시글의 총 개수 / 한 페이지에 노출시킬 게시글의 개수)
	 * @param listCount : 게시글의 총 개수
	 */
	public void setPageCount(int listCount) {
		// 총 페이지 개수 계산하기
		this.pageCount = (int) Math.ceil( (double) listCount / this.listSize );
		
		// 페이지 그룹 관련 계산하기
		this.groupCount = (int) Math.ceil( (double) this.pageCount / this.pageCountInGroup);
		this.groupNo = this.pageNo / this.pageCountInGroup;
		this.groupStartPageNo = this.groupNo * this.pageCountInGroup;
		this.groupEndPageNo = (this.groupNo + 1) * this.pageCountInGroup - 1;
		
		/*
		 * 현재 그룹이 마지막 그룹일 경우
		 * 계산된 현재 그룹의 마지막 페이지 번호와 실제 마지막 페이지 번호가 다를 수 있다
		 */
		if(this.groupEndPageNo > this.pageCount) {
			this.groupEndPageNo = this.pageCount - 1;
		}
		
		this.hasNextGroup = (this.groupNo + 1) < this.groupCount;
		this.hasPrevGroup = this.groupNo > 0;
		this.nextGroupStartPageNo = this.groupEndPageNo + 1;
		this.prevGroupStartPageNo = this.groupStartPageNo - this.pageCountInGroup;
	}

	public int getPageCountInGroup() {
		return pageCountInGroup;
	}

	public void setPageCountInGroup(int pageCountInGroup) {
		this.pageCountInGroup = pageCountInGroup;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getGroupStartPageNo() {
		return groupStartPageNo;
	}

	public void setGroupStartPageNo(int groupStartPageNo) {
		this.groupStartPageNo = groupStartPageNo;
	}

	public int getGroupEndPageNo() {
		return groupEndPageNo;
	}

	public void setGroupEndPageNo(int groupEndPageNo) {
		this.groupEndPageNo = groupEndPageNo;
	}

	public boolean isHasNextGroup() {
		return hasNextGroup;
	}

	public void setHasNextGroup(boolean hasNextGroup) {
		this.hasNextGroup = hasNextGroup;
	}

	public boolean isHasPrevGroup() {
		return hasPrevGroup;
	}

	public void setHasPrevGroup(boolean hasPrevGroup) {
		this.hasPrevGroup = hasPrevGroup;
	}

	public int getNextGroupStartPageNo() {
		return nextGroupStartPageNo;
	}

	public void setNextGroupStartPageNo(int nextGroupStartPageNo) {
		this.nextGroupStartPageNo = nextGroupStartPageNo;
	}

	public int getPrevGroupStartPageNo() {
		return prevGroupStartPageNo;
	}

	public void setPrevGroupStartPageNo(int prevGroupStartPageNo) {
		this.prevGroupStartPageNo = prevGroupStartPageNo;
	}
}
