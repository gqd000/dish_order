package com.nanyan.dishorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyan.dishorder.DTO.DishDto;
import com.nanyan.dishorder.common.R;
import com.nanyan.dishorder.entity.Category;
import com.nanyan.dishorder.entity.Dish;
import com.nanyan.dishorder.service.CategoryService;
import com.nanyan.dishorder.service.DishFlavorService;
import com.nanyan.dishorder.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name)
    {
        Page<Dish> pageInfo = new Page<>(page,pageSize);

        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        queryWrapper.like(StringUtils.isNotBlank(name), Dish::getName, name);

        dishService.page(pageInfo,queryWrapper);
        //替换不包含标签name的数据
        BeanUtils.copyProperties(pageInfo, dishDtoPage,"records");

        List<Dish> dishList = pageInfo.getRecords();
        List<DishDto> dishDtoList = new ArrayList<>();

        for(Dish item : dishList)
        {
            DishDto dishDto = new DishDto();
            //复制属性
            BeanUtils.copyProperties(item,dishDto);

            //查询标签name
            Category category = categoryService.getById(item.getCategoryId());
            if(category != null)
            {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            dishDtoList.add(dishDto);
        }
;
        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
    }
    /**
     * 添加菜品
     */
    @PostMapping
    public R<String> saveWithFlavor(@RequestBody DishDto dishDto)
    {
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功！");
    }

    //根据Id查菜品
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id)
    {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    //修改菜品数据
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto)
    {
        dishService.updateWithFlavor(dishDto);

        return R.success("修改成功！");
    }

    //（批量）停售/启售菜品
    @PostMapping("/status/{status}")
    public R<String> sale(@PathVariable int status, String[] ids)
    {
        for(String id : ids)
        {
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改成功！");
    }

    //（批量）删除功能
    @DeleteMapping
    public R<String> delete(String[] ids)
    {
        for(String id : ids)
        {
            dishService.removeById(id);
        }
        return R.success("删除成功！");
    }

    //添加套餐时查询菜品
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish)
    {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.like(dish.getName() != null, Dish::getName, dish.getName());
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishList = dishService.list(queryWrapper);

        return R.success(dishList);
    }

}
