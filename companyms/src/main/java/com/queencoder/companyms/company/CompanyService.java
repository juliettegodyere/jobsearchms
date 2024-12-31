package com.queencoder.companyms.company;

import com.queencoder.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
     public void createCompany(Company company);
    public List<Company> getAllCompanies();
    public Company getCompanyById(Long id);
    public boolean updateCompany(Company company, Long id);
    public boolean deleteCompany(Long id);

    public void updateCompanyRating(ReviewMessage reviewMessage);
}
