import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DataBase {
	Connection conn;
	public DataBase(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url ="jdbc:sqlserver://localhost:1433;"+"databaseName=sample";
		    String login = "sa"; //set Server��NetWork��TCP/IP��ODBC����SQL SERVER��֤
		    String password ="6182288AAaa";
		    conn = DriverManager.getConnection(url,login,password);
		 }
	       catch ( SQLException sqlException ) {
	        JOptionPane.showMessageDialog( null, sqlException.getMessage(),
	           "Database Error", JOptionPane.ERROR_MESSAGE );

	        System.exit( 1 );
	      }

	     // detect problems loading database driver
	     catch ( ClassNotFoundException classNotFound ) {
	        JOptionPane.showMessageDialog( null, classNotFound.getMessage(),
	           "Driver Not Found", JOptionPane.ERROR_MESSAGE );
	        System.exit( 1 );
	     }
	}
	/**
	 * �������ݿ��
	 * @param tableName
	 */
	public void creatTable(String tableName){
		String sql="create table " +tableName+"(word varchar(50))";
		try {
			Statement statement =conn.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ������
	 * @param tableName
	 * @param RowName
	 */
	public void addRow(String tableName,String RowName){
		String sql="alter table "+tableName+" add "+RowName+" float(8)";
		try {
			Statement statement =conn.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �����ݿ�д������
	 * @param dbase
	 * @param wide
	 * @param tableName
	 */
	public void insertData(List<Buffer> dbase,int wide,String tableName){
		String s="?";
		for(int i=0;i<wide;i++){
			s+=", ?";
		}
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT into "+tableName+" values ("+s+")");
		//	ps.setFloat(l+2, Nxc[l]);
			/*
			ps.setInt(2, 6);
			ps.setInt(3, 3);
			ps.setInt(4, 4);
			ps.setInt(5, 5);
			
			ps.setString(1, word);
			*/
			for(int i=0;i<dbase.size();i++){
				ps.setString(1, dbase.get(i).word);
				for(int j=0;j<wide;j++){
					ps.setFloat(j+2, dbase.get(i).pr[j]);
				}
				ps.addBatch(); 
				ps.executeBatch(); 
			}
			
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ����
	 * @param tableName
	 * @param rowName
	 * @return
	 */
	public List<Buffer> getData(String tableName,String[] rowName){
		System.out.println("Import Database..."+tableName);
		List<Buffer> dbase = new ArrayList<Buffer>();
		
		String sql="select * from "+tableName+" ";
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet rs= statement.executeQuery(sql);
			while(rs.next()){
				Buffer b=new Buffer(rowName.length);
				b.word=rs.getString("word");
				for (int j = 0; j <rowName.length; j++) {
					b.pr[j]=rs.getFloat(rowName[j]);
				}
				dbase.add(b);
			}
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
		return dbase;
	}
	
}
