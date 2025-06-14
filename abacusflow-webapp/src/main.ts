import './assets/main.css'

import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import {VueQueryPlugin} from '@tanstack/vue-query'
import {Configuration} from './core/openapi'
import TimestampTranslate from './plugin/timestamp-translate'
import FetchApi from './plugin/fetch'

const app = createApp(App)

app.use(createPinia())
// 引入路由
app.use(router)
// 引入 Ant Design Vue
app.use(Antd)
// 引入 Vue Query 插件
app.use(VueQueryPlugin)
// 配置 Openapi-Generate-Api 插件
const config = new Configuration({ basePath: '/api/v1' })
app.use(FetchApi, config)
// 全局注册一个日期格式化函数
app.use(TimestampTranslate)
app.mount('#app')
