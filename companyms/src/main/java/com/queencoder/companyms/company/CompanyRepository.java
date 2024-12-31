package com.queencoder.companyms.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CompanyRepository extends JpaRepository<Company, Long>{
   
}
