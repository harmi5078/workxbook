package com.thx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.bean.Address;
import com.book.bean.ObjTarget;
import com.book.bean.Paragraphs;
import com.book.dao.BookSqliteAdapter;
import com.book.dao.EchartSqliteAdapter;
import com.book.echart.bean.Category;
import com.book.echart.bean.Chainlink;
import com.book.echart.bean.EchartLine;
import com.book.echart.bean.EchartNode;
import com.common.TxResponseResult;

@RestController("/emap")
public class EchartController {

	@GetMapping("/selectaddress")
	@ResponseBody
	public TxResponseResult selectAddress(Address address) throws IOException {

		ArrayList<Address> addresses;

		addresses = BookSqliteAdapter.selectAddress(address);

		return TxResponseResult.createSucessResponse(addresses);

	}

	@GetMapping("/tar2links")
	@ResponseBody
	public TxResponseResult tar2Links(EchartLine line) throws IOException {

		ArrayList<Paragraphs> paragraphs = EchartSqliteAdapter.selectLinkContents(line);

		return TxResponseResult.createSucessResponse(paragraphs);

	}

	@GetMapping("/perschartlink")
	@ResponseBody
	public TxResponseResult perschartlink(int startTarid) throws IOException {

		ObjTarget target = null;
		if (startTarid > 0) {
			target = BookSqliteAdapter.selectObjTargetById(startTarid);
		} else {
			target = BookSqliteAdapter.selectLastObjTarget();
		}

		ArrayList<Chainlink> chains = EchartSqliteAdapter.selectLinkTargetsLink(target);

		ArrayList<EchartLine> links = new ArrayList<EchartLine>();
		ArrayList<EchartNode> pers = new ArrayList<EchartNode>();
		ArrayList<Category> categories = new ArrayList<Category>();
		ArrayList<String> categorieList = new ArrayList<String>();

		EchartNode p = new EchartNode();
		p.setCategory(target.getCountryName());
		p.setSymbolSize("20");
		p.setName(target.getName());
		p.setId(String.valueOf(target.getId()));

		pers.add(p);

		if (!categorieList.contains(p.getCategory())) {
			Category c = new Category();
			c.setName(p.getCategory());
			categories.add(c);
			categorieList.add(p.getCategory());
		}

		StringBuffer sb = new StringBuffer();
		sb.append("kkk");

		StringBuffer sb2 = new StringBuffer();
		sb2.append("kkk");

		for (Chainlink chain : chains) {

			if (chain.getSource().getId() == chain.getTarget().getId()) {
				continue;
			}

			if (sb2.indexOf(chain.getSource().getId() + "||" + chain.getTarget().getId()) > 0) {
				continue;
			}

			EchartLine link = new EchartLine();
			link.setSource(String.valueOf(chain.getSource().getId()));
			link.setTarget(String.valueOf(chain.getTarget().getId()));
			link.setCategory(String.valueOf(chain.getLine().getCategory()));
			link.setSourceName(chain.getSource().getName());
			link.setTargetName(chain.getTarget().getName());

			links.add(link);

			if (sb.indexOf(String.valueOf(chain.getTarget().getId())) > 0) {
				continue;
			}

			p = new EchartNode();
			p.setCategory(chain.getTarget().getCategory());
			p.setName(chain.getTarget().getName());
			p.setId(String.valueOf(chain.getTarget().getId()));

			pers.add(p);

			if (!categorieList.contains(p.getCategory())) {
				Category c = new Category();
				c.setName(p.getCategory());
				categories.add(c);

				categorieList.add(p.getCategory());
			}

			sb.append("KK").append(String.valueOf(chain.getTarget().getId()));
			sb2.append("KK").append(chain.getSource().getId() + "||" + chain.getTarget().getId());

		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("targets", pers);
		map.put("links", links);
		map.put("categories", categories);

		return TxResponseResult.createSucessResponse(map);

	}
}
