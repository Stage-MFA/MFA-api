package com.school.security.services.implementations;

import com.school.security.dtos.responses.OrganisationStatisticsResDto;
import com.school.security.repositories.DirectionRepository;
import com.school.security.repositories.MaterialRepository;
import com.school.security.repositories.SpecialityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OrganisationServiceImpl {
    private DirectionRepository directionRepository;
    private SpecialityRepository specialityRepository;
    private MaterialRepository materialRepository;

    public OrganisationStatisticsResDto getOrganisationStatistics() {
        Long directionTotal = (long) this.directionRepository.findAll().size();
        Long specialityTotal = (long) this.specialityRepository.findAll().size();
        Long materialTotal = (long) this.materialRepository.findAll().size();

        return new OrganisationStatisticsResDto(directionTotal, specialityTotal, materialTotal);
    }
}
