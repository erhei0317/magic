# magic
用webmagic测试的一个网络爬虫，来抓取一些资源信息并保到数据库中，使用的spring、mybatis，主要是利用druid的数据源。
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

​	

增加了quartz定时作业

​	在maven依赖：

```xml

<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>4.1.3.RELEASE</version>
</dependency>
```

​	编写一个Job

```java
public class Scheduler {
	private static Logger logger = Logger.getLogger(Scheduler.class);
	private static String sourceURL = "http://www.55188.com/forumdisplay.php?fid=8&filter=type&typeid=153";

	// 这就是一个普通的java类，方法名称随便写
	public void execute(){
		logger.info("抓取任务开始了--------begining");
		Spider.create(new MegagamePageProcessor())//
				.addPipeline(new MegagamePagePipeline())
				.addPipeline(new JsonFilePipeline("D:\\candata_web"))
				.addUrl(sourceURL)//
				.thread(8)
				.run();
		logger.info("抓取任务结束");
	}
}
```

 	添加spring-quartz的配置文件：

a):	声明这个bean，并包装为JobDetails，说明调用某个类的某个方法

b):	定制触发器Trigger，cron配置触发执行时间

c)：	在Scheduler中配置触发事件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:context="http://www.springframework.org/schema/context" 
xsi:schemaLocation="
http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context-3.0.xsd
">


	<bean id="scheduler" class="com.candata.magic.scheduler.Scheduler"></bean>
	
	<bean id="SpringQtzJobMethod"
		class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
		<property name="targetObject"> 
			<ref bean="scheduler" />
		</property>
		<property name="targetMethod">  <!-- 要执行的方法名称 -->
			<value>execute</value>
		</property>
	</bean>
	
	<bean id="cronTriggerFactoryBean" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean ">
		<property name="jobDetail" ref="SpringQtzJobMethod"></property>
		<property name="cronExpression" value="0 5 22 * * ?"></property>
	</bean>
	
	
	<bean id="SpringJobSchedulerFactoryBean"
		class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
		<property name="triggers">
			<list>
				<ref bean="cronTriggerFactoryBean" />
			</list>
		</property>
	</bean>
	
</beans>
```



webmgic使用教程：

	第一步，首先是添加相关的依赖包
		webmagic-core
		webmagic-extension
	第二步：编写相关代码：
	1，	实现pageprocess
			在process(Page page)方法中，编写页面的抽取逻辑
			a)		·page.addField("","")添加相关的字段
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


​	
	util时间类型与数据库时间类型java.util.Date cannot be cast to java.sql.Date:
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());


	新增加了quartz定时作业的任务
​		