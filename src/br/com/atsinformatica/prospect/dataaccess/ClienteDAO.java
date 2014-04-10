package br.com.atsinformatica.prospect.dataaccess;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.atsinformatica.prospect.models.Cliente;

public class ClienteDAO extends Dao<Cliente>{

	/** Contexto da aplica��o. */
	private static Context context;

	/**
	 * Inst�ncia est�tica da pr�pria classe para
	 * implementa��o do padr�o de projeto Singleton.
	 */
	private static ClienteDAO cliente;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "cliente";

	/**
	 * Classe est�tica com a finalidade de definir
	 * constantes mais acess�veis para nomes de colunas
	 * da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID = "_id";
		public static String NOME = "NOME";
		public static String FANTASIA = "FANTASIA";
		public static String TIPOPESSOA = "TIPOPESSOA";
		public static String CPF = "CPF";
		public static String CNPJ = "CNPJ";	
		public static String IE = "INSCESTADUAL";
		public static String SEGMENTO = "SEGMENTO";
		public static String RESP = "RESPONSAVEL";
		public static String ENDERECO = "ENDERECO";
		public static String BAIRRO = "BAIRRO";
		public static String CIDADE = "CIDADE";
		public static String ESTADO = "ESTADO";
		public static String CEP = "CEP";
		public static String COMPL = "COMPLEMENTO";
		public static String NUMERO = "NUMERO";
	    public static String EMAIL1 = "EMAIL_PRIMARIO";
		public static String EMAIL2 = "EMAIL_SECUNDARIO";
		public static String FONE1 = "TELEFONE1";
		public static String FONE2 = "TELEFONE2";
		public static String CELULAR1 = "CELULAR1";
		public static String OPERADORA1 = "OPERADORA1";
		public static String CELULAR2 = "CELULAR2";
		public static String OPERADORA2 = "OPERADORA2";
		public static String FAX = "FAX";
		public static String WEBSITE = "WEBSITE";
		public static String CONTATORESP1 = "COTATORESP1";
		public static String CONTATORESP2 = "CONTATORESP2";
		public static String CONTATORESP3 = "CONTATORESP3";
		public static String OBS = "OBSERVACAO";
		public static String ORIGEM = "ORIGEM";
		public static String ENVIOEMAIL = "ENVIOEMAIL";
	}

	/**
	 * Construtor da classe.<br/>
	 * Somente � poss�vel ter acesso a uma inst�ncia da
	 * classe atrav�s de seu m�todo est�tico getToDoDao(Context).
	 * Assim, � poss�vel gerenciar quantas inst�ncias �nicas
	 * da classe s�o criadas.
	 * 
	 * @param ctx Contexto da aplica��o.
	 */
	private ClienteDAO(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * M�todo de acesso � inst�ncia �nica da pr�pria classe.
	 * 
	 * @param ctx Contexto da aplica��o.
	 * @return Inst�ncia �nica da pr�pria classe.
	 */
	public static ClienteDAO getClienteDAO(Context ctx) {
		context = ctx;
		if (cliente == null) {
			// Uma nova inst�ncia da classe s� � criada
			// caso o contexto da aplica��o ainda n�o
			// tenha sido definido
			cliente = new ClienteDAO(context);
		}
		return cliente;
	}

	@Override
	public List<Cliente> selectAll() {
		// getDB() � uma implementa��o da classe base que
		// usa o DbHelper para conseguir uma inst�ncia
		// acess�vel do banco de dados.
		SQLiteDatabase db = getDB();

		// Cursor para itera��o sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que dever�o ser devolvidas
			// como resultadoda da consulta
			String columns[] = new String[] { Column.ID, 
					Column.NOME,
					Column.FANTASIA,
					Column.TIPOPESSOA,
					Column.CPF,
					Column.CNPJ,
					Column.IE,
					Column.SEGMENTO,
					Column.RESP,
					Column.ENDERECO,
					Column.BAIRRO,
					Column.CIDADE,
					Column.ESTADO,
					Column.CEP,
					Column.COMPL,
					Column.NUMERO,
					Column.EMAIL1,
					Column.EMAIL2,
					Column.FONE1,
					Column.FONE2,
					Column.CELULAR1,
					Column.OPERADORA1,
					Column.CELULAR2,
					Column.OPERADORA2,
					Column.FAX,
					Column.WEBSITE,
					Column.CONTATORESP1,
					Column.CONTATORESP2,
					Column.CONTATORESP3,
					Column.OBS,
					Column.ORIGEM,
					Column.ENVIOEMAIL};

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					null);

			// Vari�vel para armazenamento dos
			// resultados gerados pela consulta.
			List<Cliente> allClientes = new ArrayList<Cliente>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que ser� populada pelos
					// dados retornados pela consulta
					Cliente cliente = new Cliente();
					cliente.setId(c.getInt(c.getColumnIndex(Column.ID)));
					cliente.setNome(c.getString(c.getColumnIndex(Column.NOME)));
					cliente.setNome_fantasia(c.getString(c.getColumnIndex(Column.FANTASIA)));
					cliente.setTipo_pessoa(c.getString(c.getColumnIndex(Column.TIPOPESSOA)));
					cliente.setCpf(c.getString(c.getColumnIndex(Column.CPF)));
					cliente.setCnpj(c.getString(c.getColumnIndex(Column.CNPJ)));
					cliente.setInsc_estadual(c.getString(c.getColumnIndex(Column.IE)));
					cliente.setSegmento(c.getString(c.getColumnIndex(Column.SEGMENTO)));
					cliente.setResponsavel(c.getString(c.getColumnIndex(Column.RESP)));
					cliente.setEndereco(c.getString(c.getColumnIndex(Column.ENDERECO)));
					cliente.setBairro(c.getString(c.getColumnIndex(Column.BAIRRO)));
					cliente.setCidade(c.getString(c.getColumnIndex(Column.CIDADE)));
					cliente.setEstado(c.getString(c.getColumnIndex(Column.ESTADO)));
					cliente.setCep(c.getString(c.getColumnIndex(Column.CEP)));
					cliente.setComplemento(c.getString(c.getColumnIndex(Column.COMPL)));
					cliente.setNumero(c.getString(c.getColumnIndex(Column.NUMERO)));
					cliente.setEmail_principal(c.getString(c.getColumnIndex(Column.EMAIL1)));
					cliente.setEmail_secundario(c.getString(c.getColumnIndex(Column.EMAIL2)));
					cliente.setTelefone(c.getString(c.getColumnIndex(Column.FONE1)));
					cliente.setTelefone2(c.getString(c.getColumnIndex(Column.FONE2)));
					cliente.setCelular(c.getString(c.getColumnIndex(Column.CELULAR1)));
					cliente.setOperadora(c.getString(c.getColumnIndex(Column.OPERADORA1)));
					cliente.setCelular2(c.getString(c.getColumnIndex(Column.CELULAR2)));
					cliente.setOperadora2(c.getString(c.getColumnIndex(Column.OPERADORA2)));
					cliente.setFax(c.getString(c.getColumnIndex(Column.FAX)));
					cliente.setWebsite(c.getString(c.getColumnIndex(Column.WEBSITE)));
					cliente.setContato_responsavel(c.getString(c.getColumnIndex(Column.CONTATORESP1)));
					cliente.setContato_responsavel2(c.getString(c.getColumnIndex(Column.CONTATORESP2)));
					cliente.setContato_responsavel3(c.getString(c.getColumnIndex(Column.CONTATORESP3)));
					cliente.setObservacoes(c.getString(c.getColumnIndex(Column.OBS)));
					cliente.setOrigem(c.getString(c.getColumnIndex(Column.ORIGEM)));
					cliente.setEmailEnviado(c.getString(c.getColumnIndex(Column.ENVIOEMAIL)));
					// Adiciona-se a nova inst�ncia � lista geral.
					allClientes.add(cliente);

					// Itera enquanto houver um pr�ximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso n�o haja resgistros armazenados.
			Log.d("ClienteDAO", "Retorno Lista"+allClientes);
			return allClientes;

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			// Libera recursos para o sistema.
			// startManagingCursor(Cursor) s� funciona
			// para o ciclo de vida de uma Activity!
			if (c != null) {
				c.close();
			}
			db.close();
		}

