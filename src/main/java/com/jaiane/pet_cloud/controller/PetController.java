package com.jaiane.pet_cloud.controller;


import com.jaiane.pet_cloud.dto.PetRequestDto;
import com.jaiane.pet_cloud.exception.RecursoNaoEncontradoException;
import com.jaiane.pet_cloud.model.Pet;
import com.jaiane.pet_cloud.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    private static Logger logger = LoggerFactory.getLogger(PetController.class.getName());

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<String> savePet(@ModelAttribute PetRequestDto petRequestDto) {
        try {

            Pet petSalvo = petService.addPet(petRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo.getName() + " foi adicionado com sucesso");


        } catch (Exception e) {
            logger.error("Não foi possivel adicionar o animal.", e);
            return ResponseEntity.internalServerError().body("Não foi possivel adicionar o animal.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listarPet() {

        try {
            List<Pet> pets = petService.listar();

            if (pets.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(pets);

        } catch (Exception e) {
            logger.error("Não foi possivel listar pets", e);
            return ResponseEntity.internalServerError().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {

        petService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizarPet(@PathVariable Long id,
                                            @RequestBody PetRequestDto novosDados) {

        Pet petAtualizado = petService.atualizar(id, novosDados);

        return ResponseEntity.ok(petAtualizado);


    }


}
