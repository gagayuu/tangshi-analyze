package com.gaga.tangshiAnalyze.crawler.parse;

import com.gaga.tangshiAnalyze.crawler.common.Page;
import com.gargoylesoftware.htmlunit.html.*;


/**
 * 详情页面（数据）解析
 */
public class DataPageParse implements Parse {
    @Override
    public void parse(Page page) {
        if(!page.isDetail()){
            return;
        }
        ///html/body/div[3]/div[1]/div[2]/div[1]/h1
        //body > div.main3 > div.left > div:nth-child(2) > div.cont > h1
        HtmlPage htmlPage= page.getHtmlPage();
        HtmlElement body=htmlPage.getBody();

        //标题
        String titlePath="//div[@class='cont']/h1/text()";
        DomText titleDom= (DomText) body.getByXPath(titlePath).get(0);
        String title=titleDom.asText();

        //作者
        String authorPath="//div[@class='cont']/p/a[2]";
        HtmlAnchor anchorDom= (HtmlAnchor) body.getByXPath(authorPath).get(0);
        String author=anchorDom.asText();

        //朝代
        String dynastyPath="//div[@class='cont']/p/a[1]";
        HtmlAnchor dynastyDom= (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
        String dynasty=dynastyDom.asText();

        //正文
        String contentPath="//div[@class='cont']/div[@class='contson']";
        HtmlDivision contentDom= (HtmlDivision) body.getByXPath(contentPath).get(0);
        String content=contentDom.asText();


        page.getDataSet().putData("title",title);
        page.getDataSet().putData("dynasty",dynasty);
        page.getDataSet().putData("author",author);
        page.getDataSet().putData("content",content);

    }
}
