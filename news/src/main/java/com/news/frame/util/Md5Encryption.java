package com.news.frame.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSON;

/**
 * Created by Amuse
 * Data:2018/12/29 0029
 * Desc: md5加密
 */

public class Md5Encryption {
    private static Md5Encryption md5Encryption=null;

    public static Md5Encryption getInstance(){
        if(md5Encryption==null){
            md5Encryption=new Md5Encryption();
        }

        return md5Encryption;
    }



    private List<Map.Entry<String, Object>> mHashMapEntryList ;
    public String getMd5(Map<String,Object> hashMap){
        mHashMapEntryList=new ArrayList<Map.Entry<String,Object>>(hashMap.entrySet());


        //数组进行排序
        Collections.sort(mHashMapEntryList, new Comparator<Map.Entry<String,Object>>() {

            @Override
            public int compare(Map.Entry<String, Object> firstMapEntry, Map.Entry<String, Object> secondMapEntry) {
                return firstMapEntry.getKey().compareTo(secondMapEntry.getKey());
            }

        });


        Map<String,Object> newHasmap=new LinkedHashMap<>();
        for(Map.Entry<String,Object> mapping:mHashMapEntryList){
            newHasmap.put(mapping.getKey(),mapping.getValue());
        }
        String strUTF8="";
        try {

//            strUTF8 = JSON.toJSONString(newHasmap);
            strUTF8+="**honglaba@2017**";
        } catch (Exception e) {
            e.printStackTrace();
        }

        String remove_empty=strUTF8.replace(" ","");

        String md5_code=md5(remove_empty);

        return md5_code;

    }


    public String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
