package tn.esprit.autoloc.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi agenceApi() {
        return group("Agence", "/api/agences");
    }

    @Bean
    public GroupedOpenApi clientApi() {
        return group("Client", "/api/clients");
    }

    @Bean
    public GroupedOpenApi contratApi() {
        return group("Contrat", "/api/contrats");
    }

    @Bean
    public GroupedOpenApi employeApi() {
        return group("Employe", "/api/employes");
    }

    @Bean
    public GroupedOpenApi equipementApi() {
        return group("Equipement", "/api/equipements");
    }

    @Bean
    public GroupedOpenApi maintenanceApi() {
        return group("Maintenance", "/api/maintenances");
    }

    @Bean
    public GroupedOpenApi paiementApi() {
        return group("Paiement", "/api/paiements");
    }

    @Bean
    public GroupedOpenApi reservationApi() {
        return group("Reservation", "/api/reservations");
    }

    @Bean
    public GroupedOpenApi vehiculeApi() {
        return group("Vehicule", "/api/vehicules");
    }

    private GroupedOpenApi group(String name, String basePath) {
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(basePath, basePath + "/**")
                .build();
    }
}
