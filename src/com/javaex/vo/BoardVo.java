package com.javaex.vo;

public class BoardVo {

	// 필드
	int no;
	String title;
	String content;
	int hit;
	String regDate;
	int userNo;

	// 생성자
	public BoardVo() {

	}

	
	
	public BoardVo(String name,  int hit, String regDate, String title, String content) {
		super();
		this.title = name;
		this.hit = hit;
		this.regDate = regDate;
		this.title = title;
		this.content = content;
	}



	public BoardVo(int no, String title, String name, int hit, String regDate) {
		super();
		this.no = no;
		this.title = title;
		this.content = name;
		this.hit = hit;
		this.regDate = regDate;
	}

	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
	}

	// 메소드 g.s
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	// 메소드일반
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", hit=" + hit + ", userNo=" + userNo + ", title=" + title + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}

}
