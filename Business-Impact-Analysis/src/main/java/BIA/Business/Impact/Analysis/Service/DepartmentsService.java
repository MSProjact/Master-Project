package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import BIA.Business.Impact.Analysis.Model.Departments;
import BIA.Business.Impact.Analysis.Repository.DepartmentsRepository;
@Service
@Transactional
public class DepartmentsService {
	
	 @Autowired
	    private DepartmentsRepository repo;
	     
	    public List<Departments> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(Departments Departments) {
	        repo.save(Departments);
	    }
	     
	    public Departments get(String id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(String id) {
	        repo.deleteById(id);
	    }
}

