package com.dhu.bbs.Util;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class BbsUtil {

    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
                  } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    public static String getJSONString(int code, Map<String, Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        for (Map.Entry<String, Object> entry : map.entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public  static String IMAGE_DIR = "d:upload/";
    public  static String DOMAIN = "127.0.0.1:8080/image";
    public static  String[] IMAGE_FILE_EXT = new String[] {"png", "bmp", "jpg", "gif", "jepg"};



    public  static boolean isFileAllowed(String extName){
        for(String ext: IMAGE_FILE_EXT){
            if(extName.equals(ext) ){
                return true;
            }
        }
        return false;
    }

}
