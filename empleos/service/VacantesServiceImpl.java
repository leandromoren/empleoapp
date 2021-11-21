package com.portalempleos.empleos.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.portalempleos.empleos.model.Vacante;

@Service
public class VacantesServiceImpl implements VacantesService{
	
	//Declaro la variable fuera para que esté disponible en cualquier metodo de la clase
	private List<Vacante> lista = null;

	//Constructor
	public VacantesServiceImpl() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<Vacante>();
			
			try {
			
				
				
				//Creo la oferta de trabajo 1
				Vacante vacante1 = new Vacante();
				vacante1.setId(1);
				vacante1.setNombre("Desarrollador Front-End ssr");
				vacante1.setDescripcion("El empleado necesitará solidos conociemientos en diseño web, js, react, figma, node, "
						+ "bases de datos no relacionales, deseable conocimientos de ingles");
				vacante1.setFecha(sdf.parse("09-02-2021"));
				vacante1.setSalario(90000.0);
				vacante1.setDestacado(1);
				vacante1.setImagen("empresa1.png");
				vacante1.setEstatus("Creada");
				
				//Creo la oferta de trabajo 2
				Vacante vacante2 = new Vacante();
				vacante2.setId(2);
				vacante2.setNombre("Desarrollador Back-End sr");
				vacante2.setDescripcion("Programador Senior con +6 años en el mercado para importante multinacional");
				vacante2.setFecha(sdf.parse("29-11-2021"));
				vacante2.setSalario(190000.0);
				vacante2.setDestacado(0);
				vacante2.setImagen("empresa2.png");
				vacante2.setEstatus("Aprobada");
				
				//Creo la oferta de trabajo 3
				Vacante vacante3 = new Vacante();
				vacante3.setId(3);
				vacante3.setNombre("Chef profesional");
				vacante3.setDescripcion("Importante restaurant en palermo (CABA), diponibilidad Full time");
				vacante3.setFecha(sdf.parse("02-01-2021"));
				vacante3.setSalario(50000.0);
				vacante3.setDestacado(0);
				vacante3.setEstatus("Aprobada");
				
				//Creo la oferta de trabajo 4
				Vacante vacante4 = new Vacante();
				vacante4.setId(4);
				vacante4.setNombre("Soporte técnico");
				vacante4.setDescripcion("Help desk con +2 años de experiencia, buen ambiente de trabajo, posibilidad de crecimiento mandar cv a leandro.moren@outlook.com");
				vacante4.setFecha(sdf.parse("15-10-2021"));
				vacante4.setSalario(78000.0);
				vacante4.setDestacado(1);
				vacante4.setImagen("empresa3.png");
				vacante4.setEstatus("Creada");
			 	
				/**
				 * Agrego los objetos de tipo vacante a la lista
				 *  */
				
				lista.add(vacante1);
				lista.add(vacante2);
				lista.add(vacante3);
				lista.add(vacante4);
				
				
			} catch(ParseException e) {
				System.out.println("Error en el metodo getVacantes(). " + e.getMessage());
			}
			
		
		}
		
	
	@Override
	public List<Vacante> buscarTodas() {
		return lista;
	}


	//Se muestran todos los detalles de la vacante seleccionada
	@Override
	public Vacante buscarPorId(int idVacante) {
		for(Vacante v : lista) {
			if(v.getId() == idVacante) {
				return v;
			}
		}
		return null;
	}


	@Override
	public void guardar(Vacante vacante) {
		lista.add(vacante);
		
	}


	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void eliminar(int idVacante) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
