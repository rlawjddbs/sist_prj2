package kr.co.sist.sc.admin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.sc.admin.controller.SCAMainController;
import kr.co.sist.sc.admin.controller.SCASnackMenuAddController;

public class SCASnackMenuAddView extends JDialog {

	private JButton jbtSnackImg, jbtSnackInsert, jbtClose;
	private JTextField jtfSnackName, jtfPrice;
	private JLabel jlSnackImg;
	
	private JTextArea jtaSnackInfo;
	public SCASnackMenuAddView(SCASnackManageView scasmv, JButton jbt) {
		super(scasmv, "�޴� �߰��ϱ�", true);
		setLayout(null);
		
		// ��� ����
		JLabel jlBg = new JLabel();
		jlBg.setIcon(new ImageIcon("C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/snack_management_6-3_add_menu_bg(620x555).png"));
		jlBg.setBounds(0, 0, 620, 555);
		
		/*�� ����*/
		// ���� �⺻ �̹��� �� ���� �� ����
		jlSnackImg = new JLabel();
		jlSnackImg.setBounds(17, 20, 325, 325);
		jlSnackImg.setIcon(new ImageIcon("C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jl_no_snack_image(325x325).png"));
		
		// JTF���� ��ġ�� �� ����
		JLabel jlSnackName = new JLabel("������");
		JLabel jlPrice = new JLabel("������");
		JLabel jlSnackInfo = new JLabel("Ư����");
		
		// �� �۲�, ���� ����
		jlSnackName.setFont(new Font("�����ٸ����", Font.BOLD, 20));
		jlPrice.setFont(new Font("�����ٸ����", Font.BOLD, 20));
		jlSnackInfo.setFont(new Font("�����ٸ����", Font.BOLD, 20));

		jlSnackName.setForeground(Color.WHITE);
		jlPrice.setForeground(Color.WHITE);
		jlSnackInfo.setForeground(Color.WHITE);
		
		// �� ��ġ, ũ�� ����
		jlSnackName.setBounds(360, 20, 80, 30);
		jlPrice.setBounds(360, 65, 80, 30);
		jlSnackInfo.setBounds(360, 110, 80, 30);
		
		// JTF ����
		jtfSnackName = new JTextField();
		jtfPrice = new JTextField();
		
		// JTA ����
		jtaSnackInfo = new JTextArea();
		
		// JScrollPane ����
		JScrollPane jspSnackInfo = new JScrollPane(jtaSnackInfo);
		jspSnackInfo.setBounds(360, 150, 240, 193);
		
		// �۲� ����
		jtfSnackName.setFont(new Font("�����ٸ����", Font.BOLD, 20));
		jtfPrice.setFont(new Font("�����ٸ����", Font.BOLD, 20));
		jtaSnackInfo.setFont(new Font("�����ٸ����", Font.BOLD, 20));
		
		// JTA �ڵ� �ٹٲ� ����
		jtaSnackInfo.setLineWrap(true);
		
		// JButton ����
		jbtSnackImg = new JButton();
		jbtSnackInsert = new JButton();
		jbtClose = new JButton();

		// ������
		jbtSnackImg.setIcon(new ImageIcon("C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jbt_add_image(125x40).png"));
		jbtSnackInsert.setIcon(new ImageIcon("C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jbt_add_menu(125x40).png"));
		jbtClose.setIcon(new ImageIcon("C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jbt_close(125x40).png"));

		// setbounds
		jtfSnackName.setBounds(430, 20, 170, 30);
		jtfPrice.setBounds(430, 65, 170, 30);
		jtaSnackInfo.setBounds(360, 150, 240, 193);
		
		jbtSnackImg.setBounds(120, 355, 125, 40);
		jbtSnackInsert.setBounds(178, 450, 125, 40);
		jbtClose.setBounds(313, 450, 125, 40);

		
		// ���� ����
		jbtSnackImg.setContentAreaFilled(false);
		jbtSnackImg.setBorderPainted(false);
		
		jbtSnackInsert.setContentAreaFilled(false);
		jbtSnackInsert.setBorderPainted(false);
		
		jbtClose.setContentAreaFilled(false);
		jbtClose.setBorderPainted(false);
		// .setContentAreaFilled(boolean) : Ŭ���� ���� ���ͷ����� �����ϴ� �޼���
		// .setBorderPainted(boolean) : ���콺�� �ش� ��ư ���� �ö��� ���� ���ͷ����� �����ϴ� �޼���		
		
		
		// jlBg�� ������Ʈ �ֱ�
		jlBg.add(jlSnackImg);
		jlBg.add(jlSnackName);
		jlBg.add(jlPrice);
		jlBg.add(jlSnackInfo);
		
		jlBg.add(jtfSnackName);
		jlBg.add(jtfPrice);
		jlBg.add(jspSnackInfo);
		jlBg.add(jbtSnackImg);
		jlBg.add(jbtSnackInsert);
		jlBg.add(jbtClose);
		
		add(jlBg);
		
		// �̺�Ʈ ó��
		SCASnackMenuAddController scasmac = new SCASnackMenuAddController(scasmv, this);
		addWindowListener(scasmac);
		jbtSnackImg.addActionListener(scasmac);
		jbtSnackInsert.addActionListener(scasmac);
		jbtClose.addActionListener(scasmac);
		setBounds(scasmv.getX()+scasmv.getWidth(), scasmv.getY(), 620, 555);
		setResizable(false);
		setVisible(true);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	} // SCASnackMenuAddView

	public JButton getJbtSnackImg() {
		return jbtSnackImg;
	}

	public JButton getJbtSnackInsert() {
		return jbtSnackInsert;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public JTextField getJtfSnackName() {
		return jtfSnackName;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JTextArea getjtaSnackInfo() {
		return jtaSnackInfo;
	}

	public JLabel getJlSnackImg() {
		return jlSnackImg;
	}
	
	
	
}
