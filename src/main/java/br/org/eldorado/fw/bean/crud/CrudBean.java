package br.org.eldorado.fw.bean.crud;

import java.util.Collection;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import br.org.eldorado.fw.bean.BeanSupport;
import br.org.eldorado.fw.bean.crud.interceptor.form.AfterAdd;
import br.org.eldorado.fw.bean.crud.interceptor.form.AfterRemove;
import br.org.eldorado.fw.bean.crud.interceptor.form.AfterUpdate;
import br.org.eldorado.fw.bean.crud.interceptor.form.BeforeAdd;
import br.org.eldorado.fw.bean.crud.interceptor.form.BeforeRemove;
import br.org.eldorado.fw.bean.crud.interceptor.form.BeforeUpdate;
import br.org.eldorado.fw.bean.crud.interceptor.pageRedirect.AfterShowAddPage;
import br.org.eldorado.fw.bean.crud.interceptor.pageRedirect.AfterShowDetailPage;
import br.org.eldorado.fw.bean.crud.interceptor.pageRedirect.AfterShowUpdatePage;
import br.org.eldorado.fw.bean.crud.interceptor.pageRedirect.BeforeShowAddPage;
import br.org.eldorado.fw.bean.crud.interceptor.pageRedirect.BeforeShowDetailPage;
import br.org.eldorado.fw.bean.crud.interceptor.pageRedirect.BeforeShowUpdatePage;
import br.org.eldorado.fw.persistence.entity.EntitySupport;
import br.org.eldorado.fw.service.CrudService;
import br.org.eldorado.fw.service.FinderService;

public abstract class CrudBean<T extends EntitySupport> extends BeanSupport{

	protected T entity;
	protected T entityFilter;

	private Object idEntity;
	public final static String ID_PK_PARAM_NAME = "paramIdEntity";

	@Inject
	private FinderService finderService;

//	@Inject
//	private CrudService crudService;

	protected Collection<T> entityList;

	protected abstract T getNewEntityInstance();
	
	public abstract String getUrlUpdate();
	public abstract String getUrlIncluir();
	public abstract String getUrlDetalhar();
	public abstract String getUrlManter();

	public abstract CrudService getCrudService();

	public CrudBean(){
		this.entity = getNewEntityInstance();
		this.entityFilter = getNewEntityInstance();
	}

	public void filtrarPesquisa(){
		if (entityFilter != null){
			this.entityList = finderService.findAllByExample(entityFilter);
		}
	}

	private CrudService getCrudServiceImpl(){
		CrudService crudService = getCrudService();
		if (crudService == null){
//			crudService = this.crudService;
		}
		return crudService;
	}
	
	/**
	 * Executa a acaoo de incluir do banco
	 * @return
	 * @throws Throwable 
	 */
	public String add() throws Throwable{
		try{
			interceptMethod(BeforeAdd.class,entity);
			boolean incluido = getCrudServiceImpl().add(entity);
			interceptMethod(AfterAdd.class,entity);
	
			if (incluido){
				addMsgInfo("incluido.sucesso");
				this.entity = getNewEntityInstance();
				return getUrlManter();//TODO tirar essa gambiarra!
				//return redirectDetail();
			}
		}catch(ConstraintViolationException ex){
            for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
                addMsgError(cv.getMessage());
            }
		}
		return null;
	}
	
	/**
	 * Executa a acao de alterar do banco
	 * @return
	 */
