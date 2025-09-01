package com.school.security.dtos.responses;

public record RapportResDto(
        UserStatisticsResDto userStatistics,
        TechnicianStatisticResDto technicianStatistics,
        RequestStatisticsResDto requestStatistics,
        InterventionStatisticsResDto interventionStatistics,
        MaintenancesStatisticsResDto maintenancesStatistics,
        OrganisationStatisticsResDto organisationStatistics) {}
