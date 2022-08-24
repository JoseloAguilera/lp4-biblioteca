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

import com.biblioteca.entidad.Ciudad;
import com.biblioteca.session.CiudadSession;


@Path("/ciudad")
public class CiudadRest {
		
		@EJB
		CiudadSession cs;
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/consultar")
		public Map<String, Object> consultar() {
			return cs.consultarCiudades();
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/consultar-por-nombre")
		public Map<String, Object> consultarPorNombre(@QueryParam("nombre") String nombre) {
			return cs.consultarCiudadPorNombre(nombre);
		}
		
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/incluir")
		//BodyParam
		public Map<String, Object> incluir(Ciudad ciudad) {
			return cs.incluir(ciudad);
		}
		
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/editar")
		//BodyParam
		public Map<String, Object> editar(Ciudad ciudad) {
			return cs.editar(ciudad);
		}
		
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/actualizar")
		//BodyParam
		public Map<String, Object> actualizar(Ciudad ciudad) {
			return cs.actualizar(ciudad);
		}
		
		@DELETE
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/eliminar/{id}")
		//PathParam
		public Map<String,Object> eliminar(@PathParam("id") Integer codigo) {
			return cs.eliminar(codigo);
		}

}

