package com.ilongli.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件注入工具类
 * @author ilongli
 *
 */
public class PropertiesUtil {

	/**
	 * 从指定路径中注入配置文件
	 * @param prop		Properties对象
	 * @param propUrl	文件路径
	 * @throws IOException 
	 */
	public static void loadProperties(Properties prop, String propUrl) throws IOException {
    	InputStream in = null;
		try {
			in = new BufferedInputStream (new FileInputStream(propUrl));
			prop.load(in);
		} catch (IOException e) {
			throw new IOException("jpa配置加载失败。");
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw new IOException("jpa配置输入流关闭异常。");
			}
		}
	}
}
