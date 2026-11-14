package tn.esprit.autoloc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.service.IVehiculeService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VehiculeControllerTest {
    private IVehiculeService vehiculeService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        vehiculeService = mock(IVehiculeService.class);
        IMaintenanceService maintenanceService = mock(IMaintenanceService.class);
        IReservationService reservationService = mock(IReservationService.class);
        VehiculeController controller = new VehiculeController(
                vehiculeService,
                maintenanceService,
                reservationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getVehicleRespondsWithDtoIdentifiersInsteadOfJpaRelations() throws Exception {
        when(vehiculeService.findById(8L)).thenReturn(new VehiculeDTO(8L, "123-TN-4567", "Toyota", "Corolla",
                        CategorieVehicule.BERLINE, new BigDecimal("65.00"), StatutVehicule.DISPONIBLE,
                        2L, List.of(4L), List.of(6L), List.of(7L)));

        mockMvc.perform(get("/api/vehicules/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVehicule").value(8))
                .andExpect(jsonPath("$.agenceId").value(2))
                .andExpect(jsonPath("$.equipementIds[0]").value(4))
                .andExpect(jsonPath("$.agence").doesNotExist());
    }
}
