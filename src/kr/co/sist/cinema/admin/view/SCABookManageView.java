package kr.co.sist.cinema.admin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.cinema.admin.controller.SCABookManageController;

@SuppressWarnings("serial")
public class SCABookManageView extends JDialog {
	private JComboBox<String> jcbMovieTitle;
	private JComboBox<Integer> jcbPersonnel;
	private JButton jbtSearch, jbtBook, jbtClose;
	private DefaultTableModel dtmOnScreenList, dtmBookList;
	private JTable jtabOnScreenList, jtabBookList;
	
	public SCABookManageView(SCAMainView scamv) {
		super(scamv, "�ֿ�� - ���� ����", true);
		
		// jtab
		String[] onScreenColumnNames = {
			"��ȣ", "��ȭ �ڵ�", "��ȭ ����", "�� ��ȣ", "�󿵰�", "�� ���� �ð�", "�� ���� �ð�", "�ܿ� �¼� ��", "�� �¼� ��"	
		};
		
		dtmOnScreenList = new DefaultTableModel(onScreenColumnNames, 15) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			} // isCellEditable
		};
		
		jtabOnScreenList = new JTable(dtmOnScreenList);
		jtabOnScreenList.getTableHeader().setFont(new Font("�������", Font.BOLD, 14));
		jtabOnScreenList.getTableHeader().setForeground(Color.WHITE);
		jtabOnScreenList.getTableHeader().setReorderingAllowed(false);
		jtabOnScreenList.getTableHeader().setResizingAllowed(false);
		jtabOnScreenList.getTableHeader().setPreferredSize(new Dimension(100, 30));
//		jtabOnScreenList.getTableHeader().setOpaque(false);
//		jtabOnScreenList.setOpaque(false);

		jtabOnScreenList.setRowHeight(30);
		
		jtabOnScreenList.getColumnModel().getColumn(0).setPreferredWidth(60);
		jtabOnScreenList.getColumnModel().getColumn(1).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(2).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(3).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(4).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(5).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(6).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(7).setPreferredWidth(80);
		jtabOnScreenList.getColumnModel().getColumn(8).setPreferredWidth(80);
		
		JScrollPane jspOnScreenList = new JScrollPane(jtabOnScreenList);
		jspOnScreenList.setBounds(15, 60, 845, 235);
//		jspOnScreenList.setOpaque(false);
//		jspOnScreenList.getViewport().setOpaque(false);
		
		String[] bookColumnNames = {
				"���̵�", "���� ��ȣ", "���� ��", "�¼� ��ȣ", "�����Ͻ�"
			};
		
		dtmBookList = new DefaultTableModel(bookColumnNames, 15) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			} // isCellEditable
		};
		
		jtabBookList = new JTable(dtmBookList);
		jtabBookList.getTableHeader().setFont(new Font("�������", Font.BOLD, 14));
		jtabBookList.getTableHeader().setForeground(Color.WHITE);
		jtabBookList.getTableHeader().setReorderingAllowed(false);
		jtabBookList.getTableHeader().setResizingAllowed(false);
		jtabBookList.getTableHeader().setPreferredSize(new Dimension(100, 30));
		
		jtabBookList.setRowHeight(30);
		
		jtabBookList.getColumnModel().getColumn(0).setPreferredWidth(140);
		jtabBookList.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtabBookList.getColumnModel().getColumn(2).setPreferredWidth(140);
		jtabBookList.getColumnModel().getColumn(3).setPreferredWidth(140);
		jtabBookList.getColumnModel().getColumn(4).setPreferredWidth(140);
		
		JScrollPane jspBookList = new JScrollPane(jtabBookList);
		jspBookList.setBounds(15, 25, 845, 220);
		
		// jcb
		// temp data
		JLabel jlblMovieTitle = new JLabel("��ȭ��");
		jlblMovieTitle.setFont(new Font("�������", Font.BOLD, 16));
		jlblMovieTitle.setForeground(Color.WHITE);
		jlblMovieTitle.setBounds(110, 20, 80, 30);
		
		String[] tempName = {"�±ر� �ֳ�����", "�Ҹ��౸"};
		
		jcbMovieTitle = new JComboBox<String>(tempName);
		jcbMovieTitle.setBounds(160, 20, 150, 30);
		
		JLabel jlblPersonnel = new JLabel("�ο���");
		jlblPersonnel.setFont(new Font("�������", Font.BOLD, 16));
		jlblPersonnel.setForeground(Color.WHITE);
		jlblPersonnel.setBounds(515, 20, 80, 30);
		
		Integer[] arrPersonnel = {1, 2, 3, 4, 5};
		
		jcbPersonnel = new JComboBox<Integer>(arrPersonnel);
		jcbPersonnel.setBounds(565, 20, 100, 30);
		
		// jbt
		jbtSearch = new JButton("��ȸ");
		jbtSearch.setFont(new Font("�������", Font.BOLD, 16));
		jbtSearch.setFocusable(false);
		jbtSearch.setBorderPainted(false);
