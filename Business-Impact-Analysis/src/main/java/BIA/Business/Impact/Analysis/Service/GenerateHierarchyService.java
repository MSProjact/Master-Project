package BIA.Business.Impact.Analysis.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Repository.GenerateHierarchyRepository;

@Service
@Transactional
public class GenerateHierarchyService {

	@Autowired
	private GenerateHierarchyRepository repo;

	public List<GenerateHierarchy> listAll() {
		return repo.findAll();
	}

	public void save(GenerateHierarchy GenerateHierarchy) {
		repo.save(GenerateHierarchy);
	}

	public GenerateHierarchy get(int id) {
		return repo.findById(id).get();
	}

	public void delete(int id) {
		repo.deleteById(id);
	}
}
