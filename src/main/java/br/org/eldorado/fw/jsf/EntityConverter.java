package br.org.eldorado.fw.jsf;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import br.org.eldorado.fw.persistence.entity.EntitySupport;


@FacesConverter("br.com.owlabs.enterprise.fw.jsf.EntityConverter")
public class EntityConverter implements javax.faces.convert.Converter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String KEY_MAP_ENTITYS = "owlabs.faces.converter.entityConverter.mapEntity";

	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		if (value != null) {
			return getMapEntitys(component).get(value);
		}
		return null;
	}

	public String getAsString(FacesContext ctx, UIComponent component,
			Object entity) {

		if (entity != null && ! "".equals(entity)) {
			try{
//				String id = this.getId(getClazz(ctx, component), entity);
				
				Object idEntitySupport = ((EntitySupport)entity).getId();
				String id = idEntitySupport.toString();

				if (id != null) {
					this.addAttribute(component, id, entity);
					return id;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Adiciona a entidade do combobox no mapa do componente.
	 * @param component
	 * @param id
	 * @param o
	 */
	private void addAttribute(UIComponent component, String id, Object o) {
		Map<String, Object> mapEntitys = getMapEntitys(component);
		mapEntitys.put(id, o);
	}

	/**
	 * Recupera o mapa de entidades da combobox.
	 * @param component
	 * @return
	 */
	private Map<String, Object> getMapEntitys(UIComponent component) {
		@SuppressWarnings("unchecked")
		Map<String,Object> mapaEntitys = (Map<String, Object>) component.getAttributes().get(KEY_MAP_ENTITYS);
		if (mapaEntitys == null){
			mapaEntitys = new HashMap<String, Object>();
			component.getAttributes().put(KEY_MAP_ENTITYS, mapaEntitys);
		}
		return mapaEntitys;
	}

	//	@Override
	//	public Object getAsObject(FacesContext context, UIComponent component,
	//			String value) {
	//		if (value != null) {
	//			return getFromViewMap(context, component, value);
	//		}
	//		return null;
	//	}
	//
	//	public String getAsString(FacesContext ctx, UIComponent component,  
	//			Object obj) {  
	//		try {
	//			if (obj != null && !"".equals(obj)) {
	//				String id;
	//
	//
	//				id = this.getId(getClazz(ctx, component), obj);
	//
	//				if (id == null) {
	//					id = "";
	//				}
	//				id = id.trim();
	//				putInViewMap(id, ctx, component, obj);
	//				return id;
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		} 
	//		return null;  
	//	}  
	//
	//	public void putInViewMap(String id, FacesContext context, UIComponent component, Object object) {
	//		if (object != null) {
	//			Map objectsFromClass = (Map) context.getViewRoot().getViewMap().get(ID_PREFIX + component.getId());
	//			if (objectsFromClass == null) {
	//				objectsFromClass = new HashMap();
	//				context.getViewRoot().getViewMap().put(ID_PREFIX + component.getId(), objectsFromClass);
	//			}
	//			objectsFromClass.put(id, object);
	//		}
	//	}
	//
	//	public Object getFromViewMap(FacesContext context, UIComponent component, String value) {
	//		if (value != null && !value.trim().isEmpty()) {
	//			Map objectsFromClass = (Map) context.getViewRoot().getViewMap().get(ID_PREFIX + component.getId());
	//			if (objectsFromClass != null) {
	//				return objectsFromClass.get(value);
	//			}
	//		}
	//		return null;
	//	}
	//


	/*private String getId(Class<?> clazz, Object obj) throws SecurityException,  
	NoSuchFieldException, IllegalArgumentException,  
	IllegalAccessException {  

		List<Class<?>> hierarquiaDeClasses = this.getHierarquiaDeClasses(clazz);  

		for (Class<?> classeDaHierarquia : hierarquiaDeClasses) {  
			for (Field field : classeDaHierarquia.getDeclaredFields()) {  
				if ((field.getAnnotation(Id.class)) != null  
						|| field.getAnnotation(EmbeddedId.class) != null) {  
					Field privateField = classeDaHierarquia  
							.getDeclaredField(field.getName());  
					privateField.setAccessible(true);  
					if (privateField.get(clazz.cast(obj)) != null) {  

						return (String) field.getType()  
								.cast(privateField.get(clazz.cast(obj)))  
								.toString();  
					}  
				}  
			}  
		}  
		return null;  
	}  

	private Class<?> getClazz(FacesContext facesContext, UIComponent component) {  
		return component.getValueExpression("value").getType(  
				facesContext.getELContext());  
	}  
	//
	public List<Class<?>> getHierarquiaDeClasses(Class<?> clazz) {  

		List<Class<?>> hierarquiaDeClasses = new ArrayList<Class<?>>();  
		Class<?> classeNaHierarquia = clazz;  
		while(classeNaHierarquia != Object.class) {  
			hierarquiaDeClasses.add(classeNaHierarquia);  
			classeNaHierarquia = classeNaHierarquia.getSuperclass();  

		}  
		return hierarquiaDeClasses;  
	}   */
}