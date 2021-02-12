package br.com.diarista.routine.routine;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.diarista.bank.business.ExtratoBusiness;
import br.com.diarista.bank.entity.Extrato;
import br.com.diarista.conf.EmailInfo;
import br.com.diarista.folks.entity.Usuario;
import br.com.diarista.routine.business.LogSystemBusiness;
import br.com.diarista.routine.business.PaymentSystemBusiness;
import br.com.diarista.routine.business.SendEmailBusiness;
import br.com.diarista.routine.entity.LogSystem;
import br.com.diarista.routine.entity.PaymentSystem;
import br.com.diarista.routine.entity.SendEmail;
import br.com.diarista.utils.EmailUtil;
import br.com.diarista.work.business.WorkBusiness;
import br.com.diarista.work.entity.Work;

@Component
public class PaymentRoutine 
{	
	@Autowired
	private PaymentSystemBusiness business;
	
	@Autowired
	private EmailUtil email;
	
	@Autowired
	private SendEmailBusiness sendEmailBusiness;
	
	@Autowired
	private LogSystemBusiness logBusiness;
	
	@Autowired
	private WorkBusiness workBusiness;
	
	@Autowired
	private ExtratoBusiness extratoBusiness;
	
	//chamado pela rotina de tempo, e executa os métodos 
	private void executePayment() 
	{
		paymentOpen();	
	}
	
