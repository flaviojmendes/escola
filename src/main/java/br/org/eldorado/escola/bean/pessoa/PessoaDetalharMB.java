package br.org.eldorado.escola.bean.pessoa;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.org.eldorado.escola.persistence.entity.Pessoa;
import br.org.eldorado.fw.bean.cc.DetailFragment;
import br.org.eldorado.fw.jsf.navigation.NavigationIntent;
import br.org.eldorado.fw.service.FinderService;

@Named
@ViewScoped
public class PessoaDetalharMB extends DetailFragment<Pessoa> {

	@Inject
	private FinderService finderService;
	

	@Override
	public Class<Pessoa> getEntityClass() {
		return Pessoa.class;
	}


	@Override
	public FinderService getFinderService() {
		
		return finderService;
	}


	@Override
	public NavigationIntent getUrl() {
		return new NavigationIntent("/pessoa/pessoaDetalhar.xhtml");
	}
	
}
