package DTO;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class FacilitiesDTO {
    private int facilityID;
    private String facilityName;
    private String facilityURL;
    private List<FacilitiesMenuDTO> facilitiesMenuDTOList;
}
