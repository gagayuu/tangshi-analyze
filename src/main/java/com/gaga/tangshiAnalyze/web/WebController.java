package com.gaga.tangshiAnalyze.web;

import com.gaga.tangshiAnalyze.analyze.model.AuthorCount;
import com.gaga.tangshiAnalyze.analyze.model.WordCloud;
import com.gaga.tangshiAnalyze.analyze.service.AnalyzeService;
import com.gaga.tangshiAnalyze.config.ObjectFactory;
import com.gaga.tangshiAnalyze.crawler.Crawler;
import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

/**
 * Web API
 * 1.Spark Java框架完成Web API开发
 *
 */

public class WebController {

    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    //http://127.0.0.1:4567/
    //analyze/author_count
    public List<AuthorCount> analyzeAuthorCount() {
        return analyzeService.analyzeAuthorCount();
    }

    //http://127.0.0.1:4567/
    //analyze/word_cloud
    public List<WordCloud> analyzeWordCloud() {
        return analyzeService.analyzeWordCloud();
    }

    public void launch() {
        ResponseTransformer transformer=new JSONResponseTransformer();

        //src/main/resources/static
        Spark.staticFileLocation("/static");

        Spark.get("/analyze/author_count", ((request, response) -> analyzeAuthorCount()),transformer);
        Spark.get("/analyze/word_cloud",((request, response) -> analyzeWordCloud()),transformer);
        Spark.get("/crawler/stop",(((request, response) -> {
            Crawler crawler= ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.stop();
            return "crawler stopped";
        } )));
    }

    public static class JSONResponseTransformer implements ResponseTransformer{

        private Gson gson=new Gson();
        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }
}

