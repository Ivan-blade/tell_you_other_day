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
        + 通过监测目前的响应日期，响应一个模板页面，向模板中传递当前选中日期，点击该模板可以进入编辑页面，该页面存在两个tab，一个是日记模块，一个是秘密板块，还存在一个用户视图切换功能，如果匹配了其他id的用户可以切换到该用户的日记模块，当然秘密模块要达到一定的条件才会开放浏览权限
        + 后期的处理
            + 文章存储进数据库中分为两张表，一张表用于记录文章大体信息，id，编写者，创建日期，文章类别（日记还是秘密）等等，另一张表用于保存文章的id和具体内容（两个id应该会有所区别，可能需要第三个参数来连接两张数据表）
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
    + 由于方向做错，做其他事情平复了一下心情，模块化，必须上的，但是目前不熟悉，只保留框架，实际所有的依赖都会填充到一个模块里，后期修改，springsecurity也是必须上的，主要是userdetail的授权接口比较麻烦，暂且模仿github上的一个博客项目先做吧
+ 2020-2-28
    + 昨天给字节跳动投了前端的实习简历，唉，自己太菜了，能过就好了，半学期时间，我觉得足够达到他们前端的要求了，好像是该找实习了，毕竟暑期实习不能暑假找，先把这个项目写完吧

+ 2020-2-29
    + 将github上的vblog项目后端先整体迁移到了这个项目的后端，今天想一下修改方式吧，根据功能模块梳理一下那个项目的工作流程

+ 2020-3-1
    + 重新设计了数据库
    + 当后端采用模块化开发时，好像vscode就找不到主函数在哪了，在使用终端运行时应该是默认会寻找最外层的主函数，但是主函数在内层就没办法找到了，直接上idea跑了，话说模块化工程怎么在终端启动呢？总不能在服务器上下载个idea吧。。。使用java运行分模块的的主函数文件？？？（我错了，应该可以切换进分模块的目录跑项目的）
    + 注册登录功能算是完成了，不过用户信息需要加上性别，后面再说，今天引入mavon编辑器的时候发现它的优先级非常大，覆盖住了navbar，改z-index好像没有，这样只能给它单独一个页面了，后来发现通过增加新的路由方式，不行，因为路由渲染和bar是同一级别的，还是会覆盖住navbar，在一个教学视频上看见了v-dialog组件，应该可以实现这个需求

+ 2020-3-2
    + 今天要完成日记的修改功能，按预想使用tab将文章分为两页，一页是日记，一页是秘密
    + 解锁dialog姿势，挺好，算是解决了需要给mavon一个独立页面的问题，如果是通过增加页面的方式显然不合适，没法简单的覆盖了navbar
    + 因为一些私事情绪有点波动，目前简单的保存日记是可以做到了，不过因为后端是迁移的，和设计思路有一些矛盾的地方，明天开始着手调整后端，目前的问题是在时间上，前端的日期组件提供的时间格式是yyyy-mm-dd但是不清楚具体类型，前端测试返回是string类型，这样和后端的datetime类型能不能做比较呢？因为精度不一样，应该不能作比较，于是人工在数据库里插入了一个yyyy-mm-dd格式的数据，数据库显示的时候，多余精度位置全0，比较好的结果是对publishDate做格式化处理为yyyy-mm-dd然后填入数据库，后面直接从数据库提取该字段用于和前端datepicker获取的参数比对，不太好的情况，就是数据库中的publishdate参数的类型需要变更
    + 对于结果导向的人来说打着什么旗号并不重要，结果才是我在意的东西，希望那些人心里有点数，今天先到这里了

