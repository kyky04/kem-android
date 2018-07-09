package com.upi.kem.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KelasResponse{

	@SerializedName("data")
	private List<DataItemKelas> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItemKelas> data){
		this.data = data;
	}

	public List<DataItemKelas> getData(){
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
			"KelasResponse{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}