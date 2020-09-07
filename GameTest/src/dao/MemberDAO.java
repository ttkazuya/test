package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.sql.DataSource;
import vo.MemberBean;
import static db.JdbcUtil.*;

public class MemberDAO {
	public static MemberDAO instance;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	private MemberDAO() {
		
	}
	public static MemberDAO getInstance(){
		if(instance == null){
			instance = new MemberDAO();
		}
		return instance;
	}
	public void setConnection(Connection con){
		this.con = con;
	}
	
	public String selectLoginId(MemberBean member){
		String loginId = null;
		String sql="SELECT id FROM member WHERE id=? AND pw=?";
		
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				loginId = rs.getString("id");
			}
		}catch(Exception ex){
			System.out.println(" 에러: " + ex);			
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return loginId;
	}
	
	public int insertMember(MemberBean member){
		String sql="INSERT INTO member VALUES (?,?,?,?,?,?,?)";
		int insertCount=0;
		
		try{

			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getAddr());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getGender());
			pstmt.setString(7, member.getGrade());
			insertCount=pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("joinMember 에러: " + ex);			
		}finally{
			close(pstmt);
		}
		
		return insertCount;
	}
	
	public ArrayList<MemberBean> selectMemberList(){
		String sql="SELECT * FROM member";
		ArrayList<MemberBean> memberList=new ArrayList<MemberBean>();
		MemberBean mb = null;
		try{
			
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				do{
				mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPw(rs.getString("pw"));
				mb.setName(rs.getString("name"));
				mb.setAddr(rs.getString("addr"));
				mb.setPhone(rs.getString("phone"));
				mb.setGender(rs.getString("gender"));
				mb.setGrade(rs.getString("grade"));
				memberList.add(mb);
				}while(rs.next());
			}
		}catch(Exception ex){
			System.out.println("getDeatilMember 에러: " + ex);			
		}finally{
			close(rs);
			close(pstmt);
		}
		return memberList;
	}
	
	public MemberBean selectMember(String id){
		String sql="SELECT * FROM member WHERE id=?";
		MemberBean mb = null;
		try{
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
			mb=new MemberBean();
			mb.setId(rs.getString("id"));
			mb.setPw(rs.getString("pw"));
			mb.setName(rs.getString("name"));
			mb.setAddr(rs.getString("addr"));
			mb.setPhone(rs.getString("phone"));
			mb.setGender(rs.getString("gender"));
			mb.setGrade(rs.getString("grade"));
			}
		}catch(Exception ex){
			System.out.println("getDeatilMember 에러: " + ex);			
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return mb;
	}
	public int deleteMember(String id){
		String sql="DELETE member WHERE id=?";
		int deleteCount = 0;

		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			deleteCount = pstmt.executeUpdate();
		}catch(Exception ex){
			System.out.println("deleteMember 에러: " + ex);	
		}finally{
			close(pstmt);
		}
		
		return deleteCount;
	}
}