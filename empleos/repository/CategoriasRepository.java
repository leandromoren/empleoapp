package com.portalempleos.empleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalempleos.empleos.model.Categoria;

//En el primer parametro debo especificar el model que va a mapear a este repositorio
//En el segundo parametro debo eespecificar el tipo de dato que utilizo para la llave primaria
/** public interface CategoriasRepository extends CrudRepository<Categoria, Integer>{} 
 */
public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
