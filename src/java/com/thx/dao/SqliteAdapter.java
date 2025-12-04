package com.thx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.common.PythonUtil;
import com.common.TxResponseResult;
import com.common.TxStringUtil;
import com.thx.bean.AccCheckInfo;
import com.thx.bean.AccList;
import com.thx.bean.Account;
import com.thx.bean.BillEntry;
import com.thx.bean.BillGoods;
import com.thx.bean.Business;
import com.thx.bean.Company;
import com.thx.bean.FileTarget;
import com.thx.bean.Invoice;
import com.thx.bean.Staff;

public class SqliteAdapter {

	private static Connection connection = null;
	private static String DBPath = "";

	static final Logger logger = Logger.getLogger(SqliteAdapter.class);

	private static String getDBPath() {
		if (TxStringUtil.isEmpty(DBPath)) {
			DBPath = ("D:\\txworks\\db\\db.db");
		}

		return DBPath;
	}

	public static void closeConn() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	private static void initConn() throws Exception {

		if (connection == null || connection.isClosed()) {

			PythonUtil.updateDB();

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + getDBPath());
			connection.setAutoCommit(true);
		}

	}

	public static ArrayList<Company> queryCompanys(Company pageale) {
		ArrayList<Company> dList = new ArrayList<Company>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select   [cid]  ,   [name]  ,fullname,  nation, retax, [Type]  ,  [addr]  ,   [ContractName]  ,   [contractAddr]  , "
					+ "  [ContractInfo]  , onbussiness, [Remark] from TCompany where 0=0 ";

			if (pageale.getType() > 0) {
				sql = sql + " and type=" + pageale.getType();
			}

			if (!TxStringUtil.isEmpty(pageale.getFullName())) {
				sql = sql + " and fullname like '%" + pageale.getFullName() + "%'";
			}

			sql = sql + " order by type,fullname";

			ResultSet rs = stmt.executeQuery(sql);

			Company company;

			while (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("cid"));
				company.setName(rs.getString("name"));
				company.setFullName(rs.getString("fullname"));
				company.setNation(rs.getString("nation"));
				company.setAddr(rs.getString("addr"));
				company.setContractName(rs.getString("ContractName"));
				company.setContractAddr(rs.getString("ContractAddr"));
				company.setContractInfo(rs.getString("ContractInfo"));
				company.setRemark(rs.getString("Remark"));
				company.setType(rs.getInt("Type"));
				company.setRetax(rs.getInt("retax"));
				company.setOnbussiness(rs.getInt("onbussiness"));

				dList.add(company);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static void addCompany(Company company) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "INSERT INTO TCompany (name,fullname,nation,addr , ContractName, "
					+ " contractAddr,ContractInfo ,type,Remark) " + "VALUES ('" + company.getName() + "','"
					+ company.getFullName() + "','" + company.getNation() + "','" + company.getAddr() + "','"
					+ company.getContractName() + "','" + company.getContractAddr() + "','" + company.getContractInfo()
					+ "','" + company.getType() + "','" + company.getRemark() + "');";
			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveCompany(Company company) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "update TCompany set name = '" + company.getName() + "'," + "fullname = '"
					+ company.getFullName() + "'," + "nation = '" + company.getNation() + "'," + "addr = '"
					+ company.getAddr() + "' , " + "ContractName = '" + company.getContractName() + "',  "
					+ "ContractInfo='" + company.getContractInfo() + "' , type='" + company.getType() + "',"
					+ "Remark = '" + company.getRemark() + "' where cid=" + company.getId() + " ";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Business> queryBusiness(Business param) {

		ArrayList<Business> array = new ArrayList<Business>();

		Business business = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select bid,cida, cidb, bname, descs ,  amts , status, ca.name companyName, cb.name partnerName,bu.remark "
					+ "  from tBusiness bu inner join tcompany  ca on ca.cid =  bu.cida "
					+ " inner join tcompany  cb on cb.cid =  bu.cidb";

			if (param != null && param.getRetax() > 0) {
				sql = sql + " where bu.retax = " + param.getRetax();
			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				business = new Business();
				business.setAmts(rs.getString("amts"));
				business.setBusinessId(rs.getInt("bid"));
				business.setCompanyId(rs.getInt("cida"));
				business.setCompanyName(rs.getString("companyName"));
				business.setDescs(rs.getString("descs"));
				business.setName(rs.getString("bname"));
				business.setPartnerId(rs.getInt("cidb"));
				business.setPartnerName(rs.getString("partnerName"));
				business.setRemark(rs.getString("remark"));
				business.setStatus(rs.getString("status"));

				array.add(business);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void addBusiness(Business business) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "INSERT INTO  tBusiness(cida,cidb,bname,Descs,Amts,status,Remark,Creattime)" + " values ('"
					+ business.getCompanyId() + "'," + business.getPartnerId() + ",'" + business.getName() + "','"
					+ business.getDescs() + "','" + business.getAmts() + "','" + business.getStatus() + "','"
					+ business.getRemark() + "','" + time + "') ";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveBusiness(Business business) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "update tBusiness set bname = '" + business.getName() + "'," + "cida = '"
					+ business.getCompanyId() + "'," + "cidb = '" + business.getPartnerId() + "'," + "Descs = '"
					+ business.getDescs() + "' , " + "Amts = '" + business.getAmts() + "',  " + "status = '"
					+ business.getStatus() + "',remark='" + business.getRemark() + "' where bid="
					+ business.getBusinessId();

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveAccount(Account account) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  TAccountInfos set  cid = '" + account.getCompanyId() + "'," + "AccName = '"
					+ account.getAccName() + "'," + "AccNo = '" + account.getAccNo() + "', Bank = '" + account.getBank()
					+ "'" + " , isComp = '" + account.getIsCompany() + "',  status='" + account.getStatus()
					+ "', Remark = '" + account.getRemark() + "', alias='" + account.getAlias() + "' where aid ='"
					+ account.getAccId() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Account> queryAccounts() {

		ArrayList<Account> array = new ArrayList<Account>();

		Account account = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select acc.aid, acc.cid companyId, acc.AccName, acc.AccNo,acc.Bank, "
					+ "acc.status,acc.remark,ifnull(acc.isPayment,0) isPayment,acc.isComp,ifnull(acc.alias,'默认') alias,ca.name as companyName from TAccountInfos acc "
					+ "inner join tcompany  ca on ca.cid =  acc.cid where ca.onbussiness=1  order by ca.name ");

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));
				account.setAlias(rs.getString("alias"));
				account.setIsPayment(rs.getInt("isPayment"));

				array.add(account);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void addAccTrade(AccList accTrade) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "INSERT INTO txacctradelist (bid,aido,aidp,Amt ,ioFlag, paytype,  payTime, Remark,Createtime) "
					+ "VALUES ('" + accTrade.getBusinessId() + "','" + accTrade.getOwerAccount() + "','"
					+ accTrade.getPayAccount() + "','" + accTrade.getPayAmt() + "'," + accTrade.getIoFlag() + ",'"
					+ accTrade.getPayType() + "','" + accTrade.getPayTime() + "','" + accTrade.getRemark() + "','"
					+ time + "');";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addAccount(Account account) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "INSERT INTO TAccountInfos (cid,AccName,AccNo,Bank , isComp,  status, Remark) " + "VALUES ('"
					+ account.getCompanyId() + "','" + account.getAccName() + "','" + account.getAccNo() + "','"
					+ account.getBank() + "','" + account.getIsCompany() + "','" + account.getStatus() + "','"
					+ account.getRemark() + "');";
			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TxResponseResult queryAccountsByBusiness(int businessId) {

		TxResponseResult rrs = new TxResponseResult();
		ArrayList<Account> array = new ArrayList<Account>();
		ArrayList<Account> array2 = new ArrayList<Account>();

		Statement stmt = null;
		try {

			Account account = null;

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select  bu.bid businessId, bu.bname ,ca.cid companyId, ca.name companyName,  acc.aid , "
							+ "acc.AccName, acc.AccNo, acc.Bank, acc.status, acc.remark,"
							+ "acc.isComp  from TAccountInfos acc  " + "inner join tBusiness bu on bu.cida = ca.cid "
							+ "inner join tcompany  ca on ca.cid =  acc.cid  " + " where bu.bid =" + businessId);

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));

				array.add(account);
			}
			rs.close();

			rs = stmt.executeQuery(
					"select  bu.bid businessId, bu.bname ,ca.cid companyId, ca.name companyName,  acc.aid , "
							+ "acc.AccName, acc.AccNo, acc.Bank, acc.status, acc.remark,"
							+ "acc.isComp  from TAccountInfos acc  " + "inner join tBusiness bu on bu.cidb = ca.cid "
							+ "inner join tcompany  ca on ca.cid =  acc.cid  " + " where bu.bid =" + businessId);

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));

				array2.add(account);
			}
			rs.close();

			stmt.close();

			HashMap<String, ArrayList<Account>> map = new HashMap<String, ArrayList<Account>>();
			map.put("pays", array);
			map.put("recs", array2);

			rrs.setReObject(map);

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return rrs;
	}

	public static ArrayList<AccList> queryAccTradeList(AccList accTrade) {

		ArrayList<AccList> array = new ArrayList<AccList>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sqlCount = "select count(*) count from txacctradelist acc "
					+ "inner join tbusiness bu on bu.bid = acc.bid "
					+ "inner join taccountinfos payacc on payacc.aid = acc.aidp "
					+ "inner join taccountinfos oweracc on oweracc.aid = acc.aido "
					+ "inner join tcompany paycom on payacc.cid = paycom.cid ";

			String sql = "select acc.tid,bu.bid businessId,bu.bname businessName, "
					+ "paycom.name payCompany,ifnull(payacc.alias,'默认') payAlias,"
					+ "payacc.AccName payAccName, payacc.AccNo payAccInfo, payacc.cid payCompanyId ,"
					+ "payacc.aid payAccId, " + "oweracc.AccNo owerAccInfo, ifnull(oweracc.alias,'默认') owerAlias,   "
					+ "oweracc.AccName owerAccName, oweracc.aid owerAccId, "
					+ "acc.Amt payAmt, acc.payTime,acc.payType,acc.ioflag,acc.remark ,ifnull(acc.ioflag,1) ioflag "
					+ "from txacctradelist acc " + "inner join tbusiness bu on bu.bid = acc.bid "
					+ "inner join taccountinfos payacc on payacc.aid = acc.aidp "
					+ "inner join taccountinfos oweracc on oweracc.aid = acc.aido "
					+ "inner join tcompany paycom on payacc.cid = paycom.cid";

			String queryCondition = " where payacc.cid = paycom.cid ";
//			if (accTrade.getOwerAccount() != 0) {
//				queryCondition = queryCondition + " and acc.aido=" + accTrade.getOwerAccount();
//			}

//			if (accTrade.getIoFlag() != 0) {
//				queryCondition = queryCondition + " and acc.ioflag=" + accTrade.getIoFlag();
//			}

			if (!TxStringUtil.isEmpty(accTrade.getRemark())) {
				queryCondition = queryCondition + " and acc.remark like '%" + accTrade.getRemark() + "%'";
			}

			if (!TxStringUtil.isEmpty(accTrade.getPayCompany())) {
				queryCondition = queryCondition + " and paycom.name like '%" + accTrade.getPayCompany() + "%'";
			}

			if (!TxStringUtil.isEmpty(accTrade.getStartTime())) {
				queryCondition = queryCondition + " and acc.paytime >= '" + accTrade.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(accTrade.getEndTime())) {
				queryCondition = queryCondition + " and acc.paytime <= '" + accTrade.getEndTime() + "'";
			}

//			if (accTrade.getPayCompanyId() != 0) {
//				queryCondition = queryCondition + " and paycom.cid=" + accTrade.getPayCompanyId();
//			}

			sql = sql + queryCondition;

			if (!TxStringUtil.isEmpty(accTrade.getOrderField())) {
				sql = sql + " order by " + accTrade.getOrderField();
			}

			sqlCount = sqlCount + queryCondition;

			ResultSet rs = stmt.executeQuery(sql);

			DecimalFormat dd = new DecimalFormat("#.00");

			AccList trade = null;
			while (rs.next()) {
				trade = new AccList();

				trade.setTid(rs.getInt("tid"));
				trade.setBusinessId(rs.getInt("businessId"));
				trade.setBusinessName(rs.getString("businessName"));
				trade.setPayAccInfo(rs.getString("payAccInfo"));
				trade.setPayAccName(rs.getString("payAccName"));
				trade.setPayAccount(rs.getInt("payAccId"));
				trade.setPayAmt(dd.format(rs.getDouble("payAmt")));
				trade.setPayCompany(rs.getString("payCompany"));
				trade.setPayTime(rs.getString("payTime"));
				trade.setPayType(rs.getInt("payType"));
				trade.setOwerAccInfo(rs.getString("owerAccInfo"));
				trade.setOwerAccount(rs.getInt("owerAccId"));
				trade.setOwerAccName(rs.getString("owerAccName"));
				trade.setPayAlias(rs.getString("payAlias"));
				trade.setOwerAlias(rs.getString("owerAlias"));
				trade.setRemark(rs.getString("remark"));
				trade.setIoFlag(rs.getInt("ioflag"));

				array.add(trade);
			}
			rs.close();
			stmt.close();

			ResultSet rsCount = stmt.executeQuery(sqlCount);
			int count = 0;
			while (rsCount.next()) {
				count = rsCount.getInt("count");
			}

			rsCount.close();
			stmt.close();

			accTrade.setRowCount(count);

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static ArrayList<Staff> queryStaff() {

		ArrayList<Staff> array = new ArrayList<Staff>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(" select  con.tid   , con.cid   ,  com.name company, "
					+ "   con.name,con.addr  ,   con.phone  ,   con.email  ,   con.wxid  ,  "
					+ "  con.Status  ,   con.Remark from TContactors con "
					+ "  inner join tcompany com on con.cid = com.cid  ");

			Staff staff = null;
			while (rs.next()) {
				staff = new Staff();
				staff.setTid(rs.getInt("tid"));
				staff.setCid(rs.getInt("cid"));
				staff.setCompany(rs.getString("company"));
				staff.setEmail(rs.getString("email"));
				staff.setName(rs.getString("name"));
				staff.setPhone(rs.getString("phone"));
				staff.setWxid(rs.getString("wxid"));
				staff.setRemark(rs.getString("Remark"));
				array.add(staff);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void addStaff(Staff staff) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "INSERT INTO TContactors (cid,name,addr,phone , email,  wxid, Remark) " + "VALUES ('"
					+ staff.getCid() + "','" + staff.getName() + "','" + staff.getAddr() + "','" + staff.getPhone()
					+ "','" + staff.getEmail() + "','" + staff.getWxid() + "','" + staff.getRemark() + "');";
			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Account> queryTxAccounts() {

		ArrayList<Account> array = new ArrayList<Account>();

		Account account = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select acc.aid, acc.cid companyId, acc.AccName, acc.AccNo,acc.Bank, "
					+ "acc.status,acc.remark,acc.isComp,ifnull(acc.alias,'默认') alias,ca.name as companyName from TAccountInfos acc "
					+ "inner join tcompany  ca on ca.cid =  acc.cid where  ca.cid = 1  ");

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));
				account.setAlias(rs.getString("alias"));

				array.add(account);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static ArrayList<Account> queryPayAccounts() {
		ArrayList<Account> array = new ArrayList<Account>();

		Account account = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("select acc.aid, acc.isPayment, acc.cid companyId, acc.AccName, acc.AccNo,acc.Bank, "
							+ " acc.status,acc.remark,acc.isComp,ifnull(acc.alias,'默认') alias,ca.name as companyName "
							+ " from TAccountInfos acc  inner join tcompany  ca on ca.cid =  acc.cid "
							+ " where ca.cid > 1 and acc.isPayment=1  order by acc.seqIndex,ca.name ");

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));
				account.setAlias(rs.getString("alias"));
				account.setIsPayment(rs.getInt("isPayment"));

				array.add(account);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void counAccTradeList(AccList accTrade, TxResponseResult rr) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sqlCount = "select acc.ioflag, sum(acc.Amt)  payAmt  from txacctradelist acc "
					+ "inner join tbusiness bu on bu.bid = acc.bid "
					+ "inner join taccountinfos payacc on payacc.aid = acc.aidp "
					+ "inner join taccountinfos oweracc on oweracc.aid = acc.aido "
					+ "inner join tcompany paycom on payacc.cid = paycom.cid ";

			String queryCondition = " where payacc.cid = paycom.cid ";

			String countMark = "";
			if (accTrade != null) {
				if (accTrade.getOwerAccount() != 0) {
					queryCondition = queryCondition + " and acc.aido=" + accTrade.getOwerAccount();
				}

				if (accTrade.getIoFlag() != 0) {
					queryCondition = queryCondition + " and acc.ioflag=" + accTrade.getIoFlag();
				}

				if (!TxStringUtil.isEmpty(accTrade.getRemark())) {
					queryCondition = queryCondition + " and acc.remark like '%" + accTrade.getRemark() + "%'";
				}

				if (!TxStringUtil.isEmpty(accTrade.getStartTime())) {
					queryCondition = queryCondition + " and acc.paytime >= '" + accTrade.getStartTime() + "'";
				}

				if (!TxStringUtil.isEmpty(accTrade.getEndTime())) {
					queryCondition = queryCondition + " and acc.paytime <= '" + accTrade.getEndTime() + "'";
				}

				if (accTrade.getPayCompanyId() != 0) {
					queryCondition = queryCondition + " and paycom.cid=" + accTrade.getPayCompanyId();
				}

				countMark = "T";
			}

			sqlCount = sqlCount + queryCondition + " group by acc.ioflag ";

			ResultSet rsCount = stmt.executeQuery(sqlCount);

			while (rsCount.next()) {
				rr.addReportData(countMark + rsCount.getString("ioflag"), rsCount.getDouble("payAmt"));
			}

			rsCount.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

	}

	public static void counAccRetaxList(AccList accTrade, TxResponseResult rr) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sqlCount = "select acc.type , sum(acc.Amt)  payAmt  from txaccretaxlist acc "
					+ "inner join tbusiness bu on bu.bid = acc.bid "
					+ "inner join taccountinfos payacc on payacc.aid = acc.aidp "
					+ "inner join taccountinfos oweracc on oweracc.aid = acc.aido "
					+ "inner join tcompany paycom on payacc.cid = paycom.cid ";

			String queryCondition = " where payacc.cid = paycom.cid ";

			String countMark = "";
			if (accTrade != null) {

				if (!TxStringUtil.isEmpty(accTrade.getPayCompany())) {
					queryCondition = queryCondition + " and paycom.name like '%" + accTrade.getPayCompany() + "%'";
				}

				if (!TxStringUtil.isEmpty(accTrade.getRemark())) {
					queryCondition = queryCondition + " and acc.remark like '%" + accTrade.getRemark() + "%'";
				}

				if (!TxStringUtil.isEmpty(accTrade.getStartTime())) {
					queryCondition = queryCondition + " and acc.paytime >= '" + accTrade.getStartTime() + "'";
				}

				if (!TxStringUtil.isEmpty(accTrade.getEndTime())) {
					queryCondition = queryCondition + " and acc.paytime <= '" + accTrade.getEndTime() + "'";
				}

				countMark = "T";
			}

			sqlCount = sqlCount + queryCondition + " group by acc.type ";

			ResultSet rsCount = stmt.executeQuery(sqlCount);

			while (rsCount.next()) {
				rr.addReportData(countMark + rsCount.getString("type"), rsCount.getDouble("payAmt"));
			}

			rsCount.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

	}

	public static void saveAccTrade(AccList accTrade) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

