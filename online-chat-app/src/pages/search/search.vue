<template>
  <div class="search-body">
    <div class="top">
      <van-icon name="arrow-left" @click="back()"/>
      <div class="chat-name">查找朋友&nbsp;/&nbsp;群</div>
      <van-icon size="30" name="more-o" style="margin-right: 10px;" />

    </div>
    <div class="centre">
      <input @focus="search()" v-model="searchName" placeholder="用户名/群名" type="text"/>

      <div class="application">
        <div class="title">申请好友通知</div>
        <div class="application-item" v-for="item in applications" :key="item.id">
          <div class="friend-info">
            <van-image
                width="60"
                height="60"
                :src="item.userProfile"
                round
            />
            <div class="info-text">
              <div class="info-username">{{item.username}}</div>
              <div class="info-message">{{item.applicationMsg}}</div>
            </div>
          </div>
          <span v-if="item.status">已添加</span>
          <van-button v-else plain type="info" color="#080b12" size="small" @click="agreeApplication(item)">同意</van-button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
name: "search",
  data(){
    return{
      searchName: '',
      applications: []
    };
  },
  created() {
    this.getApplications();
  },
  methods:{
    agreeApplication(item){
      this.$http.put("/application/agreeApplication",{
        applicationId: item.applicationId
      }).then(response => {
        if(response.data.code === 1){
          // 保存朋友关系
          this.addFriend(item.id);
          this.getApplications();
        }
      })
    },
    addFriend(id) {
      this.$http.post("/userFriend/addFriend", {
        friendId: id,
      })
    },
    getApplications(){
      this.$http.get("/application/getApplications").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.applications = response.data.response;
        }
      })
    },
    search(){
      this.$router.push("/searchContent");
    },
    back(){
      this.$router.back();
    }
  }
}
</script>

<style scoped>
.search-body{
  background-color: #f5f5f5;
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
  font-size: 15px;
  color: #03060f;
}
.top .info{
  display: flex;
  align-items: center;
}

.centre{
  margin-top: 70px;
  overflow-y: scroll;
  top: 0;
  bottom: 0;
  position: fixed;
  width: 100%;
}
.centre input{
  background-color: white;
  border: 0;
  width: 90%;
  padding: 10px 0;

  text-align: center;
}
.centre .application{
  padding: 10px 10px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  background: white;
  height: 100%;
}

.application .title{
  font-size: 13px;
  color: #9c9c9c;
}

.application .application-item{
  margin-top: 20px;
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}
.application-item .friend-info{
  display: flex;
  align-items: center;
}
.info-text{
  display: flex;
  margin-left: 20px;
  flex-direction: column;
  align-items: flex-start;
}
.info-text .info-username{

  font-size: 20px;
  font-weight: 600;
  margin-bottom: 5px;
}
.info-text .info-message{
  color: #b4b4c0;
  font-size: 15px;
}
</style>
