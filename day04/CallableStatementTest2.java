package day04;
/*
create or replace procedure memo_edit
(vno in memo.idx%type, vname in memo.name%type, vmsg in memo.msg%type)
is
begin
    update memo set name = vname, msg = vmsg where idx = vno;
end;
/
*/

import java.util.*;
import common.DBUtil;
import day02.MemoVO;

import java.sql.*;

public class CallableStatementTest2 {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		// 메모글을 수정하는 memo_ edit 프로시저를 호출하는 jdbc를 구현하세요
		Connection con = DBUtil.getCon();
		String sql = "{call memo_edit(?,?,?)}";
		System.out.println("수정하려는 글의 인덱스 : ");
		int idx = sc.nextInt();
		sc.skip("\r\n");
		
		System.out.println("수정 사용자 : ");
		String name = sc.nextLine();
		
		System.out.println("수정 내용 : ");
		String msg = sc.nextLine();
		
		MemoVO vo = new MemoVO(idx,name,msg,null);
		
		CallableStatement cs = con.prepareCall(sql);
		
		cs.setInt(1, vo.getIdx());
		cs.setString(2, vo.getName());
		cs.setString(3, vo.getMsg());
		
		cs.execute();
		System.out.println(idx+"번 글 수정완료");
		cs.close();
		con.close();
	}

}
