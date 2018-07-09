package com.upi.kem.models;

import com.google.gson.annotations.SerializedName;

public class DataItemSkor {

	@SerializedName("siswa")
	private Siswa siswa;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_soal")
	private int idSoal;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("skor")
	private int skor;

	@SerializedName("id_siswa")
	private int idSiswa;

	public void setSiswa(Siswa siswa){
		this.siswa = siswa;
	}

	public Siswa getSiswa(){
		return siswa;
	}

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

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public void setSkor(int skor){
		this.skor = skor;
	}

	public int getSkor(){
		return skor;
	}

	public void setIdSiswa(int idSiswa){
		this.idSiswa = idSiswa;
	}

	public int getIdSiswa(){
		return idSiswa;
	}

	@Override
 	public String toString(){
		return 
			"DataItemKelas{" +
			"siswa = '" + siswa + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",id_soal = '" + idSoal + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",skor = '" + skor + '\'' + 
			",id_siswa = '" + idSiswa + '\'' + 
			"}";
		}
}