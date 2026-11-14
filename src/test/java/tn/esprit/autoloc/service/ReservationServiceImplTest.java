package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @Mock
    private IReservationRepository reservationRepository;
    @Mock
    private IVehiculeRepository vehiculeRepository;
    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void refusesReservationWhenPeriodOverlaps() {
        Vehicule vehicule = vehicule();
        when(vehiculeRepository.findForUpdate(3L)).thenReturn(Optional.of(vehicule));
        when(reservationRepository.existsOverlappingReservation(eq(3L), any(), any(), anyCollection(), isNull()))
                .thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> service.create(reservation(vehicule)));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void confirmsPendingReservationWhenAvailable() {
        Vehicule vehicule = vehicule();
        Reservation reservation = reservation(vehicule);
        reservation.setIdReservation(14L);
        when(reservationRepository.findById(14L)).thenReturn(Optional.of(reservation));
        when(vehiculeRepository.findForUpdate(3L)).thenReturn(Optional.of(vehicule));
        when(reservationRepository.existsOverlappingReservation(eq(3L), any(), any(), anyCollection(), eq(14L)))
                .thenReturn(false);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Reservation confirmed = service.changerStatut(14L, StatutReservation.CONFIRMEE);

        assertEquals(StatutReservation.CONFIRMEE, confirmed.getStatut());
    }

    @Test
    void rejectsEndDateThatDoesNotFollowStartDate() {
        Reservation reservation = reservation(vehicule());
        reservation.setDateFin(reservation.getDateDebut());

        assertThrows(IllegalArgumentException.class, () -> service.create(reservation));
        verifyNoInteractions(reservationRepository);
    }

    private Vehicule vehicule() {
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(3L);
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        return vehicule;
    }

    private Reservation reservation(Vehicule vehicule) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 12, 1));
        reservation.setDateFin(LocalDate.of(2026, 12, 4));
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservation.setVehicule(vehicule);
        reservation.setClient(new Client());
        return reservation;
    }
}
