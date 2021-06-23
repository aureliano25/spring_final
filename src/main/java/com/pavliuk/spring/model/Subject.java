package com.pavliuk.spring.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    private Long id;
    private String name;
}
