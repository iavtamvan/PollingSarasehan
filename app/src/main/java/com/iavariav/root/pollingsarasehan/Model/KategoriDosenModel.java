package com.iavariav.root.pollingsarasehan.Model;

import com.google.gson.annotations.SerializedName;

public class KategoriDosenModel{

	@SerializedName("JABATAN_DOSEN")
	private String jABATANDOSEN;

	@SerializedName("ID_KAT")
	private String iDKAT;

	@SerializedName("NAMA_DOSEN")
	private String nAMADOSEN;

	@SerializedName("FOTO_DOSEN")
	private Object fOTODOSEN;
	private String ID_DOS;

	public String getID_DOS() {
		return ID_DOS;
	}

	public void setID_DOS(String ID_DOS) {
		this.ID_DOS = ID_DOS;
	}



	public void setJABATANDOSEN(String jABATANDOSEN){
		this.jABATANDOSEN = jABATANDOSEN;
	}

	public String getJABATANDOSEN(){
		return jABATANDOSEN;
	}

	public void setIDKAT(String iDKAT){
		this.iDKAT = iDKAT;
	}

	public String getIDKAT(){
		return iDKAT;
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

	@Override
 	public String toString(){
		return 
			"KategoriDosenModel{" + 
			"jABATAN_DOSEN = '" + jABATANDOSEN + '\'' + 
			",iD_KAT = '" + iDKAT + '\'' + 
			",nAMA_DOSEN = '" + nAMADOSEN + '\'' + 
			",fOTO_DOSEN = '" + fOTODOSEN + '\'' +
					",ID_DOS = '" + ID_DOS + '\''+
			"}";
		}
}