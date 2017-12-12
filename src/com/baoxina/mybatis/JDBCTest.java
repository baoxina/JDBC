package com.baoxina.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 
jdbc��̲��裺
	1���������ݿ�����
	2����������ȡ���ݿ�����
	3������jdbc statement����
	4������sql���
	5������sql����еĲ���(ʹ��preparedStatement)
	6��ͨ��statementִ��sql����ȡ���
	7����sqlִ�н�����н�������
	8���ͷ���Դ(resultSet��preparedstatement��connection)

jdbc�����ܽ����£�
	1�����ݿ����Ӵ������ͷ�Ƶ�����ϵͳ��Դ�˷ѴӶ�Ӱ��ϵͳ���ܣ����ʹ�����ݿ����ӳؿɽ�������⡣
	2��Sql����ڴ�����Ӳ���룬��ɴ��벻��ά����ʵ��Ӧ��sql�仯�Ŀ��ܽϴ�sql�䶯��Ҫ�ı�java���롣
	3��ʹ��preparedStatement��ռ��λ���Ŵ���������Ӳ���룬��Ϊsql����where������һ�������ܶ�Ҳ�����٣��޸�sql��Ҫ�޸Ĵ��룬ϵͳ����ά����
	4���Խ������������Ӳ���루��ѯ��������sql�仯���½�������仯��ϵͳ����ά��������ܽ����ݿ��¼��װ��pojo��������ȽϷ��㡣
 * @author baoxina
 *
 */
public class JDBCTest {
	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//�������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");
			//ͨ�������������ȡ���ݿ�����
			connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ammy?characterEncoding=utf-8", "root", "xf07041118");
			//����sql��� ?��ʾռλ��
			String sql = "select * from user where name = ?";
			//��ȡԤ����statement
			preparedStatement = connection.prepareStatement(sql);
			//���ò�������һ������Ϊsql����в�������ţ���1��ʼ�����ڶ�������Ϊ���õĲ���ֵ
			preparedStatement.setString(1, "aaa");
			//�����ݿⷢ��sqlִ�в�ѯ����ѯ�������
			resultSet =  preparedStatement.executeQuery();
			//������ѯ�����
			while(resultSet.next()){
				System.out.println(resultSet.getString("id")+"  "+resultSet.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ͷ���Դ
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
