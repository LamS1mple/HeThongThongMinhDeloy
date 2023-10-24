package com.example.demo.WebSoket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.codec.binary.Base64;

public class Greeting {

	  private String content;
	  private boolean reTrain ;

	  private boolean isTrain ;
	  public Greeting() {
	  }

	  public Greeting(String content,boolean reTrain, boolean isTrain) {
		super();
		this.content = content;
		this.reTrain = reTrain;
		this.isTrain = isTrain;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isReTrain() {
		return reTrain;
	}

	public void setReTrain(boolean reTrain) {
		this.reTrain = reTrain;
	}

	public boolean isisTrain() {
		return isTrain;
	}

	public void setTrain(boolean isTrain) {
		this.isTrain = isTrain;
	}
	  
	  
}
