package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.sc.admin.model.SCASnackManageDAO;
import kr.co.sist.sc.admin.view.SCASnackManageView;
import kr.co.sist.sc.admin.view.SCASnackOrderAddView;
import kr.co.sist.sc.admin.vo.SCASnackMenuSelectVO;
import kr.co.sist.sc.admin.vo.SCASnackOrderAddVO;

public class SCASnackOrderAddController extends WindowAdapter implements ActionListener {

	private SCASnackManageView scasmv;
	private SCASnackOrderAddView scasoav;
	
	public SCASnackOrderAddController(SCASnackOrderAddView scasoav, SCASnackManageView scasmv) {
		this.scasoav = scasoav;
		this.scasmv = scasmv;
		searchMenu();
	} // SCASnackOrderController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == scasoav.getJcbQuan()) {
			String selectedQuan = scasoav.getJcbQuan().getSelectedItem().toString().replaceAll("��", "").trim();
			int price = Integer.parseInt(scasoav.getJtfPrice().getText().trim());
			int quan = Integer.parseInt(selectedQuan);
			scasoav.getJtfTotalPrice().setText(String.valueOf(price * quan));
		} // end if
		
		if(ae.getSource() == scasoav.getJbtOrderAdd()) {
			addSnackOrder();
		} // end if
		
		if(ae.getSource() == scasoav.getJbtClose()) {
			scasoav.dispose();
		} // end if
	}
	
	private void searchMenu() {
		String snackName = scasoav.getJtfSnackName().getText().trim();
		String imgPath = "C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/snack/";
		try {
			SCASnackMenuSelectVO scasvo = SCASnackManageDAO.getInstance().selectSnackMenuDetails(snackName);
			scasoav.getJlSnackImg().setIcon(new ImageIcon(imgPath+scasvo.getSnackImg()));
			scasoav.getJtfPrice().setText(String.valueOf(scasvo.getSnackPrice()));
			scasoav.getJtfTotalPrice().setText(String.valueOf(scasvo.getSnackPrice()));
			scasoav.getJtaSnackInfo().setText(scasvo.getSnackInfo());
			scasoav.getJlSnackImg().setIcon(new ImageIcon(imgPath+"l_snack_"+scasvo.getSnackImg()));
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(scasoav, "DB���� ������ �߻��Ͽ����ϴ�. ����� �ٽ� �õ����ּ���.");
			sqle.printStackTrace();
		} // end catch
	} // searchMenu
	
	private void addSnackOrder(){
		DefaultTableModel dtmOrderList = scasoav.getDtmOrderList();
		String orderCnt = String.valueOf(dtmOrderList.getRowCount()+1);
		String snackName = scasoav.getJtfSnackName().getText();
		String price = scasoav.getJtfPrice().getText();
		String quan = scasoav.getJcbQuan().getSelectedItem().toString().replaceAll("��", "").trim();
		String totalPrice = scasoav.getJtfTotalPrice().getText();
		
		StringBuilder msg = new StringBuilder();
		msg.append("[").append(snackName).append("][").append(quan).append(" ��][")
		.append(totalPrice).append(" ��]\n").append("�ֹ� ��Ͽ� �߰��Ͻðڽ��ϱ�?");
		
		switch (JOptionPane.showConfirmDialog(scasoav, msg, "�ֹ� Ȯ��", JOptionPane.YES_NO_OPTION)) {
		case JOptionPane.OK_OPTION:
			String[] orderRecord = {orderCnt, snackName, price, quan, totalPrice};
			dtmOrderList.addRow(orderRecord);
			JOptionPane.showMessageDialog(scasoav, "�ֹ� ��Ͽ� �߰��Ǿ����ϴ�.");
			scasoav.dispose();
		} // end siwtch
	} // addSnackOrder
	
} // class
