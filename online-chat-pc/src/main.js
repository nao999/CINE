import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

// 导入elementUI
import ElementUI from 'element-ui'
import { Message } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import axios from 'axios'

import ws from "@/store/store";


// 配置请求根路径
axios.defaults.baseURL = 'http://42.193.125.212:8000/'
// axios.defaults.baseURL = 'http://localhost:8000/'

Vue.prototype.$http = axios

Vue.prototype.$ws = ws

//导入全局样式表
import '@/assets/css/global.css'

// 导入字体图标
import '@/assets/fonts/iconfont.css'

import '@/assets/fonts/iconfont'


Vue.config.productionTip = false

Vue.use(ElementUI, {locale})

import store from '@/store/store'       //引入vuex
Vue.prototype.$store = store      //引入vuex

// 引入图片放大功能

import preview from 'vue-photo-preview'
import 'vue-photo-preview/dist/skin.css'

import VueCropper from 'vue-cropper'
Vue.use(VueCropper)

let options = {
    fullscreenEl: false, //控制是否显示右上角全屏按钮
    closeEl: false, //控制是否显示右上角关闭按钮
    tapToClose: true, //点击滑动区域应关闭图库
    shareEl: false, //控制是否显示分享按钮
    zoomEl: false, //控制是否显示放大缩小按钮
    counterEl: false, //控制是否显示左上角图片数量按钮
    arrowEl: true,  //控制如图的左右箭头（pc浏览器模拟手机时）
    tapToToggleControls: true, //点击应切换控件的可见性
    clickToCloseNonZoomable: true //点击图片应关闭图库，仅当图像小于视口的大小时
};
Vue.use(preview, options)




new Vue({
  el: '#app',
  router,
  render: h => h(App),
  components: {App},
  template: '<App/>',
    store,
})

// 添加请求拦截器，在请求头中加token

axios.interceptors.request.use(
    config => {

      if (window.localStorage.getItem('token')) {
        console.log("tokennnn...");

        config.headers.token = window.localStorage.getItem('token');

      }
      return config;

    },

    error => {

      return Promise.reject(error);

    });


// 拦截所有请求的响应
axios.interceptors.response.use(
    response => {
        // res是响应的结果
        console.log('响应拦截器：')
        console.log(response.data)
        switch (response.data.code) {
            case 401:
                // 登录失效
                // 响应成功的拦截
                Message.error("登录签名已失效，请重新登录~~")

                router.push('/login')
                break

            default:
                return response
        }
        return response
    })
