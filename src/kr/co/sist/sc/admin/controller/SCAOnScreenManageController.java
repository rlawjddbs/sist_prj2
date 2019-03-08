package kr.co.sist.sc.admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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
	private SCAOnScreenManageController ssmc;
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
		int temp = 0;

		for (int i = 0; i < listmovie.size(); i++) {
			if (listmovie.get(i).getMovie_title().equals(m_title)) {
				m_code = listmovie.get(i).getMovie_code();
				runningTime = listmovie.get(i).getRunningtime();
			}
		}

		System.out.println("����Ÿ��" + runningTime);
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


		SCAOnScreenInsertVO sivo = new SCAOnScreenInsertVO(screen_num.toString(), onDate.toString(),
				start_time.toString(), end_time.toString(), m_code.toString(), screenArea.toString());
/////////////////////////////////////////////////
		System.out.println(screen_num+"/"+screenArea+"/"+dateYear+"/"+s_hour+"/"+e_hour);
		 System.out.println(screen_num+"/"+onDate+"/"+start_time+"/"+end_time+"/"+m_code+"/"+screenArea);
		//seletiveVO ���� ���� ������ �ͼ� for�� ���� ���� ��
		//n,p �� �� ����Ÿ�� �� 
		 System.out.println("�󿵵��------------------------------------------");
		 int st_time=0;
		 int en_time=0;
		 int temp_date=Integer.parseInt(start_time.toString());
		 
		 for(int i=0; i< selectiveVO.size();i++) {
			 
			 st_time=Integer.parseInt(selectiveVO.get(i).getStart_time());
			 en_time=Integer.parseInt(selectiveVO.get(i).getEnd_time());
			 System.out.println(st_time+"/////"+en_time+"////////"+temp_date);
			 if(screen_num.toString().equals(selectiveVO.get(i).getScreen_num())) {
				 System.out.println("1--"+selectiveVO.get(i).getScreen_num());	
				 System.out.println("1--"+selectiveVO.get(i).getScreen_date()+"//"+start_time);
				
				 if(st_time<=temp_date) {
					 
					 if(temp_date<=en_time) {
						 System.out.println("2"+selectiveVO.get(i).getScreen_num());	
						 System.out.println("2"+selectiveVO.get(i).getScreen_date());
						 
						 JOptionPane.showMessageDialog(smv, "�ߺ�");
						 return;
					 }
					 
				 }
				 
			 }
		 }
		////////////////////////////////////////
		
		
	/*	try {
	//		sm_Dao.screenRegister(sivo);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
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
			System.out.println(list);
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
		String delete= smv.getTableMovieList().getValueAt(smv.getTableMovieList().getSelectedRow(), 1).toString();
		System.out.println(delete);
//		if(smv.)
		switch (JOptionPane.showConfirmDialog(smv, "asdf", "����Ȯ��",JOptionPane.OK_CANCEL_OPTION)) {
			case JOptionPane.OK_OPTION:
				try {
					sm_Dao.remove(delete);
					nowScreen();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
				}	
			
			
		
		
	}
	public String setImg(String imgFileName) {
		String path="C:/dev/Workspace/Cinema/src/kr/co/sist/sc/admin/images/movie/s_movie_";
		
		String allpath=path+imgFileName;
//		System.out.println(allpath);
		return allpath;
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
		
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		smv.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
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
