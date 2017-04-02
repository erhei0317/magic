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
			String sourceURL = "http://www.55188.com/forumdisplay.php?fid=8&filter=type&typeid=153";
			Spider.create(new MegagamePageProcessor())//
			.addPipeline(new MegagamePagePipeline())
			.addPipeline(new JsonFilePipeline("D:\\candata_web"))
			.addUrl(sourceURL)//
			.thread(8)
			.run();
		
	}
}
