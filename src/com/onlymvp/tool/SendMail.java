package com.onlymvp.tool;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件发送工具类
 *
 * SMTP 参数从 classpath 下的 mail.properties 读取，
 * 未配置或配置不完整时仅记录日志，不抛出异常，保证主业务不受影响。
 *
 * chengzhi
 *
 */
public class SendMail {

	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);

	private static final String CONFIG_FILE = "mail.properties";

	/**
	 * 发送简单文本邮件
	 *
	 * @param tomail
	 *            目标邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件正文
	 */
	public static void simpleEmail(String tomail, String subject, String content) {

		Properties cfg = loadConfig();

		if (cfg == null) {
			logger.warn("未找到 " + CONFIG_FILE + " 配置文件，邮件发送已跳过（目标：" + tomail + "，主题：" + subject + "）");
			return;
		}

		String host = cfg.getProperty("mail.host");
		String port = cfg.getProperty("mail.port", "25");
		String from = cfg.getProperty("mail.from");
		String username = cfg.getProperty("mail.username");
		String password = cfg.getProperty("mail.password");
		String ssl = cfg.getProperty("mail.ssl", "false");

		if (StringTool.isNull(host) || StringTool.isNull(from)) {
			logger.warn(CONFIG_FILE + " 中未配置 mail.host / mail.from，邮件发送已跳过（目标：" + tomail + "，主题：" + subject + "）");
			return;
		}

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", StringTool.isNull(username) ? "false" : "true");

			if ("true".equals(ssl)) {
				props.put("mail.smtp.ssl.enable", "true");
			}

			Session session;

			if (!StringTool.isNull(username)) {
				props.put("mail.smtp.user", username);
				session = Session.getDefaultInstance(props, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
			} else {
				session = Session.getDefaultInstance(props, null);
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(tomail));
			message.setSubject(subject, "UTF-8");
			message.setText(content, "UTF-8");
			message.setSentDate(new Date());
			message.saveChanges();

			Transport.send(message);

			logger.info("缴费通知邮件发送成功：" + from + " -> " + tomail);

		} catch (Exception e) {
			logger.error("缴费通知邮件发送失败（目标：" + tomail + "）", e);
		}
	}

	/**
	 * 读取 classpath 下的邮件配置文件
	 *
	 * @return 配置不存在返回 null，存在返回 Properties
	 */
	private static Properties loadConfig() {

		InputStream in = SendMail.class.getClassLoader().getResourceAsStream(CONFIG_FILE);

		if (in == null) {
			return null;
		}

		try {
			Properties props = new Properties();
			props.load(in);
			return props;
		} catch (Exception e) {
			logger.error("读取邮件配置文件失败", e);
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception ignore) {
			}
		}
	}
}
