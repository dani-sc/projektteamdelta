package at.itb13.teamF.persistence;
import java.util.Collection;
import java.util.LinkedList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import at.itb13.teamD.technicalServices.exceptions.PersistenceFacadeException;
import at.itb13.teamD.technicalServices.persistenceFacade.PersistenceFacadeProvider;

/**
 * Implementation of IFacade of Team F
 * 
 * @author Andrew Sparr
 * @date 18.05.2015
 */
import at.oculus.teamf.databaseconnection.session.exception.ClassNotMappedException;
import at.oculus.teamf.domain.entity.interfaces.IDomain;
import at.oculus.teamf.persistence.IFacade;
import at.oculus.teamf.persistence.exception.BadConnectionException;
import at.oculus.teamf.persistence.exception.DatabaseOperationException;
import at.oculus.teamf.persistence.exception.FacadeException;
import at.oculus.teamf.persistence.exception.NoBrokerMappedException;
import at.oculus.teamf.persistence.exception.search.InvalidSearchParameterException;
import at.oculus.teamf.persistence.exception.search.SearchInterfaceNotImplementedException;

public class PersistenceFacadeTeamF implements IFacade{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getById(Class clazz, int id) throws BadConnectionException,
			NoBrokerMappedException, DatabaseOperationException {
		return (T) PersistenceFacadeProvider.getPersistenceFacade().getById(id, clazz);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getAll(Class clazz) throws BadConnectionException,
			NoBrokerMappedException, DatabaseOperationException {
		return (Collection<T>) PersistenceFacadeProvider.getPersistenceFacade().getAll(clazz);
	}

	@Override
	public boolean save(IDomain obj) throws BadConnectionException,
			NoBrokerMappedException, DatabaseOperationException {
		return PersistenceFacadeProvider.getPersistenceFacade().makePersistent(obj);
	}

	@Override
	public boolean saveAll(Collection<IDomain> obj)
			throws BadConnectionException, NoBrokerMappedException,
			DatabaseOperationException {
		
		boolean isSuccessful = true;
		for(IDomain dom : obj){
			if(!PersistenceFacadeProvider.getPersistenceFacade().makePersistent(dom)){
				isSuccessful = false;
			}
		}
		return isSuccessful;
	}

	@Override
	public boolean delete(IDomain obj) throws BadConnectionException,
			NoBrokerMappedException, InvalidSearchParameterException,
			DatabaseOperationException, NotImplementedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(Collection<IDomain> obj) throws FacadeException,
			ClassNotMappedException, NotImplementedException{
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> search(Class clazz, String... search)
			throws SearchInterfaceNotImplementedException,
			BadConnectionException, NoBrokerMappedException,
			InvalidSearchParameterException, DatabaseOperationException {
			Collection<T> searchResults = new LinkedList<T>();
			String string = "";
			
			for (String str:search){
				string = string +" "+str;
			}
			try {
				searchResults.addAll(PersistenceFacadeProvider.getPersistenceFacade().searchFor(clazz, string));
			} catch (PersistenceFacadeException e) {
				
				e.printStackTrace();
			}
		return searchResults;
	}

}
