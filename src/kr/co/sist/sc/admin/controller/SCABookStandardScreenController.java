package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCABookManageDAO;
import kr.co.sist.sc.admin.view.SCABookStandardScreenView;
import kr.co.sist.sc.admin.vo.SCABookScreenVO;

public class SCABookStandardScreenController extends WindowAdapter implements ActionListener {
	private SCABookStandardScreenView scabssv;
	private SCABookScreenVO scabs_vo;
	private boolean[][] seatFlag;
	
	public SCABookStandardScreenController(SCABookStandardScreenView scabssv, SCABookScreenVO scabs_vo) {
		this.scabssv = scabssv;
		this.scabs_vo = scabs_vo;
		
		seatFlag = new boolean[4][5];
		
		for (int i = 0; i < seatFlag.length; i++) {
			for (int j = 0; j < seatFlag[0].length; j++) {
				seatFlag[i][j] = false;
			} // end for
		} // end for
		
		searchBookSeat();
		
	} // SCABookStandardScreenController
	
	@Override
	public void windowClosing(WindowEvent we) {
		scabssv.dispose();
	} // windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		JButton[][] jbtSeat = scabssv.getJbtSeat();
		
		for (int i = 0; i < jbtSeat.length; i++) {
			for (int j = 0; j < jbtSeat[0].length; j++) {
				if (ae.getSource() == jbtSeat[i][j]) {
					checkSeat(jbtSeat[i][j], i, j);
				} // end if
			} // end for
		} // end for
		
		if (ae.getSource() == scabssv.getJbtSelect()) {
			List<Integer> selectedSeat = new ArrayList<Integer>();
			
			int personnel = scabs_vo.getPersonnel();
			
			for (int i = 0; i < seatFlag.length; i++) {
				for (int j = 0; j < seatFlag[0].length; j++) {
					if (seatFlag[i][j]) {
						selectedSeat.add(Integer.parseInt(jbtSeat[i][j].getText()));
					} // end if
				} // end for
			} // end for
			
			String msg = "";
			
			if (JOptionPane.showConfirmDialog(scabssv, 
					msg = (selectedSeat.size() == 0) ? "�¼��� ���� �������ֽðھ��?" : selectedSeat.toString() + "�� �¼����� �߱��Ͻðڽ��ϱ�?", 
					"��ȭ ����", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				if (msg.substring(0, 1).equals("��")) {
					return;
				} // end if
				
				if (selectedSeat.size() < personnel) {
					JOptionPane.showMessageDialog(scabssv, "������ �¼��� (" + (personnel - selectedSeat.size()) + "��)�� �����մϴ�!");
					return;
				} // end if
				
				addBook(selectedSeat);
			} // end if
		} // end if
		
		if (ae.getSource() == scabssv.getJbtClose()) {
			scabssv.dispose();
		} // end if
	} // actionPerformed
	
	private void checkSeat(JButton jbtSeat, int i, int j) {
		String seat_num = jbtSeat.getText();
		
		int personnel = scabs_vo.getPersonnel();
		int selCnt = 0;
		
		if (!seatFlag[i][j]) {
			if (JOptionPane.showConfirmDialog(scabssv, seat_num + "�� �¼��� �����Ͻðڽ��ϱ�?", 
					"�¼� ����", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				for (int n = 0; n < seatFlag.length; n++) {
					for (int m = 0; m < seatFlag[0].length; m++) {
						if (seatFlag[n][m]) {
							selCnt++;
						} // end if
					} // end for
				} // end for
				
				if ((selCnt + 1) > personnel) {
					JOptionPane.showMessageDialog(scabssv, "���� ������ ���� �¼��� �ʰ��߽��ϴ�.");
					return;
				} // end if
				
				jbtSeat.setIcon(new ImageIcon(
						"C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jbt_s_seat_selected(67x61).png"));
				seatFlag[i][j] = true;
				return;
			} // end if
		} // end if
		
		if (seatFlag[i][j]) {
			if (JOptionPane.showConfirmDialog(scabssv, seat_num + "�� �¼��� ����Ͻðڽ��ϱ�?", 
					"�¼� ���", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				jbtSeat.setIcon(new ImageIcon(
						"C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jbt_s_seat_selectable(67x61).png"));
				seatFlag[i][j] = false;
			} // end if
		} // end if
	} // checkSeat
	
	/**
	 * ����� ���Ĵٵ� �¼��� ��ȸ�Ѵ�.
	 */
	private void searchBookSeat() {
		try {
			String screenNum = scabs_vo.getScreen_num();
			
			List<Integer> list = SCABookManageDAO.getInstance().selectBookSeat(screenNum);
			
			JButton[][] jbtSeat = scabssv.getJbtSeat();
			
			for (int i = 0; i < jbtSeat.length; i++) {
				for (int j = 0; j < jbtSeat[0].length; j++) {
					for (Integer num : list) {
						if (jbtSeat[i][j].getText().equals(String.valueOf(num))) {
							jbtSeat[i][j].setEnabled(false);
							jbtSeat[i][j].setDisabledIcon(new ImageIcon(
									"C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/jbt_s_seat_unselectable(67x61).png"));
							jbtSeat[i][j].setText("<html><font color=rgb(0, 134, 255)>" + jbtSeat[i][j].getText() + "</font></html>");
						} // end if
					} // end for
				} // end for
			} // end for
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scabssv, "���Ĵٵ� �¼� ��ȸ �� ������ �߻��߽��ϴ�.");
			sqle.printStackTrace();
		} // end catch
	} // searchBookSeat
	
	/**
	 * ���Ĵٵ� �¼� ����
	 * @param selectedSeat
	 */
	private void addBook(List<Integer> selectedSeat) {
		try {
			boolean flag = SCABookManageDAO.getInstance().insertBookTransfer(scabs_vo, selectedSeat);
			
			if (flag) {
				JOptionPane.showMessageDialog(scabssv, selectedSeat.toString() + "�� �¼����� �߱��� �Ϸ�Ǿ����ϴ�.");
				SCABookManageController.resetBookScreen();
				scabssv.dispose();
			} // end if
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scabssv, "���� �� ������ �߻��߽��ϴ�.");
			sqle.printStackTrace();
		} // end catch
	} // addBook
	
} // class
