package com.iwaproject.user.config;

import com.iwaproject.user.entities.Language;
import com.iwaproject.user.entities.Specialisation;
import com.iwaproject.user.repositories.LanguageRepository;
import com.iwaproject.user.repositories.SpecialisationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data seeder for initial database setup.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    /**
     * Logger instance.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(DataSeeder.class);

    /**
     * Language repository.
     */
    private final LanguageRepository languageRepository;

    /**
     * Specialisation repository.
     */
    private final SpecialisationRepository specialisationRepository;

    /**
     * Run data seeding on application startup.
     *
     * @param args command line arguments
     */
    @Override
    @Transactional
    public void run(final String... args) {
        seedLanguages();
        seedSpecialisations();
    }

    /**
     * Seed languages into database.
     */
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
                LOG.info("Seeded language: {}", label);
            }
        }
    }

    /**
     * Seed specialisations into database.
     */
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
                LOG.info("Seeded specialisation: {}", label);
            }
        }
    }
}

