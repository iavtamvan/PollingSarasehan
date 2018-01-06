package com.iavariav.root.pollingsarasehan.Model;

public class UserModel{
	private String nama;
	private String npm;
	private String hwid;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNpm(String npm){
		this.npm = npm;
	}

	public String getNpm(){
		return npm;
	}

	public void setHwid(String hwid){
		this.hwid = hwid;
	}

	public String getHwid(){
		return hwid;
	}

	@Override
 	public String toString(){
		return 
			"UserModel{" + 
			"nama = '" + nama + '\'' + 
			",npm = '" + npm + '\'' + 
			",hwid = '" + hwid + '\'' + 
			"}";
		}
}
