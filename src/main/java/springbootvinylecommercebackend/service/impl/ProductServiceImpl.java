package springbootvinylecommercebackend.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.dto.request.GetAllProductsFilteredAndSorteredRequest;
import springbootvinylecommercebackend.mapper.ProductMapper;
import springbootvinylecommercebackend.model.Product;
import springbootvinylecommercebackend.service.ProductService;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProduct() {
        return mapper.getAllProducts();
    }

    @Override
    public List<Product> getReadyProducts() {
        return mapper.getReadyProducts();
    }

    @Override
    public Product getProductByTitle(String title) {
        return productMapper.getProductByTitle(title);
    }

    @Override
    public List<Product> searchProductsByTitle(String searchParam) {
        List<Product> result = null;

        if (!searchParam.isEmpty()) {
            result = mapper.searchProductsByTitle(searchParam);
            return result;
        }
        return null;
    }

    @Override
    public List<Product> getAllProductsFilteredAndSorted(String title, String category, String platform, String stockStatus, String studioName, String manufactureYear, String status, String sortType) {
        if (title == null) {
            title = "";
        }
        if (category == null) {
            category = "";
        }
        if (platform == null) {
            platform = "";
        }
        if (stockStatus == null) {
            stockStatus = "";
        }
        if (studioName == null) {
            studioName = "";
        }
        if (manufactureYear == null) {
            manufactureYear = "";
        }
        if (status == null) {
            status = "";
        }

        return mapper.getAllProductsFilteredAndSorted(title, category, platform, stockStatus, studioName, manufactureYear, status, sortType);
    }

    @Override
    public List<Product> getMoreProductByName(String searchParam) {
        return mapper.getMoreProductsByName(searchParam);
    }

    @Override
    public List<Product> getProductByNameASC(String searchParam) {
        return mapper.getProductsByNameASC(searchParam);
    }

    @Override
    public List<Product> getProductByNameDESC(String searchParam) {

        return mapper.getProductsByNameDESC(searchParam);
    }


}
