package com.zqw.gp.common;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;



/**
 * 快速根据javabean来生成sql的表结构和insert和update的通用类
 *
 */
public class TableBuilder {

	/**
	 * 程序入口
	 *
	 * @param args
	 *            入参
	 */
	public static void main(String args[]) {
		Class<?> c = TableBuilder.class;
		createTable(c);
		createInsert(c);
		createUpdate(c);
	}

	/**
	 * 创建update的语句
	 *
	 * @param c
	 *            类对象
	 */
	private static void createUpdate(Class<?> c) {
		String tableName = c.getName().substring(c.getName().lastIndexOf(".") + 1);
		StringBuffer sb = new StringBuffer("@Update(\"update " + getName(tableName) + " set ");

		Field[] fields = buildFeilds(c);
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			sb.append(field.getName()).append("=#{").append(field.getName()).append("}");
			if (i < fields.length - 1) {
				sb.append(",");
			}
		}

		sb.append(" where id = #{id}\")");
		System.out.println(sb.toString());
	}

	/**
	 * 根据类获得里面的成员变量
	 *
	 * @param c
	 *            类对象
	 * @return 成员变量列表
	 */
	private static Field[] buildFeilds(Class<?> c) {
		Field[] fields = c.getDeclaredFields();
		Class<?> father = c.getSuperclass();
		if (null != father && !father.getName().contains("Object")) {
			Field[] fatherFields = father.getDeclaredFields();
			Field[] temp = new Field[fatherFields.length + fields.length];
			for (int i = 0; i < fields.length; i++) {
				temp[i] = fields[i];
			}

			for (int i = 0; i < fatherFields.length; i++) {
				temp[i + fields.length] = fatherFields[i];
			}
			fields = temp;
		}
		return fields;
	}

	/**
	 * 创建insert的sql语句
	 *
	 * @param c
	 *            类对象
	 */
	private static void createInsert(Class<?> c) {
		String tableName = c.getName().substring(c.getName().lastIndexOf(".") + 1);

		StringBuffer sb = new StringBuffer("@Insert(\"insert into " + getName(tableName) + " (");
		Field[] fields = buildFeilds(c);
		for (Field field : fields) {
			sb.append("`").append(field.getName() + "`,");
		}

		String now = sb.substring(0, sb.length() - 1);
		sb = new StringBuffer(now);
		sb.append(") values (");

		for (Field field : fields) {
			sb.append("#{" + field.getName() + "},");
		}
		String str = sb.substring(0, sb.length() - 1);

		str += ")\")";
		System.out.println(str);
	}

	/**
	 * 创建表结构的sql语句
	 *
	 * @param c
	 *            类对象
	 */
	private static void createTable(Class<?> c) {
		String tableName = c.getName().substring(c.getName().lastIndexOf(".") + 1);
		StringBuffer sb = new StringBuffer("create table " + getName(tableName) + " (");
		Field[] fields = buildFeilds(c);

		boolean isId = false;
		for (Field field : fields) {
			sb.append("\n");
			Class<?> type = field.getType();
			String name = field.getName();
			String sqlType = getMysqlType(type);
			StringBuffer line = new StringBuffer("`").append(name).append("`").append(sqlType);
			if (name.equals("id")) {
				line.append(" auto_increment PRIMARY KEY");
				isId = true;
			}
			line.append(" NOT NULL,");
			sb.append(line);
		}
		String str = sb.substring(0, sb.length() - 1);
		str += ("\n)ENGINE =InnoDB default charset utf8;\n");
		if (isId) {
			str += "alter table " + getName(tableName) + " auto_increment =100000;";
		}
		System.out.println(str);
	}

	/**
	 * 根据变量类型转为sql类型
	 *
	 * @param type
	 *            变量类型
	 * @return sql类型
	 */
	private static String getMysqlType(Class<?> type) {
		if (type.equals(String.class)) {
			return " varchar(20) ";
		}

		if (type.equals(Double.class) || type.equals(double.class)) {
			return " DOUBLE ";
		}

		if (type.equals(Float.class) || type.equals(float.class)) {
			return " FLOAT ";
		}

		if (type.equals(Long.class) || type.equals(long.class)) {
			return " BIGINT ";
		}

		if (type.equals(Integer.class) || type.equals(int.class)) {
			return " INTEGER ";
		}

		if (type.equals(Timestamp.class)) {
			return " TIMESTAMP ";
		}

		if (type.equals(Boolean.class) || type.equals(boolean.class)) {
			return " TINYINT(1) ";
		}

		if (type.equals(Date.class)) {
			return " DATETIME ";
		}

		if (type.equals(java.util.Date.class)) {
			return " DATETIME ";
		}
		return null;
	}

	/**
	 * 驼峰命名改为下划线的名称
	 *
	 * @param name
	 *            变量名
	 * @return 下划线名称
	 */
	private static String getName(String name) {
		char start = (char) (name.charAt(0) - ('A' - 'a'));
		StringBuilder sb = new StringBuilder().append(start);
		for (int i = 1; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				char newC = (char) (c - ('A' - 'a'));
				sb.append("_").append(newC);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
