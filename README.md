# zs-wan-android

### 非常感谢鸿洋大神提供的api，让我实现了为时已久的小目标。同时也感谢阿里巴巴矢量图&花瓣网提供的图标和界面设计。如果觉得有帮助到你，请给个star。App目前是第一版，所以存在很多问题，欢迎issues，事先谢过。附上github：[https://github.com/zskingking/zs-wan-android](https://github.com/zskingking/zs-wan-android)

## 效果图如下
![](http://upload-images.jianshu.io/upload_images/10073662-090c60f1edadaf32.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50)
![](http://upload-images.jianshu.io/upload_images/10073662-c9e7d9bfc4669eda.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50)

![](http://upload-images.jianshu.io/upload_images/10073662-dde68caf87b00d54.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50)

![](http://upload-images.jianshu.io/upload_images/10073662-1031a7be06be6e16.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50)
## 项目介绍
wan android是基于鸿洋大神提供的api进行开的一款Android社区app，内部共包含五个模块：首页、项目、广场、公众号、我的

## 首页
首页主要包含文章列表和banner位，可以快速对最新最热的文章进行预览

## 项目
可快速预览完整项目、跨平台项目以及一些自定义组件的文章和对应的源码


## 广场
>* 体系:根据知识点划分文章体系
>* 导航:根据知识点类别快速定位


## 公众号
包含Android领域知名公众号博主


### 我的
我的中有八个小模块：
>* 足迹:保存浏览历史，因为没有相关api，所以采用本地数据库保存
>* 排名:查看积分排名...
>* 积分:查看积分增长记录...
>* 收藏:查看收藏的文章...
>* 我的文章:自己分享的文章...
>* 关于网站:WanAndroid官网...
>* 轻松一下:内含高质量小姐姐照片...
>* 设置:可清除缓存&退出登录...


## 应用技术
### 语言
整个App全部基于Kotlin开发

### 架构模式 MVP
#### Model
作者网络请求使用的是Retrofit+RxJava,再此基础下作者又封装了HttpDefaultObserver,
可根据服务器返回数据的结构统一脱壳、解析以及错误判断，可作为所有界面的Model层,所以网络请求方面没有单独去写一个具体的Model出来。

#### View
定义了两个基类BaseActivity、BaseFragment以及LazyFragment(继承自BaseFragment),统一处理跳转逻辑
以及通过Lifecycle与Presenter进行绑定,并且施加统一沉浸式效果。

#### Presenter
所有Presenter都继承自BasePresenter,生命周期与View层同步,在基类中统一管理RxJava生命周期。

### 用到的第三方库如下

>* rxjava
>* retrofit(对网络层做了二次封装，将服务端数据脱壳，统一处理错误信息)
>* glide
>* SmartRefreshLayout
>* eventbus
>* avi:library
>* BaseRecyclerViewAdapterHelper
>* greendao
>* MagicIndicator
>* easypermissions
>* LabelsView

### 版本
当前版本为v0.0.1,后续会对性能进行优化，功能拓展。


