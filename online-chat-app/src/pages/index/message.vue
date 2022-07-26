<template>
  <div class="centre">
    <div class="msg-item" @click="chat(msgItem)" v-for="msgItem in this.msgList" :key="msgItem.friendOrGroupId">
      <div class="friend-group" >
        <van-image
            width="60"
            height="60"
            :src="msgItem.avatar"
            round
        />
        <div class="msg-content">
          <div class="friend-group-name">{{msgItem.friendOrGroupName}}</div>
          <div class="msg">{{msgItem.lastMsgUser.username}}:&nbsp;{{msgItem.lastMsg}}</div>
        </div>

      </div>
      <div class="msg-info">
        <div class="time">{{msgItem.messageTime.split(" ")[1].substring(0,5)}}</div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
name: "message",
  data(){
    return {
      msgList: [],
      friend: {},
      group: {}
    };

  },
  created() {
    this.getMsgList();
  },
  methods: {
  groupChat(msgItem){
    console.log(msgItem);
    this.$http.get("/group/getGroupMsg",{params:{
        groupId: msgItem.friendOrGroupId
      }}).then(response => {
        // console.log(response)
        if(response.data.response !== null) {
          this.group = response.data.response;
          //获得群组信息
          this.$store.state.chatGroup = msgItem;
          this.$store.state.chatGroupName = msgItem.friendOrGroupName;
          this.$store.state.chatGroupId = msgItem.friendOrGroupId;
          // 清空好友聊天状态
          this.$store.state.friendName = '';

          if(this.$store.state.socket.length !== 0){
            this.$store.commit('close');
          }

          // 初始化websocket
          this.$store.commit('init');
          this.$store.state.sessions = [];
          this.getGroupHistory();
          this.$store.state.focusGroup = msgItem.friendOrGroupId;
          this.$store.state.msgListView = false;
          this.$router.push("/groupChat/" + msgItem.friendOrGroupId)
        }
      })

  },
    friendChat(msgItem){
      // 根据id获得信息
      this.$http.get("/user/searchFriend",{params:{
          userName: msgItem.friendOrGroupName
        }}).then(response => {
        // console.log(response.data.response)
        if(response.data.response !== null) {
          this.friend = response.data.response;
        }
        console.log(this.friend)
        this.$store.state.friendName = this.friend.username;
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
        this.getFriendHistory();
        this.focusFriend = this.friend.id;
        this.$store.state.friendApplication = false;

        this.$router.push('/chat/'+this.friend.id);
      })
    },
// 显示聊天历史信息
    getMsgList(){
      this.$http.get("/user-records/getUserRecords").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.msgList = response.data.response
          // this.$store.state.user = this.user;
        }

      })
    },
    chat(msgItem){
      // 朋友
      console.log(msgItem.msgType);
      if(msgItem.msgType === 0){
        this.friendChat(msgItem);
      }else{
        this.groupChat(msgItem);
        // this.$router.push('/groupChat/'+msgItem.friendOrGroupId);
      }
    },
    getGroupHistory(){
      this.$http.get("/group-msg/getHistory",{
        params:{
          chatGroupId: this.$store.state.chatGroupId
        }
      }).then(response => {
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
    getFriendHistory(){
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
    }
  },


}
</script>

<style scoped>
.centre{
  display: flex;
  flex-direction: column;
  margin-top: 70px;
  margin-bottom: 50px;
  padding: 0 10px;
}
.msg-item{
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.friend-group{
  display: flex;
  align-items: center;
}

.centre .msg-info{
  margin-right: 10px;
}
.msg-info .time{
  color: #ccc;
}
.friend-group .msg-content{
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 10px;
}
.msg-content .friend-group-name{
  font-weight: 600;
  font-size: 20px;
}
.msg-content .msg{
  color: #ccc;
  text-align: left;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 220px;
}
</style>
