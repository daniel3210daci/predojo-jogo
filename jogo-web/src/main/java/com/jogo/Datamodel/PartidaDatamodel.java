package com.jogo.Datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.jogo.model.Partida;

public class PartidaDatamodel extends ListDataModel<Partida> implements SelectableDataModel<Partida> {
	public PartidaDatamodel() {  
    }  
  
    public PartidaDatamodel(List<Partida> listaPartidas) {  
        super(listaPartidas);  
    }  
	
    public Partida getRowData(String rowKey) {  
          
        @SuppressWarnings("unchecked")
		List<Partida> partidaList = (List<Partida>) getWrappedData();  
        
        if(!rowKey.equals("null")){
	        for(Partida partida : partidaList) {  
	            
	    		if(partida.getId().equals(Long.valueOf(rowKey))){  
	                return partida;  
	            }
	        }  
        }
	        
        return null;  
    }  

	public Object getRowKey(Partida partida) {

		return partida.getId();
	}
}
