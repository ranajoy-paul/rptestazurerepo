package com.ibm.cloudnative.dischargelist.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * This is model class for response returned when PUT is issued for VGM submission request
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpsertDischargeListResponse {
    private UUID submissionId;
}
