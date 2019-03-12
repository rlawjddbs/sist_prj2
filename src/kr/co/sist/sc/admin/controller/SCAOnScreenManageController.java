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
import kr.co.sist.sc.admin.vo.insertSelectiveVO;

public class SCAOnScreenManageController extends WindowAdapter implements ActionListener {

	private SCAOnScreenManageView smv;
	private SCAOnScreenManageDAO sm_Dao;
//	private SCAOnScreenManageController ssmc;
	private List<SCAOnScreenMovieListVO> listmovie; //�� ��ȭ ��� select
	private List<SCAOnScreenMovieListVO2> screenMovieList; //�Ʒ� ��ȭ ��� select
	private List<insertSelectiveVO> selectiveVO ;// ���̺� �� �ֱ�
	

//	SCAOnScreenManageView smv

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
//		smv.getDjcbSearchMovie().addElement(listmovie.get(i).getMovie_title());
			smv.getDjcbMovieSelect().addElement(listmovie.get(i).getMovie_title());
		}
	}
	
//������ ��ȭ�� ��ȭ���� �κ��� ��ȸ ���̺�������
	public void nowScreen() throws SQLException {
		//"����", "�󿵹�ȣ","��ȭ�ڵ�", "������", "����","�󿵰�","�� ���۽ð�","������ð�
		String path="C:/dev/Workspace/Cinema/src/kr/co/sist/sc/admin/images/movie/s_movie_";
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
	 * ���� ������ ��ȭ ��� �� ������
	 * @throws SQLException
	 */
	public void screenMovieList() throws SQLException {
		
		screenMovieList=sm_Dao.showScreenMovieList();
		
		for (int i = 0; i < screenMovieList.size(); i++) {
//		smv.getDjcbSearchMovie().addElement(listmovie.get(i).getMovie_title());
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
				runningTime = listmovie.get(i).getRunningtime();
			}
		}

//		System.out.println("����Ÿ��" + runningTime);
		if ("�Ϲ�".equals(screenArea)) {
			screenArea = "N";
		}
		if ("�����̾�".equals(screenArea)) {
			screenArea = "P";
		}
		
		// ent_time�� ���� ����� ������ 60���� ����� ���� �ð��� ���ϰ�
		// �������� �а����ؼ� 60�� �Ѿ�� �ø� �����ְ� �� �������� ������ �Ѵ�
		e_hour = s_hour + (runningTime / 60);
		e_minute = s_minute + (runningTime % 60);

		if (e_minute > 59) {
			e_hour += 1;
			e_minute = e_minute - 60;
		}
//		System.out.println(s_hour+"���۽ú�"+s_minute+"/////////"+e_hour+"���ð� ��"+e_minute);
		// sysdate
//SCREEN_NUM	SCREEN_DATE  	START_TIME	 end_time	MOVIE_CODE	SCREEN_NAME	
//	N_19 0129 0830 	2019-01-29	       0830			���  			M_000020			N	

//	System.out.println(m_code+"/"+screenArea+"/"+year+"/"+month+"/"+day+"/"+s_hour+"/"+s_minute);
//		System.out.println(nf.format(s_hour)+"��/��"+nf.format(s_minute));
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
//		System.out.println(screen_num+"/"+screenArea+"/"+dateYear+"/"+s_hour+"/"+e_hour);
//		 System.out.println(screen_num+"/"+onDate+"/"+start_time+"/"+end_time+"/"+m_code+"/"+screenArea);
		//seletiveVO ���� ���� ������ �ͼ� for�� ���� ���� ��
		//n,p �� �� ����Ÿ�� �� 
		 
		 
		 System.out.println("�󿵵��---����ϱ��� �ߺ� �˻�---------------");
		 int st_time=0;
		 int en_time=0;
		 int temp_st_date=Integer.parseInt(screen_num.substring(2).toString());
		 int temp_end_date= Integer.parseInt(screen_num.substring(2,8)+end_time);
		 boolean flag_st=false;
		 boolean flag_en=false;
		 String date="";
		 String temp_date=screen_num.substring(2,8);
		 String sc_code="";
		 String temp_code=screenArea.toString();
		 
		 System.out.println(sc_code+"/////"+temp_code);
		 for(int i=0; i< selectiveVO.size();i++) {
			 st_time=Integer.parseInt(selectiveVO.get(i).getScreen_num().substring(2));
			 en_time=Integer.parseInt(selectiveVO.get(i).getScreen_num().substring(2, 8)+selectiveVO.get(i).getEnd_time());
			 date=selectiveVO.get(i).getScreen_num().substring(2, 8);
			 sc_code=selectiveVO.get(i).getScreen_num().substring(0, 1);
			 
			 
			//SCREEN_NUM	SC
//				N_19 0129 0830
			 // ��ũ���ѹ�
//			 if(screen_num.toString().equals(selectiveVO.get(i).getScreen_num())) {
//				 System.out.println("--"+selectiveVO.get(i).getScreen_num()+"---st--"+st_time+"//en/"+en_time+"////"+temp_st_date+"////"+end_time+"///"+temp_end_date);	
				
//			 System.out.println("1--"+selectiveVO.get(i).getScreen_num()+"-����-"+st_time+"/en//"+en_time+"//�Է�/"+temp_st_date+"///////"+temp_end_date);	
			if(temp_code.equals(sc_code)) {
				if(temp_date.equals(date)) {// ��¥�� ������
///					 System.err.println(selectiveVO.get(i));
//					 System.out.println(sc_code+"////"+temp_code+"/////"+date+"////"+temp_date);
//					 System.out.println("���� �ڵ��� ���� ��¥");
						 if( st_time<=temp_st_date) {// �Է¹���  temp ��  st ���� ������
//								 System.out.println("1--"+selectiveVO.get(i).getScreen_num()+"-����-"+st_time+"/en//"+en_time+"//�Է�/"+temp_st_date+"///en//"+temp_end_date);	
//								 System.out.println();
									 if(temp_st_date<=en_time) {// �Է¹���  tempr ��  en ���� ������
										 flag_st=true;
										 System.out.println(flag_st+"//////"+flag_en+"-����-"+st_time+"/en//"+en_time+"//�Է�/"+temp_st_date+"///en//"+temp_end_date);
//										 System.out.println("111111111111111111111111111111111111�Ʒ�");
									 }
						 }
					 System.out.println();
					 //���۽ð��� ����  if ��
					 	if(st_time<=temp_end_date) {
							 if(temp_end_date<=en_time) {
								 flag_en=true;
								 System.out.println("�ȵ�");
								 System.out.println(flag_st+"//////"+flag_en+"-����-"+st_time+"/en//"+en_time+"//�Է�/"+temp_st_date+"///en//"+temp_end_date);
							 }
					 	}
			 }
			}//N,P if
		 }
			if(flag_en==flag_st) {
			System.out.println("������"+flag_st+"//////"+flag_en+"-����-"+st_time+"/en//"+en_time+"//�Է�/"+temp_st_date+"///en//"+temp_end_date);
			}
			/* if(st_time<=temp_st_date && temp_st_date <=st_time) {// ���۽ð�
					 System.out.println("111111111111111111111111111111111111�Ʒ�");
					 System.out.println("1--"+selectiveVO.get(i).getScreen_num()+"-����-"+st_time+"/en//"+en_time+"//�Է�/"+temp_st_date+"///////"+temp_end_date);	
					 System.out.println();
					 if(temp_st_date<=en_time) {
						 System.out.println("222222222222222222222222222222222222222�Ʒ�.");
						 System.out.println("1--"+selectiveVO.get(i).getScreen_num()+"---����--"+st_time+"/en//"+en_time+"/�Է�//"+temp_st_date+"/////"+temp_end_date);
						 System.out.println();
						 StringBuilder che=new StringBuilder();
						 che.append("[ ").append(smv.getJcbMovieSelect().getSelectedItem()).append(" ]").append("\n")
						 .append("[ ").append(smv.getJcbTheaterSelect().getSelectedItem()).append(" ]").append("\n")
						 .append("[ ").append(onDate).append(" ]").append("\n")
						 .append(" ���� �̹� ������ ��ȭ�� �ֽ��ϴ�");
						 JOptionPane.showMessageDialog(smv, che,"�˸�",JOptionPane.CLOSED_OPTION);
						 return;
					 }
					 
				 }*/
				 
	//		 }
//		 }
		////////////////////////////////////////////////////////////////////////////////////////// �Ʒ��� �߰� �κ�
		/* StringBuilder insertChe=new StringBuilder();
		 insertChe.append("[ ").append(smv.getJcbMovieSelect().getSelectedItem()).append(" ]").append("\n")
		 .append("[ ").append(smv.getJcbTheaterSelect().getSelectedItem()).append(" ]").append("\n")
		 .append(onDate).append("\n").append("�� ��� �Ͻðڽ��ϱ�?");
		 SCAOnScreenInsertVO sivo = new SCAOnScreenInsertVO(screen_num.toString(), onDate.toString(),
					start_time.toString(), end_time.toString(), m_code.toString(), screenArea.toString());
		 switch (JOptionPane.showConfirmDialog(smv,insertChe, "�˸�", JOptionPane.OK_CANCEL_OPTION)) {
		case JOptionPane.OK_OPTION:
			
			try {
				int cnt=0;
				cnt=sm_Dao.screenRegister(sivo);
				if (cnt == 1) {
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
			
			return;*/
	
		
	}
	
	/**
	 * �� ��ȸ�� �Ҷ�
	 */
	public void screenCheck() {
		// djcbSearchMovie =new DefaultComboBoxModel<String>();
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
		
		System.out.println("controller ��ȸ ");
		try {
			List<insertSelectiveVO>list =sm_Dao.screeningSelectMovieInfo(m_code,temp.toString());
			System.out.println("����Ʈ����      "+list.get(0).getMovie_code());
			if(null==list.get(0).getMovie_code()) {
				System.out.println("controller-screenCheck()   �ڷ����"+list);
				JOptionPane.showMessageDialog(smv, "��� ����");
				nowScreen();
				return;
			}
			else if("0".equals(list.get(0).getMovie_code())) {
				System.out.println("controller-screenCheck()   �ڷ����"+list);
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
		System.out.println("����");
		System.out.println( smv.getTableMovieList().getSelectedRow());
		
		
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
		String path="C:/dev/Workspace/Cinema/src/kr/co/sist/sc/admin/images/movie/s_movie_";
		
		String allpath=path+imgFileName;
//		System.out.println(allpath);
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
//		smv.getdcbInsertDay.add/*Item(i);
//		jcbSearchDay.addItem(i);*/
		}
//		jcbInsertDay.setSelectedItem(new Integer(nowday));
//		jcbSearchDay.setSelectedItem(new Integer(nowday));
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
//	 public static void main(String[] args) { 
//			SCAOnScreenManageController a=new SCAOnScreenManageController();
//			
//			 
//			 try {
//				 a.searchMovieList();
//			 } 
//			 
//			 catch (SQLException e) { 
//				 // TODO	 Auto-generated catch block 
//				 e.printStackTrace(); }
//	 }

}