//		jbtSearch.setContentAreaFilled(false);
		jbtSearch.setBounds(315, 20, 80, 30);
		
		jbtBook = new JButton("����");
		jbtBook.setFont(new Font("�������", Font.BOLD, 16));
		jbtBook.setFocusable(false);
		jbtBook.setBorderPainted(false);
//		jbtBook.setContentAreaFilled(false);
		jbtBook.setBounds(670, 20, 80, 30);
		
		jbtClose = new JButton("�ݱ�");
		jbtClose.setFont(new Font("�������", Font.BOLD, 16));
		jbtClose.setFocusable(false);
		jbtClose.setBorderPainted(false);
//		jbtClose.setContentAreaFilled(false);
		jbtClose.setBounds(375, 630, 125, 40);
		
		
		// bg
		JLabel jlblBackground = new JLabel(new ImageIcon(
				"C:/dev/workspace/cinema_prj/src/kr/co/sist/cinema/admin/images/admin_bg(1000x800).png"));
		jlblBackground.setLayout(null);
		jlblBackground.setBounds(0, 0, 900, 700);
		
		// jp
		TitledBorder tbSiteBook = new TitledBorder(new LineBorder(Color.WHITE), "���� ����");
		tbSiteBook.setTitleColor(Color.WHITE);
		
		JPanel jpSiteBook = new JPanel();
		jpSiteBook.setLayout(null);
		jpSiteBook.setBorder(tbSiteBook);
		jpSiteBook.setOpaque(false);
		jpSiteBook.setBounds(10, 10, 875, 310);
		
		jpSiteBook.add(jlblMovieTitle);
		jpSiteBook.add(jcbMovieTitle);
		jpSiteBook.add(jbtSearch);
		jpSiteBook.add(jlblPersonnel);
		jpSiteBook.add(jcbPersonnel);
		jpSiteBook.add(jbtBook);
		jpSiteBook.add(jspOnScreenList);
		
		TitledBorder tbBookList = new TitledBorder(new LineBorder(Color.WHITE), "���� ��Ȳ");
		tbBookList.setTitleColor(Color.WHITE);
		
		JPanel jpBookList = new JPanel();
		jpBookList.setLayout(null);
		jpBookList.setBorder(tbBookList);
		jpBookList.setOpaque(false);
		jpBookList.setBounds(10, 350, 875, 260);
		
		jpBookList.add(jspBookList);
		
		JPanel jp = new JPanel();
		jp.setLayout(null);
		jp.setBackground(Color.LIGHT_GRAY);
		jp.setBounds(0, 0, 900, 700);
		
		jp.add(jpSiteBook);
		jp.add(jpBookList);
		jp.add(jbtClose);
		jp.add(jlblBackground);
		
		add(jp);
		
		// action
		SCABookManageController scabmc = new SCABookManageController(this);
		
		addWindowListener(scabmc);
		
		jbtSearch.addActionListener(scabmc);
		jbtBook.addActionListener(scabmc);
		jbtClose.addActionListener(scabmc);
		
		// size 900X700
		setSize(900, 720);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
	} // SCABookManageView

	public JComboBox<String> getJcbMovieTitle() {
		return jcbMovieTitle;
	}

	public JComboBox<Integer> getJcbPersonnel() {
		return jcbPersonnel;
	}

	public JButton getJbtSearch() {
		return jbtSearch;
	}

	public JButton getJbtBook() {
		return jbtBook;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public DefaultTableModel getDtmOnScreenList() {
		return dtmOnScreenList;
	}

	public DefaultTableModel getDtmBookList() {
		return dtmBookList;
	}

	public JTable getJtabOnScreenList() {
		return jtabOnScreenList;
	}

	public JTable getJtabBookList() {
		return jtabBookList;
	}
	
} // class
