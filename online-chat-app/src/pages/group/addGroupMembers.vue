<template>
  <div class="group-select">
    <div class="top">
      <div @click="cancel">取消</div>
      <div class="chat-name">邀请新成员</div>
    </div>
    <div class="centre">
      <van-search v-model="value" placeholder="请输入搜索关键词" />
      <van-checkbox-group v-model="result" direction="horizontal">

        <div class="friend-list">
          <div>联系人</div>
          <div class="friend-item" v-for="(friend,index) in friendList" :key="friend.id">
            <van-checkbox :name="index" :disabled="friend.groupMember"/>
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
      <van-button  :color="buttonColor()" block @click="addFriendGroup">立即邀请</van-button>
    </div>
  </div>
</template>

<script>
export default {
name: "addGroupMembers",
  data() {
    return {
      result: [],
      friendList:[],
      selectedFriendList: [],
      groupName: '',
      memberList: [],
      groupMemberNum: 0,
      value: ''
    };
  },
  created() {
    this.getFriendList();
  },
  mounted() {
    // check
    // this.isGroupMember();
  },
  methods:{
    async isGroupMember(){
      // console.log(this.friendList)
     for(let index in this.friendList) {
       let res = await this.$http.get("/group/isGroupMember", {
         params: {
           groupId: this.$store.state.chatGroup.friendOrGroupId,
           friendId: this.friendList[index].friendId
         }
       });
       if(res.data.code === 1){
         //   // console.log(res)
           console.log(!res.data.response.isGroupMember)
           this.groupMemberList[index] = !res.data.response.isGroupMember;
         }
     }
     // console.log(res);
     // if(res.data.code === 1){
     //   // console.log(res)
     //   console.log(!res.data.response.isGroupMember)
     //   return !res.data.response.isGroupMember;
     // }
     //  let res0 = false;
     //  console.log(res0)
    },
    cancel(){
      this.$router.back();
    },
    getFriendList(){
      this.$http.get("/userFriend/getGroupsFriendList",{
        params:{
          groupId: this.$store.state.chatGroup.friendOrGroupId
        }
      }).then(response => {
        console.log(response)
        this.friendList = response.data.response;
        // 群组成员加入选中
        for(let index in this.friendList){
          if(this.friendList[index].groupMember){
            this.result.push(parseInt(index));
            this.groupMemberNum += 1 ;
          }
        }
      })

    },
    buttonColor(){
      let res = '#ccebff';
      if(this.result.length - this.groupMemberNum !== 0){
        res = '#1989fa';
      }
      return res;
    },
    addFriendGroup(){
      if(this.result.length - this.groupMemberNum === 0){
        return ;
      }
      for(let index in this.result){
        if(index < this.groupMemberNum){
          continue;
        }
        this.memberList.push(this.friendList[this.result[index]]);
      }
      this.$http.post("/group/addFriendsGroup",{
        id: this.$store.state.chatGroup.friendOrGroupId,
        memberList: this.memberList
      }).then(response => {
        // console.log(response)
        if(response.data.code === 1){
          this.$toast.success("邀请成功");
          this.cancel();
          // this.refreshGroupList();
        }else {
          // this.$message.error('创建失败');
          this.$toast.fail("邀请失败~~")
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
