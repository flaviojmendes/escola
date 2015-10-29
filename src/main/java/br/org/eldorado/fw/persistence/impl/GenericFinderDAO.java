package br.org.eldorado.fw.persistence.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import br.org.eldorado.fw.persistence.FinderDAO;
import br.org.eldorado.fw.persistence.OrderBy;
import br.org.eldorado.fw.persistence.Param;
import br.org.eldorado.fw.persistence.ParamsFinderUtil;
import br.org.eldorado.fw.persistence.entity.EntitySupport;

/**
 * DAO Genérico para busca simples de entidades.
 * @author flaviojmendes
 *
 */
public class GenericFinderDAO extends DAOSuporte implements FinderDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ParamsFinderUtil paramsFinderUtil = new ParamsFinderUtilImpl();

	@SuppressWarnings("unchecked")
	public <E> Collection<E> findAll(Class<E> entityClass){
		return createQuery("from "+entityClass.getSimpleName()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <E> Collection<E> findAll(Class<E> entityClass,OrderBy orderBy){
		return createQuery("from "+entityClass.getSimpleName() + " order by "+orderBy.getAtribute()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <E> E findByProperty(Class<E> entityClass,String propriedade,Object valor){
		Param param = new Param(propriedade, valor);
		String sql = buildSqlSelectQuery(entityClass, null, param);
		Query q = createQuery(sql.toString());
		q.setParameter(param.getPropertyWhere(), valor);
		List<E> result = q.getResultList();
		if (!result.isEmpty()){
			return (E) result.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, String propriedade, Object valor){
		Param param = new Param(propriedade, valor);
		String sql = buildSqlSelectQuery(entityClass, null, param);
		Query q = createQuery(sql);
		q.setParameter(param.getPropertyWhere(), valor);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, String propriedade,Object valor,OrderBy orderBy){
		Param param = new Param(propriedade, valor);
		String sql = buildSqlSelectQuery(entityClass, orderBy, param);

		Query q = createQuery(sql.toString());
		q.setParameter(param.getPropertyWhere(), valor);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E findByProperty(Class<E> entityClass, Param... params) {
		String sql = buildSqlSelectQuery(entityClass, null, params);
		Query q = createQuery(sql.toString());
		for (Param param : params){
			if (param.getValue() != null){
				q.setParameter(param.getPropertyWhere(), param.getValue());
			}
		}
		List<E> result = q.getResultList();
		if (!result.isEmpty()){
			return (E) result.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <E> E findByProperty(String sql, Param... params) {
		Query q = createQuery(sql);
		for (Param param : params){
			if (param.getValue() != null){
				q.setParameter(param.getPropertyWhere(), param.getValue());
			}
		}
		List<E> result = q.getResultList();
		if (!result.isEmpty()){
			return (E) result.get(0);
		}
		return null;
	}

	@Override
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, Param... params) {
		return findAllByProperty(entityClass, new OrderBy(null), params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> Collection<E> findAllByProperty(Class<E> entityClass, OrderBy orderBy, Param... params) {

		String sql = buildSqlSelectQuery(entityClass, orderBy, params);
		Query q = createQuery(sql.toString());
		for (Param param : params){
			switch (param.getOperator()) {

			case LIKE:
				try{
				q.setParameter(param.getPropertyWhere(), "%"+param.getValue()+"%");
				} catch (Exception e) {
					// TODO: Verificar melhor maneira
				}
				break;

			default:
				try {
					q.setParameter(param.getPropertyWhere(), param.getValue());
				} catch (Exception e) {
					// TODO: Verificar melhor maneira
				}
				break;

			}

		}
		return q.getResultList();
	}

	private <E> String buildSqlSelectQuery(Class<E> entityClass, OrderBy orderBy, Param... params){
		//TODO Refactor
		StringBuilder sql = new StringBuilder("from ")
		.append(entityClass.getSimpleName());
		if (params.length > 0){
			sql.append(" where ");
			for (int i=0;i<params.length;i++){
				Param param = params[i];
				if (param.getValue() != null && !(param.getValue() instanceof Collection)){
					sql.append(param.getProperty())
					.append(param.getOperator().getValue());
					sql.append("  :").append(param.getPropertyWhere());
					if (i != (params.length-1)) {
						sql.append(" and ");
					}
				}
				if (param.getValue() == null && param.getOperator()!=null){
					sql.append(param.getProperty())
					.append(param.getOperator().getValue()).append(" and ");
				}
			}
			if(sql.toString().endsWith(" and ")) {
				sql = new StringBuilder(sql.toString().substring(0, sql.toString().lastIndexOf(" and ")));
			}
			if(sql.toString().endsWith(" where ")) {
				sql = new StringBuilder(sql.toString().substring(0, sql.toString().lastIndexOf(" where ")));
			}
		}
		if (orderBy != null && orderBy.getAtribute() != null && !orderBy.getAtribute().equals("")){
			sql.append(" order by "+orderBy.getAtribute());
			if(orderBy.getType() != null) {
				sql.append(" " + orderBy.getType().name());
			}
		}

		return sql.toString();
	}


	@SuppressWarnings("unchecked")
	public <E> Collection<E> findAllByExample(EntitySupport entity){
		Param[] params = paramsFinderUtil.getParamsByExample(entity);
		return (Collection<E>) findAllByProperty(entity.getClass(), params);
	}

	@Override
	public void update(Object entidade){
		openTransaction();
		merge(entidade); 
		commit();
	}
	
	@Override
	public void add(Object entity) {
		openTransaction();
		persist(entity); 
		commit();
	}

	@Override
	public void remove(Object entity) {
		openTransaction();
		entity = merge(entity);
		super.remove(entity);
		commit();
	}

}
