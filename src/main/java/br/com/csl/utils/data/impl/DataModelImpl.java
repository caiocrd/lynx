package br.com.csl.utils.data.impl;

import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.entity.PersistentEntity;


/**
 * @author Bruno
 *
 * @param <T>
 * 
 * Use this class along with Spring to inject the Paginator.
 */
public class DataModelImpl<T extends PersistentEntity> extends AbstractDataModel<T> implements DataModel<T> {

	private static final long serialVersionUID = -4735057278252715465L;

}
