#### 2020-2-16
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

#### 2020-2-17
    + 引入google图标库（内网访问不了谷歌，需要下载到本地库--注意不是google官方的，不一定会及时更新）
        + npm install material-design-icons-iconfont -D
        + main.js中import 'material-design-icons-iconfont/dist/material-design-icons.css'
        + demo网址:https://jossef.github.io/material-design-icons-iconfont/
    + 引入bar组件
        + 创建相关组件--Navbar
        + 在app中引入
    + 开始思考具体结构和功能实现

#### 2020-2-18
    + 开始编写各个功能模块以及对应前端路由

#### 2020-2-19
    + 看date pickers的文档
    + 文档中是说当day/mouth/year发生改变时都会发生change事件，可是只说了监测pickerDate参数可以了解月和年的变化，但是怎么监测天的变化呢？机智的我直接监测了v-model绑定获取当前日期的参数用于检测天的变化
    + 下面的思路
        + 本来的思路是点击日期直接相应编辑事件，但是添加click事件之后打印日志都做不到，一开始以为是没有找到该组件下点击事件的正确姿势，后来想想，这个组件默认点击之后就会响应一个事件，所以应该不会允许自定义点击事件，于是只能通过检测变化，响应其他页面，再进行点击事件的设置
        + 通过监测目前的响应日期，响应一个模板页面，向模板中传递当前选中日期，点击该模板可以进入编辑页面，该页面存在两个tab，一个是日记模块，一个是秘密板块，还存在一个用户视图切换功能，如果匹配了其他id的用户可以切换到该用户的日记模块，当然秘密模块要达到一定的条件才会开放浏览权限
        + 后期的处理
            + 文章存储进数据库中分为两张表，一张表用于记录文章大体信息，id，编写者，创建日期，文章类别（日记还是秘密）等等，另一张表用于保存文章的id和具体内容（两个id应该会有所区别，可能需要第三个参数来连接两张数据表）
            + 在打开软件时，向数据库查询该用户所有文章的创建日期字段，形成一个数组，传入datepickers组件，标记好存在编辑行为的日期，当点击某个日期时，响应一个组件，点击该组件，前端将会根据目前的日期去数据库中查询该用户对应时间的文章，存在则渲染，不存在则返回空

    + 看一下github上面一个博客项目，了解一下文档的输入和存储吧

#### 2020-2-20
    + 看样子文章编辑会用到mavon-editor插件，不过这是通常在博客中使用的markdown编辑器，在移动端效果会怎么样就不得而知了，官方好像没有移动端的方案，在看看quil吧???
    + 还没看quil，直接打算用mavon了，官方网址：https://github.com/hinesboy/mavonEditor，工具栏和预览关闭看起来舒服多了，看一下预览功能？？？
    + 发现个bug，明明是引入的组件但是在打开navbar时mavon会在navbar上层，调整z-index无效，这个用的时候可能需要嵌入到编辑页面中，，一般编辑页面处于关闭状态，打开之后编辑完成之前也不会打开navbar，不过为什么会在navbar上层呢？？？
    + 效率比较低，麻烦

