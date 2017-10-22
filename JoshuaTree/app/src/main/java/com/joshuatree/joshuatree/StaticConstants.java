package com.joshuatree.joshuatree;

import java.util.HashMap;

/**
 * Created by Andrew on 10/21/17.
 */

public final class StaticConstants {
    final static HashMap<String,String> upcToItemMap = new HashMap<String,String>();
    final static HashMap<String,String> userMap = new HashMap<String,String>();
    final static String baseUrl = "http://10.143.195.71:8080";

    public static void setUpMaps(){
        //upc
        upcToItemMap.put("049000042566","Coke Zero");

        //people

    }
}