package br.com.atsinformatica.prospect.dataaccess;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.atsinformatica.prospect.models.Configuracoes;

public class ConfiguracoesDAO extends Dao<Configuracoes>{

	/** Contexto da aplica��o. */
	private static Context context;

	/**
	 * Inst�ncia est�tica da pr�pria classe para
	 * implementa��o do padr�o de projeto Singleton.
	 */
	private static ConfiguracoesDAO config;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "configuracoes";

	/**
	 * Classe est�tica com a finalidade de definir
	 * constantes mais acess�veis para nomes de colunas
	 * da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID         = "_id";
		public static String CODRESP    = "CODRESP";
		public static String ENVIAEMAIL = "ENVIAEMAIL";
		public static String URLIMAGEM  = "URLIMAGEM";
		public static String LINKIMAGEM = "LINKIMAGEM";
		public static String ASSUNTO    = "ASSUNTO";
		public static String SMTP       = "SMTP";
		public static String PORTA      = "PORTA";
		public static String EMAIL      = "EMAIL";
		public static String USUARIO    = "USUARIO";
		public static String SENHA      = "SENHA";
		public static String SSL        = "SSL";
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
	private ConfiguracoesDAO(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * M�todo de acesso � inst�ncia �nica da pr�pria classe.
	 * 
	 * @param ctx Contexto da aplica��o.
	 * @return Inst�ncia �nica da pr�pria classe.
	 */
	public static ConfiguracoesDAO getConfiguracoesDAO(Context ctx) {
		context = ctx;
		if (config == null) {
			// Uma nova inst�ncia da classe s� � criada
			// caso o contexto da aplica��o ainda n�o
			// tenha sido definido
			config = new ConfiguracoesDAO(context);
		}
		return config;
	}

