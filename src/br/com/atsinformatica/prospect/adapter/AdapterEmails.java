package br.com.atsinformatica.prospect.adapter;

import java.util.List;

import br.com.atsinformatica.prospect.models.Cliente;
import br.com.atsinformatica.prospect.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterEmails extends BaseAdapter {

	private LayoutInflater mInflater;
    private List<Cliente> itens;
	
    public AdapterEmails(Context context, List<Cliente> itens) {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }
    
	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Cliente getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//Pega o item de acordo com a posção.
        Cliente item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_list, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.listCod)).setText(Integer.toString(item.getId()));
        ((TextView) view.findViewById(R.id.listNome)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.listEmail)).setText(item.getEmail_principal());

        return view;
	}

}
