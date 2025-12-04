package com.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TxProperties {

	private volatile static Properties properties = null;

	public static String getString(String key) {
		if (properties == null) {
			InputStream in = TxProperties.class.getClassLoader().getResourceAsStream("config.properties");
			properties = new Properties();
			try {
				properties.load(in);
			} catch (IOException e) {

			}
		}

		if (properties == null) {
			return null;
		}

		return properties.getProperty(key);
	}
}