//	public String saveOrUpdate(){
//		interceptMethod(BeforeSaveOrUpdate.class,entity);
//		boolean savedOrUpdated = getCrudService().saveOrUpdate(entity);
//		interceptMethod(AfterSaveOrUpdate.class,entity);
//		if (savedOrUpdated){
//			addMsgInfo("alterado.sucesso");
//			return redirectDetail();
//		}
//		return null;
//	}
	

	/**
	 * Executa a acao de alterar do banco
	 * @return
	 * @throws Throwable 
	 */
	public String update() throws Throwable{
		try{
			interceptMethod(BeforeUpdate.class,entity);
			boolean alterado = getCrudServiceImpl().update(entity);
			interceptMethod(AfterUpdate.class,entity);
			if (alterado){
				addMsgInfo("alterado.sucesso");
				return redirectDetailPage(entity);
			}
		}catch(ConstraintViolationException ex){
	        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
	            addMsgError(cv.getMessage());
	        }
		}
		return null;
	}

	/**
	 * Executa a acao de Remover do banco.
	 * @return
	 * @throws Throwable 
	 */
	public String remove() throws Throwable{
		try{
			interceptMethod(BeforeRemove.class,entity);
			boolean excluido = getCrudServiceImpl().remove(entity);
			interceptMethod(AfterRemove.class,entity);
			if (excluido){
				addMsgInfo("removido.sucesso");
				return getUrlManter();
			}
		}catch(ConstraintViolationException ex){
            for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
                addMsgError(cv.getMessage());
            }
		}
		return null;
	}
	
	public String remove(T entidade) throws Throwable{
		this.entity = entidade;
		return remove();
	}


	private boolean isAjaxRequest() {
		return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
	}

	/*	*//**
	 * Recebe como parametro uma chave, faz a traducao da chave para a mensagem
	 * olhando o bundle e a adiciona no escopo {@link Flash} da aplicacao.
	 *
	 * @param chave
	 * @param severidade
	 *//*
	public void addMessageFromBundleInFlash(Severity severidade, String chave) {
		Flash flash = getFacesContext().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		getFacesContext().addMessage(null,
				new FacesMessage(severidade, null, bundle.getString(chave)));
	}
	  */	 

	/*
	 * -------------------------------------------------
	 * Redirecionamentos para pagina de inclus�o
	 * -------------------------------------------------
	 */
	
	public void showAddPage() throws Throwable {
		interceptMethod(BeforeShowAddPage.class, entity);
		entity = getNewEntityInstance();
		interceptMethod(AfterShowAddPage.class, entity);
	}

	/*
	 * -------------------------------------------------
	 * Redirecionamentos para pagina de altera��o
	 * -------------------------------------------------
	 */
	public void redirectUpdatePage(T entidade) throws Throwable {
		interceptMethod(BeforeShowUpdatePage.class, entidade);
		setIdEntity(getIdEntityParam());
		this.entity = entidade;
		interceptMethod(AfterShowUpdatePage.class, entidade);
	}

	public void showUpdatePage() throws Throwable {
		interceptMethod(BeforeShowUpdatePage.class, entity);
		setIdEntity(getIdEntityParam());
		this.entity = (T) getFinderService().find(entity.getClass(), getIdEntity());
		interceptMethod(AfterShowUpdatePage.class, entity);
	}
	
	/*
	 * -------------------------------------------------
	 * Redirecionamentos para pagina de detalhe
	 * -------------------------------------------------
	 */

	public void showDetailPage() throws Throwable{
		interceptMethod(BeforeShowDetailPage.class, entity);
		setIdEntity(getIdEntityParam());
		this.entity = (T) getFinderService().find(entity.getClass(), getIdEntity());
		interceptMethod(AfterShowDetailPage.class, entity);
	}


	public String redirectDetailPage(T entidade) throws Throwable{
		//		if (isAjaxRequest()){
		//			return null;
		//		}
		interceptMethod(BeforeShowDetailPage.class, entidade);
		this.entity = entidade;
		interceptMethod(AfterShowDetailPage.class, entidade);
		return getUrlDetalhar();
	}
	
	
	protected Object getIdEntityParam(){
		try{
		if (idEntity != null){
			return Integer.parseInt(idEntity.toString());
		}
		Object param = getRequestParam(ID_PK_PARAM_NAME);
		if (param != null && !param.equals("")){
			return Integer.parseInt(param.toString()); //TODO Fazer um cast automatico de acordo com o field pk da entidade
		}
		}catch (NumberFormatException e) {
			//TODO Logar a tentativa de acesso.
		}
		return null;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public T getEntityFilter() {
		return entityFilter;
	}

	public void setEntityFilter(T entityFilter) {
		this.entityFilter = entityFilter;
	}

	public Collection<T> getEntityList() {
		return entityList;
	}

	public void setEntityList(Collection<T> entityList) {
		this.entityList = entityList;
	}

	protected FinderService getFinderService() {
		return finderService;
	}

	public Object getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Object idEntity) {
		this.idEntity = idEntity;
	}

	public String getIdPkParamName() {
		return ID_PK_PARAM_NAME;
	}
}
