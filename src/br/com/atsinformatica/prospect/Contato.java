package br.com.atsinformatica.prospect;

import br.com.atsinformatica.prospect.R;
import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.util.Utility;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Contato extends Fragment {
	
    private TextWatcher fone1Mask;
    private TextWatcher fone2Mask;
    private TextWatcher cel1Mask;
    private TextWatcher cel2Mask;
    private TextWatcher faxMask;
	private EditText edemail1,edemail2;
	private EditText edfone1,edfone2;
	private EditText edcelular1,edcelular2;
	private EditText edoperadora1,edoperadora2;
	private EditText edfax,edwebsite;
	private EditText edcontatoresp1,edcontatoresp2,edcontatoresp3;
	private EditText edobservacao,edorigem;
	private Activity ac;
	private Cliente cliente;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contato, container, false);
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ac = getActivity();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// Se, efetivamente, houver uma tarefa a ser editada
		edemail1 = (EditText) ac.findViewById(R.id.edEmail1);
		edemail2 = (EditText) ac.findViewById(R.id.edEmail2);
		edfone1 = (EditText) ac.findViewById(R.id.edtelefone1);
		edfone2 = (EditText) ac.findViewById(R.id.edtelefone2);
		edcelular1 = (EditText) ac.findViewById(R.id.edCelular1);
		edcelular2 = (EditText) ac.findViewById(R.id.edCelular2);
		edoperadora1 = (EditText) ac.findViewById(R.id.edOperadora1);
		edoperadora2 = (EditText) ac.findViewById(R.id.edOperadora2);
		edfax = (EditText) ac.findViewById(R.id.edFax);
		edwebsite = (EditText) ac.findViewById(R.id.edWebSite);
		edcontatoresp1 = (EditText) ac.findViewById(R.id.edContResp1);
		edcontatoresp2 = (EditText) ac.findViewById(R.id.edContResp2);
		edcontatoresp3 = (EditText) ac.findViewById(R.id.edContResp3);
		edobservacao = (EditText) ac.findViewById(R.id.edObservacoes);
		edorigem = (EditText) ac.findViewById(R.id.edOrigem);
		
		fone1Mask = Utility.insert("(##)#####-####", edfone1);
		edfone1.addTextChangedListener(fone1Mask);
		fone2Mask = Utility.insert("(##)#####-####", edfone2);
		edfone2.addTextChangedListener(fone2Mask);
		cel1Mask = Utility.insert("(##)#####-####", edcelular1);
		edcelular1.addTextChangedListener(cel1Mask);
		cel2Mask = Utility.insert("(##)#####-####", edcelular2);
		edcelular2.addTextChangedListener(cel2Mask);
		faxMask = Utility.insert("(##)#####-####", edfax);
		edfax.addTextChangedListener(faxMask);
		
		cliente = CadCliente.cliente;
		if (cliente != null) {
			// ... preenche-se o formulário para alteração.
			edemail1.setText(cliente.getEmail_principal());
			edemail2.setText(cliente.getEmail_secundario());
			edfone1.setText(cliente.getTelefone());
			edfone2.setText(cliente.getTelefone2());
			edcelular1.setText(cliente.getCelular());
			edcelular2.setText(cliente.getCelular2());
			edoperadora1.setText(cliente.getOperadora());
			edoperadora2.setText(cliente.getOperadora2());
			edfax.setText(cliente.getFax());
			edwebsite.setText(cliente.getWebsite());
			edcontatoresp1.setText(cliente.getContato_responsavel());
			edcontatoresp2.setText(cliente.getContato_responsavel2());
			edcontatoresp3.setText(cliente.getContato_responsavel3());
			edobservacao.setText(cliente.getObservacoes());
			edorigem.setText(cliente.getOrigem());
		}
	}
}
