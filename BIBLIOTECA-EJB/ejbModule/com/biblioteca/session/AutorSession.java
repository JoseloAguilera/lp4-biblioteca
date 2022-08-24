package com.biblioteca.session;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.biblioteca.entidad.Autor;

@Stateless
public class AutorSession {

	@PersistenceContext
	EntityManager em;
	
	//Consultar todos los autores de la BD
	public Map<String, Object> consultarAutores(){
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			String jpql = "SELECT  a FROM Autor a ORDER BY a.codigo";
			Query q = em.createQuery(jpql);
			List<Autor> autores =  q.getResultList();
			retorno.put("success", true);
			retorno.put("result", autores);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Consultar autores por nombre
		public Map<String, Object> consultarAutoresPorNombre(String nombre ){
			Map<String,Object> retorno = new HashMap<String,Object>();
			try {
				String jpql = "SELECT  a FROM Autor a WHERE UPPER(a.nombre) LIKE :nombre ORDER BY a.codigo";
				Query q = em.createQuery(jpql);
				q.setParameter("nombre","%"+ nombre.toUpperCase() +"%");
				List<Autor> autores =  q.getResultList();
				retorno.put("success", true);
				retorno.put("result", autores);
			} catch (Exception e) {
				retorno.put("success", false);
				retorno.put("error", e.getMessage());
			}
			
			return retorno;
		}
	
	//Busca al autor por código en la BD
	public Autor buscarPorCodigo(Integer codigo) {
		Autor autor= em.find(Autor.class, codigo);
		return autor;
	}
	
	//Inserta un autor en la BD
	public Map<String, Object> incluir(Autor autor) {
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			autor.setCodigo(null);
			em.persist(autor);
			em.refresh(autor);
			retorno.put("success", true);
			retorno.put("result", autor);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Edita un autor en la BD
	public Map<String, Object> editar(Autor autor) {
		Map<String,Object> retorno = new HashMap<String,Object>();
		try {
			autor = em.merge(autor);
			retorno.put("success", true);
			retorno.put("result", autor);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Incluye o edita un autor dependiendo de si existe o no
	public Map<String,Object> actualizar(Autor autor) {
		Map<String, Object> autorActualizado= new HashMap<String,Object>();
		try {
			Autor autorBuscar = buscarPorCodigo(autor.getCodigo());
			if (autorBuscar == null) {
				autorActualizado = incluir(autor);
			}else {
				autorActualizado = editar(autor);
			}
		} catch (Exception e) {
			autorActualizado.put("success", false);
			autorActualizado.put("error", e.getMessage());
		}
		
		return autorActualizado;
	}
	
	public Map<String,Object> eliminar(Integer codigo) {
		Map<String,Object> retorno = new HashMap<String,Object>();
		//falta validación si no existe el codigo a eliminar
		try {
			Autor autorBuscar = em.find(Autor.class, codigo);
			em.remove(autorBuscar);
			retorno.put("success", "True");
			retorno.put("result", "Elimado exitosamente");
		} catch (Exception e) {
			retorno.put("success", "False");
			retorno.put("error", e.getMessage());
		}
		return retorno;		
	}
}
