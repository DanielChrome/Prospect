package br.com.atsinformatica.prospect;

import br.com.atsinformatica.prospect.R;
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

@SuppressLint("NewApi")
public class Endereco extends Fragment {

	private Activity ac;
	private Cliente cliente;
	private TextWatcher cepMask;
	private EditText edendereco, edbairro, edcidade,edestado;
	private EditText edcep, edcompl, ednumero;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_endereco, container, false);
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
		edendereco = (EditText) ac.findViewById(R.id.edEndereco);
		edbairro   = (EditText) ac.findViewById(R.id.edBairro);
		edcidade   = (EditText) ac.findViewById(R.id.edCidade);
		edestado   = (EditText) ac.findViewById(R.id.edEstado);
		edcep      = (EditText) ac.findViewById(R.id.edCEP);
		edcompl    = (EditText) ac.findViewById(R.id.edCompl);
		ednumero   = (EditText) ac.findViewById(R.id.edNumero);

		cepMask = Utility.insert("##.###-###", edcep);
		edcep.addTextChangedListener(cepMask);

		cliente = CadCliente.cliente;
		if (cliente != null) {
			// ... preenche-se o formulário para alteração.
			if (edendereco.getText().toString().isEmpty())
				edendereco.setText(cliente.getEndereco());
			if (edbairro.getText().toString().isEmpty())
				edbairro.setText(cliente.getBairro());
			if (edcidade.getText().toString().isEmpty())
				edcidade.setText(cliente.getCidade());
			if (edestado.getText().toString().isEmpty())
				edestado.setText(cliente.getEstado());
			if (edcep.getText().toString().isEmpty())
				edcep.setText(cliente.getCep());
			if (edcompl.getText().toString().isEmpty())
				edcompl.setText(cliente.getComplemento());
			if (ednumero.getText().toString().isEmpty())
				ednumero.setText(cliente.getNumero());
		}
	}
}
