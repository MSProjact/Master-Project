package BIA.Business.Impact.Analysis.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Repository.EmployeesRepository;

@Service
@Transactional
public class EmployeesService {

	@Autowired
	private EmployeesRepository repo;

	public List<Employees> listAll() {
		return repo.findAll();
	}

	public void save(Employees Employees) {
		repo.save(Employees);
	}

	public Employees get(String id) {
		return repo.findById(id).get();
	}

	public void delete(String id) {
		repo.deleteById(id);
	}
}
