<template>
  <div id="list">
    <ul>
      <p style="padding: 2px 4px;height: 20px;text-align: left;font-weight: 600">用户列表</p>
      <li :class="selectFocus('newFriend')" style="display: flex;align-items: center;"  @click="friendApplication()">
          <svg class="icon" aria-hidden="true" style="height: 30px;width: 30px">
            <use xlink:href="#icon-xindepengyou"></use>
          </svg>

        <p class="name">新的朋友</p>
      </li>
      <li :class="selectFocus(item.friendUsername)" style="display: flex;align-items: center;" v-for="item in friendList" :key="item.id" @click="chatFriend(item.friendUsername)">
        <el-badge is-dot="dd"><img class="avatar" :src="item.userProfile"></el-badge>
        <p class="name">{{item.friendUsername}}</p>
      </li>

    </ul>
  </div>
</template>

<script>
import bus from "@/event/event";
export default {
name: "list",
  data(){
    return{
      friendList: [],
      focusFriend: ''
    }
  },
  created() {
    this.getFriendList();
  },
  mounted() {
    bus.$on('refreshFriendList',() => {
      this.getFriendList();
    })

  },
  methods:{
    friendApplication(){
      this.$store.state.friendApplication = true;
      this.$store.state.friendName = '新的朋友'
      this.focusFriend = 'newFriend';
    },
    selectFocus(friendName){
      if(friendName === this.focusFriend){
        return 'focus-friend';
      }
      return '';
    },
    chatFriend(friendName){
      this.$store.state.friendName = friendName;
      this.$store.state.chatGroup = '';
      // 完成好友名字状态保存再建立websocket
      console.log(this.$store.state.socket)


      if(this.$store.state.socket.length !== 0){
        this.$store.commit('close');

      }

      // 初始化websocket
      this.$store.commit('init');
      this.$store.state.sessions = [];
      this.getHistory();
      this.focusFriend = friendName;
      this.$store.state.friendApplication = false;

    },

    getFriendList(){
      this.$http.get("/userFriend/getFriendList").then(response => {
        console.log(response)
        this.friendList = response.data.response;
      })
    },
    getHistory(){
      this.$http.get("/user-records/getHistory",{
        params:{
          friendName: this.$store.state.friendName
        }
      }).then(response => {
        console.log("session");
        console.log(response.data);
        if(response.data.code === 1){
          if((this.$store.state.sessions === undefined || this.$store.state.sessions.length === 0)){
            for(let index in response.data.response){
              this.$store.state.sessions.push(response.data.response[index])
            }
          }

        }


      })
    },
  }

}
</script>

<style scoped>
#list{
  height: 91%;
  overflow-x: hidden;
  overflow-y: scroll;
}
ul{
  margin-left: 0px;
  padding-left: 0px;
  margin-left: 2px;
}
li {
  padding-top: 14px;
  padding-bottom: 14px;
/*padding-right: 40px;*/
/*//border-bottom: 1px solid #292C33;*/
  list-style: none;
  cursor: pointer;

}
li:hover {
  background-color: #D8D6D6;
}
/*选中的朋友*/
.focus-friend{
  background-color: #D8D6D6;
}

.avatar {
  border-radius: 2px;
  width: 30px;
  height: 30px;
  vertical-align: middle;
  float: left;
}
.name {
  display: inline-block;
  margin-left: 15px;
  margin-top: 0px;
  margin-bottom: 0px;
}
</style>
