package com.school.security.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "journal")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Journal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "journal_id")
    protected Long journalId;

    @Column(name = "journal_date")
    private LocalDateTime dateJournal;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "duration_hour")
    private int durationHour;

    @ManyToOne
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;
}
