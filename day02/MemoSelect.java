package day02;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import oracle.net.aso.a;
public class MemoSelect {
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
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
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(con != null) con.close(); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MemoVO selectMemo(int idx) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo where idx = "+idx;
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			ArrayList<MemoVO> arr = makeList(rs);
			if(arr != null && arr.size() >0) {
				return arr.get(0);
			}
			return null;
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
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo where msg like '%"+keyword+"%'"
					+ " order by idx desc";
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);
			
			ArrayList<MemoVO> arr = makeList(rs);
			
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
			String sql = "select idx, rpad(name,10,' ') as name, rpad(msg,20,' ') as msg, wdate from memo where name like '%"+keyword+"%'"
					+ " order by idx desc";
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			ArrayList<MemoVO> arr = makeList(rs);
			
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
			//ResultSet executeQuery() : select?????? ??????;
			
			rs = stmt.executeQuery(sql);
			
			ArrayList<MemoVO> arr = makeList(rs);

			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}
	
	// ?????? ???????????? ???????????? ?????????
	public ArrayList<MemoVO> makeList(ResultSet rs) throws SQLException{
		ArrayList<MemoVO> arr = new ArrayList<>();
		
		//boolean next() : ????????? ?????????????????? beforeFirst??? ???????????? ????????? next()??? ???????????? ????????? ??????????????? ???????????????
		//					????????? ?????? ???????????? ????????? true??? ????????????.
		while(rs.next()) {
			int idx = rs.getInt("idx");
			String name = rs.getString("name");
			String msg = rs.getString("msg");
			java.sql.Date wdate = rs.getDate("wdate");
			
			MemoVO vo = new MemoVO(idx,name,msg,wdate);
			arr.add(vo);
		}
		
		return arr;
	}
	
	public void printMemo(ArrayList<MemoVO> memoList) {
		if(memoList != null) {
			System.out.println("--------------------------------------------");
			System.out.println("\t?????????\t????????????\t????????????\t\t?????????");
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
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-----------------");
		System.out.println("1. ?????? ??????");
		System.out.println("2. ????????? ??????");
		System.out.println("3. ????????? ??????");
		System.out.println("4. ?????? ??????");
		System.out.println("-----------------");
		int n = sc.nextInt();
		
		switch(n) {
		case 1:
			app.printMemo(app.selectMemoAll());
			break;
		case 2:
			System.out.println("???????????? ??????????????? : ");
			int idx = sc.nextInt();
			MemoVO vo = app.selectMemo(idx);
			String result = ( vo !=null) ? vo.toString() : "????????? ?????? ????????????.";
			System.out.println(result); 
			break;
		case 3:
			System.out.println("???????????? ??????????????? : ");
			String name = sc.next();
			app.printMemo(app.findMemoByName(name));
			break;
		case 4:
			System.out.println("????????? ??????????????? : ");
			String msg = sc.next();
			app.printMemo(app.findMemoByMsg(msg));
			break;
		}
		
	}

}
