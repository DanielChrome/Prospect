package br.com.atsinformatica.prospect.models;

/**
 * Classe Configurações.
 * 
 * @author Daniel Costa
 * @version 1.0 06 de abril de 2014
 */
public class Configuracoes {
	private int id;
	private String  codResp;
    private String  enviaEmail;
    private String  urlimagem;
    private String  smtp;
    private int     porta;
    private String  email;
    private String  usuario;
    private String  senha;
    private String  ssl;
    
    public Configuracoes() {
		super();
		// TODO Auto-generated constructor stub
	}
	   
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodResp() {
		return codResp;
	}
	public void setCodResp(String codResp) {
		this.codResp = codResp;
	}
	public String getEnviaEmail() {
		return enviaEmail;
	}
	public void setEnviaEmail(String enviaEmail) {
		this.enviaEmail = enviaEmail;
	}
	public String getUrlimagem() {
		return urlimagem;
	}
	public void setUrlimagem(String urlimagem) {
		this.urlimagem = urlimagem;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSSL() {
		return ssl;
	}
	public void setSSL(String ssl) {
		this.ssl = ssl;
	}
    
    
}
