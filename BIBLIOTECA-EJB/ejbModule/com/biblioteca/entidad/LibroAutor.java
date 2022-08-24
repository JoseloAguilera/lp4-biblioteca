package com.biblioteca.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "libros_autores")
public class LibroAutor  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "lau_libro")
	private Integer libro;
	
	@Id
	@Column(name = "lau_autor")
	private Integer autor;

	public LibroAutor() {
		super();
	}

	public Integer getLibro() {
		return libro;
	}

	public void setLibro(Integer libro) {
		this.libro = libro;
	}

	public Integer getAutor() {
		return autor;
	}

	public void setAutor(Integer autor) {
		this.autor = autor;
	}
	
}
