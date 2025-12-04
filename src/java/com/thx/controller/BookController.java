package com.thx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.bean.Address;
import com.book.bean.Book;
import com.book.bean.Dynasty;
import com.book.bean.ObjTarget;
import com.book.bean.Paragraphs;
import com.book.bean.Person;
import com.book.bean.TargetLink;
import com.book.dao.BookSqliteAdapter;
import com.common.KeyListData;
import com.common.TxResponseResult;
import com.common.TxStringUtil;

@RestController("/book")
public class BookController {

	@PostMapping("/addreadrecord")
	@ResponseBody
	public TxResponseResult addReadRecord(@RequestBody Paragraphs paragraphs) {

		BookSqliteAdapter.addReadRecord(paragraphs);

		return TxResponseResult.createSucessResponse(paragraphs);
	}

	@PostMapping("/addnotes")
	@ResponseBody
	public TxResponseResult addNotes(@RequestBody Paragraphs paragraphs) {

		BookSqliteAdapter.addNotes(paragraphs);

		return TxResponseResult.createSucessResponse(paragraphs);
	}

	@PostMapping("/refreshrecord")
	@ResponseBody
	public TxResponseResult refreshRecord() {

		BookSqliteAdapter.refreshRecord();

		return TxResponseResult.createSucessResponse(null);
	}

	@PostMapping("/savepost")
	@ResponseBody
	public TxResponseResult savePost(@RequestBody Person post) {

		BookSqliteAdapter.savePost(post);

		return TxResponseResult.createSucessResponse(post);
	}

	@PostMapping("/emendParagraphs")
	@ResponseBody
	public TxResponseResult emendParagraphs(@RequestBody Paragraphs content) {

		BookSqliteAdapter.emendParagraphs(content);

		return TxResponseResult.createSucessResponse(content);
	}

	@GetMapping("/book/{bookId}")
	public TxResponseResult getInfo(@PathVariable("bookId") Integer bookId) {

		Book book = BookSqliteAdapter.selectOneBook(bookId);
		ArrayList<Paragraphs> paragraphs = BookSqliteAdapter.selectBookContents(book);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("book", book);
		map.put("paras", paragraphs);

		return TxResponseResult.createSucessResponse(map);
	}

