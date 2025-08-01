package com.school.security.services.implementations;

import com.school.security.dtos.requests.SpecialityReqDto;
import com.school.security.dtos.responses.SpecialityResDto;
import com.school.security.entities.Speciality;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.SpecialityMapper;
import com.school.security.repositories.SpecialityRepository;
import com.school.security.services.contracts.SpecialityService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    private SpecialityRepository specialityRepository;
    private SpecialityMapper specialityMapper;

    @Override
    public SpecialityResDto createOrUpdate(SpecialityReqDto toSave) {
        Speciality speciality = this.specialityMapper.fromDto(toSave);
        return this.specialityMapper.toDto(this.specialityRepository.save(speciality));
    }

    @Override
    public List<SpecialityResDto> findAll() {
        return this.specialityRepository.findAll().stream()
                .map(this.specialityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialityResDto findById(Long aLong) {
        Optional<Speciality> speciality = this.specialityRepository.findById(aLong);
        if (speciality.isPresent()) {
            Speciality specialityRes = speciality.get();
            return this.specialityMapper.toDto(specialityRes);
        } else {
            throw new EntityException("Speciality not fund");
        }
    }

    @Override
    public SpecialityResDto deleteById(Long aLong) {
        Optional<Speciality> speciality = this.specialityRepository.findById(aLong);
        if (speciality.isPresent()) {
            Speciality specialityRes = speciality.get();
            this.specialityRepository.deleteById(aLong);
            return this.specialityMapper.toDto(specialityRes);
        } else {
            throw new EntityException("Speciality to delete not fund");
        }
    }
}
