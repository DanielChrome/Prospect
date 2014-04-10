package br.com.atsinformatica.prospect.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.atsinformatica.prospect.ListaEmails;
import br.com.atsinformatica.prospect.R;
import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import br.com.atsinformatica.prospect.dataaccess.ConfiguracoesDAO;
import br.com.atsinformatica.prospect.models.Cliente;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;
import android.app.Notification;
import android.app.NotificationManager;

/**
 * Classe utilit�ria.
 * 
 * @author Daniel Costa
 * @version 1.0 06 de abril de 2014
 */
public class Utility {

	private Context ct;
	private static final int MY_NOTIFICATION_ID=1;
	/**
	 * M�todo respons�vel pela gera��o e tratamento de di�logos.
	 * 
	 * @param context Contexto da aplica��o. 
	 * @param title T�tulo do di�logo a ser gerado.
	 * @param message Mensagem de texto para intera��o com o usu�rio.
	 * @param positiveLabel Texto para resposta positiva.
	 * @param positiveListener Tratamento da resposta positiva.
	 * @param negativeLabel Texto para resposta negativa.
	 * @param negativeListener Tratamento da resposta negativa.
	 * 
	 * @return Di�logo (AlertDialog) parametrizado.
	 */
	public static AlertDialog createDialog(Context context, int title,
			int message, int positiveLabel, OnClickListener positiveListener,
			int negativeLabel, OnClickListener negativeListener) {

		Log.d("Utility", title + " - " + message);
		Log.d("Utility", title + " - " + positiveLabel);
		Log.d("Utility", positiveListener == null ? "posL nulo" : "posL OK");
		Log.d("Utility", title + " - " + negativeLabel);
		Log.d("Utility", negativeListener == null ? "negL nulo" : "negL OK");

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveLabel, positiveListener);
		builder.setNegativeButton(negativeLabel, negativeListener);

