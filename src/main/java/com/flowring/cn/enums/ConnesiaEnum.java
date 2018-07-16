package com.flowring.cn.enums;

public enum ConnesiaEnum {
	NO_DATA_ERROR(204, "查無此筆資料", true),
	INPUT_NULL_DATA(500, "輸入資料為空值或空字串", false),
	INPUT_ERROR_ALERT_TYPE(500, "輸入 AlertType 不是1或2", false),
	LOGINID_ERROR(500, "LoginId 錯誤", false),
	LOGINID_REPEAT_ERROR(500, "LoginId 已存在", false),
	MEMBER_NOT_EXIST(500, "會員不存在", false),
	FUNCTIONS_NOT_EXIST(500, "無此功能", false),
	INVITED_MEMBER_NOT_EXIST(500, "受邀會員不存在", false),
	RULE_NOT_EMPTY(500, "規則模式或後續處理不得為空", false),
	PAYLOAD_NOT_EMPTY(500, "資料屬性不得為空", false),
	HIGH_AUTHORITY_REPEAT(500, "已有最高權限", false),
	GROUP_NOT_EXIST(500, "該群組不存在", false),
	GROUP_CANNOT_DELETE_BY_ROLE(500, "該區域已有角色管理，不得刪除!", false),
	GROUP_CANNOT_DELETE_BY_SUB_GROUP(500, "該區域有其它子區域，不得刪除!", false),
	ROLE_NOT_EMPTY(500, "角色名稱不得為空", false),
	ROLE_NOT_EXIST(500, "該角色不存在", false),
	ROLE_CANNOT_DELETE(500, "已有人員或區域有該權限，不得刪除!", false),
	ROLE_AUTHORITY_EXIST(500, "角色權限已建立", false),
	TOKEN_ERROR(500, "驗證碼錯誤", false),
	TOKEN_EXPIRED_ERROR(500, "驗證碼失效", false),
	PASSWORD_ERROR(500, "密碼錯誤", false),
	LOGINIDPASSWORD_ERROR(500, "LoginId 或密碼錯誤", false),
	EMAIL_ERROR(500, "Email 錯誤", false),
	EMAIL_AUTH_ERROR(500, "尚未點選 Email 驗證", false),
	DATE_FORMAT_ERROR(500, "日期格式錯誤", false),
	INSERT_FAILD_ERROR(500, "新增失敗", false),
	UPDATE_FAILD_ERROR(500, "修改失敗", false),
	DELETE_FAILD_ERROR(500, "刪除失敗", false),
	RESET_PASSWORD_ERROR(500, "重設密碼失敗", false),
	DEVICE_IS_EXIST_TEMP_DYNAMIC_TABLE(204, "此裝置已經已存在於此模組", true), 
	NOT_MATCH_AF_PRO_ID_AND_CLOSED(500, "closed 與 af_pro_id 不吻合", false),
	INPUT_ERROR_CLOSED(500, "請輸入正確的 closed (closed 為 0、1、2或3)", false);
	
	
	private Integer code;
	private String message;
	private boolean success;
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	ConnesiaEnum(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
