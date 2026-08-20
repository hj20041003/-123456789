package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
//插入员工数据
    @Insert("insert into employee (id, username, password, name, sex, id_number, phone, status, create_time, update_time, creator, updater) " +
            "values (#{id}, #{username}, #{password}, #{name}, #{sex}, #{idNumber}, #{phone}, #{status}, #{createTime}, #{updateTime}, #{creator}, #{updater})")
    void add(Employee employee);
    void    insert(Employee employee);
    /**
     * 员工分页查询
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    /**
     * 根据主键动态修改属性
     */
    void update(Employee employee);

}