//			bid] INTEGER NOT NULL, 
//			  [aido] INTEGER NOT NULL, 
//			  [aidp] INTEGER NOT NULL, 
//			  [Amt] Double NOT NULL, 
//			  [descs] Varchar, 
//			  [paytype] Int, 
//			  [ioflag] INT DEFAULT 1, 
//			  [type] Varchar, 
//			  [payTime] VARCHAR, 
//			  [Status] Int, 
//			  [Remark] Varchar, 
//			  
			String sql = " update  txacctradelist set  bid = '" + accTrade.getBusinessId() + "'," + "aido = '"
					+ accTrade.getOwerAccount() + "'," + "aidp = '" + accTrade.getPayAccount() + "', Amt = '"
					+ accTrade.getPayAmt() + "'" + " , Remark = '" + accTrade.getRemark() + "',  paytype='"
					+ accTrade.getPayType() + "', ioflag = '" + accTrade.getIoFlag() + "', payTime='"
					+ accTrade.getPayTime() + "' where tid ='" + accTrade.getTid() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addBillEntry(BillEntry billEntry) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();
			billEntry.setCreateTime(time);

			String sql = "INSERT INTO txbillentry (container,totalamt,goods,taxtype,truckDate,supplier,shortdesc,billno,entrydate,deliver , deliverfee,  Createtime, Remark) "
					+ "VALUES ('" + billEntry.getContainer() + "','" + billEntry.getTotalamt() + "','"
					+ billEntry.getGoods() + "','" + billEntry.getTaxtype() + "','" + billEntry.getTruckDate() + "','"
					+ billEntry.getSupplier() + "','" + billEntry.getShortDesc() + "','" + billEntry.getBillNo() + "','"
					+ billEntry.getEntryDate() + "','" + billEntry.getDeliver() + "','" + billEntry.getDeliverFee()
					+ "','" + billEntry.getCreateTime() + "','" + billEntry.getRemark() + "');";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<BillEntry> queryBillEntrys(BillEntry pageable) {
		ArrayList<BillEntry> array = new ArrayList<BillEntry>();

		BillEntry billEntry = null;

		Statement stmt = null;
		try {

			initConn();

			String sql = "select bill.bid,bill.shortdesc,bill.billno , "
					+ "bill.entrydate,ifnull(bill.deliver,0) deliver,bill.deliverfee,bill.Remark ,"
					+ "bill.container,bill.totalweight,bill.totalamt,bill.reqDate,bill.voyage,bill.ladingNo,bill.loadPort,"
					+ "bill.tripAgent,ifnull(com3.name,'') tripAgentName,bill.amount,bill.descs,"
					+ "bill.goods,bill.taxtype,bill.truckDate,ifnull(bill.supplier,0) supplier, ifnull(com.name,'') deliverName ,"
					+ " ifnull(com2.fullname,'') supplierName " + "from txbillentry bill "
					+ "left join  tcompany com on bill.deliver = com.cid  "
					+ "left join  tcompany com2 on bill.supplier = com2.cid  "
					+ "left join  tcompany com3 on bill.tripAgent = com3.cid where bill.deliver = com.cid  ";

			if (!TxStringUtil.isEmpty(pageable.getGoods())) {
				sql = sql + " and bill.goods like '%" + pageable.getGoods() + "%'";
			}

			if (!TxStringUtil.isEmpty(pageable.getSupplierName())) {
				sql = sql + " and com2.fullname like '%" + pageable.getSupplierName() + "%'";
			}

			if (!TxStringUtil.isEmpty(pageable.getStartTime())) {
				sql = sql + " and bill.entrydate >= '" + pageable.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getEndTime())) {
				sql = sql + " and bill.entrydate <= '" + pageable.getEndTime() + "'";
			}

			sql = sql + " order by bill.entrydate desc";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				billEntry = new BillEntry();

				billEntry.setDescs(rs.getString("descs"));
				billEntry.setVoyage(rs.getString("voyage"));
				billEntry.setTripAgent(rs.getString("tripAgentName"));
				billEntry.setTotalWeight(rs.getString("totalweight"));

				billEntry.setLoadPort(rs.getString("loadPort"));
				billEntry.setBillId(rs.getInt("bid"));
				billEntry.setBillNo(rs.getString("billno"));
				billEntry.setDeliver(rs.getInt("deliver"));
				billEntry.setDeliverFee(rs.getDouble("deliverfee"));
				billEntry.setEntryDate(rs.getString("entrydate"));
				billEntry.setRemark(rs.getString("remark"));
				billEntry.setDeliverName(rs.getString("deliverName"));
				billEntry.setSupplierName(rs.getString("supplierName"));
				billEntry.setShortDesc(rs.getString("shortdesc"));
				billEntry.setContainer(rs.getInt("container"));
				billEntry.setGoods(rs.getString("goods"));
				billEntry.setSupplier(rs.getInt("supplier"));
				billEntry.setTaxtype(rs.getInt("taxtype"));
				billEntry.setTotalamt(rs.getDouble("totalamt"));
				billEntry.setTruckDate(rs.getString("truckDate"));

				billEntry.setReqDate(rs.getString("reqDate"));
				billEntry.setAmount(rs.getString("amount"));

				billEntry.setLading(rs.getString("ladingNo"));
				array.add(billEntry);
			}
			rs.close();

			sql = "select  gid  ,bid  ,invoicid  ,goods  ,hscode  "
					+ ",weight  ,units  ,billquantity  ,billunits  ,billprice  "
					+ ",billamt  ,payamt  ,invoiceamt  ,taxAddr  ,type  "
					+ ",Status  ,Remark  ,Createtime from txbillgoods";

			BillGoods goods = null;
			ResultSet rss = stmt.executeQuery(sql);
			while (rss.next()) {
				goods = new BillGoods();

				int billId = rss.getInt("bid");

				goods.setBillId(billId);
				goods.setBillAmt(rss.getDouble("billamt"));
				goods.setBillQuantity(rss.getDouble("billquantity"));
				goods.setBillUnits(rss.getString("billunits"));
				goods.setBillPrice(rss.getDouble("billprice"));
				goods.setGid(rss.getInt("gid"));
				goods.setGoods(rss.getString("goods"));
				goods.setHscode(rss.getString("hscode"));
				goods.setInvoiceAmt(rss.getDouble("invoiceamt"));

				goods.setInvoicId(rss.getInt("invoicid"));
				goods.setPayAmt(rss.getDouble("payamt"));
				goods.setTaxAddr(rss.getString("taxAddr"));
				goods.setUnits(rss.getString("units"));
				goods.setWeight(rss.getDouble("weight"));

				for (BillEntry bill : array) {
					if (bill.getBillId() == billId) {

						goods.setBillNo(bill.getBillNo());
						goods.setEntryDate(bill.getEntryDate());
						bill.getBillGoodses().add(goods);
						break;
					}
				}
			}

			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void saveBillEntry(BillEntry billEntry) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

