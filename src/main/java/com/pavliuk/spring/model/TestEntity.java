package com.pavliuk.spring.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test")
@Data
public class TestEntity {
    @Id
    private Long id;

}
