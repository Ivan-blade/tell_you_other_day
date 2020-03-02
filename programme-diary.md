+ 2020-2-16
    + 创建vue3.x项目
        + vue create tell-you-other-day
    + 引入vuetify
        + vue add vuetify(如果无法启动npm install )
        + 允许window下开发
            + 在eslintrc中的rule下增加"linebreak-style": [0 ,"error", "windows"],
    + 单位转换移动端适应rem单位（这个先不管，好像vuetify是自适应组件库，初步看来不需要单位转换）
        + npm install lib-flexible
        + main.js中引入
        + 根目录下创建postcss.config.js进行px转rem的基准配置通常为75

+ 2020-2-17
    + 引入google图标库（内网访问不了谷歌，需要下载到本地库--注意不是google官方的，不一定会及时更新）
        + npm install material-design-icons-iconfont -D
        + main.js中import 'material-design-icons-iconfont/dist/material-design-icons.css'
    + 引入bar组件
        + 创建相关组件--Navbar
        + 在app中引入
    + 开始思考具体结构和功能实现

+ 2020-2-18
    + 开始编写各个功能模块以及对应前端路由

+ 2020-2-19
    + 看date pickers的文档
    + 文档中是说当day/mouth/year发生改变时都会发生change事件，可是只说了监测pickerDate参数可以了解月和年的变化，但是怎么监测天的变化呢？机智的我直接监测了v-model绑定获取当前日期的参数用于检测天的变化
    + 下面的思路
        + 本来的思路是点击日期直接相应编辑事件，但是添加click事件之后打印日志都做不到，一开始以为是没有找到该组件下点击事件的正确姿势，后来想想，这个组件默认点击之后就会响应一个事件，所以应该不会允许自定义点击事件，于是只能通过检测变化，响应其他页面，再进行点击事件的设置
        + 通过监测目前的响应日期，响应一个模板页面，向模板中传递当前选中日期，点击该模板可以进入编辑页面，该页面存在两个tab，一个是日记模块，一个是悄悄话板块，还存在一个用户视图切换功能，如果匹配了其他id的用户可以切换到该用户的日记模块，当然悄悄话模块要达到一定的条件才会开放浏览权限
        + 后期的处理
            + 文章存储进数据库中分为两张表，一张表用于记录文章大体信息，id，编写者，创建日期，文章类别（日记还是悄悄话）等等，另一张表用于保存文章的id和具体内容（两个id应该会有所区别，可能需要第三个参数来连接两张数据表）
            + 在打开软件时，向数据库查询该用户所有文章的创建日期字段，形成一个数组，传入datepickers组件，标记好存在编辑行为的日期，当点击某个日期时，响应一个组件，点击该组件，前端将会根据目前的日期去数据库中查询该用户对应时间的文章，存在则渲染，不存在则返回空

    + 看一下github上面一个博客项目，了解一下文档的输入和存储吧

+ 2020-2-20
    + 看样子文章编辑会用到mavon-editor插件，不过这是通常在博客中使用的markdown编辑器，在移动端效果会怎么样就不得而知了，官方好像没有移动端的方案，在看看quil吧???
    + 还没看quil，直接打算用mavon了，官方网址：https://github.com/hinesboy/mavonEditor，工具栏和预览关闭看起来舒服多了，看一下预览功能？？？
    + 发现个bug，明明是引入的组件但是在打开navbar时mavon会在navbar上层，调整z-index无效，这个用的时候可能需要嵌入到编辑页面中，，一般编辑页面处于关闭状态，打开之后编辑完成之前也不会打开navbar，不过为什么会在navbar上层呢？？？
    + 效率比较低，麻烦

