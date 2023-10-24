package com.example.demo.Entity;

import java.util.Date;

public class Model2 {
	private int id;
	private String path;
	private Date date;
	private float acc, pre, rec, f1_s ;
	boolean isTrain;
	
	
	
	public Model2(int id, String path, Date date, float acc, float pre, float rec, float f1_s, boolean isTrain) {
		super();
		this.id = id;
		this.path = path;
		this.date = date;
		this.acc = acc;
		this.pre = pre;
		this.rec = rec;
		this.f1_s = f1_s;
		this.isTrain = isTrain;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getAcc() {
		return acc;
	}
	public void setAcc(float acc) {
		this.acc = acc;
	}
	public float getPre() {
		return pre;
	}
	public void setPre(float pre) {
		this.pre = pre;
	}
	public float getRec() {
		return rec;
	}
	public void setRec(float rec) {
		this.rec = rec;
	}
	public float getF1_s() {
		return f1_s;
	}
	public void setF1_s(float f1_s) {
		this.f1_s = f1_s;
	}
	public boolean isTrain() {
		return isTrain;
	}
	public void setTrain(boolean isTrain) {
		this.isTrain = isTrain;
	}
	
	
}
