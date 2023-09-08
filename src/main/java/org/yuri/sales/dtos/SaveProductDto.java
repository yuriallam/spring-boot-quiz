package org.yuri.sales.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveProductDto {
    private String name;
    private String description;
    private String category;
}
