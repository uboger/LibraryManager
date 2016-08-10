//package PublicModule;
//Reader.java
public class Reader {
	private static String id,readername,readertype,readersex;
	private static int max_num,days_num;
	public String[] getReaderInformation(){
		String[] infor={id,readername,readertype,readersex,String.valueOf(max_num),String.valueOf(days_num)};
		return infor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReadername() {
		return readername;
	}
	public void setReadername(String readername) {
		this.readername = readername;
	}
	public String getReadertype() {
		return readertype;
	}
	public void setReadertype(String readertype) {
		this.readertype = readertype;
	}
	public String getReaderSex() {
		return readersex;
	}
	public void setReaderSex(String sex) {
		this.readersex = sex;
	}
	public int getMax_num() {
		return max_num;
	}
	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}
	public int getDays_num() {
		return days_num;
	}
	public void setDays_num(int days_num) {
		this.days_num = days_num;
	}
}
