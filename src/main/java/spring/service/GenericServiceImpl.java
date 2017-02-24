package spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import spring.repository.GenericRepository;

public class GenericServiceImpl<T> implements GenericService<T> {

	@Autowired
	private GenericRepository<T> dao;

	@Override
	@Transactional
	public T create(T t) {
		T createdT = t;
		return dao.save(createdT);
	}

	@Override
	@Transactional
	public T delete(int id) {
		T deletedT = dao.findOne(id);
		dao.delete(deletedT);
		return deletedT;
	}

	@Override
	@Transactional
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional
	public T update(T t) {
		T createdT = t;
		return dao.save(createdT);
	}

	@Override
	@Transactional
	public T findById(int id) {
		return dao.findOne(id);
	}

}
