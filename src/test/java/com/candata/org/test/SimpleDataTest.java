package com.candata.org.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import com.candata.magic.service.MegagamePagePipeline;
import com.candata.magic.service.MegagamePageProcessor;

public class SimpleDataTest {

	 public static void main(String[] args) throws ParseException {
/*		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2017-3-29");*/
		 
	//	 System.out.println(System.currentTimeMillis());;
		 
/*		String str="http://www.55188.com/viewthread.php?tid=7895018&ajaxlist=5";
		String s=str.substring(str.indexOf("tid=")+4,str.indexOf("&"));
		System.out.println(s);*/
		 
		 
			String sourceURL = "http://www.55188.com/forumdisplay.php?fid=8&filter=type&typeid=153&page=2";

			Spider.create(new MegagamePageProcessor())//
			.addPipeline(new MegagamePagePipeline())
			.addPipeline(new JsonFilePipeline("D:\\candata_web"))
			.addUrl(sourceURL)//
			.thread(8)
			.run();
		
	}
}
