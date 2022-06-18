package com.nanyan.dishorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyan.dishorder.common.CustomException;
import com.nanyan.dishorder.entity.Category;
import com.nanyan.dishorder.entity.Dish;
import com.nanyan.dishorder.entity.Setmeal;
import com.nanyan.dishorder.mapper.CategoryMapper;
import com.nanyan.dishorder.service.CategoryService;
import com.nanyan.dishorder.service.DishService;
import com.nanyan.dishorder.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceimpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(Long id) {

        //1.查询分类是否关联菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //count1大于0,说明当前标签和菜品有关联
        if (count1 > 0)
        {
            //抛出业务异常
            throw new CustomException("已经关联菜品，不能删除！");
        }

        //2.查询分类是否关联套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0)
        {
            throw new CustomException("已经关联套餐，不能删除！");
        }

        //正常执行删除操作
        super.removeById(id);
    }
}
