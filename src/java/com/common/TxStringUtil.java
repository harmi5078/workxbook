package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TxStringUtil {

	public static final String allChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public final static java.text.SimpleDateFormat sdfShortTimePlus = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
	public final static java.text.SimpleDateFormat sdfShortTime = new java.text.SimpleDateFormat("HH:mm:ss");
	public final static java.text.SimpleDateFormat sdfLong = new java.text.SimpleDateFormat("yyyy-MM-dd");
	public final static java.text.SimpleDateFormat sdfLongTimePlus = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		return "".equals(str.trim());
	}

	public static String formatLongDate(String datetime) {
		String nowDate = "";
		try {
			Date date = sdfLongTimePlus.parse(datetime);
			nowDate = sdfLong.format(date);
			return nowDate;
		} catch (Exception e) {
			return datetime;
		}
	}

	public static String getNowLongPlusTime() {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new java.util.Date().getTime());
			nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		} catch (Exception e) {
			return "2024-12-12 12:25:26";
		}
	}

	public static String getNowSortPlusTime() {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new java.util.Date().getTime());
			nowDate = sdfShortTimePlus.format(date);
			return nowDate;
		} catch (Exception e) {
			return "20150101010101";
		}
	}

	public static String getNowSortTime() {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new java.util.Date().getTime());
			nowDate = sdfShortTime.format(date);
			return nowDate;
		} catch (Exception e) {
			return "1:1:1";
		}
	}

	public static StringBuffer pros(Object object) {

		if (object == null) {
			return new StringBuffer("[]");
		}

		Class<?> clazz = object.getClass();

		Method[] methods = clazz.getMethods();

		if (methods == null || methods.length == 0) {
			return null;
		}

		Method method = null;
		Object mRetObjA = null;
		Object mRetObjB = null;

		Class<?> onwClass = null;
		Object objectB = null;
		try {
			onwClass = Class.forName(clazz.getName());
			objectB = onwClass.getDeclaredConstructor().newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (onwClass == null || objectB == null) {
			return null;
		}

		StringBuffer comStringBuffer = new StringBuffer();

		Class<?> returnType = null;

		comStringBuffer.append(clazz.getSimpleName() + "[");
		boolean hasDif = false;
		String methodName = null;
		String propertyName = null;
		for (int i = 0; i < methods.length; i++) {

			method = methods[i];
			if (!method.getName().startsWith("get")) {
				continue;
			}
			returnType = method.getReturnType();
			if (!checkReturnType(returnType)) {
				continue;
			}

			mRetObjA = null;
			try {
				mRetObjA = method.invoke(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			methodName = method.getName();
			propertyName = methodName.replace("get", "");
			if (propertyName.equalsIgnoreCase("id")) {

				if (hasDif) {
					comStringBuffer.append(", ");
				}

				comStringBuffer.append("Id=" + mRetObjA);
				hasDif = true;
				continue;
			}

			mRetObjB = null;
			try {
				mRetObjB = method.invoke(objectB);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			if (mRetObjB == null && mRetObjA == null) {
				continue;
			}

			if (mRetObjB != null && mRetObjA == null) {

				if (hasDif) {
					comStringBuffer.append(", ");
				}

				comStringBuffer.append(propertyName);
				comStringBuffer.append("->NULL");
				hasDif = true;
				continue;
			}

			if (mRetObjB == null && mRetObjA != null) {

				if (hasDif) {
					comStringBuffer.append(", ");
				}

				comStringBuffer.append(propertyName);
				comStringBuffer.append("=" + mRetObjA);
				hasDif = true;
				continue;
			}

			if (!mRetObjA.equals(mRetObjB)) {

				if (hasDif) {
					comStringBuffer.append(", ");
				}

				comStringBuffer.append(propertyName);
				comStringBuffer.append("=" + mRetObjA);
				hasDif = true;
			}
		}

		// MenuOperation [id=1876, roleId=144,staId=129, operId=70]
		comStringBuffer.append("]");

		return comStringBuffer;
	}

	private static boolean checkReturnType(Class<?> returnType) {

		String typeName = returnType.getSimpleName();

		if (typeName.equals("int") || typeName.equals("double") || typeName.equals("boolean")
				|| typeName.equals("String") || typeName.equals("float")) {
			return true;
		}

		return false;
	}

	public static JSONArray toJSONArray(@SuppressWarnings("rawtypes") ArrayList list) {

		JSONArray array = new JSONArray();

		JSONObject jsonObject = null;

		Object info = null;
		for (int i = 0; i < list.size(); i++) {
			info = list.get(i);

			try {
				jsonObject = toJSONObject(info);
				array.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return array;
	}

	public static JSONObject toJSONObject(Object object) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		if (object == null) {
			return jsonObject;
		}

		Class<?> clazz = object.getClass();

		Method[] methods = clazz.getMethods();

		if (methods == null || methods.length == 0) {
			return null;
		}

		Method method = null;
		Object mRetObjA = null;

		Class<?> onwClass = null;
		Object objectB = null;
		try {
			onwClass = Class.forName(clazz.getName());
			objectB = onwClass.getDeclaredConstructor().newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (onwClass == null || objectB == null) {
			return null;
		}

		Class<?> returnType = null;

		String methodName = null;
		String propertyName = null;
		for (int i = 0; i < methods.length; i++) {

			method = methods[i];
			if (!method.getName().startsWith("get")) {
				continue;
			}
			returnType = method.getReturnType();
			if (!checkReturnType(returnType)) {
				continue;
			}

			mRetObjA = null;
			try {
				mRetObjA = method.invoke(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			methodName = method.getName();
			propertyName = methodName.replace("get", "");

			propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);

			if (mRetObjA != null) {
				jsonObject.put(propertyName, mRetObjA);
			}
		}

		return jsonObject;
	}

	public static void main(String[] args) {

		ArrayList<Integer> ids = new ArrayList<Integer>();

		ids.add(100);
		ids.add(100);
		ids.add(100);
		ids.add(100);
		ids.add(100);
		String ddd = ids.toString();
		System.out.println(ddd.replace("[", "(").replace("]", ")"));

//		String entryDate = "2023-05-05";
//		String month = entryDate.substring(0, 7);
//
//		double f = 5.864;
//		DecimalFormat df = new DecimalFormat("#.00");
//		System.out.println(df.format(f));
//
//		double one = 5.864;
//		BigDecimal two = new BigDecimal(one);
//		double three = two.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//		System.out.println(three);
//
//		System.out.println(toFloatNumber(5566.26564964, 2));

	}

	public static void python() {

		Process proc;
		try {
			String[] args = new String[] { "python", "d:\\py\\ebpdf2.py", "D:\\20240326 铝型材 48W.pdf" };
			proc = Runtime.getRuntime().exec(args);// 执行py文件
			// 用输入输出流来截取结果
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "utf-8"));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static String getRandomKey(int length) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int random = new Random().nextInt(allChar.length() - 1);
			buffer.append(String.valueOf(allChar.charAt(random)));
		}

		return buffer.toString();
	}

	public static String fillZero(String str, int size) {
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < (size - str.length()); i++) {
				s[i] = '0';
			}
			result = new String(s) + str;
		} else {
			result = str;
		}
		return result;
	}

	public static String fillStar(String str, int size) {
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < (size - str.length()); i++) {
				s[i] = '*';
			}
			result = new String(s) + str;
		} else {
			result = str;
		}
		return result;
	}

	public static String rightTrimStar(String str) {
		String result = fillStar(str, 4);
		return "**" + result.substring(result.length() - 4, result.length());
	}

	public static String toFloatNumber(String num) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		return nf.format(Double.parseDouble(num));
	}

	public static double toFloatNumber(Double num, int accuracy) {
		BigDecimal two = new BigDecimal(num);
		double three = two.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
		return three;
	}

	public static String getHighLine(String content) {
		return "<span style='color:blue'>" + content + "</span>";
	}

	public static String toIdsList(ArrayList<Integer> ids) {

		ids.toString();

		return "";
	}
}