		return builder.create();
	}

	public static String unmask(String s) {
		return s.replaceAll("[.]", "").replaceAll("[-]", "")
				.replaceAll("[/]", "").replaceAll("[(]", "")
				.replaceAll("[)]", "");
	}

	public static TextWatcher insert(final String mask, final EditText ediTxt) {
		return new TextWatcher() {
			boolean isUpdating;
			String old = "";

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String str = unmask(s.toString());
				String mascara = "";
				if (isUpdating) {
					old = str;
					isUpdating = false;
					return;
				}
				int i = 0;
				for (char m : mask.toCharArray()) {
					if (m != '#' && str.length() > old.length()) {
						mascara += m;
						continue;
					}
					try {
						mascara += str.charAt(i);
					} catch (Exception e) {
						break;
					}
					i++;
				}
				isUpdating = true;
				ediTxt.setText(mascara);
				ediTxt.setSelection(mascara.length());
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		};
	}
	
	public void setContext(Context ctx){
		this.ct = ctx;
	}
	
	public static void sendEmail(Context ctx,Cliente cli) throws Exception {
		Log.i("SendEmail", "Preparando para enviar email");
		Mail email = new Mail(ctx);

		try {
			email.sendMail(cli);
			Log.i("SendEmail.", "Email Enviado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("SendEmail.", "Erro ao enviar email: "+e);
		}
	}

	public void executaExportCSV(Context ctx){
		Log.i("Export.", "Preparando pra executar AssyncTask");
		Toast.makeText(ctx, 
				"Exportando...", Toast.LENGTH_SHORT).show();
		new ExportTask().execute();
	}	

	private class ExportTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (values[1]>0){
				Toast.makeText(ct, 
						"Exportando: "+values[0]+" de "+values[1], Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(ct, 
						"Nenhum registro para ser importado", Toast.LENGTH_SHORT).show();
			}
				
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("AsyncTasck", "Executando AsyncTasck");
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Toast.makeText(ct, "Dados exportados com sucesso.", Toast.LENGTH_SHORT).show();
			Log.d("AsyncTasck", "Finalizando AsyncTasck");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Log.d("AsyncTasck", "Tentando envio");
				List<Cliente> lista = ClienteDAO.getClienteDAO(ct).selectAll();
				int total = lista.size();
				int count = 1;
				if(total<1){
					Log.d("AsyncTasck", "Nenhum registro para exportar.");
					publishProgress(count,total);
					return null;
				}				
				String codresp = ConfiguracoesDAO.getConfiguracoesDAO(ct).select(1).getCodResp();
				String columns[] = {"\"Raz�o Social\"","\"ID\"","\"Website\"","\"Endere�o(s) de E-mail\"",						
				        /*1*/	"\"email_addresses_non_primary\"","\"Telefone Comercial\"","\"Telefone Alternativo\"",
						/*2*/	"\"Fax\"","\"Rua do Endere�o n�o Utilizado\"","\"Cidade do Endere�o n�o Utilizado\"",
						/*3*/	"\"Estado do Endere�o n�o Utilizado\"","\"CEP do Endere�o n�o Utilizado\"",
						/*4*/	"\"Pa�s do Endere�o n�o Utilizado\"","\"Rua do Endere�o n�o Utilizado\"",
						/*5*/	"\"Cidade do Endere�o n�o Utilizado (nope)\"","\"Estado do Endere�o n�o Utilizado\"",
						/*6*/	"\"CEP do Endere�o n�o Utilizado\"","\"Pa��s do Endere�o n�o Utilizado\"",
						/*7*/	"\"Descri��o\"","\"Tipo\"","\"Neg�cio\"","\"Receita Anual\"","\"N� de Funcion�rios\"",
						/*8*/	"\"C�digo SIC\"","\"C�digo Bolsa\"","\"ID Conta Principal\"","\"Propriedade\"",
						/*9*/	"\"ID Campanha\"","\"Avalia��o\"","\"nome de usu�rio atribu�do\"","\"Atribu�do a\"",
						/*10*/	"\"Data da Cria��o no Resulth CRM\"","\"Data da Modifica��o\"","\"Modificado Por\"",
						/*11*/	"\"Criado Por\"","\"Eliminado\"","\"Ativo\"","\"Bairro do Endere�o de Fatura\"",
						/*12*/	"\"Bairro do Endere�o Principal\"","\"CNPJ\"","\"C�digo do Cliente (Resulth)\"",
						/*13*/	"\"Data de Cadastro\"","\"Estado do Endere�o de Fatura\"","\"Estado\"",
						/*14*/	"\"Motivo do Cancelamento\"","\"Nome Fantasia\"","\"N�mero do Endere�o de Fatura\"",
						/*15*/	"\"N�mero do Endere�o Principal\"","\"Pa�s do Endere�o de Fatura\"",
						/*16*/	"\"Pa�s do Endere�o Principal\"","\"Rua do Endere�o de Fatura\"",
						/*17*/	"\"Rua do Endere�o Principal\""};
				
				String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
				Log.d("ExportaCSV", "Diretorio: "+csv);
				File file  = android.os.Environment.getExternalStorageDirectory();
				if (file.canWrite()){
					File dir    =   new File (csv + "/PersonData");
					dir.mkdirs();
					file   =   new File(dir, "Data.csv");
					CSVWriter write = new CSVWriter(new FileWriter(file));
					try {
						write.writeNext(columns);
						for(Cliente cliente:lista){
						String valor[] = {"\""+cliente.getNome()+"\"","\"\"","\""+cliente.getWebsite()+"\"","\""+cliente.getEmail_principal()+"\"",
								/*1*/	"\""+cliente.getEmail_secundario()+"\"","\""+cliente.getTelefone()+"\"","\""+cliente.getTelefone2()+"\"",
								/*2*/	"\""+cliente.getFax()+"\"","\"\"","\"\"",
								/*3*/	"\"\"","\"\"",
								/*4*/	"\"\"","\"\"",
								/*5*/	"\"\"","\"\"",
								/*6*/	"\"\"","\"\"",
								/*7*/	"\"\"","\"\"","\""+cliente.getSegmento()+"\"","\"\"","\"\"",
								/*8*/	"\"\"","\"\"","\"\"","\"\"",
								/*9*/	"\"\"","\"\"","\""+cliente.getResponsavel()+"\"","\""+codresp+"\"",
								/*10*/	"\"\"","\"\"","\"1\"",
								/*11*/	"\"1\",\"0\"","\"0\"","\"\"",
								/*12*/	"\""+cliente.getBairro()+"\"","\""+cliente.getCnpj()+"\"","\"\"",
								/*13*/	"\"\"","\"\"","\""+cliente.getEstado()+"\"",
								/*14*/	"\"NAO_USAR_MUDANCA_ANTIGA_DE_PLU_P_PDU\"","\""+cliente.getNome_fantasia()+"\"","\"\"",
								/*15*/	"\""+cliente.getNumero()+"\"","\"\"",
								/*16*/	"\"\"","\"\"",
								/*17*/	"\""+cliente.getEndereco()+"\""};
							write.writeNext(valor);
							publishProgress(count,total);
							count++;
							Log.d("ExportaCSV", "Cliente "+cliente+" exportado");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						write.close();
						Log.d("ExportaCSV", "Processo finalizado");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Log.d("AsyncTasck", "Exporta��o concluido");
			} catch (Exception e) {
				Log.d("AsyncTasck", "Erro inesperado :P");
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/* Fun��o para verificar exist�ncia de conex�o com a internet 
	 */  
	public static boolean verificaConexao(Context ctx) {  
		boolean conectado;  
		ConnectivityManager conectivtyManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);  
		if (conectivtyManager.getActiveNetworkInfo() != null  
				&& conectivtyManager.getActiveNetworkInfo().isAvailable()  
				&& conectivtyManager.getActiveNetworkInfo().isConnected()) {  
			conectado = true;  
		} else {  
			conectado = false;  
		}
		Log.d("VErificaConexao", "Conectado = " + conectado);
		return conectado;  
	}
	
	//Fun��o pra enviar notifica��es
	public static void enviaNotificacao(Context ctx, int count, int sucess){
		
		NotificationManager notificationManager;
		notificationManager =
				 (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(ctx, ListaEmails.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification not  = new Notification(R.drawable.logo,"Prospect",System.currentTimeMillis());
		not.defaults |= Notification.DEFAULT_SOUND;
		not.flags |= Notification.FLAG_AUTO_CANCEL;
		if (sucess == 0){
		not.setLatestEventInfo(ctx,
		   "Emails Enviados",
		   "",
		   pIntent);
		}else
		if (sucess == 1){
			not.setLatestEventInfo(ctx,
			   "Erro ao enviar emails",
			   "Verifique as configura��es do email ou a conex�o com internet.",
			   pIntent);
		}
		notificationManager.notify(MY_NOTIFICATION_ID, not);
		    
		
	}
}    
