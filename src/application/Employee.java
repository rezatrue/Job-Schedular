package application;

import java.util.Date;

public class Employee {
	private int empid ;
	private String empName;
	private String empEmail;  
	private int empPassword;
	private String empPhone; 
	private Date empCreated;
	private int empActive; 
	private int empLevel;
	
	public Employee() {
	}

	public Employee(int empid, String empName, String empEmail, int empPassword, String empPhone, Date empCreated,
			int empActive, int empLevel) {
		super();
		this.empid = empid;
		this.empName = empName;
		this.empEmail = empEmail;
		this.empPassword = empPassword;
		this.empPhone = empPhone;
		this.empCreated = empCreated;
		this.empActive = empActive;
		this.empLevel = empLevel;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public int getEmpPassword() {
		return empPassword;
	}

	public void setEmpPassword(int empPassword) {
		this.empPassword = empPassword;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public Date getEmpCreated() {
		return empCreated;
	}

	public void setEmpCreated(Date empCreated) {
		this.empCreated = empCreated;
	}

	public int getEmpActive() {
		return empActive;
	}

	public void setEmpActive(int empActive) {
		this.empActive = empActive;
	}

	public int getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(int empLevel) {
		this.empLevel = empLevel;
	}
	
	

}
