package com.nanyan.dishorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyan.dishorder.DTO.SetmealDto;
import com.nanyan.dishorder.entity.Setmeal;
import org.springframework.stereotype.Service;

@Service
public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);
}
