package com.candata.magic.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.candata.magic.controller.ResultMsg;
import com.candata.magic.pojo.Grade;
import com.candata.magic.pojo.Opus;
import com.candata.magic.pojo.Player;
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
			.setRetryTimes(3)//
			.addHeader("Content-Type", "text/plain")//
			.setSleepTime(3000)//
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
					requesturls.add("http://www.55188.com/viewthread.php?tid="+linkedcode+"&ajaxlist=5");
					
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
			
			page.addTargetRequests(requesturls);
			
		}else{
			System.out.println(page.getUrl().toString());
			XElements xElements = Xsoup.select(page.getHtml().toString(), "//body/");
			Elements elements = xElements.getElements();
			
			System.out.println(elements.size());
			
			List<Player> players=new ArrayList<Player>();
			List<Opus> opuses=new ArrayList<Opus>();
			List<Grade> grades=new ArrayList<Grade>();
			
			for (int i=0;i<elements.size();i+=5) {
				
					Opus opus=new Opus();
					Player player=new Player();
					Grade grade=new Grade();
					
					String ranking=elements.get(i).text();
					String username=elements.get(i+1).text();
					String userlink=elements.get(i+1).attr("href");
					String sccode=elements.get(i+2).text().substring(2);
					String opusid=elements.get(i+2).text().substring(2);
					String stock=elements.get(i+3).text();
					String pid=userlink.substring(userlink.indexOf("uid=")+4);
					
					System.out.println(ranking);
					System.out.println(opusid);
					System.out.println(pid);
					System.out.println(stock);
					System.out.println(sccode);
					
					grade.setRanking(Integer.parseInt(ranking));
					int batchno=Integer.parseInt(page.getUrl().toString().substring(page.getUrl().toString().indexOf("tid=")+4,page.getUrl().toString().indexOf("&")));
					grade.setBatchno(batchno);
					
					grade.setPid(batchno);
					
					grade.setGid((int)System.currentTimeMillis());
					
					System.out.println(batchno);
					
					grade.setPid(Integer.parseInt(pid));
					grade.setOpusid(Integer.parseInt(opusid));
					
					
					opus.setOpusid(Integer.parseInt(opusid));
					opus.setSccode(Integer.parseInt(sccode));
					opus.setStock(stock);
					
					player.setPid(Integer.parseInt(pid));
					player.setUsername(username);
					player.setUserlink(userlink);
					
					grades.add(grade);
					players.add(player);
					opuses.add(opus);
			}
			page.putField("players", players);
			page.putField("opuses", opuses);
			page.putField("grades", grades);
			
		}
			
	}
	
	
	public ResultMsg response(){
		return this.msg;
	}


}
