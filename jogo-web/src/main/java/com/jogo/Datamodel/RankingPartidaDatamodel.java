package com.jogo.Datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.jogo.model.PartidaJogador;

public class RankingPartidaDatamodel extends ListDataModel<PartidaJogador> implements SelectableDataModel<PartidaJogador> {
	public RankingPartidaDatamodel() {  
    }  
  
    public RankingPartidaDatamodel(List<PartidaJogador> listaPartidas) {  
        super(listaPartidas);  
    }  
	
    public PartidaJogador getRowData(String rowKey) {  
          
        @SuppressWarnings("unchecked")
		List<PartidaJogador> partidaList = (List<PartidaJogador>) getWrappedData();  
        
        if(!rowKey.equals("null")){
	        for(PartidaJogador partida : partidaList) {  
	            
	    		if(partida.getId().equals(Long.valueOf(rowKey))){  
	                return partida;  
	            }
	        }  
        }
	        
        return null;  
    }  

	public Object getRowKey(PartidaJogador partida) {

		return partida.getId();
	}
}