+ 2020-2-21
    + 装好了springboot的环境，core中以后会引入security，下一阶段主要在web中进行开发
    + 数据库构建
        + user
            ```
                CREATE TABLE IF NOT EXISTS `user` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `username` varchar(255) DEFAULT NULL,
                `password` varchar(255) DEFAULT NULL,
                `userphone` bigint(32) DEFAULT NULL,
                `userface` varchar(255) DEFAULT NULL,
                `email` varchar(64) DEFAULT NULL,
                `enabled` tinyint(1) NOT NULL DEFAULT 1,
                `regTime` datetime DEFAULT NOW(),
                PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
            ```
        + roles
            ```
                CREATE TABLE IF NOT EXISTS `roles` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `name` varchar(32) DEFAULT NULL,
                PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
            ```
        + roles_user
            ```
                CREATE TABLE IF NOT EXISTS `roles_user` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `rid` int(11) DEFAULT 2,
                `uid` int(11) DEFAULT NULL,
                PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
                
                
                
                alter table roles_user add index(rid);
                alter table roles_user add index(uid);
            ```
        + category
            ```
                CREATE TABLE IF NOT EXISTS `category` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `cateName` varchar(255) DEFAULT NULL,
                `date` date DEFAULT NULL,
                `uid` bigint(32) DEFAULT NULL,
                PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
            ```
        + article
            ```
                CREATE TABLE IF NOT EXISTS `article` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `title` varchar(255) DEFAULT NULL,
                `mdContent` text DEFAULT NULL,
                `htmlContent` text DEFAULT NULL,
                `summary` text DEFAULT NULL,
                `uid` int(11) DEFAULT NULL,
                `publishDate` datetime DEFAULT NULL,
                `editTime` datetime DEFAULT NULL,
                `state` int(11) DEFAULT NULL,
                `pageView` int(11) DEFAULT 0,
                PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
            ```
        + 这边看了一下id，主要是感觉单纯的自增id有点简陋，百度发现了uuid的概念
            + 自增id
                + 优点
                    + 性能较高
                    + 占用空间小
                + 缺点
                    + 数据量达到一定程度将会超出自增长取值范围
                    + 由于不同表之间的id都是从1开始大量重复结果就是不同的表数据合并比较麻烦，所以不适合分布式场景，尤其是合并表的场景
                    + 由于是自增的所以可能被猜出业务量，缺乏安全性
            + uuid
                + 优点
                    + uuid的生成随MAC地址、时间戳、名字空间（Namespace）、随机或伪随机数、时序等元素的变化而变化，假设时间戳的最小精度为秒，随机数为个位，也需要在一秒内出现多个注册用户才会重复，重复概率极低，每个uuid都可能是世界唯一的，所以在合并表时比较简单，适合分布式场景
                    + UUID是16字节128位长的数字，通常以36字节的字符串表示，所以可表示范围也较大，比起自增id不容易溢出
                + 缺点
                    + 性能不那么高
                    + 相对占用存储空间较多
                    + 无序（虽然根据时间生成但是结果并不会存在序列关系），不利于查询
            + snowflake处于两者之间的方法，两者优缺点的平衡吧
                + 优点
                    + 毫秒数在高位，自增序列在低位，整个ID都是趋势递增的。
                    + 不依赖数据库等第三方系统，以服务的方式部署，稳定性更高，生成ID的性能也是非常高的。（等等，这是不是意味着要单独部署这个服务，有点麻烦）
                    + 可以根据自身业务特性分配bit位，非常灵活
                + 缺点（新的缺点已经出现？？？）
                    + 十分依赖机器时钟，如果时钟出问题，id也会出问题，比如服务器重启，机器时钟没有及时更新（多半是在固定的某一年）
            + 综上还是老实自增id吧，写个日记还用不上分布式（主要是现在还不会）

        + 写一下登录页面和登录逻辑
        
+ 2020-2-23
    + 昨天到今天打算采用模块化开发+security一气呵成来着，结果出现了各种各样的问题，不应该操之过急的，在authority这一块还不是很清楚，可能之后需要回滚了

+ 2020-2-27
    + 由于方向做错，做其他事情平复了一下心情，模块化，必须上的，但是目前不熟悉，只保留框架，实际所有的依赖都会填充到一个模块里，后期修改，springsecurity也是必须上的，主要是userdetail的授权接口比较麻烦，暂且模仿github上的项目先做吧
+ 2020-2-28
    + 昨天给字节跳动投了前端的实习简历，唉，自己太菜了，能过就好了，半学期时间，我觉得足够达到他们前端的要求了，好像是该找实习了，毕竟暑期实习不能暑假找，先把这个项目写完吧

+ 2020-2-29
    + 将github上的vblog后端先整体迁移到了这个项目的后端，今天想一下修改方式吧，根据功能模块梳理一下那个项目的工作流程

+ 2020-3-1
    + 重新设计了数据库
    + 当后端采用模块化开发时，好像vscode就找不到主函数在哪了，在使用终端运行时应该是默认会寻找最外层的主函数，但是主函数在内层就没办法找到了，直接上idea跑了，话说模块化工程怎么在终端启动呢？总不能在服务器上下载个idea吧。。。使用java运行分模块的的主函数文件？？？
    + 注册登录功能算是完成了，不过用户信息需要加上性别，后面再说，今天引入mavon编辑器的时候发现它的优先级非常大，覆盖住了navbar，改z-index好像没有，这样只能给它单独一个页面了，后来发现通过增加新的路由方式，不行，因为路由渲染和bar是同一级别的，还是会覆盖住navbar，在一个教学视频上看见了v-dialog组件，应该可以实现这个需求

+ 2020-3-2
    + 今天要完成日记的修改功能，按预想使用tab将文章分为两页，一页是日记，一页是秘密
    + 解锁dialog姿势，挺好，算是解决了需要给mavon一个独立页面的问题，如果是通过增加页面的方式显然不合适，没法简单的覆盖了navbar
    + 因为一些私事情绪有点波动，目前简单的保存日记是可以做到了，不过因为后端是迁移的，和设计思路有一些矛盾的地方，明天开始着手调整后端，目前的问题是在时间上，前端的日期组件提供的时间格式是yyyy-mm-dd但是不清楚具体类型，前端测试返回是string类型，这样和后端的datetime类型能不能做比较呢？因为精度不一样，应该不能作比较，于是人工插入了一个yyyy-mm-dd格式的数据，数据库显示的时候，多余精度位置全0，比较好的结果是对publishDate做格式化处理为yyyy-mm-dd然后填入数据库，后面直接从数据库提取该字段用于和前端datepicker获取的参数比对，不太好的情况，就是数据库中的publishdate参数的类型需要变更
    + 对于结果导向的人来说打着什么旗号并不重要，结果才是我在意的东西，希望那些人心里有点数，今天先到这里了