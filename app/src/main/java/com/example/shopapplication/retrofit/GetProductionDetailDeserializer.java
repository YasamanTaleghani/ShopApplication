package com.example.shopapplication.retrofit;

import android.media.Image;

import com.example.shopapplication.retrofit.model.ProductsItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetProductionDetailDeserializer implements JsonDeserializer<ProductsItem> {

    @Override
    public ProductsItem deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
        final int id = jsonObject.get("id").getAsInt();
        final String title = jsonObject.get("title").getAsString();
        final String description = jsonObject.get("description").getAsString();
        final List<Image> imagesItems = new ArrayList<>();

        JsonObject jsonObject1 = jsonObject.get("images").getAsJsonObject();
        JsonArray jsonArray = jsonObject1.getAsJsonArray();
        for (JsonElement itemJsonElement : jsonArray) {
            final JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();
            final int objId = itemJsonObject.get("id").getAsInt();
            final String objSrc = itemJsonObject.get("src").getAsString();

        }

        ProductsItem productionItem = new ProductsItem();

        return productionItem;
    }
}
