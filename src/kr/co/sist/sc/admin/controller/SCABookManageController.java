package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.sc.admin.model.SCABookManageDAO;
import kr.co.sist.sc.admin.nio.SCAFileHelper;
import kr.co.sist.sc.admin.view.SCABookManageView;
import kr.co.sist.sc.admin.view.SCABookPremiumScreenView;
import kr.co.sist.sc.admin.view.SCABookStandardScreenView;
import kr.co.sist.sc.admin.vo.SCABookListVO;
import kr.co.sist.sc.admin.vo.SCABookMovieListVO;
import kr.co.sist.sc.admin.vo.SCABookOnScreenVO;
import kr.co.sist.sc.admin.vo.SCABookScreenVO;

public class SCABookManageController extends WindowAdapter implements ActionListener {
	private SCABookManageView scabmv;
	private List<SCABookMovieListVO> movieList;
	
	public SCABookManageController(SCABookManageView scabmv) {
		this.scabmv = scabmv;
		
		searchMovieList();
		
		// 1) ��ȭ���� ���õ��� �ʾ��� ��� (���� 1ȸ ��ü ��ȸ ����)
		searchBookOnScreen("");
		
	} // SCABookManageController
	
	@Override
	public void windowClosing(WindowEvent we) {
		scabmv.dispose();
	} // windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == scabmv.getJbtSearch()) {
			String movieTitle = (String) scabmv.getJcbMovieTitle().getSelectedItem();
			String movieCode = "";
			
			for (int i = 0; i < movieList.size(); i++) {
				if (movieList.get(i).getMovie_title().equals(movieTitle)) {
					movieCode = movieList.get(i).getMovie_code();
				} // end if
			} // end for
			
