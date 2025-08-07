package com.school.security.services.implementations;

import com.school.security.dtos.requests.MaterialReqDto;
import com.school.security.entities.Material;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.MaterialMapper;
import com.school.security.repositories.MaterialRepository;
import com.school.security.services.contracts.MaterialService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MaterialServiceImplement implements MaterialService {

    private MaterialRepository materialRepository;
    private MaterialMapper materialMapper;

    @Override
    public Material createOrUpdate(MaterialReqDto toSave) {
        Material existingMaterial = this.materialRepository.findByName(toSave.name());

        if (existingMaterial != null) {
            existingMaterial.setType(toSave.type());
            existingMaterial.setBrand(toSave.brand());
            existingMaterial.setModel(toSave.Model());
            existingMaterial.setSerialNumber(toSave.serialNumber());
            existingMaterial.setAcquisitionDate(toSave.acquisitionDat());
            existingMaterial.setGuarantee(toSave.guarantee());

            return this.materialRepository.save(existingMaterial);
        } else {
            return this.materialRepository.save(this.materialMapper.fromDto(toSave));
        }
    }

    @Override
    public List<Material> findAll() {
        return this.materialRepository.findAll();
    }

    @Override
    public Material findById(Long aLong) {
        Optional<Material> material = this.materialRepository.findById(aLong);
        if (material.isPresent()) {
            return material.get();
        } else {
            throw new EntityException("Material not found");
        }
    }

    @Override
    public Material deleteById(Long aLong) {
        Optional<Material> material = this.materialRepository.findById(aLong);
        if (material.isPresent()) {
            this.materialRepository.deleteById(aLong);
            return material.get();
        } else {
            throw new EntityException("Material not deleted ");
        }
    }
}
