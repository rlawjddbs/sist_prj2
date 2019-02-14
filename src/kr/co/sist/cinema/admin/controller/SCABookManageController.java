package kr.co.sist.cinema.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kr.co.sist.cinema.admin.view.SCABookManageView;
import kr.co.sist.cinema.admin.view.SCABookPremiumScreenView;
import kr.co.sist.cinema.admin.view.SCABookStandardScreenView;
import kr.co.sist.cinema.admin.vo.SCABookOnScreenVO;

public class SCABookManageController extends WindowAdapter implements ActionListener {
	private SCABookManageView scabmv;
	
	public SCABookManageController(SCABookManageView scabmv) {
		this.scabmv = scabmv;
		
	} // SCABookManageController
	
	@Override
	public void windowClosing(WindowEvent we) {
		scabmv.dispose();
	} // windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == scabmv.getJbtSearch()) {
			
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
	 */
	private void searchMovieList() {
		
	} // searchMovieList
	
	/**
	 * ���� ����
	 * 1) ���� ���� �� ������ ��ü �� ��ȭ ������ ��ȸ
	 * 2) ��ȭ������ ��ȸ �� ������ �ش� �� ��ȭ ������ ��ȸ
	 */
	private void searchBookOnScreen() {
		
	} // searchBookOnScreen
	
	/**
	 * ���� ��Ȳ
	 * 1) ���� ���� �� ��ü ��ȭ�� ���� ������ ��ȸ
	 * 2) ��ȭ������ ��ȸ �� �ش� ��ȭ�� ���� ������ ��ȸ
	 */
	private void searchBookList() {
		
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
