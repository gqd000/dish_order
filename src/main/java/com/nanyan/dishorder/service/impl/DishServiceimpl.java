package com.nanyan.dishorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyan.dishorder.DTO.DishDto;
import com.nanyan.dishorder.entity.Dish;
import com.nanyan.dishorder.entity.DishFlavor;
import com.nanyan.dishorder.mapper.DishMapper;
import com.nanyan.dishorder.service.DishFlavorService;
import com.nanyan.dishorder.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceimpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品基本信息
        this.save(dishDto);
        Long dishId = dishDto.getId();

        List<DishFlavor> flavorsList = dishDto.getFlavors();
        for(DishFlavor item : flavorsList)
        {
            item.setDishId(dishId);
        }
        //
        dishFlavorService.saveBatch(flavorsList);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //查询口味
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());

        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //修改dish的基本属性
        this.updateById(dishDto);

        //解除原来两表的关联
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //建立两表新的关联
        List<DishFlavor> list = dishDto.getFlavors();

        for(DishFlavor item : list)
        {
            item.setDishId(dishDto.getId());
        }

        dishFlavorService.saveBatch(list);
    }
}
