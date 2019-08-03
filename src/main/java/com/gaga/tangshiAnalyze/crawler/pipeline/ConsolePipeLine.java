package com.gaga.tangshiAnalyze.crawler.pipeline;

import com.gaga.tangshiAnalyze.crawler.common.Page;

import java.util.Map;

public class ConsolePipeLine implements PipeLine{

    @Override
    public void pipeline(Page page) {
        Map<String,Object> data=page.getDataSet().getData();
        System.out.println(data);//存储
    }
}
