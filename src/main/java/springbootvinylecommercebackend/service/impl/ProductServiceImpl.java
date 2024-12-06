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

    @Override
    public List<Product> getAllProduct() {
        return mapper.getAllProduct();
    }

    @Override
    public List<Product> getLessProductByName(String searchParam) {
        List<Product> result = null;

        if (!searchParam.isEmpty()) {
            result = mapper.getLessProductByName(searchParam);
            return result;
        }
        return result;
    }

    @Override
    public List<Product> getBestProducts() {
        return mapper.getBestProducts();
    }

    @Override
    public List<Product> getMoreProductByName(String searchParam) {
        return mapper.getMoreProductByName(searchParam);
    }

    @Override
    public List<Product> getProductByNameASC(String searchParam) {
        return mapper.getProductByNameASC(searchParam);
    }

    @Override
    public List<Product> getProductByNameDESC(String searchParam) {

        return mapper.getProductByNameDESC(searchParam);
    }

    @Override
    public List<Product> getProductByNameFiltered(String searchParam, String categoryName, String moodName,
                                                  String releaseYear, String stockStatus) {
        return mapper.getProductByNameFiltered(searchParam, categoryName, moodName, releaseYear, stockStatus);
    }
}
