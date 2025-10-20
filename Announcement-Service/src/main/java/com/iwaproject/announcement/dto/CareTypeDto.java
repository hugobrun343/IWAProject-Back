package com.iwaproject.announcement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for CareType entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareTypeDto {
    private Long id;
    private String label;
}