	@Override
	public List<Configuracoes> selectAll() {
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
					Column.CODRESP,
					Column.ENVIAEMAIL,
					Column.URLIMAGEM,
					Column.LINKIMAGEM,
					Column.ASSUNTO,
					Column.SMTP,
					Column.PORTA,
					Column.EMAIL,
					Column.USUARIO,
					Column.SENHA,
					Column.SSL};

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					null);

			// Vari�vel para armazenamento dos
			// resultados gerados pela consulta.
			List<Configuracoes> allConfigs = new ArrayList<Configuracoes>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que ser� populada pelos
					// dados retornados pela consulta
					Configuracoes config = new Configuracoes();
					config.setId(c.getInt(c.getColumnIndex(Column.ID)));
					config.setCodResp(c.getString(c.getColumnIndex(Column.CODRESP)));
					config.setEnviaEmail(c.getString(c.getColumnIndex(Column.ENVIAEMAIL)));
					config.setUrlimagem(c.getString(c.getColumnIndex(Column.URLIMAGEM)));
					config.setLinkimagem(c.getString(c.getColumnIndex(Column.LINKIMAGEM)));
					config.setAssuntoemail(c.getString(c.getColumnIndex(Column.ASSUNTO)));
					config.setSmtp(c.getString(c.getColumnIndex(Column.SMTP)));
					config.setPorta(c.getInt(c.getColumnIndex(Column.PORTA)));
					config.setEmail(c.getString(c.getColumnIndex(Column.EMAIL)));
					config.setUsuario(c.getString(c.getColumnIndex(Column.USUARIO)));
					config.setSenha(c.getString(c.getColumnIndex(Column.SENHA)));
					config.setSSL(c.getString(c.getColumnIndex(Column.SSL)));

					// Adiciona-se a nova inst�ncia � lista geral.
					allConfigs.add(config);

					// Itera enquanto houver um pr�ximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso n�o haja resgistros armazenados.
			Log.d("ConfigDAO", "Retorno Lista"+allConfigs);
			return allConfigs;

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
	public Configuracoes select(int i) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID, 
					Column.CODRESP,
					Column.ENVIAEMAIL,
					Column.URLIMAGEM,
					Column.LINKIMAGEM,
					Column.ASSUNTO,
					Column.SMTP,
					Column.PORTA,
					Column.EMAIL,
					Column.USUARIO,
					Column.SENHA,
					Column.SSL};

			// Column.ID + " = ?" corresponde ao crit�rio de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de consulta. 
			c = db.query(TABLE_NAME, columns, Column.ID + " = ?",
					new String[] { String.valueOf(i) }, null, null, null);

			Configuracoes config = new Configuracoes();

			if (c.moveToFirst()) {
				config.setId(c.getInt(c.getColumnIndex(Column.ID)));
				config.setCodResp(c.getString(c.getColumnIndex(Column.CODRESP)));
				config.setEnviaEmail(c.getString(c.getColumnIndex(Column.ENVIAEMAIL)));
				config.setUrlimagem(c.getString(c.getColumnIndex(Column.URLIMAGEM)));
				config.setLinkimagem(c.getString(c.getColumnIndex(Column.LINKIMAGEM)));
				config.setAssuntoemail(c.getString(c.getColumnIndex(Column.ASSUNTO)));
				config.setSmtp(c.getString(c.getColumnIndex(Column.SMTP)));
				config.setPorta(c.getInt(c.getColumnIndex(Column.PORTA)));
				config.setEmail(c.getString(c.getColumnIndex(Column.EMAIL)));
				config.setUsuario(c.getString(c.getColumnIndex(Column.USUARIO)));
				config.setSenha(c.getString(c.getColumnIndex(Column.SENHA)));
				config.setSSL(c.getString(c.getColumnIndex(Column.SSL)));
				return config;
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
	public void insert(Configuracoes config) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		// Como n�o existe valor de retorno, n�o � necess�rio um Cursor

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			// _id � autoincrement�vel,
			values.put(Column.CODRESP, config.getCodResp());
			values.put(Column.ENVIAEMAIL,config.getEnviaEmail());
			values.put(Column.URLIMAGEM,config.getUrlimagem());
			values.put(Column.LINKIMAGEM, config.getLinkimagem());
			values.put(Column.ASSUNTO, config.getAssuntoemail());
			values.put(Column.SMTP,config.getSmtp());
			values.put(Column.PORTA,config.getPorta());
			values.put(Column.EMAIL,config.getEmail());
			values.put(Column.USUARIO,config.getUsuario());
			values.put(Column.SENHA,config.getSenha());
			values.put(Column.SSL, config.getSSL());

			// Inser��o do(s) valor(es) na tabela espec�fica.
			db.insert(TABLE_NAME, null, values);
			Log.d("ConfiguracoesDAO", "Cliente inserido");
		} catch (Exception e) {
			Log.e("ConfiguracoesDAO",
					TABLE_NAME + ": falha ao inserir registro ", e);
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
			Log.e("ConfiguracoesDAO", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}

	}

	@Override
	public void update(Configuracoes config) {
		// Processo semelhante ao m�todo anterior
				SQLiteDatabase db = getDB();

				try {
					// Vari�vel que conter� os valores a serem armazenados.
					ContentValues values = new ContentValues();

					// Prepara��o do par coluna/valor para inser��o.
					values.put(Column.CODRESP, config.getCodResp());
					values.put(Column.ENVIAEMAIL,config.getEnviaEmail());
					values.put(Column.URLIMAGEM,config.getUrlimagem());
					values.put(Column.LINKIMAGEM, config.getLinkimagem());
					values.put(Column.ASSUNTO, config.getAssuntoemail());
					values.put(Column.SMTP,config.getSmtp());
					values.put(Column.PORTA,config.getPorta());
					values.put(Column.EMAIL,config.getEmail());
					values.put(Column.USUARIO,config.getUsuario());
					values.put(Column.SENHA,config.getSenha());
					values.put(Column.SSL, config.getSSL());

					// "_id = ?" corresponde ao crit�rio da atualiza��o.
					// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
					// valor(es) a ser(em) substitu�do(s) no crit�rio de atualiza��o.
					db.update(TABLE_NAME, values, "_id = ?",
							new String[] { String.valueOf(config.getId()) });
				} catch (Exception e) {
					Log.e("ClienteDao", TABLE_NAME + ": falha ao atualizar registro ", e);
				} finally {
					db.close();
				}

	}

}
