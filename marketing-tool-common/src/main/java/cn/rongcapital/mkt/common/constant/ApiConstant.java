/*************************************************
 * @功能简述: API常量类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.constant;

public class ApiConstant {

	public static final String CONTENT_TYPE_JSON = "application/json";
	
	public static final String API_METHOD = "method";//api的method参数
	public static final String API_PATH = "/api";//api的路径
	public static final String API_USER_TOKEN = "user_token";//api的user_token参数
	public static final String API_USER_TOKEN_VALUE = "123";//api的user_token参数
	public static final String API_USER_ID = "user_id";//api的user_id参数	
	public static final String API_PATH_APPID = "/api/wx1f363449a14a1ad8";//api的路径
	public static final byte TABLE_DATA_STATUS_VALID = 0;//数据正常
	public static final byte TABLE_DATA_STATUS_INVALID = 1;//数据已被逻辑删除
	
	public static final int WECHAT_CHANNEL_TYPE_SYSTEM = 0;//渠道类型0系统默认渠道
	public static final int WECHAT_CHANNEL_TYPE_CUSTOM = 1;//渠道类型1自定义渠道
	
	public static final String WECHAT_CHANNEL_STATUS_VALID = "0";//渠道状态正常
	public static final String WECHAT_CHANNEL_STATUS_INVALID = "1";//渠道状态正常删除
	
	public static final int TABLE_DATA_REMOVED_NOTDEL = 0;//不可以删除
	public static final int TABLE_DATA_REMOVED_DEL = 1;//可以删除
	public static final byte TABLE_DATA_REMOVED_FAIL = 3;//失效

	public static final byte SEGMENT_PUBLISH_STATUS_NOT_PUBLISH = 0;//未发布
	public static final byte SEGMENT_PUBLISH_STATUS_PUBLISH = 1;//已发布
	public static final byte SEGMENT_PUBLISH_STATUS_ALL = 3;//全部细分
	
	public static final byte CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH = 0;//未发布
	public static final byte CAMPAIGN_PUBLISH_STATUS_PUBLISH = 1;//已发布
	public static final byte CAMPAIGN_PUBLISH_STATUS_IN_PROGRESS = 2;//活动中
	public static final byte CAMPAIGN_PUBLISH_STATUS_FINISH = 3;//已结束
	public static final byte CAMPAIGN_PUBLISH_STATUS_ALL = 4;//全部活动
	
	public static final byte SEGMENT_COMPILE_STATUS_YES = 0;//可编辑
	public static final byte SEGMENT_COMPILE_STATUS_NO = 1;//不可编辑
	
	public static final int INT_ZERO = 0;
	public static final int INT_ONE = 1;
	
	public static final String WECHAT_GROUP = "999";
	
	public static final String WECHAT_GROUP_M = "999,";
	
	public static final String WECHAT_GROUP_NAME = "未分组";
	
	public static final int PAGE_START_INDEX_DEFAULT = 0;//默认分页起始index值
	public static final int PAGE_PAGE_SIZE_DEFAULT = 10;//默认每页记录数

	public static final String FILE_UPLOAD_URL = "mkt.service.file.upload";   //文件上传地址

	public static final String COUPON_FILE_UPLOAD_URL = "mkt.materiel.coupon.file.upload";   //文件上传地址
	
	public static final int WECHAT_ASSET_SERVER_NUMBER = 0;
	public static final int WECHAT_ASSET_PERSONAL_NUMBER = 1;
	public static final int WECHAT_ASSET_SUBSCRIPTION_NUMBER=2;
	
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String DATE_FORMAT_CONSTANT_BEGIN = " 00:00:00";
    public static final String DATE_FORMAT_CONSTANT_END = " 23:59:59";
	
	public static final byte TAG_TYPE_SEGMENT = 0;// 细分
	public static final byte TAG_TYPE_ACTIVITY = 1;// 活动
	public static final byte TAG_TYPE_CONTACT = 2;// 联系人

	public static final byte CAMPAIGN_SWITCH_SWITCH_YES = 0;//是分支
	public static final byte CAMPAIGN_SWITCH_SWITCH_NO = 1;//非分支
	public static final byte CAMPAIGN_SWITCH_ENDS = 2;//多分支类型
	
	public static final byte CAMPAIGN_PARENT_NODE_PTYPE = -1;//父节点的ptype列值

	public static final byte CAMPAIGN_NODE_TRIGGER = 0;//触发
	public static final byte CAMPAIGN_NODE_AUDIENCE = 1;//受众
	public static final byte CAMPAIGN_NODE_DECISION = 2;//决策
	public static final byte CAMPAIGN_NODE_ACTION = 3;//行动
	
	public static final byte CAMPAIGN_ITEM_TRIGGER_TIMMER = 0;//时间触发
	public static final byte CAMPAIGN_ITEM_TRIGGER_MANUAL = 1;//手动触发

	public static final byte CAMPAIGN_ITEM_AUDIENCE_TARGET = 0;//目标人群
	public static final byte CAMPAIGN_ITEM_AUDIENCE_EVENT = 1;//事件人群
	
	public static final int TAG_ITEM_SYSTEM = 0;//系统标签
	public static final int TAG_ITEM_CUSTOM = 1;//自定义标签
	
	
	public static final byte CAMPAIGN_ITEM_DECISION_PROP_COMPARE = 0;//联系人属性比较
	public static final byte CAMPAIGN_ITEM_DECISION_WECHAT_SENT = 1;//微信图文是否发送
	public static final byte CAMPAIGN_ITEM_DECISION_WECHAT_READ = 2;//微信图文是否阅读
	public static final byte CAMPAIGN_ITEM_DECISION_WECHAT_FORWARD = 3;//微信图文是否转发
	public static final byte CAMPAIGN_ITEM_DECISION_IS_SUBSCRIBE = 4;//是否订阅公众号
	public static final byte CAMPAIGN_ITEM_DECISION_IS_PRIVT_FRIEND = 5;//是否个人号好友
	public static final byte CAMPAIGN_ITEM_DECISION_TAG = 6;//标签判断
	
	public static final byte CAMPAIGN_ITEM_ACTION_WAIT = 0;//等待
	public static final byte CAMPAIGN_ITEM_ACTION_SAVE_AUDIENCE = 1;//保存当前人群
	public static final byte CAMPAIGN_ITEM_ACTION_SET_TAG = 2;//设置标签
	public static final byte CAMPAIGN_ITEM_ACTION_ADD_CAMPAIGN = 3;//添加到其它活动
	public static final byte CAMPAIGN_ITEM_ACTION_MOVE_CAMPAIGN = 4;//转移到其它活动
	public static final byte CAMPAIGN_ITEM_ACTION_SEND_WECHAT_H5 = 5;//发送微信图文
	public static final byte CAMPAIGN_ITEM_ACTION_SEND_H5 = 6;//发送H5活动
	public static final byte CAMPAIGN_ITEM_ACTION_SEND_PRVT_INFO = 7;//发送个人号信息
	
	public static final byte TASK_STATUS_VALID = 0;//任务可运行
	public static final byte TASK_STATUS_INVALID = 1;//任务不可运行
	
	public static final byte CAMPAIGN_DECISION_TAG_RULE_MATCH_ALL = 0;//全部匹配
	public static final byte CAMPAIGN_DECISION_TAG_RULE_MATCH_ONE = 1;//匹配其一
	public static final byte CAMPAIGN_DECISION_TAG_RULE_MATCH_TWO_MORE = 2;//匹配二个及以上
	
	public static final long TASK_SCAN_INTERVAL_MILLS = 1000*3; //扫描任务表的时间间隔:毫秒
	public static final long TASK_DO_INTERVAL_MILLS = 1000*3; //扫描执行列表的时间间隔:毫秒
	
	public static final int SEGMENTATION_GROUP_MEMBER_MOST_COUNT = 3;//细分每个分组内标签的最多数量

	//时间触发节点
	public static final String TASK_NAME_CAMPAIGN_TRUGGER_TIME = "campaignTriggerTimeTask";
	//目标人群节点
	public static final String TASK_NAME_CAMPAIGN_AUDIENCE_TARGET = "campaignAudienceTargetTask";
	//微信是否发送节点(已去掉)
//	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_SENT = "campaignDecisionWechatSentTask";
	//微信是否阅读节点
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_READ = "campaignDecisionWechatReadTask";
	//微信是否转发节点
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_FORWARD = "campaignDecisionWechatForwardTask";
	//是否关注公众号
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_SUBSCRIBE = "campaignDecisionWechatSubscribeTask";
	//是否个人号好友
	public static final String TASK_NAME_CAMPAIGN_DECISION_WECHAT_PRV_FRIEND = "campaignDecisionWechatPrivFriendTask";
	//标签判断
	public static final String TASK_NAME_CAMPAIGN_DECISION_TAG = "campaignDecisionTagTask";
	//公众号发送微信图文
	public static final String TASK_NAME_CAMPAIGN_ACTION_PUBWECHAT_SEND_H5 = "campaignActionPubWechatSendH5Task";
	//发送个人号消息
	public static final String TASK_NAME_CAMPAIGN_ACTION_WECHAT_PRV_SEND_INFO = "campaignActionPrvWechatSendInfoTask";
	//公众号发送H5图文:个人号暂时不支持发送H5图文
	public static final String TASK_NAME_CAMPAIGN_ACTION_WECHAT_SEND_H5 = "campaignActionWechatSendH5Task";
	//等待
	public static final String TASK_NAME_CAMPAIGN_ACTION_WAIT = "campaignActionWaitTask";
	//保存当前人群
    public static final String TASK_NAME_CAMPAIGN_ACTION_SAVE_AUDIENCE = "campaignActionSaveAudienceTask";
	//设置标签
    public static final String TASK_NAME_CAMPAIGN_ACTION_SET_TAG = "campaignActionSetTagTask";
    //联系人属性比较
    public static final String TASK_NAME_CAMPAIGN_DECISION_PROP_COMPARE = "campaignDecisionPropTask";
	
	public static final int DATA_PARTY_MD_TYPE_WECHAT = 8;//data_party表:微信类型数据
	public static final int DATA_PARTY_MD_TYPE_POPULATION = 1; //data_party表:人口属性数据
	/**
	 * campaign_decision_wechat_forward表:forward_times
	 */
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_NOLIMIT = 0;//不限
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_ONE = 1;//超1人次
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_TEN = 2;//超10人次
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_ONE_HUNDRED = 3;//超100人次
	public static final byte CAMPAIGN_DECISION_WECHAT_FORWARD_FORWARD_TIMES_FIVE_HUNDRED = 4;//超500人次


	public static final String DL_PUB_GRANT_API = "ruixue.hfive.mkt.pub.grant";
	public static final String DL_PUB_LIST_API = "ruixue.hfive.mkt.pub.list";
	public static final String DL_PUB_FANSLIST_API = "ruixue.hfive.mkt.pub.fanslist";
    public static final String DL_PERSONAL_LIST = "ruixue.hfive.mkt.personal.list";
	public static final String DL_PERSONAL_CONTACTLIST = "ruixue.hfive.mkt.personal.contactlist";
	public static final String DL_PERSONAL_GROUPLIST = "ruixue.hfive.mkt.personal.grouplist";
    public static final String DL_WUWEN_SYNC = "ruixue.hfive.mkt.wtuwen.sync";
	public static final String DL_WUWEN_HOST = "ruixue.hfive.mkt.wtuwen.convert";
	public static final String DL_PERSONAL_ISONLINE = "ruixue.hfive.mkt.personal.isonline";
	public static final String DL_IS_PUBLIC_GRANTED = "ruixue.hfive.mkt.grant.status";
	public static final String DL_API_PARAM_METHOD = "method";
	public static final String DL_API_PARAM_PUB_GRANT_CALLBACK_KEY = "callback";
	public static final String DL_API_PARAM_PUB_GRANT_CALLBACK_VALUE = "mkt.data.inbound.wechat.public.auth.callback";
	public static final Integer FANS_LIST_SYNC_SIZE = 100;
	public static final Integer IMGTEXT_SYNC_SIZE = 100;

	public static final String DL_PUB_SEND_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.pub.send&pid=";
	public static final String DL_PUB_ISSENT_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.sent&pid=";
	public static final String DL_PUB_ISREAD_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.viewed&pid=";
	public static final String DL_PUB_ISFORWARD_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.wtuwen.shared&pid=";
	public static final String DL_PRV_SEND_API_PATH = "/auth-template/api/?method=ruixue.hfive.mkt.personal.send&pid=";
	
	
    public static final String DOWNLOAD_TEMPLATE_FILE_DIR = "//rc/data/templeteFiles";
	public static final String DOWNLOAD_BASE_DIR = "/rc/data/downloads/";
	
	public static final String UPLOAD_BASE_DIR = "//rc/data/uploads/";
	
	public static final String AUDIENCE_SOUCE_NAME_CAMPAIGN = "营销活动";
	public static final String AUDIENCE_SOUCE_NAME_WX = "微信二维码";
	public static final Byte WECHAT_IMGTEXT_TYPE = 0;

	public static String IMPORT_FILE_SOURCE = "文件接入";
	
	//调用后只执行1次的任务,该任务不存入task_schedule表，只在task_run_log表里记录任务日志
	public static final int MANUAL_RUN_ONCE_TASK_ID = 0;

	//自定义标签状态标识
	public static final Byte CUSTOM_TAG_INVALIDATE = 1;
	public static final Byte CUSTOM_TAG_VALIDATE = 0;

	//自定义标签元数据状态标识
	public static final Byte CUSTOM_TAG_ORIGINAL_DATA_MAP_VALIDATE = 0;
	public static final Byte CUSTOM_TAG_ORIGINAL_DATA_MAP_INVALIDATE = 1;

	//注册表中的注册类型 0代表微信注册类型 1代表Bas注册类型
	public static final Byte REGISTER_WECHAT_TYPE = 0;
	public static final Byte REGISTER_BAS_TYPE = 1;

	//注册BAS所需要的URL
	public static final String BAS_HOST = "api.bas.ruixuesoft.com";
    public static final String BAS_URL = "/bas/services/usm/company/registerCompany?terminalType=PC";

    public static final int MONGO_NODEAUDIENCE_SENTSTATUS_H5_SENT = 1;

	// 国籍
    public static final String NATIONALITY_CHINA = "中国";
    public static final String NATIONALITY_FOREIGN = "外国";
    
    // 地区
    public static final String PROVINCE_FOREIGN = "槟榔屿";
    public static final String PROVINCE_CHINA_CAPITAL = "北京";
    
    //城市
    public static final String CITY = "市";
    public static final String CITY_CHINA_CAPITAL = "北京市";
    public static final String CITY_CHINA_BEIHAI = "北海";

	public static final String CAMPAIGN_CONTENT_WECHAT = "微信图文";
	public static final String CAMPAIGN_CONTENT_H5 = "H5活动";
	public static final String CAMPAIGN_CONTENT_PRIVATE_TEXT = "个人号消息";

    public static final String WECHAT_TYPE_PUB = "[公众号]";
    public static final String WECHAT_TYPE_SUB = "[订阅号]";
    public static final String WECHAT_TYPE_PRI = "[个人号]";

	public static final Byte DATA_PARTY_TAG_RULE_TYPE_COMMON = 0;
	public static final Byte DATA_PARTY_TAG_RULE_TYPE_JS = 1;
