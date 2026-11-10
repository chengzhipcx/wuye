package com.onlymvp.service;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * 邮件发送服务(缴费通知)
 *
 * application.yml 中 spring.mail.host 留空时自动跳过发送, 不影响主业务。
 *
 * chengzhi
 *
 */
@Service
public class MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	private final ObjectProvider<JavaMailSender> mailSenderProvider;

	@Value("${spring.mail.host:}")
	private String host;

	@Value("${app.mail.from:}")
	private String from;

	@Value("${spring.mail.username:}")
	private String username;

	public MailService(ObjectProvider<JavaMailSender> mailSenderProvider) {
		this.mailSenderProvider = mailSenderProvider;
	}

	/**
	 * 发送简单文本邮件
	 *
	 * @param toMail
	 *            目标邮箱
	 * @param subject
	 *            主题
	 * @param content
	 *            正文
	 */
	public void sendSimpleEmail(String toMail, String subject, String content) {

		JavaMailSender mailSender = mailSenderProvider.getIfAvailable();

		if (mailSender == null || host == null || host.trim().isEmpty() || from == null || from.trim().isEmpty()) {
			logger.warn("未配置 SMTP(spring.mail.host / spring.mail.from), 邮件发送已跳过(目标: " + toMail + ", 主题: " + subject
					+ ")");
			return;
		}

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
			helper.setFrom(username != null && !username.trim().isEmpty() ? username : from);
			helper.setTo(toMail);
			helper.setSubject(subject);
			helper.setText(content);
			mailSender.send(message);
			logger.info("缴费通知邮件发送成功: " + from + " -> " + toMail);
		} catch (Exception e) {
			logger.error("缴费通知邮件发送失败(目标: " + toMail + ")", e);
		}
	}
}
