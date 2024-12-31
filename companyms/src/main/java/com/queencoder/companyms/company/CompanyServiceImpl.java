package com.queencoder.companyms.company;

import com.queencoder.companyms.company.clients.ReviewClient;
import com.queencoder.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Autowired
    private ReviewClient reviewClient;

    @Override
    public void createCompany(Company company) {
         companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Given id does not exist " + id));
        return company;
    }

    @Override
    public boolean updateCompany(Company updatedCompany, Long id) {
        Company existingCompany = companyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Given id does not exist " + id));
        
        existingCompany.setCompanyDescription(updatedCompany.getCompanyDescription());
        existingCompany.setName(updatedCompany.getName());
        companyRepository.save(existingCompany);
        return true;
    }

    @Override
    public boolean deleteCompany(Long id) {
        Company existingCompany = companyRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Given id does not exist " + id));
        companyRepository.delete(existingCompany);

        return true;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
//        System.out.println(reviewMessage.getDescription() + "Description Received");

        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(
                () -> new NotFoundException("Company not found" + reviewMessage.getCompanyId()));

        Double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        System.out.println(averageRating + "the average rating ");

        company.setRating(averageRating);
        companyRepository.save(company);
    }

}
