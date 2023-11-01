package com.secret.santa;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

	static public String GMAIL_ADRESS;
	static public String GMAIL_PWD;
	
	private String host = null;
	private InternetAddress from = null;
	private InternetAddress[] to = null;
	private InternetAddress[] cc = null;
	private InternetAddress[] bcc = null;
	private String title = null;
	private StringBuffer body = null;
	private File[] attachments = null;

	public Mail() {
		mail(null, null, null, null, null, null, null, null);
	}

	public Mail(String host) {
		mail(host, null, null, null, null, null, null, null);
	}

	public Mail(String[] to, String from, String[] cc, String[] bcc, String title, String body) throws AddressException {
		mail(null, convert(to), convert(from), convert(cc), convert(bcc), title, body, null);
	}

	public Mail(String[] to, String from, String[] cc, String[] bcc, String title, String body, String[] attachments) throws AddressException {
		mail(null, convert(to), convert(from), convert(cc), convert(bcc), title, body, convertFiles(attachments));
	}

	public Mail(String[] to, String from, String[] cc, String[] bcc, String title, String body, File[] attachments) throws AddressException {
		mail(null, convert(to), convert(from), convert(cc), convert(bcc), title, body, attachments);
	}

	public Mail(InternetAddress[] to, InternetAddress from, InternetAddress[] cc, InternetAddress[] bcc, String title, String body) {
		mail(null, to, from, cc, bcc, title, body, null);
	}

	public Mail(InternetAddress[] to, InternetAddress from, InternetAddress[] cc, InternetAddress[] bcc, String title, String body, String[] attachments) {
		mail(null, to, from, cc, bcc, title, body, convertFiles(attachments));
	}

	public Mail(InternetAddress[] to, InternetAddress from, InternetAddress[] cc, InternetAddress[] bcc, String title, String body, File[] attachments) {
		mail(null, to, from, cc, bcc, title, body, attachments);
	}

	public Mail(String host, String[] to, String from, String[] cc, String[] bcc, String title, String body) throws AddressException {
		mail(host, convert(to), convert(from), convert(cc), convert(bcc), title, body, null);
	}

	public Mail(String host, String[] to, String from, String[] cc, String[] bcc, String title, String body, String[] attachments) throws AddressException {
		mail(host, convert(to), convert(from), convert(cc), convert(bcc), title, body, convertFiles(attachments));
	}

	public Mail(String host, String[] to, String from, String[] cc, String[] bcc, String title, String body, File[] attachments) throws AddressException {
		mail(host, convert(to), convert(from), convert(cc), convert(bcc), title, body, attachments);
	}

	public Mail(String host, InternetAddress[] to, InternetAddress from, InternetAddress[] cc, InternetAddress[] bcc, String title, String body) {
		mail(host, to, from, cc, bcc, title, body, null);
	}

	public Mail(String host, InternetAddress[] to, InternetAddress from, InternetAddress[] cc, InternetAddress[] bcc, String title, String body, String[] attachments) {
		mail(host, to, from, cc, bcc, title, body, convertFiles(attachments));
	}

	public Mail(String host, InternetAddress[] to, InternetAddress from, InternetAddress[] cc, InternetAddress[] bcc, String title, String body, File[] attachments) {
		mail(host, to, from, cc, bcc, title, body, attachments);
	}

	private void mail(String hostP, InternetAddress[] toP, InternetAddress fromP, InternetAddress[] ccP, InternetAddress[] bccP, String titleP, String bodyP, File[] attachmentsP) {
		this.host = hostP;
		this.to = toP;
		this.from = fromP;
		this.cc = ccP;
		this.bcc = bccP;
		this.title = (titleP != null ? titleP : "");
		this.body = (bodyP != null ? new StringBuffer(bodyP) : new StringBuffer());
		this.attachments = attachmentsP;
	}

	public String getHost() {
		return this.host != null ? this.host : "mail";
	}

	public String[] getTo() {
		return convert(this.to);
	}

	public String getFrom() {
		return convert(this.from);
	}

	public String[] getCc() {
		return convert(this.cc);
	}

	public String[] getBcc() {
		return convert(this.bcc);
	}

	public String getTitle() {
		return this.title;
	}

	public String getBody() {
		return this.body.toString();
	}

	public File[] getAttachments() {
		return this.attachments;
	}

	public InternetAddress[] getToAddress() {
		return this.to;
	}

	public InternetAddress getFromAddress() {
		return this.from;
	}

	public InternetAddress[] getCcAddress() {
		return this.cc;
	}

	public InternetAddress[] getBccAddress() {
		return this.bcc;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setFrom(String from) throws AddressException {
		this.from = convert(from);
	}

	public void setFrom(InternetAddress from) {
		this.from = from;
	}

	public void setTo(String[] to) throws AddressException {
		this.to = convert(to);
	}

	public void setTo(InternetAddress[] to) {
		this.to = to;
	}

	public void setCc(String[] cc) throws AddressException {
		this.cc = convert(cc);
	}

	public void setCc(InternetAddress[] cc) {
		this.cc = cc;
	}

	public void setBcc(String[] bcc) throws AddressException {
		this.bcc = convert(bcc);
	}

	public void setBcc(InternetAddress[] bcc) {
		this.bcc = bcc;
	}

	public void setTitle(String title) {
		this.title = (title != null ? title : "");
	}

	public void setBody(String body) {
		this.body = (body != null ? new StringBuffer(body) : new StringBuffer());
	}

	public void setAttachments(String[] attachments) {
		this.attachments = convertFiles(attachments);
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

	public void addTo(String toP) throws AddressException {
		this.to = add(this.to, toP);
	}

	public void addTo(InternetAddress toP) {
		this.to = add(this.to, toP);
	}

	public void addCc(String ccP) throws AddressException {
		this.cc = add(this.cc, ccP);
	}

	public void addCc(InternetAddress ccP) {
		this.cc = add(this.cc, ccP);
	}

	public void addBcc(String bccP) throws AddressException {
		this.bcc = add(this.bcc, bccP);
	}

	public void addBcc(InternetAddress bccP) {
		this.bcc = add(this.bcc, bccP);
	}

	public void addIntoBody(String partOfBody) {
		this.body.append(partOfBody);
	}

	public void addAttachment(String attachment) {
		this.attachments = add(this.attachments, attachment);
	}

	public void addAttachment(File attachment) {
		this.attachments = add(this.attachments, attachment);
	}

	public void send() throws MessagingException, SendFailedException {
		Mail.send(getHost(), this);
	}

	private InternetAddress[] convert(String[] toConvert) throws AddressException {
		if (toConvert == null)
			return null;

		InternetAddress[] toReturn = new InternetAddress[toConvert.length];
		for (int i = 0; i < toConvert.length; i++)
			toReturn[i] = convert(toConvert[i]);
		return toReturn;
	}

	private InternetAddress convert(String toConvert) throws AddressException {
		return toConvert != null ? new InternetAddress(toConvert) : null;
	}

	private String[] convert(InternetAddress[] toConvert) {
		if (toConvert == null)
			return null;

		String[] toReturn = new String[toConvert.length];
		for (int i = 0; i < toConvert.length; i++)
			toReturn[i] = convert(toConvert[i]);
		return toReturn;
	}

	private String convert(InternetAddress toConvert) {
		return toConvert != null ? toConvert.toString() : null;
	}

	private InternetAddress[] add(InternetAddress[] address, String addressToAdd) throws AddressException {
		return add(address, convert(addressToAdd));
	}

	private InternetAddress[] add(InternetAddress[] address, InternetAddress addressToAdd) {
		if (addressToAdd == null)
			return address;

		InternetAddress[] toReturn = new InternetAddress[address != null ? address.length + 1 : 1];
		for (int i = 0; (address != null) && (i < address.length); i++)
			toReturn[i] = address[i];
		toReturn[(toReturn.length - 1)] = addressToAdd;
		return toReturn;
	}

	private File[] add(File[] files, String fileToAdd) {
		return add(files, convertFile(fileToAdd));
	}

	private File[] add(File[] files, File fileToAdd) {
		if (fileToAdd == null)
			return files;

		File[] toReturn = new File[files != null ? files.length + 1 : 1];
		for (int i = 0; (files != null) && (i < files.length); i++)
			toReturn[i] = files[i];
		toReturn[(toReturn.length - 1)] = fileToAdd;
		return toReturn;
	}

	private File[] convertFiles(String[] files) {
		if (files == null)
			return null;

		File[] toReturn = new File[files != null ? files.length : 1];
		for (int i = 0; (files != null) && (i < files.length); i++)
			toReturn[i] = convertFile(files[i]);
		return toReturn;
	}

	private File convertFile(String file) {
		return new File(file);
	}

	public static final void send(String host, Mail mail) throws SendFailedException, MessagingException {
		String[] languages = {"fr"};
		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.socketFactory.port", "465");
	    p.put("mail.smtp.socketFactory.class",
	            "javax.net.ssl.SSLSocketFactory");
		
		Session session = Session.getInstance(p,  new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL_ADRESS, GMAIL_PWD);
            }
        });

		MimeMessage message = new MimeMessage(session);
		message.setContentLanguage(languages);

		message.setFrom(mail.getFromAddress());
		message.setRecipients(RecipientType.TO, mail.getToAddress());
		message.setRecipients(RecipientType.CC, mail.getCcAddress());
		message.setRecipients(RecipientType.BCC, mail.getBccAddress());

		message.setSubject(mail.getTitle(), "UTF-8");
		message.setSentDate(new Date());

		Multipart mp = new MimeMultipart();

		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setContentLanguage(languages);
		mbp.setContent(mail.getBody(), "text/html; charset=utf-8");
		mp.addBodyPart(mbp);

		for (int i = 0; (mail.getAttachments() != null) && (i < mail.getAttachments().length); i++) {
			mbp = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(mail.getAttachments()[i]);
			mbp.setDataHandler(new DataHandler(fds));
			mbp.setFileName(mail.getAttachments()[i].getName());
			mp.addBodyPart(mbp);
		}
		message.setContent(mp);
		
		Transport.send(message);
	}


	@Override
	public String toString() {
		return "Mail \n[host=" + this.host + ",\nfrom=" + this.from + ",\nto=" + Arrays.toString(this.to) + ",\ncc=" + Arrays.toString(this.cc) + ",\nbcc="
				+ Arrays.toString(this.bcc) + ",\ntitle=" + this.title + ",\nbody=" + this.body + ",\nattachments=" + Arrays.toString(this.attachments) + "]";
	}

}