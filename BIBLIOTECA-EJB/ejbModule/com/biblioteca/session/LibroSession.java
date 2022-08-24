package com.biblioteca.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import com.biblioteca.entidad.Libro;

@Stateless
public class LibroSession {
	
	@PersistenceContext
	EntityManager em;
	
	//Consultar todos los libros de la BD
	public List<Libro> consultarLibros(){
		String jpql = "SELECT l FROM Libro l ORDER BY l.codigo";
		Query q = em.createQuery(jpql);
		List<Libro> libros =  q.getResultList();
		return libros;
	}
	
	//Consultar libros por nombre
		public List<Libro> consultarLibrosPorNombre(String nombre){
			String jpql = "SELECT l FROM Libro l WHERE UPPER(l.nombre) LIKE :nombre ORDER BY l.codigo";
			Query q = em.createQuery(jpql);
			q.setParameter("nombre","%"+ nombre.toUpperCase() +"%");
			List<Libro> libros = q.getResultList();
			return libros;
		}
	
	//Busca el libro por código en la BD
	public Libro buscarPorCodigo(Integer codigo) {
		Libro libro = em.find(Libro.class, codigo);
		return libro;
	}
	
	//Inserta un libro en la BD
	public Libro incluir(Libro libro) {
		em.persist(libro);
		em.refresh(libro);
		return libro;
	}
	
	//Edita una libro en la BD
	public Libro editar(Libro libro) {
		libro = em.merge(libro);
		return libro;
	}
	
	//Incluye o edita un libro dependiendo de si existe o no
	public Libro actualizar(Libro libro) {
		Libro libroActualizado = null;
		Libro libroBuscar = buscarPorCodigo(libro.getCodigo());
		if (libroBuscar == null) {
			libroActualizado = incluir(libro);
		}else {
			libroActualizado = editar(libro);
		}
		return libroActualizado;
	}
	
	public void eliminar(Integer codigo) {
		em.remove(codigo);
	}

}
