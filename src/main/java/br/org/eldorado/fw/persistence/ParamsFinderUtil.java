package br.org.eldorado.fw.persistence;

import br.org.eldorado.fw.persistence.entity.EntitySupport;

public interface ParamsFinderUtil {

	public Param[] getParamsByExample(EntitySupport entityExample);
}
