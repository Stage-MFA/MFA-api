package com.school.security.services.implementations;

import com.school.security.controllers.api.InvitationSseController;
import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.dtos.responses.RequestInterventionResDto;
import com.school.security.entities.InterventionRequest;
import com.school.security.entities.Material;
import com.school.security.entities.User;
import com.school.security.enums.StatusType;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.RequestInterventionMapper;
import com.school.security.repositories.MaterialRepository;
import com.school.security.repositories.RequestInterventionRepository;
import com.school.security.repositories.UserRepository;
import com.school.security.services.contracts.RequestInterventionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RequestInterventionServiceImpl implements RequestInterventionService {

    private final RequestInterventionRepository requestInterventionRepository;
    private final RequestInterventionMapper requestInterventionMapper;
    private final MaterialRepository materialRepository;
    private final UserRepository userRepository;
    private InvitationSseController invitationSseController;

    @Override
    public RequestInterventionResDto createOrUpdate(RequestInterventionReqDto toSave) {
        InterventionRequest interventionRequest = requestInterventionMapper.fromDto(toSave);
        List<RequestInterventionReqDto> interventionReqList = new ArrayList<>();
        List<RequestInterventionResDto> interventionResList = findAll();

        for (RequestInterventionResDto e : interventionResList) {
            interventionReqList.add(
                    new RequestInterventionReqDto(
                            e.requestDate(),
                            e.status(),
                            e.priority(),
                            e.materials(),
                            e.description(),
                            e.idUser()));
        }

        if (interventionReqList.contains(toSave)) {
            throw new EntityException("Data invalid or data exist");
        } else {
            if (interventionRequest.getMaterials() != null) {
                List<Material> updatedMaterials =
                        interventionRequest.getMaterials().stream()
                                .map(
                                        material -> {
                                            if (material.getMaterialId() != null) {
                                                return materialRepository
                                                        .findById(material.getMaterialId())
                                                        .map(
                                                                existing -> {
                                                                    existing.setName(
                                                                            material.getName());
                                                                    existing.setType(
                                                                            material.getType());
                                                                    existing.setBrand(
                                                                            material.getBrand());
                                                                    existing.setModel(
                                                                            material.getModel());
                                                                    existing.setSerialNumber(
                                                                            material
                                                                                    .getSerialNumber());
                                                                    existing.setAcquisitionDate(
                                                                            material
                                                                                    .getAcquisitionDate());
                                                                    existing.setGuarantee(
                                                                            material
                                                                                    .getGuarantee());
                                                                    return materialRepository.save(
                                                                            existing);
                                                                })
                                                        .orElseGet(
                                                                () ->
                                                                        materialRepository.save(
                                                                                material));
                                            } else {
                                                return materialRepository.save(material);
                                            }
                                        })
                                .toList();
                interventionRequest.setMaterials(updatedMaterials);
            }
            InterventionRequest saved = requestInterventionRepository.save(interventionRequest);
            Long countRequest = this.getCountRequest();
            invitationSseController.sendCountRequestIntervention(countRequest);
            return requestInterventionMapper.toDto(saved);
        }
    }

    @Override
    public List<RequestInterventionResDto> findAll() {
        return this.requestInterventionRepository
                .findAllByOrderByStatusDescRequestDateDescPriorityAsc()
                .stream()
                .map(requestInterventionMapper::toDto)
                .toList();
    }

    @Override
    public RequestInterventionResDto findById(Long id) {
        InterventionRequest entity = findExistingEntityById(id);
        return requestInterventionMapper.toDto(entity);
    }

    @Override
    public RequestInterventionResDto deleteById(Long id) {
        InterventionRequest existing = findExistingEntityById(id);
        requestInterventionRepository.delete(existing);
        Long countRequest = this.getCountRequest();
        invitationSseController.sendCountRequestIntervention(countRequest);
        return requestInterventionMapper.toDto(existing);
    }

    @Override
    public RequestInterventionResDto update(RequestInterventionReqDto toUpdate, Long id) {
        InterventionRequest existing = findExistingEntityById(id);

        existing.setRequestDate(toUpdate.requestDate());
        existing.setStatus(toUpdate.status());
        existing.setPriority(toUpdate.priority());
        existing.setDescription(toUpdate.description());

        if (toUpdate.idUser() != null) {
            User user =
                    userRepository
                            .findById(toUpdate.idUser())
                            .orElseThrow(
                                    () ->
                                            new EntityException(
                                                    "User with ID "
                                                            + toUpdate.idUser()
                                                            + " not found"));
            existing.setUser(user);
        }

        List<Material> updatedMaterials = new ArrayList<>();
        if (toUpdate.materials() != null) {
            for (Material mat : toUpdate.materials()) {
                if (mat.getMaterialId() != null) {
                    Material existingMat =
                            materialRepository
                                    .findById(mat.getMaterialId())
                                    .orElseThrow(
                                            () ->
                                                    new EntityException(
                                                            "Material with ID "
                                                                    + mat.getMaterialId()
                                                                    + " not found"));
                    existingMat.setName(mat.getName());
                    existingMat.setType(mat.getType());
                    existingMat.setBrand(mat.getBrand());
                    existingMat.setModel(mat.getModel());
                    existingMat.setSerialNumber(mat.getSerialNumber());
                    existingMat.setAcquisitionDate(mat.getAcquisitionDate());
                    existingMat.setGuarantee(mat.getGuarantee());
                    updatedMaterials.add(existingMat);
                } else {
                    updatedMaterials.add(materialRepository.save(mat));
                }
            }
        }
        existing.setMaterials(updatedMaterials);

        InterventionRequest updated = requestInterventionRepository.save(existing);
        Long countRequest = this.getCountRequest();
        invitationSseController.sendCountRequestIntervention(countRequest);
        return requestInterventionMapper.toDto(updated);
    }

    @Override
    public List<RequestInterventionResDto> listRequestInterventionByUserId(Long id) {
        return this.requestInterventionRepository
                .findAllByUserUsersIdOrderByStatusDescRequestDateDescPriorityAsc(id)
                .stream()
                .map(requestInterventionMapper::toDto)
                .toList();
    }

    @Override
    public Long getCountRequest() {
        return this.findAll().stream().filter(s -> s.status() == StatusType.PENDING).count();
    }

    private InterventionRequest findExistingEntityById(Long id) {
        return requestInterventionRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new EntityException(
                                        "Intervention request with ID " + id + " not found"));
    }

    @Override
    public Map<String, Object> getVariationParAnnee(int year) {

        List<Object[]> minMaxList = requestInterventionRepository.findMinMaxDateByYear(year);
        LocalDateTime minDate = null;
        LocalDateTime maxDate = null;

        if (!minMaxList.isEmpty()) {
            Object[] minMax = minMaxList.get(0);
            if (minMax[0] != null) minDate = (LocalDateTime) minMax[0];
            if (minMax[1] != null) maxDate = (LocalDateTime) minMax[1];
        }

        List<Object[]> results = requestInterventionRepository.countByStatusAndDateByYear(year);
        Map<LocalDate, Map<StatusType, Long>> variation = new LinkedHashMap<>();
        for (Object[] row : results) {
            StatusType status = (StatusType) row[0];
            LocalDate date = ((java.sql.Date) row[1]).toLocalDate();
            Long count = (Long) row[2];

            variation.putIfAbsent(date, new HashMap<>());
            variation.get(date).put(status, count);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("minDate", minDate);
        response.put("maxDate", maxDate);
        response.put("variation", variation);

        return response;
    }

    @Override
    public List<Integer> findAllYearsWithRequest() {
        return this.requestInterventionRepository.findAllYearsWithRequests();
    }
}
