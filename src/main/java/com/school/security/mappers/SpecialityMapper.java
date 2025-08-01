package com.school.security.mappers;

import com.school.security.dtos.requests.SpecialityReqDto;
import com.school.security.dtos.responses.SpecialityResDto;
import com.school.security.entities.Speciality;
import org.springframework.stereotype.Component;

@Component
public class SpecialityMapper implements Mapper<SpecialityReqDto, Speciality, SpecialityResDto> {
    @Override
    public Speciality fromDto(SpecialityReqDto d) {
        Speciality speciality = new Speciality();
        speciality.setName(d.name());
        return speciality;
    }

    @Override
    public SpecialityResDto toDto(Speciality entity) {
        return new SpecialityResDto(entity.getSpecialityId(), entity.getName());
    }
}
