package br.com.atsinformatica.prospect.adapter;

import br.com.atsinformatica.prospect.Contato;
import br.com.atsinformatica.prospect.DadosPrincipais;
import br.com.atsinformatica.prospect.Endereco;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPageAdapter extends FragmentPagerAdapter {
	 
    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new DadosPrincipais();
        case 1:
            // Games fragment activity
            return new Endereco();
        case 2:
            // Movies fragment activity
            return new Contato();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}
