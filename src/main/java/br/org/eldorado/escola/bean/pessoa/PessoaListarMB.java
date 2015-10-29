package br.org.eldorado.escola.bean.pessoa;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.org.eldorado.escola.persistence.entity.Pessoa;
import br.org.eldorado.fw.bean.cc.FilteredList;
import br.org.eldorado.fw.service.FinderService;

@Named
@ViewScoped
public class PessoaListarMB extends FilteredList<Pessoa> {

	@Inject
	private FinderService finderService;
	
	public List<Pessoa> listarUsuarios() {
		List<Pessoa> pessoas = (List<Pessoa>) finderService.findAll(Pessoa.class); 
		return pessoas;
		
	}

	@Override
	public Class<Pessoa> getEntityClass() {
		return Pessoa.class;
	}
	
}
