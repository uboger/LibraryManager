//BookUpdate.java
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
public class BookUpdate extends Frame{
	Toolkit tool= getToolkit();
	String url="bookbk.png";
	Image img=tool.getImage(url);
		public void paint(Graphics g){
		g.drawImage(img,0,0,this);
	}

	String[] lbname={"图书编号","图书名称","图书页数","图书作者","翻     译","出 版 社","出版时间","定价","库存数量","所属类型"};
	
	Label[] booklb=new Label[10];
	TextField[] booktxt=new TextField[10];
	Label idlb=new Label("图书编号");
	TextField idtxt=new TextField();
	Button savebtn=new Button("Save");
	Button closebtn=new Button("Close");
	Button querybtn=new Button("查询");
	Choice booktype=new Choice();
	public BookUpdate(){
		setTitle("图书信息修改");
		setLayout(null);
		setSize(500,280);setResizable(false);
		int lx=50,ly=80;
		booktype.add("程序设计");
		booktype.add("图形设计");
		booktype.add("其他");
		booktype.add("科技");
		booktype.add("文学");
		booktype.add("历史");
		booktype.add("百科");
		booktype.add("英语");
		booktype.add("计算机");
		booktype.add("Internet");
		booktype.add("数学");

		idlb.setBounds(100,40,50,20);
		idtxt.setBounds(160,40,100,20);
		querybtn.setBounds(280,40,80,20);
		add(idlb);add(idtxt);add(querybtn);
		for(int i=0;i<booklb.length;i++){
			if(lx>240){
				lx=50;
				ly=ly+30;
			}
			booklb[i]=new Label(lbname[i]);
			booktxt[i]=new TextField();
			booklb[i].setBounds(lx,ly,50,20);
			booktxt[i].setBounds(lx+60,ly,100,20);
			lx=lx+190;
			add(booklb[i]);add(booktxt[i]);
		}
		booktxt[0].setEditable(false);
		
		booktxt[9].setVisible(false);
		booktype.setBounds(300,200,100,20);
		add(booktype);
		savebtn.setBounds(150,240,80,25);
		closebtn.setBounds(280,240,80,25);
		add(savebtn);add(closebtn);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				DbOp.close();
				dispose();	
			}
		});
		closebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DbOp.close();
				dispose();			
			}
		});
		querybtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			querybtnActionPerformed();
						
			}
		});
		savebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			savebtnActionPerformed();
						
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public  void querybtnActionPerformed(){
		String id=idtxt.getText();
		if(id.equals("")){
			JOptionPane.showMessageDialog(null,"图书编号不能为空");
			return;
		}
		if(IfBookIdExit(id)){
			Book book =BookSelect.SelectBookByID(id);
			String[] infor=book.getBookinformation();
			if(book!=null){
				booktype.select(book.getBooktype());
				for(int i=0;i<infor.length;i++){
					booktxt[i].setText(infor[i]);
				}
			}		
		}else{	
			JOptionPane.showMessageDialog(null,"没有该图书");
		}
	}
	public  void savebtnActionPerformed(){
		String id=booktxt[0].getText();
		String typestr=booktype.getSelectedItem().toString();
		String[] inputstring=new String[9];
		for(int i=0;i<inputstring.length;i++){
			inputstring[i]=booktxt[i].getText();
		}
		if(id.equals("")){
			JOptionPane.showMessageDialog(null,"图书编号不能为空");
			return;
		}
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			sdf.parse(inputstring[6]);
			float price=Float.parseFloat(inputstring[7]);
			int stock= Integer.parseInt(inputstring[8]);
			int page=Integer.parseInt(inputstring[2]);
			String sql="update book set bookname='"+inputstring[1]+"',booktype='"+typestr+"',author='"+inputstring[3];
			sql=sql+"',translator='"+inputstring[4]+"',publisher='"+inputstring[5]+"',publish_time='"+inputstring[6];
			sql=sql+"',price='"+price+"',stock='"+stock+"',page='"+page+"' where id='"+id+"'";

			int i=DbOp.executeUpdate(sql);
			if(i==1){
				JOptionPane.showMessageDialog(null,"图书修改成功！");
				clearAllText();
			}
		}catch(ParseException e2){
			JOptionPane.showMessageDialog(null,"出版时间格式错误，正确为（年-月）");
		}catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(null,"库存数量，价格，页数错误，应为数字");
		}
	}
	public boolean IfBookIdExit(String id){
		boolean right=false;
		String sql="select*from book where id='"+id+"'";
		ResultSet rs=DbOp.executeQuery(sql);
		try{
			while(rs.next()){
			
				right = true;
			}
				//right = false;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"无法正常读取数据");
		}
		return right;
	}
	public void clearAllText(){
		for(int i=0;i<booktxt.length;i++){
			booktxt[i].setText("");
		}
	}

	public static void main(String[] args){
		new BookUpdate();
	}
}