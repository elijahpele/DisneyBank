/**
 * Object of type Person
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public abstract class Person {
	
	private String firstName;
	private String lastName;
	private String dob;
	private String address;
	private String phoneNum;
	
	public Person() {}
	
	/**
	 * 
	 * @param first
	 * @param last
	 * @param dob
	 * @param addy
	 * @param pNum
	 */
	public Person(String first, String last, String dob, String addy, String pNum) {
		this.firstName = first;
		this.lastName = last;
		this.dob = dob;
		this.address = addy;
		this.phoneNum = pNum;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", address=" + address
				+ ", phoneNum=" + phoneNum + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String fullName() {
		String s = this.firstName +" " +this.lastName;
		return s;
}
}
