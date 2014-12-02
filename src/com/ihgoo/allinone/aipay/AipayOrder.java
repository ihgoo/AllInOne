package com.ihgoo.allinone.aipay;

import java.net.URLEncoder;

import android.app.Activity;
import android.widget.Toast;

/**
 * 支付宝订单类
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class AipayOrder {

	/**
	 * 获取订单信息
	 * 
	 * @param orderId
	 *            订单号
	 * @param subject
	 *            支付商品的主题
	 * @param totalfee
	 *            总共需要支付的金额
	 * @param detail
	 *            支付商品的详情
	 * @return 成功返回新的订单组成的内容
	 */
	public static String getNewOrderInfo(String orderId, String subject, String totalfee, String detail) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(AipayKeys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(orderId);
		sb.append("\"&subject=\"");
		sb.append(subject);
		sb.append("\"&body=\"");
		sb.append(detail);
		sb.append("\"&total_fee=\"");
		sb.append(totalfee);
		sb.append("\"&notify_url=\"");
		sb.append(URLEncoder.encode(AipayKeys.NOTIFY_URL));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(AipayKeys.DEFAULT_SELLER);
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");
		return new String(sb);
	}

	/**
	 * 获得RSA后完整字符串
	 * 
	 * @param info
	 *            之前拼接的订单信息
	 * @return 待发送至支付宝的字符串
	 */
	public static String getEncodeInfo(String info) {
		String sign = Rsa.sign(info, Keys.PRIVATE);
		sign = URLEncoder.encode(sign);
		info += "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
		return info;
	}

	/**
	 * 根据返回的code作出响应
	 * 
	 * @param activity
	 *            当前Activity
	 * @param code
	 *            返回的状态码
	 */
	public static void getResponseCode(Activity activity, String code) {
		if (code.equals("9000")) {
			Toast.makeText(activity, "支付成功~", Toast.LENGTH_SHORT).show();
			activity.finish();
		} else if (code.equals("4000")) {
			Toast.makeText(activity, "支付宝系统异常~", Toast.LENGTH_SHORT).show();
			activity.finish();
		} else if (code.equals("4001")) {
			Toast.makeText(activity, "订单参数错误~", Toast.LENGTH_SHORT).show();
			activity.finish();
		} else if (code.equals("6001")) {
		} else if (code.equals("6002")) {
			Toast.makeText(activity, "网络连接异常~", Toast.LENGTH_SHORT).show();
			activity.finish();
		}
	}

}
