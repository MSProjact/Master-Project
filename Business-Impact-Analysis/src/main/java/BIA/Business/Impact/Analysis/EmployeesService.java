package BIA.Business.Impact.Analysis;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
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
	     
	    public Employees get(int id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(int id) {
	        repo.deleteById(id);
	    }
}

