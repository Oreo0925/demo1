package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Group;


public interface GroupDao {
    public List<Group> getAllGroup();
    public List<Group> getGroupListByDeleted(boolean deleted);
    public Group getGroupById(int id);
    public List<Group> getGroupByNameAndDeleted(String name, boolean deleted);
    public List<Group> getGroupByParentId(int parentId);
    public List<Group> getGroupByParentIdAndDeleted(int parentId, boolean deleted );
    public List<Group> getGroupByCreatorAndDeleted(int creator, boolean deleted);
    public int insertGroup(Group groups);
    public int updateGroup(Group groups);
    public int updateGroupDeletedById(int id, boolean deleted);
    public int deleteGroupById(int id);
}
