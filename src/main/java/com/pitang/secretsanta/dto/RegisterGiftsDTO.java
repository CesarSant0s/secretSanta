package com.pitang.secretsanta.dto;

import java.util.List;
import com.pitang.secretsanta.model.Gift;

public record RegisterGiftsDTO(Long userId, List<Gift> gifts) {
}
