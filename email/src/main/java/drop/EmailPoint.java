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
public class EmailPoint {
	//解决冲突1 擦大
     public static void main(String[] args) throws MessagingException {
		  Properties props=new Properties();
		  props.setProperty("mail.transport.protocol", "smtp");//邮箱协议
		  props.setProperty("mail.smtp.host", "smtp.qq.com");//协议地址
		  props.setProperty("mail.smtp.port", "465");//邮箱端口
		  props.setProperty("mail.smtp.auth", "true");//邮箱授权
		  //163邮箱只需配置上面
		  //qq...其他邮箱需要配置ssl安全认证
		  props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//支持ssl认证
		  props.setProperty("mail.smtp.socketFactory.fallback", "false");//非ssl的消息不处理
		  props.setProperty("mail.smtp.socketFactory.port", "465");//认证端口号
		  
    	 Session session=Session.getInstance(props);
    	 session.setDebug(true);
		   //创建邮件
    	 MimeMessage mime= createMimeMessage(session, "1978166566@qq.com", "1978166566@qq.com", "907236676@qq.com", "907236676@qq.com");
    	 Transport tran=session.getTransport();//建立连接对象
    	 tran.connect("1978166566@qq.com","pwfirxbyxwyqdefd");//建立连接,其中密码以授权码的方式
    	 tran.sendMessage(mime, mime.getAllRecipients());//发送邮件
    	 tran.close();//关闭连接1
     }
     
     public  static MimeMessage createMimeMessage(Session session,String send,String receive,String cReceive,String mReceive) {
    	 MimeMessage mime=new MimeMessage(session);//创建邮件
    	 Address address;
		try {
			//设置发件人信息
			//邮件:标题,正文,发件人,收件人   (附件,图片)
			address = new InternetAddress(send,"发件人名字", "UTF-8");
			mime.setFrom(address);
			mime.setSubject("这是标题..还有图","UTF-8");
			//添加图片节点
			MimeBodyPart image=new MimeBodyPart();
			DataHandler  dh=new DataHandler(new FileDataSource("C:\\Users\\许邦柱\\Documents\\Tencent Files\\907236676\\FileRecv\\image\\13.jpg"));
		    image.setDataHandler(dh);//设置图片
		    image.setContentID("myImage");//设置图片ID
		    //创建文本节点,目的是为了加载图片节点
		     MimeBodyPart text=new MimeBodyPart();
		     text.setContent("正文内容..image:<img src='cid:img'/>", "text/html;charset=utf-8");
			
		    //创建复合节点(组装文本节点和图片节点)
		     MimeMultipart mmu=new MimeMultipart();
		     mmu.addBodyPart(text);//将文本节点添加到复合节点上
		     mmu.addBodyPart(image);//将图片节点添加到复合节点上
		     mmu.setSubType("related");//设置关联关系
		     //注意正文中只能出现图片节点.不能出现复合节点
		     MimeBodyPart conver=new MimeBodyPart();
		     conver.setContent(mmu);//将复合节点转换成普通节点
		     //添加附件
		     MimeBodyPart atta=new MimeBodyPart();
		     DataHandler dah=new DataHandler(new FileDataSource("D:\\转移文件\\Desktop\\S2\\Y2\\Y2\\Maven\\MavneT02\\练习.txt"));
		     atta.setDataHandler(dah);//设置附件
		     //给附件设置文件名(处理中文乱码)
		     atta.setFileName(MimeUtility.encodeText(dah.getName()));
		     //组合节点
		     MimeMultipart mmu2=new MimeMultipart();
		     mmu2.addBodyPart(conver);
		     mmu2.addBodyPart(atta);
		     mmu2.setSubType("mixed");//附件和文本图片是混合关系
		     mime.setContent(mmu2,"text/html;charset=utf-8");
		     //收件人类型:TO普通收件人,cc抄送,bcc密送
			mime.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receive,"收件人名字", "UTF-8"));
			mime.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(cReceive,"抄送人名字", "UTF-8"));
			mime.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(mReceive,"密送人名字", "UTF-8"));
			//设置发件时间
			mime.setSentDate(new Date());
			//保存邮件
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
