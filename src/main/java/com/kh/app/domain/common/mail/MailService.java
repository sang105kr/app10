package com.kh.app.domain.common.mail;

import lombok.RequiredArgsConstructor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor // : final멤버필드를 생성자 매개변수로 생성
public class MailService {

	private final JavaMailSender mailSender;
	private SimpleMailMessage simpleMessage = new SimpleMailMessage();

	@Value("${spring.mail.username}")
	private String from;
	/**
	 * 메일전송
	 * @param to  : 수신자
	 * @param subject : 제목
	 * @param body : 본문
	 */
	public void sendMail(String to, String subject, String body) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setSubject(subject); //메일 제목
			messageHelper.setTo(to);					 //메일 수신자
			messageHelper.setFrom(from);			 //메일 발송자
			messageHelper.setText(body,true);  //메일 본문
			mailSender.send(message);					 //메일 발송

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	public void sendMailWithAttatch(
			String to, String subject, String body, List<File> files) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setSubject(subject); //메일 제목
			messageHelper.setTo(to);					 //메일 수신자
			messageHelper.setFrom(from);			 //메일 발송자
			messageHelper.setText(body,true);  //메일 본문
			if(files.size() > 0) {
				for(File file : files) {
					messageHelper.addAttachment("첨부.jpg", file);
				}
			}
			mailSender.send(message);					 //메일 발송

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	public void sendMailWithInline(
			String to, String subject, String body, List<File> files) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setSubject(subject); //메일 제목
			messageHelper.setTo(to);					 //메일 수신자
			messageHelper.setFrom(from);			 //메일 발송자
			messageHelper.setText(body,true);  //메일 본문
			if(files.size() > 0) {
				for(File file : files) {
					messageHelper.addInline("인라인", file);
				}
			}
			mailSender.send(message);					 //메일 발송

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 고정 메세지 보낼경우
	 * @param message : 본문
	 */
	public void sendSimpleMail(String message) {
		simpleMessage.setText(message);
		mailSender.send(simpleMessage);

	}
}
