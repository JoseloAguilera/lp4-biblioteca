package com.biblioteca.rest;

import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.biblioteca.entidad.Autor;
import com.biblioteca.session.AutorSession;

@Path("/autor")
public class AutorRest {
	
	@EJB
	AutorSession as;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/consultar")
	public Map<String, Object> consultar() {
		return as.consultarAutores();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/consultar-por-nombre")
	public Map<String, Object> consultarPorNombre(@QueryParam("nombre") String nombre) {
		return as.consultarAutoresPorNombre(nombre);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/incluir")
	//BodyParam
	public Map<String, Object> incluir(Autor autor) {
		return as.incluir(autor);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/editar")
	//BodyParam
	public Map<String, Object> editar(Autor autor) {
		return as.editar(autor);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/actualizar")
	//BodyParam
	public Map<String, Object> actualizar(Autor autor) {
		return as.actualizar(autor);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/eliminar/{id}")
	//PathParam
	public Map<String,Object> eliminar(@PathParam("id") Integer codigo) {
		return as.eliminar(codigo);
	}

}
