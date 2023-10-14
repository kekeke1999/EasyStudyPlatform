package com.keke.utils.ecc;


import com.keke.utils.encrypt.BASE64Encoder;

import java.util.Map;

public class ECTest {

    public static void main(String[] argu) throws Exception {
        Map<String,String> map = GenerateKey.getGenerateKey();
        String privKey = map.get(ECCEnum.PRIVATE_KEY.value());
        String pubKey = map.get(ECCEnum.PUBLIC_KEY.value());

        System.out.println("私钥：" + privKey);

        System.out.println("公钥：" + pubKey);
        String text = "java ECC 加密、解密算法，如果写的有问题，请大家踊跃评论，谢谢！";
        byte [] b = ECCUtil.encrypt(text.getBytes("UTF-8"),pubKey);
        String str = BASE64Encoder.encodeBuffer(b);
        System.out.println("密文：" + str);
        String outputStr = new String(ECCUtil.decrypt(b,privKey));
        System.out.println("原始文本：" + text);
        System.out.println("解密文本：" + outputStr);
    }
}

