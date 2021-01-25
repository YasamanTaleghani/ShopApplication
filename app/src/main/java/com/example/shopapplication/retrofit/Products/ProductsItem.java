package com.example.shopapplication.retrofit.Products;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductsItem{

	@SerializedName("upsell_ids")
	private List<Object> upsellIds;

	@SerializedName("featured")
	private boolean featured;

	@SerializedName("purchasable")
	private boolean purchasable;

	@SerializedName("grouped_products")
	private List<Object> groupedProducts;

	@SerializedName("_links")
	private Links links;

	@SerializedName("tax_status")
	private String taxStatus;

	@SerializedName("catalog_visibility")
	private String catalogVisibility;

	@SerializedName("type")
	private String type;

	@SerializedName("external_url")
	private String externalUrl;

	@SerializedName("price")
	private String price;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("sku")
	private String sku;

	@SerializedName("slug")
	private String slug;

	@SerializedName("date_on_sale_from")
	private Object dateOnSaleFrom;

	@SerializedName("shipping_required")
	private boolean shippingRequired;

	@SerializedName("date_modified_gmt")
	private String dateModifiedGmt;

	@SerializedName("images")
	private List<ImagesItem> images;

	@SerializedName("stock_status")
	private String stockStatus;

	@SerializedName("price_html")
	private String priceHtml;

	@SerializedName("download_expiry")
	private int downloadExpiry;

	@SerializedName("backordered")
	private boolean backordered;

	@SerializedName("weight")
	private String weight;

	@SerializedName("rating_count")
	private int ratingCount;

	@SerializedName("tags")
	private List<TagsItem> tags;

	@SerializedName("date_on_sale_to")
	private Object dateOnSaleTo;

	@SerializedName("sold_individually")
	private boolean soldIndividually;

	@SerializedName("backorders")
	private String backorders;

	@SerializedName("shipping_taxable")
	private boolean shippingTaxable;

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("download_limit")
	private int downloadLimit;

	@SerializedName("name")
	private String name;

	@SerializedName("shipping_class")
	private String shippingClass;

	@SerializedName("button_text")
	private String buttonText;

	@SerializedName("permalink")
	private String permalink;

	@SerializedName("status")
	private String status;

	@SerializedName("cross_sell_ids")
	private List<Object> crossSellIds;

	@SerializedName("short_description")
	private String shortDescription;

	@SerializedName("virtual")
	private boolean virtual;

	@SerializedName("downloadable")
	private boolean downloadable;

	@SerializedName("menu_order")
	private int menuOrder;

	@SerializedName("description")
	private String description;

	@SerializedName("date_on_sale_to_gmt")
	private Object dateOnSaleToGmt;

	@SerializedName("date_on_sale_from_gmt")
	private Object dateOnSaleFromGmt;

	@SerializedName("regular_price")
	private String regularPrice;

	@SerializedName("backorders_allowed")
	private boolean backordersAllowed;

	@SerializedName("downloads")
	private List<Object> downloads;

	@SerializedName("reviews_allowed")
	private boolean reviewsAllowed;

	@SerializedName("variations")
	private List<Object> variations;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("total_sales")
	private int totalSales;

	@SerializedName("on_sale")
	private boolean onSale;

	@SerializedName("manage_stock")
	private boolean manageStock;

	@SerializedName("default_attributes")
	private List<Object> defaultAttributes;

	@SerializedName("purchase_note")
	private String purchaseNote;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("tax_class")
	private String taxClass;

	@SerializedName("date_created_gmt")
	private String dateCreatedGmt;

	@SerializedName("average_rating")
	private String averageRating;

	@SerializedName("stock_quantity")
	private Object stockQuantity;

	@SerializedName("sale_price")
	private String salePrice;

	@SerializedName("shipping_class_id")
	private int shippingClassId;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("related_ids")
	private List<Integer> relatedIds;

	@SerializedName("attributes")
	private List<Object> attributes;

	@SerializedName("dimensions")
	private Dimensions dimensions;

	public List<Object> getUpsellIds(){
		return upsellIds;
	}

	public boolean isFeatured(){
		return featured;
	}

	public boolean isPurchasable(){
		return purchasable;
	}

	public List<Object> getGroupedProducts(){
		return groupedProducts;
	}

	public Links getLinks(){
		return links;
	}

	public String getTaxStatus(){
		return taxStatus;
	}

	public String getCatalogVisibility(){
		return catalogVisibility;
	}

	public String getType(){
		return type;
	}

	public String getExternalUrl(){
		return externalUrl;
	}

	public String getPrice(){
		return price;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public int getId(){
		return id;
	}

	public String getSku(){
		return sku;
	}

	public String getSlug(){
		return slug;
	}

	public Object getDateOnSaleFrom(){
		return dateOnSaleFrom;
	}

	public boolean isShippingRequired(){
		return shippingRequired;
	}

	public String getDateModifiedGmt(){
		return dateModifiedGmt;
	}

	public List<ImagesItem> getImages(){
		return images;
	}

	public String getStockStatus(){
		return stockStatus;
	}

	public String getPriceHtml(){
		return priceHtml;
	}

	public int getDownloadExpiry(){
		return downloadExpiry;
	}

	public boolean isBackordered(){
		return backordered;
	}

	public String getWeight(){
		return weight;
	}

	public int getRatingCount(){
		return ratingCount;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public Object getDateOnSaleTo(){
		return dateOnSaleTo;
	}

	public boolean isSoldIndividually(){
		return soldIndividually;
	}

	public String getBackorders(){
		return backorders;
	}

	public boolean isShippingTaxable(){
		return shippingTaxable;
	}

	public int getParentId(){
		return parentId;
	}

	public int getDownloadLimit(){
		return downloadLimit;
	}

	public String getName(){
		return name;
	}

	public String getShippingClass(){
		return shippingClass;
	}

	public String getButtonText(){
		return buttonText;
	}

	public String getPermalink(){
		return permalink;
	}

	public String getStatus(){
		return status;
	}

	public List<Object> getCrossSellIds(){
		return crossSellIds;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public boolean isVirtual(){
		return virtual;
	}

	public boolean isDownloadable(){
		return downloadable;
	}

	public int getMenuOrder(){
		return menuOrder;
	}

	public String getDescription(){
		return description;
	}

	public Object getDateOnSaleToGmt(){
		return dateOnSaleToGmt;
	}

	public Object getDateOnSaleFromGmt(){
		return dateOnSaleFromGmt;
	}

	public String getRegularPrice(){
		return regularPrice;
	}

	public boolean isBackordersAllowed(){
		return backordersAllowed;
	}

	public List<Object> getDownloads(){
		return downloads;
	}

	public boolean isReviewsAllowed(){
		return reviewsAllowed;
	}

	public List<Object> getVariations(){
		return variations;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public int getTotalSales(){
		return totalSales;
	}

	public boolean isOnSale(){
		return onSale;
	}

	public boolean isManageStock(){
		return manageStock;
	}

	public List<Object> getDefaultAttributes(){
		return defaultAttributes;
	}

	public String getPurchaseNote(){
		return purchaseNote;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getTaxClass(){
		return taxClass;
	}

	public String getDateCreatedGmt(){
		return dateCreatedGmt;
	}

	public String getAverageRating(){
		return averageRating;
	}

	public Object getStockQuantity(){
		return stockQuantity;
	}

	public String getSalePrice(){
		return salePrice;
	}

	public int getShippingClassId(){
		return shippingClassId;
	}

	public String getDateModified(){
		return dateModified;
	}

	public List<Integer> getRelatedIds(){
		return relatedIds;
	}

	public List<Object> getAttributes(){
		return attributes;
	}

	public Dimensions getDimensions(){
		return dimensions;
	}

}