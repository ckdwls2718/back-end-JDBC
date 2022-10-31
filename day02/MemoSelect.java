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
			//ResultSet executeQuery() : select문일 경우;
			
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
	
	// 자주 사용되는 메서드를 모듈화
	public ArrayList<MemoVO> makeList(ResultSet rs) throws SQLException{
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
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-----------------");
		System.out.println("1. 전체 검색");
		System.out.println("2. 인덱스 검색");
		System.out.println("3. 작성자 검색");
		System.out.println("4. 내용 검색");
		System.out.println("-----------------");
		int n = sc.nextInt();
		
		switch(n) {
		case 1:
			app.printMemo(app.selectMemoAll());
			break;
		case 2:
			System.out.println("인덱스를 입력하세요 : ");
			int idx = sc.nextInt();
			MemoVO vo = app.selectMemo(idx);
			String result = ( vo !=null) ? vo.toString() : "검색한 글은 없습니다.";
			System.out.println(result); 
			break;
		case 3:
			System.out.println("작성자를 입력하세요 : ");
			String name = sc.next();
			app.printMemo(app.findMemoByName(name));
			break;
		case 4:
			System.out.println("내용을 입력하세요 : ");
			String msg = sc.next();
			app.printMemo(app.findMemoByMsg(msg));
			break;
		}
		
	}

}
