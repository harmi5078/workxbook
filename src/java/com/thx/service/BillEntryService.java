package com.thx.service;

import com.common.PythonUtil;
import com.common.TxStringUtil;
import com.thx.bean.FileTarget;
import com.thx.dao.SqliteAdapter;

public class BillEntryService {

	public static void importBillEntryFromPY(FileTarget fileTarget) {
		new Thread() {

			public void run() {
				String rrs = PythonUtil.importBillentryData(fileTarget.getFilePath());

				if (TxStringUtil.isEmpty(rrs)) {
					fileTarget.setRemark("Python执行结果为空");
					fileTarget.setStatus(3);
					SqliteAdapter.saveFileTargetInfo(fileTarget);
					return;
				}

				if (!rrs.startsWith("IMPOK")) {
					fileTarget.setRemark("Python执行失败:" + rrs);
					fileTarget.setStatus(3);
					SqliteAdapter.saveFileTargetInfo(fileTarget);
					return;
				}

				String[] params = rrs.split("#");
				String bid = params[1];
				String billNo = params[2];
				String descs = params[3];

				fileTarget.setRemark("报关单编号:" + billNo);
				fileTarget.setName(descs);
				fileTarget.setStatus(2);
				SqliteAdapter.saveFileTargetInfo(fileTarget);

				SqliteAdapter.updateBillEntryFileTarget(bid, fileTarget.getFid());

			}

		}.start();
		;
	}
}
