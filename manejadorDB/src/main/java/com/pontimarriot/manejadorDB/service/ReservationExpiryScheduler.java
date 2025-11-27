package com.pontimarriot.manejadorDB.service;

import org.springframework.stereotype.Service;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.pontimarriot.manejadorDB.repository.ReservaRepository;
import com.pontimarriot.manejadorDB.repository.AvailabilityDatesRepository;
import com.pontimarriot.manejadorDB.model.Reserva;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationExpiryScheduler {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private AvailabilityDatesRepository availabilityDatesRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @PostConstruct
    public void init() {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /**
     * Schedule a check after 'delayMinutes'. If the Reserva is still PENDIENTE it will be deleted.
     * Operations run inside a TransactionTemplate to ensure a transactional EntityManager is available.
     */
    public void scheduleExpiry(UUID reservaId, long delayMinutes) {
        executor.schedule(() -> {
            try {
                transactionTemplate.execute(status -> {
                    Optional<Reserva> opt = reservaRepository.findById(reservaId);
                    if (opt.isPresent()) {
                        Reserva r = opt.get();
                        if ("PENDIENTE".equalsIgnoreCase(r.getStatus())) {
                            // ensure your repository method exists; it's executed inside the transaction
                            availabilityDatesRepository.deleteByReservationId(reservaId);
                            reservaRepository.deleteById(reservaId);
                        }
                    }
                    return null;
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, delayMinutes, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}
