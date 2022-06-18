package com.nanyan.dishorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyan.dishorder.DTO.DishDto;
import com.nanyan.dishorder.entity.Dish;
import org.springframework.stereotype.Service;

@Service
public interface DishService extends IService<Dish> {

    //新增菜品，对两个表操作
    public void saveWithFlavor(DishDto dishDto);

    //查询菜品，对两个表操作
    public DishDto getByIdWithFlavor(Long id);

    //修改菜品，对两个表操作
    public void updateWithFlavor(DishDto dishDto);
}
