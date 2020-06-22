package hello;

/*

    @author sunchao
    @create 2020/6/22 --18:10
   
*/

//2import com.sun.tools.sjavac.server.SysInfo;

import javax.mail.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

public class SendTextMail {
    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {

        Scanner in = new Scanner(System.in);
        Mail mail = new Mail();
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", mail.getSmtpServer());
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        System.out.println("请选择要发送的邮件种类\n1:文本文件\n2:带附件的邮件");
        String choice = in.nextLine();
        Message msg ;
        if(choice.equals("2"))
            msg = mail.getMimeMessage(session,mail);
        else if(choice.equals("1"))
            msg = mail.getTxtMessage(session);
        else{
            System.out.println("请输入正确的选项\n");
            return;
        }
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(mail.getUserName(),mail.getPassword());
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());

        //5、关闭邮件连接
        transport.close();

    }
}
