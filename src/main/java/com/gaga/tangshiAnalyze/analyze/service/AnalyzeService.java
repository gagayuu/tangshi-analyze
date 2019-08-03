package com.gaga.tangshiAnalyze.analyze.service;

import com.gaga.tangshiAnalyze.analyze.model.AuthorCount;
import com.gaga.tangshiAnalyze.analyze.model.WordCloud;

import java.util.List;

public interface AnalyzeService {

    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 词云分析
     * @return
     */
    List<WordCloud> analyzeWordCloud();
}