		// Garante que haja um valor de retorno
		return null;
	}
	
	public List<Cliente> selectNotSendEmail() {
		// getDB() � uma implementa��o da classe base que
		// usa o DbHelper para conseguir uma inst�ncia
		// acess�vel do banco de dados.
		SQLiteDatabase db = getDB();

		// Cursor para itera��o sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que dever�o ser devolvidas
			// como resultadoda da consulta
			String columns[] = new String[] { Column.ID, 
					Column.NOME,
					Column.FANTASIA,
					Column.TIPOPESSOA,
					Column.CPF,
					Column.CNPJ,
					Column.IE,
					Column.SEGMENTO,
					Column.RESP,
					Column.ENDERECO,
					Column.BAIRRO,
					Column.CIDADE,
					Column.ESTADO,
					Column.CEP,
					Column.COMPL,
					Column.NUMERO,
					Column.EMAIL1,
					Column.EMAIL2,
					Column.FONE1,
					Column.FONE2,
					Column.CELULAR1,
					Column.OPERADORA1,
					Column.CELULAR2,
					Column.OPERADORA2,
					Column.FAX,
					Column.WEBSITE,
					Column.CONTATORESP1,
					Column.CONTATORESP2,
					Column.CONTATORESP3,
					Column.OBS,
					Column.ORIGEM,
					Column.ENVIOEMAIL};

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, Column.ENVIOEMAIL + " <> ?", 
					new String[] { "S" }, null, null,
					null);

			// Vari�vel para armazenamento dos
			// resultados gerados pela consulta.
			List<Cliente> allClientes = new ArrayList<Cliente>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que ser� populada pelos
					// dados retornados pela consulta
					Cliente cliente = new Cliente();
					cliente.setId(c.getInt(c.getColumnIndex(Column.ID)));
					cliente.setNome(c.getString(c.getColumnIndex(Column.NOME)));
					cliente.setNome_fantasia(c.getString(c.getColumnIndex(Column.FANTASIA)));
					cliente.setTipo_pessoa(c.getString(c.getColumnIndex(Column.TIPOPESSOA)));
					cliente.setCpf(c.getString(c.getColumnIndex(Column.CPF)));
					cliente.setCnpj(c.getString(c.getColumnIndex(Column.CNPJ)));
					cliente.setInsc_estadual(c.getString(c.getColumnIndex(Column.IE)));
					cliente.setSegmento(c.getString(c.getColumnIndex(Column.SEGMENTO)));
					cliente.setResponsavel(c.getString(c.getColumnIndex(Column.RESP)));
					cliente.setEndereco(c.getString(c.getColumnIndex(Column.ENDERECO)));
					cliente.setBairro(c.getString(c.getColumnIndex(Column.BAIRRO)));
					cliente.setCidade(c.getString(c.getColumnIndex(Column.CIDADE)));
					cliente.setEstado(c.getString(c.getColumnIndex(Column.ESTADO)));
					cliente.setCep(c.getString(c.getColumnIndex(Column.CEP)));
					cliente.setComplemento(c.getString(c.getColumnIndex(Column.COMPL)));
					cliente.setNumero(c.getString(c.getColumnIndex(Column.NUMERO)));
					cliente.setEmail_principal(c.getString(c.getColumnIndex(Column.EMAIL1)));
					cliente.setEmail_secundario(c.getString(c.getColumnIndex(Column.EMAIL2)));
					cliente.setTelefone(c.getString(c.getColumnIndex(Column.FONE1)));
					cliente.setTelefone2(c.getString(c.getColumnIndex(Column.FONE2)));
					cliente.setCelular(c.getString(c.getColumnIndex(Column.CELULAR1)));
					cliente.setOperadora(c.getString(c.getColumnIndex(Column.OPERADORA1)));
					cliente.setCelular2(c.getString(c.getColumnIndex(Column.CELULAR2)));
					cliente.setOperadora2(c.getString(c.getColumnIndex(Column.OPERADORA2)));
					cliente.setFax(c.getString(c.getColumnIndex(Column.FAX)));
					cliente.setWebsite(c.getString(c.getColumnIndex(Column.WEBSITE)));
					cliente.setContato_responsavel(c.getString(c.getColumnIndex(Column.CONTATORESP1)));
					cliente.setContato_responsavel2(c.getString(c.getColumnIndex(Column.CONTATORESP2)));
					cliente.setContato_responsavel3(c.getString(c.getColumnIndex(Column.CONTATORESP3)));
					cliente.setObservacoes(c.getString(c.getColumnIndex(Column.OBS)));
					cliente.setOrigem(c.getString(c.getColumnIndex(Column.ORIGEM)));
					cliente.setEmailEnviado(c.getString(c.getColumnIndex(Column.ENVIOEMAIL)));
					// Adiciona-se a nova inst�ncia � lista geral.
					allClientes.add(cliente);

					// Itera enquanto houver um pr�ximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso n�o haja resgistros armazenados.
			Log.d("ClienteDAO", "Retorno Lista"+allClientes);
			return allClientes;

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			// Libera recursos para o sistema.
			// startManagingCursor(Cursor) s� funciona
			// para o ciclo de vida de uma Activity!
			if (c != null) {
				c.close();
			}
			db.close();
		}

		// Garante que haja um valor de retorno
		return null;
	}
	
	@Override
	public Cliente select(int i) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID, 
					Column.NOME,
					Column.FANTASIA,
					Column.TIPOPESSOA,
					Column.CPF,
					Column.CNPJ,
					Column.IE,
					Column.SEGMENTO,
					Column.RESP,
					Column.ENDERECO,
					Column.BAIRRO,
					Column.CIDADE,
					Column.ESTADO,
					Column.CEP,
					Column.COMPL,
					Column.NUMERO,
					Column.EMAIL1,
					Column.EMAIL2,
					Column.FONE1,
					Column.FONE2,
					Column.CELULAR1,
					Column.OPERADORA1,
					Column.CELULAR2,
					Column.OPERADORA2,
					Column.FAX,
					Column.WEBSITE,
					Column.CONTATORESP1,
					Column.CONTATORESP2,
					Column.CONTATORESP3,
					Column.OBS,
					Column.ORIGEM,
					Column.ENVIOEMAIL};

			// Column.ID + " = ?" corresponde ao crit�rio de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de consulta. 
			c = db.query(TABLE_NAME, columns, Column.ID + " = ?",
					new String[] { String.valueOf(i) }, null, null, null);

			Cliente cliente = new Cliente();

			if (c.moveToFirst()) {
				cliente.setId(c.getInt(c.getColumnIndex(Column.ID)));
				cliente.setNome(c.getString(c.getColumnIndex(Column.NOME)));
				cliente.setNome_fantasia(c.getString(c.getColumnIndex(Column.FANTASIA)));
				cliente.setTipo_pessoa(c.getString(c.getColumnIndex(Column.TIPOPESSOA)));
				cliente.setCpf(c.getString(c.getColumnIndex(Column.CPF)));
				cliente.setCnpj(c.getString(c.getColumnIndex(Column.CNPJ)));
				cliente.setInsc_estadual(c.getString(c.getColumnIndex(Column.IE)));
				cliente.setSegmento(c.getString(c.getColumnIndex(Column.SEGMENTO)));
				cliente.setResponsavel(c.getString(c.getColumnIndex(Column.RESP)));
				cliente.setEndereco(c.getString(c.getColumnIndex(Column.ENDERECO)));
				cliente.setBairro(c.getString(c.getColumnIndex(Column.BAIRRO)));
				cliente.setCidade(c.getString(c.getColumnIndex(Column.CIDADE)));
				cliente.setEstado(c.getString(c.getColumnIndex(Column.ESTADO)));
				cliente.setCep(c.getString(c.getColumnIndex(Column.CEP)));
				cliente.setComplemento(c.getString(c.getColumnIndex(Column.COMPL)));
				cliente.setNumero(c.getString(c.getColumnIndex(Column.NUMERO)));
				cliente.setEmail_principal(c.getString(c.getColumnIndex(Column.EMAIL1)));
				cliente.setEmail_secundario(c.getString(c.getColumnIndex(Column.EMAIL2)));
				cliente.setTelefone(c.getString(c.getColumnIndex(Column.FONE1)));
				cliente.setTelefone2(c.getString(c.getColumnIndex(Column.FONE2)));
				cliente.setCelular(c.getString(c.getColumnIndex(Column.CELULAR1)));
				cliente.setOperadora(c.getString(c.getColumnIndex(Column.OPERADORA1)));
				cliente.setCelular2(c.getString(c.getColumnIndex(Column.CELULAR2)));
				cliente.setOperadora2(c.getString(c.getColumnIndex(Column.OPERADORA2)));
				cliente.setFax(c.getString(c.getColumnIndex(Column.FAX)));
				cliente.setWebsite(c.getString(c.getColumnIndex(Column.WEBSITE)));
				cliente.setContato_responsavel(c.getString(c.getColumnIndex(Column.CONTATORESP1)));
				cliente.setContato_responsavel2(c.getString(c.getColumnIndex(Column.CONTATORESP2)));
				cliente.setContato_responsavel3(c.getString(c.getColumnIndex(Column.CONTATORESP3)));
				cliente.setObservacoes(c.getString(c.getColumnIndex(Column.OBS)));
				cliente.setOrigem(c.getString(c.getColumnIndex(Column.ORIGEM)));
				cliente.setEmailEnviado(c.getString(c.getColumnIndex(Column.ENVIOEMAIL)));
				return cliente;
			}

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			if (c != null) {
				c.close();
			}
			db.close();
		}

		return null;
	}

	@Override
	public void insert(Cliente cliente) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		// Como n�o existe valor de retorno, n�o � necess�rio um Cursor

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			// _id � autoincrement�vel,
			values.put(Column.NOME, cliente.getNome());
			values.put(Column.FANTASIA,cliente.getNome_fantasia());
			values.put(Column.TIPOPESSOA,cliente.getTipo_pessoa());
			values.put(Column.CPF,cliente.getCpf());
			values.put(Column.CNPJ,cliente.getCnpj());
			values.put(Column.IE,cliente.getInsc_estadual());
			values.put(Column.SEGMENTO,cliente.getSegmento());
			values.put(Column.RESP,cliente.getResponsavel());
			values.put(Column.ENDERECO,cliente.getEndereco());
			values.put(Column.BAIRRO,cliente.getBairro());
			values.put(Column.CIDADE,cliente.getCidade());
			values.put(Column.ESTADO,cliente.getEstado());
			values.put(Column.CEP,cliente.getCep());
			values.put(Column.COMPL,cliente.getComplemento());
			values.put(Column.NUMERO,cliente.getNumero());
			values.put(Column.EMAIL1,cliente.getEmail_principal());
			values.put(Column.EMAIL2,cliente.getEmail_secundario());
			values.put(Column.FONE1,cliente.getTelefone());
			values.put(Column.FONE2,cliente.getTelefone2());
			values.put(Column.CELULAR1,cliente.getCelular());
			values.put(Column.OPERADORA1,cliente.getOperadora());
		    values.put(Column.CELULAR2,cliente.getCelular2());
			values.put(Column.OPERADORA2,cliente.getOperadora2());
			values.put(Column.FAX,cliente.getFax());
			values.put(Column.WEBSITE,cliente.getWebsite());
		    values.put(Column.CONTATORESP1,cliente.getContato_responsavel());
			values.put(Column.CONTATORESP2,cliente.getContato_responsavel2());
		    values.put(Column.CONTATORESP3,cliente.getContato_responsavel3());
			values.put(Column.OBS,cliente.getObservacoes());
		    values.put(Column.ORIGEM,cliente.getOrigem());
		    values.put(Column.ENVIOEMAIL,cliente.getEmailEnviado());
			// Inser��o do(s) valor(es) na tabela espec�fica.
			db.insert(TABLE_NAME, null, values);
			Log.d("ClienteDAO", "Cliente inserido");
			getID(cliente);
		} catch (Exception e) {
			Log.e("ClienteDao",
					TABLE_NAME + ": falha ao inserir registro "
							+ cliente.getNome(), e);
		} finally {
			db.close();
		}

	}

	@Override
	public void delete(int i) {
		SQLiteDatabase db = getDB();

		try {
			// "_id = ?" corresponde ao crit�rio da exclus�o.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de exclus�o.
			db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(i) });
		} catch (Exception e) {
			Log.e("ClienteDAO", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}

	}

	@Override
	public void update(Cliente cliente) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			values.put(Column.NOME, cliente.getNome());
			values.put(Column.FANTASIA,cliente.getNome_fantasia());
			values.put(Column.TIPOPESSOA,cliente.getTipo_pessoa());
			values.put(Column.CPF,cliente.getCpf());
			values.put(Column.CNPJ,cliente.getCnpj());
			values.put(Column.IE,cliente.getInsc_estadual());
			values.put(Column.SEGMENTO,cliente.getSegmento());
			values.put(Column.RESP,cliente.getResponsavel());
			values.put(Column.ENDERECO,cliente.getEndereco());
			values.put(Column.BAIRRO,cliente.getBairro());
			values.put(Column.CIDADE,cliente.getCidade());
			values.put(Column.ESTADO,cliente.getEstado());
			values.put(Column.CEP,cliente.getCep());
			values.put(Column.COMPL,cliente.getComplemento());
			values.put(Column.NUMERO,cliente.getNumero());
			values.put(Column.EMAIL1,cliente.getEmail_principal());
			values.put(Column.EMAIL2,cliente.getEmail_secundario());
			values.put(Column.FONE1,cliente.getTelefone());
			values.put(Column.FONE2,cliente.getTelefone2());
			values.put(Column.CELULAR1,cliente.getCelular());
			values.put(Column.OPERADORA1,cliente.getOperadora());
		    values.put(Column.CELULAR2,cliente.getCelular2());
			values.put(Column.OPERADORA2,cliente.getOperadora2());
			values.put(Column.FAX,cliente.getFax());
			values.put(Column.WEBSITE,cliente.getWebsite());
		    values.put(Column.CONTATORESP1,cliente.getContato_responsavel());
			values.put(Column.CONTATORESP2,cliente.getContato_responsavel2());
		    values.put(Column.CONTATORESP3,cliente.getContato_responsavel3());
			values.put(Column.OBS,cliente.getObservacoes());
		    values.put(Column.ORIGEM,cliente.getOrigem());
		    values.put(Column.ENVIOEMAIL,cliente.getEmailEnviado());

			// "_id = ?" corresponde ao crit�rio da atualiza��o.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de atualiza��o.
			db.update(TABLE_NAME, values, "_id = ?",
					new String[] { String.valueOf(cliente.getId()) });
		} catch (Exception e) {
			Log.e("ClienteDao", TABLE_NAME + ": falha ao atualizar registro "
					+ cliente.getId(), e);
		} finally {
			db.close();
		}

	}
	
	public void getID(Cliente cliente) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID};
			String values[]  = new String[]{
					cliente.getNome(),
					cliente.getTipo_pessoa(),
					cliente.getCpf(),
					cliente.getCnpj(),
					cliente.getEmail_principal()};
 
			// Column.ID + " = ?" corresponde ao crit�rio de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de consulta. 
			c = db.query(TABLE_NAME, columns, Column.NOME + " = ? and "+
											  Column.TIPOPESSOA + " = ? and "+
											  Column.CPF + " = ? and "+
											  Column.CNPJ + " = ? and "+
											  Column.EMAIL1 + " = ? ",
					values, null, null, null);

			if (c.moveToFirst()) {
				cliente.setId(c.getInt(c.getColumnIndex(Column.ID)));
			}

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			if (c != null) {
				c.close();
			}
			db.close();
		}
	}

}
