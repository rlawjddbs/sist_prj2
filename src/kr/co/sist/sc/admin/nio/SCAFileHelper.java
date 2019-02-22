package kr.co.sist.sc.admin.nio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ��Ʈ���� �����ϴ� �߰� ������
 * @author owner
 */
public class SCAFileHelper extends Thread {
	private static SCAFileHelper sca_fh;
	private String revMsg;
	
	private SCAFileHelper() {
		this.revMsg = "";
		
	} // SCAFileHelper
	
	public static SCAFileHelper getInstance() {
		if (sca_fh == null) {
			sca_fh = new SCAFileHelper();
		} // end if
		
		return sca_fh;
	} // getInstance
	
	@Override
	public void run() {
		try {
			Socket scClient = null;
			
			DataOutputStream dos = null;
			DataInputStream dis = null;
			
			File file = null;
			FileOutputStream fos = null;
			
			String revMsg = "";
			
			while (true) {
				// Ŭ���̾�Ʈ ���� ���
				System.out.println("���ε� ��");
				scClient = SCAFileServer.getInstance().scServer.accept();
				System.out.println("���ε� ��");
				
				// 1) Ŭ���̾�Ʈ ������ ������ ��
				if (scClient != null) {
					revMsg = this.revMsg;
					
					// 2) addEvent�� �߻����� �� (��ȭ/���� �߰�)
					if (!revMsg.equals("")) {
						System.out.println("������ IP : " + scClient.getInetAddress().getHostAddress());
						
						// 1. Ŭ���̾�Ʈ ���� �̹��� ����� ��û�Ѵ�.
						dos = new DataOutputStream(scClient.getOutputStream());
						
						dos.writeUTF(revMsg);
						dos.flush();
						
						// 2. Ŭ���̾�Ʈ ������ ������ �����͸� ȹ���Ѵ�.
						dis = new DataInputStream(scClient.getInputStream());
						
						
						
						
						
						// admin.images ��Ű���� �����ϴ� ��ȭ/���� �̹����� �˻��ؼ� 
						String[] fileNames = SCAFileServer.getInstance().sendImageList(revMsg);
						
						System.out.println("���ϸ� : " + fileNames.toString() + " / ���� ���� : " + fileNames.length);
						
						// 
						
						// �ڵ� ���
						
						revMsg = "";
					} // end if
					
					if (revMsg.equals("")) {
						System.out.println("revMsg�� �������� ����.");
					}
					
//					for (String imgName : fileNames) {
//						System.out.println(imgName + " / " + imgName.length());
//					} // end for
					
					// Ŭ���̾�Ʈ�� �ش� �̹��� ����� ����
					
					// 1) ��ȭ�� �߰��� ���
					// scamic���� Helper�� 
					
					// �������� ���� ���� ������, Ŭ���̾�Ʈ���� ���� ���� ������.
					
					// 1-1)
					// �������� ��ȭ ���, ���� �߰� �̺�Ʈ�� �߻��ϸ� 
					// FileServer���� Ŭ���̾�Ʈ ������ sendMsg�� ����
					// sendMsg�� ���� Ŭ���̾�Ʈ ���� ���� ������ ���� ����� ����
					
					// 1-2)
					// Ŭ���̾�Ʈ���� ��ȸ�� �����ϸ� (��, query�� �߻��ϸ�)
					// FileClient���� ���� ������ sendMsg�� ����
					
					// 2) ������ �߰��� ���

				} // end if
				
				// 5�� ���
				sleep(5000);
			} // end while
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	} // run
	
	/**
	 * ��ȭ/���� �߰� �̺�Ʈ�� �߻����� ��
	 * @param revMsg
	 */
	public void addEvent(String revMsg) {
		this.revMsg = revMsg;
	} // addEvent
	
	/**
	 * Unit Test
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SCAFileServer.getInstance().openServer();
			SCAFileHelper.getInstance().start();
			
			SCAFileHelper.getInstance().addEvent("movie");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} // end catch
		
	} // main
	
} // class
