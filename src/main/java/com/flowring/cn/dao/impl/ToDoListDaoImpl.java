package com.flowring.cn.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.ToDoListDao;
import com.flowring.cn.entity.Rule;
import com.flowring.cn.entity.ToDoList;

@Repository
public class ToDoListDaoImpl extends NamedParameterJdbcDaoSupport implements ToDoListDao {
	
	@Autowired
	ToDoListDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertToDoList(ToDoList toDoList) {
		Rule rule = getRuleByRuleId(toDoList.getRuleId());
		StringBuffer sql = new StringBuffer("INSERT INTO to_do_list (rule_id, rule_conditions, rule_status, finished, feedback) values(:ruleId, :ruleConditions, :ruleStatus, :finished, :feedback)");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("ruleId", toDoList.getRuleId())
				.addValue("ruleConditions", rule.getConditions())
				.addValue("ruleStatus", rule.getStatus())
				.addValue("finished", false)
				.addValue("feedback", "");
		
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		toDoList.setId(id);
		toDoList.setRuleConditions(rule.getConditions());
		toDoList.setRuleStatus(rule.getStatus());
		return id;
	}

	private Rule getRuleByRuleId(int ruleId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM rule WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", ruleId);
		return (Rule) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(Rule.class));
	}

	@Override
	public ToDoList updateToDoList(ToDoList toDoList) {
		StringBuffer sql = new StringBuffer("UPDATE to_do_list SET rule_status=:ruleStatus, finish_time=:finishTime, finished=:finished, feedback=:feedback WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("ruleStatus", toDoList.getRuleStatus())
			.addValue("finishTime", toDoList.getFinishTime())
			.addValue("finished", toDoList.isFinished())
			.addValue("feedback", toDoList.getFeedback())
			.addValue("id", toDoList.getId());
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		ToDoList newToDoList = getToDoListById(toDoList.getId());
		return newToDoList;
	}

	@Override
	public boolean deleteToDoList(int id) {
		StringBuffer dsql = new StringBuffer("DELETE FROM to_do_list WHERE id=:id ");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

	@Override
	public ToDoList getToDoListById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * FROM to_do_list WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		return (ToDoList) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(ToDoList.class));
	}

	@Override
	public List<ToDoList> getToDoListByRuleId(int ruleId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM to_do_list WHERE rule_id=:ruleId ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("ruleId", ruleId);
		return (List<ToDoList>) this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(ToDoList.class));
	}
	
	
}
