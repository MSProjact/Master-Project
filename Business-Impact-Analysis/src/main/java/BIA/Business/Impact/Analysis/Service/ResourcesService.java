package BIA.Business.Impact.Analysis.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import BIA.Business.Impact.Analysis.Model.Resources;
import BIA.Business.Impact.Analysis.Repository.ResourcesRepository;

@Service
@Transactional
public class ResourcesService {
	
	 @Autowired
	    private ResourcesRepository repo;
	     
	    public List<Resources> listAll() {
	        return repo.findAll();
	    }
	     
	    public void save(Resources Resources) {
	        repo.save(Resources);
	    }
	     
	    public Resources get(String id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(String id) {
	        repo.deleteById(id);
	    }
}

