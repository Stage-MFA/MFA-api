package com.school.security.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Table(name = "journal")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class journal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "journal_id")
    protected Long journalId;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    @ManyToOne
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;
}
