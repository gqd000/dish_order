package com.nanyan.dishorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyan.dishorder.common.R;
import com.nanyan.dishorder.entity.Category;
import com.nanyan.dishorder.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page (int page, int pageSize)
    {
        log.info("page={},pageSize={}",page,pageSize);

        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }
    @PostMapping
    public R<String> save(@RequestBody Category category)
    {
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("添加成功！");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category)
    {
        categoryService.updateById(category);

        return R.success("修改成功！");
    }

    @DeleteMapping
    public R<String> delete(Long ids)
    {
        //直接删除操作
        categoryService.removeById(ids);
        //判断是否关联的删除操作
        categoryService.remove(ids);
        return R.success("删除成功！");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category)
    {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);

    }
}
