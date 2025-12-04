package com.thx.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.common.KeyListData;
import com.common.TxResponseResult;
import com.common.TxStringUtil;
import com.thx.bean.AccCheckInfo;
import com.thx.bean.AccList;
import com.thx.bean.Account;
import com.thx.bean.BillEntry;
import com.thx.bean.Business;
import com.thx.bean.Company;
import com.thx.bean.Invoice;
import com.thx.bean.Staff;
import com.thx.dao.SqliteAdapter;

@RestController
public class BusinessController {

	static final Logger logger = Logger.getLogger(BusinessController.class);

	@PostMapping("/updateOnBussiness")
	@ResponseBody
	public TxResponseResult updateOnBussiness(@RequestBody Company company) {

		SqliteAdapter.updateOnBussiness(company);

		return TxResponseResult.createSucessResponse(company);
	}

	@PostMapping("/updateretax")
	@ResponseBody
	public TxResponseResult updateOnRetax(@RequestBody Company company) {

		SqliteAdapter.updateRetax(company);

		return TxResponseResult.createSucessResponse(company);
	}

	@PostMapping("/modifybesupplier")
	@ResponseBody
	public TxResponseResult modifyBESupplier(@RequestBody BillEntry billEntry) throws JSONException {

		SqliteAdapter.saveBillEntrySupplier(billEntry);

		return TxResponseResult.createSucessResponse(billEntry);
	}

