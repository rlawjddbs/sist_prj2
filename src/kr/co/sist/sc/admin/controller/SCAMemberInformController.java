package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCAMemberManageDAO;
import kr.co.sist.sc.admin.view.SCAMemberInformView;
import kr.co.sist.sc.admin.view.SCAMemberManageView;
import kr.co.sist.sc.admin.vo.SCAMemberUpdateVO;

public class SCAMemberInformController extends WindowAdapter implements ActionListener, MouseListener {

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
		if(phone.equals("")) {
			JOptionPane.showMessageDialog(scamiv, "������ �޴��� ��ȣ�� �Է��ϼ���.");
			scamiv.getJtfPhone().requestFocus();
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
		if(phoneDiv.length != 3 || phoneDiv[0].length() != 3 || phoneDiv[1].length() != 4 || phoneDiv[2].length() != 4) {
			JOptionPane.showMessageDialog(scamiv, "�޴��� ��ȣ�� �ڸ����� Ȯ�����ּ���.");
			scamiv.getJtfPhone().requestFocus();
			return;
		} // end if
		
		
		////////////////////////// 19-02-19 ������ - ��Ÿ �޴��� ��ȣ ��ȿ�� ���� �ڵ� �߰� �ؾ��� ///////////////////////
		
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
					new SCAMemberController(scammv);
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
		
		switch(JOptionPane.showConfirmDialog(scamiv, removeMsg)) {
		case JOptionPane.OK_OPTION:
			try {
				if(SCAMemberManageDAO.getInstance().deleteMember(memberId)) {
					JOptionPane.showMessageDialog(scamiv, "["+memberId+"]���� ȸ�� Ż�� ó�� �Ͽ����ϴ�.");
				} else {
					JOptionPane.showMessageDialog(scamiv, "["+memberId+"]���� Ż��ó������ ���Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
				} // end else
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(scamiv, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
				return;
			} // end catch
			break;
		} // end switch	
	} // removeMember
	
	@Override
	public void windowClosing(WindowEvent we) {
		scamiv.dispose();
	} // windowClosing
	
	@Override
	public void mousePressed(MouseEvent me) {
		if(me.getSource() == scamiv.getJbtClose()) {
			scamiv.getJbtClose().setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbt_close_pressed(100x30).png"));
		} // end if
	} // mousePressed

	@Override
	public void mouseReleased(MouseEvent me) {
		if(me.getSource() == scamiv.getJbtClose()) {
			scamiv.getJbtClose().setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/jbt_close(100x30).png"));
		} // end if
	} // mouseReleased


	
	///////////////////// not used ////////////////////
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
} // class
