package com.nanyan.dishorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyan.dishorder.entity.SetmealDish;
import com.nanyan.dishorder.mapper.SetmealDishMapper;
import com.nanyan.dishorder.mapper.SetmealMapper;
import com.nanyan.dishorder.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceimpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
