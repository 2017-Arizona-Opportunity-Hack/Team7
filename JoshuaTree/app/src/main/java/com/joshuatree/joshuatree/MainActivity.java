package com.joshuatree.joshuatree;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.Manifest;

import javax.net.ssl.HttpsURLConnection;

import static com.joshuatree.joshuatree.StaticConstants.baseUrl;
import static com.joshuatree.joshuatree.StaticConstants.setUpMaps;

public class MainActivity extends AppCompatActivity {

    private boolean isFillingInventory;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpMaps();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

    }

    public void handleFillInventory(View v){
        isFillingInventory = true;
        Intent intent = new Intent(getBaseContext(), ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void handleCheckOut(View v){
        isFillingInventory = false;
        Intent intent = new Intent(getBaseContext(), ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String urlInventory = baseUrl + "/ChangeInventory";
        String urlUserInv = baseUrl + "/ChangeUsersTakenItems";
        if(resultCode == RESULT_OK){
            final Barcode barcode = data.getParcelableExtra("barcode");
            String code = barcode.displayValue;
            Log.d("yifan", code);
            if(isFillingInventory){
                String name = StaticConstants.upcToItemMap.get(code);
                if(name == null){
                    name = code;
                }
                HttpPostAsyncTask test = new HttpPostAsyncTask(name);
                Log.d("yifan", test.postData.toString());
                test.execute(urlInventory);
            }
            else{
                String userName = code;
                Intent intent = new Intent(getBaseContext(), UserCheckoutActivity.class);
                startActivityForResult(intent, REQUEST_CODE);


            }
        }
        else if(resultCode == UserCheckoutActivity.USER_CHECKOUT){
            String jsonStr = data.getStringExtra("jsonStr");
            Log.d("yifan", jsonStr);
            HttpPostAsyncTask test = new HttpPostAsyncTask("Nick", jsonStr);
            test.execute(urlUserInv);
        }
    }

    public class HttpPostAsyncTask extends AsyncTask<String, Void, Void> {
        // This is the JSON body of the post
        JSONObject postData;
        // This is a constructor that allows you to pass in the JSON body
        public HttpPostAsyncTask(String value1) {

            try{
                this.postData = new JSONObject();
                this.postData.put("keyToModify", value1);
                this.postData.put("operation", 1);
            }
            catch(JSONException e){
                e.printStackTrace();
            }


        }

        public HttpPostAsyncTask(String value1, String value2) {

            try{
                this.postData = new JSONObject();
                this.postData.put("userToModify", value1);
                this.postData.put("valuesToChange", new JSONObject(value2));

            }
            catch(JSONException e){
                e.printStackTrace();
            }


        }

        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected Void doInBackground(String... params) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(params[0]);
                Log.d("yifan", "hitting  " + url);
                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("POST");

                // Send the post body
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    Log.d("yifan", postData.toString());
                    writer.write(postData.toString());
                    writer.flush();
                }

                int statusCode = urlConnection.getResponseCode();

                if (statusCode ==  200) {
                    Log.d("yifan", "succcesssss");
                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                    // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
                } else {
                    Log.d("yifan", String.valueOf(statusCode));
                }

            } catch (Exception e) {

            }
            return null;
        }
    }

}