<template>
  <div id="application">
    <div class="application-item" v-for="item in applications" :key="item.id">
      <div class="friend-info">
        <el-avatar shape="square" :size="50" :src="item.userProfile"/>
        <div class="info-name">
          <span style="margin-bottom: 10px">{{item.username}}</span>
          <span style="color: #7f93bc;font-size: 10px">{{item.applicationMsg}}</span>
        </div>
      </div>
      <div>
        <span v-if="item.status">已添加</span>
        <el-button size="mini" v-else @click="agreeApplication(item)">同意</el-button>
      </div>
<!--      <el-button @click="refreshFriendList">test</el-button>-->
    </div>

  </div>
</template>

<script>
import bus from "@/event/event";
export default {
name: "application",
  created() {
    this.getApplications()
  },
  data(){
    return{
      applications: []
    }
  },
  methods:{
    refreshFriendList(){
      bus.$emit('refreshFriendList');
      // console.log("sss")
    },
    addFriend(id) {
      this.$http.post("/userFriend/addFriend", {
        friendId: id,
      })
    },
    agreeApplication(item){
      this.$http.put("/application/agreeApplication",{
        applicationId: item.applicationId
      }).then(response => {
        if(response.data.code === 1){
          // 保存朋友关系
          this.addFriend(item.id);
          this.getApplications();
          this.refreshFriendList();
        }
      })
    },
    getApplications(){
      this.$http.get("/application/getApplications").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.applications = response.data.response;
        }
      })
    }
  }
}
</script>

<style scoped>
.application-item{
  display: flex;
  padding: 10px 100px;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid white;
}
.friend-info{
  display: flex;
  align-items: center;
}
.info-name{
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}
</style>
