package com.school.security.services.implementations;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.entities.InterventionRequest;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.RequestInterventionMapper;
import com.school.security.repositories.RequestInterventionRepository;
import com.school.security.services.contracts.RequestInterventionService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RequestInterventionServiceImpl implements RequestInterventionService {

    private RequestInterventionRepository requestInterventionRepository;
    private RequestInterventionMapper requestInterventionMapper;

    @Override
    public InterventionRequest createOrUpdate(RequestInterventionReqDto toSave) {
        InterventionRequest interventionRequest = this.requestInterventionMapper.fromDto(toSave);
        return this.requestInterventionRepository.save(interventionRequest);
    }

    @Override
    public List<InterventionRequest> findAll() {
        return this.requestInterventionRepository.findAll();
    }

    @Override
    public InterventionRequest findById(Long aLong) {
        Optional<InterventionRequest> interventionRequest =
                this.requestInterventionRepository.findById(aLong);

        if (interventionRequest.isPresent()) {
            return interventionRequest.get();
        } else {
            throw new EntityException("Intervention not fund ");
        }
    }

    @Override
    public InterventionRequest deleteById(Long aLong) {
        Optional<InterventionRequest> interventionRequest =
                this.requestInterventionRepository.findById(aLong);

        if (interventionRequest.isPresent()) {
            this.requestInterventionRepository.deleteById(
                    interventionRequest.get().getInterventionRequestId());
            return interventionRequest.get();
        } else {
            throw new EntityException("Intervention not fund ");
        }
    }

    @Override
    public InterventionRequest update(InterventionRequest toUpdate, Long id) {

        InterventionRequest interventionRequest = this.requestInterventionMapper.toDto(toUpdate);
        interventionRequest.setInterventionRequestId(id);
        interventionRequest.setRequestDate(toUpdate.getRequestDate());
        interventionRequest.setStatus(toUpdate.getStatus());
        interventionRequest.setPriority(toUpdate.getPriority());
        interventionRequest.setMaterials(toUpdate.getMaterials());
        interventionRequest.setUser(toUpdate.getUser());

        return this.requestInterventionRepository.save(interventionRequest);
    }
}
