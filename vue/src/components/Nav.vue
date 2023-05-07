<template>
  <div>
    <el-menu
        style="width: 200px;min-height:calc(100vh - 50px)"
        :uniqueOpened="true"
        default-active="1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">

      <router-link to="/">
        <el-menu-item index="1">
          <el-icon><house/></el-icon>
          <template #title>主页</template>
        </el-menu-item>
      </router-link>

      <el-sub-menu index="2">
        <template #title>
          <span><el-icon><user /></el-icon>用户管理</span>
        </template>
        <router-link to="/user">
          <el-menu-item index="2-1">
            <el-icon><user-filled /></el-icon>
            用户管理</el-menu-item>
        </router-link>

        <router-link to="/travel">
          <el-menu-item index="2-3">
            <el-icon><promotion /></el-icon>
            旅游项目管理</el-menu-item>
        </router-link>
      </el-sub-menu>

      <el-sub-menu index="3">
        <template #title>
          <span><el-icon><collection /></el-icon>网页管理</span>
        </template>
        <router-link to="/img/">
          <el-menu-item index="3-1">
            <el-icon><picture-filled /></el-icon>
            首页图片
          </el-menu-item>
        </router-link>
        <router-link to="/text">
          <el-menu-item index="3-2">
          <el-icon><list /></el-icon>
          文章审查</el-menu-item>
        </router-link>
        <router-link to="/top">
        <el-menu-item index="3-3">
          <el-icon><star-filled /></el-icon>
          热门推荐</el-menu-item>
        </router-link>
      </el-sub-menu>

      <router-link to="/comment">
        <el-menu-item index="4">
          <el-icon><chat-line-square /></el-icon>
          <template #title>评论管理</template>
        </el-menu-item>
      </router-link>

      <router-link to="/buy">
        <el-menu-item index="5">
          <el-icon><goods /></el-icon>
          <template #title>订单管理</template>
        </el-menu-item>
      </router-link>
    </el-menu>
  </div>
</template>

<script>

import {House} from "@element-plus/icons";
import {User} from "@element-plus/icons";
import { Collection } from '@element-plus/icons'
import { ChatLineSquare } from '@element-plus/icons'
import { Goods } from '@element-plus/icons'
import { UserFilled } from '@element-plus/icons'
import { Shop } from '@element-plus/icons'
import { Promotion } from '@element-plus/icons'
import { StarFilled } from '@element-plus/icons'
import { List } from '@element-plus/icons'
import { PictureFilled } from '@element-plus/icons'

export default {
  name: "Nav",
  components:{
    House,
    User,
    Collection,
    ChatLineSquare,
    Goods,
    UserFilled,
    Shop,
    Promotion,
    StarFilled,
    List,
    PictureFilled,
  },
}
</script>

<style scoped>

</style>