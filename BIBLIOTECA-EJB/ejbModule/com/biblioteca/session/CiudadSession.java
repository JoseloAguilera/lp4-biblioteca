package com.biblioteca.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.biblioteca.entidad.Autor;
import com.biblioteca.entidad.Ciudad;

@Stateless
public class CiudadSession {

	@PersistenceContext
	EntityManager em;
	
	//Consultar todos las ciudades de la BD
	public Map<String, Object> consultarCiudades(){
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			String jpql = "SELECT c FROM Ciudad c ORDER BY c.codigo";
			Query q = em.createQuery(jpql);
			List<Ciudad> ciudades =  q.getResultList();
			retorno.put("success", true);
			retorno.put("result", ciudades);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Consultar ciudades por nombre
		public Map<String, Object> consultarCiudadPorNombre(String nombre ){
			Map<String,Object> retorno = new HashMap<String,Object>();
			try {
				String jpql = "SELECT  c FROM Ciudad c WHERE UPPER(c.nombre) LIKE :nombre ORDER BY c.codigo";
				Query q = em.createQuery(jpql);
				q.setParameter("nombre","%"+ nombre.toUpperCase() +"%");
				List<Ciudad> ciudad =  q.getResultList();
				retorno.put("success", true);
				retorno.put("result", ciudad);	
			} catch (Exception e) {
				retorno.put("success", false);
				retorno.put("error", e.getMessage());
			}
			
			return retorno;
		}
	
	//Busca la ciudad por código en la BD
	public Ciudad buscarPorCodigo(Integer codigo) {
		Ciudad ciudad= em.find(Ciudad.class, codigo);
		return ciudad;
	}
	
	//Inserta una ciudad en la BD
	public Map<String, Object> incluir(Ciudad ciudad) {
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			ciudad.setCodigo(null);
			em.persist(ciudad);
			em.refresh(ciudad);
			retorno.put("success", true);
			retorno.put("result", ciudad);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Edita una ciudad en la BD
	public Map<String, Object> editar(Ciudad ciudad) {
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			ciudad = em.merge(ciudad);
			retorno.put("success", true);
			retorno.put("result", ciudad);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Incluye o edita una ciudad dependiendo de si existe o no
	public Map<String, Object> actualizar(Ciudad ciudad) {
		Map<String,Object> ciudadActualizada = new HashMap<String,Object>();
		try {
			Ciudad ciudadBuscar = buscarPorCodigo(ciudad.getCodigo());
			if (ciudadBuscar == null) {
				ciudadActualizada = incluir(ciudad);
			}else {
				ciudadActualizada = editar(ciudad);
			}
		} catch (Exception e) {
			ciudadActualizada.put("success", false);
			ciudadActualizada.put("error", e.getMessage());
		}
		return ciudadActualizada;
	}
	
	public Map<String, Object> eliminar(Integer codigo) {
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			Ciudad ciudadBuscar = em.find(Ciudad.class, codigo);
			em.remove(ciudadBuscar);
			retorno.put("success", "True");
			retorno.put("result", "Elimado exitosamente");
		} catch (Exception e) {
			retorno.put("success", "False");
			retorno.put("error", e.getMessage());
		}
		return retorno;
		
	}
}
