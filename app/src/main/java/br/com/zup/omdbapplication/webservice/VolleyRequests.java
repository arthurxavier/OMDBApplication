package br.com.zup.omdbapplication.webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import br.com.zup.omdbapplication.ui.VolleyResult;

/**
 * Created by arthur on 26/01/17.
 */

public class VolleyRequests implements VolleyResult {

    private Context context;

    //Construtor
    public VolleyRequests(Context context)
    {
        this.context = context;
    }


    public void volleyStringRequest(String url, final VolleyResult result) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result.onSucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.onError();
                    }
                }
        );
        VolleyQueue.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void volleyImageRequest(String url, final VolleyResult result){
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        result.onSucess(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.onError();
                    }
                }
        );
        VolleyQueue.getInstance(context).addToRequestQueue(imageRequest);
    }

    public void volleyJsonRequest(String url, final VolleyResult result){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        result.onSucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.onError();
                    }
                }
        );
        VolleyQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onSucess(Object result) {

    }

    @Override
    public void onError() {

    }
}
