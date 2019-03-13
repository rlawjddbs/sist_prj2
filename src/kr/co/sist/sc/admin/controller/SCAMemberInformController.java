package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCAMemberManageDAO;
import kr.co.sist.sc.admin.view.SCAMemberInformView;
import kr.co.sist.sc.admin.view.SCAMemberManageView;
import kr.co.sist.sc.admin.vo.SCAMemberSelectVO;
import kr.co.sist.sc.admin.vo.SCAMemberUpdateVO;

public class SCAMemberInformController extends WindowAdapter implements ActionListener {

	private SCAMemberManageView scammv;
	private SCAMemberInformView scamiv;
	
	public SCAMemberInformController(SCAMemberInformView scamiv, SCAMemberManageView scammv) {
		super();
		this.scamiv = scamiv;
		this.scammv = scammv;
	} // SCAMemberInformController
	
	// �׼� �̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == scamiv.getJbtMemberUpdate()) {
			modifyMember();
		} // end if
		
		if(ae.getSource() == scamiv.getJbtMemberDelete()) {
			removeMember();
		} // end if
		
		if(ae.getSource() == scamiv.getJbtClose()) {
			scamiv.dispose();
		} // end if
	} // actionPerformed

	// ���� ����
	private void modifyMember() {
		String memberId = scamiv.getJtfMemberId().getText();
		String name = scamiv.getJtfName().getText();
		String phone = scamiv.getJtfPhone().getText();
		String[] phoneDiv = scamiv.getJtfPhone().getText().split("-");
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(scamiv, "������ �̸��� �Է��ϼ���.");
			scamiv.getJtfName().requestFocus();
			return;
		} // end if
		if(name.contains(" ")) {
			JOptionPane.showMessageDialog(scamiv, "�̸��� ������ �� �� �����ϴ�.");
			scamiv.getJtfName().requestFocus();
			return;
		} // end if
		if(phone.equals("")) {
			JOptionPane.showMessageDialog(scamiv, "������ �޴��� ��ȣ�� �Է��ϼ���.");
			scamiv.getJtfPhone().requestFocus();
			return;
		} // end if
		if(phone.contains(" ")) {
			JOptionPane.showMessageDialog(scamiv, "�޴�����ȣ�� ������ �� �� �����ϴ�.");
			scamiv.getJtfName().requestFocus();
			return;
		} // end if
		for(int i=0; i < phoneDiv.length; i++) {
			try {
				Integer.parseInt(phoneDiv[i]);
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(scamiv, "�޴��� ��ȣ�� ���ڿ� \"-\"�θ� �Է����ּ���.");
				scamiv.getJtfPhone().requestFocus();
				return;
			} // 
		} // end for
		
		if(!phoneDiv[0].equals("010") && !phoneDiv[0].equals("011") && !phoneDiv[0].equals("016") && !phoneDiv[0].equals("017") && !phoneDiv[0].equals("019")) {
			JOptionPane.showMessageDialog(scamiv, "�޴��� ���ڸ��� Ȯ�����ּ���.");
			scamiv.getJtfPhone().requestFocus();
			return;
		} // end if
		
		if(phoneDiv.length != 3 || phoneDiv[0].length() != 3 || phoneDiv[1].length() != 4 || phoneDiv[2].length() != 4) {
			JOptionPane.showMessageDialog(scamiv, "�޴��� ��ȣ�� �ڸ����� Ȯ�����ּ���.");
			scamiv.getJtfPhone().requestFocus();
			return;
		} // end if
		
		// confirm dialog �� �� �޽���
		StringBuilder modifyMsg = new StringBuilder();
		modifyMsg.append("�̸� : [").append(name).append("]\n")
					.append("�޴��� ��ȣ : [").append(phone).append("]\n")
					.append("ȸ�� ������ ���� �Ͻðڽ��ϱ�?");
		
		// switch ��
		switch(JOptionPane.showConfirmDialog(scamiv, modifyMsg, "ȸ�� ���� ����", JOptionPane.YES_NO_OPTION)) {
		case JOptionPane.OK_OPTION:
			SCAMemberUpdateVO scamuvo = new SCAMemberUpdateVO(memberId, name, phone);
			// try ��
			try {
				if(SCAMemberManageDAO.getInstance().updateMember(scamuvo)) {
					JOptionPane.showMessageDialog(scamiv, "ȸ�������� �����Ǿ����ϴ�.");
					scamiv.dispose();
					refreshAllMember();
				} else {
					JOptionPane.showMessageDialog(scamiv, "���� ������ �����Ͽ����ϴ�.");
				} // end else
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(scamiv, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
			} // end catch
		
			break;
		} // end switch
		
	} // modifyMember
	
	private void removeMember() {
		String memberId = scamiv.getJtfMemberId().getText();
		StringBuilder removeMsg = new StringBuilder();
		removeMsg.append("[").append(memberId).append("]���� ȸ�� Ż�� ó�� �Ͻðڽ��ϱ�?");
		
		switch(JOptionPane.showConfirmDialog(scamiv, removeMsg, "Ż�� Ȯ��", JOptionPane.YES_NO_OPTION)) {
		case JOptionPane.OK_OPTION:
			try {
				if(SCAMemberManageDAO.getInstance().deleteMember(memberId)) {
					JOptionPane.showMessageDialog(scamiv, "["+memberId+"]���� ȸ�� Ż�� ó�� �Ͽ����ϴ�.");
					refreshAllMember();
					scamiv.dispose();
				} else {
					JOptionPane.showMessageDialog(scamiv, "["+memberId+"]���� Ż��ó������ ���Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
				} // end else
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(scamiv, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
				sqle.printStackTrace();
				return;
			} // end catch
			break;
		} // end switch	
	} // removeMember
	
	private void refreshAllMember() {
		try {
			List<SCAMemberSelectVO> list = SCAMemberManageDAO.getInstance().selectAllMember();

			Object[] rowData = new Object[3];
			SCAMemberSelectVO scamsvo = null;

			scammv.getDtmMemberList().setRowCount(0);
			for (int i = 0; i < list.size(); i++) {
				scamsvo = list.get(i);
				rowData[0] = scamsvo.getMember_id();
				rowData[1] = scamsvo.getName();
				rowData[2] = scamsvo.getBirthDate();
				
				scammv.getDtmMemberList().addRow(rowData);
			} // end for
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	} // refreshAllMember
	
	@Override
	public void windowClosing(WindowEvent we) {
		scamiv.dispose();
	} // windowClosing
	
} // class
