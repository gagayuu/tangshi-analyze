package com.gaga.tangshi;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

//<div class="contson" id="contson09c2099ad0f1">
//                    塞马南来，五陵草树无颜色。云气黯，鼓鼙声震，天穿地裂。百二河山俱失险，将军束手无筹策。渐烟尘、飞度九重城，蒙金阙。<br>长戈袅，飞鸟绝。原厌肉，川流血。叹人生此际，动成长别。回首玉津春色早，雕栏犹挂当时月，更西来、流水绕城根，空呜咽。
//                </div>
public class TestHtmlUnit {
    public static void main(String[] args) {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(false);
            HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org/shiwenv_eeb217f8cb2d.aspx");

            HtmlElement body = htmlPage.getBody();

            //标题
            String titlePath = "//div[@class='cont']/h1/text()";
            DomText titleDom = (DomText) body.getByXPath(titlePath).get(0);
            String title = titleDom.asText();

            //作者
            String authorPath = "//div[@class='cont']/p/a[2]";
            HtmlAnchor anchorDom = (HtmlAnchor) body.getByXPath(authorPath).get(0);
            String author = anchorDom.asText();

            //朝代
            String dynastyPath = "//div[@class='cont']/p/a[1]";
            HtmlAnchor dynastyDom = (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
            String dynasty = dynastyDom.asText();

            //正文
            String contentPath = "//div[@class='cont']/div[@class='contson']";
            HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
            String content = contentDom.asText();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
