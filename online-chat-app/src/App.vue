<template>
  <div id="app">
    <transition :name="transitionName">
      <router-view/>
    </transition>
  </div>
</template>

<script>

export default {
  name: 'App',
  data(){
    return{
      transitionName: ''
    }
  },
  created() {
    /*** 手机返回键监听 ***/
    this.mui.init({
      // 监听Android手机的back、menu按键
      keyEventBind: {
        backbutton: true, //Boolean(默认true)关闭back按键监听
      }
    })
    var first = null;
    // 给手机的back按键重新绑定监听(1s内，连续点击两次返回键，则退出应用)
    this.mui.back = ()=>{
      // 首次按返回键
      if(!first){
        first = new Date().getTime();//记录第一次按返回键的时间
        if (this.$route.name == 'home') {//判断当前是否为首页(无后退页)
          this.mui.toast("再按一次退出应用");
        }
        history.go(-1); // 返回到上一页
        setTimeout(()=>{
          // 1s后清除
          first = null;
        }, 1000);
      }else{
        if (new Date().getTime() - first < 1000) {
          // 连续按两次返回键且时间小于1s
          this.plus.runtime.quit(); //退出app
        }
      }
    }
  },
  watch: {
    $route () {
      // 切换动画
      // 页面之间的跳转一般由路由中的level来实现
      let level = this.$route.meta.level // 监听路由的层级
      console.log(level)
      if (level === 1) {
        this.transitionName = 'slide-right'
      } else {
        this.transitionName = 'slide-left'
      }
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

/*将页面前进或后退两种不同效果的动画都写下来*/
.slide-left-enter, .slide-right-leave-to {
  opacity: 0;
  transform: translateX(100%)
}

.slide-left-leave-to, .slide-right-enter {
  opacity: 0;
  transform: translateX(-100%)
}

.slide-left-enter-active, .slide-left-leave-active, .slide-right-enter-active, .slide-right-leave-active {
  transition: 0.5s;
  position: absolute;
  top:0;
}
</style>
