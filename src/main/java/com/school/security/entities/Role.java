package com.school.security.entities;

import com.school.security.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends Model implements Serializable {

    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}
