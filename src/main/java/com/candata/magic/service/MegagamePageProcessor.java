package com.candata.magic.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.candata.magic.controller.ResultMsg;
import com.candata.magic.pojo.Portal;
import com.candata.magic.utils.UserAgentUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.XElements;
import us.codecraft.xsoup.Xsoup;

public class MegagamePageProcessor implements PageProcessor {

	private static String sourceURL = "http://www.55188.com/forumdisplay.php?fid=8&filter=type&typeid=153";
	private Site site = Site.me()//
			.setRetryTimes(1)//
			.addHeader("Content-Type", "text/plain")//
			.setSleepTime(1000)//
			.setUserAgent(UserAgentUtils.radomUserAgent());
	
	ResultMsg msg;
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		List<String> requesturls =null;
		if (page.getUrl().toString().equals(sourceURL)) {
			XElements xElements = Xsoup.select(page.getHtml().toString(), "//*[@id='anchorlink']/form/table/tbody");
			Elements elements = xElements.getElements();
			int count = 0;
			List<Portal> protallist = new ArrayList<Portal>();
			msg=new ResultMsg();
			requesturls = new ArrayList<String>();
			for (Element element : elements) {
				String strType = element.select("tr td img").attr("title");
				if (strType.equals("荐股比赛")) {
					Portal portal = new Portal();
					String linkedcode = element.attr("id").substring(element.attr("id").indexOf("_") + 1);
					String theme = Xsoup.select(element, "tr/th/span[@class='forumdisplay']/a/text()").get();
					String link = Xsoup.select(element, "tr/th/span[@class='forumdisplay']/a/@href").get();
					String begintime = Xsoup.select(element, "tr/td[@class='author']/em/text()").get();
					count++;
					
					portal.setTid((int) System.currentTimeMillis());
					portal.setLinkedcode(linkedcode);
					portal.setTheme(theme);
					portal.setLink(link);
					System.out.println(link);
					requesturls.add(link);
					portal.setGid(Integer.parseInt(linkedcode));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						if (begintime != null) {
							date = sdf.parse(begintime);
							portal.setBegintime(date);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					protallist.add(portal);
				}
				page.putField("list", protallist);
			}
			
/*			msg.setCount(count);
			msg.setList(protallist);
			msg.setMsg("0k");*/
		
		}else{
			
		}
		page.addTargetRequests(requesturls);	
	}
	
	
	public ResultMsg response(){
		return this.msg;
	}

	public static void main(String[] args) {
		Spider.create(new MegagamePageProcessor())//
				.addUrl(sourceURL)//
				.addPipeline(new MegagamePagePipeline())
				.thread(4)//
				.run();

	}

}
