# tell_you_other_day
+ 目前项目还处于开发期，依赖都在仓库中体积较大可能下载不便，增加了最新进度的demo演示视频
+ 功能板块
    + 日记
    + 秘密（可以向特定的用户id展示秘密，该内容将会根据用户设置时间显示给对方，如果不设置时间将会在两周内随机展示）

+ 页面板块
    + 导航栏
        + 用户头像，id，用户名，性别格式，格言，用户角色（vvvvip?）
        + 个人信息
        + 日记板块
        + 秘密板块
        + 设置板块
        + 关于
    + 个人信息
        + 静态
            + id
            + 性别
        + 动态
            + 头像（目前还没有）
            + 昵称
            + 格言
            + 用户角色

    + 日记板块
        + 日历视图并在有日记记录的日期上打上标记，点击日期进入编辑模块进行增改操作，同时在编辑模块存在正常编辑和秘密编辑两个页面，前者将会直接展示，后者将会根据用户设置时间展示给对方或者系统随机展示并于展示时在秘密板块给出消息提醒
        + 如果是已经匹配的状态，日记板块同时保存两个匹配id的日记内容

    + 秘密板块
        + 用于接收对方的秘密，并在新消息出现时给出红点标记

    + 设置板块
        + 应用风格切换（app-bar的背景切换？？？）
        + 到时候再看吧
    
    + 关于板块
        ```
            产品功能简述：
            1.日记板块：点击日历中的日期再点击那只笔跳转记录页，记录页分为两部分，日记和悄悄话，日记将无条件对匹配双方呈现
            
            2.悄悄话板块：同样点击日历的日期跳转记录页，这部分内容保存后除了自己无条件可视，匹配的对象满足一定条件才能看见
            
            3.匹配板块：对应页面输入对方邮箱（对方需要以该邮箱注册才行）发起匹配请求，对方同意后双方匹配成功
            
            4.匹配作用：匹配成功之后，用户记录的悄悄话将会与匹配对象进行绑定，如果之后更换了匹配用户，更换的用户是看不见上一次匹配期间的悄悄话的
            
            5.关于解绑：我刚改主意了，不允许解绑，之前的a2u（article to user）白写了，问题不大，匹配需谨慎，只有一次机会(并且已经匹配成功的用户再次发送匹配请求会被拒绝，当然bug还是有的，不过一般人应该不会碰到这个bug）
            如果有什么点子可以联系我，虽然即使联系我用处可能也不是那么大
            
            QQ:1178594290
            微信：L1178594290
        ```

    + programme-diary.md文件中放着个人开发过程的记录，虽然好像看起来没什么用。。。啧，问题不大