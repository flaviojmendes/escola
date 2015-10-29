package br.org.eldorado.fw.persistence.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProducer {

	@Produces @ApplicationScoped
	public EntityManagerFactory createFactory(){
		return Persistence.createEntityManagerFactory("default"); //TODO Parametrizar
	}
	static int allConn = 0;
	static int reqConn = 0;

	public static void printInfo(){
		System.out.println("Todas Conexoes: "+allConn+ " || Request Connections: "+reqConn );
	}

	@Produces @RequestScoped
	public EntityManager criaEntityManager(EntityManagerFactory factory) {
		EntityManager em = factory.createEntityManager();
		//		em.setFlushMode(FlushModeType.COMMIT);
		//		em.getTransaction().begin();
		allConn++;
		reqConn++;
		printInfo();
		return em;
	}


	public void finaliza(@Disposes EntityManager entityManager) {
		allConn--;
		printInfo();
		entityManager.close();
	}
}
