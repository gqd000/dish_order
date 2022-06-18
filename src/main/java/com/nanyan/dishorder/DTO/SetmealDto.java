package com.nanyan.dishorder.DTO;


import com.nanyan.dishorder.entity.Setmeal;
import com.nanyan.dishorder.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
