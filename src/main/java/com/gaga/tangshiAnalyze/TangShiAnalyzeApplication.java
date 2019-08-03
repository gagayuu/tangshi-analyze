package com.gaga.tangshiAnalyze;


import com.gaga.tangshiAnalyze.config.ObjectFactory;
import com.gaga.tangshiAnalyze.crawler.Crawler;
import com.gaga.tangshiAnalyze.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Launcher;


public class TangShiAnalyzeApplication {

    private static final Logger LOGGER= LoggerFactory.getLogger(TangShiAnalyzeApplication.class);

    public static void main(String[] args) {

        WebController webController = ObjectFactory.getInstance().getObject(WebController.class);
        LOGGER.info("Web Server Launch...");
        webController.launch();

        if(args.length==1 &&args[0].equals("crawler_start")) {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            LOGGER.info("crawler start...");
            crawler.start();
        }

    }
}
