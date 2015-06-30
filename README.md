##All In One 
--------------
AIO(all in one简称) 
---------------


### 1.1.1

- 新增大量工具类方法
- 去掉Volley源码以及注入框架源码（按需配置，注入框架可用butterknife或者AndroidAnnotations等）

### 1.1.0

- 去掉了冗余方法
- 去掉了jar包依赖，导入源码
- 导入更简单

### 1.1.0

- 初版，将原项目中使用到的工具整理到此项目中



简介
-------
此项目都是工作中整理的一些很常用的一些方法，在此开源出来是让更多的人开发更方便，更快捷。有很多借鉴别人的，在这里要感谢前辈无私奉献。也希望大家可以提出批评建议。



图像处理
--------
- GuideImage 在activity表面添加了一层图层，作为引导图层用，可添加透明图层

- BitmapUtils 对Bitmap二次处理，避免OOM异常出现
    - getBitmap(String imagePath, int width, int height) 
	- getBitmap(Resources res, InputStream is, int width,int height)等方法
	- getBitmapByViewSize 根据View大小获取自适应的bitmap对象
	
- Bitmap缓存
	- FIFOCache 置换算法
	- LRUCache SDK自带推荐使用


网络请求
-------

- 推荐使用[`VolleyPlus`](https://github.com/DWorkS/VolleyPlus)，如接口是REST接口推荐使用[`retrofit`](https://github.com/square/retrofit)这个框架。重点简单说下retrofit优点：
	- 采用动态代理机制和反射，使代码写起来特别简单，看起来也很清晰。
	- 可以配合OkHttp拦截器，很方便的在header加token或者处理cookie等等。
	- 可配合RxJava，可以让多个接口调用组合变的更简单。


缓存
-------
- ACache 可以缓存网络请求数据，比如oschina的android客户端可以缓存http请求的新闻内容，缓存时间假设为1个小时，超时后自动失效，让客户端重新请求新的数据，减少客户端流量，同时减少服务器并发量。


DB
-------
- 推荐使用GreenDao，优点：
    - 使用java工程生成JavaObject，熟练后非常方便
    - 数据库操作便捷，开发效率高
    - 内存消耗最少，性能最佳
- 使用教程 参考个人博客 [`GreenDao学习`](http://xunhou.me/greendao/)
    

其他工具类
-------
- AppUtil 获取关于app的一些参数，如 版本号版本名称

- Check 检查类，检查sd卡是否存在等。

- EncodeUtil(EncryptUtil) 加密解密

- NetUtil 网络判断，检查使用何种方式联网，以及网络是否可用(连接)

- LogUtils 自定义log日志，省略参数key，可定位到哪个类第几行

- ScreenUtil 和屏幕大小有关工具类

- Base64、Md5

- BitmapTool 关于图片的工具类

- EncryptUtils 加密工具类 des加密

- LogUtils 省略了传入tag的log(建议使用Log4j)

- SharePreferenceUtil 对SharePreference封装简单get&put方法

- PhoneUtils 获取手机信息的工具类，诸如获取手机型号获取手机sd卡大小等

- StringUtils 对字符串处理的工具类

- TimeUtil 时间格式化/计算时间差值

- TrafficUtil 流量统计

- ViewUtil 处理View的一些方法


License
=======

	MIT License