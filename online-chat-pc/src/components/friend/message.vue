<template>
  <div id="message" v-scroll-bottom="$store.state.sessions">
    <div >
      <ul>
        <li v-for="item in $store.state.sessions" v-bind:key="item.id">

          <div v-if="item.messageType === 0">
            <p class="time">
              <span>{{item.createTime.split(' ')[0]}} {{item.createTime.split(' ')[1].split(':')[0] > 12 ? '下午' : '上午'}} {{item.createTime.split(' ')[1]}}</span>
            </p>
            <div :class="judgeUser(item)" >

              <img  class="avatar" :src="item.userProfile" alt=""  preview="1">

              <div class="message-content">
                <p class="username" >{{item.username}}</p>
                <p class="text">{{item.messageContent}}</p>
              </div>
            </div>
          </div>
          <div v-else>
            <div :class="judgeUser(item)" >
              <img  class="avatar" :src="item.userProfile" alt=""  preview="1">
              <div class="message-content">
                <p class="username" >{{item.username}}</p>
                <el-image :src="item.messageContent"  @click="onPreview(item.messageContent)"/>
                <el-image-viewer
                    v-if="showViewer"
                    :on-close="closeViewer"
                    :url-list="[imageUrl]" />
              </div>
            </div>
          </div>
        </li>


      </ul>
    </div>
  </div>
</template>

<script>
// import ws from "@/store/ws-store";
// 导入组件
import ElImageViewer from 'element-ui/packages/image/src/image-viewer'
export default {
name: "message",
  components: { ElImageViewer },
  data(){
    return{
      msg:'',
      showViewer: false, // 显示查看器
      imageUrl:''
    }
  },
  directives: {/*这个是vue的自定义指令,官方文档有详细说明*/
    // 发送消息后滚动到底部,这里无法使用原作者的方法，也未找到合理的方法解决，暂用setTimeout的方法模拟
    'scroll-bottom' (el) {
      //console.log(el.scrollTop);
      setTimeout(function () {
        el.scrollTop+=9999;
      },1)
    }
  },



  methods:{
    onPreview(imageUrl) {
      this.showViewer = true
      this.imageUrl = imageUrl
    },
    // 关闭查看器
    closeViewer() {
      this.showViewer = false
    },

    judgeUser(item){
      // console.log(window.sessionStorage.getItem('username'));
      return item.username === window.localStorage.getItem('username') ? 'self':'main';
    }
  }
}
</script>

<style scoped>
#message {
  padding: 15px;
  height: 60%;
  max-height: 63%;
  overflow-y: scroll;
  overflow-x: hidden;
}
ul{
  list-style-type: none;
  padding-left: 0px;
}
li{
  margin-bottom: 15px;
}
.time {
  text-align: center;
  margin: 7px 0;
}
.time>span{
  display: inline-block;
  padding: 0 18px;
  font-size: 12px;
  color: #FFF;
  background-color: #dcdcdc;
  border-radius: 2px;
}

/*设置图片格式*/
.message-content .el-image{
  display: inline-block;
  /*padding: 10px 10px;*/
  max-width: 40%;
  border-radius: 10px;
  line-height: 30px;
}
.message-content .el-image{
  margin: 5px 0;
}

.main .message-content{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.message-content .text{
  margin: 5px 0;
}

.main .avatar{
  float: left;
  margin: 0 10px 0 0;
  border-radius: 3px;
  width: 30px;
  height: 30px;

}

.main .text{
  text-align: left;
  display: inline-block;
  padding: 0 10px;
  max-width: 80%;
  background-color: #fafafa;
  border-radius: 4px;
  line-height: 30px;
}
.main img{
  display: inline-block;
  height: 100px;
  width: 100px;
  margin-top: 15px;
}

.main .username{

  margin: 0 0;
  padding: 0 0;
  border-radius: 4px;
  line-height: 15px;
  font-size: 10px;
  color: grey;
}

.self{
  text-align: right;
}
.self .message-content{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.self .avatar{
  float: right;
  margin: 0 0 0 10px;
  border-radius: 3px;
  width: 30px;
  height: 30px;
}
.self .text{
  display: inline-block;
  padding: 0 10px;
  max-width: 80%;
  background-color: #b2e281;
  border-radius: 4px;
  line-height: 30px;
}
.self .img{
  display: inline-block;
  height: 100px;
  width: 100px;
  margin-top: 15px;
}
.self .username{

  margin: 0 0;
  padding: 0 0;
  width: 200px;
  line-height: 15px;
  font-size: 10px;
  color: grey;
}
</style>
