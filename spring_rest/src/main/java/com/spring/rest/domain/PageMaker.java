package com.spring.rest.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	//화면 하단에 보여줄 페이지 수
	private int displayPageNum=10;
	//현재 페이지와 현재 페이지에 보여줄 목록 수
	//객체 선언
	private Criteria cri;

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	private void calcData() {
		//total값에 따라서 endPage startPage 계산
		endPage=(int)(Math.ceil(cri.getPage()/(double)displayPageNum) * displayPageNum);
		startPage=(endPage-displayPageNum)+1;
		//endpag계산
		int tempEndPage=(int)(Math.ceil( totalCount/(double)cri.getPerPageNum() ));
		if(endPage>tempEndPage)
			endPage=tempEndPage;
		//이전 다음버튼
		prev=(startPage==1)?false:true;
		next=endPage*cri.getPerPageNum()>=totalCount?false:true;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public Criteria getCri() {
		return cri;
	}
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	public String makeQuery(int page) {
		UriComponents uricom=UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		return uricom.toUriString();
	}
	/*public String makeSearch(int page) {
		UriComponents uricom=UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
				.queryParam("keyword", ((SearchCriteria)cri).getKeyword())
				.build();
		return uricom.toUriString();
	}
*/
}
