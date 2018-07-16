package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.GroupProperties;


public interface GroupPropertiesDao {
    public List<GroupProperties> getAllGroupPropList();
    public List<GroupProperties> getGroupPropListByGroupIdAndDeleted(int groupId, boolean deleted);
    public GroupProperties getGroupPropById(int id);
    public int insertGroupPropList(List<GroupProperties> groupPropertiesList);
    public int updateGroupPropList(List<GroupProperties> groupPropertiesList);
    public int updateGroupPropDeletedById(int id, boolean deleted);
    public int updateGroupPropListDeleted(List<GroupProperties> groupPropertiesList, boolean deleted);
}
