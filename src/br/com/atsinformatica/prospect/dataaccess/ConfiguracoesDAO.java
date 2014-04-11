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

	/** Contexto da aplicação. */
	private static Context context;

	/**
	 * Instância estática da própria classe para
	 * implementação do padrão de projeto Singleton.
	 */
	private static ConfiguracoesDAO config;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "configuracoes";

	/**
	 * Classe estática com a finalidade de definir
	 * constantes mais acessíveis para nomes de colunas
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
	 * Somente é possível ter acesso a uma instância da
	 * classe através de seu método estático getToDoDao(Context).
	 * Assim, é possível gerenciar quantas instâncias únicas
	 * da classe são criadas.
	 * 
	 * @param ctx Contexto da aplicação.
	 */
	private ConfiguracoesDAO(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * Método de acesso à instância única da própria classe.
	 * 
	 * @param ctx Contexto da aplicação.
	 * @return Instância única da própria classe.
	 */
	public static ConfiguracoesDAO getConfiguracoesDAO(Context ctx) {
		context = ctx;
		if (config == null) {
			// Uma nova instância da classe só é criada
			// caso o contexto da aplicação ainda não
			// tenha sido definido
			config = new ConfiguracoesDAO(context);
		}
		return config;
	}

	@Override
	public List<Configuracoes> selectAll() {
		// getDB() é uma implementação da classe base que
		// usa o DbHelper para conseguir uma instância
		// acessível do banco de dados.
		SQLiteDatabase db = getDB();

		// Cursor para iteração sobre o resultado gerado
		Cursor c = null;

		try {
			// Nome das colunas que deverão ser devolvidas
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

			// Execução da consulta.
			// O resultado é um cursor para iteração sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					null);

			// Variável para armazenamento dos
			// resultados gerados pela consulta.
			List<Configuracoes> allConfigs = new ArrayList<Configuracoes>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que será populada pelos
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

					// Adiciona-se a nova instância à lista geral.
					allConfigs.add(config);

					// Itera enquanto houver um próximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso não haja resgistros armazenados.
			Log.d("ConfigDAO", "Retorno Lista"+allConfigs);
			return allConfigs;

		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Falha na leitura dos dados.", e);
		} finally {
			// Libera recursos para o sistema.
			// startManagingCursor(Cursor) só funciona
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
		// Processo semelhante ao método anterior
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

			// Column.ID + " = ?" corresponde ao critério de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de consulta. 
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
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();

		// Como não existe valor de retorno, não é necessário um Cursor

		try {
			// Variável que conterá os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Preparação do par coluna/valor para inserção.
			// _id é autoincrementável,
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

			// Inserção do(s) valor(es) na tabela específica.
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
			// "_id = ?" corresponde ao critério da exclusão.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de exclusão.
			db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(i) });
		} catch (Exception e) {
			Log.e("ConfiguracoesDAO", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}

	}

	@Override
	public void update(Configuracoes config) {
		// Processo semelhante ao método anterior
				SQLiteDatabase db = getDB();

				try {
					// Variável que conterá os valores a serem armazenados.
					ContentValues values = new ContentValues();

					// Preparação do par coluna/valor para inserção.
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

					// "_id = ?" corresponde ao critério da atualização.
					// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
					// valor(es) a ser(em) substituído(s) no critério de atualização.
					db.update(TABLE_NAME, values, "_id = ?",
							new String[] { String.valueOf(config.getId()) });
				} catch (Exception e) {
					Log.e("ClienteDao", TABLE_NAME + ": falha ao atualizar registro ", e);
				} finally {
					db.close();
				}

	}

}
