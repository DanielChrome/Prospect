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

	/** Contexto da aplica��o. */
	private static Context context;

	/**
	 * Inst�ncia est�tica da pr�pria classe para
	 * implementa��o do padr�o de projeto Singleton.
	 */
	private static ControleEmailDAO emailDao;

	/** Nome da tabela sobre a qual esta classe atua */
	public static String TABLE_NAME = "controleemail";

	/**
	 * Classe est�tica com a finalidade de definir
	 * constantes mais acess�veis para nomes de colunas
	 * da tabela sobre a qual esta classe atual.
	 */
	public static final class Column {
		public static String ID = "_id";
		public static String CODCLIENTE = "CODCLIENTE";
		public static String EMAIlENVIADO = "EMAILENVIADO";
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
	private ControleEmailDAO(Context ctx) {
		super(context);
		context = ctx;
	}

	/**
	 * M�todo de acesso � inst�ncia �nica da pr�pria classe.
	 * 
	 * @param ctx Contexto da aplica��o.
	 * @return Inst�ncia �nica da pr�pria classe.
	 */
	public static ControleEmailDAO getCEmailDAO(Context ctx) {
		context = ctx;
		if (emailDao == null) {
			// Uma nova inst�ncia da classe s� � criada
			// caso o contexto da aplica��o ainda n�o
			// tenha sido definido
			emailDao = new ControleEmailDAO(context);
		}
		return emailDao;
	}

	@Override
	public List<ControleEmail> selectAll() {
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
					Column.CODCLIENTE,
					Column.EMAIlENVIADO};

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, null, null, null, null,
					null);

			// Vari�vel para armazenamento dos
			// resultados gerados pela consulta.
			List<ControleEmail> allEmails = new ArrayList<ControleEmail>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que ser� populada pelos
					// dados retornados pela consulta
					ControleEmail email = new ControleEmail();
					email.setId(c.getInt(c.getColumnIndex(Column.ID)));
					email.setCliente(ClienteDAO.getClienteDAO(context).select(c.getInt(c.getColumnIndex(Column.CODCLIENTE))));
					email.setEmailenviado(c.getString(c.getColumnIndex(Column.EMAIlENVIADO)));
					

					// Adiciona-se a nova inst�ncia � lista geral.
					allEmails.add(email);

					// Itera enquanto houver um pr�ximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso n�o haja resgistros armazenados.
			Log.d("ControleEmailDAO", "Retorno Lista"+allEmails);
			return allEmails;

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
	
	public List<ControleEmail> selectNotSend() {
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
					Column.CODCLIENTE,
					Column.EMAIlENVIADO};

			// Execu��o da consulta.
			// O resultado � um cursor para itera��o sobre o resultado.
			c = db.query(TABLE_NAME, columns, Column.EMAIlENVIADO + " = ?", 
					new String[] { "N" }, null, null,
					null);

			// Vari�vel para armazenamento dos
			// resultados gerados pela consulta.
			List<ControleEmail> allEmails = new ArrayList<ControleEmail>();

			// Se existe um primeiro registro...
			if (c.moveToFirst()) {
				do {
					// ... cria-se uma classe que ser� populada pelos
					// dados retornados pela consulta
					ControleEmail email = new ControleEmail();
					email.setId(c.getInt(c.getColumnIndex(Column.ID)));
					email.setCliente(ClienteDAO.getClienteDAO(context).select(c.getInt(c.getColumnIndex(Column.CODCLIENTE))));
					email.setEmailenviado(c.getString(c.getColumnIndex(Column.EMAIlENVIADO)));
					

					// Adiciona-se a nova inst�ncia � lista geral.
					allEmails.add(email);

					// Itera enquanto houver um pr�ximo registro.
				} while (c.moveToNext());
			}

			// Devolve a lista com todos os resgistros encontrados.
			// Pode ser nulo, caso n�o haja resgistros armazenados.
			Log.d("ControleEmailDAO", "Retorno Lista Emails"+allEmails);
			return allEmails;

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
	public ControleEmail select(int i) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();
		Cursor c = null;

		try {
			String columns[] = new String[] { Column.ID, 
					Column.CODCLIENTE,
					Column.EMAIlENVIADO};

			// Column.ID + " = ?" corresponde ao crit�rio de consulta.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de consulta. 
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
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		// Como n�o existe valor de retorno, n�o � necess�rio um Cursor

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			// _id � autoincrement�vel,
			values.put(Column.CODCLIENTE,email.getCliente().getId() );
			values.put(Column.EMAIlENVIADO,email.getEmailenviado());

			// Inser��o do(s) valor(es) na tabela espec�fica.
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
			// "_id = ?" corresponde ao crit�rio da exclus�o.
			// new String[] { String.valueOf(i) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de exclus�o.
			db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(i) });
		} catch (Exception e) {
			Log.e("ControleEmailDAO", TABLE_NAME + ": falha ao excluir registro " + i, e);
		} finally {
			db.close();
		}

	}

	@Override
	public void update(ControleEmail email) {
		// Processo semelhante ao m�todo anterior
		SQLiteDatabase db = getDB();

		try {
			// Vari�vel que conter� os valores a serem armazenados.
			ContentValues values = new ContentValues();

			// Prepara��o do par coluna/valor para inser��o.
			values.put(Column.CODCLIENTE,email.getCliente().getId() );
			values.put(Column.EMAIlENVIADO,email.getEmailenviado());;

			// "_id = ?" corresponde ao crit�rio da atualiza��o.
			// new String[] { String.valueOf(todo.getId()) } corresponde ao(s)
			// valor(es) a ser(em) substitu�do(s) no crit�rio de atualiza��o.
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
