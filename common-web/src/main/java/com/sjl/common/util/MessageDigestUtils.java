package com.sjl.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 信息摘要算法工具类的功能，如 MD5 或 SHA 算法
 * 用于文本和文件加密验证，防止数据被修改
 * @author song
 * @version 1.0.0
 * @filename MessageDigestUtils.java
 * @time 2016-8-23 下午7:32:16
 * @copyright(C) 2016 深圳市北辰德科技股份有限公司
 */
public class MessageDigestUtils {
	
	/**
	 * 计算字符串的md5值
	 * @param str
	 * @return
	 */
	public static String getTextMD5(String str) {
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			byte[] digest = instance.digest(str.getBytes());
			return byteToHex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 计算字符MD5的方法2
	 * @param str
	 * @return
	 */
	public static String getTextMD5New(String str) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		return new BigInteger(1, secretBytes).toString(16);
	}
	
	/**
	 * 计算文件的md5值
	 * @param filePath
	 * @return
	 */
	public static String getFileMD5(String filePath) {
		File file = new File(filePath);
		try {
			FileInputStream inputStream = new FileInputStream(file);// 读取需要加密的文件,并封装如流对象里面
			byte[] buffer = new byte[1024];
			int len = 0;
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			while ((len = inputStream.read(buffer)) != -1) {
				messageDigest.update(buffer, 0, len);

			}
			inputStream.close();
			byte[] digest = messageDigest.digest();
			return byteToHex(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算文件的sha-1校验值
	 * @param filePath
	 * @return
	 */
	public static String getFileSha1(String filePath) {
		File file = new File(filePath);
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] buff = new byte[1024];
			int readed = -1;
			MessageDigest md = MessageDigest.getInstance("sha-1");
			while ((readed = in.read(buff)) > 0) {
				for (int i = 0; i < readed; i++)
					md.update(buff[i]);
			}
			in.close();
			byte[] digest = md.digest();
			return byteToHex(digest);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}
	
	/**
	 * 把字节码转换16进制数
	 * @param digest
	 * @return
	 */
	private static String byteToHex(byte[] digest) {
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			int i = b & 0xff; // 获取
			String hexString = Integer.toHexString(i);// 转为16进制
			if (hexString.length() < 2) {
				hexString = "0" + hexString;
			}
			sb.append(hexString);
		}
		return sb.toString();// 得到文件的特征码
	}
	
}
