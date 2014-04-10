package br.com.atsinformatica.prospect.dataaccess;

import android.content.Context;
import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Assistente de criação do banco de dados inicial do sistema.
 * Opcionalmente criam-se uma população de dados inicial.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class DbHelper extends SQLiteOpenHelper {

	/**
	 * Construtor da classe
	 * 
	 * @param context Contexto da aplicação
	 * @param name Nome do banco de dados a ser criado
	 * @param factory Interface que permite retornar novos cursores após consultas
	 * @param version Versão do banco de dados a ser criado
	 */
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * Método invocado pelo Android a fim de executar
	 * a criação de um novo banco de dados
	 * 
	 * @param db Nome do banco de dados fornecido pelo Android 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			String sql = "CREATE TABLE " + ClienteDAO.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ " NOME TEXT NOT NULL,                              "
					+ " FANTASIA TEXT ,                                  "
					+ " TIPOPESSOA TEXT NOT NULL,                        "
					+ " CPF TEXT,                                        "
					+ " CNPJ TEXT,                                       "
					+ " INSCESTADUAL TEXT,                               "
					+ " SEGMENTO TEXT,                                   "
					+ " RESPONSAVEL TEXT,                                "
					+ " ENDERECO TEXT,                                   "
					+ " BAIRRO TEXT,                                     "
					+ " CIDADE TEXT,                                     "
					+ " ESTADO TEXT,                                     "
					+ " CEP TEXT,                                        "
					+ " COMPLEMENTO TEXT,                                "
					+ " NUMERO TEXT,                                     "
					+ " EMAIL_PRIMARIO TEXT,                             "
					+ " EMAIL_SECUNDARIO TEXT,                           "
					+ " TELEFONE1 TEXT,                                  "
					+ " TELEFONE2 TEXT,                                  "
					+ " CELULAR1 TEXT,                                   "
					+ " OPERADORA1 TEXT,                                 "
					+ " CELULAR2 TEXT,                                   "
					+ " OPERADORA2 TEXT,                                 "
					+ " FAX TEXT,                                        "
					+ " WEBSITE TEXT,                                    "
					+ " COTATORESP1 TEXT,                                "
					+ " CONTATORESP2 TEXT,                               "
					+ " CONTATORESP3 TEXT,                               "
					+ " OBSERVACAO TEXT,                                 "
					+ " ORIGEM TEXT,                                     "
					+ " ENVIOEMAIL TEXT DEFAULT 'N');                     ";
			Log.d("DbHelper", "Criando tabela cliente");
			db.execSQL(sql);
			
			sql = "CREATE TABLE " + ConfiguracoesDAO.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ " CODRESP TEXT,                                    "
					+ " ENVIAEMAIL TEXT ,                                "
					+ " URLIMAGEM TEXT ,                                 "
					+ " SMTP TEXT,                                       "
					+ " PORTA TEXT,                                      "
					+ " EMAIL TEXT,                                      "
					+ " USUARIO TEXT,                                    "
					+ " SENHA TEXT,                                      "
			        + " SSL TEXT);                                       ";
			
			Log.d("DbHelper", "Criando tabela Config");
			db.execSQL(sql);
			//Inserre valores padrão.
			sql = "INSERT INTO " + ConfiguracoesDAO.TABLE_NAME
					+ " (CODRESP,                                    "
					+ " ENVIAEMAIL,                                 "
					+ " URLIMAGEM,                                  "
					+ " SMTP,                                       "
					+ " PORTA,                                      "
					+ " EMAIL,                                      "
					+ " USUARIO,                                    "
					+ " SENHA,                                      "
			        + " SSL)                                        "
					+ " VALUES('','S','http:////','smtp.atsinformatica.com.br', "
			        + "        '587','nome@atsinformatica.com.br',      "
					+ "        'nome','','N');                          ";
			Log.d("DbHelper", "Inseriu registro Config");
			
			db.execSQL(sql);
			sql = "CREATE TABLE " + ControleEmailDAO.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ " CODCLIENTE INTEGER,                              "
					+ " EMAILENVIADO TEXT);                              ";
			Log.d("DbHelper", "Criando tabela controle email");
			db.execSQL(sql);
		} catch (Exception e) {
			Log.e("DbHelper", "Erro na criação da tabela", e);
		}
	}

	/**
	 * Método invocado pelo Android a fim de executar
	 * a atualização de versões do banco de dados
	 * 
	 * @param db Banco de dados a ser atualizado
	 * @param oldVersion Número da versão atual do banco de dados
	 * @param newVersion Número da versão para a qual o banco de dados
	 * será atualizado
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			//Tabela de Cliente
			String sql = "CREATE TABLE IF NOT EXISTS " + ClienteDAO.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ " NOME TEXT NOT NULL,                              "
					+ " FANTASIA TEXT ,                                  "
					+ " TIPOPESSOA TEXT NOT NULL,                        "
					+ " CPF TEXT,                                        "
					+ " CNPJ TEXT,                                       "
					+ " INSCESTADUAL TEXT,                               "
					+ " SEGMENTO TEXT,                                   "
					+ " RESPONSAVEL TEXT,                                "
					+ " ENDERECO TEXT,                                   "
					+ " BAIRRO TEXT,                                     "
					+ " CIDADE TEXT,                                     "
					+ " ESTADO TEXT,                                     "
					+ " CEP TEXT,                                        "
					+ " COMPLEMENTO TEXT,                                "
					+ " NUMERO TEXT,                                     "
					+ " EMAIL_PRIMARIO TEXT,                             "
					+ " EMAIL_SECUNDARIO TEXT,                           "
					+ " TELEFONE1 TEXT,                                  "
					+ " TELEFONE2 TEXT,                                  "
					+ " CELULAR1 TEXT,                                   "
					+ " OPERADORA1 TEXT,                                 "
					+ " CELULAR2 TEXT,                                   "
					+ " OPERADORA2 TEXT,                                 "
					+ " FAX TEXT,                                        "
					+ " WEBSITE TEXT,                                    "
					+ " COTATORESP1 TEXT,                                "
					+ " CONTATORESP2 TEXT,                               "
					+ " CONTATORESP3 TEXT,                               "
					+ " OBSERVACAO TEXT,                                 "
					+ " ORIGEM TEXT);                                    ";
			db.execSQL(sql);
			
			//Tabela de Parametros.
			sql = "CREATE TABLE IF NOT EXISTS " + ConfiguracoesDAO.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ " CODRESP TEXT,                                        "
					+ " ENVIAEMAIL TEXT ,                                "
					+ " URLIMAGEM TEXT ,                                 "
					+ " SMTP TEXT,                                       "
					+ " PORTA TEXT,                                      "
					+ " EMAIL TEXT,                                      "
					+ " USUARIO TEXT,                                    "
					+ " SENHA TEXT,                                      "
			        + " SSL TEXT);                                       ";
			
			db.execSQL(sql);
			
			sql = "CREATE TABLE IF NOT EXISTS " + ControleEmailDAO.TABLE_NAME
					+ " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
					+ " CODCLIENTE INTEGER,                              "
					+ " EMAILENVIADO TEXT);                              ";
			db.execSQL(sql);
			
			//cria campo de envio de email
			sql = "ALTER TABLE " + ClienteDAO.TABLE_NAME
					+ " ADD COLUMN ENVIOEMAIL TEXT DEFAULT 'N';";
			db.execSQL(sql);
			//Atualiza registros
			sql = "UPDATE " + ClienteDAO.TABLE_NAME + " SET "
				+ " ENVIOEMAIL = 'S' where "+ ClienteDAO.TABLE_NAME +"._id in "
				+ " (SELECT CODCLIENTE FROM " + ControleEmailDAO.TABLE_NAME
				+ "    WHERE EMAILENVIADO = 'S') ";
			db.execSQL(sql);
		} catch (Exception e) {
			Log.e("DbHelper", "Erro na criação da tabela", e);
		}
	}

}
