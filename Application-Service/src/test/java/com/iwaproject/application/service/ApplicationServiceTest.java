package com.iwaproject.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void processApplication_ValidData_ReturnsProcessedData() {
        // Given
        String applicationData = "  test application data  ";
        
        // When
        String result = applicationService.processApplication(applicationData);
        
        // Then
        assertEquals("TEST APPLICATION DATA", result);
    }

    @Test
    void processApplication_NullData_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> applicationService.processApplication(null));
    }

    @Test
    void processApplication_EmptyData_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> applicationService.processApplication("   "));
    }

    @Test
    void processApplication_LongData_TruncatesData() {
        // Given
        String longData = "a".repeat(150);
        
        // When
        String result = applicationService.processApplication(longData);
        
        // Then
        assertEquals(103, result.length()); // 100 + "..."
        assertTrue(result.endsWith("..."));
    }

    @Test
    void validateApplication_ValidData_ReturnsTrue() {
        // Given
        String validData = "valid application";
        
        // When
        boolean result = applicationService.validateApplication(validData);
        
        // Then
        assertTrue(result);
    }

    @Test
    void validateApplication_NullData_ReturnsFalse() {
        // When
        boolean result = applicationService.validateApplication(null);
        
        // Then
        assertFalse(result);
    }

    @Test
    void validateApplication_EmptyData_ReturnsFalse() {
        // When
        boolean result = applicationService.validateApplication("   ");
        
        // Then
        assertFalse(result);
    }

    @Test
    void validateApplication_ShortData_ReturnsFalse() {
        // Given
        String shortData = "abc";
        
        // When
        boolean result = applicationService.validateApplication(shortData);
        
        // Then
        assertFalse(result);
    }    
}