package com.nanyan.dishorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyan.dishorder.DTO.SetmealDto;
import com.nanyan.dishorder.common.R;
import com.nanyan.dishorder.entity.Dish;
import com.nanyan.dishorder.entity.Setmeal;
import com.nanyan.dishorder.entity.SetmealDish;
import com.nanyan.dishorder.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize)
    {
        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Setmeal::getStatus);

        setmealService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto)
    {
        setmealService.saveWithDish(setmealDto);

        return R.success("添加成功！");
    }
}
