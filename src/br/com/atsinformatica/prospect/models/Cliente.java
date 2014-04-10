package br.com.atsinformatica.prospect.models;

/**
 * Classe Cliente.
 * 
 * @author Daniel Costa
 * @version 1.0 06 de abril de 2014
 */
public class Cliente {
    private int    id;
	//Principal
    private String nome;
    private String nome_fantasia;
    private String tipo_pessoa;
    private String cpf;
    private String cnpj;
    private String insc_estadual;
    private String segmento;
    private String responsavel;
	
	//Endereço
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String numero;
	
	//Contato
    private String email_principal;
    private String email_secundario;
    private String telefone;
    private String telefone2;
    private String celular;
    private String operadora;
    private String celular2;
    private String operadora2;
    private String fax;
    private String website;
    private String contato_responsavel;
    private String contato_responsavel2;
    private String contato_responsavel3;
    private String observacoes;
    private String origem;
	
	//extra
    private String emailenviado;
    
	public Cliente() {
		super();
		nome = "";
	    nome_fantasia = "";
	    tipo_pessoa = "";
	    cpf = "";
	    cnpj = "";;
	    insc_estadual = "";
	    segmento = "";
	    responsavel = "";
	    endereco = "";
	    bairro = "";
	    cidade = "";
	    estado = "";
	    cep = "";
	    complemento = "";
	    numero = "";
	    email_principal= "";
	    email_secundario= "";
	    telefone= "";
	    telefone2= "";
	    celular= "";
	    operadora= "";
	    celular2= "";
	    operadora2= "";
	    fax= "";
	    website= "";
	    contato_responsavel= "";
	    contato_responsavel2= "";
	    contato_responsavel3= "";
	    observacoes= "";
        origem= "";
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome_fantasia() {
		return nome_fantasia;
	}
	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}
	public String getTipo_pessoa() {
		return tipo_pessoa;
	}
	public void setTipo_pessoa(String tipo_pessoa) {
		this.tipo_pessoa = tipo_pessoa;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getInsc_estadual() {
		return insc_estadual;
	}
	public void setInsc_estadual(String insc_estadual) {
		this.insc_estadual = insc_estadual;
	}
	public String getSegmento() {
		return segmento;
	}
	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEmail_principal() {
		return email_principal;
	}
	public void setEmail_principal(String email_principal) {
		this.email_principal = email_principal;
	}
	public String getEmail_secundario() {
		return email_secundario;
	}
	public void setEmail_secundario(String email_secundario) {
		this.email_secundario = email_secundario;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getOperadora() {
		return operadora;
	}
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}
	public String getCelular2() {
		return celular2;
	}
	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}
	public String getOperadora2() {
		return operadora2;
	}
	public void setOperadora2(String operadora2) {
		this.operadora2 = operadora2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getContato_responsavel() {
		return contato_responsavel;
	}
	public void setContato_responsavel(String contato_responsavel) {
		this.contato_responsavel = contato_responsavel;
	}
	public String getContato_responsavel2() {
		return contato_responsavel2;
	}
	public void setContato_responsavel2(String contato_responsavel2) {
		this.contato_responsavel2 = contato_responsavel2;
	}
	public String getContato_responsavel3() {
		return contato_responsavel3;
	}
	public void setContato_responsavel3(String contato_responsavel3) {
		this.contato_responsavel3 = contato_responsavel3;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getEmailEnviado() {
		return emailenviado;
	}
	public void setEmailEnviado(String emailenviado) {
		this.emailenviado = emailenviado;
	}

	@Override
	public String toString() {
		return this.id + "-" +this.nome;
	}
}
