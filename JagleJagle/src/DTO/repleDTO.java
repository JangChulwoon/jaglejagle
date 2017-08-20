package DTO;

public class repleDTO {
	private int idx;
	private int Board_idx, Board_Board_Pool_idx, Board_User_idx;
	private String contents, registration_date, revise_station, delete_station, delete_Date, nickname;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoard_idx() {
		return Board_idx;
	}
	public void setBoard_idx(int board_idx) {
		Board_idx = board_idx;
	}
	public int getBoard_Board_Pool_idx() {
		return Board_Board_Pool_idx;
	}
	public void setBoard_Board_Pool_idx(int board_Board_Pool_idx) {
		Board_Board_Pool_idx = board_Board_Pool_idx;
	}
	public int getBoard_User_idx() {
		return Board_User_idx;
	}
	public void setBoard_User_idx(int board_User_idx) {
		Board_User_idx = board_User_idx;
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
	public String getRevise_station() {
		return revise_station;
	}
	public void setRevise_station(String revise_station) {
		this.revise_station = revise_station;
	}
	public String getDelete_station() {
		return delete_station;
	}
	public void setDelete_station(String delete_station) {
		this.delete_station = delete_station;
	}
	public String getDelete_Date() {
		return delete_Date;
	}
	public void setDelete_Date(String delete_Date) {
		this.delete_Date = delete_Date;
	}
	
}
