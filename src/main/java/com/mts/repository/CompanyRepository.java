package com.mts.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mts.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {


}
