<template>
  <div>
    <div class="friend-list">
      <div class="friend-item" @click="chat(friend)" v-for="friend in friendList" :key="friend.friendId">
        <van-image
            width="60"
            height="60"
            :src="friend.userProfile"
            round
        />
        <div class="friend-name" >{{friend.friendUsername}}</div>
      </div>


    </div>
  </div>
</template>

<script>
export default {
  name: "friendContact",
  data() {
    return {
      friendList:[]
    };
  },
  created() {
    this.getFriendList();

  },
  methods:{
    chat(friend){
      this.$store.state.friendName = friend.friendUsername;
      // 清空群组聊天状态
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
      this.focusFriend = friend.id;
      this.$store.state.friendApplication = false;

      this.$router.push("/chat/" + friend.id);
    },
    getHistory(){
      // console.log(this.$route.path.split("/"))
      this.friendUsername = this.$route.path.split("/")[2];

      this.$http.get("/user-records/getHistory",{
        params:{
          friendName: this.$store.state.friendName
        }
      }).then(response => {
        console.log("session");
        console.log(response.data);
        if(response.data.code === 1){

          console.log(response.data.response)
          if((this.$store.state.sessions === undefined || this.$store.state.sessions.length === 0)){
            for(let index in response.data.response){
              this.$store.state.sessions.push(response.data.response[index])
            }
          }

        }


      })
    },
    getFriendList(){
      this.$http.get("/userFriend/getFriendList").then(response => {
        console.log(response)
        this.friendList = response.data.response;
      })

    },
  }
}
</script>

<style scoped>
.friend-item{
  display: flex;
  align-items: center;
  padding: 10px 20px;
}
.friend-item .friend-name{
  margin-left: 20px;
  font-size: 18px;
  font-weight: 600;
}
</style>
