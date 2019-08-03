package com.gaga.tangshiAnalyze.crawler.parse;

import com.gaga.tangshiAnalyze.crawler.common.Page;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 文档页面（只是超链接）解析
 */
public class DocumentParse implements Parse {
    @Override
    public void parse(Page page) {
        if (page.isDetail()) {
            return;
        }
        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage.getBody()
                .getElementsByAttribute("div", "class", "typecont")
                .forEach(div -> {
                    DomNodeList<HtmlElement> aNodeList = div.getElementsByTagName("a");
                    aNodeList.forEach(aNode -> {
                        String path = aNode.getAttribute("href");
                        Page subPage = new Page(page.getBase(), path, true);
                        page.getSubPage().add(subPage);
                    });
                });

    }
}
