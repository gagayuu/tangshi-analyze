package com.gaga.tangshiAnalyze.crawler.pipeline;

import com.gaga.tangshiAnalyze.crawler.common.Page;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabasePipeline implements PipeLine {

    private final DataSource dataSource;

    public DatabasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void pipeline(Page page) {


        String title= (String) page.getDataSet().getData("title");
        String dynasty= (String) page.getDataSet().getData("dynasty");
        String author= (String) page.getDataSet().getData("author");
        String content= (String) page.getDataSet().getData("content");

        String sql="insert into poetry_info(title,dynasty,author,content) values(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement=connection.prepareStatement(sql)) {
            statement.setString(1,title);
            statement.setString(2,dynasty);
            statement.setString(3,author);
            statement.setString(4,content);

            statement.executeUpdate();

        } catch (SQLException e) {

        }
    }


}
