package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCAOnScreenManageDAO;
import kr.co.sist.sc.admin.view.SCAOnScreenManageView;
import kr.co.sist.sc.admin.vo.SCAOnScreenInsertVO;
import kr.co.sist.sc.admin.vo.SCAOnScreenMovieListVO;
import kr.co.sist.sc.admin.vo.SCAOnScreenMovieListVO2;
import kr.co.sist.sc.admin.vo.SCAOnscreenSelectiveVO;

public class SCAOnScreenManageController extends WindowAdapter implements ActionListener {

	private SCAOnScreenManageView smv;
	private SCAOnScreenManageDAO sm_Dao;
	private List<SCAOnScreenMovieListVO> listmovie; //�� ��ȭ ��� select
	private List<SCAOnScreenMovieListVO2> screenMovieList; //�Ʒ� ��ȭ ��� select
	private List<SCAOnscreenSelectiveVO> selectiveVO ;// ���̺� �� �ֱ�
	


	public SCAOnScreenManageController(SCAOnScreenManageView smv) {
		super();
		this.smv = smv;
		sm_Dao = SCAOnScreenManageDAO.getInstance();
		try {
			searchMovieList();
			nowScreen();
			screenMovieList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// �� ������ ��ȭ ���� ����� ���� ���κ� ������
	public void searchMovieList() throws SQLException {

		listmovie = sm_Dao.MovieList();

		for (int i = 0; i < listmovie.size(); i++) {
			smv.getDjcbMovieSelect().addElement(listmovie.get(i).getMovie_title());
		}
	}
	
//������ ��ȭ ��ȸ ���̺�������
	public void nowScreen() throws SQLException {
		selectiveVO=sm_Dao.selectiveList();
		smv.getDtmModel().setRowCount(0);
		Object[] rowData = null;
		
		for(int i=0; i< selectiveVO.size();i++) {
			rowData = new Object[8];
			rowData[0] = i + 1;
			rowData[1]=  selectiveVO.get(i).getScreen_num().toString();
			rowData[2] = selectiveVO.get(i).getMovie_code().toString();
			rowData[3] = new ImageIcon(setImg(selectiveVO.get(i).getMovie_img().toString()));
			rowData[4] = selectiveVO.get(i).getMovie_title().toString();
			rowData[5] = selectiveVO.get(i).getScreen_name().toString();
			rowData[6] = selectiveVO.get(i).getStart_time().toString();
			rowData[7] = selectiveVO.get(i).getEnd_time().toString();
			
			smv.getDtmModel().addRow(rowData);
		}
		
	}
	/**
	 * ���� ������ ��ȭ ��� �� ������ select �κ�
	 * @throws SQLException
	 */
	public void screenMovieList() throws SQLException {
		
		screenMovieList=sm_Dao.showScreenMovieList();
		
		for (int i = 0; i < screenMovieList.size(); i++) {
			smv.getDjcbSearchMovie().addElement(screenMovieList.get(i).getMovie_title());
		}
		
	}
	
	
	//////////////////////////////////////���� �ʹ� �� �����ٺκе� �Ʒ��� ������ ���ý� �����ϴ°͵�
///////////////////////////////////////////////////////////////////////////////
	
// �󿵰����� �翵 ��� �� �������� �ð� �ߺ��� �����ؾ���

	public void register() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);

		String m_title = smv.getJcbMovieSelect().getSelectedItem().toString();
		String screenArea = smv.getJcbTheaterSelect().getSelectedItem().toString();
		String m_code = "";
		int year = Integer.parseInt(smv.getJcbInsertYear().getSelectedItem().toString().substring(2));
		int dateYear = Integer.parseInt(smv.getJcbInsertYear().getSelectedItem().toString());
		int month = Integer.parseInt(smv.getJcbInsertMonth().getSelectedItem().toString());
		int day = Integer.parseInt(smv.getJcbInsertDay().getSelectedItem().toString());
		int s_hour = Integer.parseInt(smv.getJcbInsertHour().getSelectedItem().toString());
		int s_minute = Integer.parseInt(smv.getJcbInsertMinute().getSelectedItem().toString());
		int runningTime = 0; // ����Ÿ�� ��������
		int e_hour = 0;
		int e_minute = 0;

		for (int i = 0; i < listmovie.size(); i++) {
			if (listmovie.get(i).getMovie_title().equals(m_title)) {
				m_code = listmovie.get(i).getMovie_code();
				runningTime = 30+listmovie.get(i).getRunningtime();
			}
		}

		if ("�Ϲ�".equals(screenArea)) {
			screenArea = "N";
		}
		if ("�����̾�".equals(screenArea)) {
			screenArea = "P";
		}
		
		e_hour = s_hour + (runningTime / 60);
		e_minute = s_minute + (runningTime % 60);

		if (e_minute > 59) {
			e_hour += 1;
			e_minute = e_minute - 60;
		}

		StringBuilder screen_num = new StringBuilder();
		screen_num.append(screenArea).append("_").append(year).append(nf.format(month)).append(nf.format(day))
				.append(nf.format(s_hour)).append(nf.format(s_minute));
		StringBuilder onDate = new StringBuilder();
		onDate.append(dateYear).append("-").append(nf.format(month)).append("-").append(nf.format(day));
		StringBuilder start_time = new StringBuilder();
		start_time.append(nf.format(s_hour)).append(nf.format(s_minute));
		StringBuilder end_time = new StringBuilder();
		end_time.append(nf.format(e_hour)).append(nf.format(e_minute));


		
/////////////////////////////////////////////////
		 
		 
		 int vo_st_time=0;
		 int vo_en_time=0;
		 int temp_st_time=Integer.parseInt(screen_num.substring(2).toString());
		 int temp_end_time= Integer.parseInt(screen_num.substring(2,8)+end_time);
		 boolean flag_st=false;
		 boolean flag_en=false;
		 
		 String vo_date="";
		 String temp_date=screen_num.substring(0,8);
		 String sc_code="";
		 String temp_code=screenArea.toString();
		 
		 int selectiveVOcnt=selectiveVO.size();
		 int cnt=0;
		 String temp_tr="";
		 String temptr_date="";		//��ȯ�� ��¥
		 int temp_st_tr_time=0;
		 Calendar cal = Calendar.getInstance();
		 StringBuilder temp=new StringBuilder();
		 
		 for(int i=0; i< selectiveVO.size();i++) {
			 vo_st_time=Integer.parseInt(selectiveVO.get(i).getScreen_num().substring(2));
			 vo_en_time=Integer.parseInt(selectiveVO.get(i).getScreen_num().substring(2, 8)+selectiveVO.get(i).getEnd_time());
			 vo_date=selectiveVO.get(i).getScreen_num().substring(0, 8);
			 sc_code=selectiveVO.get(i).getScreen_num().substring(0, 1);
			 if(temp_code.equals(sc_code)) {
			 }
			 
			if(true==temp_code.equals(sc_code)) {
				if(temp_date.equals(vo_date)) {// ��¥�� ������
						 if( vo_st_time<=temp_st_time) {// �Է¹���  temp ��  st ���� ������
									 if(temp_st_time<=vo_en_time) {// �Է¹���  tempr ��  en ���� ������
										 flag_st=true;
										 break;
									 }
						 }
					 //���۽ð��� ����  if ��
					 	if(vo_st_time<=temp_end_time) {
							 if(temp_end_time<=vo_en_time) {
								 flag_en=true;
								 break;
							 }
					 	}
				 }
				
					 	//����
					 	
					 	if(0<=s_hour && s_hour<=4) {//00�ú��� 04 �ñ���
							 cal.set(year, month-1, day);// �Է� �Ǵ� �����
					 		cal.add(Calendar.DAY_OF_MONTH, -1);
					 		temp.append(temp_code).append("_").append(year).append(nf.format(cal.get(Calendar.MONTH)+1)).append(cal.get(Calendar.DAY_OF_MONTH))
					 							.append(nf.format(s_hour+24)).append(nf.format(s_minute));
					 		temp_tr=temp.toString().substring(0,8);
					 		temptr_date=temp.toString().substring(2,12);
					 		temp_st_tr_time=Integer.parseInt(temptr_date.toString());/// ���⼭ ����
					 		temp.setLength(0);
					 		if(temp_tr.equals(vo_date)) {// ��¥�� ������
 									 if( vo_st_time<=temp_st_tr_time) {// �Է¹���  temp ��  st ���� ������
 												 if(temp_st_tr_time<=vo_en_time) {// �Է¹���  tempr ��  en ���� ������
 													 flag_st=true;
 													 break;
 												 }
 									 }
					 		}
					 		
					 	}
			}//N,P if
			cnt=i;
		 }
		 if(flag_st || flag_en ) {//true �϶��� 
			 StringBuilder che=new StringBuilder();
			 che.append("[ ").append(smv.getJcbMovieSelect().getSelectedItem()).append(" ]").append("\n")
			 .append("[ ").append(smv.getJcbTheaterSelect().getSelectedItem()).append(" ]").append("\n")
			 .append("[ ").append(onDate).append(" ]").append("\n")
			 .append(" ���� �̹� ������ ��ȭ�� �ֽ��ϴ�");
			 JOptionPane.showMessageDialog(smv, che,"�˸�",JOptionPane.CLOSED_OPTION);
			 return;
		 }
		 if(selectiveVOcnt-1==cnt&&(flag_en==flag_st) ) {//�Ϻ��� false �϶���
		 
		/////////////////////////////////////////////////////////////////////////////////////////
					 StringBuilder insertChe=new StringBuilder();
					 insertChe.append("[ ").append(smv.getJcbMovieSelect().getSelectedItem()).append(" ]").append("\n")
					 .append("[ ").append(smv.getJcbTheaterSelect().getSelectedItem()).append(" ]").append("\n")
					 .append(onDate).append("\n").append("�� ��� �Ͻðڽ��ϱ�?");
					 SCAOnScreenInsertVO sivo = new SCAOnScreenInsertVO(screen_num.toString(), onDate.toString(),
								start_time.toString(), end_time.toString(), m_code.toString(), screenArea.toString());
					 
					 switch (JOptionPane.showConfirmDialog(smv,insertChe, "�˸�", JOptionPane.OK_CANCEL_OPTION)) {
					case JOptionPane.OK_OPTION:
						
						try {
							int cntt=0;
							cnt=sm_Dao.screenRegister(sivo);
							if (cntt == 1) {
								JOptionPane.showMessageDialog(null, insertChe);
								insertChe.append("[ ").append(smv.getJcbMovieSelect().getSelectedItem()).append(" ]").append("\n")
								 .append("[ ").append(smv.getJcbTheaterSelect().getSelectedItem()).append(" ]").append("\n")
								 .append(onDate).append("�� ����Ͽ����ϴ�");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					case JOptionPane.CANCEL_OPTION:
						
						return;
					 }
		 }
	
		
	}
	
	/**
	 * �� ��ȸ�� �Ҷ�
	 */
	public void screenCheck() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		String m_title = smv.getJcbSearchMovie().getSelectedItem().toString();
		String m_code = "";
		int year = Integer.parseInt(smv.getJcbSearchYear().getSelectedItem().toString()); //2�ڸ�
		int month = Integer.parseInt(smv.getJcbSearchMonth().getSelectedItem().toString());
		int day = Integer.parseInt(smv.getJcbSearchDay().getSelectedItem().toString());
		
		for (int i = 0; i < screenMovieList.size(); i++) {
			if (screenMovieList.get(i).getMovie_title().equals(m_title)) {
				m_code = screenMovieList.get(i).getMovie_code();
			}
		}
		//�ڵ�� ����� �� �ѱ���
		StringBuilder temp=new StringBuilder();
		temp.append(year).append("-").append(nf.format(month)).append("-").append(nf.format(day));
		
		try {
			List<SCAOnscreenSelectiveVO>list =sm_Dao.screeningSelectMovieInfo(m_code,temp.toString());
			if(null==list.get(0).getMovie_code()) {
				JOptionPane.showMessageDialog(smv, "��� ����");
				nowScreen();
				return;
			}
			else if("0".equals(list.get(0).getMovie_code())) {
				JOptionPane.showMessageDialog(smv, "��� ����");
				nowScreen();
				return;
			}
			smv.getDtmModel().setRowCount(0);
			Object[] rowData = null;
			
			for(int i=0; i< list.size();i++) {
				rowData = new Object[8];
				rowData[0] = i + 1;
				rowData[1]=  list.get(i).getScreen_num().toString();
				rowData[2] = list.get(i).getMovie_code().toString();
				rowData[3] =	new ImageIcon(setImg(list.get(i).getMovie_img().toString()).toString());
				rowData[4] = list.get(i).getMovie_title().toString();
				rowData[5] = list.get(i).getScreen_name().toString();
				rowData[6] = list.get(i).getStart_time().toString();
				rowData[7] = list.get(i).getEnd_time().toString();
				
				smv.getDtmModel().addRow(rowData);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���õ� ���̺��� ���ڵ忡 ���� ���� ����
	 */
	public void removeScreen() {
		
		
		if(-1==smv.getTableMovieList().getSelectedRow()) {
			JOptionPane.showMessageDialog(smv, "���� �� ������ ������ ��ȭ�� ��Ͽ��� ������ �ּ���","�˸�!",JOptionPane.CLOSED_OPTION);
			return;
			
			
			
		}else{
			StringBuilder deletemess=new StringBuilder();
			deletemess.append("[ ").append(smv.getTableMovieList().getValueAt(smv.getTableMovieList().getSelectedRow(), 1).toString()).append(" ]")
								.append("[ ").append(smv.getTableMovieList().getValueAt(smv.getTableMovieList().getSelectedRow(), 4).toString()).append(" ]")
								.append("\n").append("�� ���� �Ͻðٽ��ϱ�?");
			
			StringBuilder deleteChe=new StringBuilder();
			deleteChe.append("[ ").append(smv.getTableMovieList().getValueAt(smv.getTableMovieList().getSelectedRow(), 1).toString()).append(" ]")
			.append("[ ").append(smv.getTableMovieList().getValueAt(smv.getTableMovieList().getSelectedRow(), 4).toString()).append(" ]")
			.append("\n").append(" �� �����Ǿ����ϴ�");
			String delete= smv.getTableMovieList().getValueAt(smv.getTableMovieList().getSelectedRow(), 1).toString();
				switch (JOptionPane.showConfirmDialog(smv, deletemess, "����Ȯ��",JOptionPane.OK_CANCEL_OPTION)) {
				case JOptionPane.OK_OPTION:
					try {
						int cnt=0;
						cnt=sm_Dao.remove(delete);
						nowScreen();
						if (cnt == 1) {
							JOptionPane.showMessageDialog(null, deleteChe,"�˸�",JOptionPane.CLOSED_OPTION);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;
				case JOptionPane.CANCEL_OPTION:
					return;
					}	
		}
			
		
		
	}
	public String setImg(String imgFileName) {
		String path="C:/Users/owner/git/sist_prj2//src/kr/co/sist/sc/admin/images/movie/s_movie_";
		
		String allpath=path+imgFileName;
		return allpath;
	}
	
	public void day() {
		//�ش� �޿� ���� ��¥
		String  month = smv.getJcbInsertMonth().getSelectedItem().toString();
		int temp_month=Integer.parseInt(month);
		Calendar cal = new GregorianCalendar(2019,temp_month-1,1);
		int day = cal.getActualMaximum(Calendar.DATE);
		int nowday=cal.get(cal.DAY_OF_MONTH);
		smv.getDjcbInsertDay().removeAllElements();
		smv.getDjcbSearchDay().removeAllElements();
		for(int i=1;i<day+1;i++) {
		
			smv.getDjcbInsertDay().addElement(i);
			smv.getDjcbSearchDay().addElement(i);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == smv.getJbtOnScreenInsert()) {
			if("��ȭ ����".equals(smv.getJcbMovieSelect().getSelectedItem())) {
				JOptionPane.showMessageDialog(smv, "��ȭ ������ �ϼ���");
				return;
			}
			else {
				if("�󿵰�".equals(smv.getJcbTheaterSelect().getSelectedItem())) {
					
					JOptionPane.showMessageDialog(smv, "��ȭ�� ���� �� �ϼ���");
				}else {
					register();
				}
			}
		}
		if(ae.getSource() == smv.getjbtOnScreenSearch()) {//����ȸ
			if("��ȭ ����".equals(smv.getDjcbSearchMovie().getSelectedItem())) {
				JOptionPane.showMessageDialog(smv, "��ȭ ������ �ϼ���");
				return;
			}
			else {
				screenCheck();
				
			}
		}
		if(ae.getSource() == smv.getJbtOnScreenDelete()) {
			removeScreen();
		}
		if(ae.getSource() == smv.getJbtClose()) {
			smv.dispose();
		}
		
		//////////////////////////////////////////////////
		if(ae.getSource() == smv.getJcbInsertMonth() ) {
			//��¥
			day();
		}
		if(ae.getSource() == smv.getJcbSearchMonth()) {
			day();
		}
		
		
	}
	
	

	@Override
	public void windowClosing(WindowEvent e) {
		smv.dispose();
	}

}

