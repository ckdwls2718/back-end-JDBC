package day01;

import java.sql.*;

public class MyFirstJDBC {
	public static void main(String[] args) {
		
		try {
			//1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loading Success!!");
			
			//프로토콜:dbms유형:driver타입:host:port:전역데이터베이스이름
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "scott", pwd="tiger";
			
			//2. db와 연결
			Connection con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB Connected...");
			
			//3. query문 작성
			String sql = "create table memo(";
			sql += "idx number(4) primary key,"; //글번호
			sql += "name varchar2(20) not null,"; //작성자
			sql += "msg varchar2(100),";
			sql += "wdate date default sysdate)";
			
			//4. Statement 객체 얻기 => Connection의 createStatement()를 이용
			Statement stmt = con.createStatement();
			
			//5. Statement 의 execute()//executeXXX() 메서드를 이용해서 쿼리문을 실행시킨다.
			//boolean execute(): 모든 sql문을 실행시킨다.
			//int executeUpdate(): insert/delete/update문을 실행시킨다
			//ResultSet executeQuery(): select문을 실행시킨다.
			//반환값은 select문이면 true 아니면 false
			boolean b = stmt.execute(sql);
			System.out.println("b: "+b);
			
			
			//6. DB관련 자원 반납
			stmt.close();
			con.close();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
