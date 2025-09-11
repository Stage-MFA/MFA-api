package com.school.security.controllers.api;

import com.school.security.dtos.responses.StatResDto;
import com.school.security.entities.Stat;
import com.school.security.services.implementations.StatServiceImpl;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
@CrossOrigin(
        origins = {
            "http://localhost:3000",
            "http://192.168.1.133:3000",
            "https://mfamaintenance.netlify.app/"
        })
@RequiredArgsConstructor
public class StatController {

    private final StatServiceImpl statService;

    @PostMapping
    public ResponseEntity<Stat> generateStat() {
        Stat stat = statService.saveRapport();
        return ResponseEntity.ok(stat);
    }

    @GetMapping("/{id}")
    public StatResDto findById(@PathVariable Long id) {
        return this.statService.getStatByI(id);
    }

    @GetMapping
    public StatResDto getByDate(@RequestParam LocalDate date) {
        return this.statService.getByDate(date);
    }

    @GetMapping("/betweenDates")
    public StatResDto getMaxDateBetweenDates(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return this.statService.getReportBetweenDates(startDate, endDate);
    }

    @GetMapping("/month")
    public StatResDto getReportByMonth(@RequestParam int year, @RequestParam int month) {
        return this.statService.getReportByMonth(year, month);
    }

    @GetMapping("/trimester")
    public StatResDto getReportTrimester(@RequestParam int year, @RequestParam int trimester) {
        return this.statService.getReportByTrimester(year, trimester);
    }
}
