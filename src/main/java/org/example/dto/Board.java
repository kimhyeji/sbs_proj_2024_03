package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Board extends Dto {
    private String name;
    private String code;

    public Board(Map<String, Object> row) {
        super(row);
        this.name = (String) row.get("name");
        this.code = (String) row.get("code");
    }
}
