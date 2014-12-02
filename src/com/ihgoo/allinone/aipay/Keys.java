package com.ihgoo.allinone.aipay;

//
//请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
//这里签名时，只需要使用生成的RSA私钥。
//Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088411796495644";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "18611171841@qq.com";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM96PuTcLcFcPJc+v32CLbfUp67aDv08yW43cYeTOm//dlw4KlOh1PBVOmMrxWiDwcCXNdJdEmPKihnUpAygLn4OK2B/6+Ks3OLvafDTqv6NjkILqpDOUAV0jlmFTsb8++snWVwxumKEAe+MqRytxoh07hiODK/MWsK2R/B4ov+fAgMBAAECgYBDkvwjC/c3D/SICh7SIPEE+5T1pP3WytKMPDCUyIXy7lA9bumSPZYeBJQ0so/kH22ebWFRN5ECwXfDO/eGSTF6tG4b9Og+6hrIfhVJFqX7HmWEU24O7AX8Cq8AlGoeyAGjILZeHmybgmjmKo1PPkJ814lQ4QRkuunpPfj3eSM/MQJBAPYXdppbDlTJ6/SQEtLkZfp9/NUti/7y1s45Q5f/jXqw2dTIy9lyk+Aqr74p987XhHfHBSACnnx1RnMZ3R8NB2cCQQDX1MaKNZLR1cqCgvLcRFN2tyaCoU6FTjzUeHmirYnpVfrnD8GdzDpBxw0a3Xyk3VkYE3YuCf+qgtyJANfNHzsJAkBC7tTKXNPECfuMVBdaltaJz/SRgsbd++yiwH56+/3eDfXhYw/Wv8wqn1GXdlC5SHL2JQJm12+FyFv1+GXlCSUnAkBdp3C3jGO3NgP3+gaUWfm1q+fEC2bdJYPz1otBcfhEDhEfrpCffn1RCaTAIZfymjIM5nUDqUcNoFc+A+WgloqxAkEAs9u7BHzA8W4/08KJJLrwimpqXWlXANxHZspcSOmeGrEIzMxmVtgSwNjY1BliV1fvXZg/KCBLXZZIkt58wnCWYg==";

	//支付宝公钥
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}