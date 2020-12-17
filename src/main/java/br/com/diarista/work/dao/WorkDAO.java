package br.com.diarista.work.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.work.entity.Work;

public interface WorkDAO extends JpaRepository<Work, Long>
{
	public Long countByDateAfterAndStatusAndUsuario(Date date, boolean status, Usuario usuario);		
	
	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN work_cleaning_lady WCL ON WCL.work_id = W.id AND WCL.user_id = :cpf " +				 
			"WHERE  W.date > :date AND W.status = true  ",		
			nativeQuery = true )
	public Long countAllWorksByCleaningLadies(Date date, String cpf );

	@Query(	value = "SELECT W.* FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.bairro IN (SELECT LS.bairro FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep " +					 
			"INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  " +		 				 
			"ORDER BY W.date ASC LIMIT :limit OFFSET :offset",			
			nativeQuery = true )
	public List<Work> findAllWorkBairroNative(Date date, String cpf, Integer limit, Integer offset);

	@Query(	value = "SELECT W.* FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.localidade IN (SELECT LS.localidade FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"AND L.bairro NOT IN (SELECT LS.bairro FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +				 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  " +		 				 
			"ORDER BY L.bairro ASC, W.date ASC LIMIT :limit OFFSET :offset",			
			nativeQuery = true )
	public List<Work> findAllWorkCityNative(Date date, String cpf, Integer limit, Integer offset);

	@Query(	value = "SELECT W.* FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.uf_id IN (SELECT LS.uf_id FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"AND L.localidade NOT IN (SELECT LS.localidade FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +				 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  " +		 				 
			"ORDER BY L.bairro ASC, W.date ASC LIMIT :limit OFFSET :offset",			
			nativeQuery = true )
	public List<Work> findAllWorkUFNative(Date date, String cpf, Integer limit, Integer offset);

	
	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.bairro IN (SELECT LS.bairro FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep " +					 
			"INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true ",	
			nativeQuery = true )
	public Long countAllWorkBairroNative(Date date, String cpf);

	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.localidade IN (SELECT LS.localidade FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"AND L.bairro NOT IN (SELECT LS.bairro FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +				 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  ",			
			nativeQuery = true )
	public Long countAllWorkCityNative(Date date, String cpf);

	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.uf_id IN (SELECT LS.uf_id FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"AND L.localidade NOT IN (SELECT LS.localidade FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +				 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  ",			
			nativeQuery = true )
	public Long countAllWorkUFNative(Date date, String cpf);


}
