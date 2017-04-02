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
	
	
	
	util时间类型与数据库时间类型java.util.Date cannot be cast to java.sql.Date:
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		
	
	
	此程序通过入口程序：controller包下的RequestApp启动main方法，其中之后会开启两个线程，一个线程是启动undertow服务器，一个线程是启动spring容器，
	spring容器启动后，利用spring容器去调用quartz的scheduler，调度后台程序去从理想论坛去抓取荐股活动的最新资源信息，保存在数据库中。然后通过http请求，
	
	http:\\localhost:8080?pageindex=0&pagesize=10；
	
	注意：参数pageindex和pagesize必须要有。
	访问服务器后返回json的数据格式信息。
	上面的信息，也就是说提供了restful接口的服务，通过url请求，拿到JSON的数据格式，通过前端程序员在页面上展示。
	
	
	本例可以自己创建一个访问资源的页面请求json数据即可，通过
	以下提供的显示方式：
	在页面中添加样式表：
	
	<style>
	pre {outline: 1px solid #ccc; padding: 5px; margin: 5px; }
	.string { color: green; }
	.number { color: darkorange; }
	.boolean { color: blue; }
	.null { color: magenta; }
	.key { color: red; }
	</style>
	
	样式表添加在完之后，在页面body体中添加
	<pre id="result"></pre>
	
	引入以下JavaScript脚本：
	<script>
			function syntaxHighlight(json) {
		    if (typeof json != 'string') {
		        json = JSON.stringify(json, undefined, 2);
		    }
		    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
		    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
		        var cls = 'number';
		        if (/^"/.test(match)) {
		            if (/:$/.test(match)) {
		                cls = 'key';
		            } else {
		                cls = 'string';
		            }
		        } else if (/true|false/.test(match)) {
		            cls = 'boolean';
		        } else if (/null/.test(match)) {
		            cls = 'null';
		        }
		        return '<span class="' + cls + '">' + match + '</span>';
		    });
		}
		</script>
		
	通过ajax方法调用该方法，该方法的参数就是服务器端的JSON
	为了页面好看，可以在ajax方法中，直接调用 $('#result').html(syntaxHighlight(res));
	
	
	
		
		

			
		
	