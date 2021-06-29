package com.pavliuk.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String difficulty;
    private Long timer;
    private String title;

    public enum DIFFICULTY {
        EASY,
        NORMAL,
        HARD,
        EXTREME;

        public static boolean contains(String value) {
            for (DIFFICULTY difficulty : DIFFICULTY.values()) {
                if (difficulty.name().equalsIgnoreCase(value)) {
                    return true;
                }
            }

            return false;
        }
    }
}
