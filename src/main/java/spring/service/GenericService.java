package spring.service;

import java.util.List;

public interface GenericService<T> {
	
	public T create(T t);
	public T delete(int id);
	public List<T> findAll();
	public T update(T t);
	public T findById(int id);

}
