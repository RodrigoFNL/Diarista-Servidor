package business;

import java.util.List;

import javax.ejb.Stateful;

import model.Endereco;

@Stateful
public class EnderecoBusiness extends BasicBusiness<Endereco>
{

	@Override
	public List<?> getAllActive()
	{		
		return null;
	}
}
