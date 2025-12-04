package com.book.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.book.bean.Address;
import com.book.bean.Book;
import com.book.bean.Dynasty;
import com.book.bean.Nation;
import com.book.bean.ObjTarget;
import com.book.bean.Paragraphs;
import com.book.bean.Person;
import com.book.bean.TargetLink;
import com.common.Constants;
import com.common.TxProperties;
import com.common.TxStringUtil;

public class BookSqliteAdapter {
	private static Connection connection = null;
	private static String DBPath = "D:\\txworks\\db\\books.db";

	private static String getDBPath() {
		if (TxStringUtil.isEmpty(DBPath)) {
			DBPath = TxProperties.getString("dbpath");
		}

		return DBPath;
	}

	private static void initConn() throws Exception {

		if (connection == null) {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + getDBPath());
			connection.setAutoCommit(true);

		}
	}

	public static ArrayList<Paragraphs> selectBookContents(Book book) {

		if (book == null) {
			return null;
		}

		ArrayList<Paragraphs> bookList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " SELECT alb.bid albumid ,alb.bookname ChiefAlbum,  book.volume1 volume, "
					+ "book.bid ,book.bookname,  para.gid,para.content,para.trans FROM txparagraphs para "
					+ "inner join txbooks book on para.bid = book.bid  "
					+ "inner join txalbum alb on alb.bid = book.albid where para.bid= " + book.getBid();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Paragraphs paragraphs = new Paragraphs();

				paragraphs.setBid(rs.getInt("bid"));
				paragraphs.setGid(rs.getInt("gid"));
				paragraphs.setContent(rs.getString("content"));
				paragraphs.setTranscation(rs.getString("trans"));
				paragraphs.setBookName(rs.getString("bookname"));
				paragraphs.setAlbumId(rs.getInt("albumid"));
				paragraphs.setChiefAlbum(rs.getString("ChiefAlbum"));
				paragraphs.setVolume(rs.getString("volume"));

				bookList.add(paragraphs);

			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static ArrayList<Book> selectBooks(Book book) {

		ArrayList<Book> bookList = new ArrayList<Book>();

		Statement stmt = null;
		try {

			initConn();

			String sql = "select txbooks.bid,txbooks.bookname,txbooks.details, txalbum.bid as albumid, "
					+ "txalbum.bookname as ChiefAlbum,  txbooks.vid, txbooks.volume1 as volume  from txbooks "
					+ "inner join txalbum on txbooks.albid = txalbum.bid  where 1=1";
			if (book != null && book.getAlbumId() != 0) {
				sql = sql + " and txalbum.bid=" + book.getAlbumId();
			}

			sql = sql + " order by txbooks.sqindex ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Book retBook = new Book();
				retBook.setBid(rs.getInt("bid"));
				retBook.setName(rs.getString("bookname"));
				retBook.setAlbumId(rs.getInt("albumid"));
				retBook.setChiefAlbum(rs.getString("ChiefAlbum"));
				retBook.setVolume(rs.getString("volume"));
				bookList.add(retBook);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static ArrayList<Book> selectBooksMenu(Book book) {

		ArrayList<Book> bookList = new ArrayList<Book>();

		Statement stmt = null;
		try {

			initConn();

			String sql = "select txbooks.bid,txbooks.bookname,txbooks.details, txalbum.bid as albumid, "
					+ "txalbum.bookname as ChiefAlbum,txbooks.vid,  txbooks.volume1 as volume  from txbooks "
					+ "inner join txalbum on txbooks.albid = txalbum.bid  where 1=1";

			sql = sql + " and txbooks.albid = " + book.getAlbumId();

			String sqlUp = sql + " and txbooks.bid <  " + book.getBid() + " order by txbooks.bid desc limit 3 ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sqlUp);
			while (rs.next()) {
				Book retBook = new Book();
				retBook.setBid(rs.getInt("bid"));
				retBook.setName(rs.getString("bookname"));
				retBook.setAlbumId(rs.getInt("albumid"));
				retBook.setChiefAlbum(rs.getString("ChiefAlbum"));
				retBook.setVolume(rs.getString("volume"));
				retBook.setVolumeId(rs.getInt("vid"));
				bookList.add(retBook);
			}
			rs.close();
			stmt.close();

			bookList.add(book);

			String sqlDown = sql + " and txbooks.bid >  " + book.getBid() + " order by txbooks.bid limit 3 ";

			stmt = connection.createStatement();
			ResultSet rss = stmt.executeQuery(sqlDown);
			while (rss.next()) {
				Book retBook = new Book();
				retBook.setBid(rss.getInt("bid"));
				retBook.setName(rss.getString("bookname"));
				retBook.setAlbumId(rss.getInt("albumid"));
				retBook.setChiefAlbum(rss.getString("ChiefAlbum"));
				retBook.setVolume(rss.getString("volume"));
				retBook.setVolumeId(rss.getInt("vid"));
				bookList.add(retBook);
			}
			rss.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static Book selectOneBook(int bookid) {

		Book book = null;

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select txbooks.bid,txbooks.bookname,txbooks.details, txalbum.bid as albumid, "
					+ "txalbum.bookname as ChiefAlbum, txalbum.summary ,txbooks.sqIndex,txbooks.vid, txbooks.volume1 as volume ,"
					+ "ifnull(txvolume.bgtime, txalbum.bgtime) bgtime,  "
					+ "ifnull(txvolume.endtime,txalbum.endtime) endtime  from txbooks "
					+ "inner join txalbum on txbooks.albid = txalbum.bid "
					+ "left  join txvolume on txvolume.vid = txbooks.vid and txvolume.albid=txbooks.albid "
					+ "where txbooks.bid=" + bookid;

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				book = new Book();
				book.setBid(rs.getInt("bid"));
				book.setAlbumId(rs.getInt("albumid"));
				book.setName(rs.getString("bookname"));
				book.setChiefAlbum(rs.getString("ChiefAlbum"));
				book.setSummary(rs.getString("summary"));
				book.setVolume(rs.getString("volume"));
				book.setBgtime(rs.getInt("bgtime"));
				book.setEndtime(rs.getInt("endtime"));
				book.setVolumeId(rs.getInt("vid"));
				book.setSqIndex(rs.getInt("SqIndex"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return book;
	}

	public static Book selectOneAlbum(int albid) {

		Book book = new Book();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from txalbum where bid=" + albid);

			book.setBid(rs.getInt("bid"));
			book.setAlbumId(rs.getInt("bid"));
			book.setStatus(rs.getInt("status"));
			book.setName(rs.getString("bookname"));
			book.setChiefAlbum(rs.getString("bookname"));
			book.setSummary(rs.getString("summary"));

			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return book;
	}

	public static ArrayList<Book> selectAlbum() {

		ArrayList<Book> bookList = new ArrayList<Book>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from txalbum ");
			while (rs.next()) {
				Book book = new Book();
				book.setBid(rs.getInt("bid"));
				book.setAlbumId(rs.getInt("bid"));
				book.setStatus(rs.getInt("status"));
				book.setName(rs.getString("bookname"));
				book.setChiefAlbum(rs.getString("bookname"));
				book.setSummary(rs.getString("summary"));
				bookList.add(book);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static ArrayList<Dynasty> selectDynasty(Dynasty dynasty2) {

		ArrayList<Dynasty> dList = new ArrayList<Dynasty>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT * FROM txDynasty  where (bgtime>=" + dynasty2.getBgTime() + " and  endtime<="
					+ dynasty2.getEndTime() + ") or" + " (  bgtime<=" + dynasty2.getBgTime() + " and  endtime>="
					+ dynasty2.getBgTime() + " ) or" + "  (  bgtime<=" + dynasty2.getEndTime() + " and  endtime>="
					+ dynasty2.getEndTime() + " )  order by bgtime ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Dynasty dynasty = new Dynasty();
				dynasty.setId(rs.getInt("id"));
				dynasty.setName(rs.getString("name"));

				dynasty.setBgTime(rs.getInt("bgtime"));
				dynasty.setEndTime(rs.getInt("endtime"));
				dynasty.setCreator(rs.getString("creator"));
				dynasty.setPolit(rs.getInt("ispolit"));

				dList.add(dynasty);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<Person> selectPersons(Person param) {

		ArrayList<Person> pList = new ArrayList<Person>();

		Statement stmt = null;
		try {

			initConn();

			String sql = " SELECT id,name,details,ifnull(type,0) type,neturl FROM txtargets where type=1 ";

			if (param != null && !TxStringUtil.isEmpty(param.getName())) {
				sql = sql + " and (  name like '%" + param.getName() + "%' or details like '%" + param.getName()
						+ "%' ) ";
			}

			sql = sql + " order by edittime desc ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Person person = new Person();
				person.setPid(rs.getInt("id"));
				person.setName(rs.getString("name"));
				person.setDetail(rs.getString("details"));
				person.setNeturl(rs.getString("neturl"));
				pList.add(person);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static ArrayList<Book> selectVolumes(Book album) {

		ArrayList<Book> bookList = new ArrayList<Book>();

		Statement stmt = null;
		try {

			initConn();

			String sql = "select  txalbum.bid as albumid,  txalbum.bookname as ChiefAlbum, "
					+ "txvolume.vid as volumeid, txvolume.bookname as volume  from txvolume "
					+ "inner join txalbum on txvolume.bid = txalbum.bid where 1=1 ";
			if (album != null && album.getBid() > 0) {
				sql = sql + " and txvolume.bid =" + album.getBid();
			}
			sql = sql + " order by txvolume.sqindex";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Book book = new Book();
				book.setBid(rs.getInt("volumeid"));
				book.setName(rs.getString("volume"));
				book.setVolumeId(rs.getInt("volumeid"));

				book.setVolume(book.getName());

				book.setAlbumId(rs.getInt("albumid"));
				book.setChiefAlbum(rs.getString("ChiefAlbum"));

				bookList.add(book);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;

	}

	public static ArrayList<Nation> selectNation() {
		ArrayList<Nation> dList = new ArrayList<Nation>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM txNation");

			Nation nation = new Nation();

			while (rs.next()) {
				nation = new Nation();
				nation.setId(rs.getInt("id"));
				nation.setName(rs.getString("name"));

				nation.setBgTime(rs.getInt("bgtime"));
				nation.setEndTime(rs.getInt("endtime"));
				nation.setCreator(rs.getString("creator"));

				dList.add(nation);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<ObjTarget> selectShowObjTargets(ObjTarget target) {
		ArrayList<ObjTarget> dList = new ArrayList<ObjTarget>();

		if (target == null) {

			return dList;
		}

		if (TxStringUtil.isEmpty(target.getName())) {
			return dList;
		}

		Statement stmt = null;
		try {

			initConn();

			String sql = "SELECT txtargets.id,txtargets.name,txtargets.alias,txtargets.addr,txtargets.details,"
					+ " ifnull(txtargets.type,0) type,txtargets.bgtime,txtargets.endtime,"
					+ " ifnull(rela.gid,0) gid ,txtargets.country,ifnull(ctry.name,'') countryName  "
					+ " FROM txtargets  left join txgtrelation rela on rela.tid = txtargets.id  and rela.gid= "
					+ target.getGraphId() + " left join txcountry ctry on  ctry.id = txtargets.country  where 0=0 ";

			sql = sql + " and (  txtargets.name like '%" + target.getName() + "%' or txtargets.alias like '%"
					+ target.getName() + "%' ) ";

			sql = sql + " order by txtargets.edittime desc ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ObjTarget obj;

			StringBuffer sb = new StringBuffer();
			sb.append("0");
			while (rs.next()) {
				obj = new ObjTarget();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setAlias(rs.getString("alias"));
				obj.setDetail(rs.getString("details"));
				obj.setType(rs.getInt("type"));
				obj.setAddress(rs.getString("addr"));
				obj.setGraphId(rs.getInt("gid"));
				obj.setBgtime(rs.getInt("bgtime"));
				obj.setEndtime(rs.getInt("endtime"));
				obj.setCountry(rs.getInt("Country"));
				obj.setCountryName(rs.getString("CountryName"));

				dList.add(obj);

				sb.append(",").append(obj.getId());
			}
			rs.close();
			stmt.close();

		} catch (

		Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<ObjTarget> selectObjTargets(ObjTarget p) {

		ArrayList<ObjTarget> dList = new ArrayList<ObjTarget>();

		Statement stmt = null;
		try {

			initConn();

			String sql = "SELECT txtargets.id,txtargets.name,txtargets.alias,txtargets.addr,"
					+ " txtargets.details,ifnull(txtargets.type,0) type ,"
					+ " txtargets.bgtime,txtargets.endtime,txtargets.country,ifnull(ctry.name,'') countryName  "
					+ " FROM txtargets   left join txcountry ctry on  ctry.id = txtargets.country  where 0=0   ";
			if (p != null) {
				if (!TxStringUtil.isEmpty(p.getName())) {
					sql = sql + " and (  txtargets.name like '%" + p.getName() + "%' or txtargets.details like '%"
							+ p.getName() + "%' or txtargets.alias like '%" + p.getName() + "%' ) ";
				}

			}

			sql = sql + " order by edittime desc ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ObjTarget obj;

			while (rs.next()) {
				obj = new ObjTarget();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setAlias(rs.getString("alias"));
				obj.setType(rs.getInt("type"));
				obj.setAddress(rs.getString("addr"));
				obj.setDetail(rs.getString("details"));
				obj.setBgtime(rs.getInt("bgtime"));
				obj.setEndtime(rs.getInt("endtime"));
				obj.setCountry(rs.getInt("Country"));
				obj.setCountryName(rs.getString("CountryName"));
				dList.add(obj);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static ArrayList<ObjTarget> selectTargetsWithParagraphs(int gid) {

		ArrayList<ObjTarget> pList = new ArrayList<ObjTarget>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select evt.id,evt.name,evt.details from txtargets as evt "
					+ "	 inner join txgtrelation  rela on rela.tid = evt.id " + "  where rela.status=1 and rela.gid = "
					+ gid);

			while (rs.next()) {
				ObjTarget event = new ObjTarget();
				event.setId(rs.getInt("id"));
				event.setName(rs.getString("name"));
				event.setDetail(rs.getString("details"));
				pList.add(event);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static void addObjTarget(ObjTarget obj) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select * from txtargets where name = '" + obj.getName() + "';";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				stmt.close();
				return;
			}

			String time = TxStringUtil.getNowSortPlusTime();

			sql = "INSERT INTO txtargets (name,addr,addrid,details,country,bgtime,endtime,type,edittime)  VALUES ('"
					+ obj.getName() + "','" + obj.getAddress() + "','" + obj.getAddrId() + "','" + obj.getDetail()
					+ "'," + obj.getCountry() + ",'" + obj.getBgtime() + "','" + obj.getEndtime() + "','"
					+ obj.getType() + "','" + time + "' );";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Paragraphs> selectBookContentsByKeys(Paragraphs para) {

		ArrayList<Paragraphs> bookList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = " SELECT alb.bid albumid ,alb.bookname ChiefAlbum,  book.volume1 volume,  "
					+ "book.bid ,book.bookname,  para.gid,para.content,para.trans  FROM txparagraphs para "
					+ "inner join txbooks book on para.bid = book.bid  "
					+ "inner join txalbum alb on alb.bid = book.albid  where  para.content like '%" + para.getContent()
					+ "%' ";

			if (para.getAlbumId() > 0) {
				sql = sql + " and book.albid =  " + para.getAlbumId();
			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Paragraphs paragraphs = new Paragraphs();
				paragraphs.setBid(rs.getInt("bid"));
				paragraphs.setGid(rs.getInt("gid"));
				paragraphs.setContent(rs.getString("content"));
				paragraphs.setTranscation(rs.getString("trans"));
				paragraphs.setBookName(rs.getString("bookname"));
				paragraphs.setAlbumId(rs.getInt("albumid"));
				paragraphs.setChiefAlbum(rs.getString("ChiefAlbum"));
				paragraphs.setVolume(rs.getString("volume"));

				paragraphs.setContent(paragraphs.getContent().replace(para.getContent(),
						TxStringUtil.getHighLine(para.getContent())));

				paragraphs.setCondition(para.getContent());

				bookList.add(paragraphs);

				Constants.CurMaxContentId = paragraphs.getGid();
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static ArrayList<ObjTarget> selectObjTargets() {
		return selectObjTargets(null);
	}

	public static ArrayList<Paragraphs> selectBookContentsByTarget(int tid) {

		ArrayList<Paragraphs> pList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT alb.bid albumid ,alb.bookname ChiefAlbum, "
					+ "					 book.volume1 volume, book.bid ,book.bookname,  "
					+ "					 para.gid,para.content,para.trans FROM txparagraphs para  "
					+ "					 inner join txbooks book on para.bid = book.bid  "
					+ "					 inner join txalbum alb on alb.bid = book.albid  "
					+ "           inner join txgtrelation  rela on rela.gid = para.gid "
					+ "           where rela.tid =  " + tid;

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Paragraphs paragraphs = new Paragraphs();
				paragraphs.setBid(rs.getInt("bid"));
				paragraphs.setGid(rs.getInt("gid"));
				paragraphs.setContent(rs.getString("content"));
				paragraphs.setTranscation(rs.getString("trans"));
				paragraphs.setBookName(rs.getString("bookname"));
				paragraphs.setAlbumId(rs.getInt("albumid"));
				paragraphs.setChiefAlbum(rs.getString("ChiefAlbum"));
				paragraphs.setVolume(rs.getString("volume"));
				pList.add(paragraphs);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static ArrayList<Paragraphs> selectBookContentsLinkTarget(Paragraphs para) {

		ArrayList<Paragraphs> pList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT alb.bid albumid ,alb.bookname ChiefAlbum, "
					+ "	 book.volume1 volume, book.bid ,book.bookname,  "
					+ "	 para.gid,para.content,para.trans ,ifnull(rela.tid,0) linkTarId  FROM txparagraphs para  "
					+ "	 inner join txbooks book on para.bid = book.bid  "
					+ "	 inner join txalbum alb on alb.bid = book.albid  "
					+ "  left join txgtrelation  rela on rela.gid = para.gid and rela.tid =  " + para.getLinkTarId();
			sql = sql + " where para.content like '%" + para.getContent() + "%' ";

			if (para.getAlbumId() > 0) {
				sql = sql + " and book.albid =  " + para.getAlbumId();
			}

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Paragraphs paragraphs = new Paragraphs();
				paragraphs.setBid(rs.getInt("bid"));
				paragraphs.setGid(rs.getInt("gid"));
				paragraphs.setContent(rs.getString("content"));
				paragraphs.setTranscation(rs.getString("trans"));
				paragraphs.setBookName(rs.getString("bookname"));
				paragraphs.setAlbumId(rs.getInt("albumid"));
				paragraphs.setChiefAlbum(rs.getString("ChiefAlbum"));
				paragraphs.setVolume(rs.getString("volume"));
				paragraphs.setLinkTarId(rs.getInt("linkTarId"));

				paragraphs.setContent(paragraphs.getContent().replace(para.getContent(),
						TxStringUtil.getHighLine(para.getContent())));

				pList.add(paragraphs);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static ObjTarget selectObjTargetById(int tarid) {

		ObjTarget obj = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT txtargets.id,txtargets.name,txtargets.alias,txtargets.bgtime,txtargets.endtime,txtargets.dynasty,  "
					+ " txtargets.details,ifnull(txtargets.type,0) type,txtargets.addr,txtargets.country,ifnull(ctry.name,'') countryName ,ifnull(dyn.name,'') dynastyName "
					+ "  FROM txtargets  " + "  left join txdynasty dyn on  dyn.id = txtargets.dynasty "
					+ "  left join txcountry ctry on  ctry.id = txtargets.country where txtargets.id = '" + tarid
					+ "' ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				obj = new ObjTarget();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setAlias(rs.getString("alias"));
				obj.setAddress(rs.getString("addr"));
				obj.setDetail(rs.getString("details"));
				obj.setBgtime(rs.getInt("bgtime"));
				obj.setEndtime(rs.getInt("endtime"));
				obj.setCountry(rs.getInt("Country"));
				obj.setCountryName(rs.getString("CountryName"));
				break;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return obj;

	}

	public static ObjTarget selectObjTargetByName(String name) {

		ObjTarget obj = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT txtargets.id,txtargets.name,txtargets.alias,txtargets.bgtime,txtargets.endtime,txtargets.dynasty,  "
					+ "  txtargets.details,ifnull(txtargets.type,0) type,txtargets.addr,txtargets.country,ifnull(ctry.name,'') countryName ,ifnull(dyn.name,'') dynastyName "
					+ "  FROM txtargets  left join txdynasty dyn on  dyn.id = txtargets.dynasty "
					+ "  left join txcountry ctry on  ctry.id = txtargets.country " + "  where txtargets.name = '"
					+ name + "' or txtargets.alias like '%" + name + "%' ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				obj = new ObjTarget();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setAlias(rs.getString("alias"));
				obj.setDetail(rs.getString("details"));
				obj.setAddress(rs.getString("addr"));
				obj.setBgtime(rs.getInt("bgtime"));
				obj.setEndtime(rs.getInt("endtime"));
				obj.setCountry(rs.getInt("Country"));
				obj.setCountryName(rs.getString("CountryName"));
				break;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return obj;

	}

	public static ArrayList<String> selectTargetNamesLike(String name) {

		ArrayList<String> targetNames = new ArrayList<String>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT name FROM txtargets where name like  '%" + name + "%' or alias like  '%" + name
					+ "%' ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				targetNames.add(rs.getString("name"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return targetNames;

	}

	public static void saveObjTarget(ObjTarget obj) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "update txtargets set    name = '" + obj.getName() + "', dynasty =  " + obj.getDynasty()
					+ ",Country =  " + obj.getCountry() + " ,details = '" + obj.getDetail() + "', " + "edittime='"
					+ time + "',neturl='" + obj.getNeturl() + "' ,addrId=" + obj.getAddrId() + ",addr='"
					+ obj.getAddress() + "',bgtime='" + obj.getBgtime() + "' ,endtime='" + obj.getEndtime()
					+ "' where id =   " + obj.getId();
			stmt.executeUpdate(sql);

			System.out.print(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveTargetLink(TargetLink link) {

		TargetLink exLink = selectLink(link.getTargetId(), link.getParagId());

		if (exLink == null) {
			addLink(link.getTargetId(), link.getParagId());

			if (link.getStatus() != TargetLink.STATUS_ADDED_FOR_NEWTAR) {
				link.setStatus(TargetLink.STATUS_ADDED_FOR_TAR);
			}

		} else {
			link.setStatus(TargetLink.STATUS_EXSITS);
		}
	}

	public static TargetLink selectLink(int tid, int gid) {

		TargetLink link = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "select status from  txgtrelation  where gid=" + gid + " and tid=" + tid;

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				link = new TargetLink();
				link.setStatus(rs.getInt("status"));
				link.setParagId(gid);
				link.setTargetId(tid);
				break;
			}

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return link;
	}

	public static void recoverLink(int tid, int gid) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "update  txgtrelation set status = 1, edittime='" + time + "' where gid=" + gid + " and tid="
					+ tid;

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteLink(int tid, int gid) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "update  txgtrelation set status = 3, edittime='" + time + "' where gid=" + gid + " and tid="
					+ tid;

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean addLink(int tid, int gid) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowSortPlusTime();

			String sql = "INSERT INTO txgtrelation (gid,tid,status,edittime ) " + "VALUES (" + gid + "," + tid + ",1,'"
					+ time + "' );";

			stmt.executeUpdate(sql);

			stmt.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	public static ArrayList<Person> selectGovemmentPost(Person post) {

		ArrayList<Person> pList = new ArrayList<Person>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String strSql = " select gid,postname,levflag,levdesc,functions from [txgovpost]  where 0=0  ";

			if (!TxStringUtil.isEmpty(post.getPostName())) {
				strSql = strSql + " and postname like '%" + post.getPostName() + "%'";
			}

			if (!TxStringUtil.isEmpty(post.getPostDetails())) {
				strSql = strSql + " and functions like '%" + post.getPostDetails() + "%'";
			}

			strSql = strSql + " order by ifnull(edittime,0) desc ";

			ResultSet rs = stmt.executeQuery(strSql);
			while (rs.next()) {

				Person person = new Person();

				person.setPostId(rs.getInt("gid"));
				person.setPostName(rs.getString("postname"));
				person.setPostLevel(rs.getString("levflag"));
				person.setLevelDetails(rs.getString("levdesc"));
				person.setPostDetails(rs.getString("functions"));

				pList.add(person);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static void addReadRecord(Paragraphs paragraphs) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT * FROM txreadrecord record  " + "where gid = " + paragraphs.getGid()
					+ "  and ((julianday(strftime('%Y-%m-%d %H:%M',datetime('now','localtime'))) - julianday(datetime(readtime)))*24*60  ) < 5 ";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				stmt.close();
				return;
			}

			String time = TxStringUtil.getNowLongPlusTime();

			sql = "INSERT INTO txreadrecord (bookid,gid,readtime ) " + "VALUES ('" + paragraphs.getBid() + "','"
					+ paragraphs.getGid() + "','" + time + "'  );";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {

		}
	}

	public static void refreshRecord() {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "delete from txreadrecord where rid < (\r\n"
					+ "select min(rid) from (select  rid  from txreadrecord order by rid  desc limit 50))";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Paragraphs> selectRecords(Paragraphs para) {
		ArrayList<Paragraphs> pList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String strSql = " SELECT alb.bid albumid ,alb.bookname ChiefAlbum,  book.volume1 volume,  "
					+ "book.bid ,book.bookname,  para.gid,para.content,para.trans,record.readtime"
					+ "  FROM txreadrecord record " + "inner join txparagraphs para on record.gid = para.gid "
					+ "inner join txbooks book on para.bid = book.bid  and record.bookid = book.bid  "
					+ "inner join txalbum alb on alb.bid = book.albid order by record.readtime desc ";

			ResultSet rs = stmt.executeQuery(strSql);

			while (rs.next()) {

				Paragraphs paragraphs = new Paragraphs();

				paragraphs.setBid(rs.getInt("bid"));
				paragraphs.setGid(rs.getInt("gid"));
				paragraphs.setContent(rs.getString("content"));
				paragraphs.setTranscation(rs.getString("trans"));
				paragraphs.setBookName(rs.getString("bookname"));
				paragraphs.setAlbumId(rs.getInt("albumid"));
				paragraphs.setChiefAlbum(rs.getString("ChiefAlbum"));
				paragraphs.setVolume(rs.getString("volume"));
				paragraphs.setCondition(rs.getString("readtime"));

				pList.add(paragraphs);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static void savePost(Person post) {

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "";
			String time = TxStringUtil.getNowLongPlusTime();
			if (post.getPostId() != 0) {
				sql = "update txgovpost set postname = '" + post.getPostName() + "'," + "levflag = '"
						+ post.getPostLevel() + "'," + "levdesc = '" + post.getLevelDetails() + "'," + "functions = '"
						+ post.getPostDetails() + "',editTime='" + time + "' where gid=" + post.getPostId();
			} else {
				sql = "insert into txgovpost(postname,levflag,levdesc,functions,editTime) values( '"
						+ post.getPostName() + "'," + "'" + post.getPostLevel() + "', '" + post.getLevelDetails()
						+ "', '" + post.getPostDetails() + "','" + time + "')";
			}

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Paragraphs> selectNotes(Paragraphs paragraphs2) {

		ArrayList<Paragraphs> pList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String strSql = " SELECT alb.bid albumid ,alb.bookname ChiefAlbum,  book.volume1 volume,  "
					+ " book.bid ,book.bookname,  ifnull(para.gid,0) gid,note.title, note.content, note.edittime  "
					+ " FROM txnotes note  left join txparagraphs para on note.gid = para.gid  "
					+ " inner join txbooks book on note.bid = book.bid   "
					+ " inner join txalbum alb on alb.bid = book.albid order by note.edittime desc ";

			ResultSet rs = stmt.executeQuery(strSql);

			while (rs.next()) {

				Paragraphs paragraphs = new Paragraphs();

				paragraphs.setBid(rs.getInt("bid"));
				paragraphs.setGid(rs.getInt("gid"));
				paragraphs.setTranscation(rs.getString("title"));
				paragraphs.setContent(rs.getString("content"));
				paragraphs.setBookName(rs.getString("bookname"));
				paragraphs.setAlbumId(rs.getInt("albumid"));
				paragraphs.setChiefAlbum(rs.getString("ChiefAlbum"));
				paragraphs.setVolume(rs.getString("volume"));
				paragraphs.setCondition(rs.getString("edittime"));

				pList.add(paragraphs);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static ArrayList<Book> selectVolumes(int albumnid) {
		ArrayList<Book> bookList = new ArrayList<Book>();

		Statement stmt = null;
		try {

			initConn();

			String sql = " select ifnull(volume1,'未分卷') volume from txbooks where albid = " + albumnid
					+ " group by volume1  ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Book retBook = new Book();

				retBook.setName(rs.getString("volume"));
				bookList.add(retBook);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static ArrayList<Book> selectBooks4Volume(Book albumn) {
		ArrayList<Book> bookList = new ArrayList<Book>();

		Statement stmt = null;
		try {

			initConn();

			String sql = "select txbooks.bid,txbooks.bookname,txbooks.details, txalbum.bid as albumid, "
					+ "txalbum.bookname as ChiefAlbum,  txbooks.vid, txbooks.volume1 as volume  from txbooks "
					+ "inner join txalbum on txbooks.albid = txalbum.bid  " + "where ifnull( txbooks.volume1,'为分卷') = '"
					+ albumn.getVolume() + "' " + "and txbooks.albid = " + albumn.getAlbumId();

			sql = sql + " order by txbooks.sqindex ";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Book retBook = new Book();
				retBook.setBid(rs.getInt("bid"));
				retBook.setName(rs.getString("bookname"));
				retBook.setAlbumId(rs.getInt("albumid"));
				retBook.setChiefAlbum(rs.getString("ChiefAlbum"));
				retBook.setVolume(rs.getString("volume"));
				bookList.add(retBook);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return bookList;
	}

	public static void addNotes(Paragraphs paragraphs) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowLongPlusTime();

			String sql = "INSERT INTO txnotes (bid,gid,title,content,edittime ) " + "VALUES ('" + paragraphs.getBid()
					+ "','" + paragraphs.getGid() + "','" + paragraphs.getBookName() + "','" + paragraphs.getContent()
					+ "','" + time + "'  );";

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {

		}

	}

	public static ObjTarget selectLastObjTarget() {
		ObjTarget obj = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT txtargets.id,txtargets.name,txtargets.bgtime,txtargets.endtime,txtargets.dynasty,  "
					+ " txtargets.details,ifnull(txtargets.type,0) type,txtargets.addr,ifnull(dyn.name,'') dynastyName "
					+ "  FROM txtargets "
					+ "  left join txdynasty dyn on  dyn.id = txtargets.dynasty  order by txtargets.edittime desc limit 1 ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				obj = new ObjTarget();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setAddress(rs.getString("addr"));
				obj.setDetail(rs.getString("details"));
				obj.setDynasty(rs.getInt("dynasty"));
				obj.setBgtime(rs.getInt("bgtime"));
				obj.setEndtime(rs.getInt("endtime"));
				obj.setDynastyName(rs.getString("dynastyName"));
				break;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return obj;
	}

	public static ArrayList<Person> selectPersonsByYear(int year) {
		ArrayList<Person> pList = new ArrayList<Person>();

		Statement stmt = null;
		try {

			initConn();

			String sql = " SELECT tar.id,tar.name,tar.details,citys.name addr ,citys.latitude,citys.longitude "
					+ " FROM txtargets  tar " + " inner join citys on tar.cityid = citys.cid " + " where type=1 ";

			sql = sql + " order by tar.edittime desc limit 10";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Person person = new Person();
				person.setPid(rs.getInt("id"));
				person.setName(rs.getString("name"));
				person.setDetail(rs.getString("details"));
				person.setLongitude(rs.getDouble("Longitude"));
				person.setLatitude(rs.getDouble("Latitude"));
				pList.add(person);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static ArrayList<Address> selectAddress(Address address) {
		ArrayList<Address> pList = new ArrayList<Address>();

		Statement stmt = null;
		try {

			initConn();

			String sql = "select [aid],[name],address,detail , [longitude] , [latitude] ,[cid] , [edittime]  "
					+ "from address where 0=0  ";

			if (address != null && !TxStringUtil.isEmpty(address.getAddress())) {
				sql = sql + "  and address like '%" + address.getAddress() + "%' ";
			}

			if (address != null && !TxStringUtil.isEmpty(address.getName())) {
				sql = sql + "  and name like '%" + address.getName() + "%' ";
			}

			sql = sql + " order by edittime desc";

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Address addr = new Address();
				addr.setAddrId(rs.getInt("aid"));
				addr.setName(rs.getString("name"));
				addr.setDetail(rs.getString("detail"));
				addr.setLongitude(rs.getDouble("longitude"));
				addr.setLatitude(rs.getDouble("latitude"));

				pList.add(addr);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return pList;
	}

	public static ArrayList<Dynasty> selectCountry(Dynasty country) {
		ArrayList<Dynasty> dList = new ArrayList<Dynasty>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT * FROM txcountry  where (bgtime>=" + country.getBgTime() + " and  endtime<="
					+ country.getEndTime() + ") or   (  bgtime<=" + country.getBgTime() + " and  endtime>="
					+ country.getBgTime() + " ) or (  bgtime<=" + country.getEndTime() + " and  endtime>="
					+ country.getEndTime() + " )   order by bgtime ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Dynasty dynasty = new Dynasty();
				dynasty.setId(rs.getInt("id"));
				dynasty.setName(rs.getString("name"));
				dynasty.setBgTime(rs.getInt("bgtime"));
				dynasty.setEndTime(rs.getInt("endtime"));
				dynasty.setCreator(rs.getString("creator"));

				dList.add(dynasty);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return dList;
	}

	public static Address selectAddressByName(String address) {

		Address obj = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT aid,name,address,detail,longitude ,latitude ,cid ,edittime FROM address"
					+ " where name = '" + address + "' ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				obj = new Address();
				obj.setAddrId(rs.getInt("aid"));
				obj.setName(rs.getString("name"));
				obj.setDetail(rs.getString("detail"));
				obj.setAddress(rs.getString("address"));
				obj.setLongitude(rs.getDouble("longitude"));
				obj.setLatitude(rs.getDouble("latitude"));
				break;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return obj;
	}

	public static Address addAddress(String address) {

		Address addr = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String time = TxStringUtil.getNowLongPlusTime();

			String sql = "INSERT INTO address (name,address,detail,edittime ) " + "VALUES ('" + address + "','"
					+ address + "','" + address + "','" + time + "' );";

			stmt.executeUpdate(sql);

			stmt.close();

			addr = selectAddressByName(address);

		} catch (Exception e) {

		}

		return addr;
	}

	public static void emendParagraphs(Paragraphs paragraphs) {
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "update txparagraphs set content = '" + paragraphs.getContent() + "'," + "trans = '"
					+ paragraphs.getTranscation() + "'   where gid=" + paragraphs.getGid();

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ObjTarget selectTargetBaike(int tarid) {

		ObjTarget obj = null;
		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT txtargets.id,txtargets.name, neti.summary,neti.attrs,neti.netname "
					+ "FROM txtargets   inner join txtarnetinfos neti on  neti.tid =  txtargets.id "
					+ " where txtargets.id  ='" + tarid + "' ";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				obj = new ObjTarget();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				obj.setDetail(rs.getString("summary"));
				obj.setAttrList(rs.getString("attrs"));
				obj.setAlias(rs.getString("netname"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}

		return obj;
	}

}
