import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

import 'vant/lib/index.css'
import { Button } from 'vant';
import { Form } from 'vant';
import { Field } from 'vant';
import { Tabbar, TabbarItem } from 'vant';
import { Image as VanImage } from 'vant';
import { Icon } from 'vant';
import { Search } from 'vant';
import { Tab, Tabs } from 'vant';
import { Toast } from 'vant';
import { Popover } from 'vant';
import { DropdownMenu, DropdownItem } from 'vant';
import { Popup } from 'vant';
import { ActionSheet } from 'vant';
import { Checkbox, CheckboxGroup } from 'vant';
import { Dialog } from 'vant';
import { Uploader } from 'vant';
import { ImagePreview } from 'vant';

// 全局注册
Vue.use(ImagePreview);
Vue.use(Uploader);
Vue.use(Dialog);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(ActionSheet);
Vue.use(Popup);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Popover);
Vue.use(Toast);
Vue.use(Tab);
Vue.use(Tabs);
Vue.use(Search);
Vue.use(Icon);
Vue.use(VanImage);
Vue.use(Tabbar);
Vue.use(TabbarItem);
Vue.use(Form);
Vue.use(Field);
Vue.use(Button);

import mui from 'vue-awesome-mui'

Vue.use(mui)
Vue.prototype.mui = mui




import axios from 'axios'
// 配置请求根路径
// axios.defaults.baseURL = 'http://localhost:8000/'
axios.defaults.baseURL = 'http://42.193.125.212:8000/'

Vue.prototype.$http = axios

import store from '@/store/store'       //引入vuex
Vue.prototype.$store = store      //引入vuex

new Vue({
  render: h => h(App),
  router
}).$mount('#app')

// 拦截所有发出的请求
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
                Toast.fail("登录签名已失效，请重新登录~~")

                router.push('/login')
                break

            default:
                return response
        }
        return response
    })
