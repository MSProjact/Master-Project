package BIA.Business.Impact.Analysis.Service;

import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.Model.Product;
import BIA.Business.Impact.Analysis.Model.Resource;
import BIA.Business.Impact.Analysis.Model.Role;
import BIA.Business.Impact.Analysis.Repository.ResourcesRepository;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResourcesService {

    private final ResourcesRepository repo;

    private final GenerateHierarchyService generateHierarchyService;

    public ResourcesService(ResourcesRepository repo, GenerateHierarchyService generateHierarchyService) {
        this.repo = repo;
        this.generateHierarchyService = generateHierarchyService;
    }

    public List<Resource> listAll() {
        return repo.findAll();
    }

    public void save(Resource resource) {
        //In case of edit
        if (!StringUtils.isEmpty(resource.getId()) && Objects.isNull(resource.getProduct())) {
            resource.setProduct(get(resource.getId()).getProduct());
        }
        //In case of new resource API call while it already exists.
        else if (StringUtils.isEmpty(resource.getId())
                && Objects.nonNull(resource.getProduct())
                && !StringUtils.isEmpty(resource.getProduct().getId())) {
        	Resource resourceByProduct = repo.findByProductId(resource.getProduct().getId());
        	if(Objects.nonNull(resourceByProduct)) {
        		resource.setId(resourceByProduct.getId());
			}
        }
        repo.save(resource);
    }

    public Resource get(String id) {
        return repo.findById(id).get();
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public List<Resource> getResourceListForManageResources() {
        if (UserUtil.isCurrentUserRole(Role.ADMIN)) {
            return listAll();
        }
        GenerateHierarchy hierarchy = generateHierarchyService.getForCurrentEmployee();
        Resource resource = null;
        if(Objects.nonNull(hierarchy)) {
            resource = hierarchy.getResource();
        }
        return getSubResources(resource);
    }

    public List<Resource> getSubResources(Resource resource) {
        List<GenerateHierarchy> generateHierarchy = generateHierarchyService.getGenerateHierarchyListForCurrentUser();
        List<Resource> resources = new ArrayList<>();
        if (!CollectionUtils.isEmpty(generateHierarchy)) {
            resources.addAll(generateHierarchy
                    .stream()
                    .filter(hierarchy -> Objects.nonNull(hierarchy.getResource()))
                    .map(GenerateHierarchy::getResource)
                    .collect(Collectors.toList()));
        } else  if (Objects.nonNull(resource)) {
            resources.add(resource);
        }
        return resources;
    }
}

