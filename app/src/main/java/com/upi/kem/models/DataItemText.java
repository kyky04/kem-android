package com.upi.kem.models;

import com.google.gson.annotations.SerializedName;

public class DataItemText {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("jumlah_kata")
	private int jumlahKata;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_kelas")
	private int idKelas;

	@SerializedName("id")
	private int id;

	@SerializedName("judul")
	private String judul;

	@SerializedName("deleted_at")
	private String deletedAt;

	@SerializedName("pertanyaan")
	private String pertanyaan;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setJumlahKata(int jumlahKata){
		this.jumlahKata = jumlahKata;
	}

	public int getJumlahKata(){
		return jumlahKata;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setIdKelas(int idKelas){
		this.idKelas = idKelas;
	}

	public int getIdKelas(){
		return idKelas;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setDeletedAt(String deletedAt){
		this.deletedAt = deletedAt;
	}

	public String getDeletedAt(){
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
			"DataItemText{" +
			"updated_at = '" + updatedAt + '\'' + 
			",jumlah_kata = '" + jumlahKata + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_kelas = '" + idKelas + '\'' + 
			",id = '" + id + '\'' + 
			",judul = '" + judul + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",pertanyaan = '" + pertanyaan + '\'' + 
			"}";
		}
}