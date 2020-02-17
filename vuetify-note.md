+ v-app项目主页面
    + 包含bar和content部分，bar即导航栏，content用于展示路由对应的内容
    ```
        <v-app class="blue lighten-4">
            <Navbar />
            <v-content>
            <router-view></router-view>
            </v-content>
        </v-app>
    ```
+ v-app-bar
    + 一般包含bar和点击触发的navigation两部分
        ``` 
            <template>
                <!-- nav标签内没有特殊效果，该标签只是表示包含内容是导航标签，一般内容为列表 -->
                <nav>
                    <v-app-bar>
                    </v-app-bar>
                    <v-navigation-drawer app>
                <!-- 此处app表示挂载点，即导航会在挂载点页面渲染，不设置的话，嘿嘿嘿 -->
                    </v-navigation-drawer>
                </nav>
            </template>
        ```
    + 一般作为组件引入进主页面v-app中
    + v-app-bar-nav-icon
        + 导航栏控制图标，一般使用v-model实现对导航栏的控制
    + v-toolbar-title
        + bar标题
    + v-spacer
        + 自动填充间隔
    + note
        + 使用appbar时有一些内容可能不能再pc端显示，在pc端应该使用toolbar
+ v-navigation-drawer（导航栏，配合v-model绑定布尔值进行展开收缩）
    ```
        <v-navigation-drawer app v-model="navShow" class="blue lighten-3">
          <v-list>
            <v-subheader>Here id Menu</v-subheader>
            <v-list-item-group>
                <v-list-item
                v-for="(link, i) in links"
                :key="i"
                router :to="link.route"
                >
                    <v-list-item-icon>
                        <v-icon v-text="link.icon" class="text--white"></v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title v-text="link.text" class="text--white"></v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
          </v-list>
      </v-navigation-drawer>
    ```
+ v-icon（图标）
    + 在标签内部直接引用库中定义好的图标名即可，也可以使用v-text代替直接在标签上引用图标名
+ v-list(承载列表所有内容)
    + v-subheader（列表标题）
    + v-list-item-group（承载列表中重复内容）
        + v-list-item（单个内容）
            + v-list-item-icon（单个内容图标）
            + v-list-item-title(单个内容标题)
