package com.nanyan.dishorder.DTO;

import com.nanyan.dishorder.entity.Dish;
import com.nanyan.dishorder.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
