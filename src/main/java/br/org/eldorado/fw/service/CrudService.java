package br.org.eldorado.fw.service;


public interface CrudService {

	public boolean add(Object entity);
	public boolean update(Object entity);
	public boolean remove(Object entity);
	public <E> E saveOrUpdate(E entity);
	public boolean removeAll(Class<?> class1, String param, Object paramValue);
}
