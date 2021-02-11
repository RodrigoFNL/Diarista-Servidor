package br.com.diarista.routine.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diarista.routine.dao.LogSystemDAO;
import br.com.diarista.routine.entity.LogSystem;

@Service
public class LogSystemBusiness 
{
	@Autowired
	private LogSystemDAO logRepository;

	public void save(LogSystem log)
	{	
		logRepository.save(log);
	}
}
