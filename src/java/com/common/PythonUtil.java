package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.thx.dao.SqliteAdapter;

public class PythonUtil {

	static final Logger logger = Logger.getLogger(PythonUtil.class);

	public static void main(String[] args) {
		importBillentryData("D:\\E20240001198965681.pdf");
	}

	public static void exeExportTradeList() {

		try {
			JSONObject jo = new JSONObject(python("D:\\TxWork\\py\\exptrades.py", null));
			System.out.println(jo.get("result"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String importBillentryData(String billfile) {

		String jjd = python("D:\\TxWork\\py\\ebpdf.py", billfile);

		return jjd;
	}

	public static String importData(String filehashid) {

		String jjd = python("D:\\TxWorks\\py\\tx.py", filehashid);

		return jjd;
	}

	public static String importDataByFileType(int fileId) {

		String jjd = python("D:\\TxWorks\\py\\tx_filedats.py", String.valueOf(fileId));

		return jjd;
	}

	public static String python(String pyFile, String params) {
		String result = null;
		Process proc;
		try {

			String[] args = new String[] { "python", pyFile, params };
			if (TxStringUtil.isEmpty(params)) {
				args = new String[] { "python", pyFile };
			}

			proc = Runtime.getRuntime().exec(args);
			// 用输入输出流来截取结果
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "utf-8"));
			result = String.valueOf(in.readLine());
			logger.error(result);

			in.close();
			proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);

		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e);
		}

		return result;
	}

	public static String commitDB() {

		String res = "";

		try {
			SqliteAdapter.closeConn();
			res = "dbconn closed.\n";
		} catch (Exception ex) {
			res = "dbconn close failed. " + ex;
		}
		res = res + python("D:\\TxWorks\\py\\svn-commit-db.py", null);
		return res;

	}

	public static String updateDB() {

		String res = "";

		try {
			SqliteAdapter.closeConn();
			res = "dbconn closed.\n";
		} catch (Exception ex) {
			res = "dbconn close failed. " + ex;
		}

		res = res + python("D:\\TxWorks\\py\\svn-update-db.py", null);

		return res;

	}

}
