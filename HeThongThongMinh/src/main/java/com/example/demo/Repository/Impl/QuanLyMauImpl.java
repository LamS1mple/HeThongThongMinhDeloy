package com.example.demo.Repository.Impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.example.demo.Entity.Mau1;
import com.example.demo.Entity.Mau2;
import com.example.demo.Entity.Model2;
import com.example.demo.Entity.Nhan;
import com.example.demo.Entity.Nhan1;

public interface QuanLyMauImpl {

	public List<Nhan> getAllNhanByTen(String a);
	public List<Mau2> getAllMauByTen(int id , String name);
	public void addImgMau2(int id ,Mau2 mau2);
	public void updateImgMau2(int id ,Mau2 mau2);
	public void deleteMau2(int id);
	//Detection
	public List<Mau1> getAllMau1(String name) throws IOException;
	public void addMau1(Mau1 mau1);
	public void deleteMau1(int id);
	public void deleteNhan1(int id);
	public void updateMau1(Mau1 mau1, int id);
	//Model
	public List<Model2> getAllModel( ) throws IOException;
	public void deleteModel(String name);
	public void updateModel();

	}
