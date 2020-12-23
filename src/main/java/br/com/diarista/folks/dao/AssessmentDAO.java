package br.com.diarista.folks.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.folks.entity.Assessment;
import br.com.diarista.folks.entity.Usuario;

public interface AssessmentDAO extends JpaRepository<Assessment, Long>
{
	List<Assessment> findAllByEvaluatorAndStatusAndDateAfter(Usuario usuario, boolean status, Date date);
}
