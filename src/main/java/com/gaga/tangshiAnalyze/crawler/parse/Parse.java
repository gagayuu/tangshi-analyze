package com.gaga.tangshiAnalyze.crawler.parse;

import com.gaga.tangshiAnalyze.crawler.common.Page;

public interface Parse {
    /**
     * 解析页面
     * @param page
     */
    void parse(Page page);
}
