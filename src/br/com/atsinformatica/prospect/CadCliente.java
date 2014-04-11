package br.com.atsinformatica.prospect;

import br.com.atsinformatica.prospect.adapter.TabsPageAdapter;
import br.com.atsinformatica.prospect.R;
import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.util.ServicoEmail;
import br.com.atsinformatica.prospect.util.Utility;
import br.com.atsinformatica.prospect.dataaccess.ClienteDAO;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
 
@SuppressLint("NewApi")
public class CadCliente extends FragmentActivity implements
        ActionBar.TabListener {
 
    public static Cliente cliente;
	private ViewPager viewPager;
    private TabsPageAdapter mAdapter;
    private ActionBar actionBar;
    private EditText ednome,edfantasia,edcpfcnpj;
	private EditText edinsc,edsegmto,edresp;
    private RadioButton rbpessoaj;
    private EditText edemail1,edemail2;
	private EditText edfone1,edfone2;
	private EditText edcelular1,edcelular2;
	private EditText edoperadora1,edoperadora2;
	private EditText edfax,edwebsite;
	private EditText edcontatoresp1,edcontatoresp2,edcontatoresp3;
	private EditText edobservacao,edorigem;
	private EditText edendereco, edbairro, edcidade,edestado;
    private EditText edcep, edcompl, ednumero;
    // Tab titles
    private String[] tabs = { "Principal", "Endereço", "Contatos" };
 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPageAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
 
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
     // Recuperação do conteúdo de dados da Intent.
     	Bundle bundle = getIntent().getExtras();

     	// Caso haja dados...
     	if (bundle != null) {
     			
     		// ... recupera-se a id passada e
     		int clienteID = bundle.getInt(ListaClientes.CLIENTE_ID);
     			
     		// ... recupera-se a tarefa correspondente á id passada.
     		cliente = ClienteDAO.getClienteDAO(getApplicationContext()).select(clienteID);
     	}
        
        
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    public void salvar(View v) {
		// Recupera-se o dado digitado.
    	viewPager.setCurrentItem(0);//seta aba principal
    	ednome = (EditText) findViewById(R.id.edNome);
		edfantasia = (EditText) findViewById(R.id.edFantasia);
		rbpessoaj = (RadioButton) findViewById(R.id.rgTipoPessoaJ);
		edcpfcnpj = (EditText) findViewById(R.id.cpfcnpj);
		edinsc    = (EditText) findViewById(R.id.edInscE);
	    edsegmto  = (EditText) findViewById(R.id.edSegmento);
	    edresp    = (EditText) findViewById(R.id.edResp);
	    
    	viewPager.setCurrentItem(1);//seta aba cadastro
    	edendereco = (EditText) findViewById(R.id.edEndereco);
		edbairro   = (EditText) findViewById(R.id.edBairro);
		edcidade   = (EditText) findViewById(R.id.edCidade);
		edestado   = (EditText) findViewById(R.id.edEstado);
		edcep      = (EditText) findViewById(R.id.edCEP);
		edcompl    = (EditText) findViewById(R.id.edCompl);
		ednumero   = (EditText) findViewById(R.id.edNumero);
    	
    	viewPager.setCurrentItem(2);//seta aba cadastro
    	edemail1 = (EditText) findViewById(R.id.edEmail1);
		edemail2 = (EditText) findViewById(R.id.edEmail2);
		edfone1 = (EditText) findViewById(R.id.edtelefone1);
		edfone2 = (EditText) findViewById(R.id.edtelefone2);
		edcelular1 = (EditText) findViewById(R.id.edCelular1);
		edcelular2 = (EditText) findViewById(R.id.edCelular2);
		edoperadora1 = (EditText) findViewById(R.id.edOperadora1);
		edoperadora2 = (EditText) findViewById(R.id.edOperadora2);
		edfax = (EditText) findViewById(R.id.edFax);
		edwebsite = (EditText) findViewById(R.id.edWebSite);
		edcontatoresp1 = (EditText) findViewById(R.id.edContResp1);
		edcontatoresp2 = (EditText) findViewById(R.id.edContResp2);
		edcontatoresp3 = (EditText) findViewById(R.id.edContResp3);
		edobservacao = (EditText) findViewById(R.id.edObservacoes);
		edorigem = (EditText) findViewById(R.id.edOrigem);
    	
		// Caso não haja cliente instanciado
		if (cliente == null) {
			
			// ... cria-se uma entidade.
			cliente = new Cliente();
		}
		
		// Carrega dados principais.
		cliente.setNome(ednome.getText().toString());
		cliente.setNome_fantasia(edfantasia.getText().toString());
		if (rbpessoaj.isChecked()){
			cliente.setTipo_pessoa("J");
			cliente.setCnpj(Utility.unmask(edcpfcnpj.getText().toString()));
			if (!Utility.isValidCNPJ(cliente.getCnpj())){
				Toast.makeText(getApplicationContext(), "CNPJ digitado inválido.", Toast.LENGTH_SHORT).show();
				viewPager.setCurrentItem(0);//seta aba principal
				edcpfcnpj.requestFocus();
				return;	
			}
		}
		else{
			cliente.setTipo_pessoa("F");
			cliente.setCpf(Utility.unmask(edcpfcnpj.getText().toString()));
			if (!Utility.isValidCPF(cliente.getCpf())){
				Toast.makeText(getApplicationContext(), "CPF digitado inválido.", Toast.LENGTH_SHORT).show();
				viewPager.setCurrentItem(0);//seta aba principal
				edcpfcnpj.requestFocus();
				return;	
			}
		}
		cliente.setInsc_estadual(edinsc.getText().toString());
		cliente.setSegmento(edsegmto.getText().toString());
		if(cliente.getSegmento().isEmpty()){
			Toast.makeText(getApplicationContext(), "Segmento obrigatório.", Toast.LENGTH_SHORT).show();
			viewPager.setCurrentItem(0);//seta aba principal
			edsegmto.requestFocus();
			return;	
		}
		cliente.setResponsavel(edresp.getText().toString());
	    
	    //carrega dados endereco
		cliente.setEndereco(edendereco.getText().toString());
		cliente.setBairro(edbairro.getText().toString());
		cliente.setCidade(edcidade.getText().toString());
		cliente.setEstado(edestado.getText().toString());
		cliente.setCep(Utility.unmask(edcep.getText().toString()));
		cliente.setComplemento(edcompl.getText().toString());
		cliente.setNumero(ednumero.getText().toString());
	    
	    //carrega dados contatos
	    cliente.setEmail_principal(edemail1.getText().toString());
	    if(cliente.getEmail_principal().isEmpty()){
			Toast.makeText(getApplicationContext(), "Email Principal obrigatório.", Toast.LENGTH_SHORT).show();
			viewPager.setCurrentItem(2);//seta aba contato
			edemail1.requestFocus();
			return;	
		}
		cliente.setEmail_secundario(edemail2.getText().toString());
		cliente.setTelefone(edfone1.getText().toString());
		if(cliente.getTelefone().isEmpty()){
			Toast.makeText(getApplicationContext(), "Telefone principal obrigatório.", Toast.LENGTH_SHORT).show();
			viewPager.setCurrentItem(2);//seta aba contato
			edfone1.requestFocus();
			return;	
		}
		cliente.setTelefone2(edfone2.getText().toString());
		cliente.setCelular(edcelular1.getText().toString());
		cliente.setCelular2(edcelular2.getText().toString());
		cliente.setOperadora(edoperadora1.getText().toString());
		cliente.setOperadora2(edoperadora2.getText().toString());
		cliente.setFax(edfax.getText().toString());
		cliente.setWebsite(edwebsite.getText().toString());
		cliente.setContato_responsavel(edcontatoresp1.getText().toString());
		cliente.setContato_responsavel2(edcontatoresp2.getText().toString());
		cliente.setContato_responsavel3(edcontatoresp3.getText().toString());
		cliente.setObservacoes(edobservacao.getText().toString());
		cliente.setOrigem(edorigem.getText().toString());
		cliente.setEmailEnviado("N");
		
		// Caso a identificação seja 0
		if (cliente.getId() == 0) {	
			// ... insere-se uma nova tarefa
			ClienteDAO.getClienteDAO(getApplicationContext()).insert(cliente);
			//Envio de email de boas vindas
			Log.d("CadCliente", "Iniciando Serviço");
			Intent intent = new Intent(this, ServicoEmail.class);
			startService(intent);		
		} else {
			
			// ... caso contrário, atualiza-se a tarefa.
			ClienteDAO.getClienteDAO(getApplicationContext()).update(cliente);
		}

		// Define-se a resposta como sucesso.
		setResult(ListaClientes.RESPONSE_SUCCESS);
		
		
		// Encerra-se a activity.
		finish();
	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	cliente = null;
    }
   
}
