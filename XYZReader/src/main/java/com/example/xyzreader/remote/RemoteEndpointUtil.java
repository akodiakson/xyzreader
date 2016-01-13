package com.example.xyzreader.remote;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;

public class RemoteEndpointUtil {
    private static final String TAG = "RemoteEndpointUtil";

    private RemoteEndpointUtil() {
    }

    public static JSONArray fetchJsonArray() {
        String itemsJson = null;
        try {
            itemsJson = fetchPlainText(Config.BASE_URL);
        } catch (IOException e) {
            Log.e(TAG, "Error fetching items JSON", e);
            return null;
        }

        // Parse JSON
        try {
            JSONTokener tokener = new JSONTokener(itemsJson);
            Object val = tokener.nextValue();
            if (!(val instanceof JSONArray)) {
                throw new JSONException("Expected JSONArray");
            }
            return (JSONArray) val;
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing items JSON", e);
        }

        return null;
    }

    static String fetchPlainText(URL url) throws IOException {
        //Source: https://discussions.udacity.com/t/volley-not-loading-images-from-https-on-device/37286/10
        OkHttpClient client = new OkHttpClient();

        com.squareup.okhttp.Request request = new  com.squareup.okhttp.Request.Builder().url(url).build();
        com.squareup.okhttp.Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
