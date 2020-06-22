package hello;

/*

    @author sunchao
    @create 2020/6/22 --17:32
   
*/



import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;



public class Mail {

    /** 邮件主题 **/
    private String subject;
    /** 从此地址发出 **/
    private String fromMail ="2737793330@qq.com";
    /** 用户名 **/
    private String userName = "2737793330@qq.com";
    /** 登录密码 **/
    private String password = "lipbeahsvaghdggc";
    /** SMTP 服务器地址 **/
    private String smtpServer = "smtp.qq.com";
    /** POP3 服务器地址 **/
    private String pop3Server;
    /** SMTP 服务器端口（163默认：25  qq默认：587） **/
    private int smtpPort ;
    /** POP3 服务器端口  默认110 **/
    private int pop3Port = 110;
    /** 发送到 toMail 中的所有地址 **/
    private String toMail;
    /** 邮件内容 **/
    private String content;
    /** 是否显示日志 **/
    private boolean showLog = true;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public void setPop3Server(String pop3Server) {
        this.pop3Server = pop3Server;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setPop3Port(int pop3Port) {
        this.pop3Port = pop3Port;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public String getFromMail() {
        return fromMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public String getPop3Server() {
        return pop3Server;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public int getPop3Port() {
        return pop3Port;
    }

    public String getToMail() {
        return toMail;
    }

    public String getContent() {
        return content;
    }

    public MimeMessage getMimeMessage(Session session ,Mail mail) throws MessagingException, UnsupportedEncodingException {
        Scanner in = new Scanner(System.in);
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(mail.getFromMail()));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */

        System.out.println("请输入邮件发送地址\n");
        this.setToMail(in.nextLine());
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(mail.getToMail()));
        //4.设置邮件主题
        System.out.println("请输入邮件标题\n");
        this.setSubject(in.nextLine());
        msg.setSubject(this.getSubject());

        //下面是设置邮件正文
        //msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");

        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        System.out.println("请输入图片目录\n");
        String pathImage = in.nextLine();
        DataHandler dh = new DataHandler(new FileDataSource(pathImage));
        // 将图片数据添加到"节点"
        image.setDataHandler(dh);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("mailTestPic");

        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("这是一张图片<br/><a href='http://www.baidu.com'><img src='cid:mailTestPic'/></a>", "text/html;charset=UTF-8");

        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // 关联关系

        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        System.out.println("请输入文件目录\n");
        String pathDoc = in.nextLine();
        DataHandler dh2 = new DataHandler(new FileDataSource(pathDoc));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    //发送纯文本邮件
    public Message getTxtMessage(Session session) throws MessagingException {
        Scanner in =new Scanner(System.in);
        //  标准输入流

        // 创建邮件对象
        Message msg = new MimeMessage(session);
        System.out.println("请输入邮件标题\n");
        this.setSubject(in.nextLine());
        msg.setSubject(this.getSubject());

        // 设置邮件内容
        System.out.println("请输入邮件内容\n");
        this.setContent(in.nextLine());
        msg.setText(this.getContent());

        // 设置发件人
        msg.setFrom(new InternetAddress(userName));

        Transport transport = session.getTransport();
        // 连接邮件服务器
        transport.connect(userName, password);
        // 发送邮件
        System.out.println("请输入邮件发送地址\n");
        this.setToMail(in.nextLine());
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(this.getToMail()));


        return msg;

    }


}
