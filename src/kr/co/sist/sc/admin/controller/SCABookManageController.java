package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.sc.admin.model.SCABookManageDAO;
import kr.co.sist.sc.admin.view.SCABookManageView;
import kr.co.sist.sc.admin.view.SCABookPremiumScreenView;
import kr.co.sist.sc.admin.view.SCABookStandardScreenView;
import kr.co.sist.sc.admin.vo.SCABookListVO;
import kr.co.sist.sc.admin.vo.SCABookMovieListVO;
import kr.co.sist.sc.admin.vo.SCABookOnScreenVO;

public class SCABookManageController extends WindowAdapter implements ActionListener {
	private SCABookManageView scabmv;
	private List<SCABookMovieListVO> movieList;
	
	public SCABookManageController(SCABookManageView scabmv) {
		this.scabmv = scabmv;
		
		searchMovieList();
		
		// 1) ��ȭ���� ���õ��� �ʾ��� ��� (���� 1ȸ ��ü ��ȸ ����)
		searchBookOnScreen("");
		searchBookList("");
		
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
			searchBookList(movieCode);
		} // end if
		
		if (ae.getSource() == scabmv.getJbtBook()) {
			showBookScreen();
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
			
//			Set<String> set = new HashSet<String>();
//			
//			for (int i = 0; i < list.size(); i++) {
//				set.add(list.get(i).getMovie_title());
//			} // end for
//			
//			Iterator<String> ita = set.iterator();
//			
//			while (ita.hasNext()) {
//				scabmv.getJcbMovieTitle().addItem(ita.next());	
//			} // end while
			
			for (int i = 0; i < movieList.size(); i++) {
				scabmv.getJcbMovieTitle().addItem(movieList.get(i).getMovie_title());
				
				// value check
				System.out.println((i + 1) + " / " + movieList.get(i).getMovie_code() + " / " + movieList.get(i).getMovie_title());
			} // end for
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
				rowData[5] = scabos_vo.getStart_time();
				rowData[6] = scabos_vo.getEnd_time();
				rowData[7] = new Integer(1);
				rowData[8] = scabos_vo.getSeat_count();
				
				dtmOnScreenList.addRow(rowData);
			} // end for
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
			
//			Set<SCABookListVO> set = new HashSet<SCABookListVO>();
//			
//			for (int i = 0; i < list.size(); i++) {
//				set.add(list.get(i).getMovie_title());
//			} // end for
//			
//			Iterator<String> ita = set.iterator();
//			
//			while (ita.hasNext()) {
//				scabmv.getJcbMovieTitle().addItem(ita.next());	
//			} // end while
			
			SCABookListVO scabl_vo = null;
			
			Object[] rowData = null;
			
			for (int i = 0; i < list.size(); i++) {
				scabl_vo = list.get(i);
				
				rowData = new Object[6];
				
				rowData[0] = new Integer(i + 1);
				rowData[1] = scabl_vo.getMember_id();
				rowData[2] = scabl_vo.getBook_number();
				rowData[3] = scabl_vo.getPersonnel();
				rowData[4] = scabl_vo.getSeat_num();
				rowData[5] = scabl_vo.getPayment_date();
				
				dtmBookList.addRow(rowData);
			} // end for
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scabmv, "���� ��Ȳ ��ȸ �� ������ �߻��߽��ϴ�.");
			sqle.printStackTrace();
		} // end catch
	} // searchBookList
	
	/**
	 * ���� ��ư Ŭ�� �� �󿵰��� ���� �ٸ� ��ũ���� �����ִ� �޼���
	 */
	public void showBookScreen() {
		// ������ ���̺��� �ش� row�� �󿵰� ������ �����ͼ� �ٸ� 
		SCABookOnScreenVO scabos_vo = null;
		
		String move = JOptionPane.showInputDialog(scabmv, "���Ĵٵ� : 1, �����̾� : 2");
		
		if (move.equals("1")) {
			new SCABookStandardScreenView(scabmv, scabos_vo);
		} // end if
		
		if (move.equals("2")) {
			new SCABookPremiumScreenView(scabmv);
		} // end if
	} // showBookScreen
	
} // class
