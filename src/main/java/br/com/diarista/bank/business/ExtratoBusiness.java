package br.com.diarista.bank.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.diarista.bank.dao.ExtratoDAO;
import br.com.diarista.bank.entity.Extrato;
import br.com.diarista.folks.business.BasicBusiness;
import br.com.diarista.folks.entity.Usuario;

@Service
public class ExtratoBusiness extends BasicBusiness<Extrato>
{
	@Autowired
	private ExtratoDAO dao;

	@Override
	public List<?> getAllActive() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(Extrato object, Usuario user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void save(Extrato extrato) 
	{		
		dao.save(extrato);
	}

}
