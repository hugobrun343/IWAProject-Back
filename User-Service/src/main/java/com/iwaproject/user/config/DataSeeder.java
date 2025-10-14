package com.iwaproject.user.config;

import com.iwaproject.user.entities.Language;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.repositories.LanguageRepository;
import com.iwaproject.user.repositories.SpecialisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final LanguageRepository languageRepository;
    private final SpecialisationRepository specialisationRepository;

    public DataSeeder(LanguageRepository languageRepository,
                      SpecialisationRepository specialisationRepository) {
        this.languageRepository = languageRepository;
        this.specialisationRepository = specialisationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedLanguages();
        seedSpecialisations();
    }

    private void seedLanguages() {
        List<String> languages = List.of(
                "English",
                "French",
                "Spanish",
                "German",
                "Italian"
        );

        for (String label : languages) {
            if (!languageRepository.existsById(label)) {
                Language lang = new Language(label);
                languageRepository.save(lang);
                log.info("Seeded language: {}", label);
            }
        }
    }

    private void seedSpecialisations() {
        List<String> specs = List.of(
                "Plumber",
                "Electrician",
                "Carpenter",
                "Painter",
                "Gardener",
                "Cleaner",
                "HVAC Technician",
                "Roofer",
                "Locksmith"
        );

        for (String label : specs) {
            if (!specialisationRepository.existsById(label)) {
                Specialisation spec = new Specialisation(label);
                specialisationRepository.save(spec);
                log.info("Seeded specialisation: {}", label);
            }
        }
    }
}
