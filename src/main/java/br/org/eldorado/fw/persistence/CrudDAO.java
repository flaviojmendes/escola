package br.org.eldorado.fw.persistence;


public interface CrudDAO {

	public void add(Object entity);
	public void update(Object entity);
	public void remove(Object entity);
	public <E> E saveOrUpdate(E entidade);
	public void flush();
	public void removeAll(Class<?> class1, String param, Object paramValue);
	public void commit();
}
