package application;

import java.util.Date;

public class LogInfo {

	private int empid;
	private int taskid;
	private Date starttime;
	private Date endtime;
	private int keycount;
	private int mousecount;
	private String image;
	private String base64encodedImage;
	
	public LogInfo() {
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getKeycount() {
		return keycount;
	}

	public void setKeycount(int keycount) {
		this.keycount = keycount;
	}
	
	public void increaseKeycount() {
		this.keycount += 1;
	}

	public int getMousecount() {
		return mousecount;
	}

	public void setMousecount(int mousecount) {
		this.mousecount = mousecount;
	}
	
	public void increaseMousecount() {
		this.mousecount += 1;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBase64encodedImage() {
		return base64encodedImage;
	}

	public void setBase64encodedImage(String base64encodedImage) {
		this.base64encodedImage = base64encodedImage;
	}

	
}
