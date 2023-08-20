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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public BrandDTO saveBrand(BrandSaveRequest brandSaveRequest) {

        // Mapper Islemi -> brandSaveRequest to Brand
        Brand brand = brandMapper.brandSaveRequestToBrand(brandSaveRequest);

        // Olusturulma zamanini setledik.
        brand.setCreateAt(LocalDateTime.now());

        Brand updateBrand = brandRepository.save(brand);

        return brandMapper.brandToBrandDTO(updateBrand);



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


//    public Page<BrandDTO> getBrandDTOPage(Pageable pageable, Boolean active) {
//
//        Page<Brand> brandPage = null;
//
//        if(!active) {
//            throw new ResourceNotFoundException(String.format(ErrorMessage.NO_ACTIVE_BRANDS_MESSAGE));
//        }else{
//            brandPage = brandRepository.getActiveBrands(pageable);
//        }
//
//        return brandPage.map(brand -> brandMapper.brandToBrandDTO(brand));
//
//    }public Page<BrandDTO> getBrandDTOPage(Pageable pageable) {
//
//        Page<Brand> brandPage = brandRepository.getActiveBrands(pageable);
//
//        return brandPage.map(brand -> brandMapper.brandToBrandDTO(brand));
//
//    }
//
//    public BrandDTO getBrandDTOById(Boolean active, Long id) {
//
//        Brand brand = getBrand(id);
//
//        if(!active){
//            throw new ResourceNotFoundException(String.format(ErrorMessage.NO_ACTIVE_BRANDS_MESSAGE));
//        }
//        return brandMapper.brandToBrandDTO(brand);
//
//    }
//
//    public BrandDTO getBrandDTOById(Long id) {
//
//        Brand brand = getBrand(id);
//
//        return brandMapper.brandToBrandDTO(brand);
//    }
}
