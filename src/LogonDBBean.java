package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// Model
public class LogonDBBean {
	public static LogonDBBean instance = new LogonDBBean();
	public static LogonDBBean getInstance() {			// Singleton Pattern
		return instance;
	}
	
	public Connection getConnection() throws NamingException, SQLException {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup( "java:comp/env" );
		DataSource ds = (DataSource) envCtx.lookup( "jdbc/encore" );
		return ds.getConnection();
	}
	
	public int insertMember( LogonDataBean memberDto ) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "insert into memberstudy values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			pstmt = con.prepareStatement( sql );
			pstmt.setString( 1, memberDto.getId() );
			pstmt.setString( 2, memberDto.getPasswd() );
			pstmt.setString( 3, memberDto.getName() );
			pstmt.setString( 4, memberDto.getJumin1() );
			pstmt.setString( 5, memberDto.getJumin2() );
			pstmt.setString( 6, memberDto.getZipcode() );
			pstmt.setString( 7, memberDto.getAddress() );
			pstmt.setString( 8, memberDto.getTel() );
			pstmt.setString( 9, memberDto.getEmail() );
			pstmt.setTimestamp( 10, memberDto.getReg_date() );
			
			result = pstmt.executeUpdate();
			
		} catch ( NamingException e ) {
			e.printStackTrace();
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}		
		return result;
	}
	
	public int check( String id ) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select * from memberstudy where id=?";
			pstmt = con.prepareStatement( sql );
			pstmt.setString( 1, id );
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				// 아이디가 있다
				result = 1;
			} else {
				// 아이디가 없다
				result = 0;
			}			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ) rs.close();
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}		
		return result;
	}
	
	public int check( String id, String passwd ) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select * from memberstudy where id=?";
			pstmt = con.prepareStatement( sql );
			pstmt.setString( 1, id );
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				// 아이디가 있다
				if( passwd.equals( rs.getString( "passwd" ) ) ) {
					// 비밀번호 같다
					result = 1;
				} else {
					// 비밀번호 다르다
					result = -1;
				}
			} else {
				// 아이디가 없다
				result = 0;
			}			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ) rs.close();
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}	
		return result;
	}
	
	public LogonDataBean getMember( String id ) {
		LogonDataBean memberDto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select * from memberstudy where id=?";
			pstmt = con.prepareStatement( sql );
			pstmt.setString( 1, id );
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				memberDto = new LogonDataBean();
				memberDto.setId( rs.getString( "id" ) );
				memberDto.setPasswd(  rs.getString( "passwd" ) );
				memberDto.setName( rs.getString( "name" ) );
				memberDto.setJumin1( rs.getString( "jumin1" ) );
				memberDto.setJumin2( rs.getString( "jumin2" ) );
				memberDto.setZipcode(rs.getString( "zipcode") );
				memberDto.setAddress(rs.getString( "address") );
				memberDto.setTel( rs.getString( "tel" ) );
				memberDto.setEmail( rs.getString( "email" ) );
				memberDto.setReg_date( rs.getTimestamp( "reg_date" ) );				
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ) rs.close();
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}			
		return memberDto;
	}
	
	public int updateMember( LogonDataBean memberDto ) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "update memberstudy set passwd=?, address=?, tel=?, email=? where id=?";
			pstmt = con.prepareStatement( sql );
			pstmt.setString( 1, memberDto.getPasswd() );
			pstmt.setString( 2, memberDto.getAddress());
			pstmt.setString( 3, memberDto.getTel() );
			pstmt.setString( 4, memberDto.getEmail() );
			pstmt.setString( 5, memberDto.getId() );
			
			result = pstmt.executeUpdate();					
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}	
		return result;
	}
	
	public int deleteMember( String id ) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "delete from memberstudy where id=?";
			pstmt = con.prepareStatement( sql );
			pstmt.setString( 1, id );
			result = pstmt.executeUpdate();			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}	
		return result;
	}
		
	public Vector<ZipcodeBean> getZipcodes(String area3){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<ZipcodeBean> veclist = new Vector<>();
		try {
			con = getConnection();
			sql = "select * from zipcode where area3 like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+area3+"%");
			rs = pstmt.executeQuery();
			while(rs.next()){ 
				ZipcodeBean tempZipcode = new ZipcodeBean();
				tempZipcode.setZipcode(rs.getString(1));
				tempZipcode.setArea1(rs.getString(2));
				tempZipcode.setArea2(rs.getString(3));
				tempZipcode.setArea3(rs.getString(4));
			    tempZipcode.setArea4(rs.getString(5));
			    veclist.addElement(tempZipcode);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ) rs.close();
				if( pstmt != null ) pstmt.close();
				if( con != null ) con.close();
			} catch( SQLException e ) {
				e.printStackTrace();
			}			
		}	
		return veclist;
	}
	
} // class




















