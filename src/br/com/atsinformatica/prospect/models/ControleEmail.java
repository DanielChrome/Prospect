package br.com.atsinformatica.prospect.models;

public class ControleEmail {
	private int id;
	private Cliente cliente;
	private String emailenviado;
		
	public ControleEmail() {
	
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getEmailenviado() {
		return emailenviado;
	}
	public void setEmailenviado(String emailenviado) {
		this.emailenviado = emailenviado;
	}


}
