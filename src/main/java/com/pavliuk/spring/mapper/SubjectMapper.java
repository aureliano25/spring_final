package com.pavliuk.spring.mapper;

import com.pavliuk.spring.dto.SubjectDto;
import com.pavliuk.spring.model.Subject;

public class SubjectMapper {
    public static Subject createSubjectFromDto(SubjectDto subjectDto) {
        Subject subject = new Subject();
        subject.setId(subjectDto.getId());
        subject.setName(subjectDto.getName());

        return subject;
    }
}
