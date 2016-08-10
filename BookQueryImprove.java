// BookQueryImprove.java


import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookQueryImprove extends Frame implements ActionListener {
	private static final long serialVersionUID = 5477686699015910381L;
	TextField seriesName;
	TextArea bookName;
	Button button;

	BookQueryImprove() { // 构造方法
		super("图书查询");
		setBounds(150, 150, 300, 300);
		seriesName = new TextField(16);
		bookName = new TextArea(5, 10);
		button = new Button("确定");
		Panel p1 = new Panel(), p2 = new Panel();
		p1.add(new Label("请输入丛书名："));
		p1.add(seriesName);
		p2.add(button);
		add(p1, "North");
		add(p2, "South");
		add(bookName, "Center");
		button.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setLocationRelativeTo(null); // 使窗体在屏幕上居中放置
		setVisible(true); // 显示窗体
	}

	public void actionPerformed(ActionEvent e) {
		// 如果当前单击对象为按钮
		if (e.getSource() == button) {
			try {
				bookName.setText(null); // 清空文本区
				ListStudent();
			} catch (SQLException ee) {
			}
		}
	}

	private void ListStudent() throws SQLException {
		String bn1, bn2, sqlcmd;
		// 获取输入的丛书代号
		bn1 = seriesName.getText();
		// 如果丛书代号为空，则直接返回
		if (bn1.length()==0){
			return;
		}
		try {
			// 加载JDBC-ODBC驱动程序
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
		}
		// 创建数据库连接
		Connection con = DriverManager.getConnection("jdbc:odbc:javaodbc");
		// 创建Statement对象
		Statement st = con.createStatement();
		// 读出全部记录，得到结果集ResultSet对象
		// 创建SQL语句，like表示模糊匹配，%表示任意字符串
		// “丛书代号 like '输入的丛书代号字符串%'”表示查找丛书代号为
		// 以输入的丛书代号字符串开头的全部丛书
		sqlcmd = "select * from 书目名录 where 丛书代号 like '";
		sqlcmd = sqlcmd + bn1 + "%'";
		// 执行SQL语句
		ResultSet rs = st.executeQuery(sqlcmd);
		boolean boo = false;
		while (rs.next()) {
			bn2 = rs.getString("书名"); // 读取书名
			bookName.append(bn2 + "\n");
			boo = true; // 该系列丛书不为空
		}
		con.close();
		if (boo == false) {
			bookName.append("该系列丛书不存在！");
		}
	}

	public static void main(String[] args) {
		new BookQueryImprove();
	}
}
