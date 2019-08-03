package com.gaga.tangshiAnalyze.crawler;

import com.gaga.tangshiAnalyze.crawler.common.Page;
import com.gaga.tangshiAnalyze.crawler.parse.DataPageParse;
import com.gaga.tangshiAnalyze.crawler.parse.DocumentParse;
import com.gaga.tangshiAnalyze.crawler.parse.Parse;
import com.gaga.tangshiAnalyze.crawler.pipeline.ConsolePipeLine;
import com.gaga.tangshiAnalyze.crawler.pipeline.PipeLine;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Crawler {

    private final Logger logger= LoggerFactory.getLogger(Crawler.class);
    /**
     * 放置文档页面（超链接）
     * 未被采集和解析的页面 page-htemlPage,dataSet
     */
    private final Queue<Page> docQueue = new LinkedBlockingDeque<>();

    /**
     * 放置详情页面（处理完成，数据已经在dataSet）
     */
  private final Queue<Page> detailQueue=new LinkedBlockingDeque<>();

    /**
     * 采集器
     */
    private final WebClient webClient;

    /**
     * 所有的解析器
     */
    private final List<Parse> parseList = new LinkedList<>();

    /**
     * 所有的清洗器（管道）
     */
    private final List<PipeLine> pipeLineList = new LinkedList<>();

    /**
     * 线程调度器
     */
    private final ExecutorService executorService;

    public Crawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        //线程调度器
        this.executorService = Executors.newFixedThreadPool(8, new ThreadFactory() {
            private final AtomicInteger id = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler_Thread_" + id.getAndIncrement());
                return thread;
            }
        });
    }

    /**
     * 启动爬虫
     */
    public void start() {
        //爬取、解析文档，清洗数据
        this.executorService.submit(new Runnable() {
            @Override
            public void run() {
                parse();
            }
        });
        this.executorService.submit(new Runnable() {
            @Override
            public void run() {
                pipeline();
            }
        });
    }

    private void parse() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.error("Parse occur an Exception {} .",e.getMessage());
            }

            final Page page = this.docQueue.poll();
            if (page == null) {
                continue;
            }
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        //采集
                        HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getUrl());
                        page.setHtmlPage(htmlPage);
                        for (Parse parse : Crawler.this.parseList) {
                            parse.parse(page);
                        }

                        if(page.isDetail()) {
                            Crawler.this.detailQueue.add(page);
                        }else{

                            Iterator<Page> iterator = page.getSubPage().iterator();
                            while (iterator.hasNext()) {
                                Page subPage = iterator.next();
                                //                  System.out.println(subPage);
                                Crawler.this.docQueue.add(subPage);
                                iterator.remove();

                            }
                        }

                    } catch (IOException e) {
                        logger.error("Parse occur an Exception {} .",e.getMessage());
                    }
                }
            });


        }
    }

    private void pipeline(){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Page page=this.detailQueue.poll();
            if(page==null){
                continue;
            }

            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for(PipeLine pipeLine:Crawler.this.pipeLineList){
                        pipeLine.pipeline(page);
                    }
                }
            });

        }
    }

    public void addPage(Page page) {
        this.docQueue.add(page);
    }

    public void addParse(Parse parse) {
        this.parseList.add(parse);
    }

    public void addPipeLine(PipeLine pipeLine) {
        this.pipeLineList.add(pipeLine);
    }

    /**
     * 停止爬虫
     */
    public void stop() {
        if (this.executorService != null && !this.executorService.isShutdown()) {
            this.executorService.shutdown();
        }
        logger.info("crawler stopped.");
    }

}
