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

public class GetProductionListDeserializer implements JsonDeserializer<List<ProductsItem>> {

    @Override
    public List<ProductsItem> deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        List<ProductsItem> items = new ArrayList<>();

        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonArray itemJsonArray = jsonObject.getAsJsonArray();
        for (JsonElement itemsJsonElement : itemJsonArray) {
            final JsonObject itemJsonObject = itemsJsonElement.getAsJsonObject();
            final int id = itemJsonObject.get("id").getAsInt();
            final String title = itemJsonObject.get("title").getAsString();
            final String description = jsonObject.get("description").getAsString();
            final List<Image> imagesItems = new ArrayList<>();

            JsonObject jsonObject1 = jsonObject.get("images").getAsJsonObject();
            JsonArray jsonArray = jsonObject1.getAsJsonArray();
            for (JsonElement itemJsonElement : jsonArray) {
                final JsonObject itemJsonObject1 = itemJsonElement.getAsJsonObject();
                final int objId = itemJsonObject1.get("id").getAsInt();
                final String objSrc = itemJsonObject1.get("src").getAsString();

            }

            ProductsItem productionItem = new ProductsItem();
            items.add(productionItem);

        }

        return items;
    }

}