+ 2020-3-3
    + 今天换地方了，昨晚没睡好，脑子有点迷迷糊糊的，
    + 梳理一下思路
    + 不行有点很困，我要休息一会
    + 下午看了点mysql昨天的left jion算是搞明白是表连接了，现在的后端相当麻烦，很多东西看着不顺眼，又不能随便动。。。牵一发而动全身就很麻烦，目前苟且一波吧，需要重写文章获取接口，当选中日期进入编辑模式时，将会向后端发送get请求，同时传递日期，uid和state三个参数，检查是否存在数据，如果存在读取数据，如果不存在跳过。
    + 因为要传输多个参数所以使用parameterType时也要三个？？？必然不行啊，百度了一下这个博客写的不错：https://www.jianshu.com/p/d977eaadd1ed。本来打算直接传输一个article对象来着，不过因为接口想要设计成/{uid}/{date}/{state}的格式，但是对象的属性好像不能被识别没法传递？？？还是老老实实一个个来吧
    + 出了点问题，应该是前端的问题，毕竟后端没有返回报错
    + 打脸是前后端的问题，这么看来可能是前端传递的日期字段和接口中的timestamp字段格式不统一不能匹配
    + 复盘一下问题，前端可以返回的时间类型是yyyy-mm-dd后端java好像没有单纯的日期类型，只有timestamp类型精度到秒，这样前后端无法匹配，等等既然前端测试时间类型为string，那么我后端可不可以也使用string类型接收，最后在sql语言中使用like语句对数据库中的timestamp类型进行匹配？话说后端string类型可以和mysql中的timestamp类型匹配吗？？？
    + 百度了一下好像可以？？？
    + 预想失败，甚至连错误类型都没变，我佛了
    + 前面的思路修改一下，本来是一次获取一个状态的文章的，但是点击编辑图标之后就不会再发送数据请求了，所以一个日期两个状态的文章需要在一次请求内完成，接口更改为/uid吧。。。
    + 哦豁。。。突然想起了前面少加了/article字段。。。刚刚加上去之后报错类型改了，看样子前面的解决方案是没有问题的，前端的string类型数据虽然符合日期的格式但是不能和后端的timestamp匹配，只能用string类型接收，不过后端的string类型可以和数据库中的timestamp匹配。。。虽然多了很多连带报错。。。总比没有进展强，这次还是前端的问题
+ 2020-3-4
    + 今天先处理一下昨天的bug然后修改思路比较方便
    + 昨天的一堆报错是因为对于一个参数的赋值，因为该参数与前端页面渲染的逻辑也有关系，现在的问题是接口请求的确没问题但是没有查询到数据，目前猜测原因是like匹配语法有点问题，修改一下试试
    + 完美，我可真是个小机灵鬼哈哈哈哈哈
    + 下面需要将后端原本获取单个对象改为获取一个list对象，同时包含一个用户同一天两个状态的文章？要不对state参数进行监测吧，这样就不需要修改后端了，前端点击tab切换页面，state状态发生改变时自动向后端发送接口请求
    + 完美，目前已经可以实现自动获取选中日期的文章数据了，今天的节奏飞起啊
    + 下面需要处理的问题是用户信息，在考虑是不是用vuex管理一下，目前是向security接口请求当前用户，但是security好像用户信息只能保存一晚左右，这样用户需要反复登录，可能会影响用户体验，应该有修改时长的方式后面再说，不过每个页面都需要用户信息还是使用vuex管理会比较好，不过使用vuex在关闭软件后应该也会注销用户状态吧，可能需要引入token机制了，这个是小问题，还有就是虽然目前可以完成新建日记和查询日记操作，当对已存在日记进行修改时还需要一些处理，后端不能使用insert而是使用update操作
    + 还有一个问题，mavon编辑器分为两个页面，md编辑页面，和html预览页面，之前我是隐藏了html页面，这么做的后果就是没法支持图片显示，后期需要调整，目前的调整思路是编辑时使用md内容，显示时使用html内容
    + problem
        + 好像改着改着出现了新的问题，当该日期没有记录，请求不到信息之后，前端的参数好像会被清空？？？无法找到这个参数了
        + .......原因找到了。。。。是因为在进行文章赋值时没有进行正确的过滤，当查询数据为空时数据结构也为空，本地默认的article含有的数据结构无法进行赋值会造成报错。。。所以当后端  数据为空时需要进行一次数据初始化，一方面防止前端数据结构被打乱，另一方面需要清除上一次请求的数据，否则切换tab时state发生改变如果请求不到数据将会显示上一次请求的数据，话说一开始怎么会没有问题的？？？可能是点击tab更新的函数刚刚才加上，产生了各种各样的问题。。。
    + 接口格式修改了/date/state uid在后端通过security内置的接口自动获取，前端就不需要多余的配置了。
    + 个人信息还不是很完善，基本上功能实现了一丢丢，作为普通的纯文本日记员工是够用了，下面需要开始秘密的功能了，明天开始吧
    + task
        + 优化一下登录注册页面，用tab分离一下？还是用两个页面比较直接
    + 登录ui优化完了，后端冗余的代码现在先搁着吧，等功能开发完成，再处理，久违的刷一次leetcode吧，生平第三道题，从初级算法开始

