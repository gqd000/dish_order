package com.nanyan.dishorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyan.dishorder.DTO.SetmealDto;
import com.nanyan.dishorder.entity.Setmeal;
import com.nanyan.dishorder.entity.SetmealDish;
import com.nanyan.dishorder.mapper.SetmealMapper;
import com.nanyan.dishorder.service.SetmealDishService;
import com.nanyan.dishorder.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealSetviceimpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        this.save(setmealDto);

        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();

        for(SetmealDish item : setmealDishList)
        {
            item.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishList);
    }
}
