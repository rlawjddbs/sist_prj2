package kr.co.sist.sc.admin.controller;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.sc.admin.model.SCASnackManageDAO;
import kr.co.sist.sc.admin.view.SCASnackManageView;
import kr.co.sist.sc.admin.view.SCASnackMenuAddView;
import kr.co.sist.sc.admin.view.SCASnackMenuRemoveView;
import kr.co.sist.sc.admin.view.SCASnackOrderAddView;
import kr.co.sist.sc.admin.vo.SCASnackMenuTableSelectVO;
import kr.co.sist.sc.admin.vo.SCASnackPaymentVO;

public class SCASnackManageController extends WindowAdapter implements ActionListener {

	private SCASnackManageView scasmv;

	public SCASnackManageController(SCASnackManageView scasmv) {
		this.scasmv = scasmv;
		selectSnackMenuTable();
	} // SCASnackManageController

	@Override
	public void actionPerformed(ActionEvent ae) {
		// ���� �޴�(�̹��� ��ư) Ŭ����
		if (ae.getSource() instanceof JButton) {
			String btnName = ((JButton) ae.getSource()).getText();
			if (btnName != "") {
				new SCASnackOrderAddView(scasmv, btnName, scasmv.getDtmOrderList());
			} // end if
		} // end if

		// ���� ��ư Ŭ����
		if (ae.getSource() == scasmv.getJbtSnackPayment()) {
			if (scasmv.getJtabOrderList().getRowCount() > 0) {
				progressPayment();
			} else {
				JOptionPane.showMessageDialog(scasmv, "�ֹ� ����� ����ֽ��ϴ�.");
				return;
			} // end else
		} // end if

		// �ֹ� ���� ��ư Ŭ����
		if (ae.getSource() == scasmv.getJbtSnackOrderDelete()) {
			int selectedRow = scasmv.getJtabOrderList().getSelectedRow();
			int[] selectedRows = scasmv.getJtabOrderList().getSelectedRows();

			if (selectedRow != -1) {
				String selectedSnackName = (String) scasmv.getJtabOrderList().getValueAt(selectedRow, 1);
				switch (JOptionPane.showConfirmDialog(scasmv, "[" + selectedSnackName + "]\n�ֹ� ��Ͽ��� �����Ͻðڽ��ϱ�?", "�ֹ� ����",
						JOptionPane.YES_NO_OPTION)) {
				case JOptionPane.OK_OPTION:
					for(int i = 0; i < selectedRows.length; i++) {
						scasmv.getDtmOrderList().removeRow(selectedRows[i]);
//						selectedRows = scasmv.getJtabOrderList().getSelectedRows();
					} // end for
					break;
				} // end switch
			} else {
				JOptionPane.showMessageDialog(scasmv, "���� ������ �ֹ��� �������ּ���.");
			} // end else
		} // end if

		// �޴� �߰� ��ư Ŭ����
		if (ae.getSource() == scasmv.getJbtSnackMenuInsert()) {
			
//			if(scasmv.getJbtSnackImg().length < 8) {
			try {
				if(SCASnackManageDAO.getInstance().selectSnackMenuTable().size() < 8) {
					showSnackMenuAdd();
				} else {
					JOptionPane.showMessageDialog(scasmv, "�� �̻� ���� �޴��� �߰��� �� �����ϴ�.");
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(scasmv, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
			} // end if
			
		} // end if

		// �޴� ���� ��ư Ŭ����
		if (ae.getSource() == scasmv.getJbtSnackMenuDelete()) {
			new SCASnackMenuRemoveView(scasmv);
		} // end if

		// �ݱ� ��ư Ŭ����
		if (ae.getSource() == scasmv.getJbtClose()) {
			scasmv.dispose();
		} // end if
	} // actionPerformed

	/**
	 * ���� ���� ��ư Ŭ���� JButton�� �迭�� snack ���̺��� �޴����� �߰��ϴ���
	 */
	private void selectSnackMenuTable() {
		JButton[][] jbtSnack = scasmv.getJbtSnackImg();
		List<SCASnackMenuTableSelectVO> snackList = new ArrayList<SCASnackMenuTableSelectVO>();
		try {
			snackList = SCASnackManageDAO.getInstance().selectSnackMenuTable();

			int listLength = 0;
			String imgDir = "C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/snack/";

			for (int i = 0; i < jbtSnack.length; i++) {
				for (int j = 0; j < jbtSnack[i].length; j++) {
					jbtSnack[i][j].setIcon(new ImageIcon("C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jl_no_snack_image(187x162).png"));
					jbtSnack[i][j].setText("");
					listLength++;
				} // end for
			} // end for

			listLength = 0;
			for (int k = 0; k < jbtSnack.length; k++) {
				for (int l = 0; l < jbtSnack[k].length; l++) {
					if (listLength == snackList.size()) {
						return;
					} // end if
					jbtSnack[k][l].setIcon(new ImageIcon(imgDir +"s_snack_"+ snackList.get(listLength).getSnackImg()));
					jbtSnack[k][l].setText(snackList.get(listLength).getSnackName());
					jbtSnack[k][l].setHorizontalTextPosition(JButton.CENTER);
					jbtSnack[k][l].setVerticalTextPosition(JButton.CENTER);
					jbtSnack[k][l].setForeground(new Color(255, 255, 255, 0));
					listLength++;
				} // end for
			} // end for

			listLength = 0;

		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scasmv, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
		} // end catch
	} // selectSnackMenuTable

	private void showSnackMenuAdd() {
		new SCASnackMenuAddView(scasmv, scasmv.getJbtSnackMenuInsert());
	} // showSnackMenuAdd

	private void progressPayment() {
		SCASnackPaymentVO scaspvo = null;
		List<SCASnackPaymentVO> list = new ArrayList<SCASnackPaymentVO>();
		DefaultTableModel dtmOrderlist = scasmv.getDtmOrderList();
		String snackName = "";
		int price = 0, quan = 0, itemPrice = 0, totalPrice = 0;

		StringBuilder receipt = new StringBuilder();
		
		
		for (int i = 0; i < dtmOrderlist.getRowCount(); i++) {
			snackName = String.valueOf(scasmv.getDtmOrderList().getValueAt(i, 1));
			price = Integer.parseInt(scasmv.getDtmOrderList().getValueAt(i, 2).toString());
			quan = Integer.parseInt(scasmv.getDtmOrderList().getValueAt(i, 3).toString());
			itemPrice = 0;
			itemPrice += price * quan;
			totalPrice += price * quan;
			receipt.append("[������] - ").append(snackName).append("\t")
			.append("[������] - ").append(quan).append("��\t")
			.append("[������] - ").append(itemPrice).append("��\t\n");
		} // end for
		receipt.append("-----------------------------------------------------------------------------------------------------------\n");
		receipt.append("[�Ѱ���] - ").append(totalPrice).append("��\n\n")
		.append("���� �Ͻðڽ��ϱ�?");
		
		JTextArea jtaReceipt = new JTextArea(30, 40);
		jtaReceipt.setText(receipt.toString());
		jtaReceipt.setEditable(false);
		
		switch(JOptionPane.showConfirmDialog(scasmv, jtaReceipt, "������", JOptionPane.YES_NO_OPTION)){
			case JOptionPane.OK_OPTION :
			try {
				for(int j=0; j < dtmOrderlist.getRowCount(); j++) {
					snackName = String.valueOf(scasmv.getDtmOrderList().getValueAt(j, 1));
					quan = Integer.parseInt(scasmv.getDtmOrderList().getValueAt(j, 3).toString());
					scaspvo = new SCASnackPaymentVO(snackName, quan);
					SCASnackManageDAO.getInstance().insertPayment(scaspvo);
				} // end for
			} catch (SQLException se) {
				se.printStackTrace();
			} // end catch
			JOptionPane.showMessageDialog(scasmv, "������ �Ϸ�Ǿ����ϴ�.");
			dtmOrderlist.setRowCount(0);
		} // end switch

	} // progressPayment

	@Override
	public void windowClosing(WindowEvent we) {
		scasmv.dispose();
	} // windowClosing


} // class
