webmgic使用教程：
	第一步，首先是添加相关的依赖包
		webmagic-core
		webmagic-extension
	第二步：编写相关代码：
	1，	实现pageprocess
			在process(Page page)方法中，编写页面的抽取逻辑
			a)	·page.addField("","")添加相关的字段
				·page.addTargetRequests("")添加后续处理的url,
			
			 b)·	抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    		private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
			
	 2，写一个main方法启动爬虫
	 		Spider.create(new 页面处理程序的实现类)
	 				.addUrl("添加url的入口")
					.thread(开启线程数)
					.run()//方法启动
					
	主要的实现是在对于页面的抽取，webmagic使用了jsoup、xpath、正则等基本的语法来获取需要的节点，但是作者在其基础之上添加了
	Xsoup对象，这个对象返回的是XElement对象，可以转化为Jsoup的Element对象。
	具体的使用可以参考作者的github。
	对于该功能来说：参考数据库的表protal的设计：需要的字段有：
		theme(varchar),begintime(timestamp),endtime(timestamp),
		link(varchar),linkedcode(varchar),read(int),gid(int)
	
	对抓取页面的分析：
		
		抓取列表的xpath路径：	//*[@id='anchorlink']/form/table/tbody 
		
				theme：	= Xsoup.select(element, "tr/th/span[@class='forumdisplay']/a/text()").get();
				begintime：//*[@id="normalthread_7918217"]/tr/td[2]/em/text();
				endtime：
				link：tr/th/span[@class='forumdisplay']/a/@href;
				linkedcode：element.attr("id").substring(element.attr("id").indexOf("_")+1);
				gid：element.attr("id").substring(element.attr("id").indexOf("_")+1)
				
	然后调用page.addTargetRequest();将待抓取的url放入对象中。
			
		
	