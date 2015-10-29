package br.org.eldorado.fw.bean;

import java.io.Serializable;

/*@ref Representa uma activity como no android */

/*
 * O Bean representa exatamente a 1 tela que o usuário está visualizando.
 * 
 */
public abstract class Bean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** Representa a página que será mostrada para o usuário. */
	private String pageView;
	
	/**
	 * Método chamado para abrir a página.
	 */
	public abstract void onCreate();

	
	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}
	
	

}
