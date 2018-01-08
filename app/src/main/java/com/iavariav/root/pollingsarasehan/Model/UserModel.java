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
//	@SerializedName("hwid")
	@Expose
	private String hwid;

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

	public String getHwid() {
		return hwid;
	}

	public void setHwid(String hwid) {
		this.hwid = hwid;
	}

}