package com.school.security.services.contracts;

import com.school.security.dtos.requests.SpecialityReqDto;
import com.school.security.dtos.responses.SpecialityResDto;

public interface SpecialityService extends Service<SpecialityReqDto, SpecialityResDto, Long> {
    public SpecialityResDto save(SpecialityReqDto toSave, Long id);
}
