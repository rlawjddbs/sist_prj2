package kr.co.sist.sc.admin.nio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * ���� ���� ��û�� ������ Ŭ���̾�Ʈ�� �����ϰ� �ִ� �̹��� ����� Helper Class�� �����ϰ�, 
 * ���� �� ���� �̹����� ���Ͽ� �����ϰ� ���� ���� �̹����� ���۹޴� ��
 * ���� ��ġ : �α��� ���� �� (?)
 * @author owner
 */
public class SCUFileClient {
	private static SCUFileClient scu_fc;
	
	private SCUFileClient() { } // SCUFileClient
	
	public static SCUFileClient getInstance() {
		if (scu_fc == null) {
			scu_fc = new SCUFileClient();
		} // end if
		
		return scu_fc;
	} // getInstance
	
	/**
	 * user.images ��Ű���� �ִ� �̹����� Helper Class�� �����ϴ� ��
	 * @param revMsg
	 * @return
	 */
	public String[] sendImageList(String revMsg) {
		String[] fileList = null;
		
		String imgPath = "C:/Users/owner/git/sist_prj2/src/kr/co/sist/sc/user/images/" + revMsg + "/";
		
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
	
//	public void requestImageList() { }
	
	/**
	 * Helper Class�κ��� �̹��� ��� ���� ��û�� ������, 
	 * �̹��� ����� Helper�� �����ϰ� �������� �ʴ� �̹����� �ٿ�ε��Ѵ�.
	 * @throws IOException
	 */
	public void connectToServer() throws IOException {
		Socket scClient = null;
		
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		FileOutputStream fos = null;
		
		try {
			String address = "211.63.89.132";
			int port = 3333;
			
			scClient = new Socket(address, port);
			
			// Helper�κ��� �������� ��ȭ/������ �߰��Ǿ��ٴ� �޽����� �ް�, 
			dis = new DataInputStream(scClient.getInputStream());
			
			String revMsg = dis.readUTF();
			
			if (!revMsg.equals("")) {
				// �̹��� ����� �˻��Ͽ� 
				String[] imgList = SCUFileClient.getInstance().sendImageList(revMsg);
				
				// Helper�� ������ �� 
				dos = new DataOutputStream(scClient.getOutputStream());
				dos.writeInt(imgList.length);
				dos.flush();
				
				System.out.println(imgList.length);
				
				for (int i = 0; i < imgList.length; i++) {
					dos.writeUTF(imgList[i]);
					dos.flush();
					
					System.out.println(imgList[i]);
				} // end for
				
				// Helper���� �������� �̹��� ����� �����Ѵ�.
				dis = new DataInputStream(scClient.getInputStream());
				
				
				
			} // end if
		} finally {
			
			if (scClient != null) { scClient.close(); } // end if
		} // end finally
	} // connectToServer
	
	/**
	 * Unit Test
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SCUFileClient.getInstance().connectToServer();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} // end catch
		
	} // main
	
} // class
