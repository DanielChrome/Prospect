package br.com.atsinformatica.prospect;

import br.com.atsinformatica.prospect.util.Utility;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Principal extends Activity {
	
	ImageButton[] menu = new ImageButton[5];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		menu[0] = (ImageButton) findViewById(R.id.addCliente);
        menu[1] = (ImageButton) findViewById(R.id.listaCliente);
        menu[2] = (ImageButton) findViewById(R.id.listaEmail);
        menu[3] = (ImageButton) findViewById(R.id.Exportar);
        menu[4] = (ImageButton) findViewById(R.id.Configuracoes);
        
	}
	
	public void executaOpcao(View v){
        	Intent i = new Intent(this,Principal.class);;
        	switch(v.getId()){
        		case R.id.addCliente:
        			i = new Intent(this,CadCliente.class);
        			break;
        		case R.id.listaCliente:
        			i = new Intent(this,ListaClientes.class);
        			break;
        		case R.id.listaEmail:
        			i = new Intent(this,ListaEmails.class);
        			break;
        		case R.id.Exportar:
        			Utility u = new Utility();
        			u.setContext(getApplicationContext());
        			u.executaExportCSV(getApplicationContext());
        			break;
        		case R.id.Configuracoes:
        			i = new Intent(this,AConfigs.class);
        			break;
        		default:
        			i = new Intent(this,Principal.class);
        	}
        	if (v.getId() != R.id.Exportar)
        		startActivity(i);
	}
}
