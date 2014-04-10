package br.com.atsinformatica.prospect.dataaccess;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.atsinformatica.prospect.models.ControleEmail;

public class ControleEmailDAO extends Dao<ControleEmail>{

	/** Contexto da aplicação. */
	private static Context context;

	/**
	 * Instância estática da própria classe para
	 * implementação do padrão de projeto Singleton.
	 */
	private static ControleEmailDAO emailDao;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "controleemail";

	/**
	 * Classe estática com a finalidade de definir
	 * constantes mais acessíveis para nomes de colunas
	 * da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID = "_id";
		public static String CODCLIENTE = "CODCLIENTE";
		public static String EMAIlENVIADO = "EMAILENVIADO";
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
	private ControleEmailDAO(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * Método de acesso à instância única da própria classe.
	 * 
	 * @param ctx Contexto da aplicação.
	 * @return Instância única da própria classe.
	 */
	public static ControleEmailDAO getCEmailDAO(Context ctx) {
		context = ctx;
		if (emailDao == null) {
			// Uma nova instância da classe só é criada
			// caso o contexto da aplicação ainda não
			// tenha sido definido
			emailDao = new ControleEmailDAO(context);
		}
		return emailDao;
	}

	@Override
	public List<ControleEmail> selectAll() {
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
					Column.CODCLIENTE,
					Column.EMAIlENVIADO};

			// Execução da consulta.
			// O resultado é um cursor para iteração sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					null);

			// Variável para armazenamento dos
			// resultados gerados pela consulta.
			List<ControleEmail> allEmails = new ArrayList<ControleEmail>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que será populada pelos
					// dados retornados pela consulta
					ControleEmail email = new ControleEmail();
					email.setId(c.getInt(c.getColumnIndex(Column.ID)));
					email.setCliente(ClienteDAO.getClienteDAO(context).select(c.getInt(c.getColumnIndex(Column.CODCLIENTE))));
					email.setEmailenviado(c.getString(c.getColumnIndex(Column.EMAIlENVIADO)));
					

					// Adiciona-se a nova instância à lista geral.
					allEmails.add(email);

					// Itera enquanto houver um próximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso não haja resgistros armazenados.
			Log.d("ControleEmailDAO", "Retorno Lista"+allEmails);
			return allEmails;

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
	
	public List<ControleEmail> selectNotSend() {
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
					Column.CODCLIENTE,
					Column.EMAIlENVIADO};

			// Execução da consulta.
			// O resultado é um cursor para iteração sobre o resultado.
			c = db.query(TABLE_NAME, columns, Column.EMAIlENVIADO + " = ?", 
					new String[] { "N" }, null, null,
					null);

			// Variável para armazenamento dos
			// resultados gerados pela consulta.
			List<ControleEmail> allEmails = new ArrayList<ControleEmail>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que será populada pelos
					// dados retornados pela consulta
					ControleEmail email = new ControleEmail();
					email.setId(c.getInt(c.getColumnIndex(Column.ID)));
					email.setCliente(ClienteDAO.getClienteDAO(context).select(c.getInt(c.getColumnIndex(Column.CODCLIENTE))));
					email.setEmailenviado(c.getString(c.getColumnIndex(Column.EMAIlENVIADO)));
					

					// Adiciona-se a nova instância à lista geral.
					allEmails.add(email);

					// Itera enquanto houver um próximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso não haja resgistros armazenados.
			Log.d("ControleEmailDAO", "Retorno Lista Emails"+allEmails);
			return allEmails;

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
	public ControleEmail select(int i) {
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID, 
					Column.CODCLIENTE,
					Column.EMAIlENVIADO};

			// Column.ID + " = ?" corresponde ao critério de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de consulta. 
			c = db.query(TABLE_NAME, columns, Column.ID + " = ?",
					new String[] { String.valueOf(i) }, null, null, null);

			ControleEmail email = new ControleEmail();

			if (c.moveToFirst()) {
				
				email.setId(c.getInt(c.getColumnIndex(Column.ID)));
				email.setCliente(ClienteDAO.getClienteDAO(context).select(c.getInt(c.getColumnIndex(Column.CODCLIENTE))));
				email.setEmailenviado(c.getString(c.getColumnIndex(Column.EMAIlENVIADO)));
				return email;
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
	public void insert(ControleEmail email) {
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();

		// Como não existe valor de retorno, não é necessário um Cursor

		try {
			// Variável que conterá os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Preparação do par coluna/valor para inserção.
			// _id é autoincrementável,
			values.put(Column.CODCLIENTE,email.getCliente().getId() );
			values.put(Column.EMAIlENVIADO,email.getEmailenviado());

			// Inserção do(s) valor(es) na tabela específica.
			db.insert(TABLE_NAME, null, values);
			Log.d("ControleEmailDAO", "Email inserido na fila");
		} catch (Exception e) {
			Log.e("ControleEmailDAO",
					TABLE_NAME + ": falha ao inserir registro "
							+ email, e);
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
			Log.e("ControleEmailDAO", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}

	}

	@Override
	public void update(ControleEmail email) {
		// Processo semelhante ao método anterior
		SQLiteDatabase db = getDB();

		try {
			// Variável que conterá os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Preparação do par coluna/valor para inserção.
			values.put(Column.CODCLIENTE,email.getCliente().getId() );
			values.put(Column.EMAIlENVIADO,email.getEmailenviado());;

			// "_id = ?" corresponde ao critério da atualização.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substituído(s) no critério de atualização.
			db.update(TABLE_NAME, values, "_id = ?",
					new String[] { String.valueOf(email.getId()) });
		} catch (Exception e) {
			Log.e("ClienteDao", TABLE_NAME + ": falha ao atualizar registro "
					+ email.getId(), e);
		} finally {
			db.close();
		}

	}

}
