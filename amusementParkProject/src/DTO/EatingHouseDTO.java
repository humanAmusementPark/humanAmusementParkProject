package DTO;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class EatingHouseDTO {
    private int eatingHouseID;
    private String eatingHouseName;
    private String eatingHouseURL;
    private List<MenuDTO> menuDTOList;
}
