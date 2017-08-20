package DTO;

public class BoardDTO {
	private int idx, User_idx, Board_Pool_idx, recommand = 0, price = 0, view_count = 0;
	private String contents,registration_date, delete_station, delete_date, title, nickname;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getUser_idx() {
		return User_idx;
	}
	public void setUser_idx(int User_idx) {
		this.User_idx = User_idx;
	}
	public int getBoard_Pool_idx() {
		return Board_Pool_idx;
	}
	public void setBoard_Pool_idx(int Board_Pool_idx) {
		this.Board_Pool_idx = Board_Pool_idx;
	}
	public int getRecommand() {
		return recommand;
	}
	public void setRecommand(int recommand) {
		this.recommand = recommand;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public String getRegistration_date() {
		return registration_date;
	}
	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}
	public String getDelete_station() {
		return delete_station;
	}
	public void setDelete_station(String delete_station) {
		this.delete_station = delete_station;
	}
	public String getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}