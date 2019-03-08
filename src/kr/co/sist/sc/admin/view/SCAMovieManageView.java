package kr.co.sist.sc.admin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.sc.admin.controller.SCAMovieManageController;

@SuppressWarnings("serial")
public class SCAMovieManageView extends JDialog {

	private JButton regesterMovie, exit;
	private DefaultTableModel dtmModel;
	private JTable tableMovieList;
	private JScrollPane jspList;
	private JLabel backColor;
	private String admin_id;

	public SCAMovieManageView(SCAMainView scamv, String admin_id) {
		admin_id="hee"; // ������ iD 
		setTitle("������ :"+admin_id);
		  // ���̺� ���� ��� �����ϱ�
	      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
	      dtcr.setHorizontalAlignment(SwingConstants.CENTER);

	    Font setFont=new Font("�������", Font.BOLD, 20);
		JLabel movieManagement = new JLabel("��ȭ����");
		movieManagement.setBounds(20, 20, 125, 60);
//		movieManagement.setContentAreaFilled(false);
//		movieManagement.setBorderPainted(false);
		movieManagement.setOpaque(false); // ���� ���� �̿��� �κ��� �����ϰ�
		movieManagement.setFont(new Font("�������", Font.BOLD, 25));
		movieManagement.setForeground(Color.WHITE);
		String colum[] = { "����", "�ڵ�","������", "����" };
		dtmModel = new DefaultTableModel(colum, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
			

	/*		protected void paintComponent(Graphics g) {
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				super.dtmModel(g);
			}*/
		};
//		dtmModel.
//		tempColumSet = new JTable(dtmModel);
		tableMovieList = new JTable(dtmModel);
		tableMovieList.getTableHeader().setFont(new Font("�������", Font.BOLD, 22));
		tableMovieList.getTableHeader().setBorder(new LineBorder(Color.WHITE));
		tableMovieList.getTableHeader().setForeground(Color.white);
		tableMovieList.getTableHeader().setBackground(new Color(20,30,65));
		tableMovieList.getTableHeader().setResizingAllowed(false);
		tableMovieList.getTableHeader().setPreferredSize(new Dimension(100, 30));
		// ���̺��� ��ġ �̵� �Ұ�����
		tableMovieList.getTableHeader().setReorderingAllowed(false);
		tableMovieList.getTableHeader().setOpaque(false);
		tableMovieList.setBorder(new LineBorder(Color.WHITE));
//		tableMovieList.setGridColor(new Color(20,35,65));
//		tableMovieList.setShowVerticalLines(false);//������
		tableMovieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableMovieList.setSelectionForeground(Color.white);
		tableMovieList.setSelectionBackground(new Color(20,35,65));
		tableMovieList.setFocusable(false);
		tableMovieList.setOpaque(false);
//		�� �Ӽ����� ������ ����
		tableMovieList.getColumnModel().getColumn(0).setPreferredWidth(60); // ����
		tableMovieList.getColumnModel().getColumn(0).setCellRenderer(dtcr);
		tableMovieList.setFont(setFont);
		
		tableMovieList.getColumnModel().getColumn(1).setPreferredWidth(120); // ��ȭ �ڵ�
		tableMovieList.getColumnModel().getColumn(1).setCellRenderer(dtcr);
		tableMovieList.getColumnModel().getColumn(2).setPreferredWidth(80); // ������ �̹���
		tableMovieList.getColumnModel().getColumn(3).setPreferredWidth(150); // ��ȭ ����
		tableMovieList.getColumnModel().getColumn(3).setCellRenderer(dtcr);
		tableMovieList.setRowHeight(160);
		jspList = new JScrollPane(tableMovieList);
		jspList.setBounds(10, 100, 565, 450);
		jspList.setOpaque(false);

		//////////////////////////////////
		// ���
		regesterMovie = new JButton();
		regesterMovie.setIcon(
				new ImageIcon("C:/dev/Workspace/Cinema/src/kr/co/sist/sc/admin/images/jbt_resist(125x40).png"));
		regesterMovie.setBounds(150, 580, 125, 40);
		regesterMovie.setContentAreaFilled(false);
		regesterMovie.setBorderPainted(false);

		// �ݱ�
		exit = new JButton();
		exit.setIcon(new ImageIcon("C:/dev/Workspace/Cinema/src/kr/co/sist/sc/admin/images/jbt_close(125x40).png"));
		exit.setBounds(285, 580, 125, 40);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);

		////////////////////////////////////// ��ü ��� �� �߰�
		backColor = new JLabel();
		backColor.setIcon(new ImageIcon(
				"C:/dev/Workspace/Cinema/src/kr/co/sist/sc/admin/images/admin_movie_management_bg(600x700).png"));
		backColor.setBounds(0, 0, 600, 700);

		setLayout(null);
		TitledBorder tb=new TitledBorder("��ȭ���");
		tb.setTitleFont(setFont);
		tb.setTitleColor(Color.white);
		jspList.setBorder(tb);
//		jspList.setFont(new Font("�����ü�", Font.BOLD, 22));
		jspList.getVerticalScrollBar().setUI(new BasicScrollBarUI() { 
			 			@Override 
						protected void configureScrollBarColors() { 
			 				this.thumbColor = new Color(163, 184, 204); 
			 			} // configureScrollBarColors 
					}); 

		
		jspList.setForeground(Color.BLUE);
		jspList.setBackground(Color.BLUE);
		jspList.setOpaque(false);
		
		

		backColor.add(movieManagement);
		backColor.add(jspList);
		backColor.add(regesterMovie);
		backColor.add(exit);


		add(backColor);

		SCAMovieManageController scammc = new SCAMovieManageController(this);
		addWindowListener(scammc);

		////////// ����Ŭ��
		tableMovieList.addMouseListener(scammc);

		regesterMovie.addActionListener(scammc);
		exit.addActionListener(scammc);

		setSize(600, 720);
		setLocationRelativeTo(scamv);
		setResizable(false);
		setVisible(true);

	}

	public JButton getRegesterMovie() {
		return regesterMovie;
	}

	public JButton getExit() {
		return exit;
	}

	public DefaultTableModel getDtmModel() {
		return dtmModel;
	}

	public JTable getTableMovieList() {
		return tableMovieList;
	}

	public JScrollPane getJspList() {
		return jspList;
	}

	public JLabel getBackColor() {
		return backColor;
	}

	public String getAdmin_id() {
		return admin_id;
	}
	
}
