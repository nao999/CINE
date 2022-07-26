<template>
  <div class="content">
    <div class="top">
      <div class="info" @click="showUserMsg">
        <van-image
            width="35"
            height="35"
            :src="this.user.userProfile"
            round

        />
        <div class="info-username">{{this.user.username}}</div>
      </div>

      <van-icon @click="showSheet()" size="30" name="plus" style="margin-right: 10px;" />


    </div>
    <friends v-if="active"></friends>
    <message v-else></message>
    <div class="bottom">
      <van-tabbar v-model="active" >
        <van-tabbar-item @click="switchIndex(0)" icon="comment-o">消息</van-tabbar-item>
        <van-tabbar-item @click="switchIndex(1)" icon="contact">联系人</van-tabbar-item>
      </van-tabbar>
    </div>

    <van-popup v-model="show" position="bottom">
      <div class="add-list-item" @click="search">
        <van-icon name="friends-o" size="25">&nbsp;加好友</van-icon>

      </div>
      <div class="add-list-item" @click="createGroup">
        <van-icon name="chat-o" size="25">&nbsp;创建群组</van-icon>
      </div>

    </van-popup>



  </div>
</template>

<script>
import message from "@/pages/index/message";
import friends from "@/pages/index/friends";
export default {
  name: "index",

  components: {friends, message},
  data() {
    return {
      showPopover: false,
      // 通过 actions 属性来定义菜单选项
      friendList: [],
      active: 0,
      user: {},
      show: false,
      userMsgShow: false,
      showLog:false
    };
  },
  created() {
    this.getUserInfo();
  },

  methods: {
    showUserMsg(){
      this.$router.push("/showUserMsg");
    },


    createGroup(){
      this.$router.push("/createGroup");
    },
    showSheet(){
      this.show = true;
    },
    search(){
      this.$router.push("/search");
    },
    getUserInfo(){
      this.$http.get("/user/getUserInfo").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.user = response.data.response
          this.$store.state.user = this.user;
        }

      })
    },
    switchIndex(val){
      this.active = val;
    }
  },
}
</script>

<style scoped>
.content{
  display: flex;
  flex-direction: column;
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
}
.top .info{
  display: flex;
  align-items: center;
}
.info .info-username{
  margin-left: 10px;
  font-size: 20px;
  font-weight: 600;
}

.add-list-item{
  margin: 20px 0;
  font-size: 20px;
}



</style>
