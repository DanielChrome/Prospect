package br.com.atsinformatica.prospect.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties; 
import javax.activation.CommandMap;  
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap; 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 

import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import br.com.atsinformatica.prospect.dataaccess.ConfiguracoesDAO;
import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.models.Configuracoes;

import br.com.atsinformatica.prospect.R;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Classe de gerenciamento de emails.
 * 
 * @author Daniel Costa
 * @version 1.0 06 de abril de 2014
 */
public class Mail extends javax.mail.Authenticator { 
	private Context ctx; 
	private Cliente cliente;
	private String _user; 
	private String _pass; 

	private String[] _to; 
	private String _from; 

	private String _port; 
	private String _sport; 

	private String _host;
	private boolean _ssl;

	private String _subject;
	private String _IMGLINK;
	private String _body; 

	private boolean _auth; 

	private boolean _debuggable; 

	private Multipart _multipart; 


	public Mail(Context ctx) { 
		this.ctx = ctx;

		Configuracoes config = new Configuracoes();
		config = ConfiguracoesDAO.getConfiguracoesDAO(this.ctx).select(1);
		_host = config.getSmtp(); // default smtp server 
		_port = Integer.toString(config.getPorta()); // default smtp port 
		_sport = Integer.toString(config.getPorta()); // default socketfactory port 
		_ssl   = ("S".equals(config.getSSL()));
		
		_IMGLINK = 
				"<a href=\""+config.getLinkimagem()+"\" target=\"_blank\"> " +
				"   <img width=\"539\" height=\"549\" border=\"0\" align=\"center\"  src=\""+config.getUrlimagem()+" \"/> " +
				"</a>";
		
		_user = config.getUsuario(); // username 
		_pass = config.getSenha(); // password 
		_from = config.getEmail(); // email sent from 
		_subject = config.getAssuntoemail(); // email subject 
		_body = "<html>      "
                +"   <head>   "
                +"      </head>   "
                +"      <body>    "
                + _IMGLINK 
                +"  <br /> "+R.string.app_name + " - "+R.string.version
                +"      </body>   "
                +"</html>"; // email body 

		_debuggable = false; // debug mode on or off - default off 
		_auth = true; // smtp authentication - default on 

		_multipart = new MimeMultipart(); 

		// There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added. 
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
		//mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		//mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		//mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		//mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
		CommandMap.setDefaultCommandMap(mc); 
	} 

	public void sendMail(Cliente cli) {
		Session session = createSessionObject();
		cliente = cli;
		try {
			Message message = createMessage(cli.getEmail_principal(), _subject, _body, session);
			new SendMailTask().execute(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(messageBody.getBytes(), "text/html"));
		message.setFrom(new InternetAddress(_from, "ATS Informática"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
		message.setSubject(subject);
		message.setDataHandler(handler);
		return message;
	}

	private Session createSessionObject() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", (Boolean.toString(_ssl)));
		properties.put("mail.smtp.host", _host);
		properties.put("mail.smtp.port", _port);

		return Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(_user, _pass);
			}
		});
	}

	private class SendMailTask extends AsyncTask<Message, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("AsyncTasck", "Executando AsyncTasck");
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Log.d("AsyncTasck", "Finalizando AsyncTasck");
		}

		@Override
		protected Void doInBackground(Message... messages) {
			try {
				Log.d("AsyncTasck", "Tentando envio");
				Transport.send(messages[0]);
				Log.d("AsyncTasck", "Envio concluido");
				cliente.setEmailEnviado("S");
				ClienteDAO.getClienteDAO(ctx).update(cliente);
				Utility.enviaNotificacao(ctx, 1,0);
			} catch (MessagingException e) {
				Log.d("AsyncTasck", "Erro inesperado :P"+e);
				e.printStackTrace();
			}
			return null;
		}
	}

	public class ByteArrayDataSource implements DataSource {   
        private byte[] data;   
        private String type;   

        public ByteArrayDataSource(byte[] data, String type) {   
            super();   
            this.data = data;   
            this.type = type;   
        }   

        public ByteArrayDataSource(byte[] data) {   
            super();   
            this.data = data;   
        }   

        public void setType(String type) {   
            this.type = type;   
        }   

        public String getContentType() {   
            if (type == null)   
                return "application/octet-stream";   
            else  
                return type;   
        }   

        public InputStream getInputStream() throws IOException {   
            return new ByteArrayInputStream(data);   
        }   

        public String getName() {   
            return "ByteArrayDataSource";   
        }   

        public OutputStream getOutputStream() throws IOException {   
            throw new IOException("Not Supported");   
        }   
    }
	
	// the getters and setters 
	public void setContext(Context ctx){
		this.ctx = ctx;
	}
	
	public String getBody() { 
		return _body; 
	} 

	public void setBody(String _body) { 
		this._body = _body; 
	}

	public String get_user() {
		return _user;
	}

	public void set_user(String _user) {
		this._user = _user;
	}

	public String get_pass() {
		return _pass;
	}

	public void set_pass(String _pass) {
		this._pass = _pass;
	}

	public String[] get_to() {
		return _to;
	}

	public void set_to(String[] _to) {
		this._to = _to;
	}

	public String get_from() {
		return _from;
	}

	public void set_from(String _from) {
		this._from = _from;
	}

	public String get_port() {
		return _port;
	}

	public void set_port(String _port) {
		this._port = _port;
	}

	public String get_sport() {
		return _sport;
	}

	public void set_sport(String _sport) {
		this._sport = _sport;
	}

	public String get_host() {
		return _host;
	}

	public void set_host(String _host) {
		this._host = _host;
	}

	public String get_subject() {
		return _subject;
	}

	public void set_subject(String _subject) {
		this._subject = _subject;
	}

	public String get_body() {
		return _body;
	}

	public void set_body(String _body) {
		this._body = _body;
	}

	public boolean is_auth() {
		return _auth;
	}

	public void set_auth(boolean _auth) {
		this._auth = _auth;
	}

	public boolean is_debuggable() {
		return _debuggable;
	}

	public void set_debuggable(boolean _debuggable) {
		this._debuggable = _debuggable;
	}

	public Multipart get_multipart() {
		return _multipart;
	}

	public void set_multipart(Multipart _multipart) {
		this._multipart = _multipart;
	} 

	// more of the getters and setters ….. 


}