<template>
  <div class="content">
    <div class="top">
      <van-icon name="arrow-left" @click="back()"/>
      <div class="title">群聊成员</div>
      <van-icon name="plus" style="margin-right: 20px;font-size: 20px"/>

    </div>
    <div class="centre">
      <div class="member-item" v-for="item in memberList" :key="item.id">
        <div class="member-item-info">
          <van-image
              width="50"
              height="50"
              :src="item.userProfile"
              round
          />
          <div class="info-groupName">{{item.username}}</div>
        </div>
        <div class="member-item-notes" v-if="judgeSelf(item.username) === 1">我</div>
        <div class="member-item-notes" v-else-if="!isFriend(item.id)">
          <van-button type="default">加好友</van-button>
        </div>
        <div class="member-item-notes"  v-else-if="isFriend(item.id) && (judgeSelf(item.username) !== 1)"></div>

      </div>

    </div>
  </div>
</template>

<script>
export default {
name: "groupMembers",
  data(){
    return{
      memberList:[]
    }
  },
  created() {
    this.getGroupMembers()
  },
  methods:{
    async isFriend(friendId) {
      let res = await this.$http.get("/userFriend/isFriend", {
        params: {
          friendId: friendId
        }
      });
      console.log(res.data);
      if(res.data.code === 1){
        return res.data.response.isFriend;
      }
    },
    judgeSelf(username){
      let name = window.localStorage.getItem('username');
      if(name === username){

        return 1;
      }
      return 0;
    },
    getGroupMembers(){
      this.$http.get("/group/getGroupMsg", {
        params: {
          groupId: this.$store.state.chatGroup.friendOrGroupId
        }
      }).then(response => {
        console.log(response)
        if (response.data.response !== null) {
          this.memberList = response.data.response.userVOList;
        }
      })
    },
    back() {
      this.$router.go(-1);
    }
  }
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

.top .title{
  font-size: 20px;
  color: #03060f;
}

.top .submit{
  margin-right: 20px;
  font-size: 18px;
  color: #03060f;
}
.content .centre{
  margin-top: 60px;
  display: flex;
  flex-direction: column;
}

.centre .member-item{
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  margin-bottom: 10px;
}
.member-item .member-item-info{
  display: flex;
  align-items: center;
}
.member-item-info .info-groupName{
  margin-left: 10px;
}
</style>
