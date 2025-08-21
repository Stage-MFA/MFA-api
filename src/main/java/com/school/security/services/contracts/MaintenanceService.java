package com.school.security.services.contracts;

import com.school.security.dtos.requests.MaintenanceReqDto;
import com.school.security.dtos.responses.MaintenanceResDto;

public interface MaintenanceService extends Service<MaintenanceReqDto, MaintenanceResDto, Long> {
    public MaintenanceResDto update(MaintenanceReqDto maintenanceReqDto, Long Id);
}
