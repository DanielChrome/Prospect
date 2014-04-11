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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;
import android.app.Notification;
import android.app.NotificationManager;

/**
 * Classe utilitária.
 * 
 * @author Daniel Costa
 * @version 1.0 06 de abril de 2014
 */
public class Utility {

	private Context ct;
	private static final int MY_NOTIFICATION_ID  =1;
	private static final int MY_NOTIFICATION_ID2 =2;
	private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	private Builder mBuilder;
	private NotificationManager mNotifyManager;
	/**
	 * Método responsável pela geração e tratamento de diálogos.
	 * 
	 * @param context Contexto da aplicação. 
	 * @param title Título do diálogo a ser gerado.
	 * @param message Mensagem de texto para interação com o usuário.
	 * @param positiveLabel Texto para resposta positiva.
	 * @param positiveListener Tratamento da resposta positiva.
	 * @param negativeLabel Texto para resposta negativa.
	 * @param negativeListener Tratamento da resposta negativa.
	 * 
	 * @return Diálogo (AlertDialog) parametrizado.
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
		mNotifyManager =
		        (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(ctx);
		mBuilder.setContentTitle("Exportando Registros")
		    .setContentText("Exportação em progresso")
		    .setSmallIcon(R.drawable.ic_launcher);
		Log.i("Export.", "Preparando pra executar AssyncTask");
		Toast.makeText(ctx, 
				"Exportando...", Toast.LENGTH_SHORT).show();
		new ExportTask().execute();
	}	

	private class ExportTask extends AsyncTask<Void, Integer, Void> {
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.d("AsyncTasck", "On Progress "+values[0] + "-"+values[1]);
			mBuilder.setProgress(values[0], values[1], false);
            mNotifyManager.notify(MY_NOTIFICATION_ID2, mBuilder.build());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("AsyncTasck", "Executando AsyncTasck");
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			mBuilder.setContentText("Exportado com sucesso.")
            		.setProgress(0,0,false);
			mNotifyManager.notify(MY_NOTIFICATION_ID2, mBuilder.build());
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
				String columns[] = {"Razão Social","ID","Website","Endereço(s) de E-mail",						
						/*1*/	"email_addresses_non_primary","Telefone Comercial","Telefone Alternativo",
						/*2*/	"Fax","Rua do Endereço não Utilizado","Cidade do Endereço não Utilizado",
						/*3*/	"Estado do Endereço não Utilizado","CEP do Endereço não Utilizado",
						/*4*/	"País do Endereço não Utilizado","Rua do Endereço não Utilizado",
						/*5*/	"Cidade do Endereço não Utilizado (nope)","Estado do Endereço não Utilizado",
						/*6*/	"CEP do Endereço não Utilizado","Paí­s do Endereço não Utilizado",
						/*7*/	"Descrição","Tipo","Negócio","Receita Anual","Nº de Funcionários",
						/*8*/	"Código SIC","Código Bolsa","ID Conta Principal","Propriedade",
						/*9*/	"ID Campanha","Avaliação","nome de usuário atribuído","Atribuído a",
						/*10*/	"Data da Criação no Resulth CRM","Data da Modificação","Modificado Por",
						/*11*/	"Criado Por","Eliminado","Ativo","Bairro do Endereço de Fatura",
						/*12*/	"Bairro do Endereço Principal","CNPJ","Código do Cliente (Resulth)",
						/*13*/	"Data de Cadastro","Estado do Endereço de Fatura","Estado",
						/*14*/	"Motivo do Cancelamento","Nome Fantasia","Número do Endereço de Fatura",
						/*15*/	"Número do Endereço Principal","País do Endereço de Fatura",
						/*16*/	"País do Endereço Principal","Rua do Endereço de Fatura",
				/*17*/	"Rua do Endereço Principal"};

				String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
				Log.d("ExportaCSV", "Diretorio: "+csv);
				File file  = android.os.Environment.getExternalStorageDirectory();
				if (file.canWrite()){
					File dir    =   new File (csv + "/PersonData");
					dir.mkdirs();
					file   =   new File(dir, "Data.csv");
					CSVWriter write = new CSVWriter(new FileWriter(file));
					try {
						Log.d("AsyncTasck", "Gravando colunas");
						write.writeNext(columns);
						for(Cliente cliente:lista){						
							Log.d("AsyncTasck", "exportando "+cliente.getNome());
							String valor[] = {cliente.getNome(),"",cliente.getWebsite(),cliente.getEmail_principal(),
									/*1*/	cliente.getEmail_secundario(),cliente.getTelefone(),cliente.getTelefone2(),
									/*2*/	cliente.getFax(),"","",
									/*3*/	"","",
									/*4*/	"","",
									/*5*/	"","",
									/*6*/	"","",
									/*7*/	"","",cliente.getSegmento(),"","",
									/*8*/	"","","","",
									/*9*/	"","",cliente.getResponsavel(),codresp,
									/*10*/	"","","1",
									/*11*/	"1","0","0","",
									/*12*/	cliente.getBairro(),cliente.getCnpj(),"",
									/*13*/	"","",cliente.getEstado(),
									/*14*/	"NAO_USAR_MUDANCA_ANTIGA_DE_PLU_P_PDU",cliente.getNome_fantasia(),"",
									/*15*/	cliente.getNumero(),"",
									/*16*/	"","",
									/*17*/	cliente.getEndereco()};
							write.writeNext(valor);
							count++;
							publishProgress(total,count);
							Log.d("ExportaCSV", "Cliente "+cliente+" exportado");
						}
					} catch (Exception e) {
						Log.d("AsyncTasck", "Erro ao exportar:" +e);
					}
					try {
						write.close();
						Log.d("ExportaCSV", "Processo finalizado");
					} catch (IOException e) {
						Log.d("AsyncTasck", "Erro ao salvar arquivo: "+e);
					}
				}
				Log.d("AsyncTasck", "Exportação concluido");
			} catch (Exception e) {
				Log.d("AsyncTasck", "Erro inesperado :P");
				e.printStackTrace();
			}
			return null;
		}
	}

	/* Função para verificar existência de conexão com a internet 
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

	//Função pra enviar notificações
	public static void enviaNotificacao(Context ctx, int count, int sucess){

		NotificationManager notificationManager;
		notificationManager =
				(NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(ctx, ListaEmails.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification not  = new Notification(R.drawable.ic_launcher,"Prospect",System.currentTimeMillis());
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
						"Verifique as configurações do email ou a conexão com internet.",
						pIntent);
			}
		notificationManager.notify(MY_NOTIFICATION_ID, not);		
	}



	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt(str.substring(indice,indice+1));
			soma += digito*peso[peso.length-str.length()+indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValidCPF(String cpf) {
		if ((cpf==null) || (cpf.length()!=11)) return false;

		Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj==null)||(cnpj.length()!=14)) return false;

		Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	}
}    
