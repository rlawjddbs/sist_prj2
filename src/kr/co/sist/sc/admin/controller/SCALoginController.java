package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.sc.admin.model.SCALoginDAO;
import kr.co.sist.sc.admin.nio.SCAFileHelper;
import kr.co.sist.sc.admin.nio.SCAFileServer;
import kr.co.sist.sc.admin.view.SCALoginView;
import kr.co.sist.sc.admin.view.SCAMainView;
import kr.co.sist.sc.admin.vo.SCALoginCheckVO;
import kr.co.sist.sc.admin.vo.SCALoginVO;

public class SCALoginController extends WindowAdapter implements ActionListener {
	private SCALoginView scalv;
	
	public SCALoginController(SCALoginView scalv) {
		this.scalv = scalv;
		
	} // SCALoginController
	
	@Override
	public void windowClosing(WindowEvent we) {
		scalv.dispose();
	} // windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		JPasswordField jpfPasswd = scalv.getJpfPasswd();
		
		if (ae.getSource() == scalv.getJtfId()) {
			jpfPasswd.requestFocus();
		} // end if
		
		if (ae.getSource() == scalv.getJpfPasswd() || ae.getSource() == scalv.getJbtLogin()) {
			loginCheck();
		} // end if
	} // actionPerformed
	
	private void loginCheck() {
		JTextField jtfId = scalv.getJtfId();
		JPasswordField jpfPasswd = scalv.getJpfPasswd();
		
		String id = jtfId.getText().trim();
		String passwd = new String(jpfPasswd.getPassword()).trim();
		
		if (id.equals("")) {
			JOptionPane.showMessageDialog(scalv, "���̵� �Է����ּ���.");
			jpfPasswd.setText("");
			jtfId.setText("");
			jtfId.requestFocus();
			return;
		} // end if
		
		if (passwd.equals("")) {
			JOptionPane.showMessageDialog(scalv, "��й�ȣ�� �Է����ּ���.");
			jpfPasswd.setText("");
			jpfPasswd.requestFocus();
			return;
		} // end if
		
		if (!id.equals("") && !passwd.equals("")) {
			try {
				SCALoginCheckVO scalc_vo = new SCALoginCheckVO(id, passwd);
				
				SCALoginVO scal_vo = 
						SCALoginDAO.getInstance().loginAdmin(scalc_vo);
				
				String name = scal_vo.getName();
				
				if (!name.equals("")) {
					// ���� ���� ���� �� �ߺ� ���� ���� �Ǵ�
					SCAFileHelper.getInstance().openServer();
					
					// Thread start
					SCAFileHelper.getInstance().start();
					
					JOptionPane.showMessageDialog(scalv, name + "��, ���ӿ� �����߽��ϴ�.");
					
					// MainView
					new SCAMainView(scal_vo);
					
					scalv.dispose();
				} // end if
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(scalv, "���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���.");
				jpfPasswd.setText("");
				jtfId.setText("");
				jtfId.requestFocus();
				npe.printStackTrace();
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(scalv, "�α��� �� ������ �߻��߽��ϴ�.");
				sqle.printStackTrace();
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(scalv, "���������� ������ �����Ǿ� ���α׷��� �����մϴ�.");
				ioe.printStackTrace();
				scalv.dispose();
			} // end catch
		} // end if
	} // loginCheck
	
} // class
