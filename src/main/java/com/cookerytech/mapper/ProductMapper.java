package com.cookerytech.mapper;

import com.cookerytech.domain.Product;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.dto.request.ProductSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product productSaveRequestToProduct(ProductSaveRequest productSaveRequest);

    ProductDTO productToProductDTO(Product updateProduct);
}
