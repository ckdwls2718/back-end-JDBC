package day05;
/*
create or replace procedure dept_all(
pdeptno in dept.deptno%type,
mycr out sys_refcursor)
is
begin
    open mycr for
    select ename,job,hiredate,dname,loc
    from emp join dept
    using(deptno)
    where deptno = pdeptno;
end;
/ 
 * */
import java.sql.*;
import java.sql.Date;
import java.util.*;
import common.DBUtil;
public class CallableStatementTest4 {

	public static void main(String[] args) throws SQLException{
		//부서번호를 인파라미터로 전달하면 
		//해당 부서에 있는 사원정보(사원명, 업무, 입사일)와 부서정보(부서명,근무지)를
		//가져오는 프로시저를 작성하고 이것을 자바에서 호출해서 결과 데이터를 출력하세요
		Scanner sc = new Scanner(System.in);
		System.out.println("검색하려는 부서번호를 입력하세요 : ");
		int detpno = sc.nextInt();
		
		Connection con = DBUtil.getCon();
		String sql = "{call dept_all(?,?)}";
		
		CallableStatement cs = con.prepareCall(sql);
		cs.setInt(1, detpno);
		cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
		
		cs.execute();
		ResultSet rs = (ResultSet)cs.getObject(2);
		
		while(rs.next()) {
			
			String ename = rs.getString("ename");
			String job = rs.getString("job");
			Date hiredate = rs.getDate("hiredate");
			String dname = rs.getString("dname");
			String loc = rs.getString("loc");
			
			System.out.println(ename+"\t"+job+"\t"+hiredate+"\t"+dname+"\t"+loc);
		}
			
		rs.close();
		cs.close();
		con.close();
	}

}