			// 2) ��ȭ���� ���õ��� ��� (Ư�� ��ȭ�� ��ȸ �ø��� ����)
			searchBookOnScreen(movieCode);
		} // end if
		
		if (ae.getSource() == scabmv.getJbtBook()) {
			try {
				JTable jtabOnScreenList = scabmv.getJtabOnScreenList();
				
				int row = jtabOnScreenList.getSelectedRow();
				
				String[] colValue = new String[7];
				
				for (int i = 0; i < colValue.length; i++) {
					colValue[i] = String.valueOf(jtabOnScreenList.getValueAt(row, (i + 1)));
				} // end for
				
				String movieCode = colValue[0];
				String movieTitle = colValue[1];
				String screenNum = colValue[2];
				String screenName = colValue[3];
				String startTime = colValue[4];
				String endTime = colValue[5];
				String seat_remain = colValue[6];
				
				Calendar cal = Calendar.getInstance();
				
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				String screenDate = String.valueOf(
						year + "-" + month + "-" + day + "/" + startTime + "/" + endTime);
				
				String personnel = String.valueOf(scabmv.getJcbPersonnel().getSelectedItem());
				
				SCABookScreenVO scabs_vo = new SCABookScreenVO(
						movieCode, 
						screenDate, 
						screenName, 
						screenNum, 
						Integer.parseInt(personnel));
				
				if (JOptionPane.showConfirmDialog(scabmv, 
						"[��ȭ�� : " + movieTitle + "]\n" + 
						"[���ż� : " + personnel + "]\n" + 
						"[�󿵰� : " + screenName + "]\n" + 
						"[���Ͻ� : " + screenDate + "]\n" + 
						"�����Ͻ� ������ ���Ÿ� �����Ͻðڽ��ϱ�?", "��ȭ ����", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					if (Integer.parseInt(seat_remain) < Integer.parseInt(personnel)) {
						JOptionPane.showMessageDialog(scabmv, "�ܿ� �¼��� �����մϴ�!");
						resetBookScreen();
						return;
					} // end if
					
					showBookScreen(scabs_vo);
				} // end if
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				JOptionPane.showMessageDialog(scabmv, "�����Ͻ� ��ȭ�� �������ּ���!");
				aioobe.printStackTrace();
			} // end catch
		} // end if
		
		if (ae.getSource() == scabmv.getJbtClose()) {
			scabmv.dispose();
		} // end if
	} // actionPerformed
	
	/**
	 * ��ȭ���� ��ȸ�ϴ� �޼���
	 * �ش� ��ȭ������ ��ȸ �� ���� ���� ���̺� 
	 * ���Ͽ� �� ���� ��ȭ ������ �����ش�.
	 */
	private void searchMovieList() {
		try {
			movieList = SCABookManageDAO.getInstance().selectMovieList();
			
			for (int i = 0; i < movieList.size(); i++) {
				scabmv.getJcbMovieTitle().addItem(movieList.get(i).getMovie_title());
			} // end for
			
			// test source
			SCAFileHelper.getInstance().addEvent("movie");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} // end catch
	} // searchMovieList
	
	/**
	 * ���� ����
	 * 1) ���� ���� �� ������ ��ü �� ��ȭ ������ ��ȸ
	 * 2) ��ȭ������ ��ȸ �� ������ �ش� �� ��ȭ ������ ��ȸ
	 */
	private void searchBookOnScreen(String movieCode) {
		DefaultTableModel dtmOnScreenList = scabmv.getDtmOnScreenList();
		
		dtmOnScreenList.setRowCount(0);
		
		try {
			List<SCABookOnScreenVO> list = SCABookManageDAO.getInstance().selectBookOnScreen(movieCode);
			
			SCABookOnScreenVO scabos_vo = null;
			
			Object[] rowData = null;
			
			for (int i = 0; i < list.size(); i++) {
				scabos_vo = list.get(i);
				
				rowData = new Object[9];
				
				rowData[0] = new Integer(i + 1);
				rowData[1] = scabos_vo.getMovie_code();
				rowData[2] = scabos_vo.getMovie_title();
				rowData[3] = scabos_vo.getScreen_num();
				rowData[4] = scabos_vo.getScreen_name();
				rowData[5] = scabos_vo.getStart_time().substring(0, 2) + ":" + scabos_vo.getStart_time().substring(2);
				rowData[6] = scabos_vo.getEnd_time().substring(0, 2) + ":" + scabos_vo.getEnd_time().substring(2);
				rowData[7] = scabos_vo.getSeat_remain();
				rowData[8] = scabos_vo.getSeat_count();
				
				dtmOnScreenList.addRow(rowData);
			} // end for
			
			searchBookList(movieCode);
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scabmv, "�� ���� ��ȭ ��ȸ �� ������ �߻��߽��ϴ�.");
			sqle.printStackTrace();
		} // end catch
	} // searchBookOnScreen
	
	/**
	 * ���� ��Ȳ
	 * 1) ���� ���� �� ��ü ��ȭ�� ���� ������ ��ȸ
	 * 2) ��ȭ������ ��ȸ �� �ش� ��ȭ�� ���� ������ ��ȸ
	 */
	private void searchBookList(String movieCode) {
		DefaultTableModel dtmBookList = scabmv.getDtmBookList();
		
		dtmBookList.setRowCount(0);
		
		try {
			List<SCABookListVO> list = SCABookManageDAO.getInstance().selectBookList(movieCode);
			
			SCABookListVO scabl_vo = null;
			Object[] rowData = null;
			
			List<Integer> seatNum = null;
			int num = 0;
			
			for (int i = 0; i < list.size(); i++) {
				scabl_vo = list.get(i);
				
				rowData = new Object[6];
				
				rowData[0] = new Integer(num++ + 1);
				rowData[1] = scabl_vo.getMember_id();
				rowData[2] = scabl_vo.getBook_number();
				rowData[3] = scabl_vo.getPersonnel();
				rowData[5] = scabl_vo.getPayment_date();
				
				// �ο���
				int personnel = scabl_vo.getPersonnel();
				int cnt = 0;
				
				seatNum = new ArrayList<Integer>();
				
				for (int j = i; j < i + personnel; j++) {
					if (cnt < personnel) {
						seatNum.add(list.get(j).getSeat_num());
						
						cnt++;
					} // end if
				} // end for
				
				i += cnt - 1;
				
				rowData[4] = seatNum.toString();
				
				dtmBookList.addRow(rowData);
			} // end for
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scabmv, "���� ��Ȳ ��ȸ �� ������ �߻��߽��ϴ�.");
			sqle.printStackTrace();
		} // end catch
	} // searchBookList
	
	/**
	 * ���� ���� �ʱ�ȭ
	 */
	private void resetBookScreen() {
		String item = (String) scabmv.getJcbMovieTitle().getSelectedItem();
		String code = "";
		
		for (int i = 0; i < movieList.size(); i++) {
			if (item.equals(movieList.get(i).getMovie_title())) {
				code = movieList.get(i).getMovie_code();
			} // end if
		} // end for
		
		scabmv.getJcbMovieTitle().setSelectedIndex(scabmv.getJcbMovieTitle().getSelectedIndex());
		scabmv.getJcbPersonnel().setSelectedIndex(0);
		
		searchBookOnScreen(code);
	} // resetBookScreen
	
	/**
	 * ���� ��ư Ŭ�� �� �󿵰��� ���� �ٸ� ��ũ���� �����ִ� �޼���
	 */
	public void showBookScreen(SCABookScreenVO scabs_vo) {
		// ������ ���̺��� �ش� row�� �󿵰� ������ �����ͼ� �ٸ� 
		
		String screenName = scabs_vo.getScreen_num().substring(0, 1);
		
		if (screenName.equals("N")) {
			new SCABookStandardScreenView(scabmv, scabs_vo);
		} // end if
		
		if (screenName.equals("P")) {
			new SCABookPremiumScreenView(scabmv, scabs_vo);
		} // end if
	} // showBookScreen
	
} // class
