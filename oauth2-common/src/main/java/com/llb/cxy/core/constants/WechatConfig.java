package com.llb.cxy.core.constants;

/**
 * description: 微信授权登录的一些常量 <br>
 * date: 2019/12/19 15:01 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class WechatConfig {

	/**微信授权成功后回调地址*/
	public static final String CALL_BACK_URI = "http://www.5itrip.com";
	//登录
	public static final String LOGIN_URI = "http://www.5itrip.com/wx/loginPage";
	//我的订单
	public static final String MY_ORDER_URI = "http://www.5itrip.com/#/mobile/orderList";
	//我的审批
	public static final String MY_APPROVAL_URI = "http://www.5itrip.com/#/approval/list";
	
	/**微信授权获取code地址*/
	public static final String WEB_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	//获取openid url
	public final static String getOpen_id_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	//主动推送信息接口
	public final static String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	//模板主动推送信息接口
	public final static String TEMPLATE_SEND_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	/**微信授权Scope snsapi_userinfo、snsapi_base */
	public static final String SCOPE = "snsapi_base";

	/**微信授权STATE*/
	public static final String STATE = "STATE";

	/**微信公众号APP_ID*/
	//public static final String APP_ID = "wxca4a524f3aabdef3";

	/**微信公众号APP_SECRET*/
	//public static final String APP_SECRET = "6451f1cc25c36c91a18dd95c0768e14e";

	//公众平台id
	public final static String APP_ID               = "wxc7985953a73c1612";                                   // 微信公众号身份的唯一标识。审核通过后，在微信发送的邮件中查看
	//	公众平台密钥
	public final static String APP_SECRET           = "7a5e40fb5a18952e6347ac60d49a7280";                     //公众号appsecret
	//获取 access  token URL
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ WechatConfig.APP_ID + "&secret=" + WechatConfig.APP_SECRET;
	
	//微信推送出票消息模板地址
	public final static String SEND_TICKET_MSG  = "http://www.5itrip.com:8010/wx/sendIssueTicketMsgList";
	//微信推送改签消息模板地址
	public final static String SEND_CHANGING_TICKET_MSG  = "http://www.5itrip.com:8010/wx/sendChangingIssueTicketMsgList";
	//微信推送退票消息模板地址
	public final static String SEND_REFUND_TICKET_MSG  = "http://www.5itrip.com:8010/wx/sendRefundIssueTicketMsgList";
}
