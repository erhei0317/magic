package com.candata.magic.service;

import java.util.List;

import com.candata.magic.pojo.Portal;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MegagamePagePipeline implements Pipeline {

	@Override
	public void process(ResultItems resultitems, Task task) {
		System.out.println(task.getUUID());
		String reqUrl = resultitems.getRequest().getUrl();
		List<Portal> List = resultitems.get("list");
		for (Portal portal : List) {
			System.out.println(portal.getLink());
		}
		
	}

}
