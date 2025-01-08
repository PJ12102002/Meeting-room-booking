package com.example.MeetingRoomBooking.service;


import com.example.MeetingRoomBooking.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getTo());
            mailMessage.setText(details.getBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }
//public String sendSimpleMail(EmailDetails details)
//{
//    MimeMessage mimeMessage
//            = javaMailSender.createMimeMessage();
//    MimeMessageHelper mimeMessageHelper;
//
//    try {
//        mimeMessageHelper
//                = new MimeMessageHelper(mimeMessage, true);
//        mimeMessageHelper.setFrom(sender);
//        mimeMessageHelper.setTo(details.getTo());
//        mimeMessageHelper.setText(details.getBody());
//        mimeMessageHelper.setSubject(
//                details.getSubject());
//        javaMailSender.send(mimeMessage);
//        return "Mail sent Successfully";
//    }
//    catch (Exception e) {
//        return "Error while sending mail!!!";
//    }
//}


}
