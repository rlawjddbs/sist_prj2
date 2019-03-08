package kr.co.sist.sc.admin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.sc.admin.controller.SCAMemberInformController;
import kr.co.sist.sc.admin.vo.SCAMemberInformVO;

public class SCAMemberInformView extends JDialog{

	private JTextField jtfMemberId, jtfName, jtfBirthdate, jtfPhone, jtfMembership, jtfHoldPoint, jtfAccPoint, jtfInputDate;
	private JPasswordField jpfPassword;
	private JButton jbtMemberUpdate, jbtMemberDelete, jbtClose;
	
	public SCAMemberInformView(SCAMemberManageView scammv, SCAMemberInformVO scamivo) {
		super(scammv,"["+scamivo.getMember_id()+"]���� ȸ������", true);
		
		// ��� ���̵� �޾ƾ� �ϴ°�? �� ���� �ʿ���
		
		// ���, Ÿ��Ʋ ��
		JLabel jlBg = new JLabel();
		jlBg.setBounds(0, 0, 390, 550);
		jlBg.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/member_management_4-2_info_bg(390x520).png"));
		
		JLabel jlTitle = new JLabel("ȸ�� ����");
		jlTitle.setFont(new Font("�����ٸ����", Font.BOLD, 24));
		jlTitle.setForeground(Color.WHITE);
		jlTitle.setBounds(25, 15, 150, 40);
		
		JLabel jlMemberId = new JLabel("���̵�");
		JLabel jlPassword = new JLabel("��й�ȣ");
		JLabel jlName = new JLabel("�̸�");
		JLabel jlBirthDate = new JLabel("�������");
		JLabel jlPhone = new JLabel("�޴��� ��ȣ");
		JLabel jlMembership = new JLabel("ȸ�� ���");
		JLabel jlHoldPoint = new JLabel("���� ����Ʈ");
		JLabel jlAccPoint = new JLabel("���� ����Ʈ");
		JLabel jlInputDate = new JLabel("ȸ�� ������");
		
		
		// �� �۲� ����
		jlMemberId.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlPassword.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlName.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlBirthDate.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlPhone.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlMembership.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlHoldPoint.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlAccPoint.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jlInputDate.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		
		// �� �ؽ�Ʈ ���� ����
		jlMemberId.setForeground(Color.WHITE);
		jlPassword.setForeground(Color.WHITE);
		jlName.setForeground(Color.WHITE);
		jlBirthDate.setForeground(Color.WHITE);
		jlPhone.setForeground(Color.WHITE);
		jlMembership.setForeground(Color.WHITE);
		jlHoldPoint.setForeground(Color.WHITE);
		jlAccPoint.setForeground(Color.WHITE);
		jlInputDate.setForeground(Color.WHITE);
		
		// �� ��ġ, ũ�� ����
		jlMemberId.setBounds(25, 84, 80, 22);
		jlPassword.setBounds(25, 121, 80, 22);
		jlName.setBounds(25, 158, 80, 22);
		jlBirthDate.setBounds(25, 195, 80, 22);
		jlPhone.setBounds(25, 232, 80, 22);
		jlMembership.setBounds(25, 269, 80, 22);
		jlHoldPoint.setBounds(25, 306, 80, 22);
		jlAccPoint.setBounds(25, 343, 80, 22);
		jlInputDate.setBounds(25, 380, 80, 22);
		
		// JTF, JPF ����
		jtfMemberId = new JTextField(scamivo.getMember_id());
		jtfName = new JTextField(scamivo.getName());
		jtfBirthdate = new JTextField(scamivo.getBirthdate());
		jtfPhone = new JTextField(scamivo.getPhone());
		jtfMembership = new JTextField(scamivo.getMembership());
		jtfHoldPoint = new JTextField(String.valueOf(scamivo.getHold_point()));
		jtfAccPoint = new JTextField(String.valueOf(scamivo.getAcc_point()));
		jtfInputDate = new JTextField(scamivo.getInput_date());
		jpfPassword = new JPasswordField(scamivo.getPassword());
		
		// JTF, JPF ���� ����
		jtfMemberId.setEditable(false);
		jtfBirthdate.setEditable(false);
		jtfMembership.setEditable(false);
		jtfHoldPoint.setEditable(false);
		jtfAccPoint.setEditable(false);
		jtfInputDate.setEditable(false);
		jpfPassword.setEditable(false);
		
		// JTF, JPF �۲� ����
		jtfMemberId.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		jtfName.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jtfBirthdate.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		jtfPhone.setFont(new Font("�����ٸ����", Font.BOLD, 15));
		jtfMembership.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		jtfHoldPoint.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		jtfAccPoint.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		jtfInputDate.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		jpfPassword.setFont(new Font("�����ٸ����", Font.PLAIN, 15));
		
		// JTF ��ġ ����
		jtfMemberId.setBounds(105, 84, 250, 22);
		jpfPassword.setBounds(105, 121, 250, 22);
		jtfName.setBounds(105, 158, 250, 22);
		jtfBirthdate.setBounds(105, 195, 250, 22);
		jtfPhone.setBounds(105, 232, 250, 22);
		jtfMembership.setBounds(105, 269, 250, 22);
		jtfHoldPoint.setBounds(105, 306, 250, 22);
		jtfAccPoint.setBounds(105, 343, 250, 22);
		jtfInputDate.setBounds(105, 380, 250, 22);
		
		// JButton ����
		jbtMemberUpdate = new JButton();
		jbtMemberDelete = new JButton();
		jbtClose = new JButton();
		
		// JButton ������ ����
		jbtMemberUpdate.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbt_member_update(100x30).png"));
		jbtMemberDelete.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbt_member_delete(100x30).png"));
		jbtClose.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbt_close(100x30).png"));
		
		// JButton ��ġ, ũ��
		jbtMemberUpdate.setBounds(25, 430, 100, 30);
		jbtMemberDelete.setBounds(140, 430, 100, 30);
		jbtClose.setBounds(255, 430, 100, 30);
		
		// JButton ������ ���ֱ�
		jbtMemberUpdate.setContentAreaFilled(false);
		jbtMemberUpdate.setBorderPainted(false);
		jbtMemberDelete.setContentAreaFilled(false);
		jbtMemberDelete.setBorderPainted(false);
		jbtClose.setContentAreaFilled(false);
		jbtClose.setBorderPainted(false);
		
		jlBg.add(jlTitle);
		jlBg.add(jlMemberId);
		jlBg.add(jlPassword);
		jlBg.add(jlName);
		jlBg.add(jlBirthDate);
		jlBg.add(jlPhone);
		jlBg.add(jlMembership);
		jlBg.add(jlHoldPoint);
		jlBg.add(jlAccPoint);
		jlBg.add(jlTitle);
		jlBg.add(jlInputDate);
		
		jlBg.add(jtfMemberId);
		jlBg.add(jtfName);
		jlBg.add(jtfBirthdate);
		jlBg.add(jtfPhone);
		jlBg.add(jtfMembership);
		jlBg.add(jtfHoldPoint);
		jlBg.add(jtfAccPoint);
		jlBg.add(jtfInputDate);
		jlBg.add(jpfPassword);
		
		jlBg.add(jbtMemberUpdate);
		jlBg.add(jbtMemberDelete);
		jlBg.add(jbtClose);
		
		add(jlBg);
		
		// �̺�Ʈ ó��
		SCAMemberInformController scamic = new SCAMemberInformController(this, scammv);
		addWindowListener(scamic);
		jbtMemberUpdate.addActionListener(scamic);
		jbtMemberDelete.addActionListener(scamic);
		jbtClose.addActionListener(scamic);
		
		jbtMemberUpdate.addMouseListener(scamic);
		jbtMemberDelete.addMouseListener(scamic);
		jbtClose.addMouseListener(scamic);
		
		
		setBounds(scammv.getX()+scammv.getWidth(), scammv.getY(), 390, 520);
		setResizable(false);
		setVisible(true);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	} // SCAMemberInformView

	public JTextField getJtfMemberId() {
		return jtfMemberId;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfBirthdate() {
		return jtfBirthdate;
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
