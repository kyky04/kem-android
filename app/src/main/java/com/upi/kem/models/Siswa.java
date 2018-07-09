package com.upi.kem.models;

import com.google.gson.annotations.SerializedName;

public class Siswa{

	@SerializedName("sekolah")
	private String sekolah;

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_kelas")
	private int idKelas;

	@SerializedName("id")
	private int id;

	@SerializedName("jenis_kelamin")
	private int jenisKelamin;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setSekolah(String sekolah){
		this.sekolah = sekolah;
	}

	public String getSekolah(){
		return sekolah;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public void setJenisKelamin(int jenisKelamin){
		this.jenisKelamin = jenisKelamin;
	}

	public int getJenisKelamin(){
		return jenisKelamin;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"Siswa{" + 
			"sekolah = '" + sekolah + '\'' + 
			",password = '" + password + '\'' + 
			",nama = '" + nama + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_kelas = '" + idKelas + '\'' + 
			",id = '" + id + '\'' + 
			",jenis_kelamin = '" + jenisKelamin + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}