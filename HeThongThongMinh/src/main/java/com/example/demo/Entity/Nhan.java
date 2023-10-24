package com.example.demo.Entity;

public class Nhan {

	private long id;
	private String nhan;
	public Nhan(String nhan) {
		super();
		this.nhan = nhan;
	}
	public Nhan(long id, String nhan) {
		this.id = id;
		this.nhan = nhan;
	}
	public String getNhan() {
		return nhan;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Nhan [id=" + id + ", nhan=" + nhan + "]";
	}
	
}
