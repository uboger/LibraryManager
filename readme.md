#### 登录信息
- 超级管理员（用户名：admin 密码：123）
- 运行前确定你已经成功的安装32位java和配置Access数据源

#### 登陆成功界面
![](https://github.com/uboger/images/blob/master/image/loginin/lbm.gif)

#### 运行环境
- JDK(32位jdk) access是只支持32位（x86)所以必须在32位jdk下运行
- windows mac linux ....
- 数据库（access数据库）
- 使用jdk1.5_x


#### 使用技术
- Java awt 非 swing
- access 数据库
- 数据库驱动 sun.jdbc.odbc.JdbcOdbcDriver

#### 连接数据库代码
- 从access数据库连接代码可以看出access数据库毫无安全可言
```java
public class DbOp{
	private static String driver="sun.jdbc.odbc.JdbcOdbcDriver";
  /**
  *jdbc:odbc:bookdb";
  *数据库名称是 bookdb
  *
  **/
	private static String url="jdbc:odbc:bookdb";
	private static Connection con=null;
	private DbOp(){
		try{
			if(con==null){
				Class.forName(driver);
				con=DriverManager.getConnection(url);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"数据库未能打开！");
			System.out.println(e.getMessage());
		}
	}
```
  
#### 入口类
 - Login.java 点击登陆按钮的代码
 ```java
 	public void sureActionListener(ActionEvent le){
		String user=text_user.getText();//从文本框得到输入的用户名和密码
		String pass=text_pass.getText();
		String is_admin="";
		if(user.equals("")||pass.equals("")){
			JOptionPane.showMessageDialog(null,"密码不能为空，请输入密码");
			return;
		}
		try{
    /**
    *数据库核对密码是否正确，正确就登陆成功
    *
    */
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
 ```
  #### access 数据源配置
  - 将uboger/LibraryManager下的“图书管理.mdb” 下载到本地电脑
  - 建议不要放在有中文名的路径下
  - 下载方法略过
  - 打开>控制面板>所有控制面板项>管理工具>ODBC数据源(32位）进行数据源配置
  - ![](https://github.com/uboger/images/blob/master/image/access/00.png)
  - 用户DNS>添加>选择 Driver do Microsoft Access(*.mdb)项
  - ![](https://github.com/uboger/images/blob/master/image/access/1.png)
  - 
  - ![](https://github.com/uboger/images/blob/master/image/access/2.png)
  - 对话框填写数据源名为 “bookdb”
  - ![](https://github.com/uboger/images/blob/master/image/access/4.png)
  - 选择“图书管理.mdb”作为数据源>确定>确定>确定
  - ![](https://github.com/uboger/images/blob/master/image/access/5.png)
  - ![](https://github.com/uboger/images/blob/master/image/access/6.png)
  
 #### 32位JDK及配置(注意:64位操作系统支持32位JDK,而32位操作系统不支持64位JDK安装）
 - [oracle官网下载](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html"@oracle")jdk-8u181-windows-i586.exe
 - [百度网盘下载java8](https://pan.baidu.com/s/1qBPDtTaIY_IXTsWHck2ZIQ@猪侯美人戏)jdk-8u181-windows-i586.exe
 - [百度网盘下载java7](https://pan.baidu.com/s/1O0a35pjyUvM7ppgGkBl97w@猪侯美人戏)jdk-7u60-windows-i586.exe
 - [百度网盘下载java6](https://pan.baidu.com/s/1K4gaAjtwqZ22mguqfr97YQ@猪侯美人戏)jdk-6u13-windows-i586-p.exe
 - [百度网盘下载java5](https://pan.baidu.com/s/1mold9T3rzu9nQiLNGt8KbQ@猪侯美人戏)jdk-1_5_0_04-windows-i586-p.exe
 - 在环境变量path加入“D:\Program Files (x86)\Java\jdk1.8.0_181\bin”
 - 打开>计算机>属性>高级系统设置>环境变量>path>编辑加入变量值D:\Program Files (x86)\Java\jdk1.8.0_181\bin
 - 以管理员打开CMD命令行
 - 输入 Java -version 测试Java是否安装成功
 - 接着输入 javac 和 java 如果后面有出现内容说明Java安装成功
 
 

 
