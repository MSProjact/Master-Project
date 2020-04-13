package BIA.Business.Impact.Analysis.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import BIA.Business.Impact.Analysis.Controller.LoginController;
import BIA.Business.Impact.Analysis.Model.GenerateHierarchy;
import BIA.Business.Impact.Analysis.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import BIA.Business.Impact.Analysis.Model.Employees;
import BIA.Business.Impact.Analysis.Repository.EmployeesRepository;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class EmployeesService {

	@Autowired
	private EmployeesRepository repo;

	@Autowired
	private GenerateHierarchyService generateHierarchyService;

	public List<Employees> listAll() {
		return repo.findAll();
	}

	public void save(Employees employee) {
		repo.save(employee);
	}

	public Employees get(String id) {
		return repo.findById(id).get();
	}

	public void delete(String id) {
		repo.deleteById(id);
		generateHierarchyService.delete(id);
	}

	public List<Employees> getSubEmployeesForCurrentUser() {
		generateHierarchyService.getGenerateHierarchyList();

		List<Employees> generateHierarchy = listAll();
		List<Employees> mainHierarchyList = new ArrayList<Employees>();
		Employees me = UserUtil.getCurrentUser();
		for (final Employees parentHierarchy : generateHierarchy) {
			if (parentHierarchy.getId().equals(me.getId())) {
				// if parentHierarchy is mine, add my sub-module
				List<Employees> childHierarchyList = getSubModule(parentHierarchy.getId(),
						generateHierarchy);
				parentHierarchy.setSubEmployees(childHierarchyList);
				mainHierarchyList.add(parentHierarchy);
			}
		}
		return  mainHierarchyList;
	}

	public List<Employees> getSubModule(String i, List<Employees> employees) {
		List<Employees> subHierarchyList = new ArrayList<Employees>();
		for (final Employees child : employees) {
			if (Objects.nonNull(child) && i.equals(child.getReportToid())) {
				subHierarchyList.add(child);
			}
		}
		if (subHierarchyList.size() > 0) {
			for (final Employees subHierarchy : subHierarchyList) {
				List<Employees> childHierarchyList = getSubModule(subHierarchy.getId(), employees);
				subHierarchy.setSubEmployees(childHierarchyList);
			}
		}
		return subHierarchyList;
	}

	public List<Employees> getSubEmployeesForEmployee(Employees employees) {
		List<Employees> employeeList = new ArrayList<>();
		employeeList.add(employees);
		employeeList.addAll(getSubEmployees(employees.getSubEmployees()));
		return employeeList;
	}

	public List<Employees> getSubEmployees(List<Employees> subEmployeeList) {
		List<Employees> subEmployees = new ArrayList<>();
		if(subEmployeeList == null) {
			return Collections.EMPTY_LIST;
		}
		subEmployees.addAll(subEmployeeList);
		for(Employees employee : subEmployeeList) {
			subEmployees.addAll(getSubEmployees(employee.getSubEmployees()));
		}
		return subEmployees;
	}
}
