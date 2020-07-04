package com.utilClass;

import java.security.*;

public class EncryptionPass {

    private static String encryptInnerPass(String user, String pass) throws NoSuchAlgorithmException {

        StringBuilder sb = new StringBuilder();

        if (!user.isEmpty() && !pass.isEmpty()) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(user.getBytes());
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            
            for(int i = 0; i < digest.length; i++){
                // 値の符号を反転させ、16進数に変換
                String tmpStr = Integer.toHexString(digest[i] & 0xff);
                sb.append(tmpStr);
            }
            
        }
        
        return sb.toString();
    }

    /*
     暗号化処理インターフェース関数
    */
    public static String encryptPass(String user, String pass) {

        String rtnStr = "";

        try {
            rtnStr = encryptInnerPass(user, pass);
        } catch (NoSuchAlgorithmException e) {
            rtnStr = "";
        }

        return rtnStr;
    }

}
