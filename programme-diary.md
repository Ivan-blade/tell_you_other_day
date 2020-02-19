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
    + 引入google图标库（内网访问不了谷歌，需要下载到本地库--注意不是官方的，不一定会更新）
        + npm install material-design-icons-iconfont -D
        + main.js中import 'material-design-icons-iconfont/dist/material-design-icons.css'
    + 引入bar组件
        + 创建相关组件--Navbar
        + 在app中引入
    + 开始思考具体结构和功能实现

+ 2020-2-18
    + 开始编写各个功能模块以及对应路由