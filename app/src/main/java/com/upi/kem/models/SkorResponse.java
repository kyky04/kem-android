package com.upi.kem.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SkorResponse{

	@SerializedName("data")
	private List<DataItemSkor> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItemSkor> data){
		this.data = data;
	}

	public List<DataItemSkor> getData(){
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
			"SkorResponse{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}