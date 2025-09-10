package com.school.security.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.security.dtos.responses.RapportResDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "stat")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private Long id;

    @Column(name = "date_stat")
    private LocalDate date;

    @Lob
    @Column(name = "stat_json", columnDefinition = "TEXT")
    private String statJson;

    public RapportResDto getStat() {
        if (statJson == null) return null;
        try {
            return new ObjectMapper().readValue(statJson, RapportResDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur de désérialisation JSON", e);
        }
    }

    public void setStat(RapportResDto stat) {
        try {
            this.statJson = new ObjectMapper().writeValueAsString(stat);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur de sérialisation JSON", e);
        }
    }
}
