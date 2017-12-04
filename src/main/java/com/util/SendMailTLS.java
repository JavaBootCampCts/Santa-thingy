package com.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.santa.SecretSanta;

public class SendMailTLS {

	public void sendMail(String santa, String reciever) {

		final String username = "xyz@gmail.com";
		final String password = "XXXXXXX";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("xyz@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(santa));
			message.setSubject("Testing");
			// =========================
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<H1 style='font-family:Garamond;'>Dear " + santa + ",\nGive a pleasant surprise to " + reciever
					+ " as his/her secret Santa 2017</H1><img src=\"cid:image\">";
			messageBodyPart.setContent(htmlText, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("Tulips.jpg");

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");

			// add image to the multipart
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			message.setContent(multipart);

			// ====================
			String emailText = "Dear " + santa + ",\nGive a pleasant surprise to " + reciever
					+ " as his/her secret Santa 2017";
			// message.setText(emailText);
			SecretSanta.logger.info(emailText);

			Transport.send(message);

			System.out.println("mail sent");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}