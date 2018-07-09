package com.upi.kem.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItemSoal {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_soal")
	private int idSoal;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("jawaban")
	private List<JawabanItem> jawaban;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("pertanyaan")
	private String pertanyaan;

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

	public void setJawaban(List<JawabanItem> jawaban){
		this.jawaban = jawaban;
	}

	public List<JawabanItem> getJawaban(){
		return jawaban;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public void setPertanyaan(String pertanyaan){
		this.pertanyaan = pertanyaan;
	}

	public String getPertanyaan(){
		return pertanyaan;
	}

	@Override
 	public String toString(){
		return 
			"DataItemSoal{" +
			"updated_at = '" + updatedAt + '\'' + 
			",id_soal = '" + idSoal + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",jawaban = '" + jawaban + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",pertanyaan = '" + pertanyaan + '\'' + 
			"}";
		}
}