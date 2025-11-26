package com.quiz.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

//    transient = do not save this field in DB
//👉 Used for temporary / computed / external data
//👉 Avoids JPA mapping errors
    @Transient
    private List<Question> questions;

    public Long getId() {
        return id;
    }

    public void setQuestions(List<Question> q) {
        this.questions = q;
    }

}
