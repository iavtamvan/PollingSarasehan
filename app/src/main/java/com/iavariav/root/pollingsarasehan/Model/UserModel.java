package com.iavariav.root.pollingsarasehan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

//	@SerializedName("npm")
	@Expose
	private String npm;
//	@SerializedName("nama")
	@Expose
	private String nama;
//	@SerializedName("kelas")
	@Expose
	private String kelas;

	@Expose
	private String img;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	public String getNpm() {
		return npm;
	}

	public void setNpm(String npm) {
		this.npm = npm;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getkelas() {
		return kelas;
	}

	public void setkelas(String kelas) {
		this.kelas = kelas;
	}

}