package com.candata.magic.mapper;

import com.candata.magic.pojo.Player;


public interface PlayerMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}