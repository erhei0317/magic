package com.candata.magic.service;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.xsoup.XElements;
import us.codecraft.xsoup.Xsoup;

import com.candata.magic.pojo.Portal;

public class XsoupTest {
	public static void main(String[] args) throws MalformedURLException, IOException {
		String sourceURL="http://www.55188.com/forumdisplay.php?fid=8&filter=type&typeid=153";
		XElements xElements = Xsoup.select(Jsoup.parse(new URL(sourceURL), 3000), "//*[@id='anchorlink']/form/table/tbody");
		Elements elements = xElements.getElements();
		int count=0;
		List<Portal> protallist=new ArrayList<Portal>();
		for (Element element : elements) {
			String strType=element.select("tr td img").attr("title");
			if(strType.equals("荐股比赛")){
				Portal portal=new Portal();
				String linkedcode=element.attr("id").substring(element.attr("id").indexOf("_")+1);
				String theme = Xsoup.select(element, "tr/th/span[@class='forumdisplay']/a/text()").get();
				String link = Xsoup.select(element, "tr/th/span[@class='forumdisplay']/a/@href").get();
				String begintime = Xsoup.select(element, "tr/td[@class='author']/em/text()").get();
				count++;
				portal.setTid((int)System.currentTimeMillis());
				portal.setLinkedcode(linkedcode);
				portal.setTheme(theme);
				portal.setLink(link);
				portal.setGid(Integer.parseInt(linkedcode));
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					if(begintime!=null){
						System.out.println(begintime);
						date = sdf.parse(begintime);
						portal.setBegintime(date);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				protallist.add(portal);
			}
		}
		
	}
}