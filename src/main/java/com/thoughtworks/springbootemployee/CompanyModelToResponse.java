package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CompanyModelToResponse {
    @Mappings({
            @Mapping(target="companyID", source="company.id")
    })
    CompanyResponse companyToCompanyResponse(Company company);
}
