package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.SubjectDto;
import com.pavliuk.spring.mapper.SubjectMapper;
import com.pavliuk.spring.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public void deleteSubject(Long serviceId) {
        subjectRepository.deleteById(serviceId);
    }

    public void updateSubject(SubjectDto subjectDto) {
        subjectRepository.save(SubjectMapper.createSubjectFromDto(subjectDto));
    }
}
