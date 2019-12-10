package com.emp.models;

public class Task {

	private int ID;
	private String Title;
	private String Deadline;
	private int ID_prog;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDeadline() {
		return Deadline;
	}
	public void setDeadline(String deadline) {
		Deadline = deadline;
	}
	public int getID_prog() {
		return ID_prog;
	}
	public void setID_prog(int iD_prog) {
		ID_prog = iD_prog;
	}
	
}
