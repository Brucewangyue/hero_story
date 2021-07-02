package org.tinygame.herostory.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class DbHelper {
    static private SqlSessionFactory sqlSessionFactory ;

    private DbHelper(){}

    static public  void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory= new SqlSessionFactoryBuilder().build(resourceAsStream);
    }

    static public SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
