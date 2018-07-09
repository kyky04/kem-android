package com.upi.kem.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("siswa")
	private Siswa siswa;

	@SerializedName("status")
	private String status;

	public void setSiswa(Siswa siswa){
		this.siswa = siswa;
	}

	public Siswa getSiswa(){
		return siswa;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"siswa = '" + siswa + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}