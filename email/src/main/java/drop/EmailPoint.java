package drop;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
//����
public class EmailPoint {
     public static void main(String[] args) throws MessagingException {
		  Properties props=new Properties();
		  props.setProperty("mail.transport.protocol", "smtp");//����Э��
		  props.setProperty("mail.smtp.host", "smtp.qq.com");//Э���ַ
		  props.setProperty("mail.smtp.port", "465");//����˿�
		  props.setProperty("mail.smtp.auth", "true");//������Ȩ
		  //163����ֻ����������
		  //qq...����������Ҫ����ssl��ȫ��֤
		  props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//֧��ssl��֤
		  props.setProperty("mail.smtp.socketFactory.fallback", "false");//��ssl����Ϣ������
		  props.setProperty("mail.smtp.socketFactory.port", "465");//��֤�˿ں�
		  
    	 Session session=Session.getInstance(props);
    	 session.setDebug(true);
		   //�����ʼ�
    	 MimeMessage mime= createMimeMessage(session, "1978166566@qq.com", "1978166566@qq.com", "907236676@qq.com", "907236676@qq.com");
    	 Transport tran=session.getTransport();//�������Ӷ���
    	 tran.connect("1978166566@qq.com","pwfirxbyxwyqdefd");//��������,������������Ȩ��ķ�ʽ
    	 tran.sendMessage(mime, mime.getAllRecipients());//�����ʼ�
    	 tran.close();//�ر�����
     }
     
     public  static MimeMessage createMimeMessage(Session session,String send,String receive,String cReceive,String mReceive) {
    	 MimeMessage mime=new MimeMessage(session);//�����ʼ�
    	 Address address;
		try {
			//���÷�������Ϣ
			//�ʼ�:����,����,������,�ռ���   (����,ͼƬ)
			address = new InternetAddress(send,"����������", "UTF-8");
			mime.setFrom(address);
			mime.setSubject("���Ǳ���..����ͼ","UTF-8");
			//���ͼƬ�ڵ�
			MimeBodyPart image=new MimeBodyPart();
			DataHandler  dh=new DataHandler(new FileDataSource("C:\\Users\\�����\\Documents\\Tencent Files\\907236676\\FileRecv\\image\\13.jpg"));
		    image.setDataHandler(dh);//����ͼƬ
		    image.setContentID("myImage");//����ͼƬID
		    //�����ı��ڵ�,Ŀ����Ϊ�˼���ͼƬ�ڵ�
		     MimeBodyPart text=new MimeBodyPart();
		     text.setContent("��������..image:<img src='cid:img'/>", "text/html;charset=utf-8");
			
		    //�������Ͻڵ�(��װ�ı��ڵ��ͼƬ�ڵ�)
		     MimeMultipart mmu=new MimeMultipart();
		     mmu.addBodyPart(text);//���ı��ڵ���ӵ����Ͻڵ���
		     mmu.addBodyPart(image);//��ͼƬ�ڵ���ӵ����Ͻڵ���
		     mmu.setSubType("related");//���ù�����ϵ
		     //ע��������ֻ�ܳ���ͼƬ�ڵ�.���ܳ��ָ��Ͻڵ�
		     MimeBodyPart conver=new MimeBodyPart();
		     conver.setContent(mmu);//�����Ͻڵ�ת������ͨ�ڵ�
		     //��Ӹ���
		     MimeBodyPart atta=new MimeBodyPart();
		     DataHandler dah=new DataHandler(new FileDataSource("D:\\ת���ļ�\\Desktop\\S2\\Y2\\Y2\\Maven\\MavneT02\\��ϰ.txt"));
		     atta.setDataHandler(dah);//���ø���
		     //�����������ļ���(������������)
		     atta.setFileName(MimeUtility.encodeText(dah.getName()));
		     //��Ͻڵ�
		     MimeMultipart mmu2=new MimeMultipart();
		     mmu2.addBodyPart(conver);
		     mmu2.addBodyPart(atta);
		     mmu2.setSubType("mixed");//�������ı�ͼƬ�ǻ�Ϲ�ϵ
		     mime.setContent(mmu2,"text/html;charset=utf-8");
		     //�ռ�������:TO��ͨ�ռ���,cc����,bcc����
			mime.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receive,"�ռ�������", "UTF-8"));
			mime.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(cReceive,"����������", "UTF-8"));
			mime.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(mReceive,"����������", "UTF-8"));
			//���÷���ʱ��
			mime.setSentDate(new Date());
			//�����ʼ�
			mime.saveChanges();
			return mime;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    	 
     }
}
