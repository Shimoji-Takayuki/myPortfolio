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
                // �l�̕����𔽓]�����A16�i���ɕϊ�
                String tmpStr = Integer.toHexString(digest[i] & 0xff);
                sb.append(tmpStr);
            }
            
        }
        
        return sb.toString();
    }

    /*
     �Í��������C���^�[�t�F�[�X�֐�
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
