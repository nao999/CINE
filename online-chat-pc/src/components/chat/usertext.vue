<template>
  <div id="usertext">
    <div style="display:flex">
      <el-popover placement="top-start" width="400"  trigger="click" class="emoBox" >
        <div class="emotionList">
          <a href="javascript:void(0);" @click="getEmo(index)" v-for="(item,index) in faceList" :key="index" class="emotionItem">{{item}}</a>
        </div>
        <el-button id="emojiBtn" class="emotionSelect" slot="reference">
          <i class="iconfont icon-biaoqing" aria-hidden="true"></i>
        </el-button>
      </el-popover>
      <el-upload
          ref="uploadFiles"
          class="upload-demo"
          action="http://42.193.125.212:8000/upload/uploadImg"
          :on-remove="handleRemove"
          :file-list="fileList"
          name="image"
          :on-success="handleUploadSuccess"
          :show-file-list = "false"
          limit="1">
        <el-button id="uploadImgBtn" icon="el-icon-picture-outline"></el-button>
      </el-upload>
    </div>
    <textarea id="textarea" placeholder="按 Ctrl + Enter 发送" v-model="content" >
    </textarea>
<!--      <el-button @click="test">test</el-button>-->
    <el-button id="sendBtn" type="primary" size="mini" @click="sendMessage" >发送(S)</el-button>
  </div>
</template>

<script>
const emojiData=require("@/assets/json/emoji.json")//引入存放emoji表情的json文件

export default {
name: "usertext",
  data(){
    return{
      content: '',
      faceList: [],
      fileList: [],
    }
  },
  created() {
    // this.test();
  },
  mounted(){
    for (let i in emojiData){//读取json文件保存数据给数组
      this.faceList.push(emojiData[i].char);
    }
  },
  methods:{
    handleUploadSuccess(response){
      // 获得上传成功后的链接
      console.log(response)
      this.$store.commit("send",'image::' + response.response);
      this.$refs.uploadFiles.clearFiles();
    },
    getHistory(){
      this.$http.get("/user-records/getHistory",{
        params:{
          friendName: this.$store.state.friendName
        }
      }).then(response => {
        console.log(response.data)
      })
    },
    test(){
      this.$store.commit("send","123");

    },
    sendMessage(){
      this.$store.commit("send",this.content);
      this.content = ''
    },
    //获取Emoji
    getEmo(index){
      var textArea=document.getElementById('textarea');
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
      this.content=textArea.value;// 要同步data中的数据
      // console.log(this.faceList[index]);
      return;

    },
  }
}
</script>

<style scoped>
#usertext {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 30%;
  border: solid 1px #DDD;
  background-color: white;
}
textarea {
  padding: 10px;
  width: 95%;
  height: 58%;
  border: none;
  outline: none;
  resize: none;
}
#sendBtn{
  float: right;
  margin-right: 10px;
}
#uploadImgBtn{
  border: none;
  padding-bottom: 0px;
  margin-bottom: 0px;
  padding-left: 12px;
}
#sendBtn{
  float: right;
  margin-right: 10px;
}
#uploadImgBtn{
  border: none;
  padding-bottom: 0px;
  margin-bottom: 0px;
  padding-left: 12px;
}
#emojiBtn{
  border: none;
  padding-right: 0px;
  padding-bottom: 0px;
  margin-bottom: 0px;
}
#emojiBtn:hover{
  background-color: white;
}
.upload-btn{
  display: inline-block;
}
.emotionList{
  display: flex;
  flex-wrap: wrap;
  padding:5px;
}
.emotionItem{
  width:10%;
  font-size:20px;
  text-align:center;
}
/*包含以下四种的链接*/
.emotionItem {
  text-decoration: none;
}
/*正常的未被访问过的链接*/
.emotionItem:link {
  text-decoration: none;
}
/*已经访问过的链接*/
.emotionItem:visited {
  text-decoration: none;
}
/*鼠标划过(停留)的链接*/
.emotionItem:hover {
  text-decoration: none;
}
/* 正在点击的链接*/
.emotionItem:active {
  text-decoration: none;
}
</style>

<style>
.el-popover{
  height: 400px;
  overflow-y: auto;
}
</style>
