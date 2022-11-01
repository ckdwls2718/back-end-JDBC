package day04;

import java.sql.*;
import java.util.*;
import common.DBUtil;
/* 여러 개의 sql문을 한꺼번에 전송하는 일괄처리 방식
 * - 여러 개의 sql문을 작성해서 Statement의 addBatch(sql),
 * 	 executeBacth() 메서드로 일괄 처리한다.
 *   모두 성공하던지, 아니면 모두 실패하던지 ==> Transaction 원자성
 * */

public class BatchQueryTest {

	public static void main(String[] args) throws Exception{
		Connection con = DBUtil.getCon();
		con.setAutoCommit(false);//자동 커밋 취소. ==> 수동으로 트랜잭션을 관리하기위해
		
		Statement stmt = con.createStatement();
		stmt.addBatch("insert into memo values(memo_seq.nextval, '김길동', 'batchTest1',sysdate)");
		stmt.addBatch("insert into memo values(memo_seq.nextval, '박길동', 'batchTest2',sysdate)");
		stmt.addBatch("insert into memo values(memo_seq.nextval, '이길동', 'batchTest3',sysdate)");
		stmt.addBatch("insert into memo values(memo_seq.nextval, '최길동', 'batchTest4',sysdate)");
		stmt.addBatch("insert into memo values(memo_seq.nextval, '고길동', 'batchTest5',sysdate)");
		
		boolean isCommit = false;
		
		try {
			int[] updateCount = stmt.executeBatch();//일괄처리하여 실행함
			isCommit = true;
		}catch(SQLException e) {
			isCommit = false;
			e.printStackTrace();
		}
		
		if(isCommit) {
			con.commit();
		} else {
			con.rollback();
		}
		
		con.setAutoCommit(true);// 자동커밋 원래상태로 복귀
		
		// 저장된 값 가져오기
		boolean b = stmt.execute("select * from memo order by idx desc");
		if(b) {
			ResultSet rs = stmt.getResultSet();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getDate(4));
			}
			rs.close();
		}
		
		stmt.close();
		con.close();
		
	}

}
