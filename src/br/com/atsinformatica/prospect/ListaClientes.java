package br.com.atsinformatica.prospect;

import java.util.List;

import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import br.com.atsinformatica.prospect.util.Utility;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity para gestão da tela principal da aplicação.
 * 
 * @author Luis Guisso
 * @version 1.0 02 de junho de 2012
 */
public class ListaClientes
	// Activity para criação de listas.
	extends ListActivity
 	// Interface para tratar eventos de clique em menus de contexto.
	implements OnCreateContextMenuListener {

	// Adaptador para manutenção de tarefas.
	private ArrayAdapter<Cliente> adapter;

	// Código para inclusão e extração da id da tarefa em Intents.
	public static final String CLIENTE_ID = "clienteid";

	// Código para identificação de clique em Menu/Novo.
	public static final int MENUITEM_NEW = 100;
	
	// Código para identificação de clique em Menu/Config.
	public static final int MENUITEM_SETTINGS = 101;
	
	// Código para identificação de clique em Menu/Config.
	public static final int MENUITEM_EXPORT = 102;
	
	// Código para identificação de clique em Menu/Config.
	public static final int MENUITEM_EMAILS = 103;
	
	// Código para identificação de clique em Menu/Edit.
	public static final int CONTEXTMENUITEM_EDIT = 101;
	
	// Código para identificação de clique em Menu/Delete.
	public static final int CONTEXTMENUITEM_DELETE = 102;

	// Código para identificação de solicitação de operação
	// inclusão de um nova tarefa no banco de dados.
	public static final int REQUEST_NEW = 100;
	
	// Código para identificação de solicitação de operação
	// atualização de tarefas no banco de dados.
	public static final int REQUEST_UPDATE = 101;

	// Código de resposta a ser utilizado para quando houver
	// sucesso no processamento solicitado. 
	public static final int RESPONSE_SUCCESS = 200;
	
	// Código de resposta a ser utilizado para quando houver
	// cancelamento no processamento solicitado.
	public static final int RESPONSE_CANCEL = 300;

	/**
	 * Processamento de incialização da activity.
	 * 
	 * @param savedInstanceState Conjunto de dados para reinicialização.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// População do adaptador que quarda as tarefas a serem exibidas.
		//adapter = new ArrayAdapter<Cliente>(getApplicationContext(),
		//		R.layout.listitems);
		adapter = new ArrayAdapter<Cliente>(getApplicationContext(), 
		        android.R.layout.simple_list_item_1) {
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		        View view = super.getView(position, convertView, parent);
		        TextView text = (TextView) view.findViewById(android.R.id.text1);
		        text.setTextColor(Color.BLACK);
		        return view;
		    }
		};
		// Definição do adaptador desta ListActivity.
		setListAdapter(adapter);	
		// Carga dos dados do banco de dados para o adaptador e
		// consequente resposta na ListActivity.
		loadClientes();
		// Registro de ouvinte de menu de contexto para a ListActivity.
		registerForContextMenu(getListView());
	}

	/**
	 * Método responsável pela carga dos dados do banco de dados.<br/>
	 * Limpam-se os dados do adaptador atual, pois pode haver alterações
	 * que devam ser refletidas nele, e, posteriormente, ocorre a carga
	 * dos dados através do método selectAll() do ToDoDao.
	 */
	private void loadClientes() {
		adapter.clear();
		List<Cliente> allClientes = ClienteDAO.getClienteDAO(getApplicationContext()).selectAll();
		if (allClientes != null) {
			for (Cliente item : allClientes) {
				adapter.add(item);
			}
		}
	}

	/**
	 * Método responsável pela criação do menu de adição de nova tarefa.
	 * 
	 * @param menu Objeto representativo do menu da aplicação
	 * passado pelo Android.
	 * @return Sucesso ou falha da operação de criação do menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuItem menuNovo = menu.add(0, MENUITEM_NEW, 0, R.string.menuitem_new);
		menuNovo.setIcon(android.R.drawable.ic_menu_add);
		return true;
	}

	/**
	 * Método responsável pelo tratamento da seleção do item de menu.
	 * 
	 * @param item Item de menu clicado passado pelo Android.
	 * @return Sucesso ou falha da operação de tratamento do evento.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		// Pode haver vários itens de menus, assim, identifica-se
		// qual item menu foi clicado. Sendo um pedido de gravação
		//  de uma nova tarefa... 
		if (item.getItemId() == MENUITEM_NEW) {
			
			// ... cria-se uma Intent para iniciar a activity que
			// conteém o formulário de inserção e...
			Intent intent = new Intent(getApplicationContext(),
					CadCliente.class);
			
			// ... inicia-se a nova activity aguardando resposta.
			startActivityForResult(intent, REQUEST_NEW);
		}

		return true;
	}

	/**
	 * Método responsável pelo tratamento da resposta dada pelo formulário
	 * de cadastro de uma nova tarefa no banco de dados.<br/>
	 * 
	 * @param requestCode Código correspondente à chamada efetuada.
	 * @param resultCode Código de resposta da outra activity.
	 * @param data Dados devolvidos pela outra activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Tarefa inserida com sucesso
		if (resultCode == RESPONSE_SUCCESS) {
			
			// Se houve uma inserção ou alteração é necessário recarregar(reexibir) dados
			loadClientes();
			if (requestCode == REQUEST_NEW){
				Toast.makeText(getApplicationContext(), R.string.iteminsert_sucess,
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), R.string.itemedit_sucess,
						Toast.LENGTH_SHORT).show();
			}		
		// Operação cancelada
		} else {
			// Não é necessário carregar dados, somente informar
			// o usuário do sucesso da operação.
			Toast.makeText(getApplicationContext(), R.string.cancel,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Método não utilizado, porém, seria possível que, ao clicar em
	 * uma tarefa, esta seja passada a um formulário para visualização
	 * de detalhes ou mesmo alteração.
	 * 
	 * @param l Esta ListView.
	 * @param v A View clicada.
	 * @param position A posição View na ListView.
	 * @param id A id da view clicada.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		/*
		 * **
		 * ** Também é possível tratar um clique simples em um item
		 * **
		 *  
		 * ToDo todo = (ToDo) l.getItemAtPosition(position);
		 * 
		 * Intent intent = new Intent(getApplicationContext(),
		 * ToDoFormActivity.class);
		 * intent.putExtra(TODO_ID, todo.getId());
		 * 
		 * startActivityForResult(intent, REQUEST_UPDATE);
		 */
	}

	/**
	 * Método responsável pela criação do menu de contexto de edição
	 * e exclusão de uma tarefa.
	 * 
	 * @param menu O próprio menu de contexto.
	 * @param v A View construída no menu de contexto.
	 * @param menuInfo Informações adicionais sobre o item de menu clicado.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		// Constrói o menu de contexto...
		if (v.getId() == getListView().getId()) {
			
			// ... no grupo 0
			// ... com o código CONTEXTMENUITEM_EDIT
			// ... na ordem 1
			// ... e com o texto R.string.context_edit.
			menu.add(0, CONTEXTMENUITEM_EDIT, 1, R.string.context_edit);
			
			// Idem à anterior.
			menu.add(0, CONTEXTMENUITEM_DELETE, 2, R.string.context_delete);
		}
	}

	/**
	 * Método responsável pelo tratamento da seleção
	 * do item de menu de contexto.
	 * 
	 * @param item O item de menu de contexto selecionado.
	 * @return Sucesso ou falha na operação.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		// Recupera-se informação adicional sobre o item de menu clicado.
		AdapterView.AdapterContextMenuInfo info
				= (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		
		// "final" para que seja possível seu uso na classe interna
		// anônima de tratamento de eventos.
		// Aqui ocorre a recuperação da tarefa (ToDo) clicada nesta
		// ListActivity através de seu adaptador.
		final Cliente cliente = adapter.getItem(info.position);

		// Registra-se em log a tarefa selecionada. O método toString()
		// da classe ToDo foi sobrescrito para apresentar somente
		// a descrição da tarefa.
		Log.d(this.getClass().getName(), "Cliente selecionado: " + cliente);

		// Caso o item de menu de contexto selecionado seja edição...
		if (item.getItemId() == CONTEXTMENUITEM_EDIT) {
			
			// ... cria-se uma Intent para invocar o formulário de edição
			Intent intent = new Intent(getApplicationContext(),
					CadCliente.class);
			
			// ... insere-se na Intent a id da tarefa a ser alterada
			intent.putExtra(CLIENTE_ID, cliente.getId());

			// ... e invoca-se a activity com o código de requisição
			// de alteração de dados da tarefa.
			startActivityForResult(intent, REQUEST_UPDATE);
		
		// Caso o item de menu de contexto selecionado seja exclusão...
		} else if (item.getItemId() == CONTEXTMENUITEM_DELETE) {

			// Ouvinte de clique para CONFIRMAÇÃO de exclusão.
			// ** ATENÇÃO! OnClickListener de DialogInterface **
			OnClickListener positiveListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// Efetivação da exclusão.
					ClienteDAO.getClienteDAO(getApplicationContext()).delete(cliente.getId());
					
					// Recarga dos dados efetivos no banco de dados.
					loadClientes();
					
					// Fechamento da caixa de diálogo aberta.
					dialog.dismiss();
					
					// Informação de sucesso da operação.
					Toast.makeText(getApplicationContext(),
							R.string.itemdelete_sucess,
							Toast.LENGTH_SHORT).show();
				}
			};

			// Ouvinte de clique para CANCELAMENTO de exclusão
			// ** ATENÇÃO! OnClickListener de DialogInterface **
			OnClickListener negativeListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// Fechamento da caixa de diálogo aberta.
					dialog.dismiss();
					
					// Informação de sucesso da operação.
					Toast.makeText(getApplicationContext(),
							R.string.cancel,
							Toast.LENGTH_SHORT).show();
				}
			};
			
			// this --> getContextApplication() não funciona!
			// O diálogo deve estar vinculado a esta (this) activity.
			AlertDialog dialog = Utility.createDialog(this,
					R.string.dialog_title, R.string.dialog_message,
					R.string.dialog_positve, positiveListener,
					R.string.dialog_negative, negativeListener);
		
			// Exibição do diálogo criado pela classe Utility.
			dialog.show();
			
		}

		return true;
	}

}