package bbs;

public class BoardDTO {
	private int listNum, idx;
	private String title, contents, registration_date, group_time, group_date, group_location, introduce_comment, nickname;
	private String file_path;
	private int view_count, recommend, limit_count, user_idx, board_pool_idx;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}
	public String getGroup_time() {
		return group_time;
	}
	public void setGroup_time(String group_time) {
		this.group_time = group_time;
	}
	public String getGroup_date() {
		return group_date;
	}
	public void setGroup_date(String group_date) {
		this.group_date = group_date;
	}
	public String getGroup_location() {
		return group_location;
	}
	public void setGroup_location(String group_location) {
		this.group_location = group_location;
	}
	public String getIntroduce_comment() {
		return introduce_comment;
	}
	public void setIntroduce_comment(String introduce_comment) {
		this.introduce_comment = introduce_comment;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getLimit_count() {
		return limit_count;
	}
	public void setLimit_count(int limit_count) {
		this.limit_count = limit_count;
	}
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public int getBoard_pool_idx() {
		return board_pool_idx;
	}
	public void setBoard_pool_idx(int board_pool_idx) {
		this.board_pool_idx = board_pool_idx;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
	
	
	// getter / setter
}