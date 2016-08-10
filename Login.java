//Login.java
//import PublicModule.DbOp;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;
public class Login extends Frame{
	Toolkit tool= getToolkit();
	String url="login.png";
	Image img=tool.getImage(url);
		public void paint(Graphics g){
		g.drawImage(img,0,0,this);
	}

	TextField text_user,text_pass;
	public Login(){
		this.setTitle("Please Login");
		this.setLayout(null);
		//setOpaque(true);
		this.setSize(260,170);setResizable(false);
		Label userlb=new Label("UserName:");
		Label passlb=new Label("PassWord:");
		Button sure=new Button("Login");
		Button cancel=new Button("Cancel");
		text_user=new TextField();
		text_pass=new TextField();
		text_pass.setEchoChar('°Ò');
		userlb.setBounds(30,53,70,20);
		passlb.setBounds(30,83,70,20);
		text_user.setBounds(110,50,120,20);
		text_pass.setBounds(110,80,120,20);
		sure.setBounds(42,120,80,25);
		cancel.setBounds(135,120,80,25);
		this.add(text_user);this.add(text_pass);this.add(sure);this.add(cancel);
		sure.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sureActionListener(e);
				
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DbOp.close();
				dispose();

			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				DbOp.close();
				dispose();
			}
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public void sureActionListener(ActionEvent le){
		String user=text_user.getText();
		String pass=text_pass.getText();
		String is_admin="";
		if(user.equals("")||pass.equals("")){
			JOptionPane.showMessageDialog(null,"√‹¬Î≤ªƒ‹Œ™ø’£¨«Î ‰»Î√‹¬Î");
			return;
		}
		try{
			String sql="select * from user where username="+"'"+user+"'"+"and password="+"'"+pass+"'";
			ResultSet rs=DbOp.executeQuery(sql);
				if(rs.next()){
					is_admin=rs.getString("is_admin");
				}else{
					JOptionPane.showMessageDialog(null,"Wrong that is UserNmae or Password ");
					return;
				}
			GlobalVar.login_user=user;				
			ShowMain show=new ShowMain();
			show.setRights(is_admin);
			System.out.println("Successed");
			dispose();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,"the wrong from information");
		}
	}
	public static void main(String[] args){
		new Login();
	}
}