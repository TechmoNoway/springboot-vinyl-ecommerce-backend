package springbootvinylecommercebackend.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetAllProductsFilteredAndSorteredRequest {
    private String title;
    private String category;
    private String platform;
    private String stockStatus;
    private String studio;
    private String manufactureYear;
    private String status;
}
