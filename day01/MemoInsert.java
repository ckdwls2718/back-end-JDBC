package day01;

import java.sql.*;
import javax.swing.*;

public class MemoInsert {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//memo테이블에 insert문을 작성해서 메모글을 insert하는 프로그램을 완성하세요
		//1.홍길동 첫번째 작성한 글입니다 sysdate
		
		String name = JOptionPane.showInputDialog("이름을 입력하세요");
		String msg = JOptionPane.showInputDialog("내용을 입력하세요");
		
		if(name == null) return;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("Driver loading Success!!");
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "scott", pwd = "tiger";
		
		Connection con = DriverManager.getConnection(url,user,pwd);
		
		String sql = "insert into memo(idx, name, msg) values";
		sql +="(memo_seq.nextval,'"+name+"','"+msg+"')";
		
		Statement stmt = con.createStatement();
		
		boolean b = stmt.execute(sql);
		System.out.println("b : "+b);
		
		stmt.close();
		con.close();
		
	}

}
