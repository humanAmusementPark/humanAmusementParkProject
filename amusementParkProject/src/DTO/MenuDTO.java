package DTO;
import lombok.*;

@Getter
@Builder
public class MenuDTO {
    private String menuName;
    private String menuURL;
    private int menuPrice;
}
