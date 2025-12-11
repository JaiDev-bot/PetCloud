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

    @GetMapping
    public ResponseEntity<List<Pet>> listarPet (){

        try{
            List<Pet> pets = petService.listar();

            if(pets.isEmpty()){
                return ResponseEntity.noContent().build();
            }
                return ResponseEntity.status(HttpStatus.OK).body(pets);

        } catch (Exception e) {
            logger.error("Não foi possivel listar pets", e);
                return  ResponseEntity.internalServerError().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet (@PathVariable Long id){

        try{

            petService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (RecursoNaoEncontradoException e) {
            logger.warn("Tentativa de deletar recurso inexistente: \" + id");
            throw e;
        }

        catch (Exception e){
             logger.error(" Não foi possivel deletar o pet com o ID: " + id, e);
             return ResponseEntity.internalServerError().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizarPet(@PathVariable Long id,
                                            @RequestBody PetRequestDto novosDados) {

        try {
            Pet petAtualizado = petService.atualizar(id, novosDados);
            return ResponseEntity.ok(petAtualizado);

        } catch (RecursoNaoEncontradoException e) {
            throw e;

        } catch (Exception e) {
            logger.error("não foi possivel  atualizar o pet com o ID: " + id, e);
            return ResponseEntity.internalServerError().build();

        }
    }


    }
