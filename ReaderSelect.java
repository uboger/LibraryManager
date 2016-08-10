//ReaderSelect.java;
//package PublicModule;
import java.sql.*;
import javax.swing.JOptionPane;
public class ReaderSelect{
	public static Reader SelectReaderByID(String id){
		String sql="select * from reader where id='"+id+"'";
		ResultSet rs=DbOp.executeQuery(sql);
		Reader reader=null;
		try{
			if(rs.next()){
				reader=new Reader();
					reader.setId(rs.getString("id"));
					reader.setReadername(rs.getString("readername"));
					reader.setReadertype(rs.getString("readertype"));
					reader.setReaderSex(rs.getString("sex"));
					reader.setMax_num(rs.getInt("max_num"));
					reader.setDays_num(rs.getInt("days_num"));					
			}else{
				reader=null;
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"无法正常读取数据库！");
		}
		return reader;
	}
}