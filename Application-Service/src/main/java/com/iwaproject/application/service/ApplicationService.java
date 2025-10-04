package com.iwaproject.application.service;

import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    public String processApplication(String applicationData) {
        if (applicationData == null || applicationData.trim().isEmpty()) {
            throw new IllegalArgumentException("Application data cannot be null or empty");
        }
        
        // Simple processing logic for demonstration
        String processed = applicationData.trim().toUpperCase();
        
        if (processed.length() > 100) {
            return processed.substring(0, 100) + "...";
        }
        
        return processed;
    }
    
    public boolean validateApplication(String applicationData) {
        return applicationData != null && 
               !applicationData.trim().isEmpty() && 
               applicationData.length() >= 5;
    }
}