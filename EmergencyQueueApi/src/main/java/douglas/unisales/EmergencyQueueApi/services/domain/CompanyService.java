package douglas.unisales.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import douglas.unisales.EmergencyQueueApi.model.domain.Company;
import douglas.unisales.EmergencyQueueApi.repository.domain.CompanyRepository;


@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company save(Company company) {
        validateCompany(company);
        return companyRepository.save(company);
    }

    public Company findById(UUID id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public void delete(UUID id) {
        Company company = findById(id);
        companyRepository.delete(company);
    }

    public Company update(Company company) {
        Company existingCompany = findById(company.getId());
        
        existingCompany.setName(company.getName());
        existingCompany.setCnpj(company.getCnpj());

        return companyRepository.save(existingCompany);
    }

    private void validateCompany(Company company) {
        if (company.getName() == null || company.getName().isEmpty()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (company.getCnpj() == null || company.getCnpj().isEmpty()) {
            throw new IllegalArgumentException("CNPJ is required");
        }
    }
} 