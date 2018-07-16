package com.flowring.cn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.GroupDao;
import com.flowring.cn.entity.Group;

@Repository
public class GroupDaoImpl extends NamedParameterJdbcDaoSupport implements
		GroupDao {
	
	public final static String GET_ALL_GROUP = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group";
	public final static String GET_GROUP_BY_DELETED = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group WHERE deleted=:deleted";
	public final static String GET_GROUP_BY_ID = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group WHERE id=:id";
	public final static String GET_GROUP_BY_PARENT_ID = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group WHERE parent_id=:parent_id";
	public final static String GET_GROUP_BY_PARENT_ID_AND_DELETED = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group WHERE parent_id=:parent_id AND deleted=:deleted";
	public final static String GET_GROUP_BY_CREATOR_ID_AND_DELETED = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group WHERE creator=:creator AND deleted=:deleted ORDER BY id DESC";
	public final static String GET_GROUP_BY_NAME_AND_DELETED = "SELECT id, parent_id, name, location, description, creator, map_type, deleted FROM cn_group WHERE name=:name AND deleted=:deleted";
	public final static String INSERT_GROUP = "INSERT INTO cn_group(parent_id, name, location, description, creator, map_type, deleted) VALUES (:parent_id, :name , :location , :description, :creator, :map_type, :deleted)";
	public final static String UPDATE_GROUP = "UPDATE cn_group SET parent_id=:parent_id, name=:name, location=:location, description=:description, creator=:creator, map_type=:map_type, deleted=:deleted WHERE id=:id ";
	public final static String DELETE_GROUP_BY_ID = "DELETE FROM cn_group WHERE (id=:id) ";
	public final static String UPDATE_GROUP_DELETED_BY_ID = "UPDATE cn_group SET deleted=:deleted WHERE id=:id ";
	
	@Autowired
	GroupDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	/*
	 * 查詢群組 (non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.GroupDao#getAllGroup()
	 */
	public List<Group> getAllGroup() {
		List<Group> rtnValue = new ArrayList<Group>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_ALL_GROUP,
				parameters, new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;
	}

	/*
	 * 取得未被使用者刪除的群組 (non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.GroupDao#getGroupListByDeleted(boolean)
	 */
	@Override
	public List<Group> getGroupListByDeleted(boolean deleted) {
		List<Group> rtnValue = new ArrayList<Group>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"deleted", deleted);
		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_GROUP_BY_DELETED,
				parameters, new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;
	}

	@Override
	/*
	 * 查詢群組 ById (non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.GroupDao#getGroupById(int)
	 */
	public Group getGroupById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		Group rtnValue = this.getNamedParameterJdbcTemplate().queryForObject(
				GET_GROUP_BY_ID, parameters,
				new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;
	}
	
	@Override
	public List<Group> getGroupByNameAndDeleted(String name, boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", name)
				.addValue("deleted", deleted);
		List<Group> rtnValue = this.getNamedParameterJdbcTemplate().query(GET_GROUP_BY_NAME_AND_DELETED, parameters, new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;	
	}

	@Override
	/*
	 * 查詢群組 ByParentId(non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.GroupDao#getGroupByParentId(int)
	 */
	public List<Group> getGroupByParentId(int parentId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"parent_id", parentId);
		List<Group> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_GROUP_BY_PARENT_ID, parameters,
				new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;
	}
	
	@Override
	public List<Group> getGroupByParentIdAndDeleted(int parentId,
			boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"parent_id", parentId).addValue(
						"deleted", deleted);
		List<Group> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_GROUP_BY_PARENT_ID_AND_DELETED, parameters,
				new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;
	}
	
	@Override
	public List<Group> getGroupByCreatorAndDeleted(int creator, boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("creator", creator)
				.addValue("deleted", deleted);
		List<Group> rtnValue = this.getNamedParameterJdbcTemplate().query(GET_GROUP_BY_CREATOR_ID_AND_DELETED, parameters, new BeanPropertyRowMapper<Group>(Group.class));
		return rtnValue;			
	}

	@Override
	/*
	 * 新增群組(non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.GroupDao#insertGroup(com.flowring.cn.entity.Group)
	 */
	public int insertGroup(Group group) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("parent_id", group.getParentId())
				.addValue("name", group.getName())
				.addValue("location", group.getLocation())
				.addValue("description", group.getDescription())
				.addValue("creator", group.getCreater())
				.addValue("map_type", group.getMapType())
				.addValue("deleted", group.isDeleted());
		this.getNamedParameterJdbcTemplate().update(INSERT_GROUP, parameters);
		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		group.setId(id);
		return id;
	}

	@Override
	/*
	 * 修改群組(non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.GroupDao#updateGroup(com.flowring.cn.entity.Group)
	 */
	public int updateGroup(Group group) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("parent_id", group.getParentId())
				.addValue("id", group.getId())
				.addValue("location", group.getLocation())
				.addValue("name", group.getName())
				.addValue("description", group.getDescription())
				.addValue("creator", group.getCreater())
				.addValue("map_type", group.getMapType())
				.addValue("deleted", group.isDeleted());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_GROUP, parameters);
		return result;
	}

	@Override
	/*
	 * 刪除群組(non-Javadoc)，但不用。
	 * 
	 * @see com.flowring.cn.dao.GroupDao#deleteGroupById(int)
	 */
	public int deleteGroupById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_GROUP_BY_ID,
				parameters);
		return rs;
	}

	/*
	 * 刪除裝置(修改裝置deleted欄位) (non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.GroupDao#updateGroupDeletedById(int, boolean)
	 */
	@Override
	public int updateGroupDeletedById(int id, boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id).addValue("deleted", deleted);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_GROUP_DELETED_BY_ID, parameters);
		return result;
	}

}
