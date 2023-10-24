package com.example.demo.Controller.QuanLyMauController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Mau1;
import com.example.demo.Entity.Mau2;
import com.example.demo.Entity.Nhan;
import com.example.demo.Entity.Nhan1;
import com.example.demo.Repository.QuanLyMauRepository;

@CrossOrigin(origins = "*")
@RestController
public class QuanLyMauHome {
	
//	private String UPLOAD_DIRECTORY =  System.getProperty("user.dir") + "/src/main/resources/static/img/";
	private String UPLOAD_DIRECTORY =  "D:\\IMG_HTTM\\Re\\";
	private String UPLOAD_DECTION =  "D:\\IMG_HTTM\\De\\";

	@Autowired(required = true)
	QuanLyMauRepository quanLyMauRepository;

	
	@GetMapping(value = "/quanlymau/{ten}")
	public List<Nhan> searchNhan(@PathVariable("ten") String ten) {
       List<Nhan> nhans =  quanLyMauRepository.getAllNhanByTen(ten);
       for (Nhan nhan : nhans) {
    	   System.out.println(nhan);
       }
		return nhans;
		
	}
	@GetMapping(value = "/quanlymau/get-img/{id}/{ten}")
	public List<Mau2> searchMau2(@PathVariable("id") int id , @PathVariable("ten")String ten) throws IOException{
		List<Mau2> mau2s = quanLyMauRepository.getAllMauByTen(id, ten);
		
		for (int i = 0 ; i < mau2s.size() ; i ++) {
			try {
				FileInputStream inputStream = new FileInputStream(new File("D:/IMG_HTTM/Re/" + mau2s.get(i).getPathMau2()));	
				mau2s.get(i).setImgMau2( inputStream.readAllBytes());
				System.out.println(i + "da doc xong");
				inputStream.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		return mau2s;
	}
	
	@PostMapping(value = "/quanlymau/post-img")
	public String addImgMau2(@RequestParam("img") MultipartFile imgFile,@RequestParam("ten-path") String path,
			@RequestParam("id") int id ) throws IOException{
		String fileNameString = new String();
		
		Mau2 mau2 = new Mau2(path , new Date());
		quanLyMauRepository.addImgMau2(id, mau2);
		Path filePath = Paths.get(UPLOAD_DIRECTORY, path);
		System.out.println(filePath);
		Files.write(filePath, imgFile.getBytes());
	
		
		return "Thanh Cong";
	}
	@PostMapping(value = "/quanlymau/sua-khong-img")
	public String suaImg(@RequestBody String s) throws JSONException, IOException {
		JSONObject jsonObject = new JSONObject(s);
		System.out.println();
		int id = jsonObject.getInt("id");
		String pathCu = jsonObject.getString("pathCu");
		Path oldFile = Paths.get(UPLOAD_DIRECTORY, pathCu);
		String pathMoi = jsonObject.getString("pathMoi");
		Path newFile = Paths.get(UPLOAD_DIRECTORY, pathMoi);
		
		try {
			System.out.println(0);
			Files.move(oldFile, newFile);
		
//			Files.delete(oldFile);
			System.out.println(1);
			Mau2 mau2 = new Mau2(pathMoi, new Date());
			System.out.println(2);

			quanLyMauRepository.updateImgMau2(id, mau2);
		} catch (Exception e) {
			System.out.println(e);
			return "thai bai";

		}
		
		return "thanh cong";
	}
	@PostMapping("/quanlymau/sua-co-img")
	public String suaImg(@RequestParam("img")MultipartFile imgFile,
			@RequestParam("path-cu") String pathCu,
			@RequestParam("path-moi") String pathMoi,
			@RequestParam("id") int id) throws IOException {
		Mau2 mau2 = new Mau2(pathMoi, new Date());
		quanLyMauRepository.updateImgMau2(id, mau2);
		Path path = Paths.get(UPLOAD_DIRECTORY,  pathMoi);
		Path path2 = Paths.get(UPLOAD_DIRECTORY,  pathCu);

		Files.write(path, imgFile.getBytes());
		if (!pathCu.equals(pathMoi)) {
			
			Files.delete(path2);
		}
		return "thanh cong";
	}
	@GetMapping(value = "/quanlymau/delete-{path}/{id}")
	public void xoaImg(@PathVariable("id") int id, @PathVariable("path")String paString) throws IOException {
		Path aPath = Paths.get(UPLOAD_DIRECTORY, paString);
		Files.delete(aPath);
		quanLyMauRepository.deleteMau2(id);
	}
	
	// FACE DETECTION
	
	@GetMapping(value = "/quanlymau/face-detection/{ten}")
	public List<Mau1> getMau1( @PathVariable("ten")String ten) throws IOException {
		List<Mau1> mau1 = quanLyMauRepository.getAllMau1(ten);
		
		return mau1;
	}
	
	@PostMapping(value = "/quanlymau/face-detection")
	public void addMau1(@RequestParam("img")MultipartFile img1,
			@RequestParam("path")String path, @RequestParam("viTri")String viTri) throws IOException {
		Path a = Paths.get(UPLOAD_DECTION , path);
		JSONObject jsonObject = new JSONObject(viTri);
		System.out.println(jsonObject.getJSONArray("x1") );
		
		JSONArray x1 = jsonObject.getJSONArray("x1");
		JSONArray x2 = jsonObject.getJSONArray("x2");
		JSONArray y1 = jsonObject.getJSONArray("y1");
		JSONArray y2 = jsonObject.getJSONArray("y2");

		Mau1 mau1 = new Mau1(path, new Date());
		List<Nhan1> nhan1s = new ArrayList<Nhan1>();
		for (int i = 0 ; i < x1.length(); i ++) {
			Nhan1 nhan1 = new Nhan1(x1.getInt(i), y1.getInt(i), x2.getInt(i), y2.getInt(i) );
			nhan1s.add(nhan1);
		}
		mau1.setLisNhan1(nhan1s);
		Files.write(a, img1.getBytes());
		quanLyMauRepository.addMau1(mau1);
		
		
	}
	@PostMapping(value = "/quanlymau/face-detection/delete")
	public void deleteMau1(@RequestParam("id")int id , @RequestParam("path")String path ) throws IOException{
		
		quanLyMauRepository.deleteMau1(id);
		Path aPath = Paths.get(UPLOAD_DECTION, path);
		Files.delete(aPath);
	}
	
	@PostMapping(value = "/quanlymau/face-detection/sua-co-anh")
	public Mau1 suaMau1 (@RequestParam("img")MultipartFile img ,
			@RequestParam("path")String path,
			@RequestParam("viTri")String viTri,
			@RequestParam("id")int id,
			@RequestParam("pathCu")String pathCu) throws IOException {
		
		Path a = Paths.get(UPLOAD_DECTION , path);
		Path paCuPath = Paths.get(UPLOAD_DECTION, pathCu);
		JSONObject jsonObject = new JSONObject(viTri);
		System.out.println(path + " " + pathCu);
		JSONArray x1 = jsonObject.getJSONArray("x1");
		JSONArray x2 = jsonObject.getJSONArray("x2");
		JSONArray y1 = jsonObject.getJSONArray("y1");
		JSONArray y2 = jsonObject.getJSONArray("y2");

		Mau1 mau1 = new Mau1(path, new Date());
		List<Nhan1> nhan1s = new ArrayList<Nhan1>();
		for (int i = 0 ; i < x1.length(); i ++) {
			Nhan1 nhan1 = new Nhan1(x1.getInt(i), y1.getInt(i), x2.getInt(i), y2.getInt(i) );
			nhan1s.add(nhan1);
		}
		mau1.setLisNhan1(nhan1s);
		
		quanLyMauRepository.updateMau1(mau1, id);
		
		Files.write(a, img.getBytes());
		if (!path.equals(pathCu)) {
			
			Files.delete(paCuPath);
		}
		
		List<Mau1> mau1s = quanLyMauRepository.getAllMau1(path);
		System.out.println(mau1s.get(0));
		return mau1s.get(0);
	}
	
	
	@PostMapping(value = "/quanlymau/face-detection/sua-khong-anh")
	public Mau1 suaMau1 (@RequestParam("path")String path,
			@RequestParam("viTri")String viTri,
			@RequestParam("id")int id,
			@RequestParam("pathCu")String pathCu) throws IOException {
		
		Path a = Paths.get(UPLOAD_DECTION , path);
		Path paCuPath = Paths.get(UPLOAD_DECTION, pathCu);
		JSONObject jsonObject = new JSONObject(viTri);
		System.out.println(path + " " + pathCu);
		JSONArray x1 = jsonObject.getJSONArray("x1");
		JSONArray x2 = jsonObject.getJSONArray("x2");
		JSONArray y1 = jsonObject.getJSONArray("y1");
		JSONArray y2 = jsonObject.getJSONArray("y2");

		Mau1 mau1 = new Mau1(path, new Date());
		List<Nhan1> nhan1s = new ArrayList<Nhan1>();
		for (int i = 0 ; i < x1.length(); i ++) {
			Nhan1 nhan1 = new Nhan1(x1.getInt(i), y1.getInt(i), x2.getInt(i), y2.getInt(i) );
			nhan1s.add(nhan1);
		}
		mau1.setLisNhan1(nhan1s);
		
		quanLyMauRepository.updateMau1(mau1, id);
		
		Files.move(paCuPath, a);
		
		List<Mau1> mau1s = quanLyMauRepository.getAllMau1(path);
		System.out.println(mau1s.get(0));
		return mau1s.get(0);
	}
	
	
}
