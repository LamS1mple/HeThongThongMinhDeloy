package com.example.demo.WebSoket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.Entity.Model2;
import com.example.demo.Repository.QuanLyMauRepository;


@RestController
@CrossOrigin(origins = "*")
public class GreetingController {
	@Autowired
	private QuanLyMauRepository quanLyMauRepository;
	
private	String nhanAnhString;
private String guiAnhString = "";
 private Boolean reTrain = false;
 private boolean isTrain = false;
 
 // client
  @PostMapping(value = "/thu")
	  public Greeting greeting1(@RequestBody String message) throws Exception {
		  JSONObject aJsonObject = new JSONObject(message);
		  String aString = aJsonObject.getString("name");
		  try {
			
			  String[] bStrings = aString.split(",");
			  nhanAnhString = bStrings[1];
//			  Path aPath = Paths.get("D:\\TEST\\",   "123.jpg");
//			  System.out.println(aString);
//			  Files.write(aPath, Base64.decodeBase64(bStrings[1]));
		} catch (Exception e) {
			System.out.println(e);
		}
	    return new Greeting(guiAnhString, false,false );

	  }
  		// python get frame
	  @GetMapping(value = "/xuly")
	  public Greeting greeting2() throws Exception {
		Greeting a= new Greeting(nhanAnhString , reTrain, isTrain);
		reTrain = false;
		isTrain = false;
	    return a;
	  }
	  //python xu ly xong frame
	  @PostMapping(value = "/xuly")
	  public Greeting greeting3(@RequestBody String a) {
		  JSONObject b  = new JSONObject(a);
		  System.out.println(b.getString("name"));
		  guiAnhString = b.getString("name");
		  return new Greeting(guiAnhString, false, false);
	  }
	  
	  //get model
	  @GetMapping(value = "/model2")
	  public Dem getModel() throws IOException{
		  List<Model2> a1 = quanLyMauRepository.getAllModel();
		  int a = quanLyMauRepository.count(true);
		  int b = quanLyMauRepository.count(false);
		  float c = b / (float)(a + b);
		  Dem dem = new Dem(a1, c);
		  return dem;
	  }
	  // chon model
	  @GetMapping(value = "/model2/{idCu}/{idMoi}")
	  public void isTrain(@PathVariable("idMoi")int idMoi, @PathVariable("idCu")int idCu) throws IOException{
		  quanLyMauRepository.updateModel2(idMoi, true);
		  quanLyMauRepository.updateModel2(idCu, false);
		  System.out.println(idMoi + " " + idCu);
		  isTrain = true;
		  
	  }
	  @GetMapping(value = "/model2/retrain")
	  public void isTrain() throws IOException{
		  quanLyMauRepository.updateModel();
		  reTrain = true;
		  System.out.println("retrain");
	  }
	  @GetMapping(value = "/model2/delete{id}")
	  public void deleteModel(@PathVariable("id")String path) throws IOException{
		  Path aPath = Paths.get("C:\\Users\\s1mple\\Desktop\\Tester\\encodings\\", path);
		  Files.delete(aPath);
		  quanLyMauRepository.deleteModel(path);
	  }
}