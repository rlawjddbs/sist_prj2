package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCASnackManageDAO;
import kr.co.sist.sc.admin.view.SCASnackManageView;
import kr.co.sist.sc.admin.view.SCASnackMenuRemoveView;
import kr.co.sist.sc.admin.vo.SCASnackMenuTableSelectVO;

public class SCASnackMenuRemoveController extends WindowAdapter implements ActionListener {

	private SCASnackMenuRemoveView scasmrv;
	private SCASnackManageView scasmv;
	
	public SCASnackMenuRemoveController(SCASnackManageView scasmv, SCASnackMenuRemoveView scasmrv) {
		this.scasmrv = scasmrv;
		this.scasmv = scasmv;
		selectSnackNameTable();
	} // SCASnackMenuRemoveController

	@Override
	public void actionPerformed(ActionEvent ae) {
		// ����
		if (ae.getSource() == scasmrv.getJbtSnackDelete()) {
			String selectedSnack = scasmrv.getJlstSnackName().getSelectedValue();
			
			if(selectedSnack == null) {
				JOptionPane.showMessageDialog(scasmrv, "���� ������ �������� �����ؾ� �մϴ�.");
				return;
			} // end if
			
			try {
				if(SCASnackManageDAO.getInstance().deleteSnackMenu(selectedSnack)) {
					StringBuilder msg = new StringBuilder();
					msg.append("["+selectedSnack+"]\n").append("������Ͽ��� �����Ǿ����ϴ�.");
					JOptionPane.showMessageDialog(scasmrv, msg);
					selectSnackNameTable();
					refreshSnackMenu();
					scasmrv.dispose();
				} else {
					JOptionPane.showMessageDialog(scasmrv, "���ö��� �������� �ʾҽ��ϴ�.");
				} // end if
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				JOptionPane.showMessageDialog(scasmrv, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
			} // end catch
		} // end if
		
		// �ݱ�
		if (ae.getSource() == scasmrv.getJbtClose()) {
			scasmrv.dispose();
		} // end if
		
	} // actionPerformed

	private void selectSnackNameTable() {
		List<SCASnackMenuTableSelectVO> snackNameList = new ArrayList<SCASnackMenuTableSelectVO>();
		DefaultListModel<String> dlmSnackName = scasmrv.getDlmSnackName();
		try {
			snackNameList = SCASnackManageDAO.getInstance().selectSnackMenuTable();
			dlmSnackName.removeAllElements();
			for(int i = 0; i < snackNameList.size(); i++) {
				dlmSnackName.addElement(snackNameList.get(i).getSnackName());
			} // end for
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} // end catch
		
	} // selectSnackNameTable
	
	private void refreshSnackMenu() {
		JButton[][] jbtSnack = scasmv.getJbtSnackImg();
		List<SCASnackMenuTableSelectVO> snackList = new ArrayList<SCASnackMenuTableSelectVO>();
		try {
			snackList = SCASnackManageDAO.getInstance().selectSnackMenuTable();
			
			
			int listLength = 0;
			String imgDir = "C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/";
			
			for(int i=0; i < jbtSnack.length; i++) {
				for(int j=0; j < jbtSnack[i].length; j++) {
					jbtSnack[i][j].setIcon(null);
					jbtSnack[i][j].setText("");
					listLength++;
				} // end for
			} // end for
			
			listLength = 0;
			for(int k=0; k < jbtSnack.length; k++) {
				for(int l=0; l < jbtSnack[k].length; l++) {
					if(listLength == snackList.size()) {
						return;
					} // end if
					jbtSnack[k][l].setIcon(new ImageIcon(imgDir+snackList.get(listLength).getSnackImg()));
					jbtSnack[k][l].setText(snackList.get(listLength).getSnackName());
					listLength++;
				} // end for
			} // end for
			
			listLength = 0;
			
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scasmrv, "DB���� ������ �߻��Ͽ����ϴ�. �������� â�� �ݰ� �ٽ� ���ּ���.");
		} // end catch
	} // refreshSnackButton
	
} // class
