package com.thx.service;

import com.common.PythonUtil;

public class FileTargetService {

	public static void importDataFromPY(String filehashid) {
		new Thread() {

			public void run() {
				PythonUtil.importData(filehashid);
			}
		}.start();
	}

	public static void importDataByFileType(int fid) {
		new Thread() {

			public void run() {
				PythonUtil.importDataByFileType(fid);
			}
		}.start();
	}

}
