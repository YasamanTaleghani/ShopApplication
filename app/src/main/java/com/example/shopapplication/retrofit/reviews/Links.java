package com.example.shopapplication.retrofit.reviews;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("self")
	private List<SelfItem> self;

	@SerializedName("collection")
	private List<CollectionItem> collection;

	@SerializedName("up")
	private List<UpItem> up;

	public List<SelfItem> getSelf(){
		return self;
	}

	public List<CollectionItem> getCollection(){
		return collection;
	}

	public List<UpItem> getUp(){
		return up;
	}
}