package com.jaiane.pet_cloud.dto;


import org.springframework.web.multipart.MultipartFile;

public record PetRequestDto(String name,
                            Integer idade,
                            String raca,
                            MultipartFile arquivo) {

}
