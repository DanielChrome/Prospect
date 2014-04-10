package br.com.atsinformatica.prospect;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.atsinformatica.prospect.R;
import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.adapter.AdapterEmails;
import br.com.atsinformatica.prospect.util.*;

public class ListaEmails extends Activity implements OnCreateContextMenuListener{
	
	//Opções do menu de contexto
	public static final int CONTEXTMENUITEM_SEND = 101;
	public static final int REQUEST_SEND = 100;
	ClienteDAO cdao;
	Cliente cliente;
	private ListView listView;
	private Button   enviaemails,atualizar;
    private AdapterEmails adapterListView;
    private List<Cliente> itens;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //carrega o layout onde contem o ListView
        setContentView(R.layout.lista);
        //Pega a referencia do ListView
        listView = (ListView) findViewById(R.id.lista);
        enviaemails = (Button) findViewById(R.id.btEnviar);
        atualizar = (Button) findViewById(R.id.btAtualizar);
        //Define o Listener quando alguem clicar no item.
        registerForContextMenu(listView);
        itens = ClienteDAO.getClienteDAO(getApplicationContext()).selectNotSendEmail();
        createListView();
    }

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView      
        //Cria o adapter
    	adapterListView = new AdapterEmails(this, itens);
        //Define o Adapter
        listView.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para rolagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == listView.getId()) {
			menu.add(0, CONTEXTMENUITEM_SEND, 1, R.string.ctx_send);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		AdapterView.AdapterContextMenuInfo info
				= (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		
		final Cliente cliente = adapterListView.getItem(info.position);

		if (item.getItemId() == CONTEXTMENUITEM_SEND) {	
			try {
				Utility.sendEmail(getApplicationContext(), cliente);
				Toast.makeText(getApplicationContext(), 
						"Email enviado", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), 
						"Erro: Email não enviado:\n"+e, Toast.LENGTH_SHORT).show();
			}			
		} 
		return true;
	}
	
	
	 public void reenviarEmails(View v) { 
		 Toast.makeText(getApplicationContext(), 
					"Reenviando Emails.", Toast.LENGTH_SHORT).show();
		 Intent intent = new Intent(this, ServicoEmail.class);
			startService(intent);	
	 }
	 
	 public void atualizarLista(View v){
		 itens = ClienteDAO.getClienteDAO(getApplicationContext()).selectNotSendEmail();
		 createListView();
	 }
}
