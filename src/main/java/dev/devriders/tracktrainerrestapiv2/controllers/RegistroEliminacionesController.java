package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.repositories.IRegistroEliminacionesRepository;
import dev.devriders.tracktrainerrestapiv2.services.RegistroEliminacionesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/registro-eliminaciones")
public class RegistroEliminacionesController {
    private final RegistroEliminacionesService registroEliminacionesService;
    private final IRegistroEliminacionesRepository registroEliminacionesRepository;
    public RegistroEliminacionesController(RegistroEliminacionesService registroEliminacionesService,
                                           IRegistroEliminacionesRepository registroEliminacionesRepository) {
        this.registroEliminacionesService = registroEliminacionesService;
        this.registroEliminacionesRepository = registroEliminacionesRepository;
    }
}
