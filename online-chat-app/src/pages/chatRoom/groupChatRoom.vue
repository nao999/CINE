<template>
  <div class="chat-room">
    <div class="top">
      <van-icon name="arrow-left" @click="back()"/>
      <div class="chat-name">{{this.$store.state.chatGroupName}}</div>
      <van-icon @click="groupMsg()" size="30" name="more-o" style="margin-right: 10px;" />

    </div>
    <div class="centre"  v-scroll-bottom="$store.state.sessions">
      <div  v-for="msgItem in this.$store.state.sessions" :key="msgItem.id">
        <p class="time">
          <span>{{msgItem.createTime.split(' ')[0]}} {{msgItem.createTime.split(' ')[1].split(':')[0] > 12 ? '下午' : '上午'}} {{msgItem.createTime.split(' ')[1]}}</span>
        </p>
        <div :class="getClass(msgItem.username)">
          <van-image
              width="60"
              height="60"
              :src="msgItem.userProfile"
              round
          />
          <div class="msg">
            <div class="user-name">{{msgItem.username}}</div>
            <div  v-if="msgItem.messageType === 0" class="msg-content">
              {{msgItem.messageContent}}
            </div>
            <van-image @click="viewImg(msgItem.messageContent)" style="width: 60%" v-else :src="msgItem.messageContent"/>

          </div>
        </div>
      </div>

      <!--      <div class="user-item" >-->
      <!--        <van-image-->
      <!--            width="70"-->
      <!--            height="70"-->
      <!--            :src="ss"-->
      <!--            round-->
      <!--        />-->
      <!--        <div class="msg">-->
      <!--          <div class="user-name">ss</div>-->
      <!--          <div class="msg-content">ss</div>-->
      <!--        </div>-->
      <!--      </div>-->

      <!--      <div class="user-item-self" >-->
      <!--        <van-image-->
      <!--            width="70"-->
      <!--            height="70"-->
      <!--            src="http://nao190830.oss-cn-beijing.aliyuncs.com/20211114/a33ab955387c43a29f4be767ca648190.jpg"-->
      <!--            round-->
      <!--        />-->
      <!--        <div class="msg">-->
      <!--          <div class="user-name">ss</div>-->
      <!--          <div class="msg-content">ss</div>-->
      <!--        </div>-->


      <!--      </div>-->

    </div>
    <div class="bottom">
      <div class="chat-input">
        <input id="msg-input"  v-model="message" type="text"/>
        <van-icon name="smile-o" @click="emojiShow = !emojiShow"/>
        <van-button v-if="this.message.length === 0? 0 : 1" size="small" type="info" @click="sendMessage">发送</van-button>
        <van-uploader  v-else :after-read="afterRead">
          <van-icon name="add-o" />
        </van-uploader>

      </div>
      <div v-if="emojiShow" class="emotionList">
        <a href="javascript:void(0);" @click="getEmo(index)" v-for="(item,index) in faceList" :key="index" class="emotionItem">{{item}}</a>
      </div>
    </div>
  </div>
</template>

<script>
import {ImagePreview} from "vant";

const emojiData=require("@/assets/json/emoji_easy.json")//引入存放emoji表情的json文件
export default {
name: "groupChatRoom",
  data() {
    return {
      friendUsername: '',
      inputActive: 0,
      message: "",
      faceList: [],
      emojiShow: false,
      imgList: []

    };
  },
  mounted(){
    for (let i in emojiData){//读取json文件保存数据给数组
      this.faceList.push(emojiData[i].char);
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
    viewImg(img){
      this.imgList.push(img)
      ImagePreview({
        images: this.imgList,
        showIndex: false,
        closeable: true

      });
      this.imgList=[];
    },
    afterRead(file) {
      // 此时可以自行将文件上传至服务器
      console.log(file);
      let formData = new FormData();
      formData.append("image",file.file);
      // 先将图片上传到云端
      this.$http.post("/upload/uploadImg",formData,
          {'Content-type':'multipart/form-data'})
          .then(response =>{
            // console.log(response)
            this.message = response.data.response;
            this.$store.commit("send",'image::' + this.message);
            this.message = '';
            this.emojiShow = false;
          })
    },
    groupMsg(){
      this.$router.push("/groupMsg/"+ this.$store.state.chatGroupId);
    },
    //获取Emoji
    getEmo(index){

      var textArea=document.getElementById('msg-input');
      //将选中的表情插入到输入文本的光标之后
      function changeSelectedText(obj, str) {
        if (window.getSelection) {
          // 非IE浏览器
          textArea.setRangeText(str);
          // 在未选中文本的情况下，重新设置光标位置
          textArea.selectionStart += str.length;
          textArea.focus()
        } else if (document.selection) {
          // IE浏览器
          obj.focus();
          var sel = document.selection.createRange();
          sel.text = str;
        }
      }
      changeSelectedText(textArea,this.faceList[index]);
      this.message=textArea.value;// 要同步data中的数据
      // console.log(this.faceList[index]);
      return;

    },
    sendMessage(){
      this.$store.commit("send",this.message);
      this.message = '';
      this.emojiShow = false;
    },
    // sendBtn(){
    //   if(this.message.length === 0){
    //     this.inputActive = 0 ;
    //   }else {
    //     this.inputActive = 1;
    //   }
    // },
    getClass(username){
      // console.log(this.$store.state.focusGroup)
      // console.log("///")
      // console.log(id);
      // return username === this.$store.state.friendName ? 'user-item' : 'user-item-self';
      return this.$store.state.user.username !== username ? 'user-item' : 'user-item-self';

    },

    back(){
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
.chat-room{
  background-color : #f5f5f5;
  height: 100%;
  position: fixed;
  width: 100%
}
.top{
  display: flex;
  align-items: center;
  justify-content: space-between;
  /*margin-top: 10px;*/
  padding: 10px 10px;
  background-color: aliceblue;
  position: fixed;
  width: 100%;
  z-index: 1;
  top: 0;
}
.top .chat-name{
  font-size: 20px;
  color: #03060f;
}
.top .info{
  display: flex;
  align-items: center;
}

.centre{
  display: flex;
  flex-direction: column;
  margin-top: 50px;
  margin-bottom: 50px;
  overflow-y: scroll;
  top: 0;
  bottom: 0;
  position: fixed;
  width: 100%;
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

.centre .user-item{
  display: flex;
}
.msg{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
  width: 75%;
}
.msg .user-name{
  color: #a0a0a0;
}
.msg .msg-content{
  background-color: white;
  font-size: 20px;
  padding: 10px 20px;
  border-radius:30px;
  margin-left: -10px;
  margin-top: 10px;
  word-break: break-word;
}

.centre .user-item-self{
  text-align: right;
}

.user-item-self .van-image{
  float: right;
}

.user-item-self .msg{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.user-item-self .msg .msg-content{
  background-color: #0099ff;
  font-size: 20px;
  color: white;
  padding: 10px 20px;
  border-radius:30px;
  margin-left: -10px;
  margin-top: 10px;
  word-break: break-word;
}

.bottom{
  position: fixed;
  bottom: 0;
  width: 100%;
  background-color: white;
}

.bottom .chat-input{
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
}
.chat-input input{
  background-color: #f4f4f4;
  border: 0;
  width: 80%;
}
.chat-input .van-icon{
  font-size: 25px;
}
.emotionList{
  height: 300px;
  display: flex;
  padding: 5px;
  font-size: 40px;
  flex-wrap: wrap;
  overflow: scroll;
}
</style>
