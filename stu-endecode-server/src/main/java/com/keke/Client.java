package com.keke;

import org.apache.commons.codec.binary.Hex;
import com.keke.utils.StreamUtil;
import com.keke.utils.ecc.ECCEnum;
import com.keke.utils.ecc.ECCUtil;
import com.keke.utils.ecc.GenerateKey;
import com.keke.utils.encrypt.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

public class Client {

    private static String publicKey_s;
    private static String publicKey_c;
    private static String privateKey_c;
    private static Map<String, String> keyPair;
    private static String md;
    private static String ewk;
    private static String de;
    private static String ds;
    private static String ad;
    private static Socket server= null;
    private static BufferedReader in = null;
    private static PrintWriter out= null;

    static {
        try {
            server=new Socket(InetAddress.getLocalHost(),5678);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out=new PrintWriter(server.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String decrypt(Integer rn) throws Exception {
        System.out.println("rn:"+rn);
        ad = "Client/"+rn.toString()+"/";
        String ds = StreamUtil.readFromFile(ad+"DS");
        publicKey_s = StreamUtil.readFromFile(ad+"publicKey_s");
        System.out.println("publicKey_s:"+publicKey_s);
        publicKey_c = StreamUtil.readFromFile(ad+"publicKey_c");
        privateKey_c = StreamUtil.readFromFile(ad+"privateKey_c");

        if(verifySignature() == true){
            de = StreamUtil.readFromFile(ad+"DE");
            byte[] dpk = ECCUtil.decrypt(BASE64Decoder.decodeBuffer(de), privateKey_c);
            System.out.println("dpk:"+StreamUtil.byteToBase64(dpk));
            SecretKeySpec key = new SecretKeySpec(dpk, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            ewk = StreamUtil.readFromFile(ad+"E(WK)");
            byte[] result = cipher.doFinal(StreamUtil.base64ToByte(ewk));
            return new String(result);
        }else{
            return null;
        }
    }

    public String encrypt(String wk,Integer rn) throws Exception{
        ad = "Client/"+rn.toString()+"/";
        BufferedReader wt=new BufferedReader(new InputStreamReader(System.in));
        String st=wt.readLine();
        out.println(st);
        out.flush();
        String str = in.readLine();
        System.out.println("str:"+ str +"12344");
        if(str.equals("请求正文内容...")){
            out.println(wk);
            out.flush();
        }
        str = in.readLine();
        System.out.println("str:"+ str +"123446");
        if(str.equals("请求客户端公钥...")){
            generateKeyPair();
            out.println(publicKey_c);
            out.flush();
        }
        out.println("请求DE...");
        out.flush();
        String de = in.readLine();
        StreamUtil.printToFile(ad+"DE",de);
        out.println("请求E(WK)...");
        out.flush();
        String ewk = in.readLine();
        StreamUtil.printToFile(ad+"E(WK)",ewk);
        out.println("请求DS...");
        out.flush();
        String ds =in.readLine();
        StreamUtil.printToFile(ad+"DS",ds);
        out.println("请求服务端公钥...");
        out.flush();
        publicKey_s =in.readLine();
        StreamUtil.printToFile(ad+"publicKey_s",publicKey_s);
        out.println("请求MD...");
        out.flush();
        md =in.readLine();
        StreamUtil.printToFile(ad+"MD(WK)",md);
        return ewk;
        //    server.close();
    }

    public boolean verifySignature() throws Exception{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(StreamUtil.base64ToByte(publicKey_s));
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);
        String md = StreamUtil.readFromFile(ad+"MD(WK)");
        signature.update(md.getBytes());
        ds = StreamUtil.readFromFile(ad+"DS");
        boolean bool = signature.verify(Hex.decodeHex(ds));
        System.out.println("验证：" + bool);
        return bool;
    }

    public void generateKeyPair() throws Exception{
        keyPair = GenerateKey.getGenerateKey();
        privateKey_c = keyPair.get(ECCEnum.PRIVATE_KEY.value());
        publicKey_c = keyPair.get(ECCEnum.PUBLIC_KEY.value());
        System.out.println("PublicKey_c="+publicKey_c);
        System.out.println("PrivateKey_c="+privateKey_c);
        StreamUtil.printToFile(ad+"publicKey_c", publicKey_c);
        StreamUtil.printToFile(ad+"privateKey_c", privateKey_c);
    }
}

