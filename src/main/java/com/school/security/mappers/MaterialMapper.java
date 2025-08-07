package com.school.security.mappers;

import com.school.security.dtos.requests.MaterialReqDto;
import com.school.security.entities.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper implements Mapper<MaterialReqDto, Material, Material> {

    @Override
    public Material fromDto(MaterialReqDto d) {
        Material material = new Material();
        material.setName(d.name());
        material.setType(d.type());
        material.setBrand(d.brand());
        material.setModel(d.Model());
        material.setSerialNumber(d.serialNumber());
        material.setAcquisitionDate(d.acquisitionDat());
        material.setGuarantee(d.guarantee());
        return material;
    }

    @Override
    public Material toDto(Material entity) {
        return new Material(
                entity.getMaterialId(),
                entity.getName(),
                entity.getType(),
                entity.getBrand(),
                entity.getModel(),
                entity.getSerialNumber(),
                entity.getAcquisitionDate(),
                entity.getGuarantee());
    }
}
