package day04;
/*
create or replace procedure memo_add(name in memo.name%type, msg in memo.msg%type)
is
begin
    insert into memo values(memo_seq.nextval,name,msg,sysdate);
    commit;
end;
/*/
import java.util.*;
import common.DBUtil;
import java.sql.*;

public class CallableStatementTest {

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.println("작성자명 : ");
		String name = sc.nextLine();
		
		System.out.println("메모 내용 : ");
		String msg = sc.nextLine();
		
		Connection con = DBUtil.getCon();
		String sql = "{call memo_add(?,?)}";
		
		//프로시저를 호출하기 위해서는 CallableStatement객체를 얻어온다.
		CallableStatement cs = con.prepareCall(sql);
		
		//in parameter 값 세팅
		cs.setString(1, name);
		cs.setString(2, msg);
		
		//실행
		cs.execute();
		System.out.println("메모글 등록 성공");
		cs.close();
		con.close();
	}

}
