package com.baoxina.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 
jdbc编程步骤：
	1、加载数据库驱动
	2、创建并获取数据库链接
	3、创建jdbc statement对象
	4、设置sql语句
	5、设置sql语句中的参数(使用preparedStatement)
	6、通过statement执行sql并获取结果
	7、对sql执行结果进行解析处理
	8、释放资源(resultSet、preparedstatement、connection)

jdbc问题总结如下：
	1、数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。
	2、Sql语句在代码中硬编码，造成代码不易维护，实际应用sql变化的可能较大，sql变动需要改变java代码。
	3、使用preparedStatement向占有位符号传参数存在硬编码，因为sql语句的where条件不一定，可能多也可能少，修改sql还要修改代码，系统不易维护。
	4、对结果集解析存在硬编码（查询列名），sql变化导致解析代码变化，系统不易维护，如果能将数据库记录封装成pojo对象解析比较方便。
 * @author baoxina
 *
 */
public class JDBCTest {
	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			//通过驱动管理类获取数据库链接
			connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ammy?characterEncoding=utf-8", "root", "xf07041118");
			//定义sql语句 ?表示占位符
			String sql = "select * from user where name = ?";
			//获取预处理statement
			preparedStatement = connection.prepareStatement(sql);
			//设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			preparedStatement.setString(1, "aaa");
			//向数据库发出sql执行查询，查询出结果集
			resultSet =  preparedStatement.executeQuery();
			//遍历查询结果集
			while(resultSet.next()){
				System.out.println(resultSet.getString("id")+"  "+resultSet.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放资源
			if(resultSet!=null){
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(preparedStatement!=null){
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