	@GetMapping("/getbillfiledatas")
	@ResponseBody
	public TxResponseResult getBillFileDatas(AccList accList) throws JSONException {

		TxResponseResult rr = TxResponseResult.createResponse(null);

		ArrayList<AccList> billList = SqliteAdapter.queryAccBillList(accList, rr);

		rr.setReObject(billList);

		return rr;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getcheckbills")
	@ResponseBody
	public TxResponseResult getCheckBills(AccList accList) throws JSONException {

		if (TxStringUtil.isEmpty(accList.getOrderField())) {
			accList.setOrderField("acc.paytime desc ");
		}

		ArrayList<AccList> tradeList = SqliteAdapter.queryAccTradeList(accList);
		ArrayList<AccList> billList = SqliteAdapter.queryAccBillList(accList, null);

		HashMap<String, KeyListData> dataMap = new HashMap<String, KeyListData>();

		ArrayList<KeyListData> retDtaList = new ArrayList<KeyListData>();

		String payTime = "";

		double billTMyi = 0.00, billTMyo = 0.00, billTTai = 0.00, billTTao = 0.00;

		for (AccList acc : tradeList) {
			payTime = acc.getPayTime();

			KeyListData retData = dataMap.get(payTime);

			if (retData == null) {
				retData = new KeyListData();
				retData.getReportData().setKey(payTime);
				retData.setKey(payTime);
				retData.setObject(new ArrayList<AccList>());

				retDtaList.add(retData);
				dataMap.put(payTime, retData);
			}

			retData.getList().add(acc);
			String amt = acc.getPayAmt().replace(",", "");

			if (acc.getIoFlag() == 2) {
				billTMyi += Double.valueOf(amt);
				retData.getReportData().setMyiAmt(retData.getReportData().getMyiAmt() + Double.valueOf(amt));
			} else {
				billTMyo += Double.valueOf(amt);
				retData.getReportData().setMyoAmt(retData.getReportData().getMyoAmt() + Double.valueOf(amt));
			}

		}

		for (AccList acc : billList) {
			payTime = acc.getPayTime();

			KeyListData retData = dataMap.get(payTime);

			if (retData == null) {
				retData = new KeyListData();
				retData.getReportData().setKey(payTime);
				retData.setKey(payTime);
				retData.setObject(new ArrayList<AccList>());

				retDtaList.add(retData);
				dataMap.put(payTime, retData);
			}

			if (retData.getObject() == null) {
				retData.setObject(new ArrayList<AccList>());
			}

			((ArrayList<AccList>) retData.getObject()).add(acc);
			String amt = acc.getPayAmt().replace(",", "");
			if (acc.getIoFlag() == 2) {
				billTTai += Double.valueOf(amt);
				retData.getReportData().setTaiAmt(retData.getReportData().getTaiAmt() + Double.valueOf(amt));
			} else {
				billTTao += Double.valueOf(amt);
				retData.getReportData().setTaoAmt(retData.getReportData().getTaoAmt() + Double.valueOf(amt));
			}

			retData.getList().sort((a, b) -> ((AccList) a).compareTo((AccList) b));
			((ArrayList<AccList>) retData.getObject()).sort((a, b) -> ((AccList) a).compareTo((AccList) b));
		}

		for (KeyListData retData : retDtaList) {
			int myLen = ((ArrayList<AccList>) retData.getObject()).size();
			int taLen = retData.getList().size();

			if (myLen == taLen) {
				continue;
			}

			if (myLen > taLen) {

				for (int i = 1; i <= (myLen - taLen); i++) {
					retData.getList().add(new AccList());
				}
			} else {
				for (int i = 1; i <= (taLen - myLen); i++) {
					((ArrayList<AccList>) retData.getObject()).add(new AccList());
				}
			}

		}

		TxResponseResult txrs = TxResponseResult.createSucessResponse(retDtaList);
		txrs.getReportData().put("billTMyi", billTMyi);
		txrs.getReportData().put("billTMyo", billTMyo);
		txrs.getReportData().put("billTTai", billTTai);
		txrs.getReportData().put("billTTao", billTTao);

		return txrs;
	}

	@GetMapping("/getcompinvs")
	@ResponseBody
	public TxResponseResult getCompinvs(Company company) throws JSONException {
		ArrayList<Company> ps = SqliteAdapter.queryCompinvs(company);
		return TxResponseResult.createSucessResponse(ps);
	}

	@PostMapping("/savebillentry")
	@ResponseBody
	public TxResponseResult saveBillEntry(@RequestBody BillEntry billEntry) {

		SqliteAdapter.saveBillEntry(billEntry);

		return TxResponseResult.createSucessResponse(billEntry);
	}

	@PostMapping("/addbillentry")
	@ResponseBody
	public TxResponseResult addBillEntry(@RequestBody BillEntry billEntry) {

		SqliteAdapter.addBillEntry(billEntry);

		return TxResponseResult.createSucessResponse(billEntry);
	}

	@GetMapping("/getbills")
	@ResponseBody
	public TxResponseResult getBillEntrys(BillEntry billEntry) throws JSONException {
		ArrayList<BillEntry> ps = SqliteAdapter.queryBillEntrys(billEntry);
		return TxResponseResult.createSucessResponse(ps);
	}

	@GetMapping("/getminvacclist")
	@ResponseBody
	public TxResponseResult getAccInvListGroupByMonth(Invoice invoice) throws JSONException {

		ArrayList<Invoice> ps = SqliteAdapter.queryAccInvList(invoice);

		HashMap<String, KeyListData> reportData = new HashMap<String, KeyListData>();

		ArrayList<KeyListData> keyDataList = new ArrayList<KeyListData>();

		String month = "";

		for (Invoice inv : ps) {

			String entryDate = inv.getMakeDate();
			if (TxStringUtil.isEmpty(entryDate) || entryDate.length() <= 6) {
				month = "不确定月份";
			} else {
				month = entryDate.substring(0, 7);
			}

			KeyListData beList = reportData.get(month);

			if (beList == null) {
				beList = new KeyListData();
				reportData.put(month, beList);
				beList.setKey(month);
				beList.getReportData().setName(month);
				keyDataList.add(beList);

			}

			beList.getList().add(inv);
			beList.getReportData()
					.setAmt(TxStringUtil.toFloatNumber(beList.getReportData().getAmt() + inv.getTotalAmt(), 2));

		}

		keyDataList.sort((a, b) -> a.compareTo(b));

		return TxResponseResult.createSucessResponse(keyDataList);
	}

	@GetMapping("/getinvlist")
	@ResponseBody
	public TxResponseResult getInvoiceListGroupByCompany(Invoice invoice) throws JSONException {

		ArrayList<Invoice> ps = SqliteAdapter.queryInvoiceList(invoice);

		HashMap<String, KeyListData> reportData = new HashMap<String, KeyListData>();

		ArrayList<KeyListData> keyDataList = new ArrayList<KeyListData>();

		Company company = new Company();
		company.setId(invoice.getCompany());
		ArrayList<Company> companyList = SqliteAdapter.queryCompinvs(company);

		String companyName = "";

		for (Invoice inv : ps) {
			companyName = inv.getCompanyName();

			KeyListData beList = reportData.get(companyName);

			if (beList == null) {
				beList = new KeyListData();
				reportData.put(companyName, beList);
				beList.setKey(companyName);
				beList.getReportData().setName(companyName);
				keyDataList.add(beList);

				for (Company com : companyList) {
					if (com.getId() == inv.getCompany()) {
						beList.setObject(com);
						break;
					}
				}

			}

			beList.getList().add(inv);
			beList.getReportData()
					.setAmt(TxStringUtil.toFloatNumber(beList.getReportData().getAmt() + inv.getTotalAmt(), 2));

			beList.getReportData().setQuantity(beList.getReportData().getQuantity() + inv.getQuantity());
			beList.getReportData().setNums(beList.getReportData().getNums() + 1);
			beList.getReportData().setUnit(inv.getUnit());
		}

		keyDataList.sort((a, b) -> a.compareTo(b));

		return TxResponseResult.createSucessResponse(keyDataList);
	}

	@GetMapping("/getmbills")
	@ResponseBody
	public TxResponseResult getBillEntrysGroupByMonth(BillEntry billEntry) throws JSONException {

		ArrayList<BillEntry> ps = SqliteAdapter.queryBillEntrys(billEntry);

		HashMap<String, KeyListData> reportData = new HashMap<String, KeyListData>();

		ArrayList<KeyListData> keyDataList = new ArrayList<KeyListData>();

		String month = "";

		double totalBEAmt = 0.0;
		int bes = 0;

		for (BillEntry be : ps) {

			String entryDate = be.getEntryDate();
			if (TxStringUtil.isEmpty(entryDate) || entryDate.length() <= 6) {
				month = "不确定月份";
			} else {
				month = entryDate.substring(0, 7);
			}

			totalBEAmt += be.getTotalamt();
			bes += 1;

			KeyListData beList = reportData.get(month);
			if (beList == null) {
				beList = new KeyListData();
				reportData.put(month, beList);
				beList.setKey(month);
				keyDataList.add(beList);

			}

			beList.getList().add(be);
			beList.getReportData()
					.setAmt(TxStringUtil.toFloatNumber(beList.getReportData().getAmt() + be.getTotalamt(), 2));

			beList.getReportData().setQuantity(beList.getReportData().getQuantity() + 1);

		}

		if (billEntry.getOrderField().equals("2")) {
			keyDataList.sort((a, b) -> a.compareTo(b));
		} else {
			keyDataList.sort((a, b) -> b.compareTo(a));
		}

		TxResponseResult txrs = TxResponseResult.createSucessResponse(keyDataList);

		txrs.getReportData().put("amt", totalBEAmt);
		txrs.getReportData().put("bes", bes);

		return txrs;
	}

	@GetMapping("/getcomplist")
	@ResponseBody
	public TxResponseResult getComplist(Company company) throws JSONException {

		ArrayList<Company> ps = null;

		int queryType = company.getQueryType();

		if (queryType == 2) {
			ps = SqliteAdapter.queryCompanysFromInvoice(company);
		} else if (queryType == 3) {
			ps = SqliteAdapter.queryCompanysFromRetax(company);
		} else {
			ps = SqliteAdapter.queryCompanys(company);
		}

		// ps.sort((a, b) -> a.compareTo(b));

		return TxResponseResult.createSucessResponse(ps);
	}

	@PostMapping("/addcompany")
	@ResponseBody
	public TxResponseResult addCompany(@RequestBody Company company) {

		SqliteAdapter.addCompany(company);

		return TxResponseResult.createSucessResponse(company);
	}

	@PostMapping("/savecompany")
	@ResponseBody
	public TxResponseResult saveCompany(@RequestBody Company company) {

		SqliteAdapter.saveCompany(company);

		return TxResponseResult.createSucessResponse(company);
	}

	@PostMapping("/addaccount")
	@ResponseBody
	public TxResponseResult addAccount(@RequestBody Account account) {

		SqliteAdapter.addAccount(account);

		return TxResponseResult.createSucessResponse(account);
	}

	@GetMapping("/getaccounts")
	@ResponseBody
	public TxResponseResult getAccounts() throws JSONException {
		ArrayList<Account> ps = SqliteAdapter.queryAccounts();
		return TxResponseResult.createSucessResponse(ps);
	}

	@PostMapping("/saveaccount")
	@ResponseBody
	public TxResponseResult saveAccount(@RequestBody Account account) {

		SqliteAdapter.saveAccount(account);

		return TxResponseResult.createSucessResponse(account);
	}

	@PostMapping("/updateIsPayment")
	@ResponseBody
	public TxResponseResult updateIsPayment(@RequestBody Account account) {

		SqliteAdapter.updateIsPayment(account);

		return TxResponseResult.createSucessResponse(account);
	}

	@GetMapping("/gettaxopaccs")
	public TxResponseResult getRetaxOPAccounts() {

		ArrayList<Account> rrs = SqliteAdapter.queryTxAccounts();
		ArrayList<Account> rrs2 = SqliteAdapter.queryRetaxPayAccounts();

		ArrayList<Account> accsFee = SqliteAdapter.queryRetaxAccounts(9);
		ArrayList<Account> accsTax = SqliteAdapter.queryRetaxAccounts(8);

		@SuppressWarnings("rawtypes")
		HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();
		map.put("owers", rrs);
		map.put("pays", rrs2);
		map.put("fees", accsFee);
		map.put("taxs", accsTax);

		return TxResponseResult.createSucessResponse(map);
	}

	@GetMapping("/getopaccs")
	public TxResponseResult getOPAccounts() {

		ArrayList<Account> rrs = SqliteAdapter.queryTxAccounts();
		ArrayList<Account> rrs2 = SqliteAdapter.queryPayAccounts();

		ArrayList<Company> rrs3 = SqliteAdapter.queryPayAccountsFromTrade();

		@SuppressWarnings("rawtypes")
		HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();
		map.put("owers", rrs);
		map.put("pays", rrs2);
		map.put("coms", rrs3);

		return TxResponseResult.createSucessResponse(map);
	}

	@GetMapping("/getbusaccs")
	@ResponseBody
	public TxResponseResult queryBusinessAccounts(Business business) {

		TxResponseResult rrs = SqliteAdapter.queryAccountsByBusiness(business.getBusinessId());
		return rrs;
	}

	@GetMapping("/getbusiness")
	@ResponseBody
	public TxResponseResult queryBusiness(Business business) {

		ArrayList<Business> array = SqliteAdapter.queryBusiness(business);
		return TxResponseResult.createSucessResponse(array);
	}

	@PostMapping("/addbusiness")
	@ResponseBody
	public TxResponseResult addBusiness(@RequestBody Business business) {

		SqliteAdapter.addBusiness(business);

		return TxResponseResult.createSucessResponse(business);
	}

	@PostMapping("/savebusiness")
	@ResponseBody
	public TxResponseResult savebusiness(@RequestBody Business business) {

		SqliteAdapter.saveBusiness(business);

		return TxResponseResult.createSucessResponse(business);
	}

	@PostMapping("/addretax")
	@ResponseBody
	public TxResponseResult addRetax(@RequestBody AccList accList) {

		SqliteAdapter.addAccRetax(accList);

		return TxResponseResult.createSucessResponse(accList);
	}

	@PostMapping("/addtrade")
	@ResponseBody
	public TxResponseResult addTrade(@RequestBody AccList accTrade) {

		SqliteAdapter.addAccTrade(accTrade);

		return TxResponseResult.createSucessResponse(accTrade);
	}

	@PostMapping("/saveretax")
	@ResponseBody
	public TxResponseResult saveRetax(@RequestBody AccList accTrade) {

		SqliteAdapter.saveAccRetax(accTrade);

		return TxResponseResult.createSucessResponse(accTrade);
	}

	@PostMapping("/savetrade")
	@ResponseBody
	public TxResponseResult saveTrade(@RequestBody AccList accTrade) {

		SqliteAdapter.saveAccTrade(accTrade);

		return TxResponseResult.createSucessResponse(accTrade);
	}

	@GetMapping("/getaccchecks")
	@ResponseBody
	public TxResponseResult getAccCheckList(AccCheckInfo accCheck) {

		TxResponseResult rr = TxResponseResult.createSucessResponse(null);

		ArrayList<AccCheckInfo> array = SqliteAdapter.queryAccCheckList(accCheck, rr);
		rr.setReObject(array);

		return rr;
	}

	@GetMapping("/getaccretaxlist")
	@ResponseBody
	public TxResponseResult getAccRetaxList(AccList accList) {

		ArrayList<AccList> array = SqliteAdapter.queryAccRetaxList(accList);
		TxResponseResult rr = TxResponseResult.createSucessResponse(array);

		rr.setPageable(accList);

		SqliteAdapter.counAccRetaxList(accList, rr);

//		SqliteAdapter.counAccTradeList(null, rr);
//		rr.setPageable(accList);

		return rr;
	}

	@GetMapping("/getacclist")
	@ResponseBody
	public TxResponseResult getAccTradeList(AccList accTrade) {

		ArrayList<AccList> array = SqliteAdapter.queryAccTradeList(accTrade);
		TxResponseResult rr = TxResponseResult.createSucessResponse(array);

		SqliteAdapter.counAccTradeList(accTrade, rr);
		SqliteAdapter.counAccTradeList(null, rr);
		rr.setPageable(accTrade);

		return rr;
	}

	@GetMapping("/getstaff")
	@ResponseBody
	public TxResponseResult getStaff() {
		ArrayList<Staff> array = SqliteAdapter.queryStaff();
		return TxResponseResult.createSucessResponse(array);
	}

	@PostMapping("/addstaff")
	@ResponseBody
	public TxResponseResult addStaff(@RequestBody Staff staff) {

		SqliteAdapter.addStaff(staff);

		return TxResponseResult.createSucessResponse(staff);
	}
}
