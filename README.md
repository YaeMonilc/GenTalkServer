# GenTalkServer(**<font color="#dd0000">该项目命名混乱，请注意踩坑</font>**)

![GentalkServer](https://socialify.git.ci/YaeMonilc/GenTalkServer/image?description=1&font=Source%20Code%20Pro&forks=1&issues=1&language=1&owner=1&pattern=Floating%20Cogs&pulls=1&stargazers=1&theme=Light)

***

**如果遇到了乱码可以添加 ```"-Dfile.encoding=utf8"``` 解决这个问题。**

***

**目前实现的功能：**  

1.账号系统：
 - 登录
 - 注册
 - 令牌

2.基础的聊天功能：
 - 文本 & HTML

3.屏蔽词

***

**配置：**

>Config.java
> >Host ：域名 （保持原样即可）  
> >port ：端口 （酌情更改）  
> >databaseUri ：Mongodb连接地址 （务必更改）  
> >databaseName ：数据库名称 （可以不改）  
> >expirationTime ：令牌过期时间 （时间戳）  

***

**屏蔽词：** *目前为简易实现*  

如果你想添加屏蔽词：  
请在Main类中```reset();```下方加入  
```java
Util.SensitiveWordCheck.sensitiveWordList.add(new SensitiveWord("被屏蔽词", "替换词"));
```

***

**关闭服务端：**  

使用 <kbd>Ctrl</kbd>+<kbd>C</kbd> 关闭

***

**客户端：** *使用Vue编写（仅供参考）*

点此链接跳转：[gentalk-vue](https://github.com/YaeMonilc/gentalk-vue/)