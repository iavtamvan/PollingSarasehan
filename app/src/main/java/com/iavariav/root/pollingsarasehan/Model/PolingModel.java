package com.iavariav.root.pollingsarasehan.Model;
import com.google.gson.annotations.SerializedName;

public class PolingModel{

//	@SerializedName("ID_DOSEN")
	private String iDDOSEN;

//	@SerializedName("JABATAN_DOSEN")
	private String jABATANDOSEN;

//	@SerializedName("POLING")
	private String pOLING;

//	@SerializedName("NAMA_DOSEN")
	private String nAMADOSEN;

//	@SerializedName("FOTO_DOSEN")
	private Object fOTODOSEN;

//	@SerializedName("FAKULTAS")
	private String fAKULTAS;

	private String error_msg;

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

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

	public void setPOLING(String pOLING){
		this.pOLING = pOLING;
	}

	public String getPOLING(){
		return pOLING;
	}

	public void setNAMADOSEN(String nAMADOSEN){
		this.nAMADOSEN = nAMADOSEN;
	}

	public String getNAMADOSEN(){
		return nAMADOSEN;
	}

	public void setFOTODOSEN(Object fOTODOSEN){
		this.fOTODOSEN = fOTODOSEN;
	}

	public Object getFOTODOSEN(){
		return fOTODOSEN;
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
//			"PolingModel{" +
//			"iD_DOSEN = '" + iDDOSEN + '\'' +
//			",jABATAN_DOSEN = '" + jABATANDOSEN + '\'' +
//			",pOLING = '" + pOLING + '\'' +
//			",nAMA_DOSEN = '" + nAMADOSEN + '\'' +
//			",fOTO_DOSEN = '" + fOTODOSEN + '\'' +
//			",fAKULTAS = '" + fAKULTAS + '\'' +
//			"}";
//		}
}