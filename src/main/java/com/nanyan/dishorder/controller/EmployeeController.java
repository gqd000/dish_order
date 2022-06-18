package com.nanyan.dishorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyan.dishorder.common.R;
import com.nanyan.dishorder.entity.Employee;
import com.nanyan.dishorder.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest req, @RequestBody Employee employee)
    {
        String password = employee.getPassword();
        //将密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //获得username
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //若用户名不存在
        if(emp == null)
        {
            return R.error("用户名或密码错误！");
        }
        //若密码错误
        if(!emp.getPassword().equals(password))
        {
            return R.error("用户名或密码错误！");
        }

        //查看员工状态
        if(emp.getStatus() == 0)
        {
            return R.error("账户已禁用！");
        }

        //登陆成功
        req.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest req)
    {
        req.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee)
    {
        log.info("新增员工，员工信息：{}",employee.toString());

        //初始化密码并用md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        Long empID = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empID);
//        employee.setUpdateUser(empID);

        employeeService.save(employee);

        return R.success("添加成功！");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name)
    {
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加判断条件
        queryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);
        //添加排序条件(按创建时间降序)
        queryWrapper.orderByDesc(Employee::getCreateTime);

        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee)
    {
        Long empID = (Long) request.getSession().getAttribute("employee");

//        employee.setUpdateUser(empID);
//        employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);
        return R.success("员工信息修改成功！");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id)
    {
        Employee employee = employeeService.getById(id);

        if(employee != null) return R.success(employee);
        return R.error("没有查询到该用户信息！");
    }
}
