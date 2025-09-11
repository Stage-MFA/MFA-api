package com.school.security.services.implementations;

import com.school.security.dtos.responses.*;
import com.school.security.entities.Stat;
import com.school.security.exceptions.EntityException;
import com.school.security.repositories.StatRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatServiceImpl {
    private StatRepository statRepository;
    private InterventionServiceImplement interventionService;
    private UserServiceImpl userService;
    private MaintenanceServiceImpl maintenanceService;
    private RequestInterventionServiceImpl requestInterventionService;
    private OrganisationServiceImpl organisationService;

    public List<Stat> findAllStat() {
        return this.statRepository.findAll();
    }

    public Stat saveRapport() {
        UserStatisticsResDto userStatistics = this.userService.getStatisticUsers();
        TechnicianStatisticResDto technicianStatistics = this.userService.getStatisticTechnician();
        RequestStatisticsResDto requestStatistics =
                this.requestInterventionService.getStatisticIntervention();
        RapportResDto rapportResDto =
                getRapportResDto(userStatistics, technicianStatistics, requestStatistics);

        Stat stat = new Stat();
        stat.setDate(LocalDate.now());
        stat.setStat(rapportResDto);
        this.statRepository.save(stat);
        return stat;
    }

    public StatResDto getStatByI(long id) {
        Optional<Stat> optionalStat = this.statRepository.findById(id);
        if (optionalStat.isPresent()) {
            Stat stat = optionalStat.get();
            return new StatResDto(stat.getId(), stat.getDate(), stat.getStat());
        } else {
            throw new EntityException("Stat not found");
        }
    }

    public StatResDto getByDate(LocalDate date) {
        Stat stat =
                this.statRepository.findAll().stream()
                        .filter(stats -> stats.getDate().equals(date))
                        .findFirst()
                        .orElse(null);
        assert stat != null;
        return new StatResDto(stat.getId(), stat.getDate(), stat.getStat());
    }

    public StatResDto getReportBetweenDates(LocalDate startDate, LocalDate endDate) {
        LocalDate maxDate = this.statRepository.findMaxDateBetweenDates(startDate, endDate);
        if (maxDate != null) {
            return getByDate(maxDate);
        } else {
            throw new EntityException("Date not found");
        }
    }

    public StatResDto getReportByMonth(int year, int month) {
        LocalDate maxDateInMonth = this.statRepository.findMaxDateInMonthAndYear(year, month);
        if (maxDateInMonth != null) {
            return getByDate(maxDateInMonth);
        } else {
            throw new EntityException("Date not found");
        }
    }

    public LocalDate getMaxDateInTrimester(int year, int trimester) {
        if (trimester < 1 || trimester > 4) {
            throw new EntityException("Trimester must be between 1 and 4");
        }

        int startMonth = (trimester - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        return this.statRepository.findMaxDateInTrimester(year, startMonth, endMonth);
    }

    public StatResDto getReportByTrimester(int year, int trimester) {
        LocalDate maxDateInMonth = getMaxDateInTrimester(year, trimester);
        if (maxDateInMonth != null) {
            return getByDate(maxDateInMonth);
        } else {
            throw new EntityException("Date not found");
        }
    }

    private RapportResDto getRapportResDto(
            UserStatisticsResDto userStatistics,
            TechnicianStatisticResDto technicianStatistics,
            RequestStatisticsResDto requestStatistics) {
        InterventionStatisticsResDto interventionStatistics =
                this.interventionService.getInterventionStatistics();
        MaintenancesStatisticsResDto maintenancesStatistics =
                this.maintenanceService.getMaintenanceStatistic();
        OrganisationStatisticsResDto organisationStatistics =
                this.organisationService.getOrganisationStatistics();

        return new RapportResDto(
                userStatistics,
                technicianStatistics,
                requestStatistics,
                interventionStatistics,
                maintenancesStatistics,
                organisationStatistics);
    }
}
