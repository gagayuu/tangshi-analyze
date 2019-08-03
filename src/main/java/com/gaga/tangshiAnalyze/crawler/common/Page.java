package com.gaga.tangshiAnalyze.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Page {

    /**
     * 数据网站的根地址：
     * https://so.gushiwen.org
     */
    private final String base;

    /**
     * 具体网页的路径：
     * /shiwenv_09c2099ad0f1.aspx
     */
    private final String path;

    /**
     * 网页dom对象
     */
    private HtmlPage htmlPage;

    /**
     * 标识网页是否是详情页
     */
    private final boolean detail;

    /**
     * 子页面对象集合
     */
    private Set<Page> subPage = new HashSet<>();

    /**
     * 数据对象
     */
    private DataSet dataSet = new DataSet();

    public String getUrl() {
        return this.base + this.path;
    }

}