#### 2020-2-21
    + 装好了springboot的环境，core中以后会引入security，下一阶段主要在web中进行开发
    + 数据库构建
        + user
            ```
                CREATE TABLE IF NOT EXISTS `user` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `username` varchar(255) DEFAULT NULL,
                `gender` varchar(10) DEFAULT "保密",
                `password` varchar(255) DEFAULT NULL,
                `userphone` bigint(32) DEFAULT NULL,
                `userface` varchar(255) DEFAULT NULL,
                `email` varchar(64) DEFAULT NULL,
                `enabled` tinyint(1) NOT NULL DEFAULT 1,
                `regTime` datetime DEFAULT NOW(),
                `isMatch` tinyint(1) NOT NULL DEFAULT 0,
                `matchId` bigint(32) DEFAULT NULL,
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

                insert into roles set name = "超级管理员";
                insert into roles set name = "普通用户";
                insert into roles set name = "锁定用户";
                虽然现在还没什么用。。。
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
        + article
            ```
                CREATE TABLE IF NOT EXISTS `article` (
                `id` bigint(32) NOT NULL AUTO_INCREMENT,
                `title` varchar(255) DEFAULT NULL,
                `mdContent` text DEFAULT NULL,
                `summary` text DEFAULT NULL,
                `uid` int(11) DEFAULT NULL,
                `publishDate` date DEFAULT NULL,
                `editTime` datetime DEFAULT NULL,
                `state` int(11) DEFAULT NULL,
                `otherId` bigint(32) DEFAULT NULL,
                `showTime` datetime DEFAULT NULL,
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
        
#### 2020-2-23
    + 昨天到今天打算采用模块化开发+security一气呵成来着，结果出现了各种各样的问题，不应该操之过急的，在authority这一块还不是很清楚，可能之后需要回滚了

#### 2020-2-27
    + 由于方向做错，做其他事情平复了一下心情，模块化，必须上的，但是目前不熟悉，只保留框架，实际所有的依赖都会填充到一个模块里，后期修改，springsecurity也是必须上的，主要是userdetail的授权接口比较麻烦，暂且模仿github上的一个博客项目先做吧
#### 2020-2-28
    + 昨天给字节跳动投了前端的实习简历，唉，自己太菜了，能过就好了，半学期时间，我觉得足够达到他们前端的要求了，好像是该找实习了，毕竟暑期实习不能暑假找，先把这个项目写完吧

#### 2020-2-29
    + 将github上的vblog项目后端先整体迁移到了这个项目的后端，今天想一下修改方式吧，根据功能模块梳理一下那个项目的工作流程

#### 2020-3-1
    + 重新设计了数据库
    + 当后端采用模块化开发时，好像vscode就找不到主函数在哪了，在使用终端运行时应该是默认会寻找最外层的主函数，但是主函数在内层就没办法找到了，直接上idea跑了，话说模块化工程怎么在终端启动呢？总不能在服务器上下载个idea吧。。。使用java运行分模块的的主函数文件？？？（我错了，应该可以切换进分模块的目录跑项目的）
    + 注册登录功能算是完成了，不过用户信息需要加上性别，后面再说，今天引入mavon编辑器的时候发现它的优先级非常大，覆盖住了navbar，改z-index好像没有，这样只能给它单独一个页面了，后来发现通过增加新的路由方式，不行，因为路由渲染和bar是同一级别的，还是会覆盖住navbar，在一个教学视频上看见了v-dialog组件，应该可以实现这个需求

#### 2020-3-2
    + 今天要完成日记的修改功能，按预想使用tab将文章分为两页，一页是日记，一页是秘密
    + 解锁dialog姿势，挺好，算是解决了需要给mavon一个独立页面的问题，如果是通过增加页面的方式显然不合适，没法简单的覆盖了navbar
    + 因为一些私事情绪有点波动，目前简单的保存日记是可以做到了，不过因为后端是迁移的，和设计思路有一些矛盾的地方，明天开始着手调整后端，目前的问题是在时间上，前端的日期组件提供的时间格式是yyyy-mm-dd但是不清楚具体类型，前端测试返回是string类型，这样和后端的datetime类型能不能做比较呢？因为精度不一样，应该不能作比较，于是人工在数据库里插入了一个yyyy-mm-dd格式的数据，数据库显示的时候，多余精度位置全0，比较好的结果是对publishDate做格式化处理为yyyy-mm-dd然后填入数据库，后面直接从数据库提取该字段用于和前端datepicker获取的参数比对，不太好的情况，就是数据库中的publishdate参数的类型需要变更
    + 对于结果导向的人来说打着什么旗号并不重要，结果才是我在意的东西，希望那些人心里有点数，今天先到这里了

#### 2020-3-3
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
#### 2020-3-4
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

#### 2020-3-5
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
                    CREATE TABLE IF NOT EXISTS `match` (
                    `id` bigint(32) NOT NULL AUTO_INCREMENT,
                    `userId` bigint(32) DEFAULT NULL,
                    `userEmail` varchar(64) DEFAULT NULL,
                    `otherId` bigint(32) DEFAULT NULL,
                    `otherEmail` varchar(64) DEFAULT NULL,
                    `status` int(11) DEFAULT 0,
                    `matchTime` datetime DEFAULT NULL,
                    PRIMARY KEY (`id`)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
                ```
            + 显示
                + 匹配数据表status字段0,1,2,3分别表示默认，匹配发起，匹配通过，匹配失败
                + 匹配页面加载前对后端进行查询请求，请求对象为match数据表，如果匹配到othermail与当前用户的usermail相同，status为2，显示页面为匹配用户的部分信息，如果status ！= 2展示匹配发起页面，同时两种情况下右上角图标可以进去匹配申请页面查看是否自己或者其他用户发送过匹配申请，如果已经匹配的情况下点击了同意将会提示已经匹配，请先断开当前匹配
                + 匹配申请页面将会向后端发送请求，获取所有usermail/otheremail与该用户匹配的数据筛出status = 1的数据
                + 用户同意后，status变为2，向usermail和othermail两个字段对应的用户数据表中的otherid填入匹配用户的userid（为什么要填入userid不填入usermail呢？害，usermail绑定还没有查重，仔细想想问题只可能更多，但是让用户匹配id就感觉很诡异了，邮箱主要是想让用户发起匹配的范围更大，但是用户id很可能只有用户自己知道），拒绝，status变为3
                + 这边的邮箱还没有过滤重复的邮箱（啧，有点麻烦，如果有人认证时用了其他人的邮箱怎么办？我怎么判断他是他呢？有了，使用邮箱验证码，不过security好像只接受username和password验证，如果再username字段中填入邮箱也不是不可以只是有点不舒服，后端好像可以将username改成email，这样就差不多了，只不过邮箱验证还没有了解过，应该有对应的第三方接口。。。收费的。。。这个功能先预想好吧。。。实现就算了）
                + 经过以上考虑，邮箱验证就算了，反正也是给朋友和他女朋友用的。。。到时候用不用还难说。。。咳咳，用户规模不大的情况下应该也不会有人用其他人邮箱注册吧
                + 功能越写越复杂。。。本来以为已经到中期来着，看样子遥遥无期啊

#### 2020-3-6
    + 终于到周末了，学校的停课不停学是真的诡异，传闻因为网课进度不行要占用我们暑假时间，那线上教学的意义是什么呢？浪费我们时间？？？
    + 先解决预览问题（两种方案）
        + 找到mavon-editer的html内容获取方式，因为这部分内容展示的页面被隐藏了，能不能获取还不得而知，既然官方有提到可以在手机端使用，理论应该有解决方案的
        + 如果不幸没有，那就直接展示mdContent的内容吧
    + task(这个不急，闲下来再说)
        + 注册表单没有过滤，理论上导致null的情况下也能注册，而且可能会有sql注入，过滤函数有机会去那个渗透平台看一下（哦豁。。。平台名字给忘了，问一下朋友，估计他还在睡觉，前后端都做一个过滤比较好，虽然我觉得只做后端就可以了，前端过滤的话，网页是可以截包工具拦截之后修改表单数据再发给后端的，虚有其表。。。话说webapp可以截包吗？理论是可以的，所以传输所有内容应该是要加密的，QQ的数据包就是全部加密了，用什么加密算法不太清楚），叫dvwa，这居然能忘了。。。才过了一年多。。。真是

    + 问题解决结果
        + ？？？哦豁，双栏的情况下的确是mdcontent和htmlcontent（相当于md内容的预览）但是单栏的情况下只需要保存mdcontent就可以了，通过对defaultopen参数的修改，我们可以预览mdcontent也就是双栏情况下的htmlcontent了，后端和数据库中的html参数后面可以删除了
    + 下面开始写预览模块，我要想一下前端的呈现方式，在此之前，可能需要对存在记录的日期打上标记，vuetify刚好提供了标记功能
    + 可能需要对数据库中的字段进行重写，配合vuetify的datepicker，数据格式只能是yyyy-mm-dd格式，我看一下前端的参数，能不能避免后端修改
    + 前端可以通过截取字符将数据库中timestamp格式数据格式化为yyyy-mm-dd格式的数据，事件标记需要一个数据，写一个后端接口获取特定id所有日记的publishdate，使用类型list，前端获取数据之后使用遍历函数对list中每个日期进行格式化就可以。。。
    + problem
        + 出了一点问题，在执行查询时，后端一直返回结果为null，mapper中的sql语句在数据库中手动执行过了没有问题，不过后端控制台显示接收的参数为null？怎么会是null呢？
        + 思考了一下问题，mapper引入参数之后使用配置文件进行查询肯定没有问题，肯定是传入参数的过程中出现了问题，思考了一下原因，这边是用的get方式传参和之前的post方式理论上会有差异，刚刚才察觉到，现在验证一下
        + ...的确是传参的问题，基础还不是很牢固，这边补充一下getMapping的参数传入方式（如果我没有记错get参数获取几乎都是要在url上体现的，接口与参数用?隔开，多个参数之间&连接）
    + tips(后端获取前端参数的七种方式)
        +  直接将需要提交的参数写入方法形参（只适用于get传参，post传参由于安全性一般不在url显示。。。）
            ```
                后端
                @RequestMapping(value = "/allPublishDate",method = RequestMethod.GET)
                public List<String> getAllPublishDate(Long id) {
                    return articleService.getAllPublishDate(id);
                }
                前端相对应的参数参数格式/allPublishDate?id=this.id
                getAllPublishDate () {
                    var _this = this
                    getRequest(`/article/allPublishDate?id=${_this.userid}`).then(resp => {
                    if (resp.status == 200 && resp.data) {
                    _this.allPublishDate = resp.data;
                    console.log(resp)
                    console.log(_this.allPublishDate)
                    } else {
                        console.log('查询失败') 
                    }
                    })
                }
                感觉很诡异。。。
            ```
        + 通过HttpServletRequest接收，post方式和get方式都可以
            ```
                后端
                @RequestMapping(value = "/allPublishDate",method = RequestMethod.POST)
                public List<String> getAllPublishDate(HttpServletRequest request) {
                    Long id = request.getParameter("id")
                    return articleService.getAllPublishDate(id);
                }
                前端
                getAllPublishDate () {
                    var _this = this
                    postRequest('/article/allPublishDate',{
                        id: _this.userid
                    }).then(resp => {
                    if (resp.status == 200 && resp.data) {
                    _this.allPublishDate = resp.data;
                    console.log(resp)
                    console.log(_this.allPublishDate)
                    } else {
                        console.log('查询失败') 
                    }
                    })
                }
                哦豁？还有这种操作？没有试过
            ```
        + 通过一个bean来接收,post方式和get方式都可以
            ```
                后端(传入参数必须与bean中的属性一致)
                @RequestMapping(value = "/allPublishDate",method = RequestMethod.POST)
                public List<String> getAllPublishDate(Article article) {
                    return articleService.getAllPublishDate(uid);
                }
                前端
                getAllPublishDate () {
                    var _this = this
                    postRequest('/article/allPublishDate',{
                        uid: _this.userid
                    }).then(resp => {
                    if (resp.status == 200 && resp.data) {
                    _this.allPublishDate = resp.data;
                    console.log(resp)
                    console.log(_this.allPublishDate)
                    } else {
                        console.log('查询失败') 
                    }
                    })
                }
                当传输数据为一个bean的属性时可以用，主要后端可以减少形参的定义
            ```
        + 通过@PathVariable获取路径中的参数
            ```
                后端
                @RequestMapping(value = "/allPublishDate/{id}",method = RequestMethod.GET)
                public List<String> getAllPublishDate(@PathVariable Long id) {
                    return articleService.getAllPublishDate(id);
                }
                前端
                getAllPublishDate () {
                    var _this = this
                    getRequest(`/article/allPublishDate/${_this.userid}`).then(resp => {
                    if (resp.status == 200 && resp.data) {
                    _this.allPublishDate = resp.data;
                    console.log(resp)
                    console.log(_this.allPublishDate)
                    } else {
                        console.log('查询失败') 
                    }
                    })
                }
                很喜欢这个方法，直接构造url就可以了
            ```
        + 使用@ModelAttribute注解获取POST请求的FORM表单数据（好像是和jsp连用。。。没兴趣，也许能和thymeleaf结合使用，不过都学了前端了还用thymeleaf干嘛。。。）
        + 用注解@RequestParam绑定请求参数到方法入参（针对get方式）
            ```
                后端(可选参数defaultValue,required-boolean)
                @RequestMapping(value = "/allPublishDate",method = RequestMethod.GET)
                public List<String> getAllPublishDate(@RequestParam(value = "id",defaultValue = "-1",) Long id) {
                    return articleService.getAllPublishDate(id);
                }
                前端
                getAllPublishDate () {
                    var _this = this
                    getRequest("/article/allPublishDate?id=1").then(resp => {
                    if (resp.status == 200 && resp.data) {
                    _this.allPublishDate = resp.data;
                    console.log(resp)
                    console.log(_this.allPublishDate)
                    } else {
                        console.log('查询失败') 
                    }
                    })
                }
                很喜欢这个方法，直接构造url就可以了
            ```
        + 用注解@RequestBody绑定请求参数到方法入参  用于POST请求
            ```
                后端(传入参数必须与bean中的属性一致)
                @RequestMapping(value = "/allPublishDate",method = RequestMethod.POST)
                public List<String> getAllPublishDate(@RequestBody Long id) {
                    return articleService.getAllPublishDate(id);
                }
                前端
                getAllPublishDate () {
                    var _this = this
                    postRequest('/article/allPublishDate',{
                        id: _this.userid
                    }).then(resp => {
                    if (resp.status == 200 && resp.data) {
                    _this.allPublishDate = resp.data;
                    console.log(resp)
                    console.log(_this.allPublishDate)
                    } else {
                        console.log('查询失败') 
                    }
                    })
                }
            ```
    + 返回list对象
        + 在mybatis文件配置版中如果mapper返回的数据类型是List<String>，那么对应xml文件中resultType是填入String而不是List
            ```
                <select id="getAllPublishDate" parameterType="Long" resultType="String">
                    SELECT publishDate FROM article WHERE uid=#{id}
                </select>
            ```
    + temp
        + 我佛了。。。刚刚填了东方财富发的测试题，太猛了。。。应该会记得很久，早知道应该起码准备好姿势的。。。坐在床上草稿纸都没有。。。太惨了，仿佛看见了结局，当教训吧，做梦也没想到是这样的。。。
    
    + 复习一下js语法吧
        + 整理一下ES6语法，在study仓库中同步吧
        + ???怎么感觉nodejs要火。。。前后端都用js好像的确不错，不过。。。
        + 半天看不完，明天早上es6应该差不多了，symbol好像没怎么看见过。。。万一碰到应用场景再说吧
        + 明天得早起看看能不能领到阿里云的免费服务器，实在不行还能趁着学生特惠一百多买个一年

#### 2020-3-8
    + 在菜鸟教程上大概过了一遍es6语法，有一些之前不太理解的函数和变量大概了解了，项目当中的部分js代码应该可以优化一下，明天应该可以把预览功能做完，话说。。。明天好像是星期一

#### 2020-3-9
    + 本来以外es6看的差不多了处理个数组元素是很简单的事，结果才出了点问题，主要是要对数组中的每一个元素进行处理
        + array.map()方法
            + 对数组中每一个元素进行遍历，之后返回一个新的数组，不改变原数组
        + array.filter()方法
            + 筛选数组中所有符合条件的元素
        + array.find()方法
            + 同样筛选符合条件的元素但是只会返回第一个
        + array.forEach()方法
            + 对于数组每个元素进行操作，会直接修改原数组，没有返回值
    + error: 'val' is defined but never used  no-unused-vars 
        + 导航守卫报错，在设置语法检查之后，如果声明了一个参数但是没有使用，就会报错，之前的处理是console.log打印一下。。。实在太麻烦了，干脆关闭提示吧
        + .eslintrc.js文件，rules 属性里加一个 'no-unused-vars': 0
    + 记录提示差不多了，虽然还有一些系细节问题，比如秘密和日记的发布日期时同时获取的，所以接口数据中有重复数据，但是不影响结果，也不会影响到后面对于匹配用户的呈现，仔细想想也不一定需要专门写一个获取所有日期的接口，写获取所有文章的接口就可以了，不过数据量可能会偏大，对性能可能有一定影响（但是秘密和日记应该使用不同的颜色标记的，只传回日期恐怕不行，可能后面会需要修改）
    + 下面开始预览功能，文章预览直接修改editor模式就可以，不过文章分布的话，打算用vuetify内置的timeline组件，在看了
    + 。。。数据库居然布置课堂作业了，居然是实际的创建数据库插入内容？虽然要求是图形化操作，不过我懒得下载图形化工具，作为一个三流的程序员，手写脚本才是正道好吧，图形化太麻烦了，顺便复习一下数据库的基本操作吧
    + publishdate参数设置方式需要修改一下，不能让后端生成，改由前端传入，如果前端记录的信息不是当前日期，后端的publishdate仍然会获取当前实际日期，这样后端才查询时会同时返回一个多个文章，将会报错，后端publishdate参数类型可能需要修改为string，java好像没有yyyy-mm-dd格式的参数类型。
    + 我还在想之前为什么没有碰到这个问题来着。。。数据库里有很多同一日期的文章。。。后来发现是uid不同，所以查询时并没有查询到同一天的情况
    + 要不数据库也改一下？
    + ok了，预览还没上
    + 预览这么做吧
        + 创建一个新的路由，前端效果用timeline加上代表文章类型的两种图标实现，点击图标弹出一个card组件，显示文章内容，现在比较麻烦的地方在于存在两个用户分别有两种类型的文章要显示，怎么将这些内容呈现在页面上呢？目前的想法是设置两个按钮控制分别筛选文章的用户和类型，这个按钮，怎么设置才能对界面的影响更小一点？navbar，我会设置上划消失，这样沉浸感应该会更好，可选按钮我看看vuetify的文档吧
    + 目前预览可以显示标题和日期了，明天写一下内容预览，本来前端打算做一下内容过滤的，结果。。。技术有待精进，不过滤凑合着用吧
    + 最后出了点问题，因为前面的在home页面的id是写死了，最后才发现，改成动态的时候需要在mounted阶段先获取userid再根据userid获取publishdate，但是涉及到了异步操作，不太清楚，最后熟悉了一下nextTick花了一些时间才处理好

#### 2020-3-10
    + 单独的预览功能ok了，说实话挺好看的，嘿嘿嘿，下面就是根据文章状态和用户id进行切换转变视图的功能了，等等，这边有个问题，秘密是对特定的人说的，如果匹配的人改变了？？？秘密要不要全部删除？？？不然给其他人看见挺尴尬的。。。，要不article数据表中加上展示对象字段？？？
    + 哦豁，发现了其他问题，没有按时间排序。。。数据库里查询的时候修改一下吧
    + ok，查询语句加一个字段就能让js少写一个函数，真的舒服

#### 2020-3-11
    + 最近打算将产品部署到服务器上了，买了阿里云的云服务器，系统centos，好像win10自带的远程登录有点问题，目前只能在阿里云官网登录或者用crt登录，不过crt的确不太好看。。。的确。。。
    + 用了xshell6，域名实名制好像要两三天，备案又要三天左右，麻烦，今天整理一下后端，用不到的代码删掉吧
    + article部分删的差不多了，其他无关紧要的部分也差不多了，只剩下几个接口了，不过roles接口和users接口还不敢轻举妄动，事实上日记项目用户都不需要分类来着，接口先留着吧
    + 碰到一个奇怪的点，在插入和修改文章时使用了int类型的mapper想不通返回数据和int类型有什么关系，最后找到，int类型返回修改数据的条数，如果未修改就是0，可以判断操作是否成功

#### 2020-3-12
    + 进度要稍微快一点了，今天完成时间表双用户视图和双状态切换功能，文章数据表需要增加otherId字段和showTime字段，用户数据表需要加上isMarch字段和marchId
        ```
            ALTER TABLE `article` ADD COLUMN `otherId` bigint(32) DEFAULT NULL;
            ALTER TABLE `article` ADD COLUMN `showTime` datetime DEFAULT NULL;
            ALTER TABLE `user` ADD COLUMN `matchId` bigint(32) DEFAULT NULL;
            ALTER TABLE `user` DROP COLUMN `gender`;
            ALTER TABLE `user` ADD COLUMN `gender` varchar(10) DEFAULT "保密";
            ALTER TABLE `user` ADD COLUMN `isMatch` int(2) DEFAULT 0;
            ALTER TABLE `match` ADD COLUMN `matchTime` datetime DEFAULT NULL;
            由于粘贴脚本时忘了改名字，加错了数据库
            ALTER TABLE `article` DROP COLUMN `marchId`;
            ALTER TABLE `article` DROP COLUMN `isMarch`;
            ALTER TABLE `user` DROP COLUMN `matchId`;
            ALTER TABLE `user` DROP COLUMN `isMatch`;
        ```
    + 目前只写日记视图渲染，所以匹配用户先直接插入做测试，匹配功能可能明天再写，开始整理后端
    + 哦豁上数据库的课了
    + 继续
    + 哦豁已经遇上之前想的问题了，现在viewall功能的接口是通用型的接口，即接收到/id/state就开始查询数据，这样用户视图切换的确比较简单，但是会有一点点问题，我想想，在这个接口下增加一个比对功能，如果请求id和目前登录的userid不一致，另写一个mapper查询，该mapper将会对article的otherid和前端传入id进行匹配，另外如果传入state = 2，将会对article的showTime参数进行比较，只有当前时间大于showTime时，文章才会进行展示
    + 标题做了截断，控制在十个字符以内，下面开始mapper校对功能
    + 打包参考链接https://www.jb51.net/article/142837.htm
    + 思路出了点问题，理一下思路，将匹配用户id传入后端时，后端做比对，如果与当前用户不匹配将会对article中的otherId字段进行搜索，数值为当前用户id
    + 当对匹配用户的文章进行查询时，如果state = 1直接返回数据，如果state = 2时，将对时间进行比对，这边写一个sql判断函数吧
    + sql里面的case好像不太会用，传闻mybatis当中有if语句用了很奇怪。。。这特么是什么人间疾苦，本来是这样的
        ```
            SELECT * FROM article
            WHERE otherId=#{tempId}
            AND state=#{state}
            <if test="state = 2">
                AND current_timestamp > showTime
            </if>
            ORDER BY publishDate DESC
            这个写法，我还好奇为什么前端到后端state会被强制写成2，知道是mapper问题但是不知道是哪里的问题。。。
            ----------------------------------
            SELECT * FROM article
            WHERE otherId=#{tempId}
            AND state=#{state}
            <if test="state.equals(2)">
                AND current_timestamp > showTime
            </if>
            ORDER BY publishDate DESC
            然后我发现这个可以用。。。以为自己发现了新大陆。。。
            ----------------------------------
            SELECT * FROM article
            WHERE otherId=#{tempId}
            AND state=#{state}
            <if test="state == 2">
                AND current_timestamp > showTime
            </if>
            ORDER BY publishDate DESC
            哦豁。。。这是正解。。。被自己蠢哭了，跨语言也不能忘记比较用==啊。。。
        ```
    + 差不多该写匹配功能了，明天一天够了，然后文章功能部分修改一下估计也是一天，之后三天处理一下设置模块和基础信息部分的ui，到时候备案应该快了，到时候前端打包成app看能不能扔到一个平台上，后端部署到阿里云服务器，基本上算是完结了，更新后面再看，其他的技术栈不太能拖了，时日无多
    + 突然想起来java好像是可以发邮件的，这样邮箱验证并不是很麻烦，验证码用一个插件搞定就好了，突然有个新点子，我干脆加一个新功能吧，将秘密定时发到对应邮箱？发件人就是我，不过内容是md格式就很诡异。。。可能发一个html文件会比较好，唉，还是有htmlContent比较好。。。先提上日程，今天还是按计划完成匹配功能，看一下之前的思路
    + 早上一起启动控制台居然报错，虽然对于页面渲染没有影响，百度了一下，是由于开始没有数据，所以substr报错，用来一个v-if语句处理了

#### 2020-3-13
+ 现在可以进行用户匹配了，但是匹配之后的数据渲染以及匹配之前的视图渲染需要修改
+ 目前的逻辑
    + 请求方发起一个匹配会在match表中插入一个字段，默认状态为0，当被请求仿同意时，match表对应字段状态变为1，拒绝变为2
    + 当被请求方同意时，我们将会修改user数据表中两个用户的otherId字段和isMatch字段，表示这个用户处于匹配状态
    + 。。。哦豁就这个功能居然花了。。。唉，今天主要就是新建了match表，然后创建好对应的controller，service，mapper，然后写了几个函数，还有前端加了一点点东西。。。为什么做起来很麻烦，做完又感觉没做什么事。。。今天到这吧

#### 2020-3-14
+ 又到周末了，今天写一下匹配成功之后的数据渲染
+ 根据用户和类型切换的文章渲染基本上没问题了，保存的秘密类型文章只会对一个otherid生效并且只有在满足showtime字段要求后才会显示，这样在更换匹配对象之后，原先的秘密也不会被查看了
+ 下面对秘密类型的文章保存做处理
+ 等等，如果没有匹配也写了秘密呢？？？话说。。。没有匹配的情况下私人日记应用还有秘密要矫情成什么样啊？没错，我就是这么矫情，mybatis语句里面加一个if吧
+ mybatis里if语句好像条件判断的参数必须是传入的参数不能对本地参数进行校验，这个功能先保留吧，先对文章提交做处理，主要是根据当前匹配情况设置文章的otherid和showTime字段
+ 看样子ok了？我试试看，没有匹配时保存的秘密是什么样子的
+ 遇到一个坑位，mybatis对于前端传递字符串是否为空的判断。。。我以为前端param：'' 后端接收就是null了，结果。。。后端接收的是参数为null的字符串。。。需要用param == 'null'匹配。。。佛了
+ 好像差不多了？？？我想想，应该是差不多了，还有就是对没有匹配时写的秘密了。。。这个要不要给其他人看？？？算了，能保存就行，能不能后面再说
+ 哦豁。。。突然想起了性别没加，唉，日记这事还分什么男女啊，，，还是得分的吧。。。
+ 剩下的，解绑功能，还有性别字段，还有匹配也对方基本信息渲染，邮箱验证，好像还有什么来着。。。想起来再说
+ 匹配用户信息渲染完成，改了点ui一点点。。。vuetify里面绝对定位和相对定位能用吗？？？布局有点麻烦。。。

#### 2020-3-15
+ 在做邮箱验证功能，使用的是QQ邮箱，现在的问题是验证码和用户绑定，总感觉越写路子越野了。。。理论上一个交给security过滤器来处理的。。。不过稍微有点繁琐。。。等等。。。直接写死吧。。。是不是太草率了。。。
+ 。。。后端写死发固定验证码是真蠢。。。不过也太方便了吧。。。前端直接参数过滤。。。我怎么能这样。。。
+ 解绑功能的话，不写解绑功能吧，待会修改一下逻辑，匹配之前进行参数检查，如果已经匹配则请求结束，虽然不能解绑有点产品缺陷的感觉，不过，我希望这个软件只为两个人存在，不应该存在第三个人，理论上到后期日记功能也不会经常用吧，如果他们在一起生活的话，如果最后分了，这个软件里的内容会成为专属于对方的历史，ok，就这么定了，性别字段。。。现在加上好吧
+ 组件库虽然封装的ui好看但是。。。在调整样式细节时总会出那么点问题，组件库自己封装的样式有时候也不一定生效
+ 性别字段添加好了，用的varchar（10），总感觉有点消耗性能
+ 修改一下匹配机制
+ 逻辑修改完成，当发起匹配时，数据库将会对已有邮箱进行查询，如果该邮箱存在且未匹配，请求才能进行
+ 到这里应该差不多了，突然想起了之前大一那时候，有个人发现了验证码绕过漏洞，验证码直接写在了前端。。。不就是这种情况吗。。。真的是。。。后面改吧，功能到这里基本上差不多了，需要完善的部分
+ 日记图片添加（话说前几天我买的服务器好像40G总量。。。能放多少图片。。。算了，该加还是得加的）
+ 前端各种提示信息--毕竟用户看不见控制台报错
+ 哦豁。。。突然看到了红点功能（badges），还有是邮件的话不应该只用于用户注册。。。虽然验证码写死了，但是发发用户想传递的信息应该是没问题的，等等，问题很大，我还不清楚自动发邮件的操作，难道每秒都要监测数据库的每一条信息？？？用什么监测还不清楚，和事务有关？红点功能必须和已读未读一起用，啧，新的麻烦
+ 加了功能：注册时对邮箱进行查询，防止重复邮箱注册
+ 啧，vuetify好像没有我想要的提示功能

#### 2020-3-17
+ 备案应该快了，发了实习简历给字节跳动北京，南京这边好像是要数据背景应该是凉了，今天处理一下用户界面的提示功能，如果vuetify找不到相关组件可能需要手写了
+ 找到了tooltip,封装成组件使用，显示内容从外部传递，显示判断参数也是
+ 组件封装好了，当父组件传递给子组件的内容发散改变时将会触发显示tip，三秒之后自动关闭，这边有个setTimeout的注意事项，引用方法时是不能加（）的，加上了方法不会执行
+ 下面就是对将tip组件放入各个模块中，当接口报错时，将报错信息赋值给传入参数了
+ 做了部分提示处理，还不是特别全面，对所有var _this = this进行了处理，在es6中箭头函数的this指针指向全局，所以不需要借用_this将this引入函数体内部
+ 下面是对axios请求报错的提示处理
+ 差不多了，到这里大体上已经结束了，下面就是webapp部署了，时间已经来到了三月中旬，我的时间也不多了

#### 2020-3-20
+ 备案是真快，我以为要等很久来着，下面开始打包项目了，前端通过hbuilder打包成app扔到应用商店，后端打包扔到服务器
+ linux安装mysql消耗了点时间，后端需要调整一下，因为数据库更新了，版本不能保持一致，依赖需要修改，先配置好数据表吧