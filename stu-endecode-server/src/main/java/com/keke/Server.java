package com.keke;

import com.keke.utils.StreamUtil;
import com.keke.utils.des.DESProvider;
import com.keke.utils.ecc.ECCUtil;
import com.keke.utils.encrypt.BASE64Encoder;
import com.keke.utils.sha1.ShaUtil;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class Server {

    private static byte[] dpk_b;
    private static String dpk;
    private static String publicKey_c;
    private static String publicKey_s;
    private static String privateKey_s;
    private static String md;
    private static byte[] ewk;
    private static byte[] de;
    private static byte[] ds;

    public static void main(String[] args) throws Exception {
        ServerSocket server=new ServerSocket(5678);
        Socket client=server.accept();
        System.out.println("服务器端启动成功！");
        BufferedReader in=
                new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out=new PrintWriter(client.getOutputStream());
        while(true){
            String str=in.readLine();
            if(null == str)
                continue;
            else{
                if(str.equals("begin...")){
                    out.println("请求正文内容...");
                    out.flush();

                    String wk = in.readLine();
                    System.out.println("wk:"+wk);
                    out.println("请求客户端公钥...");
                    out.flush();

                    publicKey_c = in.readLine();
                    System.out.println("publicKey_c:"+publicKey_c+"lll");
                    StreamUtil.printToFile("Server/publicKey_c",publicKey_c);

                    Server server1 = new Server();
                    server1.generateDpk();
                    String str_de = server1.generateDE();
                    String str_ewk = server1.generateEWK(wk);
                    server1.generateMD(wk);
                    String str_ds = server1.generateDS();
                    str = in.readLine();
                    if(str.equals("请求DE...")){
                        out.println(str_de);
                        out.flush();
                        str = in.readLine();
                        if(str.equals("请求E(WK)...")){
                            out.println(str_ewk);
                            out.flush();
                            str = in.readLine();
                            if(str.equals("请求DS...")){
                                out.println(str_ds);
                                out.flush();
                                str = in.readLine();
                                if(str.equals("请求服务端公钥...")){
                                    out.println(publicKey_s);
                                    out.flush();
                                    str = in.readLine();
                                    if(str.equals("请求MD...")) {
                                        out.println(md);
                                        out.flush();
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    str = in.readLine();
                    if (null == str){
                        continue;
                    }else{
                        if(str.equals("请求服务端公钥...")){
                            System.out.println("str:"+str);
                            out.println(publicKey_s);
                            out.flush();
                        }
                    }
                }
            }
        }



    }

    public String generateDE() throws Exception{
        byte [] de = ECCUtil.encrypt(dpk_b,publicKey_c);
        String str = BASE64Encoder.encodeBuffer(de);
        System.out.println("DE:"+str);
        StreamUtil.printToFile("Server/DE",str);
        return str;
    }

    public String generateEWK(String wk) throws Exception{
        SecretKeySpec key = new SecretKeySpec(dpk_b, "AES");
        // 创建密码器。Cipher类为加密和解密提供密码功能
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = wk.getBytes("utf-8");
        // 初始化为加密模式的密码器。DECRYPT_MODE用于将 Cipher 初始化为解密模式的常量。用密钥初始化此 Cipher。
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //按单部分操作加密或解密数据，或者结束一个多部分操作。数据将被加密或解密
        byte[] result = cipher.doFinal(byteContent);// 加密
        ewk = result;
        StreamUtil.printToFile("Server/E(WK)",StreamUtil.byteToBase64(result));
        System.out.println("E(WK):"+StreamUtil.byteToBase64(result));
        return StreamUtil.byteToBase64(result);
    }

    public void generateMD(String wk) throws Exception{
        md = ShaUtil.toSHA1(wk);
        StreamUtil.printToFile("Server/MD(WK)",md);
    }

    public String generateDS() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
        publicKey_s = StreamUtil.byteToBase64(dsaPublicKey.getEncoded());
        StreamUtil.printToFile("Server/publicKey_s",publicKey_s);
        privateKey_s = StreamUtil.byteToBase64(dsaPrivateKey.getEncoded());
        StreamUtil.printToFile("Server/privateKey_s",privateKey_s);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);
        signature.update(md.getBytes());
        byte[] res = signature.sign();
        System.out.println("签名：" + Hex.encodeHexString(res));
        StreamUtil.printToFile("Server/DS",Hex.encodeHexString(res));
        return Hex.encodeHexString(res);
    }


    public void generateDpk() throws Exception {
        dpk_b = DESProvider.generateKeyByte();
        dpk = StreamUtil.byteToBase64(dpk_b);
        System.out.println("dpk:"+dpk);
        StreamUtil.printToFile("Server/dpk",dpk);
    }

}
