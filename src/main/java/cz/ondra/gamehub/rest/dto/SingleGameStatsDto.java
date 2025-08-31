package cz.ondra.gamehub.rest.dto;

import lombok.Data;

@Data
public class SingleGameStatsDto {

    private long played;
    private long won;
    private long lost;
}
