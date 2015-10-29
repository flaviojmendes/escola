package br.org.eldorado.fw.bean.cc;

import br.org.eldorado.fw.jsf.navigation.NavigationIntent;
import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.service.CrudService;
import br.org.eldorado.fw.service.FinderService;


/**
 * Classe componentizadora, que auxilia nas telas de listagem.
 * @author flaviojmendes
 *
 */
public abstract class CrudFragment<T extends EntitySupport> {

	protected T entity;
	
	public abstract Class<T> getEntityClass();
	public abstract FinderService getFragmentFinderService();
	public abstract CrudService getFragmentCrudService();
	public abstract NavigationIntent getDetailUrl();
	public abstract NavigationIntent getUpdateUrl();
	public abstract NavigationIntent getAddUrl();
	public abstract NavigationIntent getListUrl();
	private Long id;
	
	public CrudFragment(){

	}
	
	/**
	 * Fragment de lista de moradores
	 */
	private FilteredList<T> listFragment = new FilteredList<T>() {
		
		@Override
		public FinderService getFinderService() {
			return getFragmentFinderService();
		}

		@Override
		public Class<T> getEntityClass() {
			return CrudFragment.this.getEntityClass();
		}
	};
	
	/**
	 * Fragment de detalhe de moradores
	 */
	private DetailFragment<T> detailFragment = new DetailFragment<T>() {

		@Override
		public FinderService getFinderService() {
			return getFragmentFinderService();
		}

		@Override
		public Class<T> getEntityClass() {
			return CrudFragment.this.getEntityClass();
		}

		@Override
		public NavigationIntent getUrl() {
			return getDetailUrl();
		}
	};

	/**
	 * Fragment de incluir morador
	 */
	private AddFragment<T> addFragment = new AddFragment<T>() {
		
		@Override
		public Class<T> getEntityClass() {
			return CrudFragment.this.getEntityClass();
		}
		
		@Override
		public NavigationIntent getUrl() {
			return getAddUrl();
		}

		@Override
		public CrudService getCrudService() {
			return getFragmentCrudService();
		}

		@Override
		public NavigationIntent getCallBackUrl() {
			return getListUrl();
		}
	};

	/**
	 * Fragment de alterar morador
	 */
	private UpdateFragment<T> updateFragment = new UpdateFragment<T>() {
		
		@Override
		public FinderService getFinderService() {
			return getFragmentFinderService();
		}
		
		@Override
		public Class<T> getEntityClass() {
			return CrudFragment.this.getEntityClass();
		}
		
		@Override
		public NavigationIntent getUrl() {
			return getUpdateUrl();
		}
		
		@Override
		public CrudService getCrudService() {
			return getFragmentCrudService();
		}
		
		@Override
		public NavigationIntent getCallBackUrl() {
			return getListUrl();
		}
	};
	
	/** Just a Bunch of Getters & Setters **/
	
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FilteredList<T> getListFragment() {
		return listFragment;
	}
	public void setListFragment(FilteredList<T> listFragment) {
		this.listFragment = listFragment;
	}
	public DetailFragment<T> getDetailFragment() {
		return detailFragment;
	}
	public void setDetailFragment(DetailFragment<T> detailFragment) {
		this.detailFragment = detailFragment;
	}
	public AddFragment<T> getAddFragment() {
		return addFragment;
	}
	public void setAddFragment(AddFragment<T> addFragment) {
		this.addFragment = addFragment;
	}
	public UpdateFragment<T> getUpdateFragment() {
		return updateFragment;
	}
	public void setUpdateFragment(UpdateFragment<T> updateFragment) {
		this.updateFragment = updateFragment;
	}
}