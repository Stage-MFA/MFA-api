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
