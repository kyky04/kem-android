package com.upi.kem.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JawabanResponse {

	@SerializedName("data")
	private List<JawabanItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<JawabanItem> data){
		this.data = data;
	}

	public List<JawabanItem> getData(){
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
			"TextResponse{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}