package com.atlantis;

import com.atlantis.dao.UserDAO;
import com.atlantis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestMyBatis {

    /**
     * 基本的MyBatis开发步骤
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserDAO userDAO = sqlSession.getMapper(UserDAO.class); // 代理设计模式
        List<User> users = userDAO.queryAllUsers();

        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

    /**
     * SQLSession 的第二种用法
     */
    @Test
    public void test2() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("com.xutp.dao.UserDAO.queryAllUsers");
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void testJDBC() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_mybatis?useSSL=false", "root", "root");
        Statement statement = conn.createStatement();
        statement.execute("select * from t_user");
        ResultSet resultSet = statement.getResultSet();
    }

    /**
     * 用于测试
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.insert("");
    }

    /**
     * 用于测试 MyBatis Proxy代理
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Class[] interfaces = new Class[]{UserDAO.class};

        UserDAO userDAO = (UserDAO) Proxy.newProxyInstance(TestMyBatis.class.getClassLoader(),
                interfaces,
                new MyMapperProxy(sqlSession, UserDAO.class));

        List<User> users = userDAO.queryAllUsers();
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

    /**
     * 用于测试 XPathParser
     * @throws IOException
     */
    @Test
    public void testXml() throws IOException {
        Reader reader = Resources.getResourceAsReader("users.xml");
//        InputStream inputStream = Resources.getResourceAsStream("users.xml");
        XPathParser xPathParser = new XPathParser(reader);

        List<XNode> xNodes = xPathParser.evalNodes("/users/*");
        // xNodes 里面的 xNode 对应的 <user>
        System.out.println("xNodes.size() = " + xNodes.size());

        List<com.atlantis.entity.xml.User> users = new ArrayList<>();

        // 这里面的每一个 xNode 对应的是 <user>
        for (XNode xNode : xNodes) {
            com.atlantis.entity.xml.User user = new com.atlantis.entity.xml.User();
            List<XNode> children = xNode.getChildren();
            user.setName(children.get(0).getStringBody());
            user.setPassword(children.get(1).getStringBody());
            users.add(user);
        }

        for (com.atlantis.entity.xml.User user : users) {
            System.out.println("user = " + user);
        }
    }
}
