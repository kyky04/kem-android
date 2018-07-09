package com.upi.kem.models;

import com.google.gson.annotations.SerializedName;

public class JawabanItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_soal")
	private int idSoal;

	@SerializedName("benar")
	private int benar;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("jawaban")
	private String jawaban;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIdSoal(int idSoal){
		this.idSoal = idSoal;
	}

	public int getIdSoal(){
		return idSoal;
	}

	public void setBenar(int benar){
		this.benar = benar;
	}

	public int getBenar(){
		return benar;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setJawaban(String jawaban){
		this.jawaban = jawaban;
	}

	public String getJawaban(){
		return jawaban;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	@Override
 	public String toString(){
		return 
			"JawabanItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",id_soal = '" + idSoal + '\'' + 
			",benar = '" + benar + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",jawaban = '" + jawaban + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			"}";
		}
}