//			bill.bid,bill.shortdesc,bill.billno , "
//					+ "bill.entrydate,bill.deliver,bill.deliverfee,bill.Remark "

//			  [shortdesc] VARCHAR, 
//			  [container] INT, 
//			  [totalamt] DOUBLE, 
//			  [goods] VARCHAR, 
//			  [taxtype] INT DEFAULT 0, 
//			  [billno] Varchar NOT NULL, 
//			  [truckDate] VARCHAR, 
//			  [entrydate] VARCHAR NOT NULL, 
//			  [supplier] INT, 
//			  [deliver] INTEGER NOT NULL, 
//			  [deliverfee] DOUBLE, 

			String sql = " update  txbillentry set supplier='" + billEntry.getSupplier() + "', truckDate='"
					+ billEntry.getTruckDate() + "', taxtype='" + billEntry.getTaxtype() + "',  goods='"
					+ billEntry.getGoods() + "', totalamt='" + billEntry.getTotalamt() + "', container='"
					+ billEntry.getContainer() + "', shortdesc='" + billEntry.getShortDesc() + "', billno='"
					+ billEntry.getBillNo() + "' , entrydate='" + billEntry.getEntryDate() + "',deliver='"
					+ billEntry.getDeliver() + "', deliverfee='" + billEntry.getDeliverFee() + "' ,Remark='"
					+ billEntry.getRemark() + "' where bid ='" + billEntry.getBillId() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean addFileTarget(FileTarget fileTarget) {
		Statement stmt = null;
		boolean ok = false;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowLongPlusTime();
			fileTarget.setCreateTime(time);

			String sql = "INSERT INTO txfiles (name,fpath,type,Status, Createtime ,hashid ) " + "VALUES ('"
					+ fileTarget.getName() + "','" + fileTarget.getFilePath() + "','" + fileTarget.getType() + "','"
					+ fileTarget.getStatus() + "','" + fileTarget.getCreateTime() + "','" + fileTarget.getHashid()
					+ "' );";

			stmt.executeUpdate(sql);

			stmt.close();

			ok = true;

		} catch (Exception e) {
			e.printStackTrace();

		}

		return ok;
	}

	public static ArrayList<FileTarget> queryFileTargets(FileTarget pageable) {
		ArrayList<FileTarget> array = new ArrayList<FileTarget>();

		FileTarget fileTarget = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select fid,name,fpath,type,Status,Createtime,remark from txfiles order by Createtime desc  ");

			while (rs.next()) {
				fileTarget = new FileTarget();
				fileTarget.setFid(rs.getInt("fid"));
				fileTarget.setName(rs.getString("name"));
				fileTarget.setCreateTime(rs.getString("Createtime"));
				fileTarget.setFileName(rs.getString("name"));
				fileTarget.setFilePath(rs.getString("fpath"));
				fileTarget.setType(rs.getInt("type"));
				fileTarget.setStatus(rs.getInt("Status"));
				fileTarget.setRemark(rs.getString("remark"));

				array.add(fileTarget);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void saveFileTargetInfo(FileTarget fileTarget) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  txfiles set name='" + fileTarget.getName() + "',status='" + fileTarget.getStatus()
					+ "',Remark='" + fileTarget.getRemark() + "'  where fid ='" + fileTarget.getFid() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateBillEntryFileTarget(String bid, int fid) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  txbillentry set fid='" + fid + "'  where bid ='" + bid + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Company> queryCompinvs(Company pageale) {

		ArrayList<Company> dList = new ArrayList<Company>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select com.cid, name,fullname,"
					+ " ifnull(bien.totalamt,0) bienAmt,ifnull(inv.amt,0) invAmt,ifnull(act.amt,0) payAMt,ifnull(acc.accamt,0) accAmt "
					+ " from TCompany com "
					+ " left join (select sum(totalamt) totalamt,  ifnull(supplier,0) supplier  from txbillentry  group by supplier) bien on bien.supplier = com.cid "
					+ " left join (select cid, sum(ifnull( totalamt,0)) amt from txinvoicelist where  ifnull(statusFlag,'正常') ='正常' and ifnull(ioflag,'是') ='是' group by cid) inv on inv.cid = com.cid "
					+ " left join ( select cid comid,sum(payDebit-receiveCredit) amt from txaccchecklist where cid > 0 group by  cid ) act on act.comid = com.cid "
					+ " left join ( select paycom.cid,sum(amt) accamt   from txacctradelist acc  "
					+ " inner join taccountinfos payacc on payacc.aid = acc.aidp  "
					+ " inner join tcompany paycom on payacc.cid = paycom.cid "
					+ " group by paycom.cid  ) acc on acc.cid=com.cid where com.cid > 0 and (ifnull(bien.totalamt,0) + ifnull(inv.amt,0) + ifnull(act.amt,0)) > 0 ";

			if (pageale.getId() > 0) {
				sql = sql + " and com.cid =" + pageale.getId();
			}

			if (!TxStringUtil.isEmpty(pageale.getName())) {
				sql = sql + " and com.name like '%" + pageale.getName() + "%' ";
			}

			sql = sql + " order by fullname, payAMt - invAmt desc";

			ResultSet rs = stmt.executeQuery(sql);

			Company company;

			while (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("cid"));
				company.setName(rs.getString("name"));
				company.setFullName(rs.getString("fullname"));

				company.setExportAmt(rs.getDouble("bienAmt"));
				company.setInvoiceAmt(rs.getDouble("invAmt"));
				company.setPayedAmt(rs.getDouble("payAmt"));
				company.setAccAmt(rs.getDouble("accAmt"));

				dList.add(company);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<Invoice> queryInvoiceList(Invoice pageable) {

		ArrayList<Invoice> array = new ArrayList<Invoice>();

		Invoice invoice = null;

		Statement stmt = null;
		try {

			initConn();

			String sql = "select  inv.iid,inv.cid,inv.goods,inv.invno,inv.makedate,inv.quantity, "
					+ "inv.type,inv.unit,inv.fid,com.fullname ,inv.totalamt from txinvoicelist inv  "
					+ "inner join  tcompany com on inv.cid = com.cid "
					+ " where ifnull(statusFlag,'正常')='正常' and ifnull(ioflag,'是') ='是' " + " and inv.totalamt>0 "
					+ " and  inv.cid = com.cid ";

			if (!TxStringUtil.isEmpty(pageable.getGoods())) {
				sql = sql + " and inv.goods like '%" + pageable.getGoods() + "%'";
			}

			if (pageable.getCompany() != 0) {
				sql = sql + " and inv.cid  ='" + pageable.getCompany() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getStartTime())) {
				sql = sql + " and inv.makedate >= '" + pageable.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getEndTime())) {
				sql = sql + " and inv.makedate <= '" + pageable.getEndTime() + "'";
			}

			sql = sql + " order by inv.makedate  desc,inv.invno ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				invoice = new Invoice();

				invoice.setCompany(rs.getInt("cid"));
				invoice.setCompanyName(rs.getString("fullname"));

				invoice.setFid(rs.getInt("fid"));
				invoice.setGoods(rs.getString("goods"));
				invoice.setInvId(rs.getInt("iid"));
				invoice.setInvoiceNo(rs.getString("invno"));

				invoice.setMakeDate(TxStringUtil.formatLongDate(rs.getString("makeDate")));

				invoice.setQuantity(TxStringUtil.toFloatNumber(rs.getDouble("quantity"), 2));

				invoice.setTotalAmt(TxStringUtil.toFloatNumber(rs.getDouble("totalamt"), 2));

				invoice.setUnit(rs.getString("unit"));

				invoice.setType(rs.getInt("type"));

				array.add(invoice);
			}
			rs.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static ArrayList<Invoice> queryInvoiceList_sys4remove(Invoice pageable) {

		ArrayList<Invoice> array = new ArrayList<Invoice>();

		Invoice invoice = null;

		Statement stmt = null;
		try {

			initConn();

			String sql = "select  inv.iid,inv.cid,inv.goods,inv.invoiceno,inv.makedate,inv.quantity, "
					+ "inv.type,inv.unit,inv.fid,com.fullname ,inv.totalamt from txinvoice_sys inv  "
					+ "inner join  tcompany com on inv.cid = com.cid "
					+ " where statusFlag='正常' and ioflag='是' and inv.totalamt>0 and inv.cid = com.cid ";

			if (!TxStringUtil.isEmpty(pageable.getGoods())) {
				sql = sql + " and inv.goods like '%" + pageable.getGoods() + "%'";
			}

			if (pageable.getCompany() != 0) {
				sql = sql + " and inv.cid  ='" + pageable.getCompany() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getStartTime())) {
				sql = sql + " and inv.makedate >= '" + pageable.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getEndTime())) {
				sql = sql + " and inv.makedate <= '" + pageable.getEndTime() + "'";
			}

			sql = sql + " order by inv.makedate  desc,inv.invoiceno ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				invoice = new Invoice();

				invoice.setCompany(rs.getInt("cid"));
				invoice.setCompanyName(rs.getString("fullname"));

				invoice.setFid(rs.getInt("fid"));
				invoice.setGoods(rs.getString("goods"));
				invoice.setInvId(rs.getInt("iid"));
				invoice.setInvoiceNo(rs.getString("invoiceno"));

				invoice.setMakeDate(TxStringUtil.formatLongDate(rs.getString("makeDate")));

				invoice.setQuantity(TxStringUtil.toFloatNumber(rs.getDouble("quantity"), 2));

				invoice.setTotalAmt(TxStringUtil.toFloatNumber(rs.getDouble("totalamt"), 2));

				invoice.setUnit(rs.getString("unit"));

				invoice.setType(rs.getInt("type"));

				array.add(invoice);
			}
			rs.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static ArrayList<Company> queryCompanysFromInvoice(Company pageale) {

		ArrayList<Company> dList = new ArrayList<Company>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select com.cid , name,fullname, ifnull(inv.amt,0) invAmt  " + " from TCompany com "
					+ " inner join (select cid, sum(ifnull( totalamt,0)) amt from txinvoice_sys group by cid) inv on inv.cid = com.cid "
					+ " where com.cid > 0 ";

			if (pageale.getId() > 0) {
				sql = sql + " and com.cid =" + pageale.getId();
			}

			ResultSet rs = stmt.executeQuery(sql);

			Company company;

			while (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("cid"));
				company.setName(rs.getString("name"));
				company.setFullName(rs.getString("fullname"));
				company.setInvoiceAmt(rs.getDouble("invAmt"));

				dList.add(company);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<Company> queryPayAccountsFromTrade() {
		ArrayList<Company> array = new ArrayList<Company>();

		Company company = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select  ca.cid ,ca.name,ca.fullname  " + "from tcompany  ca  "
					+ "inner join ( select acc.cid comid,sum(amt) amt from txacctradelist "
					+ "inner join taccountInfos acc on acc.aid = txacctradelist.aidp group by acc.cid ) act on act.comid = ca.cid "
					+ "order by  ca.name ");

			while (rs.next()) {

				company = new Company();
				company.setId(rs.getInt("cid"));
				company.setName(rs.getString("name"));
				company.setFullName(rs.getString("fullname"));

				array.add(company);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static ArrayList<AccCheckInfo> queryAccCheckList(AccCheckInfo accCheck, TxResponseResult rr) {

		ArrayList<AccCheckInfo> array = new ArrayList<AccCheckInfo>();

		AccCheckInfo acccek = null;

		Statement stmt = null;

		double totalRecAmt = 0.0;
		double totalPayAmt = 0.0;

		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select    [tid] ,  [cid]  ,  accDate  , [descs]  ,  [payDebit]  ,  "
					+ "  [receiveCredit] ,   balance ,   accNo  ,  accName  ,  accid from Txaccchecklist where 0=0 ";

			String queryCondition = "";
			if (!TxStringUtil.isEmpty(accCheck.getStartTime())) {
				queryCondition = queryCondition + " and accDate >= '" + accCheck.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(accCheck.getEndTime())) {
				queryCondition = queryCondition + " and accDate <= '" + accCheck.getEndTime() + "'";
			}

			if (!TxStringUtil.isEmpty(accCheck.getDescs())) {
				queryCondition = queryCondition + " and descs like '%" + accCheck.getDescs() + "%'";
			}

			if (!TxStringUtil.isEmpty(accCheck.getAccName())) {
				queryCondition = queryCondition + " and accName like '%" + accCheck.getAccName() + "%' ";
			}

			sql = sql + queryCondition;

			if (!TxStringUtil.isEmpty(accCheck.getOrderField())) {
				sql = sql + " order by " + accCheck.getOrderField();
			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				acccek = new AccCheckInfo();
				acccek.setAccDate(rs.getString("accDate"));
				acccek.setAccid(rs.getInt("accid"));
				acccek.setAccName(rs.getString("accName"));
				acccek.setAccNo(rs.getString("accNo"));
				acccek.setBalance(rs.getDouble("balance"));
				acccek.setDescs(rs.getString("descs"));
				acccek.setPayAmt(rs.getDouble("payDebit"));
				acccek.setRecAmt(rs.getDouble("receiveCredit"));

				totalRecAmt += acccek.getRecAmt();
				totalPayAmt += acccek.getPayAmt();

				array.add(acccek);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		if (rr != null) {
			rr.addReportData("totalPayAmt", totalPayAmt);
			rr.addReportData("totalRecAmt", totalRecAmt);
		}

		return array;
	}

	public static ArrayList<Invoice> queryAccInvList(Invoice pageable) {
		ArrayList<Invoice> array = new ArrayList<Invoice>();

		Invoice invoice = null;

		Statement stmt = null;
		try {

			initConn();

			String sql = "select  inv.iid,inv.cid,inv.goods,inv.invno,inv.makedate,inv.quantity, "
					+ "inv.type,inv.unit,inv.fid,com.fullname ,inv.totalamt from txaccinvlist inv  "
					+ "inner join  TXInvCompany com on inv.cid = com.cid "
					+ " where ifnull(statusFlag,'正常')='正常' and ifnull(ioflag,'是') ='是' " + " and inv.totalamt>0 "
					+ " and  inv.cid = com.cid ";

			if (!TxStringUtil.isEmpty(pageable.getGoods())) {
				sql = sql + " and inv.goods like '%" + pageable.getGoods() + "%'";
			}

			if (pageable.getCompany() != 0) {
				sql = sql + " and inv.cid  ='" + pageable.getCompany() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getStartTime())) {
				sql = sql + " and inv.makedate >= '" + pageable.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(pageable.getEndTime())) {
				sql = sql + " and inv.makedate <= '" + pageable.getEndTime() + "'";
			}

			sql = sql + " order by inv.makedate  desc,inv.invno ";

			System.out.print(sql);

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				invoice = new Invoice();

				invoice.setCompany(rs.getInt("cid"));
				invoice.setCompanyName(rs.getString("fullname"));

				invoice.setInvId(rs.getInt("iid"));
				invoice.setInvoiceNo(rs.getString("invno"));

				invoice.setMakeDate(TxStringUtil.formatLongDate(rs.getString("makeDate")));

				invoice.setTotalAmt(TxStringUtil.toFloatNumber(rs.getDouble("totalamt"), 2));

				invoice.setType(rs.getInt("type"));

				array.add(invoice);
			}
			rs.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void saveBillEntrySupplier(BillEntry billEntry) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  txbillentry set supplier='" + billEntry.getSupplier() + "'  where bid ='"
					+ billEntry.getBillId() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<AccList> queryAccBillList(AccList accList, TxResponseResult rr) {
		ArrayList<AccList> array = new ArrayList<AccList>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " select Amt,descs,ioflag,acctype,payTime from Txaccbilllist acc  ";

			String queryCondition = " where 0 = 0 ";

			if (!TxStringUtil.isEmpty(accList.getRemark())) {
				queryCondition = queryCondition + " and descs like '%" + accList.getRemark() + "%'";
			}

			if (!TxStringUtil.isEmpty(accList.getPayAccName())) {
				queryCondition = queryCondition + " and acctype like '%" + accList.getPayAccName() + "%'";
			}

			if (!TxStringUtil.isEmpty(accList.getStartTime())) {
				queryCondition = queryCondition + " and paytime >= '" + accList.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(accList.getEndTime())) {
				queryCondition = queryCondition + " and paytime <= '" + accList.getEndTime() + "'";
			}

			sql = sql + queryCondition;

			System.out.println(sql);

			if (!TxStringUtil.isEmpty(accList.getOrderField())) {
				sql = sql + " order by " + accList.getOrderField();
			}

			ResultSet rs = stmt.executeQuery(sql);

			DecimalFormat dd = new DecimalFormat("#.00");

			AccList trade = null;

			double payamt = 0.0;
			double recamt = 0.0;

			while (rs.next()) {
				trade = new AccList();
				trade.setPayAccName(rs.getString("acctype"));
				trade.setPayAmt(dd.format(rs.getDouble("amt")));
				trade.setPayTime(rs.getString("payTime"));
				trade.setRemark(rs.getString("descs"));
				trade.setIoFlag(rs.getInt("ioflag"));

				if (trade.getIoFlag() == 1) {
					payamt = payamt + Double.valueOf(trade.getPayAmt());
				} else {
					recamt = recamt + Double.valueOf(trade.getPayAmt());
				}

				array.add(trade);
			}

			if (rr != null) {
				rr.addReportData("T1", payamt);
				rr.addReportData("T2", recamt);
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void updateOnBussiness(Company company) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "update TCompany set onbussiness = '" + company.getOnbussiness() + "' where cid="
					+ company.getId() + " ";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateIsPayment(Account account) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  TAccountInfos set  ispayment = '" + account.getIsPayment() + "' where aid ='"
					+ account.getAccId() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Company> queryRetaxCompanys() {
		ArrayList<Company> dList = new ArrayList<Company>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select   [cid]  ,   [name]  ,fullname,  nation,  [Type]  ,  [addr]  ,   [ContractName]  ,   [contractAddr]  , "
					+ "  [ContractInfo]  , onbussiness, [Remark] from TCompany where retax=1 ";

			ResultSet rs = stmt.executeQuery(sql);

			Company company;

			while (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("cid"));
				company.setName(rs.getString("name"));
				company.setFullName(rs.getString("fullname"));
				company.setNation(rs.getString("nation"));
				company.setAddr(rs.getString("addr"));
				company.setContractName(rs.getString("ContractName"));
				company.setContractAddr(rs.getString("ContractAddr"));
				company.setContractInfo(rs.getString("ContractInfo"));
				company.setRemark(rs.getString("Remark"));
				company.setType(rs.getInt("Type"));
				company.setOnbussiness(rs.getInt("onbussiness"));

				dList.add(company);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<Account> queryRetaxPayAccounts() {
		ArrayList<Account> array = new ArrayList<Account>();

		Account account = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select acc.aid, acc.cid companyId, acc.AccName, acc.AccNo,acc.Bank, "
					+ "acc.status,acc.remark,acc.isComp,ifnull(acc.alias,'默认') alias,ca.name as companyName from TAccountInfos acc "
					+ "inner join tcompany  ca on ca.cid =  acc.cid and ca.retax=1 ");

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));
				account.setAlias(rs.getString("alias"));

				array.add(account);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static ArrayList<AccList> queryAccRetaxList(AccList accList) {
		ArrayList<AccList> array = new ArrayList<AccList>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sqlCount = "select count(*) count from Txaccretaxlist acc "
					+ "inner join tbusiness bu on bu.bid = acc.bid "
					+ "inner join taccountinfos payacc on payacc.aid = acc.aidp "
					+ "inner join taccountinfos oweracc on oweracc.aid = acc.aido "
					+ "inner join tcompany paycom on payacc.cid = paycom.cid ";

			String sql = "select acc.tid,bu.bid businessId,bu.bname businessName, "
					+ "paycom.name payCompany,ifnull(payacc.alias,'默认') payAlias,"
					+ "payacc.AccName payAccName, payacc.AccNo payAccInfo, payacc.cid payCompanyId ,"
					+ "payacc.aid payAccId, " + "oweracc.AccNo owerAccInfo, ifnull(oweracc.alias,'默认') owerAlias,   "
					+ "oweracc.AccName owerAccName, oweracc.aid owerAccId, "
					+ "acc.Amt payAmt, acc.payTime,ifnull(acc.type,4) type,acc.payType,acc.ioflag,acc.remark ,ifnull(acc.ioflag,1) ioflag "
					+ "from Txaccretaxlist acc " + "inner join tbusiness bu on bu.bid = acc.bid "
					+ "inner join taccountinfos payacc on payacc.aid = acc.aidp "
					+ "inner join taccountinfos oweracc on oweracc.aid = acc.aido "
					+ "inner join tcompany paycom on payacc.cid = paycom.cid";

			String queryCondition = " where payacc.cid = paycom.cid ";
//			if (accTrade.getOwerAccount() != 0) {
//				queryCondition = queryCondition + " and acc.aido=" + accTrade.getOwerAccount();
//			}

//			if (accTrade.getIoFlag() != 0) {
//				queryCondition = queryCondition + " and acc.ioflag=" + accTrade.getIoFlag();
//			}

			if (!TxStringUtil.isEmpty(accList.getRemark())) {
				queryCondition = queryCondition + " and acc.remark like '%" + accList.getRemark() + "%'";
			}

			if (!TxStringUtil.isEmpty(accList.getPayCompany())) {
				queryCondition = queryCondition + " and paycom.name like '%" + accList.getPayCompany() + "%'";
			}

			if (!TxStringUtil.isEmpty(accList.getStartTime())) {
				queryCondition = queryCondition + " and acc.paytime >= '" + accList.getStartTime() + "'";
			}

			if (!TxStringUtil.isEmpty(accList.getEndTime())) {
				queryCondition = queryCondition + " and acc.paytime <= '" + accList.getEndTime() + "'";
			}

//			if (accTrade.getPayCompanyId() != 0) {
//				queryCondition = queryCondition + " and paycom.cid=" + accTrade.getPayCompanyId();
//			}

			sql = sql + queryCondition;

			if (!TxStringUtil.isEmpty(accList.getOrderField())) {
				sql = sql + " order by " + accList.getOrderField();
			}

			sql = sql + " limit " + accList.getPageSize();

			sqlCount = sqlCount + queryCondition;

			ResultSet rs = stmt.executeQuery(sql);

			DecimalFormat dd = new DecimalFormat("#.00");

			AccList trade = null;
			while (rs.next()) {
				trade = new AccList();

				trade.setTid(rs.getInt("tid"));
				trade.setBusinessId(rs.getInt("businessId"));
				trade.setBusinessName(rs.getString("businessName"));
				trade.setPayAccInfo(rs.getString("payAccInfo"));
				trade.setPayAccName(rs.getString("payAccName"));
				trade.setPayAccount(rs.getInt("payAccId"));
				trade.setPayAmt(dd.format(rs.getDouble("payAmt")));
				trade.setPayCompany(rs.getString("payCompany"));
				trade.setPayTime(rs.getString("payTime"));
				trade.setPayType(rs.getInt("payType"));
				trade.setOwerAccInfo(rs.getString("owerAccInfo"));
				trade.setOwerAccount(rs.getInt("owerAccId"));
				trade.setOwerAccName(rs.getString("owerAccName"));
				trade.setPayAlias(rs.getString("payAlias"));
				trade.setOwerAlias(rs.getString("owerAlias"));
				trade.setRemark(rs.getString("remark"));
				trade.setIoFlag(rs.getInt("ioflag"));

				trade.setType(rs.getInt("type"));

				array.add(trade);
			}
			rs.close();
			stmt.close();

			ResultSet rsCount = stmt.executeQuery(sqlCount);
			int count = 0;
			while (rsCount.next()) {
				count = rsCount.getInt("count");
			}

			rsCount.close();
			stmt.close();

			accList.setRowCount(count);

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void addAccRetax(AccList accList) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "INSERT INTO Txaccretaxlist (bid,aido,aidp,Amt ,ioFlag, paytype, type,  payTime, Remark,Createtime) "
					+ "VALUES ('" + accList.getBusinessId() + "','" + accList.getOwerAccount() + "','"
					+ accList.getPayAccount() + "','" + accList.getPayAmt() + "'," + accList.getIoFlag() + ",'"
					+ accList.getPayType() + "','" + accList.getType() + "','" + accList.getPayTime() + "','"
					+ accList.getRemark() + "','" + time + "');";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Account> queryRetaxAccounts(int type) {
		ArrayList<Account> array = new ArrayList<Account>();

		Account account = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select acc.aid, acc.cid companyId, acc.AccName, acc.AccNo,acc.Bank, "
					+ "acc.status,acc.remark,acc.isComp,ifnull(acc.alias,'默认') alias,ca.name as companyName from TAccountInfos acc "
					+ "inner join tcompany  ca on ca.cid =  acc.cid and ca.retax=1 and acc.type =" + type);

			while (rs.next()) {
				account = new Account();
				account.setAccId(rs.getInt("aid"));
				account.setAccName(rs.getString("AccName"));
				account.setAccNo(rs.getString("AccNo"));
				account.setBank(rs.getString("Bank"));
				account.setCompanyName(rs.getString("companyName"));
				account.setRemark(rs.getString("remark"));
				account.setStatus(rs.getInt("status"));
				account.setIsCompany(rs.getInt("isComp"));
				account.setCompanyId(rs.getInt("companyId"));
				account.setAlias(rs.getString("alias"));

				array.add(account);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return array;
	}

	public static void saveAccRetax(AccList accTrade) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  txaccretaxlist set  bid = '" + accTrade.getBusinessId() + "'," + "aido = '"
					+ accTrade.getOwerAccount() + "'," + "aidp = '" + accTrade.getPayAccount() + "', Amt = '"
					+ accTrade.getPayAmt() + "'" + " , Remark = '" + accTrade.getRemark() + "', type='"
					+ accTrade.getType() + "', ioflag = '" + accTrade.getIoFlag() + "', payTime='"
					+ accTrade.getPayTime() + "' where tid ='" + accTrade.getTid() + "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static FileTarget queryOneFileTarget(String fileId) {

		FileTarget fileTarget = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select fid,name,fpath,type,Status,Createtime,remark " + "from txfiles where fid='" + fileId + "'");

			while (rs.next()) {
				fileTarget = new FileTarget();
				fileTarget.setFid(rs.getInt("fid"));
				fileTarget.setName(rs.getString("name"));
				fileTarget.setCreateTime(rs.getString("Createtime"));
				fileTarget.setFileName(rs.getString("name"));
				fileTarget.setFilePath(rs.getString("fpath"));
				fileTarget.setType(rs.getInt("type"));
				fileTarget.setStatus(rs.getInt("Status"));
				fileTarget.setRemark(rs.getString("remark"));

				break;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return fileTarget;
	}

	public static void saveFileType(FileTarget fileTarget) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " update  txfiles set type='" + fileTarget.getType() + "'  where fid ='" + fileTarget.getFid()
					+ "'";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Company> queryCompanysFromRetax(Company pageale) {

		ArrayList<Company> dList = new ArrayList<Company>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select   [cid]  ,   [name]  ,fullname,  nation, retax, [Type]  ,  [addr]  ,   [ContractName]  ,   [contractAddr]  , "
					+ "  [ContractInfo]  , onbussiness, [Remark] from TCompany where retax=1 ";

			if (!TxStringUtil.isEmpty(pageale.getFullName())) {
				sql = sql + " and fullname like '%" + pageale.getFullName() + "%'";
			}

			sql = sql + " order by type,fullname";

			ResultSet rs = stmt.executeQuery(sql);

			Company company;

			while (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("cid"));
				company.setName(rs.getString("name"));
				company.setFullName(rs.getString("fullname"));
				company.setNation(rs.getString("nation"));
				company.setAddr(rs.getString("addr"));
				company.setContractName(rs.getString("ContractName"));
				company.setContractAddr(rs.getString("ContractAddr"));
				company.setContractInfo(rs.getString("ContractInfo"));
				company.setRemark(rs.getString("Remark"));
				company.setType(rs.getInt("Type"));
				company.setRetax(rs.getInt("retax"));
				company.setOnbussiness(rs.getInt("onbussiness"));

				dList.add(company);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static void updateRetax(Company company) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "update TCompany set retax = '" + company.getRetax() + "' where cid=" + company.getId() + " ";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
