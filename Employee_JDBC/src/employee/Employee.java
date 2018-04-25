package employee;

public class Employee {
	private String name;
	private String empSSN; 
	private double salary;
	private String position;
	private String department;
	private int age;
	private String email;
	private String hireDate;
	
	public Employee() {
		
	}
	
	public Employee(String name, 
					String empSSN, 
					double salary, 
					String position, 
					String department,
					int age, 
					String email ) {
		this.name = name;
		this.empSSN = empSSN;
		this.salary = salary;
		this.position = position;
		this.department = department;
		this.age = age;
		this.email = email;
		
	}
	
	public Employee(String name, 
					String empSSN, 
					double salary, 
					String position, 
					String department,
					int age,
					String email, 
					String hireDate) {
		this.name = name;
		this.setEmpSSN(empSSN);
		this.salary = salary;
		this.position = position;
		this.department = department;
		this.age = age;
		this.email = email;
		this.hireDate = hireDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmpSSN() {
		return empSSN;
	}
	public void setEmpSSN(String empSSN) {
		this.empSSN = empSSN;
	}
	
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
}