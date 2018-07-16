package com.flowring.cn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.ForgetPwdDao;
import com.flowring.cn.entity.ForgetPwd;

@Repository
public class ForgetPwdDaoImpl extends NamedParameterJdbcDaoSupport implements ForgetPwdDao {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	@Autowired
	ForgetPwdDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public int insertForgetPwd(ForgetPwd forgetPwd) {
		StringBuffer sql = new StringBuffer("INSERT INTO forget_pwd (member_id, valid_time, verification_code, status) VALUES (:member_id, :valid_time, :verification_code, :status)");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", forgetPwd.getMemberId())
			.addValue("valid_time", forgetPwd.getValidTime())
			.addValue("verification_code", forgetPwd.getVerificationCode())
			.addValue("status", forgetPwd.getStatus());
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		forgetPwd.setId(id);
		return id;	
	}

	@Override
	public int updateForgetPwd(ForgetPwd forgetPwd) {
		StringBuffer sql = new StringBuffer("UPDATE forget_pwd SET member_id=:member_id, valid_time=:valid_time, verification_code=:verification_code, status=:status WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("member_id", forgetPwd.getMemberId())
				.addValue("valid_time", forgetPwd.getValidTime())
				.addValue("verification_code", forgetPwd.getVerificationCode())
				.addValue("status", forgetPwd.getStatus())
				.addValue("id", forgetPwd.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public ForgetPwd getForgetPwdById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * FROM forget_pwd WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", id);
		return (ForgetPwd) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(ForgetPwd.class));
	}
	
	@Override
	public ForgetPwd getForgetPwdByVerificationCode(String verificationCode) {
		StringBuffer sql = new StringBuffer("SELECT * FROM forget_pwd WHERE verification_code=:verification_code ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("verification_code", verificationCode);
		return (ForgetPwd) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(ForgetPwd.class));
	}

	@Override
	public List<ForgetPwd> getForgetPwdByMemId(int memberId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM forget_pwd WHERE member_id=:member_id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", memberId);
		
		List<ForgetPwd> forgetPwdList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, 
				new RowMapper<ForgetPwd>() {

			@Override
			public ForgetPwd mapRow(ResultSet rs, int rowNum) throws SQLException {
				ForgetPwd forgetPwd = new ForgetPwd();
				forgetPwd.setId(rs.getInt("id"));
				forgetPwd.setMemberId(rs.getInt("member_id"));
				forgetPwd.setValidTime(rs.getInt("valid_time"));
				forgetPwd.setVerificationCode(rs.getString("verification_code"));
				forgetPwd.setStatus(rs.getString("status"));
				forgetPwd.setChangeTime(sdf.format(rs.getTimestamp("change_time")));
				return forgetPwd;
			}
		});
		return forgetPwdList;
	}

}
