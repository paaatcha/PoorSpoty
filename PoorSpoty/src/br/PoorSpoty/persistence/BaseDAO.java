package br.PoorSpoty.persistence;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T extends Serializable> {
	public T salvar(T obj);	
	public T obter(Long id);	
	public List<T> listar();
	public void excluir(Long id);
}
