package br.com.atsinformatica.prospect;

import br.com.atsinformatica.prospect.R;
import br.com.atsinformatica.prospect.CadCliente;
import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.util.Utility;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DadosPrincipais extends Fragment {

	private TextWatcher cpfMask;
	private TextWatcher cnpjMask;
	private EditText ednome,edfantasia,edcpfcnpj;
	private EditText edinsc,edsegmto,edresp;
	private TextView tvcpfcnpj;
	private RadioButton rbpessoaf, rbpessoaj;
	private Activity ac;
	private Cliente cliente;
	private RadioGroup rgPessoa;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_principal, container, false);		
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
		ednome = (EditText) ac.findViewById(R.id.edNome);
		edfantasia = (EditText) ac.findViewById(R.id.edFantasia);
		rbpessoaf = (RadioButton) ac.findViewById(R.id.rgTipoPessoaF);
		rbpessoaj = (RadioButton) ac.findViewById(R.id.rgTipoPessoaJ);
		edcpfcnpj = (EditText) ac.findViewById(R.id.cpfcnpj);
		edinsc    = (EditText) ac.findViewById(R.id.edInscE);
		edsegmto  = (EditText) ac.findViewById(R.id.edSegmento);
		edresp    = (EditText) ac.findViewById(R.id.edResp);
		tvcpfcnpj = (TextView) ac.findViewById(R.id.TextView4);

		cpfMask = Utility.insert("###.###.###-##", edcpfcnpj);
		cnpjMask = Utility.insert("##.###.###/####-##", edcpfcnpj);
		edcpfcnpj.addTextChangedListener(cpfMask);

		rgPessoa = (RadioGroup) ac.findViewById(R.id.rgTipoPessoa);
		rgPessoa.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				edcpfcnpj.setText("");
				int opcao = rgPessoa.getCheckedRadioButtonId();
				if (opcao == rbpessoaj.getId()) {
					edcpfcnpj.removeTextChangedListener(cpfMask);
					edcpfcnpj.addTextChangedListener(cnpjMask);
					edcpfcnpj.setHint(R.string.hcnpj);
					tvcpfcnpj.setText(R.string.cnpj);
				} else {
					edcpfcnpj.removeTextChangedListener(cnpjMask);
					edcpfcnpj.addTextChangedListener(cpfMask);
					edcpfcnpj.setHint(R.string.hcpf);
					tvcpfcnpj.setText(R.string.cpf);
				}
			}
		});

		cliente = CadCliente.cliente;
		if (cliente != null) {

			// ... preenche-se o formulário para alteração.

			if (ednome.getText().toString().isEmpty())
				ednome.setText(cliente.getNome());
			if (edfantasia.getText().toString().isEmpty())
				edfantasia.setText(cliente.getNome_fantasia());
			if (edinsc.getText().toString().isEmpty())
				edinsc.setText(cliente.getInsc_estadual());
			if (edsegmto.getText().toString().isEmpty())
				edsegmto.setText(cliente.getSegmento());
			if (edresp.getText().toString().isEmpty())
				edresp.setText(cliente.getResponsavel());
			if (edcpfcnpj.getText().toString().isEmpty()){
				if ("J".equals(cliente.getTipo_pessoa())){
					rbpessoaj.setChecked(true);
					edcpfcnpj.setText(cliente.getCnpj());
				}
				else{
					rbpessoaf.setChecked(true);
					edcpfcnpj.setText(cliente.getCpf());
				}
			}
		}
	}
}
