package com.example.demo.WebSoket;

import java.util.List;

import com.example.demo.Entity.Model2;

public class Dem {
	private List<Model2> aList;
	private float so;
	public List<Model2> getaList() {
		return aList;
	}
	public void setaList(List<Model2> aList) {
		this.aList = aList;
	}
	public float getSo() {
		return so;
	}
	public void setSo(float so) {
		this.so = so;
	}
	public Dem(List<Model2> aList, float so) {
		super();
		this.aList = aList;
		this.so = so;
	}
	
}
