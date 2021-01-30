package com.example.shopapplication.retrofit.reviews;

import com.google.gson.annotations.SerializedName;

public class ReviewsResponse{

	@SerializedName("reviewer_avatar_urls")
	private ReviewerAvatarUrls reviewerAvatarUrls;

	@SerializedName("_links")
	private Links links;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("review")
	private String review;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("rating")
	private int rating;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("id")
	private int id;

	@SerializedName("reviewer")
	private String reviewer;

	@SerializedName("reviewer_email")
	private String reviewerEmail;

	@SerializedName("status")
	private String status;

	public ReviewerAvatarUrls getReviewerAvatarUrls(){
		return reviewerAvatarUrls;
	}

	public Links getLinks(){
		return links;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getReview(){
		return review;
	}

	public int getProductId(){
		return productId;
	}

	public int getRating(){
		return rating;
	}

	public boolean isVerified(){
		return verified;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public int getId(){
		return id;
	}

	public String getReviewer(){
		return reviewer;
	}

	public String getReviewerEmail(){
		return reviewerEmail;
	}

	public String getStatus(){
		return status;
	}
}