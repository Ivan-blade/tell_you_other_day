<template>
    <v-card
    class="my-5 mx-auto"
    max-width="344"
    outlined
    >
      <v-list>
        <v-subheader>个人信息</v-subheader>
        <v-list-item-group>
          <v-list-item>
            <span>
              id: 123456789
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              性别：男
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              昵称：administrator
            </span>
          </v-list-item>
          <v-list-item>
            <span>
              格言：只放一只牛
            </span>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-card>
</template>

<script>
  export default {
    name: 'PersonalInfo'
  }
</script>