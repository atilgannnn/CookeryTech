package com.cookerytech.service;

import com.cookerytech.domain.Brand;
import com.cookerytech.dto.BrandDTO;
import com.cookerytech.dto.request.BrandRequest;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.BrandMapper;
import com.cookerytech.dto.request.BrandSaveRequest;
import com.cookerytech.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;




    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public void saveBrand(BrandSaveRequest brandSaveRequest) {

        // Mapper Islemi -> brandSaveRequest to Brand
        Brand brand = brandMapper.brandSaveRequestToBrand(brandSaveRequest);

        // Olusturulma zamanini setledik.
        brand.setCreateAt(LocalDateTime.now());

        brandRepository.save(brand);



    }

    public BrandDTO updateBrandById(Long id, BrandRequest brandRequest) {
      Brand brand = getBrand(id);

      if (brand.getBuiltIn()){
          throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
      }

        LocalDateTime now = LocalDateTime.now();

      brand.setName(brandRequest.getName());
      brand.setProfitRate(brandRequest.getProfitRate());
      brand.setIsActive(brandRequest.getIsActive());
      brand.setBuiltIn(brandRequest.getBuiltIn());
      brand.setCreateAt(brand.getCreateAt());
      brand.setUpdateAt(now);
        Brand updatedBrand= brandRepository.save(brand);


      return  brandMapper.brandToBrandDTO(updatedBrand);
    }

    public BrandDTO deleteBrandById(Long id) {

        Brand brand = getBrand(id);

        if (brand.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        brandRepository.delete(brand);

        return  brandMapper.brandToBrandDTO(brand);


    }
    private Brand getBrand(Long id){
        Brand brand = brandRepository.findById(id).orElseThrow(()->
             new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id))
        );
        return brand;
    }


}
