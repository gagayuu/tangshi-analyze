package com.gaga.tangshiAnalyze.analyze.service.Impl;

import com.gaga.tangshiAnalyze.analyze.entity.PoetryInfo;
import com.gaga.tangshiAnalyze.analyze.dao.AnalyzeDao;
import com.gaga.tangshiAnalyze.analyze.model.AuthorCount;
import com.gaga.tangshiAnalyze.analyze.model.WordCloud;
import com.gaga.tangshiAnalyze.analyze.service.AnalyzeService;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

public class AnalyzeServiceImpl implements AnalyzeService {

    private final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        return analyzeDao.analyzeAuthorCount();
    }

    @Override
    public List<WordCloud> analyzeWordCloud() {

        //1.查询出所有的数据
        //2.取出 title content
        //3.分词,过滤
        //4.统计，key-词，value-词频

        Map<String,Integer> map=new HashMap<>();
        List<PoetryInfo> poetryInfos=analyzeDao.queryAllPoetry();

        for(PoetryInfo poetryInfo:poetryInfos){

            List<Term> terms=new ArrayList<>();

            String title=poetryInfo.getTitle();
            String content=poetryInfo.getContent();

            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            Iterator<Term> iterator=terms.iterator();
            while(iterator.hasNext()){
                Term term=iterator.next();

                //词性过滤
                if(term.getNatureStr()==null || term.getNatureStr().equals("w") || term.equals("d")
                || term.equals("uj") || term.equals("j") || term.equals("q") || term.equals("p")
                || term.equals("f") || term.equals("c") || term.equals("e") || term.equals("nr")){
                    iterator.remove();
                    continue;
                }

                //词的长度过滤
                if(term.getRealName().length()<2  || term.getRealName().length()>6){
                    iterator.remove();
                    continue;
                }

                //统计
                String realName=term.getRealName();
                int count=0;
                if(map.containsKey(realName)){
                    count=map.get(realName)+1;
                }else{
                    count=1;
                }
                map.put(realName,count);
            }
        }

        List<WordCloud> wordClouds=new ArrayList<>();
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            WordCloud wordCloud=new WordCloud();
            wordCloud.setWord(entry.getKey());
            wordCloud.setCount(entry.getValue());
            wordClouds.add(wordCloud);
        }
        return wordClouds;
    }
}
