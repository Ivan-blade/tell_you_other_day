<template>
    <v-container class="px-10">
        <v-row align="center">
            <v-col
            class="my-5"
            >
                <v-text-field
                v-model="username"
                label="username"
                :rules="rules" 
                hide-details="auto"
                class="my-5"
                ></v-text-field>
                <v-text-field
                v-model="email"
                label="email"
                :rules="rules" 
                hide-details="auto"
                class="my-5"
                ></v-text-field>
                <v-text-field
                v-model="password"
                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                :type="show ? 'text' : 'password'"
                label="password"
                @click:append="show = !show"
                :rules="rules" 
                hide-details="auto"
                class="my-5"
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row align="center" class="sty-btn">
            <v-col class="text-center">
                <div>
                    <v-btn 
                    color="blue lighten-1 white--text" 
                    depressed
                    large
                    tile
                    >登录</v-btn>
                </div>
            </v-col>
            <v-col class="text-center">
                <div>
                    <v-btn
                    text 
                    color="blue lighten-1" 
                    large
                    tile
                    >注册</v-btn>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'Login',
    data() {
      return {
        username: '',
        password: '',
        email: '',
        show: false,
        rules: [
            value => !!value || 'Required.'
        ]
      }
    }
}
</script>

<style lang="less" scoped>

</style>