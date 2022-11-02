package day05;


/*
create or replace procedure memo_all(
mycr out sys_refcursor)
is
begin
    open mycr for
    select idx, name, msg, wdate from memo
    order by idx desc;
end;
/
*/


import java.sql.*;
import java.sql.Date;
import java.util.*;
import common.DBUtil;
public class CallableStatementTest3 {

	public static void main(String[] args) throws SQLException {
		Connection con = DBUtil.getCon();
		String sql = "{call memo_all(?)}";
		
		CallableStatement cs = con.prepareCall(sql);
		//커서 out 파라미터로 반환
		cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
		
		//ResultSet rs = cs.executeQuery(); [x] 에러 발생함
		cs.execute();
		ResultSet rs = (ResultSet)cs.getObject(1);
		
		while(rs.next()) {
			int idx = rs.getInt("idx");
			String name = rs.getString("name");
			String msg = rs.getString("msg");
			Date wdate = rs.getDate("wdate");
			
			System.out.println(idx+"\t"+name+"\t"+msg+"\t"+wdate);
		}
		
		rs.close();
		cs.close();
		con.close();
	}

}
