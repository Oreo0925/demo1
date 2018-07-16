package com.flowring.cn.dao.impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.SequenceDao;
import com.flowring.cn.entity.Sequence;


@Repository
public class SequenceDaoImpl extends NamedParameterJdbcDaoSupport implements SequenceDao {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final String INSERT_SQL = "INSERT INTO sequence (name, prefix_code, today, type) VALUES(:name, :prefixCode, :today, :type)";
	private static final String QUERY_SQL = "SELECT * FROM sequence WHERE name = :name AND prefix_code = :prefixCode AND today = Date(:today)";
	private static final String QUERY_NAME_SQL = "SELECT * FROM sequence WHERE name = :name";
	private static final String UPDATE_SQL = "UPDATE sequence SET cur_value = (@next := cur_value + :increment) WHERE name = :name";
	private static final String UPDATE_TODAY_PREFIX_CODE_SQL = "UPDATE sequence SET cur_value = (@next := cur_value + :increment) WHERE name = :name AND prefix_code = :prefixCode AND today = Date(:today)";
	private static final String GET_SQL = "SELECT @next";

	public SequenceDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public long getNextSequenceByName(String name, String prefixCode, Date today, int type) {
		MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("name", name);
		Sequence sequence = null;
		try {
			sequence = getNamedParameterJdbcTemplate().queryForObject(QUERY_NAME_SQL, parameters,
					new BeanPropertyRowMapper<Sequence>(Sequence.class));
		} catch (EmptyResultDataAccessException e) {
			newSequence(name, prefixCode, new Date(), type);
			sequence = getNamedParameterJdbcTemplate().queryForObject(QUERY_NAME_SQL, parameters,
					new BeanPropertyRowMapper<Sequence>(Sequence.class));
		}
		int increment = sequence.getIncrement();
		parameters.addValue("increment", increment);
		getNamedParameterJdbcTemplate().update(UPDATE_SQL, parameters);

		MapSqlParameterSource parameters2 = new MapSqlParameterSource();
		long nextId = getNamedParameterJdbcTemplate().queryForObject(GET_SQL, parameters2, Long.class);

		return nextId;
	}

	@Override
	public long getNextSequence(String name, String prefixCode, Date today, int type) {
		long nextId = 0;

		try {
			MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", name)
				.addValue("prefixCode", prefixCode)
				.addValue("today", sdf.format(today));
		
			Sequence sequence = null;
			try {
				sequence = getNamedParameterJdbcTemplate().queryForObject(QUERY_SQL, parameters,
						new BeanPropertyRowMapper<Sequence>(Sequence.class));
			} catch (EmptyResultDataAccessException e) {
				newSequence(name, prefixCode, new Date(), type);
				sequence = getNamedParameterJdbcTemplate().queryForObject(QUERY_SQL, parameters,
						new BeanPropertyRowMapper<Sequence>(Sequence.class));
			}
			
			int increment = sequence.getIncrement();

			parameters.addValue("increment", increment);

			getNamedParameterJdbcTemplate().update(UPDATE_TODAY_PREFIX_CODE_SQL, parameters);

			MapSqlParameterSource parameters2 = new MapSqlParameterSource();
			nextId = getNamedParameterJdbcTemplate().queryForObject(GET_SQL, parameters2, Long.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nextId;
	}

	@Override
	public int newSequence(String name, String prefixCode, Date today, int type) {
		
		int lastId = 0;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", name)
				.addValue("prefixCode", prefixCode)
				.addValue("today", sdf.format(today))
				.addValue("type", type);
			
		parameters.registerSqlType("today", Types.DATE);
		
		this.getNamedParameterJdbcTemplate().update(INSERT_SQL, parameters);

		lastId = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);

		return lastId;
	}
	
}
