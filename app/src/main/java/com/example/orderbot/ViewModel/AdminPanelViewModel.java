package com.example.orderbot.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.orderbot.AllCardsFragment;
import com.example.orderbot.Model.Card;
import com.example.orderbot.Model.Item;
import com.example.orderbot.Request.RequestSingleton;
import com.example.orderbot.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPanelViewModel extends AndroidViewModel {
    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<List<Card>> cards;
    private Context context;

    private String TAG = AccessTokenViewModel.class.getSimpleName();

    public AdminPanelViewModel(@NonNull Application application) {
        super(application);

        this.context = application.getApplicationContext();

        this.isLoading = new MutableLiveData<>();
        this.isLoading.setValue(true);
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<List<Card>> getCards(String accessToken) {
        if (cards == null) {
            cards = new MutableLiveData<List<Card>>();
            this.loadCards(accessToken);
        }

        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards.setValue(cards);
    }

    public void loadCards(String accessToken) {
        // make api call
        String url = Settings.domain + "/api/user/cards";

        RequestQueue queue = RequestSingleton.getInstance(context).getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response.toString());

                setIsLoading(false);

                List<Card> cardsList = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        Log.d(TAG, "name: " + response.getJSONObject(i).get("items").toString());
                        JSONObject cardObject = response.getJSONObject(i);

                        Card card = new Card();

                        card.setName(cardObject.get("name").toString());
                        card.setAbout(cardObject.get("about").toString());
                        card.setId(Integer.parseInt(cardObject.get("id").toString()));
                        card.setImage(cardObject.get("image_link").toString());
                        card.setCategory(cardObject.get("category").toString());

                        List<Item> items = new ArrayList<>();

                        for (int j = 0; j < cardObject.getJSONArray("items").length(); j++) {
                            JSONObject itemObject = cardObject.getJSONArray("items").getJSONObject(i);

                            Item item = new Item();
                            item.setCardID(card.getId());
                            item.setId(Integer.parseInt(itemObject.get("id").toString()));
                            item.setQuantity(Integer.parseInt(itemObject.get("quantity").toString()));
                            item.setPrice(Integer.parseInt(itemObject.get("price").toString()));
                            item.setCategoryID(Integer.parseInt(itemObject.get("category_id").toString()));

                            items.add(item);
                        }

                        cardsList.add(card);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                cards.setValue(cardsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                Log.d(TAG, "request access token: " + accessToken);

                headers.put("Accept", "Application/json");
                headers.put("Authorization", "Bearer " + accessToken);

                return headers;
            }
        };

        RequestSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void reset() {
        cards = null;
        isLoading.setValue(true);
        Log.d(TAG, "cards: " + cards);
    }
}
