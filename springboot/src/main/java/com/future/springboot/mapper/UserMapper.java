package com.future.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.future.springboot.dao.UserDO;
import org.apache.ibatis.annotations.Param;


/**
 * @Description
 * <p>
 *  Mapper 接口
 *  继承BaseMapper，后MyBatis-Plus 就可以替我们自动生成常规的 CRUD 操作
 *  Service CRUD 接口:https://baomidou.com/pages/49cc81/
 *  条件构造器： https://baomidou.com/pages/10c804/
 * </p>
 * @Author v_hhuima
 * @Date 2023/6/8 15:17
 */
public interface UserMapper extends BaseMapper<UserDO> {

    // 条件构造器： https://baomidou.com/pages/10c804/
    default UserDO selectByUsername(@Param("name") String name) {
        return selectOne(new QueryWrapper<UserDO>().eq("name", name));
    }
}
