package kr.co.sist.sc.admin.controller;

import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCASnackManageDAO;
import kr.co.sist.sc.admin.view.SCASnackManageView;
import kr.co.sist.sc.admin.view.SCASnackMenuAddView;
import kr.co.sist.sc.admin.vo.SCASnackMenuAddVO;
import kr.co.sist.sc.admin.vo.SCASnackMenuTableSelectVO;


public class SCASnackMenuAddController extends WindowAdapter implements ActionListener {
	
	private SCASnackManageView scasmv;
	private SCASnackMenuAddView scasmav;
	

	public SCASnackMenuAddController(SCASnackManageView scasmv, SCASnackMenuAddView scasmav) {
		this.scasmv = scasmv;
		this.scasmav = scasmav;
	} // SCASnackMenuAddController

	@Override
	public void actionPerformed(ActionEvent ae) {
		// �̹��� ��� ��ư
		if(ae.getSource() == scasmav.getJbtSnackImg()) {
			addSnackImg();
		} // end if
		
		// �޴� ��� ��ư
		if(ae.getSource() == scasmav.getJbtSnackInsert()) {
			addSnackMenu();
		} // end if
		
		// �ݱ� ��ư
		if(ae.getSource() == scasmav.getJbtClose()) {
			scasmav.dispose();
		} // end if
	} // actionPerformed
	
	private void addSnackImg() {
		FileDialog fdOpenImg = new FileDialog(scasmav, "���� �̹��� ���", FileDialog.LOAD);
		fdOpenImg.setVisible(true);
		
		String path = fdOpenImg.getDirectory();
		String name = fdOpenImg.getFile();
		
		boolean flag = false;
		if (path != null) {
			String imgsFormat = "png";
			
			if(name.toLowerCase().endsWith(imgsFormat)){
				flag = true;
			} // end if
			
			if(flag) {
				String uploadImg = path + name;
				scasmav.getJlSnackImg().setIcon(new ImageIcon(uploadImg));
			} else {
				JOptionPane.showMessageDialog(scasmav, "��ϰ����� �̹��� ������ png �Դϴ�.");
			} // end else
		} // end if
		
	} // addSnackImg
	
	private void addSnackMenu() {
		File imageFile = new File(scasmav.getJlSnackImg().getIcon().toString());
		String snackName = scasmav.getJtfSnackName().getText();
		String snackPrice = scasmav.getJtfPrice().getText();
		String snackInfo = scasmav.getjtaSnackInfo().getText();
		
		if(imageFile.getName().endsWith("admin_snack_default_img(325x325).png")) {
			JOptionPane.showMessageDialog(scasmav, "�̹����� ������ּ���.");
			return;
		} // end if
		
		if(snackName.equals("")) {
			JOptionPane.showMessageDialog(scasmav, "�������� �ʼ��� �Է����ּ���.");
			scasmav.getJtfSnackName().requestFocus();
			return;
		} // end if
		
		if(snackPrice.equals("")) {
			JOptionPane.showMessageDialog(scasmav, "������ ������ �ʼ��� �Է����ּ���.");
			scasmav.getJtfPrice().requestFocus();
			return;
		} // end if
		
		if(snackPrice.length() > 5) {
			JOptionPane.showMessageDialog(scasmav, "������ ������ �ִ� 5�ڷ� �Է����ּ���.");
			scasmav.getJtfPrice().requestFocus();
			return;
		} // end if
		
		int price = 0;
		try {
			price = Integer.parseInt(snackPrice);
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(scasmav, "������ ������ ���ڸ� �Է����ּ���.");
			scasmav.getJtfPrice().setText("");
			scasmav.getJtfPrice().requestFocus();
			return;
		} // end catch
		
		if(snackInfo.equals("")) {
			JOptionPane.showMessageDialog(scasmav, "������ Ư������ �ʼ��� �Է����ּ���.");
			scasmav.getJtfPrice().requestFocus();
			return;
		} // end if
		
		try {
			// ������ ��ϵ� ���� �޴��� 8�� �̸�����?
			if(SCASnackManageDAO.getInstance().selectSnackMenuTable().size() != 8) {
				SCASnackMenuAddVO scasmavo = new SCASnackMenuAddVO(snackName, imageFile.getName(), snackInfo, price);
			
				try {
					// DB�� ���ο� �����޴��� �߰�
					SCASnackManageDAO.getInstance().insertSnackMenu(scasmavo);
					// �̹����� ���� ������ ���ε�
					uploadSnackImg(imageFile);
					refreshSnackMenu();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} // end catch
				
				JOptionPane.showMessageDialog(scasmav, "���ο� ���� �޴��� �߰��Ǿ����ϴ�.");
				scasmav.dispose();
				
				
//				// ������Ʈ �ʱ�ȭ
//				scasmav.getJtfSnackName().setText("");
//				scasmav.getJtfPrice().setText("");
//				scasmav.getjtaSnackInfo().setText("");
//				scasmav.getJtfSnackName().requestFocus();
//				scasmav.getJlSnackImg().setIcon(new ImageIcon("C:/dev/workspace/cinema_prj/src/kr/co/sist/sc/admin/images/admin_snack_default_img(325x325).png"));
				
				
				
			} else {
				JOptionPane.showMessageDialog(scasmav, "�� �̻� ���� �޴��� �߰��� �� �����ϴ�. (�ִ� �޴� ���� 8��)");
			} // end else
			
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scasmav, "DB���� ������ �߻��߽��ϴ�. ����� �ٽ� �õ����ּ���.");
			sqle.printStackTrace();
		} // end catch
		
		
	} // addSnackMenu
	
	private void uploadSnackImg(File imageFile) throws IOException{
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			// ū ���� �̹��� ���ε�
			fis = new FileInputStream(imageFile);
			byte[] readData = new byte[512];
			
			int length = 0;
			String uploadPath = "C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/snack/";
			fos = new FileOutputStream(uploadPath+"l_snack_"+imageFile.getName());
			
			while((length = fis.read(readData)) != -1) {
				fos.write(readData, 0, length);
				fos.flush();
			} // end while
			
			fis.close();
			fos.close();
			
			// ���� ���� �̹��� ���ε�
			fis = new FileInputStream(imageFile.getParent()+"/s_snack_"+imageFile.getName());
			
			length = 0;
			fos = new FileOutputStream(uploadPath+"s_snack_"+imageFile.getName());
			
			while( (length=fis.read(readData)) != -1 ) {
				fos.write(readData, 0, length);
				fos.flush();
			} // end while
			
		} finally {
			if( fis != null ) { fis.close(); } // end if
			if( fos != null ) { fos.close(); } // end if
		} // end finally
		
	} // uploadSnackImg
	
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
			JOptionPane.showMessageDialog(scasmav, "DB���� ������ �߻��Ͽ����ϴ�. �������� â�� �ݰ� �ٽ� ���ּ���.");
		} // end catch
	} // refreshSnackMenu
	
} // class
