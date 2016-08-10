//Borrow.java;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.text.*;
import javax.swing.JOptionPane;

public class Borrow extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7670129939284773294L;
	Label bookidlb = new Label("图书编号"), readeridlb = new Label("读者编号");
	TextField bookidtxt = new TextField(), readeridtxt = new TextField();
	Button querybtn = new Button("查询"), borrowbtn = new Button("借出"),
			closebtn = new Button("清除");
	String SepLine = "--------------------------------------------------";
	String[] sep = { "图书信息", "读者信息", "借阅信息" };
	Label[] seplabel = new Label[3];
	String[] optionname = { "书名：", "作者：", "出版社：", "出版时间：", "定价：", "存量：", "姓名：",
			"类型：", "可借数：", "可借天数：", "已借数量：", "是否可借：", "借阅日期：" };
	Label[] alloption = new Label[13];
	Label[] showoption = new Label[13];

	public Borrow() {
		setTitle("图书借出");
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

	@SuppressWarnings("static-access")
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
		if (IfBorrowBack.findBook(bookid, readerid)) {
			Date date_1 = new Date();

			Date date2 = getBorrowDate(bookid, readerid);
			String fulldays = getReaderBorrowDays(date2, date_1);
			System.out.println("fulldays:  " + fulldays);
			JOptionPane.showMessageDialog(null, book.getBookname() + "已被"
					+ reader.getReadername() + "借阅" + fulldays
					+ "天，且还未归还，该读者不能借阅此图书");
			setInitialize();
			return;

		} else {

		}
		if (book != null && reader != null) {
			if (reader.getMax_num() <= Integer
					.parseInt(getReaderBorrowBookCounts(readerid))) {
				JOptionPane.showMessageDialog(null, "对不起该读者借阅图书太多，不能再借阅");
				return;
			}

			if (Integer.parseInt(getImposeRestrictionsOnDays_num(readerid)) > reader
					.getDays_num()) {
				JOptionPane.showMessageDialog(null, "对不起"
						+ reader.getReadername() + "借阅天数已超过"
						+ reader.getDays_num() + "天，不能再借阅");
				return;
			}
			if (book.getStock() == 0) {
				JOptionPane.showMessageDialog(null, "对不起该图书已无库存，不能再借阅");
				return;
			}
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

		showoption[10].setText(getReaderBorrowBookCounts(readerid));
		showoption[12].setText(sdf.format(currentdate));
		showoption[11].setText("是");
		borrowbtn.setEnabled(true);
		bookidtxt.setEditable(false);
		readeridtxt.setEditable(false);
	}

	public void borrowActionPerformed(ActionEvent e) {
		String bookid = bookidtxt.getText(), readerid = readeridtxt.getText();
		if (!bookid.equals("") && !readerid.equals("")) {
			Date currentdate = new Date();
			String borrowdate = showoption[12].getText();
			String IOL = getInsertOrderedList();
			String sql = "insert into borrow(id,book_id,reader_id,borrow_date,if_back)  values('"
					+ IOL
					+ "','"
					+ bookid
					+ "','"
					+ readerid
					+ "','"
					+ borrowdate + "','否')";
			String sql1 = "update book set stock='"
					+ (Integer.parseInt(showoption[5].getText()) - 1) + "'"
					+ "where id='" + bookid + "'";
			int success = DbOp.executeUpdate(sql);
			if (success == 1) {
				DbOp.executeUpdate(sql1);
				JOptionPane.showMessageDialog(null, "借书成功");

			} else {
				JOptionPane.showMessageDialog(null, "借书数据登记失败！");
			}
			setInitialize();
		}
	}

	public String getImposeRestrictionsOnDays_num(String readerid) {// 读者借阅最早图书未归还天数；
		String sql, mydate = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sql = "select * from borrow where reader_id='" + readerid
				+ "' and if_back='否'";
		ResultSet rs = DbOp.executeQuery(sql);
		int a = 0;
		try {
			while (rs.next()) {
				a++;
			}
			Date[] sqldate = new Date[a];
			int[] bookborrowdays = new int[a];
			a = 0;
			ResultSet rs1 = DbOp.executeQuery(sql);
			while (rs1.next()) {
				sqldate[a] = sdf.parse(rs1.getString("borrow_date"));
				bookborrowdays[a] = Integer.parseInt(getReaderBorrowDays(
						sqldate[a], date));
				a++;
			}
			int temp = -1;
			for (int i = 0; i < bookborrowdays.length; i++) {
				if (bookborrowdays[i] > temp) {
					temp = bookborrowdays[i];
				}
			}
			mydate = String.valueOf(temp);
			DbOp.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "借书日期查询失败");
		} catch (ParseException e2) {
			JOptionPane.showMessageDialog(null, "借书日期异常");
		}
		return mydate;
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

	public String getReaderBorrowBookCounts(String id) {// 获取读者借阅数量
		String numbers = "";
		int count = 0;
		String sql = "select * from borrow where reader_id='" + id + "'";
		ResultSet rs = DbOp.executeQuery(sql);
		try {
			while (rs.next()) {
				if (rs.getString("if_back").equals("否")) {
					count++;
				}
			}
			numbers = String.valueOf(count);
			DbOp.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "借书数量查询失败");
		}
		return numbers;
	}

	public String getInsertOrderedList() {// 自动生成借阅表编号
		String returnstring = "";
		String sql = "select * from borrow";

		try {
			int count = 0;
			ResultSet rs = DbOp.executeQuery(sql);
			while (rs.next()) {

				count++;
			}
			String[] allid = new String[count];
			int[] intofid = new int[count];

			int i = 0;
			ResultSet rs1 = DbOp.executeQuery(sql);
			while (rs1.next()) {
				allid[i] = rs1.getString("id");
				intofid[i] = Integer.parseInt(allid[i]);
				i++;
			}
			int temp = -1;
			for (int j = 0; j < intofid.length; j++) {
				if (intofid[j] > temp) {
					temp = intofid[j];
				}
			}
			returnstring = String.valueOf(temp + 1);
			int len = returnstring.length();
			for (int f = 0; f < 9 - len; f++) {
				returnstring = "0" + returnstring;

			}
			DbOp.close();
		} catch (SQLException ee) {

		}

		return returnstring;
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

	public static void main(String[] args) {
		new Borrow();
	}
}
