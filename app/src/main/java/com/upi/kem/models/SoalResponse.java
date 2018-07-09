package com.upi.kem.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SoalResponse{

	@SerializedName("data")
	private List<DataItemSoal> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItemSoal> data){
		this.data = data;
	}

	public List<DataItemSoal> getData(){
		return data;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"SoalResponse{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}