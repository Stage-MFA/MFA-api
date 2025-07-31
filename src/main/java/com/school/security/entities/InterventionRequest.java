package com.school.security.entities;

import com.school.security.enums.Priority;
import com.school.security.enums.StatusType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@Entity
@Table(name = "intervention_request")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InterventionRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intervention_request_id")
    protected Long interventionRequestId;

    @Column(name = "request_date", nullable = false)
    private String requestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = true)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
}
