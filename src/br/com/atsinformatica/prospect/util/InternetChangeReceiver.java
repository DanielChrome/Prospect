package br.com.atsinformatica.prospect.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InternetChangeReceiver extends BroadcastReceiver{

  @Override
     public void onReceive(final Context context, final Intent intent) {
	  Log.d("Receiver", "Recebeu Notification");
	     if (Utility.verificaConexao(context)){
        	 Log.d("Receiver", "Conectou na Internet. enviando emails.");
        	 Intent inte = new Intent(context, ServicoEmail.class);
 			 context.startService(inte);
         }
     }
}
