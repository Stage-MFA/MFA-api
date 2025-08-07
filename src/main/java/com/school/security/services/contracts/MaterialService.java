package com.school.security.services.contracts;

import com.school.security.dtos.requests.MaterialReqDto;
import com.school.security.entities.Material;

public interface MaterialService extends Service<MaterialReqDto, Material, Long> {}
