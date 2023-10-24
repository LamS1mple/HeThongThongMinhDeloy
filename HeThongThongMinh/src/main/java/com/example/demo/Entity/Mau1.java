package com.example.demo.Entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Mau1 {
	private int idMau1;
	private String pathMau1;
	private Date ngayThem;
	
	
	private List<Nhan1> listNhan1;
	private byte[] imgMau1;



	public Mau1(int idMau1, String pathMau1, Date ngayThem) {
		super();
		this.idMau1 = idMau1;
		this.pathMau1 = pathMau1;
		this.ngayThem = ngayThem;
	}



	public Mau1(String pathMau1, Date ngayThem) {
		super();
		this.pathMau1 = pathMau1;
		this.ngayThem = ngayThem;
	}



	public int getIdMau1() {
		return idMau1;
	}



	public void setIdMau1(int idMau1) {
		this.idMau1 = idMau1;
	}



	public String getPathMau1() {
		return pathMau1;
	}



	public void setPathMau1(String pathMau1) {
		this.pathMau1 = pathMau1;
	}



	public Date getNgayThem() {
		return ngayThem;
	}



	public void setNgayThem(Date ngayThem) {
		this.ngayThem = ngayThem;
	}



	public List<Nhan1> getListNhan1() {
		return listNhan1;
	}



	public void setLisNhan1(List<Nhan1> lisNhan1) {
		this.listNhan1 = lisNhan1;
	}



	public byte[] getImgMau1() {
		return imgMau1;
	}



	public void setImgMau1(byte[] imgMau1) {
		this.imgMau1 = imgMau1;
	}



	@Override
	public String toString() {
		return "Mau1 [idMau1=" + idMau1 + ", pathMau1=" + pathMau1 + ", ngayThem=" + ngayThem + ", lisNhan1=" + listNhan1
				+ ", imgMau1=" + Arrays.toString(imgMau1) + "]";
	}
	
	
	
	
}
