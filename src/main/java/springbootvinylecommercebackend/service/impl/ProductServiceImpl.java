package springbootvinylecommercebackend.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public List<Product> getLessProductByName(String searchParam) {
        List<Product> result = null;

        if (!searchParam.isEmpty()) {
            result = mapper.getLessProductsByName(searchParam);
            return result;
        }
        return null;
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

    @Override
    public List<Product> getProductByNameFiltered(String searchParam, String categoryName, String moodName,
                                                  String releaseYear, String stockStatus) {
        return mapper.getProductsByNameFiltered(searchParam, categoryName, moodName, releaseYear, stockStatus);
    }
}
