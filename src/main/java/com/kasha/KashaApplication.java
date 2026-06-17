package com.kasha;

import com.kasha.model.Contador;
import com.kasha.repository.ContadorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class KashaApplication {

    private final ContadorRepository contadorRepository;

    public KashaApplication(ContadorRepository contadorRepository) {
        this.contadorRepository = contadorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(KashaApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if (contadorRepository.findById(1L).isEmpty()) {
            contadorRepository.save(new Contador(1L, 0));
        }
    }
}
