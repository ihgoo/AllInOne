###All In One 
--------------
* aio(all in one简称) 包含 ioc 框架 、以及一些常用的工具类
---------------
简介
=======

* ViewUtils 控制反转,也就是ioc框架，用反射注解方便的 替代findviewbyid，setContentView等常用方法，有效减少代码量。

* BitmapUtils 对图片缩放进行了处理，加载本地图片不会OOM异常了

* RequestUtils 初始化请求、获得一个请求队列、添加一个请求队列等方法



###图像处理
=======

* GuideImage 在activity表面添加了一层图层，作为引导图层用，可添加透明图层


###网络请求
=======

* RequestManager 内含初始化Volley方法以及获取请求队列实例

* GsonRequest 请求网络成功后，会解析json，用gson反射方法赋值到bean对象中


###其他工具
=======

* LogUtils 自定义log日志，省略key

* ScreenUtil 和屏幕大小有关工具类

* SharePreferenceUtil 对SharePreference封装简单get&put方法

* Aipay 加密拼接字符串类
	* AipayKeys 这里保存了所能用到的常量，如 合作身份者id、收款支付宝账号、私钥等等
	* AipayOrder 订单类，这里有 getNewOrderInfo 方法获取拼接的订单详情、以及加密后的订单详情等

License
=======

	Copyright 2014 Kelvin ihgoo2008@gmail.com
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.