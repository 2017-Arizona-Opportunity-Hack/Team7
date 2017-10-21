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
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    Button btn;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.txtContent);
        btn = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.imgview);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        

        Bitmap bitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.puppy
        );
        mImageView.setImageBitmap(bitmap);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        if (!barcodeDetector.isOperational()) {
            mTextView.setText("Not working :(");
        }

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = barcodeDetector.detect(frame);

        Barcode code = barcodes.valueAt(0);
        mTextView.setText(code.rawValue);

//        String url ="https://rel203.000webhostapp.com/stories/listStories.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        String[] tokens = response.split(";");
//                        for (String token: tokens){
//                            //if (token.substring(0, 1) != "." && token != "" && token.substring(0, 4) != "list")
//                            //stories.add(token.substring(0, token.lastIndexOf(".")));
//                            Log.v("azooz", token + " - ");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("azooz", "Returned Error");
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
//                        Log.d("Error.Response", error.getMessage().toString());
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("item", "Box of Chocolate #23");
//                params.put("quantity", -1 + "");
//
//                return params;
//            }
//        };

//        Singleton.getSingleton(this.getApplicationContext()).addRequest(stringRequest);
    }
}
