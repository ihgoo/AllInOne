##All In One 
--------------
* AIO(all in one简称) 包含 ioc 框架 、以及一些常用的工具类 *
---------------

简介
-------
###几个简单util类
* ViewUtils 控制反转,也就是IoC(Inversion of Control)框架&依赖注入(DI)，用反射注解方便的 替代findviewbyid，setContentView等常用方法，有效减少代码量。

* BitmapUtils 对图片缩放进行了处理，加载本地图片不会OOM异常了

* RequestUtils 初始化网络请求、获得一个请求队列、添加一个请求队列等方法


控制反转
--------
* ViewUtils 控制反转&依赖注入，快速开发，极少代码来findviewbyid、setonclick等等


图像处理
--------
* GuideImage 在activity表面添加了一层图层，作为引导图层用，可添加透明图层

* BitmapUtils 对Bitmap二次处理，避免OOM异常出现
	* getBitmap(String imagePath, int width, int height) 
	* getBitmap(Resources res, InputStream is, int width,int height)等方法
	* getBitmapByViewSize 根据View大小获取自适应的bitmap对象
	
* Bitmap缓存
	* FifoCache
	* LruCache


网络请求
-------
* RequestManager 内含初始化Volley方法以及获取请求队列实例

* GsonRequest 请求网络成功后，会解析json，用gson反射方法赋值到bean对象中

* RequestUtils 初始化请求、获得一个请求队列、添加一个请求队列等方法


缓存
-------
* ACache 可以缓存网络请求数据，比如oschina的android客户端可以缓存http请求的新闻内容，缓存时间假设为1个小时，超时后自动失效，让客户端重新请求新的数据，减少客户端流量，同时减少服务器并发量。


DB
-------
- 推荐使用GreenDao，优点：
    - 使用java工程生成JavaObject，熟练后非常方便
    - 数据库操作便捷，开发效率高
    - 内存消耗最少，性能最佳
- 使用教程 参考个人博客 [`GreenDao学习`](http://xunhou.me/greendao/)
    

其他工具类
-------
* LogUtils 自定义log日志，省略参数key，可定位到哪个类第几行

* ScreenUtil 和屏幕大小有关工具类

* Base64、Md5

* BitmapTool 关于图片的工具类

* EncryptUtils 加密工具类 des加密

* LogUtils 省略了传入tag的log

* StringUtils 对字符串处理的工具类

* SharePreferenceUtil 对SharePreference封装简单get&put方法

* PhoneUtils 获取手机信息的工具类，诸如获取手机型号获取手机sd卡大小等


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
