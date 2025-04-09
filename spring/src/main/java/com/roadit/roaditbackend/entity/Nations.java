package com.roadit.roaditbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nations")
@Getter
@Setter
@NoArgsConstructor
public class Nations {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;
}
