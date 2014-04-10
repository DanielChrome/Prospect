package br.com.atsinformatica.prospect;

import br.com.atsinformatica.prospect.dataaccess.ConfiguracoesDAO;
import br.com.atsinformatica.prospect.models.Configuracoes;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AConfigs extends Activity {
	
	private Configuracoes config;
	private EditText edcodresp,edimagem;
	private EditText edsmtp,edporta,edemail,edusuario,edsenha;
	private CheckBox ckenviaemail,ckssl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracoes);
		edcodresp     = (EditText) findViewById(R.id.edcodresp);
		edimagem      = (EditText) findViewById(R.id.edCaminhoImg);
		edsmtp        = (EditText) findViewById(R.id.edSMTP);
		edporta       = (EditText) findViewById(R.id.edPorta);
		edemail       = (EditText) findViewById(R.id.edEmailFrom);
		edusuario     = (EditText) findViewById(R.id.edUsuario);
		edsenha       = (EditText) findViewById(R.id.edSenha);
		ckenviaemail  = (CheckBox) findViewById(R.id.ckEnviaEmail1);
		ckssl         = (CheckBox) findViewById(R.id.ckSSL);
		
		//REcarrega as configura��es
		config = ConfiguracoesDAO.getConfiguracoesDAO(getApplicationContext()).select(1);
		
		if (config != null){
			edcodresp.setText(config.getCodResp());
			edimagem.setText(config.getUrlimagem());
			edsmtp.setText(config.getSmtp()); 
			edporta.setText(Integer.toString(config.getPorta())); 
			edemail.setText(config.getEmail()); 
			edusuario.setText(config.getUsuario()); 
			edsenha.setText(config.getSenha());
			ckenviaemail.setChecked("S".equals(config.getEnviaEmail()));
			ckssl.setChecked("S".equals(config.getSSL()));
		}
	}
	
	public void salvarConfig(View v) {
		// Recupera-se o dado digitado.
		
		// Caso n�o haja cliente instanciado
		if (config == null) {
			
			// ... cria-se uma entidade.
			config = new Configuracoes();
		}
		
		// Define-se a descri��o da tarefa.
		config.setCodResp(edcodresp.getText().toString());
		config.setEnviaEmail("N");
		if(ckenviaemail.isChecked()){
			config.setEnviaEmail("S");
		}
		config.setUrlimagem(edimagem.getText().toString());
		config.setSmtp(edsmtp.getText().toString());
		config.setPorta(Integer.parseInt(edporta.getText().toString()));
		config.setEmail(edemail.getText().toString());
		config.setUsuario(edusuario.getText().toString());
		config.setSenha(edsenha.getText().toString());
		config.setSSL("N");
		if(ckssl.isChecked()){
			config.setSSL("S");
		}
		
		// Caso a identifica��o seja 0
		if (config.getId() == 0) {
			
			// ... insere-se uma nova tarefa
			ConfiguracoesDAO.getConfiguracoesDAO(getApplicationContext()).insert(config);
		} else {
			
			// ... caso contr�rio, atualiza-se a tarefa.
			ConfiguracoesDAO.getConfiguracoesDAO(getApplicationContext()).update(config);
		}

		Toast.makeText(getApplicationContext(), "Configura��es salvas", Toast.LENGTH_SHORT).show();
		// Encerra-se a activity.
		finish();
	}
}
