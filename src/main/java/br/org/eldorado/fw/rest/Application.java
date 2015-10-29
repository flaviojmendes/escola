package br.org.eldorado.fw.rest;


import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
 
/**
 * Registers the components to be used by the JAX-RS application  
 * 
 * @author ama
 *
 */
@ApplicationPath(value = "app")
public class Application extends javax.ws.rs.core.Application {
 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set<Object> singletons = new HashSet();
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Set<Class<?>> empty = new HashSet();

    public Application() {
        // ADD YOUR RESTFUL RESOURCES HERE
//        this.singletons.add(new LoginService());
    }

    public Set<Class<?>> getClasses()
    {
        return this.empty;
    }

    public Set<Object> getSingletons()
    {
        return this.singletons;
    }
}