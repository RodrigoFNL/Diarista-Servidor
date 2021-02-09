package br.com.diarista.work.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.work.entity.Work;

public interface WorkDAO extends JpaRepository<Work, Long>
{
	public Long countByDateAfterAndStatusAndUsuarioAndStageNot(Date date, boolean status, Usuario usuario, Short stage);	
	
	
	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +		 
			"WHERE  W.date > :date AND W.status = true AND W.stage <> 4  AND W.user_cpf = :cpf ", nativeQuery = true )
	public Long countMyPublicacoes(Date date, String cpf);
	
	@Query(	value =  "SELECT COUNT(id) FROM ( " +
			"SELECT W.id FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN work_cleaning_lady WCL ON WCL.work_id = W.id AND WCL.user_id = :cpf " +				 
			"WHERE  W.date > :date AND W.status = true AND W.stage <> 4  " +			
			"UNION " +
			"SELECT W.id FROM work W " +
			"INNER JOIN usuario U ON  W.cleaning_lady_cpf = U.cpf  AND U.cpf = :cpf AND U.registration_situation = 4 	" +
			"WHERE W.status = true AND W.stage = 3 AND W.status = true " +
			 " ) AS total",		
			nativeQuery = true )
	public Long countAllWorksByCleaningLadies(Date date, String cpf);
	
	@Query(	value =" SELECT COUNT(id) FROM (" +
			"SELECT W.id FROM work W " +	
			"INNER JOIN work_cleaning_lady WC ON WC.work_id = W.id AND WC.user_id = :cpf " +
			"WHERE (W.date > :date AND W.stage < 4 OR W.stage > 3) AND W.status = true  AND W.stage <> 4 " +
			"UNION " +
			"SELECT W.id FROM work W "	+
			"INNER JOIN usuario U ON  W.cleaning_lady_cpf = U.cpf  AND U.cpf = :cpf AND U.registration_situation = 4 " +
			"WHERE (W.date > '2021-01-09' AND W.stage < 4 OR W.stage > 3) AND W.status = true  AND W.stage <> 4 " +
			") AS total ",		
			nativeQuery = true )
	public Long countMyOportunity(String cpf, Date date);
	
	@Query(	value = "SELECT * FROM ( " +
			"SELECT W.* FROM work W " +		
			"INNER JOIN usuario U ON U.cpf = W.cleaning_lady_cpf AND  U.cpf = :cpf AND U.registration_situation = 4 " + 			
			"WHERE (W.date > :date AND W.stage < 4 OR W.stage > 3) AND W.status = true AND W.stage = 3 " +
			"UNION " +
			"SELECT W.* FROM work W " +
			"INNER JOIN work_cleaning_lady WC ON WC.work_id = W.id AND WC.user_id = :cpf  " +			
			"INNER JOIN usuario UW ON UW.cpf = WC.user_id AND UW.registration_situation = 4 " +			
			"WHERE (W.date > :date AND W.stage < 4 OR W.stage > 3) AND W.status = true AND W.stage <> 4 " +				
			") AS W " +
			"ORDER BY W.stage,  W.date DESC LIMIT :limit OFFSET :offset",
			nativeQuery = true )
	public List<Work> getAllOpportunitiesCleaningLady(Date date, String cpf,  Integer limit, Integer offset);

	@Query(	value = "SELECT W.* FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.bairro IN (SELECT LS.bairro FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep " +					 
			"INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true AND W.stage < 3  " +		 				 
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
			"AND W.date > :date AND W.status = true  AND W.stage < 3 " +		 				 
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
			"AND W.date > :date AND W.status = true  AND W.stage < 3 " +		 				 
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
			"AND W.date > :date AND W.status = true  AND W.stage < 3 ",	
			nativeQuery = true )
	public Long countAllWorkBairroNative(Date date, String cpf);

	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.localidade IN (SELECT LS.localidade FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"AND L.bairro NOT IN (SELECT LS.bairro FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +				 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  AND W.stage < 3 ",			
			nativeQuery = true )
	public Long countAllWorkCityNative(Date date, String cpf);

	@Query(	value = "SELECT COUNT(W) FROM work W " +
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.registration_situation = 4 " +
			"INNER JOIN andress A ON A.id = W.adress_id " +
			"INNER JOIN locality L ON L.cep = A.locality_id " +
			"AND L.uf_id IN (SELECT LS.uf_id FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +					 
			"AND L.localidade NOT IN (SELECT LS.localidade FROM locality LS INNER JOIN andress AF ON AF.locality_id = LS.cep  INNER JOIN usuario U ON AF.id = U.andress_id AND U.cpf = :cpf ) " +				 
			"WHERE :cpf NOT IN (SELECT UL.cpf FROM usuario UL INNER JOIN work_cleaning_lady WCL ON WCL.work_id = w.id) " +	 				 
			"AND W.date > :date AND W.status = true  AND W.stage < 3 ",			
			nativeQuery = true )
	public Long countAllWorkUFNative(Date date, String cpf);
	
	
	@Query(	value = "SELECT W.* FROM work W " +					
			"INNER JOIN usuario UW ON UW.cpf = W.user_cpf AND UW.cpf = :cpf  AND UW.registration_situation = 4 " +			
			"WHERE (W.date > :date AND W.stage < 4 OR W.stage > 3) AND W.status = true AND W.stage <> 4 " +	
			"ORDER BY W.stage,  W.date DESC LIMIT :limit OFFSET :offset",
			nativeQuery = true )
	public List<Work> getAllMyPuclications(Date date, String cpf, Integer limit, Integer offset);
	

}