	@GetMapping("/selectgovpost")
	@ResponseBody
	public TxResponseResult selectGovemmentPost(Person post) throws IOException {

		ArrayList<Person> targets = BookSqliteAdapter.selectGovemmentPost(post);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selectnotes")
	@ResponseBody
	public TxResponseResult selectNotes(Paragraphs paragraphs) throws IOException {

		ArrayList<Paragraphs> targets = BookSqliteAdapter.selectNotes(paragraphs);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selectRecords")
	@ResponseBody
	public TxResponseResult selectRecords(Paragraphs paragraphs) throws IOException {

		ArrayList<Paragraphs> targets = BookSqliteAdapter.selectRecords(paragraphs);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selectpersons")
	@ResponseBody
	public TxResponseResult selectPersons(Person person) throws IOException {

		ArrayList<Person> targets = BookSqliteAdapter.selectPersons(person);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selectpersonsbyyear")
	@ResponseBody
	public TxResponseResult selectPersonsByYear(int year) throws IOException {

		ArrayList<Person> targets = BookSqliteAdapter.selectPersonsByYear(year);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selectcountry")
	@ResponseBody
	public TxResponseResult selectCountry(Dynasty dynasty) throws IOException {

		ArrayList<Dynasty> Dynastys;
		if (dynasty == null) {
			dynasty = new Dynasty();
			dynasty.setBgTime(-5000);
			dynasty.setEndTime(2000);
		}

		Dynastys = BookSqliteAdapter.selectCountry(dynasty);

		TxResponseResult rr = TxResponseResult.createSucessResponse(Dynastys);

		return rr;

	}

	@GetMapping("/selectdynasty")
	@ResponseBody
	public TxResponseResult selectDynasty(Dynasty dynasty) throws IOException {

		ArrayList<Dynasty> Dynastys;
		if (dynasty == null) {
			dynasty = new Dynasty();
			dynasty.setBgTime(-5000);
			dynasty.setEndTime(2000);
		}

		Dynastys = BookSqliteAdapter.selectDynasty(dynasty);

		TxResponseResult rr = TxResponseResult.createSucessResponse(Dynastys);

		return rr;

	}

	@GetMapping("/selectTargetsByParag")
	@ResponseBody
	public TxResponseResult selectTargetsByParag(int gid) throws IOException {

		ArrayList<ObjTarget> targets = BookSqliteAdapter.selectTargetsWithParagraphs(gid);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selecttargets")
	@ResponseBody
	public TxResponseResult selectTargets(ObjTarget target) throws IOException {

		ArrayList<ObjTarget> targets = BookSqliteAdapter.selectObjTargets(target);

		return TxResponseResult.createSucessResponse(targets);

	}

	@GetMapping("/selectShowTargets")
	@ResponseBody
	public TxResponseResult selectShowTargets(ObjTarget target) throws IOException {

		ArrayList<ObjTarget> targets = BookSqliteAdapter.selectShowObjTargets(target);

		ArrayList<String> targetNames = BookSqliteAdapter.selectTargetNamesLike(target.getName());

		Person post = new Person();
		post.setPostName(target.getName());
		post.setPostDetails(target.getName());
		ArrayList<Person> posts = BookSqliteAdapter.selectGovemmentPost(post);

		if (posts != null) {

			for (Person p : posts) {
				ObjTarget tar = new ObjTarget();
				tar.setName(p.getPostName());
				tar.setDetail(p.getPostLevel() + "," + p.getLevelDetails() + "," + p.getPostDetails());
				targets.add(tar);
			}
		}

		TxResponseResult rrs = TxResponseResult.createSucessResponse(targets);

		rrs.getReportData().put("tarnames", targetNames);
		return rrs;

	}

	@GetMapping("/tardetail")
	@ResponseBody
	public TxResponseResult tardetail(int tarid) throws IOException {
		ObjTarget target = BookSqliteAdapter.selectObjTargetById(tarid);
		ArrayList<Paragraphs> paragraphs = BookSqliteAdapter.selectBookContentsByTarget(tarid);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("target", target);
		map.put("paras", paragraphs);

		return TxResponseResult.createSucessResponse(map);

	}

	@GetMapping("/onetarget")
	@ResponseBody
	public TxResponseResult selectOneTarget(int tarid) throws IOException {
		ObjTarget target = BookSqliteAdapter.selectObjTargetById(tarid);

		return TxResponseResult.createSucessResponse(target);

	}

	@GetMapping("/selectbaike")
	@ResponseBody
	public TxResponseResult selectTargetBaike(int tarid) throws IOException {

		ObjTarget target = BookSqliteAdapter.selectTargetBaike(tarid);

		TxResponseResult trr = TxResponseResult.createSucessResponse(target);

		if (target == null) {
			trr.setReCode(TxResponseResult.CODE_FAILED);
		}

		return trr;

	}

	@GetMapping("/selectcontents")
	@ResponseBody
	public TxResponseResult selectContents(int bookid) throws IOException {
		Book book = BookSqliteAdapter.selectOneBook(bookid);
		ArrayList<Paragraphs> paragraphs = BookSqliteAdapter.selectBookContents(book);

		ArrayList<Book> booksOfVol = BookSqliteAdapter.selectBooksMenu(book);

		booksOfVol.sort((a, b) -> a.compareTo(b));

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("book", book);
		map.put("paras", paragraphs);
		map.put("menu", booksOfVol);

		return TxResponseResult.createSucessResponse(map);

	}

	@GetMapping("/searchParas")
	@ResponseBody
	public TxResponseResult searchParagraphs(Paragraphs content) throws IOException {

		ArrayList<Paragraphs> rrs = null;

		// target
		ObjTarget target = BookSqliteAdapter.selectObjTargetByName(content.getContent());
		if (target == null) {
			target = new ObjTarget();
			target.setName("无");
			target.setDetail("");

			rrs = BookSqliteAdapter.selectBookContentsByKeys(content);

		} else {
			content.setLinkTarId(target.getId());
			rrs = BookSqliteAdapter.selectBookContentsLinkTarget(content);
		}

		TxResponseResult trr = TxResponseResult.createSucessResponse(rrs);
		trr.getReportData().put("tar", target);
		return trr;

	}

	@GetMapping("/selectbooks")
	@ResponseBody
	public TxResponseResult selectBooks(int bookid) throws IOException {

		Book pageable = new Book();
		pageable.setAlbumId(bookid);

		Book album = BookSqliteAdapter.selectOneAlbum(bookid);
		ArrayList<Book> books = BookSqliteAdapter.selectBooks(pageable);

		HashMap<String, KeyListData> reportData = new HashMap<String, KeyListData>();

		ArrayList<KeyListData> keyDataList = new ArrayList<KeyListData>();

		String albumName = "";

		for (Book book : books) {

			albumName = book.getVolume();
			if (TxStringUtil.isEmpty(albumName)) {
				albumName = "<未卷>";
			}

			KeyListData bookList = reportData.get(albumName);

			if (bookList == null) {
				bookList = new KeyListData();
				reportData.put(albumName, bookList);
				bookList.setKey(albumName);

				bookList.getReportData().setName(albumName);
				keyDataList.add(bookList);

			}

			bookList.getList().add(book);

		}

		TxResponseResult ts = TxResponseResult.createSucessResponse(keyDataList);
		ts.getReportData().put("album", album);

		return ts;

	}

	@PostMapping("/savetarget")
	@ResponseBody
	public TxResponseResult saveTarget(@RequestBody ObjTarget target) {

		Address address = null;

		if (!TxStringUtil.isEmpty(target.getAddress()) && target.getAddrId() == 0) {

			address = BookSqliteAdapter.selectAddressByName(target.getAddress());

			if (address == null) {
				address = BookSqliteAdapter.addAddress(target.getAddress());
			}

		}

		if (address != null) {
			target.setAddrId(address.getAddrId());
		}

		if (target.getId() == 0) {
			BookSqliteAdapter.addObjTarget(target);
		} else {
			BookSqliteAdapter.saveObjTarget(target);
		}

		return TxResponseResult.createSucessResponse(target);
	}

	@PostMapping("/addTargetFromGrap")
	@ResponseBody
	public TxResponseResult addTargetFromGrap(@RequestBody TargetLink link) {

		ObjTarget target = new ObjTarget();
		target.setName(link.getTargetName());

		ObjTarget tar = BookSqliteAdapter.selectObjTargetByName(target.getName());
		if (tar == null) {

			BookSqliteAdapter.addObjTarget(target);

			tar = BookSqliteAdapter.selectObjTargetByName(target.getName());

			link.setStatus(TargetLink.STATUS_ADDED_FOR_NEWTAR);
		}

		link.setTargetId(tar.getId());
		BookSqliteAdapter.saveTargetLink(link);

		return TxResponseResult.createSucessResponse(link);

	}

	@PostMapping("/addtarget")
	@ResponseBody
	public TxResponseResult addTarget(@RequestBody ObjTarget target) {

		BookSqliteAdapter.addObjTarget(target);

		return TxResponseResult.createSucessResponse(target);
	}

	@GetMapping("/selectalbums")
	@ResponseBody
	public TxResponseResult selectAlbums() throws IOException {
		ArrayList<Book> rrs = BookSqliteAdapter.selectAlbum();
		return TxResponseResult.createSucessResponse(rrs);

	}

	@GetMapping("/selectvolumes")
	@ResponseBody
	public TxResponseResult selectVolumes(Book albumn) throws IOException {
		ArrayList<Book> rrs = BookSqliteAdapter.selectVolumes(albumn.getAlbumId());
		return TxResponseResult.createSucessResponse(rrs);

	}

	@GetMapping("/selectbooksv")
	@ResponseBody
	public TxResponseResult selectBooks4Volume(Book albumn) throws IOException {
		ArrayList<Book> rrs = BookSqliteAdapter.selectBooks4Volume(albumn);
		return TxResponseResult.createSucessResponse(rrs);

	}

	@PostMapping("/addlink")
	@ResponseBody
	public TxResponseResult addlink(@RequestBody TargetLink link) {

		BookSqliteAdapter.saveTargetLink(link);

		return TxResponseResult.createSucessResponse(link);
	}

	@GetMapping("/01")
	public String getView() {
		return "album";
	}

}
