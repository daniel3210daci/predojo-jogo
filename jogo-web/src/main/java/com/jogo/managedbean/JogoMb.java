package com.jogo.managedbean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.jogo.Datamodel.ArmaDatamodel;
import com.jogo.Datamodel.JogoDatamodel;
import com.jogo.Datamodel.PartidaDatamodel;
import com.jogo.Datamodel.RankingPartidaDatamodel;
import com.jogo.dao.impl.ArmaDao;
import com.jogo.dao.impl.JogadorDao;
import com.jogo.dao.impl.PartidaDao;
import com.jogo.exception.BusinessEntityViolationException;
import com.jogo.model.Partida;
import com.jogo.model.PartidaJogador;
import com.jogo.model.Jogador;
import com.jogo.model.Arma;

@ManagedBean
@ViewScoped
public class JogoMb implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger log;
	
	@Inject
	private PartidaDao partidaDao;
	
	@Inject
	private JogadorDao jogadorDao;
	
	@Inject
	private ArmaDao armaDao;
	
	private List<Partida> partidas;
	
	private List<PartidaJogador> partidasJogador;
	
	private List<Jogador> jogadores;
	
	private List<Arma> armas;
	
	private PartidaDatamodel partidaDataModel;
	
	private RankingPartidaDatamodel partidaJogadorModel;
	
	private JogoDatamodel jogoDataModel;
	
	private ArmaDatamodel armaDataModel;
	
	private long qtdMortes;
	
	@PostConstruct
	public void inicializacao (){
		jogoDataModel = new JogoDatamodel(PopulaJogadores());
		armaDataModel = new ArmaDatamodel(PopulaArmas());
		partidaDataModel = new PartidaDatamodel(PopulaPartida());
	}
	
	public List<Jogador> PopulaJogadores(){
		jogadores = jogadorDao.findAll();
		
		return jogadores;
	}
	
	public List<Arma> PopulaArmas(){
		 armas = armaDao.findAll();
		
		 return armas;
	}
	
	public List<Partida> PopulaPartida(){
		partidas = partidaDao.findAll();
		
		return partidas;
	}
	
	public void lerarquivolog(){
		boolean bInicio = false;
		boolean bFinal = false;
		
	    try {
	      FileReader arq = new FileReader("C:\\Amil Pre-dojo\\ArquivoLog\\log.txt");
	      BufferedReader lerArq = new BufferedReader(arq);
	 
	      String linha = lerArq.readLine();
	      
	      Long nPartida = RetornaCodigodaPartida(linha);
	      
	      bInicio = VerificaIniciodePartida(linha, nPartida);

	      while (linha != null) {
	        System.out.printf("%s\n", linha);
	        
	        nPartida = RetornaCodigodaPartida(linha);
	        		
	        bInicio = VerificaIniciodePartida(linha, nPartida);
	        
	        bFinal = VerificaFinaldePartida(linha);
	        
	        VerificaAcaoJogador(linha, nPartida);
	        
	        linha = lerArq.readLine(); 
	      }
	 
	      arq.close();
	      
	      RankingPartida();
	            
	      RankingJogadorPartida();
	      
	    } catch (IOException e) {
	    	FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, JogoMb.getMsg("msgErroLeituradelog"), "");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			
			return;
	    }
	}
	
	public void RankingJogadorPartida(){
		partidasJogador = partidaDao.rankingPartidaJogador();
		partidaJogadorModel = new RankingPartidaDatamodel(partidasJogador);
	}
	
	public void RankingPartida(){
		partidas = partidaDao.findAll();
	}
	
	public List<Arma> verificaarmapreferida(long codigoPartida){
		List<Partida> lstPartidas = partidaDao.findAll();
		List<Arma> lstArmas = new ArrayList<Arma>();
		
		for (Partida item : lstPartidas){
			lstArmas = partidaDao.VerificaArmaPreferidaGanhador(item.getCodPartida());
		}
		
		return lstArmas;
	}
	
	public void verificajogadorvencedorsemmorrer(){
		List<Jogador> lstJogadores;
		
		lstJogadores = jogadorDao.listJogadorVencedorSemMorrer();
		
		for (Jogador item : lstJogadores){
			//Todo
			//Ganha award
		}
	}
	
	public long RetornaCodigodaPartida(String slinha){
		return Long.parseLong(slinha.substring(slinha.indexOf("New match") + 1 , slinha.indexOf("has started") -1).trim());
	}
	
	public boolean VerificaIniciodePartida(String slinha, long codigoPartida){
		boolean bRetorno = false;
		
		if (slinha.contains("has started")){
			
			Partida partida = new Partida();
			
			partida.setDataInicio(Date.valueOf(slinha.substring(0, 19)));
			partida.setDataFinal(null);
			partida.setCodPartida(codigoPartida);
			partida.setDescricao(slinha.substring(32, 52));
			
			AtualizaPartida(partida, 1);
			
			bRetorno = true;
		}
		
		return bRetorno;
	}
	
	public boolean VerificaFinaldePartida(String slinha){
		boolean bRetorno = false;
		
		if (slinha.contains("has ended")){
			
			Partida partida;
			
			try {
				partida = partidaDao.findByCodigo(Long.parseLong(slinha.substring(32, 41)));
				partida.setDataFinal(Date.valueOf(slinha.substring(0, 19)));
				partida.setDescricao("Match " + Long.parseLong(slinha.substring(32, 41)) + " + has ended ");
				
				AtualizaPartida(partida, 2);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessEntityViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bRetorno = true;
		}
		
		return bRetorno;
	}
	
	public boolean VerificaAcaoJogador(String slinha, long codigoPartida){
		boolean bRetorno = false;
		
		if (slinha.contains("killed") && !slinha.contains("<WORLD>")){
			List<Jogador> lstJogadores = new ArrayList<Jogador>();
			List<Arma> lstArmas = new ArrayList<Arma>();
			
			Arma arma;

			arma = armaDao.findByArma(slinha.substring(slinha.indexOf("using"), + 5));
			arma.setNome(slinha.substring(slinha.indexOf("using"), + 5));
			arma.setTipo(slinha.substring(slinha.indexOf("using"), + 5));
			
			lstArmas.add(arma);
			
			Jogador jogadorAtivo = jogadorDao.findByNome(slinha.substring(slinha.indexOf(" - ") + 1,  slinha.indexOf("killed") -1).trim());
			jogadorAtivo.setArmas(lstArmas);
			
			if (jogadorAtivo == null)
				AtualizaJogador(jogadorAtivo, arma, 1, 1);
			else
				AtualizaJogador(jogadorAtivo, arma, 2, 1);

			lstJogadores.add(jogadorAtivo);
			
			Jogador jogadorInativo = jogadorDao.findByNome(slinha.substring(slinha.indexOf("killed") + 6,  slinha.indexOf("using") -1).trim());
			
			if (jogadorInativo == null)
				AtualizaJogador(jogadorInativo, arma, 1, 0);
			else
				AtualizaJogador(jogadorInativo, arma, 2, 0);
			
			lstJogadores.add(jogadorInativo);
			
			Partida partida;
			
			try {
				partida = partidaDao.findByCodigo(codigoPartida);
				partida.setJogadores(lstJogadores);
				partida.setQtdMortes(qtdMortes++);
				partida.setDescricao(slinha.substring(0, 52));
				
				AtualizaPartida(partida, 2);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessEntityViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bRetorno = true;
		}
		
		return bRetorno;
	}
	
	public void AtualizaJogador(Jogador jogador, Arma arma,  int operacao, int ativo){
		List<Arma> lstArmas = new ArrayList<Arma>();
		lstArmas.add(arma);
		
		try {
			jogador.setArmas(lstArmas);
			jogador.setAtivo(ativo);
			
			if (operacao == 1)
				jogadorDao.persistDaoBean(jogador);
			else if (operacao == 2)
				jogadorDao.mergeDaoBean(jogador);
		} catch (BusinessEntityViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AtualizaArma(Arma arma, int operacao){
		try {
			if (operacao == 1)
				armaDao.persistDaoBean(arma);
			else if (operacao == 2)
				armaDao.mergeDaoBean(arma);
		} catch (BusinessEntityViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AtualizaPartida(Partida partida, int operacao){
		try {
			if (operacao == 1)
				partidaDao.persistDaoBean(partida);
			else if (operacao == 2)
				partidaDao.mergeDaoBean(partida);
		} catch (BusinessEntityViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getMsg(String messageId) {  
		
		FacesContext facesContext = FacesContext.getCurrentInstance();  
		Locale locale = facesContext.getViewRoot().getLocale();  
        ResourceBundle bundle = ResourceBundle.getBundle("com.jogo.messages.messages", locale);
        
        String msg = "";
        
        try {  
        	
            msg = bundle.getString(messageId);
            
        } catch (Exception e) {  
        }  
        
        return msg;  
    }

	public PartidaDao getPartidaDao() {
		return partidaDao;
	}

	public void setPartidaDao(PartidaDao partidaDao) {
		this.partidaDao = partidaDao;
	}

	public JogadorDao getJogadorDao() {
		return jogadorDao;
	}

	public void setJogadorDao(JogadorDao jogadorDao) {
		this.jogadorDao = jogadorDao;
	}

	public ArmaDao getArmaDao() {
		return armaDao;
	}

	public void setArmaDao(ArmaDao armaDao) {
		this.armaDao = armaDao;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public List<Arma> getArmas() {
		return armas;
	}

	public void setArmas(List<Arma> armas) {
		this.armas = armas;
	}

	public PartidaDatamodel getPartidaDataModel() {
		return partidaDataModel;
	}

	public void setPartidaDataModel(PartidaDatamodel partidaDataModel) {
		this.partidaDataModel = partidaDataModel;
	}

	public JogoDatamodel getJogoDataModel() {
		return jogoDataModel;
	}

	public void setJogoDataModel(JogoDatamodel jogoDataModel) {
		this.jogoDataModel = jogoDataModel;
	}

	public ArmaDatamodel getArmaDataModel() {
		return armaDataModel;
	}

	public void setArmaDataModel(ArmaDatamodel armaDataModel) {
		this.armaDataModel = armaDataModel;
	}

	public long getQtdMortes() {
		return qtdMortes;
	}

	public void setQtdMortes(long qtdMortes) {
		this.qtdMortes = qtdMortes;
	}

	public RankingPartidaDatamodel getPartidaJogadorModel() {
		return partidaJogadorModel;
	}

	public void setPartidaJogadorModel(RankingPartidaDatamodel partidaJogadorModel) {
		this.partidaJogadorModel = partidaJogadorModel;
	}

	public List<PartidaJogador> getPartidasJogador() {
		return partidasJogador;
	}

	public void setPartidasJogador(List<PartidaJogador> partidasJogador) {
		this.partidasJogador = partidasJogador;
	}
}
