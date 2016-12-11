package com.carl_yang.mail;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/**
 * Author: Carl_yang on 2016/11/7
 * Email : 464479297@qq.com
 */
public class Mail {
    private String port = "25";  //smtp协议使用的是25号端口
    private String host="smtp.sina.com"; // 发件人邮件服务器
    private String user;   // 使用者账号
    private String password; //使用者密码
    private Transport transport;
    private MimeMessage msg;
    //构造发送邮件帐户的服务器，端口，帐户，密码
    public Mail(String user, String passwd) {
        this.user = user;
        this.password = passwd;
    }

    /**
     *   email  收件人电子邮箱
     *   subject 邮件标题
     *   body 正文内容
     **/
    public int sendEmail(String email, String subject, String body,Bitmap bimap,String pdf) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls","true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        msg = new MimeMessage(session);
        try {
            transport =  session.getTransport("smtp");
            transport.connect(host, user, password);    //建立与服务器连接
            msg.setSentDate(new Date());
            //设置发件人地址
            InternetAddress fromAddress  = new InternetAddress(user);
            msg.setFrom(fromAddress);
            //设置收件人地址
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(email);
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject, "UTF-8");            //设置邮件标题
            //正文
            //1、文本
            MimeBodyPart body_text = new MimeBodyPart();
            body_text.setContent(body+"<br><img src='cid:a'>","text/html;charset=utf-8");
            //2、图片
            MimeBodyPart body_pic = new MimeBodyPart();
            byte[] mapbyte=Bimap2Bytes(bimap);
//            DataSource picds=  new ByteArrayDataSource(mapbyte,"application/octet-stream");
            DataHandler aa=new DataHandler( new ByteArrayDataSource(mapbyte,"application/octet-stream"));
//            String a=new File(Environment.getExternalStorageDirectory(),"a.text").getAbsolutePath();
//            DataHandler picDataHandler = new DataHandler(new FileDataSource(new File(Environment.getExternalStorageDirectory(),imgPath)));
            body_pic.setDataHandler(aa);
            body_pic.setContentID("a");//和html链接的cid一致
            //3、文本和图片关系
            MimeMultipart relatedMultipart = new MimeMultipart();
            relatedMultipart.addBodyPart(body_text);
            relatedMultipart.addBodyPart(body_pic);
            relatedMultipart.setSubType("related");
            //将文本和图片关系封装为body一部分
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(relatedMultipart);
            //4、附件
            MimeBodyPart attachment = new MimeBodyPart();
            DataHandler attachDataHandler = new DataHandler(new FileDataSource(new File(pdf)));
            attachment.setDataHandler(attachDataHandler);
            attachment.setFileName(MimeUtility.encodeText(attachDataHandler.getName()));
            //5、整合上述部分
            MimeMultipart mixedMultipart = new MimeMultipart();
            mixedMultipart.addBodyPart(contentPart);//正文内容
            mixedMultipart.addBodyPart(attachment);//附件
            mixedMultipart.setSubType("mixed");

            msg.setContent(mixedMultipart);
            //发送
            transport.sendMessage(msg, msg.getAllRecipients());
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            Log.i("aa", "发送邮件失败!");
            return 0;
        }
    }

    //bimap转byte[]
    public byte[] Bimap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        return baos.toByteArray();
    }
}
