//Back.java;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.text.*;
import javax.swing.JOptionPane;

public class Back extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7670129939284773294L;
	Label bookidlb = new Label("图书编号"), readeridlb = new Label("读者编号");
	TextField bookidtxt = new TextField(), readeridtxt = new TextField();
	Button querybtn = new Button("查询"), borrowbtn = new Button("还书"),
			closebtn = new Button("清除");
	String SepLine = "--------------------------------------------------";
	String[] sep = { "图书信息", "读者信息", "借阅信息" };
	Label[] seplabel = new Label[3];
	String[] optionname = { "书名：", "作者：", "出版社：", "出版时间：", "定价：", "存量：", "姓名：","类型：", "可借数：", "可借天数：", "借阅日期：","阅读天数：","还书日期" };
	Label[] alloption = new Label[13];
	Label[] showoption = new Label[13];

	public Back() {
		setTitle("图书归还");
		setLayout(null);
		setSize(500, 470);
		setResizable(false);
		this.setForeground(Color.BLACK);
		bookidlb.setBounds(50, 50, 50, 20);
		bookidtxt.setBounds(110, 50, 100, 20);
		readeridlb.setBounds(220, 50, 50, 20);
		readeridtxt.setBounds(280, 50, 100, 20);
		querybtn.setBounds(400, 50, 50, 20);
		add(bookidlb);
		add(bookidtxt);
		add(readeridlb);
		add(readeridtxt);
		add(querybtn);
		int lx = 50, ly = 90, i = 0, k = 0;
		for (int j = 0; j < alloption.length; j++) {
			if (lx > 300) {
				lx = 50;
				ly = ly + 30;
			}
			if (ly == 90 || ly == 210 || ly == 300) {
				System.out.println(i);// /太奇怪了
				seplabel[i] = new Label(SepLine + sep[i] + SepLine);
				seplabel[i].setBounds(20, ly, 440, 20);
				add(seplabel[i]);
				j--;
				k++;
				if (k <= 1) {
					i = 0;
				}
				if (k == 2 || k == 3) {
					i = 1;
				}
				if (k == 4) {
					i = 2;
				}
			} else {
				alloption[j] = new Label(optionname[j]);
				showoption[j] = new Label("");
				alloption[j].setBounds(lx, ly, 70, 20);
				showoption[j].setBounds(lx + 70, ly, 150, 20);
				add(alloption[j]);
				add(showoption[j]);
			}
			lx = lx + 250;
		}
		borrowbtn.setBounds(110, 400, 50, 20);
		closebtn.setBounds(310, 400, 50, 20);
		add(borrowbtn);
		add(closebtn);
		borrowbtn.setEnabled(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				//System.exit(0);
			}
		});
		querybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				queryActionPerformed(e);
			}
		});

		borrowbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowActionPerformed(e);
			}
		});
		closebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setInitialize();	
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void borrowActionPerformed(ActionEvent e) {
		String bookid = bookidtxt.getText(), readerid = readeridtxt.getText();
		if (!bookid.equals("") && !readerid.equals("")) {
			Date currentdate = new Date();
			String borrowbackdate = showoption[12].getText();
			String sql = "update borrow set back_date='"+borrowbackdate+"',if_back='是'";
			sql=sql+"where book_id='"+bookid+"'and reader_id='"+readerid+"'and if_back='否'";
			String sql1 = "update book set stock='"
					+ (Integer.parseInt(showoption[5].getText()) + 1) + "'"
					+ "where id='" + bookid + "'";
			int success = DbOp.executeUpdate(sql);
			if (success == 1) {
				DbOp.executeUpdate(sql1);
				JOptionPane.showMessageDialog(null, "还书成功");

			} else {
				JOptionPane.showMessageDialog(null, "还书数据登记失败！");
			}
			setInitialize();
		}
	}

	public void queryActionPerformed(ActionEvent e) {
		String bookid = bookidtxt.getText(), readerid = readeridtxt.getText();
		if (!bookid.equals("") && !readerid.equals("")) {
		} else {
			JOptionPane.showMessageDialog(null, "图书编号和读者编号都不可以为空");
			setInitialize();
			return;
		}
		Book book = BookSelect.SelectBookByID(bookid);
		Reader reader = ReaderSelect.SelectReaderByID(readerid);
		if (!IfBorrowBack.findBook(bookid, readerid)) {
			JOptionPane.showMessageDialog(null,"查询不到该读者借阅这本书");
			setInitialize();
			return;

		} else {

		}
		if (book != null && reader != null) {
			showoption[0].setText(book.getBookname().toString());
			showoption[1].setText(book.getAuthor().toString());
			showoption[2].setText(book.getPublisher().toString());
			showoption[3].setText(book.getPublish_time().toString());
			showoption[4].setText(String.valueOf(book.getPrice()));
			showoption[5].setText(String.valueOf(book.getStock()));
			showoption[6].setText(reader.getReadername());
			showoption[7].setText(reader.getReadertype());
			showoption[8].setText(String.valueOf(reader.getMax_num()));
			showoption[9].setText(String.valueOf(reader.getDays_num()));
		} else {
			JOptionPane.showMessageDialog(null, "不存在该图书或该读者");
			setInitialize();
			return;

		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentdate = new Date();
		Date thisbookborrowdate=getBorrowDate(bookid,readerid);
		String daycutearlyday=getReaderBorrowDays(thisbookborrowdate,currentdate);
		showoption[10].setText(sdf.format(thisbookborrowdate));
		showoption[12].setText(sdf.format(currentdate));
		showoption[11].setText(daycutearlyday);
		borrowbtn.setEnabled(true);
		bookidtxt.setEditable(false);
		readeridtxt.setEditable(false);
	}
	public boolean IfLeapYear(int year) {// 是否闰年，用于计算时间差
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		}
		return false;
	}

	public String getReaderBorrowDays(Date date1, Date date2) {// 计算时间差
		String sum = "";
		int year1 = 0, month1 = 0, day1 = 0;
		int year2 = 0, month2 = 0, day2 = 0;
		int[] monthdays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		cal1.setTime(date1);
		year1 = cal1.get(Calendar.YEAR);
		month1 = cal1.get(Calendar.MONTH);
		day1 = cal1.get(Calendar.DAY_OF_MONTH);
		cal2.setTime(date2);
		year2 = cal2.get(Calendar.YEAR);
		month2 = cal2.get(Calendar.MONTH);
		day2 = cal2.get(Calendar.DAY_OF_MONTH);
		if (IfLeapYear(year1)) {
			monthdays[1] = 29;
		} else {
			monthdays[1] = 28;
		}
		int a = 3, count = 0;
		while (a > 0) {
			if (month1 > monthdays.length - 1) {
				year1++;
				month1 = 0;
			}
			if (IfLeapYear(year1)) {
				monthdays[1] = 29;
			} else {
				monthdays[1] = 28;
			}
			if (day1 > monthdays[month1]) {
				month1++;
				day1 = 1;
			}
			day1++;
			count++;
			if (year1 >= year2 && month1 >= month2 && day1 >= day2) {
				a = -1;

			}
		}
		sum = String.valueOf(count);
		return sum;
	}
	public Date getBorrowDate(String bookid, String readerid) {// 获取读者借书日期
		String sql, mydate = "";
		Date readerdate = new Date();
		sql = "select * from borrow where book_id='" + bookid
				+ "' and reader_id='" + readerid + "' and if_back='否'";
		ResultSet rs = DbOp.executeQuery(sql);
		try {
			if (rs.next()) {
				mydate = rs.getString("borrow_date");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			readerdate = sdf.parse(mydate);
			DbOp.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "借书日期查询失败");
		} catch (ParseException e2) {
			JOptionPane.showMessageDialog(null, "借书日期异常");
		}
		return readerdate;
	}

	public void setInitialize() {
		for (int i = 0; i < showoption.length; i++) {
			showoption[i].setText("xxxxx");

		}
		bookidtxt.setText("");
		readeridtxt.setText("");
		bookidtxt.setEditable(true);
		readeridtxt.setEditable(true);
		borrowbtn.setEnabled(false);
	}

	public static void main(String[] args){
		new Back();
	}
}