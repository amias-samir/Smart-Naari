package com.nepal.naxa.smartnaari.data.network;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("owl_gender")
	private String owlGender;

	@SerializedName("owl_introduction_text")
	private String owlIntroductionText;

	@SerializedName("owl_name")
	private String owlName;

	@SerializedName("owl_id")
	private String owlId;

	public void setOwlGender(String owlGender){
		this.owlGender = owlGender;
	}

	public String getOwlGender(){
		return owlGender;
	}

	public void setOwlIntroductionText(String owlIntroductionText){
		this.owlIntroductionText = owlIntroductionText;
	}

	public String getOwlIntroductionText(){
		return owlIntroductionText;
	}

	public void setOwlName(String owlName){
		this.owlName = owlName;
	}

	public String getOwlName(){
		return owlName;
	}

	public void setOwlId(String owlId){
		this.owlId = owlId;
	}

	public String getOwlId(){
		return owlId;
	}
}