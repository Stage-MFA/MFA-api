package com.school.security.entities;

import com.school.security.enums.Priority;
import com.school.security.enums.StatusType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = true)
    private Priority priority;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Material> materials = new ArrayList<>();

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    public void addMaterial(Material material) {
        if (!materials.contains(material)) {
            materials.add(material);
        }
    }

    public void removeMaterial(Material material) {
        materials.add(material);
    }
}
