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
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.GroupPropertiesDao;
import com.flowring.cn.entity.GroupProperties;

@Repository
public class GroupPropertiesDaoImpl extends NamedParameterJdbcDaoSupport
		implements GroupPropertiesDao {
	
	public final static String GET_ALL_GROUP_PROP = "SELECT id, group_id, english_name, name, value, fix, deleted FROM group_properties";
	public final static String GET_GROUP_PROP_BY_GROUP_ID_AND_DELETED = "SELECT id, group_id, english_name, name, value, fix, deleted FROM group_properties WHERE group_id=:group_id AND deleted=:deleted";
	public final static String GET_GROUP_PROP_BY_ID = "SELECT id, group_id, english_name, name, value, fix, deleted FROM group_properties WHERE id=:id";
	public final static String INSERT_GROUP_PROP_LIST = "INSERT INTO group_properties(group_id, english_name, name, value, fix, deleted) VALUES (:group_id, :english_name, :name, :value, :fix, :deleted)";
	public final static String UPDATE_GROUP_PROP_LIST = "UPDATE group_properties SET group_id=:group_id, english_name=:english_name, name=:name, value=:value, fix=:fix, deleted=:deleted WHERE id=:id";
	public final static String UPDATE_GROUP_PROP_DELETED_BY_ID = "UPDATE group_properties SET deleted=:deleted WHERE id=:id ";
	public final static String UPDATE_GROUP_PROP_LIST_DELETED = "UPDATE group_properties SET deleted=:deleted WHERE id=:id";
	
	@Autowired
	GroupPropertiesDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<GroupProperties> getAllGroupPropList() {
		List<GroupProperties> rtnValue = new ArrayList<GroupProperties>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_ALL_GROUP_PROP,
				parameters, new BeanPropertyRowMapper<GroupProperties>(GroupProperties.class));
		return rtnValue;
	}
	

	@Override
	public List<GroupProperties> getGroupPropListByGroupIdAndDeleted(int groupId, boolean deleted) {
		List<GroupProperties> rtnValue = new ArrayList<GroupProperties>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"group_id", groupId)
				.addValue("deleted", deleted);
		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_GROUP_PROP_BY_GROUP_ID_AND_DELETED,
				parameters, new BeanPropertyRowMapper<GroupProperties>(GroupProperties.class));
		
		return rtnValue;
	}
	
	
	@Override
	public GroupProperties getGroupPropById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		GroupProperties groupProperties = this
				.getNamedParameterJdbcTemplate().queryForObject(GET_GROUP_PROP_BY_ID,
						parameters,
						new BeanPropertyRowMapper<GroupProperties>(GroupProperties.class));
		return groupProperties;
	}

	@Override
	@Transactional
	public int insertGroupPropList(List<GroupProperties> groupPropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (GroupProperties groupProperties : groupPropertiesList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("group_id", groupProperties.getGroupId())
					.addValue("english_name", groupProperties.getEnglishName())
					.addValue("name", groupProperties.getName())
					.addValue("value", groupProperties.getValue())
					.addValue("fix", groupProperties.isFix())
					.addValue("deleted", groupProperties.isDeleted()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_GROUP_PROP_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for(int i : rs){
			total += i;
		}
		return total;
	}

	@Override
	@Transactional
	public int updateGroupPropList(List<GroupProperties> groupPropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (GroupProperties groupProperties : groupPropertiesList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("id", groupProperties.getId())
					.addValue("english_name", groupProperties.getEnglishName())
					.addValue("group_id", groupProperties.getGroupId())
					.addValue("name", groupProperties.getName())
					.addValue("value", groupProperties.getValue())
					.addValue("fix", groupProperties.isFix())
					.addValue("deleted", groupProperties.isDeleted()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_GROUP_PROP_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for(int i : rs){
			total += i;
		}
		return total;
	}

	@Override
	public int updateGroupPropDeletedById(int id, boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id).addValue("deleted", deleted);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_GROUP_PROP_DELETED_BY_ID, parameters);
		return result;
	}

	@Override
	@Transactional
	public int updateGroupPropListDeleted(
			List<GroupProperties> groupPropertiesList, boolean deleted) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (GroupProperties groupProperties : groupPropertiesList) {
			parameters.add(new MapSqlParameterSource().addValue("id",
					groupProperties.getId()).addValue("deleted", deleted));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_GROUP_PROP_LIST_DELETED, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for(int i: rs){
			total += i;
		}
		return total;
	}

}
