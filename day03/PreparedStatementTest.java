package day03;
import java.sql.*;

public class PreparedStatementTest {
	
	
	
	public static void main(String[] args) throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user ="scott", pwd ="tiger";
		Connection con = DriverManager.getConnection(url,user,pwd);
		String sql = "insert into emp(empno, ename, job, hiredate, sal, deptno)"
				+ " values(?,?,?,sysdate,?,?)";
		//?를 제외한 sql을 미리 전처리하여 DBMS포맷에 맞게 컴파일한다.
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, 8000);
		pstmt.setString(2, "TOM");
		pstmt.setString(3, "MANAGER");
		pstmt.setInt(4, 4000);
		pstmt.setInt(5, 10);
		
		int n = pstmt.executeUpdate();
		System.out.println(n+"개의 레코드 삽입함");
		
		pstmt.close();
		con.close();
	}

}
