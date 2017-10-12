package com.nepal.naxa.smartnaari.data.local.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class YuwaPustaResponse{

	@SerializedName("data")
	private List<YuwaQuestion> data;

	@SerializedName("status")
	private String status;

	public void setData(List<YuwaQuestion> data){
		this.data = data;
	}

	public List<YuwaQuestion> getData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}