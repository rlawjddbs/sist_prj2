package kr.co.sist.sc.admin.nio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
			
			int code = 0;
			int cnt = 0;
			
			String revMsg = "";
			
			String[] serverFileNames = null;
			String[] clientFileNames = null;
			
			List<String> checkFileNames = null;
			
			while (true) {
				// Ŭ���̾�Ʈ ���� ���
				scClient = SCAFileServer.getInstance().scServer.accept();
				
				// 1) Ŭ���̾�Ʈ ������ ������ ��
				if (scClient != null) {
					revMsg = this.revMsg;
					
					// Ŭ���̾�Ʈ ������ ����������, ��ȭ/���� �߰��� �߻����� ���� ���
					// Ŭ���̾�Ʈ ������ �̹��� ������ ��û�� ��
					if (revMsg.equals("")) {
						dis = new DataInputStream(scClient.getInputStream());
						
						code = dis.readInt();
						
						revMsg = SCAFileHelper.getInstance().translateCode(code);
					} // end if
					
					// trouble prevention
					if (revMsg.equals("")) {
						// Connection reset
						return;
					} // end if
					
					// 2) addEvent�� �߻����� �� (��ȭ/���� �߰�)
					if (!revMsg.equals("")) {
						// 1. Ŭ���̾�Ʈ ���� �̹��� ����� ��û�Ѵ�.
						dos = new DataOutputStream(scClient.getOutputStream());
						
						dos.writeUTF(revMsg);
						dos.flush();
						
						// 2. Ŭ���̾�Ʈ ������ �۽��� �����͸� ȹ���Ѵ�.
						dis = new DataInputStream(scClient.getInputStream());
						
						// ���۹��� Ŭ���̾�Ʈ ������ ����
						cnt = dis.readInt();
						
						// Ŭ���̾�Ʈ ������ ������ŭ �����͸� ���۹޴´�.
						clientFileNames = new String[cnt];
						
						for (int i = 0; i < cnt; i++) {
							clientFileNames[i] = dis.readUTF();
						} // end for
						
						// admin.images ��Ű���� �����ϴ� ��ȭ/���� �̹����� �˻��ؼ� 
						serverFileNames = SCAFileServer.getInstance().sendImageList(revMsg);
						
						// ���� ������ Ŭ���̾�Ʈ�� �����ϰ� �ִ� �̹��� ����� ���Ͽ� 
						checkFileNames = new ArrayList<String>();
						
						for (String sfn : serverFileNames) {
							checkFileNames.add(sfn);
						} // end for
						
						for (String cfn : clientFileNames) {
							checkFileNames.remove(cfn);
						} // end for
						
						// Ŭ���̾�Ʈ�� �����ϰ� ���� ���� �̹����� �������ش�.
						dos = new DataOutputStream(scClient.getOutputStream());
						
						// Ŭ���̾�Ʈ�� ���� ������ ������ �����Ѵ�.
						dos.writeInt(checkFileNames.size());
						dos.flush();
						
						for (String fileName : checkFileNames) {
							SCAFileServer.getInstance().sendFile(dos, fileName);
							sleep(100);
						} // end for
						
						revMsg = "";
					} // end if
				} // end if
			} // end while
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} // end catch
	} // run
	
	/**
	 * ��ȭ/���� �߰� �̺�Ʈ�� �߻����� ��
	 * @param revMsg
	 */
	public void addEvent(String revMsg) {
		this.revMsg = revMsg;
	} // addEvent
	
	/**
	 * �ڵ� ��ȯ
	 * @return
	 */
	private String translateCode(int code) {
		String title = "";
		
		// revMsg = "movie"
		if (code == 1) {
			title = "movie";
		} // end if
		
		// revMsg = "snack"
		if (code == 2) {
			title = "snack";
		} // end if
		
		return title;
	} // translateCode
	
	/**
	 * ���� ���� ����
	 * @throws IOException
	 */
	public void openServer() throws IOException {
		SCAFileServer.getInstance().open();
	} // openServer
	
	/**
	 * ���� ���� ����
	 * @throws IOException
	 */
	public void closeServer() throws IOException {
		SCAFileServer.getInstance().close();
	} // closeServer
	
//	public static void main(String[] args) {
//		try {
//			SCAFileHelper.getInstance().openServer();
//			System.out.println("3333 Port open");
//			SCAFileHelper.getInstance().start();
//			System.out.println("Thread start");
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		} // end catch
//	} // main
	
} // class
