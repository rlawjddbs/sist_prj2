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
					
					if (revMsg.equals("")) {
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
					
					// 1) ��ȭ�� �߰��� ���
					// scamic���� Helper�� addEvent method ȣ��
					
					// 1-1)
					// �������� ��ȭ ���, ���� �߰� �̺�Ʈ�� �߻��ϸ� 
					// FileServer���� Ŭ���̾�Ʈ ������ sendMsg�� ����
					// sendMsg�� ���� Ŭ���̾�Ʈ ���� ���� ������ ���� ����� ����
					
					// 2) ������ �߰��� ���
					// scasic���� Helper�� addEvent method ȣ��
					
					// 2-1)
					// 1-1 ��
					
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
	
} // class
