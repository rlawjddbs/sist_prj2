package kr.co.sist.cinema.admin.vo;

/**
 * @author owner
 * ȸ�� ���� - ȸ�� ���� (���� ���� ��ư)
 */
public class SCAMemberUpdateVO {
	private String password, name, phone;

	public SCAMemberUpdateVO(String password, String name, String phone) {
		this.password = password;
		this.name = name;
		this.phone = phone;
	} // SCAMemberUpdateVO

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}
	
} // class
