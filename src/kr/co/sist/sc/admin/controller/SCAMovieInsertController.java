package kr.co.sist.sc.admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import kr.co.sist.sc.admin.model.SCAMovieManageDAO;
import kr.co.sist.sc.admin.view.SCAMovieInsertView;
import kr.co.sist.sc.admin.vo.SCAMovieInsertVO;

public class SCAMovieInsertController extends WindowAdapter implements ActionListener {
	private SCAMovieInsertView scrv;
	private SCAMovieManageDAO scamm_dao;
	private SCAMovieManageController scmc;
	private String uploadImgName;

	public SCAMovieInsertController(SCAMovieInsertView scrv, SCAMovieManageController scmc) {
		this.scrv = scrv;
		this.scmc = scmc;
		scamm_dao = SCAMovieManageDAO.getInstance();
		uploadImgName="null";
		
		scrv.addWindowListener( new WindowAdapter() {
		    public void windowOpened( java.awt.event.WindowEvent e ){
		        scrv.jtfmovieTitle.requestFocus();
		    }
		}); 
	}

	public void registerMovie() {
		
			if (scrv.getJtfmovieTitle().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "Ÿ��Ʋ �Է��ϼ���");
				scrv.getJtfmovieTitle().setText("");
				scrv.getJtfmovieTitle().requestFocus();
				return;

			}
			// �̹������� �ؾ���
			if ("null".equals(uploadImgName)) {
				JOptionPane.showMessageDialog(scrv, " �̹����� �������ּ���.");
				return;
			} // end if
			if (scrv.getJtfGenre().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "�帣.�Է��ϼ���");
				scrv.getJtfGenre().setText("");
				scrv.getJtfGenre().requestFocus();
				return;
			}
			if (scrv.getJtfCountry().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "����.�Է��ϼ���");
				scrv.getJtfCountry().setText("");
				scrv.getJtfCountry().requestFocus();
				return;
			}
			if (scrv.getJtfDirector().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "���� �Է��ϼ���");
				scrv.getJtfDirector().setText("");
				scrv.getJtfDirector().requestFocus();
				return;
			}
			if (scrv.getJtfmovieGrade().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "��� �Է��ϼ���");
				scrv.getJtfmovieGrade().setText("");
				scrv.getJtfmovieGrade().requestFocus();
				return;
			}
			if(scrv.getJtfmovieGrade().getText().length()>2) {
				JOptionPane.showMessageDialog(scrv, "����� 2 �ڸ� �Դϴ�");
				scrv.getJtfmovieGrade().setText("");
				scrv.getJtfmovieGrade().requestFocus();
			}
			if (scrv.getJtfPlaydate().getText().equals("")) {//������϶�
				JOptionPane.showMessageDialog(scrv, "��¥ �Է�");
				scrv.getJtfPlaydate().setText("");
				scrv.getJtfPlaydate().requestFocus();
				return;
			}
			if(scrv.getJtfPlaydate().getText().length()>8) {
				JOptionPane.showMessageDialog(scrv, "��¥�� ������ EX) 20191111 ");
				scrv.getJtfPlaydate().setText("");
				scrv.getJtfPlaydate().requestFocus();
				return;
			}
			if (scrv.getJtaSysnopsis().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "�ٰŸ� �Է��ϼ���");
				scrv.getJtaSysnopsis().setText("");
				scrv.getJtaSysnopsis().requestFocus();
				return;
			}
			if (scrv.getJtfActor().getText().equals("")) {
				JOptionPane.showMessageDialog(scrv, "��� �Է��ϼ���" );
				scrv.getJtfActor().setText("");
				scrv.getJtfActor().requestFocus();
				return;
			}
			// ���� �� �ѹ� �Լ¼� ���� ó��
			@SuppressWarnings("unused")
			int num = 0;
			try {
				num = Integer.parseInt(scrv.getJtfRunningtime().getText().trim());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(scrv, "���� Ÿ���� ���ڸ� �Է°���");
				return;
			}
			
		// �߰�
		// ������ ��� �� ���ϸ��� ������ �;���
		SCAMovieInsertVO scami_vo = new SCAMovieInsertVO(scrv.getJtfmovieTitle().getText(), uploadImgName,
				scrv.getJtfGenre().getText(), scrv.getJtfCountry().getText(), scrv.getJtfDirector().getText(),
				scrv.getJtfmovieGrade().getText(), scrv.getJtfPlaydate().getText(), scrv.getJtaSysnopsis().getText(),
				scrv.getJtfActor().getText(), scrv.getAdminId(), Integer.parseInt(scrv.getJtfRunningtime().getText()));

		try {
			StringBuilder tempTitle=new StringBuilder();
			tempTitle.append("[ ").append(scrv.getJtfmovieTitle().getText()).append(" ]").append("��").append("\n").append("��� �Ͻðٽ��ϱ�?");
			switch (JOptionPane.showConfirmDialog(scrv, tempTitle,"��� Ȯ��", JOptionPane.OK_CANCEL_OPTION)) {
			case JOptionPane.OK_OPTION:
				scamm_dao.registerMovie(scami_vo);
				JOptionPane.showMessageDialog(null, "�߰��Ϸ�");
				scrv.dispose();
				scmc.showMovie();
				break;
			case JOptionPane.CANCEL_OPTION:
				JOptionPane.showMessageDialog(null, "���");
				break;
			}
			
			
			
			
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void setImg() {
		FileDialog fdOpen = new FileDialog(scrv, "������ ���ε�", FileDialog.LOAD);
		
		fdOpen.setVisible(true);
	
		String path = fdOpen.getDirectory();
		String name = fdOpen.getFile();
		
		if(null==path) {
			return;
		}
		
		name=name.substring(8);
		
		boolean flag = false;
		if (path != null) {
			String[] extFlag = { "jpg", "gif", "jpeg", "png", "bmp" };

			for (String ext : extFlag) {

				if (name.toLowerCase().endsWith(ext)) { // ���ε� ���� Ȯ����
					flag = true;
				} // end if

			} // end for
			if (flag) {
				uploadImgName = name;
				scrv.getImg().setIcon(new ImageIcon(path+"l_movie_" + name));
			} else {
				JOptionPane.showMessageDialog(scrv, name + "�� �̹����� �ƴմϴ�.");
			} // end if

		} // end if

	} // setImg

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == scrv.getImgRegister()) {
			
			setImg();
		}
		if (ae.getSource() == scrv.getRegister()) {
			registerMovie();
		}
		if (ae.getSource() == scrv.getExit()) {
			scrv.dispose();
		}
	}

}
