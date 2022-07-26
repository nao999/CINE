<template>
  <div>
    <div class="group-list">
      <div class="group-item" @click="chatGroup(item)" v-for="item in this.groupList" :key="item.id">
        <van-image
            width="60"
            height="60"
            :src="item.groupProfile"
            round
        />
        <div class="group-name">{{item.groupName}}</div>
      </div>
<!--      <div class="group-item">-->
<!--        <van-image-->
<!--            width="70"-->
<!--            height="70"-->
<!--            src="https://nao190830.oss-cn-beijing.aliyuncs.com/img/2022/06/11/20220611112717.png"-->
<!--            round-->
<!--        />-->
<!--        <div class="group-name">土木工程交流群</div>-->
<!--      </div>-->


    </div>
  </div>
</template>

<script>
export default {
  name: "groupContract",
  data() {
    return {
      groupList:[]
    }
  },
  created() {
    this.getGroupList();
    // console.log(this.groupList)
  },
  methods:{
    getGroupList(){
      this.$http.get('/group/getGroupList').then(response => {
        console.log(response);
        if(response.data.code === 1){
          this.groupList = response.data.response;
        }
      })
    },
    getHistory(){
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
    chatGroup(item){
      this.$store.state.chatGroup = item;
      this.$store.state.chatGroupName = item.groupName;
      this.$store.state.chatGroupId = item.id;
      // 清空好友聊天状态
      this.$store.state.friendName = '';

      if(this.$store.state.socket.length !== 0){
        this.$store.commit('close');
      }

      // 初始化websocket
      this.$store.commit('init');
      this.$store.state.sessions = [];
      this.getHistory();
      this.$store.state.focusGroup = item.id;
      this.$store.state.msgListView = false;
      this.$router.push("/groupChat/" + item.id)
    }
  }
}
</script>

<style scoped>
.group-item{
  display: flex;
  align-items: center;
  padding: 0 20px;
}
.group-item .group-name{
  margin-left: 20px;
  font-size: 18px;
  font-weight: 600;
}
</style>
