package br.org.eldorado.fw.persistence.producer;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Interceptor @Transaction
public class TransactionInterceptor {

	@Inject
	private EntityManager entityManager;

	@AroundInvoke
	public Object intercept(InvocationContext icx) throws Exception{
		Object result = null;
		try{
			entityManager.getTransaction().begin();

			result = icx.proceed();

			entityManager.getTransaction().commit();
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw e;
		}
	}
}
