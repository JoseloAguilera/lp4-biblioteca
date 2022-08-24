package com.biblioteca.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import com.biblioteca.entidad.Cliente;

@Stateless
public class ClienteSession {

	@PersistenceContext
	EntityManager em;
	
	//Consultar todos los clientes de la BD
	public List<Cliente> consultarClientes(){
		String jpql = "SELECT c FROM Cliente c ORDER BY c.codigo";
		Query q = em.createQuery(jpql);
		List<Cliente> clientes =  q.getResultList();
		return clientes;
	}
	
	//Consultar clientes por nombre
		public List<Cliente> consultarClientesPorNombre(String nombre){
			String jpql = "SELECT c FROM Cliente c WHERE UPPER(c.nombre) LIKE :nombre ORDER BY c.codigo";
			Query q = em.createQuery(jpql);
			q.setParameter("nombre","%"+ nombre.toUpperCase() +"%");
			List<Cliente> clientes = q.getResultList();
			return clientes;
		}
	
	//Busca cliente por código en la BD
	public Cliente buscarPorCodigo(Integer codigo) {
		Cliente cliente = em.find(Cliente.class, codigo);
		return cliente;
	}
	
	//Inserta un cliente en la BD
	public Cliente incluir(Cliente cliente) {
		em.persist(cliente);
		em.refresh(cliente);
		return cliente;
	}
	
	//Edita un cliente en la BD
	public Cliente editar(Cliente cliente) {
		cliente = em.merge(cliente);
		return cliente;
	}
	
	//Incluye o edita un cliente dependiendo de si existe o no
	public Cliente actualizar(Cliente cliente) {
		Cliente clienteActualizado = null;
		Cliente clienteBuscar = buscarPorCodigo(cliente.getCodigo());
		if (clienteBuscar == null) {
			clienteActualizado = incluir(cliente);
		}else {
			clienteActualizado = editar(cliente);
		}
		return clienteActualizado;
	}
	
	public void eliminar(Integer codigo) {
		em.remove(codigo);
	}

}
