package day02;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import oracle.net.aso.a;
public class MemoSelect {
	
	private Connection con;
	private Statement stmt;
	
	private String url="jdbc:oracle:thin:@localhost:1521:XE";
	private String user ="scott", pwd ="tiger";
	
	public MemoSelect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(stmt != null) stmt.close();
			if(con != null) con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MemoVO selectMemo(int idx) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo where idx = "+idx
					+ " order by idx desc";
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			int index = rs.getInt("idx");
			String name = rs.getString("name");
			String msg = rs.getString("msg");
			java.sql.Date wdate = rs.getDate("wdate");

			MemoVO vo = new MemoVO(index, name, msg, wdate);
			
			
			
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
		
	}
	
	public ArrayList<MemoVO> findMemoByMsg(String keyword){
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo where msg like %"+keyword+"%"
					+ " order by idx desc";
			stmt = con.createStatement();
			

			
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<MemoVO> arr = new ArrayList<>();
			

			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				java.sql.Date wdate = rs.getDate("wdate");
				
				MemoVO vo = new MemoVO(idx,name,msg,wdate);
				arr.add(vo);
			}
			
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}
	
	public ArrayList<MemoVO> findMemoByName(String keyword){
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo where name like %"+keyword+"%"
					+ " order by idx desc";
			stmt = con.createStatement();
			

			
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<MemoVO> arr = new ArrayList<>();
			

			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				java.sql.Date wdate = rs.getDate("wdate");
				
				MemoVO vo = new MemoVO(idx,name,msg,wdate);
				arr.add(vo);
			}
			
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}
	
	
	
	public ArrayList<MemoVO> selectMemoAll(){
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo order by idx desc";
			stmt = con.createStatement();
			
//			boolean b = stmt.execute(sql);
//			ResultSet rs = stmt.getResultSet();
//			
//			System.out.println("b : "+b);
			//ResultSet executeQuery() : select문일 경우;
			
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<MemoVO> arr = new ArrayList<>();
			
			//boolean next() : 커서는 결과테이블의 beforeFirst에 위치하고 있다가 next()가 호출되면 커서를 다음칸으로 이동시키고
			//					이동한 곳에 레코드가 있으면 true를 반환한다.
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String name = rs.getString("name");
				String msg = rs.getString("msg");
				java.sql.Date wdate = rs.getDate("wdate");
				
				MemoVO vo = new MemoVO(idx,name,msg,wdate);
				arr.add(vo);
			}
			
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}
	
	public void printMemo(ArrayList<MemoVO> memoList) {
		if(memoList != null) {
			System.out.println("--------------------------------------------");
			System.out.println("\t글번호\t작성자명\t메모내용\t\t작성일");
			System.out.println("--------------------------------------------");
			for(MemoVO memo : memoList) {
				System.out.printf("\t%d\t%s\t%s\t\t",memo.getIdx(),memo.getName(),memo.getMsg());
				System.out.println(memo.getWdate());
			}
			System.out.println("--------------------------------------------");
		}
	}
	
	public static void main(String[] args) {
		MemoSelect app = new MemoSelect();
		
		ArrayList<MemoVO> memoList = app.selectMemoAll();
		
		app.printMemo(memoList);
	}

}
