/*************************************************
 * @功能简述: API错误码类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.constant;

public enum ApiErrorCode {
	
	SUCCESS(0,"success"),
	
	PARAMETER_ERROR(1001,"parameter error"),//1001-1999,参数相关错误码
	VALIDATE_ERROR(1002,"validation failed"),//校验失败
	
	FILE_ERROR(1003,"file is null"),
	
	DB_ERROR(2001,"DB error"),//2001-2999,数据库相关的错误码
	DB_ERROR_TABLE_DATA_NOT_EXIST(2002,"table data not exist"),//数据在表里不存在
	
	BIZ_ERROR(3001,"business error"),//3001-3999,业务逻辑相关错误
	BIZ_ERROR_SEGMENTATION_IN_CAMPAIGN(3003,"egmentation is in campaign"),//细分处在活动中
    BIZ_ERROR_SEGMENTATION_NOT_PUBLISH(3008,"egmentation not published"),//细分未发布

    BIZ_ERROR_CANPAIGN_NOT_PUBLISH(3007,"campaign not published"),//活动没有发布
    BIZ_ERROR_CANPAIGN_PUBLISHED(3011,"campaign is published"),//活动已发布
    BIZ_ERROR_CANPAIGN_IN_PROGRESS(3004,"campaign is in progress"),//活动进行中
    BIZ_ERROR_CANPAIGN_FINISH(3005,"ccampaign is finish"),//活动已结束

	BIZ_ERROR_CANPAIGN_CAN_NOT_MANUAL_START(3006,"campaign can not manual start"),//活动无法手动开启
	BIZ_ERROR_CANPAIGN_CAN_NOT_START(3009,"campaign can not start"),//活动无法开启
	BIZ_ERROR_CONTACTINFO_KEYID(3010,"can not get the keyid"),//
	
	//优惠码校验接口的错误代码
	BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_DELETE(3011,"coupon code status is not undelete"),//当前数据已经不是未删除状态，表示该数据不可用
	BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_RELEASE(3012,"coupon code release status is not received"),//当前优惠码发放状态不是"收到"
	BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_VERIFY(3013,"coupon code verify status is not unverify"),//当前优惠码的核销状态不是未使用
	BIZ_ERROR_MATERIAL_COUPOON_CODE_CHECK_NOT_IN_PERIOD(3014,"coupon code is not in the period of validity"),//当前优惠码不在有效期范围内
	BIZ_ERROR_MATERIAL_COUPOON_CODE_VERIFY_FAILD(3015,"coupon code verify faild"),//优惠码核销失败
	BIZ_ERROR_MATERIAL_COUPOON_VALIDATE_ERROR(3016,"非未使用状态,不可操作!"),
	BIZ_ERROR_MATERIAL_COUPOON_VERIFYDATE_ERROR(3020,"优惠券不存在或是已删除状态 ！"),
	BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE(3017,"coupon have not related to task yet"),//优惠券没有关联任务ID
	BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE_FAILED(3018,"create target group of people faild"),//新建固定人群失败
	BIZ_ERROR_MATERIAL_COUPOON_UPDATE_ERROR(3019,"只有未使用的优惠券才可以编辑！"),
	BIZ_ERROR_MATERIAL_COUPOON_CODE_ERROR(3020,"优惠码正在生成！请稍后操作"),
	BIZ_ERROR_EVENT_SOURCE_CODE_ALREADY_EXIST(3021,"code of event source has already exist."),
	BIZ_ERROR_EVENT_SOURCE_PF_NOT_EXIST(3022,"platform code of event source  is not exist."),
	BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST(3023,"code of event object has already exist."),
	BIZ_ERROR_EVENT_OBJECT_ATTRIBUTE_DUPLICATED(3024,"attribute of event object is duplicated."),

	SMS_ERROR_MATERIAL_CAN_NOT_DELETE(4001,"can not delete the material"),
	SMS_ERROR_MATERIAL_CAN_NOT_UPDATE(4002,"can not update the material"),
	SMS_ERROR_TEMPLETE_CAN_NOT_DELETE(4003,"can not delete the templete"),
	SMS_ERROR_TEMPLETE_CAN_NOT_UPDATE(4004,"can not update the templete"),
	
	VALIDATE_ERROR_SOURCECODE(4005,"source_code validation failed"),//校验失败
	VALIDATE_ERROR_RULE(4006,"rule validation failed"),//校验失败
	VALIDATE_ERROR_TITLE(4007,"title validation failed"),//校验失败
	VALIDATE_ERROR_COUPON_CODE(4008,"common coupon_code validation failed"),//校验失败
	VALIDATE_ERROR_OWN_RULE_ERROR(4009,"own rule validation failed"),//校验失败
	VALIDATE_ERROR_COMMON_RULE_ERROR(4010,"common rule validation failed"),//校验失败
	VALIDATE_ERROR_GENERATE_RULE_ERROR(4011,"generate rule validation failed"),//校验失败
	VALIDATE_ERROR_TIME_ERROR(4012,"起始时间大于结束时间"),//校验失败
	
	VALIDATE_ERROR_EVENT_UNSUBSCRIBABLE(4013,"event subscribe can not cancel"),//事件订阅不可以被取消
	
	EVENT_ERROR_NOT_FOUND_ERROR(5001,"Event not exist"),
	EVENT_SOURCE_ERROR_NOT_FOUND_ERROR(5002,"Event Source not exist"),
	EVENT_OBJECT_ERROR_NOT_FOUND_ERROR(5003,"Event Object not exist"),
	EVENT_SOURCE_ID_NOT_FOUND(5004,"Event Source Id not found"),
	EVENT_OBJECT_ID_NOT_FOUND(5005,"Event Object Id not found"),
	VALIDATE_JSON_ERROR(5006,"JSON validation error"),//校验失败
	
	SYSTEM_ERROR(9001,"system error");//9001-9999,系统错误码
	
    private int code;
     
    private String msg;
     
    private ApiErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
     
    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
    
}
