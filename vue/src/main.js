import { createApp } from 'vue'
import Vue from 'vue'

import App from './App.vue'
import router from './router'
import store from './store'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import '@/assets/css/globalCSS.css'

import * as ElIcons from '@element-plus/icons'
const app = createApp(App);
for (const iconName in ElIcons) { app.component(iconName,ElIcons[iconName])}


import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

createApp(App)
    .use(store)
    .use(router)
    .use(mavonEditor)
    .use(ElementPlus, {locale: zhCn,})
    .mount('#app')
