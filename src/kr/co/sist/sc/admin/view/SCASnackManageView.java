package kr.co.sist.sc.admin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.sc.admin.controller.SCASnackManageController;

public class SCASnackManageView extends JDialog{

	private JButton[][] jbtSnackImg;
	private JButton jbtSnackMenuInsert, jbtSnackMenuDelete, jbtSnackPayment, jbtSnackOrderDelete, jbtClose;
	private DefaultTableModel dtmOrderList;
	private JTable jtabOrderList;
	private ButtonGroup jbtGroup;
	
	public SCASnackManageView(SCAMainView scamv) {
		super(scamv,"��������",true);
		setLayout(null);
		
		JLabel jlBg = new JLabel();
		jlBg.setBounds(0, 0, 900, 800);
		jlBg.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/admin_snack_bg(900x800).png"));
	
		// JButton �迭�� ���� JPanel
		JPanel jpSnackImgBox = new JPanel();
		jpSnackImgBox.setLayout(null);
		jpSnackImgBox.setBackground(new Color(0, 0, 0, 0));
		jpSnackImgBox.setBounds(50, 20, 793, 339);
		
		// JButton �迭 (��ϵ� ���� �����۵�)
		jbtSnackImg = new JButton[2][4];
		// JButton ���� ������ ���� ���� colGab, lineGab
		int colGab = 0, lineGab = 0;
		for(int i = 0; i < jbtSnackImg.length; i++) {
			for(int j = 0; j < jbtSnackImg[i].length; j++) {
				jbtSnackImg[i][j] = new JButton();
				if(j != 0) {
					colGab += 15;
				} // end if
				jbtSnackImg[i][j].setBounds(j*187+colGab, i*162+lineGab, 187, 162);
				jbtSnackImg[i][j].setBackground(new Color(255, 255, 255));
				jpSnackImgBox.add(jbtSnackImg[i][j]);
			} // end for
			colGab = 0;
			lineGab = 15;
		} // end for
		
		// JButton ������Ʈ
		jbtSnackMenuInsert = new JButton();
		jbtSnackPayment = new JButton();
		jbtSnackMenuDelete = new JButton();
		jbtSnackOrderDelete = new JButton();
		jbtClose = new JButton();
		
		jbtSnackMenuInsert.setContentAreaFilled(false);
		jbtSnackMenuInsert.setBorderPainted(false);
		jbtSnackPayment.setContentAreaFilled(false);
		jbtSnackPayment.setBorderPainted(false);
		jbtSnackMenuDelete.setContentAreaFilled(false);
		jbtSnackMenuDelete.setBorderPainted(false);
		jbtSnackOrderDelete.setContentAreaFilled(false);
		jbtSnackOrderDelete.setBorderPainted(false);
		jbtClose.setContentAreaFilled(false);
		jbtClose.setBorderPainted(false);
		
		jbtSnackMenuInsert.setBounds(315, 374, 125, 40);
		jbtSnackMenuDelete.setBounds(455, 374, 125, 40);
		jbtSnackPayment.setBounds(245, 700, 125, 40);
		jbtSnackOrderDelete.setBounds(385, 700, 125, 40);
		jbtClose.setBounds(525, 700, 125, 40);
		
		jbtSnackMenuInsert.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbt_add_menu(125x40).png"));
		jbtSnackPayment.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbtSnackPayment(125x40).png"));
		jbtSnackMenuDelete.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbtDeleteMenu(125x40).png"));
		jbtSnackOrderDelete.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbtDeleteOrder(125x40).png"));
		jbtClose.setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbtClose(125x40).png"));

		String[] strTabCols = {"��ȣ","������","����","����","�Ѱ���"}; 
				
		// JTable ������Ʈ
		dtmOrderList = new DefaultTableModel(strTabCols, 0);
		jtabOrderList = new JTable(dtmOrderList);
		jtabOrderList.getTableHeader().setOpaque(false);
		jtabOrderList.getTableHeader().setFont(new Font("�����ٸ����", Font.PLAIN, 16));
		jtabOrderList.getTableHeader().setBackground(new Color(20, 42, 99));
		jtabOrderList.getTableHeader().setForeground(new Color(255, 255, 255));
		jtabOrderList.getTableHeader().setReorderingAllowed(false);
		jtabOrderList.getTableHeader().setResizingAllowed(false);
		jtabOrderList.getTableHeader().setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
		jtabOrderList.setRowHeight(25);
		jtabOrderList.getTableHeader().setPreferredSize(new Dimension(100, 30));
		
		// JTable ��ũ�ѹ�
		JScrollPane jspOrderList = new JScrollPane(jtabOrderList);
		jspOrderList.setBounds(50, 430, 795, 250);
			
		// ��� �󺧿� ������Ʈ �߰�
		jlBg.add(jbtSnackMenuInsert);
		jlBg.add(jbtSnackMenuDelete);
		jlBg.add(jbtSnackPayment);
		jlBg.add(jbtSnackOrderDelete);
		jlBg.add(jspOrderList);
		jlBg.add(jbtClose);
		
		add(jpSnackImgBox);
		add(jlBg);
		
		SCASnackManageController scasmc = new SCASnackManageController(this);
		addWindowListener(scasmc);
		
		// JButton(���� �޴�) �̺�Ʈ �ϰ�ó��
		for(int k = 0; k < jbtSnackImg.length; k ++) {
			for(int l = 0; l < jbtSnackImg[k].length; l ++) {
				jbtSnackImg[k][l].addActionListener(scasmc);
			} // end for
		} // end for
		
		jbtSnackMenuInsert.addActionListener(scasmc);
		jbtSnackPayment.addActionListener(scasmc);
		jbtSnackMenuDelete.addActionListener(scasmc);
		jbtSnackOrderDelete.addActionListener(scasmc);
		jbtClose.addActionListener(scasmc);
		jbtSnackMenuInsert.addMouseListener(scasmc);
		jbtSnackMenuDelete.addMouseListener(scasmc);
		jbtSnackPayment.addMouseListener(scasmc);
		jbtSnackOrderDelete.addMouseListener(scasmc);
		jbtClose.addMouseListener(scasmc);
		
		setBounds(100, 100, 900, 800);
		setResizable(false);
		setVisible(true);
		
	} // SCASnackMageView

	
	/////////////////////////  getter  ///////////////////////////////
	public JButton[][] getJbtSnackImg() {
		return jbtSnackImg;
	}

	public JButton getJbtSnackMenuInsert() {
		return jbtSnackMenuInsert;
	}
	
	public JButton getJbtSnackMenuDelete() {
		return jbtSnackMenuDelete;
	}

	public JButton getJbtSnackPayment() {
		return jbtSnackPayment;
	}

	public JButton getJbtSnackOrderDelete() {
		return jbtSnackOrderDelete;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public DefaultTableModel getDtmOrderList() {
		return dtmOrderList;
	}

	public JTable getJtabOrderList() {
		return jtabOrderList;
	}

	public ButtonGroup getJbtGroup() {
		return jbtGroup;
	}

	
	
	/////////////////////////  getter  ///////////////////////////////

} // class

