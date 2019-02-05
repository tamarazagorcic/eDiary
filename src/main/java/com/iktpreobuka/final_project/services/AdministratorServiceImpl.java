package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Administrator;
import com.iktpreobuka.final_project.repositories.AdministratorRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService{

	@Autowired
	private AdministratorRepository adminRepo;
	
	public Iterable<Administrator> getAllAdministrators() {
		return adminRepo.findAll();
	}

	public Optional<Administrator> findById(Long id) {
		return adminRepo.findById(id);
	}

	public Administrator addNewAdministrator(Administrator newAdministrator) {
		

		
		return adminRepo.save(newAdministrator);
	}
	
	public Administrator updateAdministrator(Long id, Administrator newAdministrator) {

		if (newAdministrator == null || !adminRepo.findById(id).isPresent()) {
			return null;
		}

		Administrator temp = adminRepo.findById(id).get();

		temp.setName(newAdministrator.getName());
		temp.setSurname(newAdministrator.getSurname());
		temp.setCode(newAdministrator.getCode());
		temp.setVersion(newAdministrator.getVersion());
		temp.setUser_id(newAdministrator.getUser_id());
		

		return adminRepo.save(temp);
	}

	public Administrator deleteAdministrator(Long id) {

		if (!adminRepo.findById(id).isPresent()) {
			return null;
		}
		Administrator temp = adminRepo.findById(id).get();
		adminRepo.deleteById(id);
		return temp;
	}
	
	 public Administrator findByCode(String code) {
			
			return adminRepo.findByCode(code);
		}
	 
	 public boolean ifExists(String code) {
			
			if(adminRepo.findByCode(code) != null) {
				return true;
			}else return false;
			
			
		}
}
