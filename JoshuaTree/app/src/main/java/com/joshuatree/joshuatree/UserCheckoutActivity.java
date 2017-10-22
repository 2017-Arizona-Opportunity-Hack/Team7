package com.joshuatree.joshuatree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.joshuatree.joshuatree.StaticConstants.setUpMaps;

public class UserCheckoutActivity extends AppCompatActivity {

    private ListView lv;
    private HashMap<String, Integer> map;
    private MyAdapter adapter;
    public static final int USER_CHECKOUT = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_checkout);
        setUpMaps();
        this.lv = (ListView) findViewById(R.id.lv);
        this.map = new HashMap<>();
        this.adapter = new MyAdapter(map);
        this.lv.setAdapter(adapter);

    }

    public void handleCamera(View v){
        Intent intent = new Intent(getBaseContext(), ScanActivity.class);
        startActivityForResult(intent, 200);

    }

    public void handleCheckOut(View v){
        Intent intent = new Intent();
        JSONObject json = new JSONObject(map);

        intent.putExtra("jsonStr", json.toString());
        setResult(USER_CHECKOUT, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            final Barcode barcode = data.getParcelableExtra("barcode");
            String code = barcode.displayValue;
            Log.d("yifan", code);
            if(StaticConstants.upcToItemMap.containsKey(code)){
                String translatedItemName = StaticConstants.upcToItemMap.get(code);
                if(map.containsKey(translatedItemName)){
                    map.put(translatedItemName, map.get(translatedItemName)+1);
                }
                else{
                    map.put(translatedItemName, 1);
                }
            }
            else{
                if(map.containsKey(code)){
                    map.put(code, map.get(code)+1);
                }
                else{
                    map.put(code, 1);
                }
            }
        }
        showList();
    }

    public void showList(){
        adapter = new MyAdapter(this.map);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
