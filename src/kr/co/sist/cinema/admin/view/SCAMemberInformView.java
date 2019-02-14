package kr.co.sist.cinema.admin.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.cinema.admin.controller.SCAMemberInformController;

@SuppressWarnings("serial")
public class SCAMemberInformView extends JDialog {
	private JTextField jtfMemberId, jtfName, jtfHiredate, jtfPhone, jtfMembership, jtfHoldPoint, jtfAccPoint, jtfInputDate;
	private JPasswordField jpfPassword;
	private JButton jbtMemberUpdate, jbtMemberDelete, jbtClose;
	
	public SCAMemberInformView(SCAMemberManageView scammv) {
		super(scammv, "ȸ�� ���� - ȸ�� ����", true);
		
		SCAMemberInformController scamic = new SCAMemberInformController(this);
		
		addWindowListener(scamic);
		
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
	} // SCAMemberInformView

	public JTextField getJtfMemberId() {
		return jtfMemberId;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfHiredate() {
		return jtfHiredate;
	}

	public JTextField getJtfPhone() {
		return jtfPhone;
	}

	public JTextField getJtfMembership() {
		return jtfMembership;
	}

	public JTextField getJtfHoldPoint() {
		return jtfHoldPoint;
	}

	public JTextField getJtfAccPoint() {
		return jtfAccPoint;
	}

	public JTextField getJtfInputDate() {
		return jtfInputDate;
	}

	public JPasswordField getJpfPassword() {
		return jpfPassword;
	}

	public JButton getJbtMemberUpdate() {
		return jbtMemberUpdate;
	}

	public JButton getJbtMemberDelete() {
		return jbtMemberDelete;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}
	
} // class
