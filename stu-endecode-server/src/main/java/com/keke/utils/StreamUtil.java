package com.keke.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * XStream工具类, 用于xml与对象之间的转换
 * <br>Object fromXML(String xml) 将XML转换成对象
 * <br>String toXML(Object obj) 将对象转换成XML字段串
 *
 * @author XerCis
 * @version  1.0
 *
 */
public class StreamUtil {

	private static XStream xstream = new XStream(new DomDriver());//X流
	private static PrintStream ps;
	private static BufferedReader in;

	/**
	 * 字符串转为base64
	 * @param s 原始字符串
	 * @return String base64
	 */
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		try {
			Encoder encoder = Base64.getEncoder();
			byte[] encode = encoder.encode(s.getBytes("UTF-8"));
			return new String(encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * base64还原成字符串
	 * @param s base64串
	 * @return String 原始串
	 */
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		Decoder decoder = Base64.getDecoder();
		try {
			byte[] b = decoder.decode(s);
			return new String(b, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 *
	 * 字节数组转base64
	 * @param bs 字节数组
	 * @return String base64串
	 */
	public static String byteToBase64(byte[] bs) {
		Encoder encoder = Base64.getEncoder();
		return new String(encoder.encode(bs));
	}

	/**
	 * base64转字节数组
	 * @param base64 base64串
	 * @return byte 原始字节数组
	 */
	public static byte[] base64ToByte(String base64) {
		Decoder decoder = Base64.getMimeDecoder();
		return decoder.decode(base64);
	}

	/**
	 * 将XML转换成对象
	 * @return Object
	 */
	public static Object fromXML(String xml) {
		return xstream.fromXML(xml);
	}

	/**
	 * 将对象转换成XML字段串
	 * @return
	 */
	public static String toXML(Object obj) {
		String xml = xstream.toXML(obj);
		String a = xml.replaceAll("\n", "");//去掉换行
		String s = a.replaceAll("\r", "");
		return s;
	}

	/**
	 * 指定文件名写数据
	 * @throws IOException
	 */
	public static void printToFile(String file,String data) throws IOException {
		//不存在则创建
		File temp = new File(file);
		File tempParent = temp.getParentFile();
		if(tempParent!=null) {
			if(!tempParent.exists()) {
				tempParent.mkdirs();
				temp.createNewFile();
			}
		}

		FileOutputStream fos = new FileOutputStream(file);
		ps = new PrintStream(fos);
		ps.print(data);
		ps.close();
	}

	/**
	 * 指定文件名读数据
	 * @throws IOException
	 */
	public static String readFromFile(String file) throws IOException {
		in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String s = "";
		String temp = null;
		while((temp = in.readLine())!=null) {
			s += temp;
		}
		in.close();
		return s;
	}
}
