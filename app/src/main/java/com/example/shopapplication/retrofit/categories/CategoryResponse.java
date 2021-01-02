package com.example.shopapplication.retrofit.categories;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse{

	@SerializedName("parent")
	private int parent;

	@SerializedName("image")
	private Image image;

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("_links")
	private Links links;

	@SerializedName("display")
	private String display;

	@SerializedName("name")
	private String name;

	@SerializedName("count")
	private int count;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	public int getParent(){
		return parent;
	}

	public Image getImage(){
		return image;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

	public Links getLinks(){
		return links;
	}

	public String getDisplay(){
		return display;
	}

	public String getName(){
		return name;
	}

	public int getCount(){
		return count;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getSlug(){
		return slug;
	}
}