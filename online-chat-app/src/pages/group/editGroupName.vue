<template>
  <div class="content">
    <div class="top">
      <van-icon name="arrow-left" @click="back()"/>
      <div class="title">编辑群聊名称</div>
      <div class="submit" @click="updateGroupName">提交</div>

    </div>
    <div class="centre">
      <van-cell-group>
        <van-field v-model="groupName" placeholder="请输入用户名" />
      </van-cell-group>
    </div>
  </div>
</template>

<script>
export default {
name: "editGroupName",
  data(){
   return{
     groupName: this.$store.state.chatGroupName
   }
  },
  methods:{
    updateGroupName(){
      this.$store.state.chatGroupName = this.groupName;
      this.$store.state.chatGroup.friendOrGroupName = this.groupName;
      this.$http.put("/group/updateGroupMsg",{
        id: this.$store.state.chatGroup.friendOrGroupId,
        groupName: this.groupName
      }).then(response => {
        // console.log(response);
        if(response.data.code === 1){
          this.$toast.success("更改成功");
          this.back();
        }else {
          this.$toast.success("更改失败");
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
}

.van-cell{
  background-color: aliceblue;

}
</style>
