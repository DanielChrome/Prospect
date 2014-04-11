package br.com.atsinformatica.prospect.util;

import java.util.List;
import android.os.Message;
import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import br.com.atsinformatica.prospect.dataaccess.ConfiguracoesDAO;
import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.models.Configuracoes;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
/**
 * Serviço de envio de Email.
 * 
 * @author Daniel Costa
 * @version 1.0 06 de abril de 2014
 */
public class ServicoEmail extends Service {
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		public void handleMessage(Message msg) {
			int count = 0;
			while(!Utility.verificaConexao(getApplicationContext())){
				Log.d("Servico", "Sem conexão, tentado reconectar. Tentativa: "+count);
				count++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(count > 60){
					Log.d("Servico", "Sem conexão, Tentou reconectar sem sucesso");
					stopSelf();
				}
			}
			enviaEmails();
		}
	} 

	@Override
	public void onCreate() {
		// Start up the thread running the service.  Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block.  We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				android.os.Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		// Get the HandlerThread's Looper and use it for our Handler 
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Servico", "Iniciando Serviço");
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		mServiceHandler.sendMessage(msg);		
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressLint("NewApi")
	private boolean enviaEmails(){
		Log.d("Servico", "Enviando Emails");	
		Configuracoes config;	
		config = ConfiguracoesDAO.getConfiguracoesDAO(getApplicationContext()).select(1);
		if ("S".equals(config.getEnviaEmail())){
			Log.d("Servico", "Enviar emails = S");
			List<Cliente> lista = ClienteDAO.getClienteDAO(getApplicationContext()).selectNotSendEmail();
			for(Cliente item : lista){
				try {
					if(!item.getEmail_principal().isEmpty()){
						Log.d("Servico", "Enviando email para "+item.getEmail_principal());
						Utility.sendEmail(getApplicationContext(), item);
						Log.d("Servico", "email enviado");	
					}
				} catch (Exception e) {
					Log.d("Servico", "Erro ao enviar email");
					e.printStackTrace();
				}
			}
			Log.d("Servico","Fim de LOOP");
		}
		
		stopSelf();

		return false;
	}
}
