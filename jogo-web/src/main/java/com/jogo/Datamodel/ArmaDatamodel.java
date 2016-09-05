package com.jogo.Datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.jogo.model.Arma;

public class ArmaDatamodel extends ListDataModel<Arma> implements SelectableDataModel<Arma>{
	public ArmaDatamodel() {  
    }  
  
    public ArmaDatamodel(List<Arma> listaArmas) {  
        super(listaArmas);  
    }  
	
    public Arma getRowData(String rowKey) {  
          
        @SuppressWarnings("unchecked")
		List<Arma> armaList = (List<Arma>) getWrappedData();  
        
        if(!rowKey.equals("null")){
	        for(Arma arma : armaList) {  
	            
	    		if(arma.getId().equals(Long.valueOf(rowKey))){  
	                return arma;  
	            }
	        }  
        }
	        
        return null;  
    }  

	public Object getRowKey(Arma arma) {

		return arma.getId();
	}
}
