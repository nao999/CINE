<template>
  <div class="group-select">
    <div class="top">
      <div @click="cancel">取消</div>
      <div class="chat-name">创建群聊</div>
    </div>
    <div class="centre">
      <van-search v-model="value" placeholder="请输入搜索关键词" />
      <van-checkbox-group v-model="result" direction="horizontal">

      <div class="friend-list">
        <div>联系人</div>
        <div class="friend-item" v-for="(friend,index) in friendList" :key="friend.id">
          <van-checkbox :name="index"/>
          <van-image
              width="70"
              height="70"
              :src="friend.userProfile"
              round
          />
          <div class="friend-name" >{{friend.friendUsername}}</div>
         </div>


        </div>
      </van-checkbox-group>
    </div>
    <div class="bottom">
      <van-button  :color="buttonColor()" block @click="createGroup">立即创建</van-button>
    </div>
  </div>
</template>

<script>
export default {
name: "group",
  data() {
    return {
      result: [],
      friendList:[],
      selectedFriendList: [],
      groupName: '',
      memberList: [],
      value: ''
    };
  },
  created() {
    this.getFriendList();
  },
  methods:{
    cancel(){
      this.$router.back();
    },
    getFriendList(){
      this.$http.get("/userFriend/getFriendList").then(response => {
        console.log(response)
        this.friendList = response.data.response;
      })

    },
    buttonColor(){
      let res = '#ccebff';
      if(this.result.length !== 0){
        res = '#1989fa';
      }
      return res;
    },
    createGroup(){
      if(this.result.length === 0){
        return ;
      }
      for(let index in this.result){
        this.memberList.push(this.friendList[index]);
      }
      this.groupName = this.friendList[this.result[0]].friendUsername  + '...';
      this.$http.post("/group/createGroup",{
        groupName: this.groupName,
        groupProfile: 'https://nao190830.oss-cn-beijing.aliyuncs.com/img/2022/06/11/20220611112717.png',
        memberList: this.memberList
      }).then(response => {
        // console.log(response)
        if(response.data.code === 1){
          this.$toast.success("创建成功");
          this.$router.push('/');
          // this.refreshGroupList();
        }else {
          // this.$message.error('创建失败');
          this.$toast.fail("创建失败~~")
        }
      });
    }
  }
}
</script>

<style scoped>
.group-select{
  /*display: flex;*/
}
.top{
  display: flex;
  align-items: center;
  /*margin-top: 10px;*/
  padding: 20px 10px;
  background-color: aliceblue;
  position: fixed;
  width: 100%;
  z-index: 1;
  top: 0;
}
.top .chat-name{
  font-size: 20px;
  color: #03060f;
  flex: 0 0 80%;
}
.centre{
  /*display: flex;*/
  /*flex-direction: column;*/
  margin-top: 60px;
  margin-bottom: 50px;
}
.centre .van-search{
  position: fixed;
  right: 0;
  left: 0;
  z-index: 1;
}

.centre .van-checkbox-group{
  margin-top: 20px;
}
.friend-list{
  width: 100%;
  margin-top: 60px;
}
.friend-item{
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 0 20px;
}
.friend-item .van-image{
  margin-right: 10px;
}
.bottom{
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
}
.bottom .van-button{

}
</style>
