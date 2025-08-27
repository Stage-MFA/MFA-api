package com.school.security.services.implementations;

import com.school.security.dtos.requests.JournalReqDto;
import com.school.security.dtos.responses.JournalResDto;
import com.school.security.entities.Journal;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.JournalMapper;
import com.school.security.repositories.JournalRepository;
import com.school.security.services.contracts.JournalService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;
    private final JournalMapper journalMapper;

    @Override
    public JournalResDto createOrUpdate(JournalReqDto toSave) {
        Journal journal = journalMapper.fromDto(toSave);
        return journalMapper.toDto(journalRepository.save(journal));
    }

    @Override
    public List<JournalResDto> findAll() {
        return journalRepository.findAll().stream().map(journalMapper::toDto).toList();
    }

    @Override
    public JournalResDto findById(Long id) {
        Journal journal =
                journalRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityException("Journal not found with id: " + id));
        return journalMapper.toDto(journal);
    }

    @Override
    public JournalResDto deleteById(Long id) {
        Journal journal =
                journalRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityException("Journal not found with id: " + id));

        journalRepository.delete(journal);
        return journalMapper.toDto(journal);
    }
}
