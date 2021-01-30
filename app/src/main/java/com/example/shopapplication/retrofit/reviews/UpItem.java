package com.example.shopapplication.retrofit.reviews;

import com.google.gson.annotations.SerializedName;

public class UpItem{

	@SerializedName("href")
	private String href;

	public String getHref(){
		return href;
	}
}