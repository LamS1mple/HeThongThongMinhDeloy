package com.example.demo.Entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class Mau2 {
	private int idMau2;
	private String pathMau2;
	private Date ngayThem;
	private boolean isTrain;
	
	
	private byte[] imgMau2;
	public Mau2() {
		// TODO Auto-generated constructor stub
	}
	public int getIdMau2() {
		return idMau2;
	}
	public void setIdMau2(int idMau2) {
		this.idMau2 = idMau2;
	}
	public String getPathMau2() {
		return pathMau2;
	}
	public void setPathMau2(String pathMau2) {
		this.pathMau2 = pathMau2;
	}
	public Date getNgayThem() {
		return ngayThem;
	}
	public void setNgayThem(Date ngayThem) {
		this.ngayThem = ngayThem;
	}
	
	
	public byte[] getImgMau2() {
		return imgMau2;
	}
	public void setImgMau2(byte[] imgMau2) {
		this.imgMau2 = imgMau2;
	}
	public Mau2(int idMau2, String pathMau2, Date ngayThem) {
		super();
		this.idMau2 = idMau2;
		this.pathMau2 = pathMau2;
		this.ngayThem = ngayThem;
	}
	
	
	public Mau2(String pathMau2 , Date ngayThem) {
		super();
		this.pathMau2 = pathMau2;
		this.ngayThem = ngayThem;

	}
	
	@Override
	public String toString() {
		return "Mau2 [idMau2=" + idMau2 + ", pathMau2=" + pathMau2 + ", ngayThem=" + ngayThem + ", imgMau2="
				+ Arrays.toString(imgMau2) + "]";
	}
	
	
}
