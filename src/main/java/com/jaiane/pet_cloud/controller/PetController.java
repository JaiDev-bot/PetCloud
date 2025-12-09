package com.jaiane.pet_cloud.controller;


import com.jaiane.pet_cloud.dto.PetRequestDto;
import com.jaiane.pet_cloud.model.Pet;
import com.jaiane.pet_cloud.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {

    private static Logger logger = LoggerFactory.getLogger(PetController.class.getName());

    private final PetService petService;

    public PetController(PetService petService){
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<String> savePet (@ModelAttribute PetRequestDto petRequestDto){

        try{

            Pet petSalvo = petService.addPet(petRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo.getName() + " foi adicionado com sucesso");


        }catch (Exception e){
            logger.error("Não foi possivel adicionar o animal.", e);
            return  ResponseEntity.internalServerError().body("Não foi possivel adicionar o animal.");

        }

    }

}
