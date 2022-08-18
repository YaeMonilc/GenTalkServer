# GenTalkServer

**如果遇到了乱码可以添加 ```"-Dfile.encoding=utf8"``` 解决这个问题。**
***
**目前实现的功能：**  
1.账号系统：
 - 登录
 - 注册
 - 令牌

2.基础的聊天功能：
 - 文本

3.屏蔽词
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