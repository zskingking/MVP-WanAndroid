
[简书地址](https://www.jianshu.com/p/60eefed7870f)
### 1.背景
其实在我入行的时候就一直想做一款自己平时能把玩的App，之前也想过要不要做一个本地的，但是局限性太大激不起来自己的动力，联网的自己又不会写后端所以就一直拖着。年前Github上瞎逛发现了一个好玩的App  `WanAndroid`，仔细一瞅提供的还有 `API`，再仔细一瞅原来是`鸿洋`大大发起的甚是激动，拍拍我那满是灰尘的小目标，历时半个月(也可能是20天)有了现在的第一版`WanAndroid`。


主色调目前为红色，后续的版本会加入切换主题功能。

`文章底部有APK二维码以及github地址`



### 2.效果图
整个App基于Material Design风格，超级简洁的界面极具沉浸式的体验。
![](http://upload-images.jianshu.io/upload_images/10073662-32bab01573486f63.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50)



### 3.功能
首页包含五个Tab
>* 首页: 可预览最新最热的技术文章，对喜欢的文章可做收藏操作。
>* 项目: 包含完整原生项目、跨平台项目，也有动画、网络等Demo。
>* 广场: 目前包含两个模块，分别是体系、导航，可快速定位知识点。
>* 公众号: 可实时查看Android领域知名度较高的博主公众号。
>* 我的: 可查看排明、收藏、我的文章以及发布文章。

具体功能可下载Apk或者clone项目自我体验，对于一个技术人来说我觉得还是挺实用的。

### 4应用技术
#### 4.1语言
整个App全部基于Kotlin开发

#### 4.2 架构模式 MVP
##### Model
作者网络请求使用的是Retrofit+RxJava,再此基础下作者又封装了HttpDefaultObserver,
可根据服务器返回数据的结构统一脱壳、解析以及错误判断，可作为所有界面的Model层,所以网络请求方面没有单独去写一个具体的Model出来。

##### View
定义了两个基类BaseActivity、BaseFragment以及LazyFragment(继承自BaseFragment),统一处理跳转逻辑
以及通过Lifecycle与Presenter进行绑定,并且施加统一沉浸式效果。

##### Presenter
所有Presenter都继承自BasePresenter,生命周期与View层同步,在基类中统一管理RxJava生命周期。

其实最初是想直接用jetpack开发，但作者还是想按照自己的想法搭建一套MVP吗，但是jetpack版以及Flutter版`WanAndroid`后续都会逐个推出，敬请期待。

#### 4.3用到的第三方库如下

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

### 5.总结
麻雀虽小，五脏俱全，虽然项目很简单，但内部代码以及整个架构我觉得还是具备参考价值的，附上[github](https://github.com/zskingking/WanAndroid-ZS)，让我们共同进步。

也可直接扫码下载apk。apk目前托管在码云上，所以扫描二维码可能需要登录。
![apk二维码](https://upload-images.jianshu.io/upload_images/10073662-b6953eee3d1f2199.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

最后再次感谢`鸿洋`大大提供的API，以及阿里巴巴矢量图&花瓣网提供的图标和UI设计。