/*	*//**
	 * 微信公众号ID
	 *//*
	public static final String APPID = "wx00f7d56d549f82ce";	
	*//**
	 * 微信公众号appsecret
	 *//*
	public static final String SECRET = "d30f3c2bdd6f0769673c94365031e588";*/
	
/*	public static final String ENCODING_AES_KEY = "abcdefghijklmnopqrstuvwxyz12345678900987654";
	public static final String TOKEN = "ruixuemarketingcloud";	*/

	public static final String UPLOAD_IMG_PATH_LARGE = "/rc/data/downloads/large/";
	public static final String UPLOAD_IMG_PATH_MIDDLE = "/rc/data/downloads/middle/";
	public static final String UPLOAD_IMG_PATH_SMALL = "/rc/data/downloads/small/";		
	public static final String RETURN_IMG_PATH_LARGE = "large/";
	public static final String RETURN_IMG_PATH_MIDDLE = "middle/";
	public static final String RETURN_IMG_PATH_SMALL = "small/";	
	
	public static final String WEIXIN_QRCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create";	
	public static final String WEIXIN_QRCODE_SHOW = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
//	public static final String WEIXIN_REDIRECT_URL = "http://mc6666.ruixuesoft.com/html/data-access/weixin.html";	
//	public static final String WEIXIN_AUTH_CALLBACK_URI = "http://mc6666srv.ruixuesoft.com/api?method=mkt.data.inbound.wechat.public.auth.code.callback";	
	public static final String WEIXIN_AUTH_COMPONENT_LOGIN_PAGE ="https://mp.weixin.qq.com/cgi-bin/componentloginpage?";
	/**
	 * 微信关注、取消关注、扫描
	 */
	public static final String WEIXIN_MSG_EVENT_TYPE_SCAN="SCAN";
	public static final String WEIXIN_MSG_EVENT_TYPE_SUBSCRIBE="subscribe";
	public static final String WEIXIN_MSG_EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
	
	public static final String SORT_DESC = "desc";
	public static final String SORT_ASC ="asc";
	public static final String SORT_ORDER_FIELD = "orderField";
	public static final String SORT_ORDER_FIELD_TYPE = "orderFieldType";
	
	public static final String AUDIENCE_LIST_ADD_MSG = "人群名称已经重复";

	public static final String CUSTOM_TAG_ROOT = ",自定义";
	public static final String CUSTOM_TAG_SYSTEM_ROOT = ",其他,自定义";
	public static final String CUSTOM_TAG_SYSTEM_PARENT = "其他";
	public static final String CUSTOM_TAG_SEPARATOR = ",";
	public static final Integer CUSTOM_TAG_LAYER_TYPE = 1;
	public static final Integer CUSTOM_TAG_LEAF_TYPE = 2;
	
	/**
	 * 微信粉丝在 data_population 中的bitmap 字段的数值
	 */
	public static final String WEIXIN_BITMAP = "00000011000000000";
	
	/**
	 * 标签级别
	 */
	public static final int TAG_LEVEL = 2;
	
	/**
	 * 标签级别
	 */
	public static final String TAG_LINE = "-";
	
	public static final int WEIXIN_BATCH_GET_USER_INFO_SIZE=100;
	
	public static final int WEIXIN_BATCH_INSERT_USER_INFO_SIZE=100;
	
	
	/**
	 * 登录冲突
	 */
	public static final int USER_TOKEN_LOGIN_CONFLICT =3001;
    /**
     * 回话超时
     */
    public static final int USER_TOKEN_LOGIN_TIMEOUT =3002;
    /**
     * 参数缺失
     */
    public static final int USER_TOKEN_PARAMS_MISSING =3003;
    /**
     * redis保存错误
     */
    public static final int USER_TOKEN_REDIS_SAVE_ERR =3004;
    /**
     * redis信息错误
     */
    public static final int USER_TOKEN_REDIS_INFO_ERR =3005;
    
    /**
     * redis性别标签获取主数据id key
     */
    public static final String GENDER_TAG_KEY_MALE = "tagcoverid:LBej3qLy_0";
    public static final String GENDER_TAG_KEY_FEMALE = "tagcoverid:LBej3qLy_1";
    
    /**
     * 用户所在区域类型
     */
    public static final String DATA_PARTY_LOCATION_TYPE = "1";
    /**
     * 用户活动区域类型
     */
    public static final String DATA_PARTY_ACTIVE_TYPE = "2";
    
    /**
     * 用户所在区域tag_id
     */
    public static final String DATA_PARTY_LOCATION_TAG_ID = "8HARIs7F";
    
    /**
     * 用户活动区域tag_id
     */
    public static final String DATA_PARTY_ACTIVE_TAG_ID = "4HAcjGd5";
    
    
    /**
     * 省份字典表名
     */
    public static final String PROVINCE_DIC_TABLE_NAME = "province_dic";
    
    /**
     * 标签
     */
    public static final String IS_TAG = "1";
    /**
     * 标签值
     */
    public static final String IS_TAG_VALUE = "0";
    
    public static final String SEGMENT_SHOW_PIE = "1";//显示饼图
    public static final String SEGMENT_SHOW_MAP = "2";//显示地图
    
    /**
     * 微信图文的缩略图片存储
     */
    public static final String WEIXIN_MATERIAL_IMG_PATH = "/rc/data/downloads/material/";
    public static final String WEIXIN_MATERIAL_IMG_PATH_TO_SHOW = "material/";

    public static final String WEIXIN_TEST_APPID = "wx570bc396a51b8ff8";
    
    public static final String WEIXIN_AUDIENCE_SOURCE = "微信人群";
    
    /**
     * SQL like ESCAPE CHAR
     */
    public static final String SQL_ESCAPE_CHARACTER = "\\";
    
    /**
     * 系统标签自定义分类最大显示个数
     */
    public static final int TAG_CUSTOM_TAXONOMY_MAX_SHOW = 10;
}