	//busca todos os pagamentos aberto e tenta realizar o pagamento junto a operadora
	@SuppressWarnings("unchecked")
	private void paymentOpen() 
	{
		try
		{			
			List<PaymentSystem> payments = business.getAllOpenPayment();	
			if(payments != null && payments.size() > 0)
			{
				Gson gson = new Gson();
				for (PaymentSystem pay : payments)
				{					
					String paystring = new String(pay.getObject());
					System.out.println(paystring);
					Map<String, String> map = gson.fromJson(paystring, Map.class);
					
					System.out.println("cpf Proffisional: " + map.get("cpfCleaningLady"));
					System.out.println("Validade do Cartão: " + map.get("shelfLifeCard"));
					System.out.println("Número do Cartão: " + map.get("numCard"));
					System.out.println("Código Segurança: " + map.get("codSeg"));
					System.out.println("Forma Pagamento: " + map.get("formPayment"));
					System.out.println("Nome Cartão: " + map.get("nameCad"));
					System.out.println("Id Serviço: " + map.get("idWork"));
									
					
					if(pay.getWork().getDate().before(new Date())) 
					{
						pay.setStatus(PaymentSystem.STATUS_FAIL);	
						pay.setObject("INFORMAR NÚMERO DA TRANSAÇÃO".getBytes());
						business.save(pay);
						
						LogSystem log = new LogSystem();
						log.setDate(new Date());
						log.setTypeLog(LogSystem.TYPE_ERROR);
						log.setDescription("Não foi possível processar o pagamento: [" + pay.getId() + "] work id: ["  + pay.getWork().getId() + "] User id: ["  + pay.getUser().getCpf() + "] , Pois a data do serviço está expirada! ");
						log.setLineCode("PaymentRoutine linha 86");
						logBusiness.save(log);
						
						SendEmail email = new SendEmail();	
						
						email.setDateRegister(new Date());
						email.setRecipient(pay.getUser().getEmail());
						email.setStatus(SendEmail.STATUS_OPEN);
						email.setSubject("Pagamento em Processo!");
						email.setBody(("Olá " + pay.getUser().getName() +  "\n Não foi efetuar o pagamento do serviço " + pay.getWork().getId() + "\n Pois no momento de processar o pagamento, o serviço se encontrava expirado").getBytes());	
						sendEmailBusiness.save(email);	
						return;
					}
					
										
					boolean isPaidOut = makePayment(map, pay.getUser(), pay.getWork(), pay.getCleanLady());
					if(isPaidOut)
					{
						pay.setStatus(PaymentSystem.STATUS_SUCESS);	
						pay.setObject("INFORMAR NÚMERO DA TRANSAÇÃO".getBytes());
						pay.setDateSend(new Date());
						business.save(pay);
						
						Work work = pay.getWork();
						work.setStage(Work.STAGE_PAY_OUT);						
						workBusiness.save(work);	
						
						Extrato extrato = new Extrato();
						extrato.setBlocked(true);
						extrato.setDateRegister(new Date());
						extrato.setDocument(pay.getId().toString());
						extrato.setOrigin("PaymentSystem");
						extrato.setType(Extrato.TYPE_DEPOSITO);
						extrato.setUser(pay.getUser());
						extrato.setValue(workBusiness.getValueWork(pay.getWork()));		
						extratoBusiness.save(extrato);
						
						LogSystem log = new LogSystem();
						log.setDate(new Date());
						log.setTypeLog(LogSystem.TYPE_SUCESS);
						log.setDescription("Pagamento Realizado com sucesso. Serviço: [" + pay.getId() + "] work id: ["  + pay.getWork().getId() + "] User id: ["  + pay.getUser().getCpf() + "]");
						log.setLineCode("PaymentRoutine linha 131");
						logBusiness.save(log);
						
						SendEmail email = new SendEmail();	
						
						email.setDateRegister(new Date());
						email.setRecipient(pay.getUser().getEmail());
						email.setStatus(SendEmail.STATUS_OPEN);
						email.setSubject("Pagamento Aprovado!!");
						email.setBody(("Olá " + pay.getUser().getNickname() +  "\n O pagamento da publicação " + pay.getWork().getId() + "\n Foi realizada com sucesso!, Foi liberado o endereço para o(a) profissional realizar o serviço.\nApós a realização, entre no App e confirme a execução do serviço para liberar o valor para o(a) profissional receber. e faça avaliação do(a) mesmo(a)").getBytes());	
						sendEmailBusiness.save(email);	
						
						SendEmail emailCleaningLady = new SendEmail();	
						
						emailCleaningLady.setDateRegister(new Date());
						emailCleaningLady.setRecipient(pay.getCleanLady().getEmail());
						emailCleaningLady.setStatus(SendEmail.STATUS_OPEN);
						emailCleaningLady.setSubject("Informativo Oportunidade Nº " + pay.getWork().getId());
						
						StringBuilder bodyEmail = new StringBuilder();
						bodyEmail.append("Olá ").append(pay.getCleanLady().getNickname()).append("\n");
						bodyEmail.append("Foi realizado o pagamento do Serviço ").append(pay.getWork().getId()).append("\n");
						bodyEmail.append("O endreço para executar o serviço é:\n\n");
						
						bodyEmail.append(pay.getWork().getAdress().getLocality().getLogradouro()).append(" Nº: ").append(pay.getWork().getAdress().getNumber()).append("\n");
						bodyEmail.append("Complemento: ").append(pay.getWork().getAdress().getComplement()).append("\n");						
						
						bodyEmail.append(pay.getWork().getAdress().getLocality().getBairro()).append(" - ").append(pay.getWork().getAdress().getLocality().getLocalidade()).append("/");
						bodyEmail.append(pay.getWork().getAdress().getLocality().getUf().getSigla()).append("\n\n");
						
						bodyEmail.append("O endreço também está liberado no App para consulta do local do serviço\n");
						bodyEmail.append("Após a execução, confirme que foi executado, e peça também para o cliente confirmar no app, para liberar o pagamento com maior rapidez\n");
						
						emailCleaningLady.setBody(bodyEmail.toString().getBytes());	
						sendEmailBusiness.save(emailCleaningLady);						
					}
					else
					{			
						if(pay.getAttempt() < 5) pay.setAttempt((short) (pay.getAttempt() + 1));
						else
						{
							pay.setStatus(PaymentSystem.STATUS_FAIL);	
							pay.setObject("INFORMAR NÚMERO DA TRANSAÇÃO".getBytes());
							
							LogSystem log = new LogSystem();
							log.setDate(new Date());
							log.setTypeLog(LogSystem.TYPE_ERROR);
							log.setDescription("Não foi possível processar o pagamento: [" + pay.getId() + "] work id: ["  + pay.getWork().getId() + "] User id: ["  + pay.getUser().getCpf() + "]");
							log.setLineCode("PaymentRoutine linha 178");
							logBusiness.save(log);
							
							SendEmail email = new SendEmail();	
							
							email.setDateRegister(new Date());
							email.setRecipient(pay.getUser().getEmail());
							email.setStatus(SendEmail.STATUS_OPEN);
							email.setSubject("Pagamento em Processo!");
							email.setBody(("Olá " + pay.getUser().getName() +  "\n Ocorreu um erro ao tentar efetuar o pagamento do serviço " + pay.getWork().getId() + "\n Verifique o que ocorreu com sua operadora financeira e tente realizar o pagamento novamente ").getBytes());	
							sendEmailBusiness.save(email);	
						}
						
						business.save(pay);
					}
				}				
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			email.sendEmail(EmailInfo.EMAIL_ADMINISTRADOR, "Error no Sistema de pagamento", e.getMessage());
		}
	}

	private boolean makePayment(Map<String, String> map, Usuario user, Work work, Usuario cleanLady)
	{	
		return true;
		
		
		
		
		
		
		
	}

	//loop temporizado que irá verificar a existência de pagamento pendente
	@PostConstruct
	public void run() 
	{
		Timer timer = new Timer(); 
		timer.scheduleAtFixedRate(new TimerTask() 
		{
		    @Override 
		    public void run()
		    { 
		    	executePayment();
		    }		    
		}, 20000, 120000);
	}	
}
