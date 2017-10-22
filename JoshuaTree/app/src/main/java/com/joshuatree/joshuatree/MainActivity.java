package com.joshuatree.joshuatree;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScanItem.class);
                startActivity(intent);
            }
        });

//        String url ="http://10.152.123.130:8080/Modify-ItemData";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                        String[] tokens = response.split(";");
////                        for (String token: tokens){
////                            //if (token.substring(0, 1) != "." && token != "" && token.substring(0, 4) != "list")
////                            //stories.add(token.substring(0, token.lastIndexOf(".")));
////                            Log.v("azooz", token + " - ");
////                        }
//                        Log.v("azooz", response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("azooz", error.getMessage().toString());
//            }
//        });

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.v("azooz", response);
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.v("azooz", error.getMessage().toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("keyToModify", "Box of Chocolate #23");
//                params.put("valueToSet", "123");
//
//                return params;
//            }
//        };

//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("keyToModify", "azaldin");
//            jsonBody.put("valueToSet", "4");
//        } catch (JSONException e) {
//            e.printStackTrace();
////            Log.v("azooz", e.getMessage().toString());
//        }
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.v("azooz", response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.v("azooz", error.toString());
//                error.printStackTrace();
//            }
//        });

//        Singleton.getSingleton(this.getApplicationContext()).addRequest(jsonObjectRequest);
    }
}