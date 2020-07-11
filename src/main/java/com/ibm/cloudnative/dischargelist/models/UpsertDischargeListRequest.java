package com.ibm.cloudnative.dischargelist.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpsertDischargeListRequest implements Serializable {

//    @NotNull
//    @Pattern(regexp = ValidationRegEx.PORT_CODE, message = ValidationErrorMessages.PORT_CODE)
    private String portCode;

//    @NotNull
//    @Pattern(regexp = ValidationRegEx.IMO_VESSEL_CODE, message = ValidationErrorMessages.IMO_VESSEL_CODE)
    private String vesselCode;

//    @NotNull
//    @Pattern(regexp = ValidationRegEx.ARRIVAL_VOYAGE_CODE, message = ValidationErrorMessages.ARRIVAL_VOYAGE_CODE)
    private String voyageCode;

}
