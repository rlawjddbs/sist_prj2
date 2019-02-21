package kr.co.sist.sc.admin.nio;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * 1) ��ȭ�� �߰��� ���
 * 2) ������ �߰��� ���
 * Ŭ���̾�Ʈ ������ ������ �߻��ϸ� ? �̹� ������ Ŭ���̾�Ʈ�� ��쿡��?
 * s_ : thumbnail image
 * l_ : original image
 * @author owner
 */
public class SCAFileServer {
	private static SCAFileServer sca_fs;
	protected ServerSocket scServer;
	
	private SCAFileServer() {
		openServer();
		
	} // SCAFileServer
	
	public static SCAFileServer getInstance() {
		if (sca_fs == null) {
			sca_fs = new SCAFileServer();
		} // end if
		
		return sca_fs;
	} // getInstance
	
	/**
	 * ���� ���� ���� (3333�� PORT)
	 */
	private void openServer() {
		if (scServer == null) {
			try {
				int port = 3333;
				
				scServer = new ServerSocket(port);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} // end catch
		} // end if
	} // openServer
	
	/**
	 * images ��Ű���� �ִ� �̹����� Helper Class�� �����ϴ� ��
	 * @param revMsg "movie" or "snack"
	 * @return
	 */
	public String[] sendImageList(String revMsg) {
		String[] fileList = null;
		
		String imgPath = "C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/admin/images/";
		
		File file = new File(imgPath);
		
		List<String> list = new ArrayList<String>();
		
		for (String fileName : file.list()) {
			if (fileName.startsWith("l_" + revMsg + "_") && fileName.endsWith(".png")) {
				list.add(fileName);
			} // end if	
		} // end for
		
		fileList = new String[list.size()];
		
		list.toArray(fileList);
		
		return fileList;
	} // sendImageList
	
	/**
	 * 
	 */
	public void receiveMessage() {
		
	} // receiveMessage

//	public static void main(String[] args) {
//		SCAFileServer fs = new SCAFileServer();
//		
//		fs.sendImageList("movie");
//		
//	} // main
	
} // class
