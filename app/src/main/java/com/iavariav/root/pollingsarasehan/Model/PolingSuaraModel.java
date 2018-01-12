package com.iavariav.root.pollingsarasehan.Model;

import com.google.gson.annotations.SerializedName;

public class PolingSuaraModel{

	@SerializedName("ID_DOSEN")
	private String iDDOSEN;

	@SerializedName("JABATAN_DOSEN")
	private String jABATANDOSEN;

	@SerializedName("SARAN")
	private String sARAN;

	@SerializedName("POLING")
	private String pOLING;

	@SerializedName("KRITIK")
	private String kRITIK;

	@SerializedName("NAMA_DOSEN")
	private String nAMADOSEN;

	@SerializedName("FOTO_DOSEN")
	private String fOTODOSEN;

	@SerializedName("ID_DOS")
	private String iDDOS;

	@SerializedName("NPM")
	private String nPM;

	@SerializedName("FAKULTAS")
	private String fAKULTAS;

	public void setIDDOSEN(String iDDOSEN){
		this.iDDOSEN = iDDOSEN;
	}

	public String getIDDOSEN(){
		return iDDOSEN;
	}

	public void setJABATANDOSEN(String jABATANDOSEN){
		this.jABATANDOSEN = jABATANDOSEN;
	}

	public String getJABATANDOSEN(){
		return jABATANDOSEN;
	}

	public void setSARAN(String sARAN){
		this.sARAN = sARAN;
	}

	public String getSARAN(){
		return sARAN;
	}

	public void setPOLING(String pOLING){
		this.pOLING = pOLING;
	}

	public String getPOLING(){
		return pOLING;
	}

	public void setKRITIK(String kRITIK){
		this.kRITIK = kRITIK;
	}

	public String getKRITIK(){
		return kRITIK;
	}

	public void setNAMADOSEN(String nAMADOSEN){
		this.nAMADOSEN = nAMADOSEN;
	}

	public String getNAMADOSEN(){
		return nAMADOSEN;
	}

	public void setFOTODOSEN(String fOTODOSEN){
		this.fOTODOSEN = fOTODOSEN;
	}

	public String getFOTODOSEN(){
		return fOTODOSEN;
	}

	public void setIDDOS(String iDDOS){
		this.iDDOS = iDDOS;
	}

	public String getIDDOS(){
		return iDDOS;
	}

	public void setNPM(String nPM){
		this.nPM = nPM;
	}

	public String getNPM(){
		return nPM;
	}

	public void setFAKULTAS(String fAKULTAS){
		this.fAKULTAS = fAKULTAS;
	}

	public String getFAKULTAS(){
		return fAKULTAS;
	}

//	@Override
// 	public String toString(){
//		return
//			"PolingSuaraModel{" +
//			"iD_DOSEN = '" + iDDOSEN + '\'' +
//			",jABATAN_DOSEN = '" + jABATANDOSEN + '\'' +
//			",sARAN = '" + sARAN + '\'' +
//			",pOLING = '" + pOLING + '\'' +
//			",kRITIK = '" + kRITIK + '\'' +
//			",nAMA_DOSEN = '" + nAMADOSEN + '\'' +
//			",fOTO_DOSEN = '" + fOTODOSEN + '\'' +
//			",iD_DOS = '" + iDDOS + '\'' +
//			",nPM = '" + nPM + '\'' +
//			",fAKULTAS = '" + fAKULTAS + '\'' +
//			"}";
//		}
}