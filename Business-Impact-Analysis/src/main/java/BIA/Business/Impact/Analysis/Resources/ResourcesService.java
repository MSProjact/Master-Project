package BIA.Business.Impact.Analysis.Resources;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
	     
	    public Resources get(int id) {
	        return repo.findById(id).get();
	    }
	     
	    public void delete(int id) {
	        repo.deleteById(id);
	    }
}

