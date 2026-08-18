package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
<<<<<<< HEAD
=======
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
>>>>>>> cca8e75 (苍穹外卖初始代码)

@Mapper
public interface EmployeeMapper {

<<<<<<< HEAD
    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

}
=======
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    void insert(Employee employee);

    List<Employee> pageQuery(String name);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    @Update("update employee set " +
            "name = #{name}, " +
            "phone = #{phone}, " +
            "sex = #{sex}, " +
            "id_number = #{idNumber}, " +
            "update_time = #{updateTime}, " +
            "update_user = #{updateUser} " +
            "where id = #{id}")
    void update(Employee employee);

    @Update("update employee set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    void statusUpdate(Long id, Integer status, LocalDateTime updateTime, Long updateUser);

}
>>>>>>> cca8e75 (苍穹外卖初始代码)
