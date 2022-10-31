package day03;

import common.DBUtil;
import java.sql.*;
import java.util.*;
public class PreparedStatementTest3 {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		Connection con = DBUtil.getCon();
		
		System.out.println("검색할 사원의 이름을 입력하세요 : ");
		String name = sc.next();
		
		//검색할 사원의 이름을 입력받아서 해당 사원정보를 출력하세요
		//사번, 사원명, 부서명, 담당업무, 입사일
		
		String sql = "select empno, ename, dname, job, hiredate"
				+ " from emp e join dept d"
				+ " using(deptno)"
				+ " where ename like ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		// sql에 %를 작성하면 문법에러 발생함
		pstmt.setString(1, "%"+name+"%");
		
		ResultSet rs = pstmt.executeQuery();
		
		print(rs);
		
		rs.close();
		pstmt.close();
		con.close();
	}
	
	public static void print(ResultSet rs) throws SQLException{
		System.out.println("사번\t사원명\t부서명\t담당업무\t입사일");
		if(rs.next()) {
			int empno = rs.getInt("empno");
			String ename = rs.getString("ename");
			String dname = rs.getString("dname");
			String job = rs.getString("job");
			java.sql.Date hiredate = rs.getDate("hiredate");
			System.out.printf("%d\t%s\t%s\t%s\t%s\t\n",empno,ename,dname,job,hiredate);
		}
	}

}
