package com.roadit.roaditbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
public class Schools {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;
}