+ 2020-3-5
    + task
        + 后端用户数据表需要插入一个otherid属性，用来表示已经匹配的用户id，前端需要增加预览功能了，预览应该会用到mavon-editor的另一页htmlcontent之前隐藏掉了，所以输入mdcontent内容时，htmlcontent没有自动更新，现在的问题是怎么在隐藏掉mdcontent内容时获取到htmlcontent内容
        + 后端文章数据表需要插入一个showdate属性，用来确定秘密将会在什么时间展示给匹配用户
        + 预览功能完毕之后增加按钮用于切换用户视图，目前的接口逻辑是前端传输/date/state参数后端自动获取当前用户，搜索对应文章，用户切换之后是另外编写一个接口还是将这个接口编写的更通用一点呢？
            + 将接口编写的更加通用可能需要恢复之前的/uid/date/state接口（日。。。白给），前端初始化时向后端请求uid和otherid，当切换用户时，只要传输不同的id值传输过去就可以了，不过如果再传输id上不做区分后面秘密部分，如何判定showdate字段该对谁生效呢？通用接口可能不太行
            + 编写另外的接口格式/otherid/date/state逻辑和现在的类似，后端不对uid进行自动获取，而是根据传输的otherid对数据表中的uid字段进行数据查询，同时对当前日期和showdate字段设置的日期进行效验，如果当前日期在showdate日期之前查询借书，否则显示查询内容（这边日期比对没有了解过。。。但是功能应该有吧）
        + 匹配机制
            + 前端需要写一个匹配页面（。。。唉，虽然也就加个页面，一个表单一个按钮总感觉号麻烦），用户输入邮箱后向目标用户发送匹配申请，目标用户收到申请后点击确认双方数据表中otherid更改为对方uid，问题来了，匹配的具体实现？
            + 目前的考虑是新建一个专门的匹配数据表match，数据元素：id,userid,otherid,status
                ```
                    CREATE TABLE IF NOT EXISTS `article` (
                    `id` bigint(32) NOT NULL AUTO_INCREMENT,
                    `userid` bigint(32) DEFAULT NULL,
                    `useremail` varchar(64) DEFAULT NULL,
                    `otherid` bigint(32) DEFAULT NULL,
                    `otheremail` varchar(64) DEFAULT NULL,
                    `status` int(11) DEFAULT 0,
                    PRIMARY KEY (`id`)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
                ```
            + 显示
                + 匹配数据表status字段0,1,2,3分别表示默认，匹配发起，匹配通过，匹配失败
                + 匹配页面加载前对后端进行查询请求，请求对象为match数据表，如果匹配到othermail与当前用户的usermail相同，status为2，显示页面为匹配用户的部分信息，如果status ！= 2展示匹配发起页面，同时两种情况下右上角图标可以进去匹配申请页面查看是否自己或者其他用户发送过匹配申请，如果已经匹配的情况下点击了同意将会提示已经匹配，请先断开当前匹配
                + 匹配申请页面将会向后端发送请求，获取所有usermail/otheremail与该用户匹配的数据筛出status = 1的数据
                + 用户同意后，status变为2，向usermail和othermail两个字段对应的用户数据表中的otherid填入匹配用户的userid（为什么要填入userid不填入usermail呢？害，usermail绑定还没有查重，仔细想想问题只可能更多，但是让用户匹配id就感觉很诡异了，邮箱主要是想让用户发起匹配的范围更大，但是用户id很可能只有用户自己知道），拒绝，status变为3
                + 这边的邮箱还没有过滤重复的邮箱（啧，有点麻烦，如果有人认证时用了其他人的邮箱怎么办？我怎么判断他是他呢？有了，使用邮箱验证码，不过security好像只接受username和password验证，如果再username字段中填入邮箱也不是不可以只是有点不舒服，后端好像可以将username改成email，这样就差不多了，只不过邮箱验证还没有了解过，应该有对应的第三方接口。。。收费的。。。这个功能先预想好吧。。。实现就算了）
                + 经过以上考虑，邮箱验证就算了，反正也是给朋友和他女朋友用的。。。到时候用不用还难说。。。咳咳，用户规模不大的情况下应该也不会有人用其他人邮箱登录吧
                + 功能越写越复杂。。。本来以为已经到中期来着，看样子遥遥无期啊