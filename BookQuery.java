//BookQuery.java
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.util.Date;
public class BookQuery extends JFrame{
	JTable table;
	JScrollPane scrollpane;
	String[] lbsign={"图书名称","作       者 ","出  版  社","出版时间"};
	Label[] booklb=new Label[4];
	TextField[] booktxt=new TextField[4];
	Button querybtn=new Button("查询");
	Button closebtn=new Button("关闭");
	String[]heads={"图书编号","图书名称","图书类型","作者","翻译","出版社","出版时间","定价","库存数量","图书印张"};
	Object[][] bookq=new Object[40][heads.length];
	public BookQuery(){
		setTitle("图书查询");
		setLayout(null);
		setSize(1200,700);
		setResizable(false);
		int lx=150;
		for(int i=0;i<booklb.length;i++){
			booklb[i]=new Label(lbsign[i]);
			booktxt[i]=new TextField();
			booklb[i].setBounds(lx,30,50,20);
			booktxt[i].setBounds(lx+70,30,120,20);
			lx=lx+210;
			add(booklb[i]);add(booktxt[i]);
		}
		querybtn.setBounds(300,80,50,20);
		closebtn.setBounds(500,80,50,20);
		add(querybtn);add(closebtn);
		

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				DbOp.close();
				dispose();
				//System.exit(0);
			}
		});
		querybtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				querybtnActionPerformed(e);
			}
		});
		closebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DbOp.close();
				dispose();
				//System.exit(0);				
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void querybtnActionPerformed(ActionEvent e){
		bookq=new Object[40][heads.length];
		String sql="",sql1="",sql2="",pubyear="",pubmonth="";
		String[] sqloption={"bookname","author","publisher","publish_time"};
		String[] inputinfor=new String[4];
		boolean impty=false;
		try{
		if(booktxt[3].getText().equals("")){
			sql2="";
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			Calendar car=new GregorianCalendar();
			Date pubtime=sdf.parse(booktxt[3].getText());
			car.setTime(pubtime);
			pubyear=String.valueOf(car.get(Calendar.YEAR));
			pubmonth=String.valueOf(car.get(Calendar.MONTH)+1);
			sql2="year(publish_time)='"+pubyear+"'and month(publish_time)='"+pubmonth+"'";
			
		}
		int count=0;
		for(int i=0;i<booktxt.length-1;i++){
			inputinfor[i]=booktxt[i].getText();
			if(inputinfor[i].equals("")){
				//
			}else{
				if(count==0){
					sql1=sqloption[i]+" like'%"+inputinfor[i]+"%'";
				}else{
					sql1=sql1+" and "+sqloption[i]+" like'%"+inputinfor[i]+"%'";				
				}
				count++;
				impty=true;
			}
		}
		if((!sql1.equals(""))&&(!sql2.equals(""))){
			sql2=" and "+sql2;
		}else{
			
		}
		if((!impty)&&(sql2.equals(""))){
			JOptionPane.showMessageDialog(null,"请选择任意一项输入");
			//scrollpane.setVisible(false);
			return;
		}else{
			sql="select * from book where ";
			sql=sql+sql1+sql2;
		}
		boolean rsnext=false;
		ResultSet rs=DbOp.executeQuery(sql);
		
	String[] informationoption={"id","bookname","booktype","author","translator","publisher","publish_time","price","stock","page"};
		int i=0;
		while(rs.next()){
			for(int j=0;j<heads.length;j++){
 				bookq[i][j]=rs.getString(informationoption[j]);
			}
			i++;
			rsnext=true;
		}
		if(rsnext){
		}else{
			JOptionPane.showMessageDialog(null,"没有查询到任何相关联的信息");
			return;
			//bookq=null;
		}
		table=new JTable(bookq,heads);
		scrollpane=new JScrollPane(table);
		scrollpane.setBounds(50,130,1100,520);
		add(scrollpane);
		DbOp.close();
		}catch(ParseException e9){
			JOptionPane.showMessageDialog(null,"时间格式错误如（2012-02）");
		}catch(SQLException e7){
			JOptionPane.showMessageDialog(null,"找不到你要查询的信息");
		}
	}
	public static void main(String[] args){
		new BookQuery();
	}
}