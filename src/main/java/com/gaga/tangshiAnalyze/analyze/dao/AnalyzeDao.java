package com.gaga.tangshiAnalyze.analyze.dao;

import com.gaga.tangshiAnalyze.analyze.entity.PoetryInfo;
import com.gaga.tangshiAnalyze.analyze.model.AuthorCount;

import java.util.List;

public interface AnalyzeDao {

    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 查询所有的诗文，提供给业务层进行分析
     * @return
     */
    List<PoetryInfo> queryAllPoetry();
}
