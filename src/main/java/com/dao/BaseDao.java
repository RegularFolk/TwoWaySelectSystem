package com.dao;

import com.utils.JDBCUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Dao是基于dbutils编写的，若将来需要更换框架，则需要修改此处代码，其子类不许修改，二次封装
 * 所有连接使用完必须释放，否则会用尽产生异常
 *
 * @param <T>
 */

public class BaseDao<T> {
    final private QueryRunner queryRunner = new QueryRunner();

    //开启驼峰映射，by 郑应啟
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    /**
     * 批处理方法
     */
    public int[] batchUpdate(String sql, Object[][] paramsArr) {
        try {
            Connection connection = JDBCUtil.getConnection();
            return queryRunner.batch(connection, sql, paramsArr);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public int update(String sql, Object... params) {
        try {
            Connection connection = JDBCUtil.getConnection();
            //执行增删改的的sql语句，返回受到影响的行数
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public T getBean(Class<T> tClass, String sql, Object... params) {
        try {
            Connection connection = JDBCUtil.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<>(tClass,processor), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //执行查询多行数据的sql语句，并且将结果集封装到List中
    public List<T> getBeanList(Class<T> tClass, String sql, Object... params) {
        try {
            Connection connection = JDBCUtil.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<>(tClass,processor), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    //执行插入操作，并返回主键值，仅限自增主键，by 郑应啟
    public int generatedKeyUpdate(String sql, Object... params) {
        try {
            Connection connection = JDBCUtil.getConnection();
            //执行增删改的的sql语句，返回受到影响的行数
            BigInteger b= queryRunner.insert(connection, sql,new ScalarHandler<>(),params);
            String s=b.toString();
            return Integer.parseInt(s);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
