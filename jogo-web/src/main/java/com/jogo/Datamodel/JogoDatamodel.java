package com.jogo.Datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.jogo.model.Jogador;

public class JogoDatamodel extends ListDataModel<Jogador> implements SelectableDataModel<Jogador> {
	public JogoDatamodel() {  
    }  
  
    public JogoDatamodel(List<Jogador> listaJogadores) {  
        super(listaJogadores);  
    }  
	
    public Jogador getRowData(String rowKey) {  
          
        @SuppressWarnings("unchecked")
		List<Jogador> jogadorList = (List<Jogador>) getWrappedData();  
        
        if(!rowKey.equals("null")){
	        for(Jogador jogador : jogadorList) {  
	            
	    		if(jogador.getId().equals(Long.valueOf(rowKey))){  
	                return jogador;  
	            }
	        }  
        }
	        
        return null;  
    }  

	public Object getRowKey(Jogador jogador) {

		return jogador.getId();
	}

}
