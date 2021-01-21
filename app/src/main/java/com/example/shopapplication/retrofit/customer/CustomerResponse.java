package com.example.shopapplication.retrofit.customer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@Entity
public class CustomerResponse {

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("role")
	private String role;

	@SerializedName("_links")
	private Links links;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("billing")
	private Billing billing;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("shipping")
	private Shipping shipping;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@PrimaryKey
	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("is_paying_customer")
	private boolean isPayingCustomer;

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	@SerializedName("username")
	private String username;

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public String getRole(){
		return role;
	}

	public Links getLinks(){
		return links;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getLastName(){
		return lastName;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public Billing getBilling(){
		return billing;
	}

	public String getDateModified(){
		return dateModified;
	}

	public Shipping getShipping(){
		return shipping;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public boolean isIsPayingCustomer(){
		return isPayingCustomer;
	}

	public String getUsername(){
		return username;
	}

	//constructor
	public CustomerResponse(String firstName, String lastName, Billing billing,
							String email, String username) {
		this.lastName = lastName;
		this.billing = billing;
		this.firstName = firstName;
		this.email = email;
		this.username = username;
	}

	public CustomerResponse() {
	}
}