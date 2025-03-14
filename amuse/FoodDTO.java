package amuse;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class FoodDTO {
    private String fId;
    private String fName;
    private String fUrl;
    private List<FoodmenuDTO> menuDTOList;
}
