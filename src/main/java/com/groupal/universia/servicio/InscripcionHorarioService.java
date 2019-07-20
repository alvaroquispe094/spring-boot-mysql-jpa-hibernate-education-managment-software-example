package com.groupal.universia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.universia.modelo.InscripcionHorario;
import com.groupal.universia.repositorio.InscripcionHorarioRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class InscripcionHorarioService {
	
	@Autowired
    private InscripcionHorarioRepository inscripcionHorarioRepository;
	
	@Autowired
    private InscripcionService inscripcionService;
	
	@Autowired
    private SemanaService semanaService;
	
	@Autowired
    private AulaService aulaService;
	
	
	public Iterable<InscripcionHorario> listAllInscripcionHorarios() {
        return inscripcionHorarioRepository.findAll();
    }
	
	public Iterable<InscripcionHorario> listAllInscripcionHorarios(BooleanExpression predicate) {
        return inscripcionHorarioRepository.findAll(predicate);
    }
	
	public Page<InscripcionHorario> listAllInscripcionHorariosPagination(BooleanExpression predicate, Pageable pageable) {
        return inscripcionHorarioRepository.findAll(predicate, pageable);
    }
  
    public InscripcionHorario getInscripcionHorarioById(Integer id) {
        return inscripcionHorarioRepository.findOne(id);
    }

    public InscripcionHorario getInscripcionHorarioById(BooleanExpression predicate) {
        return inscripcionHorarioRepository.findOne(predicate);
    }
   
    public InscripcionHorario saveInscripcionHorario(Integer id, Integer idinscripcion, Integer idSemana, String horaInicio, String horaFin, Integer idAula, Boolean activo) {
    	
    	InscripcionHorario inscripcionHorario = null;
		if (id!=null){
			inscripcionHorario = inscripcionHorarioRepository.findOne(id);
		}else{
			inscripcionHorario = new InscripcionHorario();
		}

		inscripcionHorario.setInscripcion(inscripcionService.getInscripcionById(idinscripcion));
		inscripcionHorario.setSemana(semanaService.getSemanaById(idSemana));
		inscripcionHorario.setHora_inicio(FechaHelper.convertirFechaADate(horaInicio, "HH:mm"));
		inscripcionHorario.setHora_fin(FechaHelper.convertirFechaADate(horaFin, "HH:mm"));
		inscripcionHorario.setAula(aulaService.getAulaById(idAula));
		inscripcionHorario.setActivo(activo);
		inscripcionHorarioRepository.save(inscripcionHorario);

		return inscripcionHorario;
    }

  
    public void deleteInscripcionHorario(Integer id) {
    	inscripcionHorarioRepository.delete(id);
    }


	public void cambiarEstadoInscripcionHorario(Integer idInscripcionHorario, boolean estado) {
		InscripcionHorario inscripcionHorario = inscripcionHorarioRepository.findOne(idInscripcionHorario);
		inscripcionHorario.setActivo(estado);
		
		inscripcionHorarioRepository.save(inscripcionHorario);
	}
    
    
    
}
