package com.pavliuk.spring.service;

import com.pavliuk.spring.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public void deleteSubject(Long serviceId) {
        subjectRepository.deleteById(serviceId);
    }
}
