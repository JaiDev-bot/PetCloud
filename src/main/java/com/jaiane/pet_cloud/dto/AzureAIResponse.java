package com.jaiane.pet_cloud.dto;

import java.util.List;

public record AzureAIResponse(List<String> tags, String descricao,
                              Double confianca) {


}
