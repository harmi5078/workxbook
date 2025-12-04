package com.thx.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.PythonUtil;
import com.common.TxResponseResult;
import com.common.TxStringUtil;
import com.thx.bean.BillEntry;
import com.thx.bean.FileTarget;
import com.thx.dao.SqliteAdapter;
import com.thx.service.BillEntryService;
import com.thx.service.FileTargetService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class FileController {

	static final Logger logger = Logger.getLogger(FileController.class);

	@PostMapping("/mideyfiletype")
	@ResponseBody
	public TxResponseResult mideyFileType(@RequestBody FileTarget fileTarget) throws JSONException {

		SqliteAdapter.saveFileType(fileTarget);

		return TxResponseResult.createSucessResponse(fileTarget);
	}

	@PostMapping("/loadbefile")
	@ResponseBody
	public TxResponseResult loadBEFile(@RequestBody FileTarget fileTarget) {

		if (fileTarget != null && !TxStringUtil.isEmpty(fileTarget.getFilePath())) {
			BillEntryService.importBillEntryFromPY(fileTarget);
		}

		return TxResponseResult.createSucessResponse("");
	}

	@GetMapping("/getfiles")
	@ResponseBody
	public TxResponseResult getFileTargets(FileTarget fileTarget) throws JSONException {
		ArrayList<FileTarget> ps = SqliteAdapter.queryFileTargets(fileTarget);
		return TxResponseResult.createSucessResponse(ps);
	}

	@PostMapping("/loadbillentry")
	@ResponseBody
	public TxResponseResult loadBillentry(@RequestBody BillEntry billEntry) {

		TxResponseResult rrs = TxResponseResult.createResponse(PythonUtil.importBillentryData(billEntry.getBillFile()));

		return rrs;
	}

	@PostMapping("/uploadonlyfile")
	@ResponseBody
	public Object uploadOnlyfile(MultipartFile file) throws Exception {

		if (file != null) { // 现在有文件上传

			String fileName = file.getOriginalFilename();

			// 文件路径 位置 + 文件名
			String filePath = "D:\\TxWorks\\files\\" + fileName;

			File saveFile = new File(filePath);

			FileTarget fileTarget = new FileTarget();
			fileTarget.setName(file.getOriginalFilename());
			fileTarget.setFileName(fileName);
			fileTarget.setFilePath(filePath);
			fileTarget.setType(0);

			if (saveFile.exists()) {
				logger.error("上传文件已存在：" + saveFile);

				TxResponseResult trr = TxResponseResult.createFailedResponse(fileTarget);
				trr.setReMsg("上传文件名冲突");
				return trr;

			}

			file.transferTo(saveFile); // 文件保存

			long hashCode = System.currentTimeMillis();

			fileTarget.setHashid(TxStringUtil.getRandomKey(6) + String.valueOf(hashCode));

			if (SqliteAdapter.addFileTarget(fileTarget)) {
				// FileTargetService.importDataFromPY(fileTarget.getHashid());
			}
			TxResponseResult trr = TxResponseResult.createSucessResponse(fileTarget);
			trr.setReMsg("上传成功");
			return trr;
		} else {

			TxResponseResult trr = TxResponseResult.createFailedResponse(new FileTarget());
			trr.setReMsg("上传对象为空");
			return trr;

		}
	}

	@PostMapping("/upload")
	@ResponseBody
	public Object upload(MultipartFile file) throws Exception {

		if (file != null) { // 现在有文件上传

			String fileName = file.getOriginalFilename();

			// 文件路径 位置 + 文件名
			String filePath = "D:\\TxWorks\\files\\" + fileName;

			File saveFile = new File(filePath);
			file.transferTo(saveFile); // 文件保存

			FileTarget fileTarget = new FileTarget();
			fileTarget.setName(file.getOriginalFilename());
			fileTarget.setFileName(fileName);
			fileTarget.setFilePath(filePath);
			fileTarget.setType(0);

			long hashCode = System.currentTimeMillis();

			fileTarget.setHashid(TxStringUtil.getRandomKey(6) + String.valueOf(hashCode));

			if (SqliteAdapter.addFileTarget(fileTarget)) {
				FileTargetService.importDataFromPY(fileTarget.getHashid());
			}

			return TxResponseResult.createResponse(fileTarget);
		} else {
			return "nothing";
		}
	}

	@PostMapping("/savefiledata")
	@ResponseBody
	public Object saveFileData(@RequestBody FileTarget fileTarget) throws Exception {
		FileTargetService.importDataByFileType(fileTarget.getFid());
		return TxResponseResult.createResponse("nothing");

	}

	@PostMapping("/updbe")
	@ResponseBody
	public Object uploadBillEntry(String pfilename, MultipartFile billFile) throws Exception {

		if (billFile != null) {

			String fileName = TxStringUtil.getNowSortPlusTime() + billFile.getOriginalFilename();
			System.out.println(fileName);
//			fileName = fileName + "."
//					+ billFile.getContentType().substring(billFile.getContentType().lastIndexOf("/") + 1); // 创建文件名称
			// 文件路径 位置 + 文件名
			String filePath = "D:\\TxWork\\bills\\" + fileName;

			File saveFile = new File(filePath);
			billFile.transferTo(saveFile); // 文件保存

			FileTarget fileTarget = new FileTarget();
			fileTarget.setName("报关单-" + TxStringUtil.getNowSortPlusTime());
			fileTarget.setFileName(fileName);
			fileTarget.setFilePath(filePath);
			fileTarget.setType(1);
			SqliteAdapter.addFileTarget(fileTarget);
			return TxResponseResult.createSucessResponse(fileName);

		}

		return TxResponseResult.createFailedResponse("nothing");

	}

	@GetMapping("/dld")
	public void download(HttpServletResponse response) {
		try {
			String path = "D:\\txworks\\files\\20220427 铝合金型材和配件.pdf";
			// path是指想要下载的文件的路径
			File file = new File(path);

			// 获取文件名
			String filename = file.getName();
			// 获取文件后缀名
			// String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

			// 将文件写入输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStream fis = new BufferedInputStream(fileInputStream);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			// Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
			// attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline;
			// filename=文件名.mp3"
			// filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			// 告知浏览器文件的大小
			response.addHeader("Content-Length", "" + file.length());
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			outputStream.write(buffer);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@GetMapping("/downld")
	public void downloadFile(@RequestParam(name = "fid") String fileId, HttpServletResponse response) {

		System.out.println("fileId:" + fileId);

		FileTarget fileTarget = SqliteAdapter.queryOneFileTarget(fileId);
		try {

			String path = fileTarget.getFilePath();

			File file = new File(path);

			String filename = file.getName();
			// 获取文件后缀名
			// String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

			// 将文件写入输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStream fis = new BufferedInputStream(fileInputStream);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			// Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
			// attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline;
			// filename=文件名.mp3"
			// filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			// 告知浏览器文件的大小
			response.addHeader("Content-Length", "" + file.length());
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			outputStream.write(buffer);
			outputStream.flush();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@GetMapping("/commitdb")
	@ResponseBody
	public TxResponseResult commitDB(FileTarget fileTarget) {

		String res = PythonUtil.commitDB();

		return TxResponseResult.createSucessResponse(res);
	}

	@GetMapping("/lfd")
	@ResponseBody
	public TxResponseResult loadFileData(FileTarget fileTarget) {

		String res = PythonUtil.commitDB();

		return TxResponseResult.createSucessResponse(res);
	}

}
