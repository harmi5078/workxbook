package com.book.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.book.bean.ObjTarget;
import com.book.bean.Paragraphs;
import com.book.echart.bean.Chainlink;
import com.book.echart.bean.EchartLine;
import com.book.echart.bean.EchartNode;
import com.common.TxProperties;
import com.common.TxStringUtil;

public class EchartSqliteAdapter {

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

	public static ArrayList<Chainlink> selectLinkTargetsLink(ObjTarget source) {
		ArrayList<Chainlink> pList = new ArrayList<Chainlink>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT  para.gid,"
					+ "rela.tid SourceId, tar.name SourceName,ifnull(dyn.name,'根') dynastyName1,"
					+ "rela2.tid targetId,tar2.name targetName,ifnull(dyn2.name,'一级') dynastyName2 ,para.content "
					+ " FROM txparagraphs para " + " inner join txgtrelation  rela on rela.gid = para.gid  "
					+ " inner join txgtrelation  rela2 on rela2.gid = rela.gid  "
					+ " inner join txtargets tar on tar.id =rela.tid "
					+ " inner join txtargets tar2 on tar2.id =rela2.tid  "
					+ " left join txcountry dyn on  dyn.id = tar.dynasty "
					+ " left join txcountry dyn2 on   dyn2.id = tar2.country where rela.tid = " + source.getId();

			ResultSet rs = stmt.executeQuery(sql);

			ArrayList<Integer> sbSourceids = new ArrayList<Integer>();
			ArrayList<Integer> sbTarids = new ArrayList<Integer>();
			ArrayList<Integer> sbGids = new ArrayList<Integer>();

			sbSourceids.add(source.getId());

			EchartNode sourceNode = new EchartNode();
			sourceNode.setCategory(source.getDynastyName());
			sourceNode.setSymbolSize("20");
			sourceNode.setName(source.getName());
			sourceNode.setId(String.valueOf(source.getId()));

			while (rs.next()) {
				EchartLine line = new EchartLine();
				int gid = rs.getInt("gid");
				int targetId = rs.getInt("targetId");
				int sourceId = rs.getInt("SourceId");

				if (targetId == source.getId()) {
					continue;
				}

				line.setCategory(String.valueOf(gid));
				line.setSource(String.valueOf(sourceId));
				line.setTarget(String.valueOf(targetId));

				EchartNode tarNode = new EchartNode();
				tarNode.setCategory(rs.getString("dynastyName2"));
				tarNode.setSymbolSize("15");
				tarNode.setName(rs.getString("targetName"));
				tarNode.setId(String.valueOf(targetId));

				Chainlink cl = new Chainlink();
				cl.setLine(line);
				cl.setSource(sourceNode);
				cl.setTarget(tarNode);

				pList.add(cl);

				sbGids.add(gid);
				sbTarids.add(targetId);

				// 先避免网状关系
				sbSourceids.add(targetId);
			}
			rs.close();
			stmt.close();

			String strTarIds = sbTarids.toString();
			strTarIds = strTarIds.replace("[", "(").replace("]", ")");

			String strGids = sbTarids.toString();
			strGids = strGids.replace("[", "(").replace("]", ")");

			String strSourceIds = sbSourceids.toString();
			strSourceIds = strSourceIds.replace("[", "(").replace("]", ")");

			sql = "SELECT  para.gid,   sourceRela.tid SourceId, tar.name SourceName,ifnull(dyn.name,'一级') dynastyName1,"
					+ "	rela2.tid targetId,tar2.name targetName,ifnull(dyn2.name,'二级') dynastyName2 ,para.content "
					+ "	FROM txparagraphs para  "
					+ " inner join txgtrelation  sourceRela on sourceRela.gid = para.gid  "
					+ " inner join txgtrelation  rela2 on rela2.gid = sourceRela.gid  "
					+ " inner join txtargets tar on tar.id =sourceRela.tid "
					+ " inner join txtargets tar2 on tar2.id =rela2.tid   "
					+ " left join txcountry dyn on  dyn.id = tar.dynasty "
					+ " left join txcountry dyn2 on   dyn2.id = tar2.country where sourceRela.tid in " + strTarIds
					+ " and para.gid not in " + strGids + " and rela2.tid not in " + strSourceIds;

			stmt = connection.createStatement();
			ResultSet rs2 = stmt.executeQuery(sql);
			while (rs2.next()) {
				EchartLine line = new EchartLine();
				int gid = rs2.getInt("gid");
				int targetId = rs2.getInt("targetId");
				int sourceId = rs2.getInt("SourceId");

				if (targetId == sourceId) {
					continue;
				}

				line.setCategory(String.valueOf(gid));
				line.setSource(String.valueOf(sourceId));
				line.setTarget(String.valueOf(targetId));

				EchartNode tarNode = new EchartNode();
				tarNode.setCategory(rs2.getString("dynastyName2"));
				tarNode.setSymbolSize("15");
				tarNode.setName(rs2.getString("targetName"));
				tarNode.setId(String.valueOf(targetId));

				sourceNode = new EchartNode();
				sourceNode.setCategory(rs2.getString("dynastyName1"));
				sourceNode.setSymbolSize("20");
				sourceNode.setName(rs2.getString("SourceName"));
				sourceNode.setId(String.valueOf(sourceId));

				Chainlink cl = new Chainlink();
				cl.setLine(line);
				cl.setSource(sourceNode);
				cl.setTarget(tarNode);

				pList.add(cl);

			}
			rs2.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pList;
	}

	public static ArrayList<Paragraphs> selectLinkContents(EchartLine line) {

		ArrayList<Paragraphs> pList = new ArrayList<Paragraphs>();

		Statement stmt = null;
		try {

			initConn();

			stmt = connection.createStatement();

			String sql = "SELECT  alb.bid albumid ,alb.bookname ChiefAlbum,  "
					+ "book.volume1 volume, book.bid ,book.bookname, " + "para.gid,para.content,para.trans , "
					+ "rela.tid SourceId, tar.name SourceName, " + "rela2.tid targetId,tar2.name targetName "
					+ "FROM txparagraphs para " + "inner join txbooks book on para.bid = book.bid  "
					+ "inner join txalbum alb on alb.bid = book.albid  "
					+ "inner join txgtrelation  rela on rela.gid = para.gid  "
					+ "inner join txgtrelation  rela2 on rela2.gid = rela.gid "
					+ "inner join txtargets tar on tar.id =rela.tid "
					+ "inner join txtargets tar2 on tar2.id =rela2.tid  " + "where rela2.tid = " + line.getTarget()
					+ " and rela.tid=" + line.getSource() + "";

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
}
