<template>
  <div class="group-msg">
    <div class="top">
      <div class="head">
        <van-icon name="arrow-left" @click="back()"/>
        <div class="chat-name">群聊设置</div>
      </div>
      <div class="info" @click="show = true">
        <div class="info-content">
          <van-image
              width="50"
              height="50"
              :src="this.$store.state.chatGroup.avatar"
              round
          />
          <div class="info-groupName">{{this.$store.state.chatGroupName}}</div>
        </div>
        <van-icon name="arrow" />
      </div>
    </div>

    <div class="centre">
      <div class="group-content-item">
        <div class="group-content-item-head" @click="groupMembers">
          <div class="item-title">群聊成员</div>
          <div class="item-more">
            <span>共{{this.memberNum}}人&nbsp;</span>
            <van-icon name="arrow" />
          </div>
        </div>
        <div class="group-member">
          <div class="group-member-item" v-for="item in memberList" :key="item.id">
            <van-image
                width="50"
                height="50"
                :src="item.userProfile"
                round
            />
            <div class="group-member-name">{{item.username}}</div>
          </div>
          <div class="group-member-item" @click="addGroupMembers()">
            <van-icon name="add-o" size="50" color="#a0a0a0"/>
            <div class="group-member-name" style="color:#a0a0a0">添加</div>
          </div>

        </div>
      </div>
      <div class="group-msg-title">群聊信息</div>
      <div class="group-content-item">
        <div class="group-msgItem" @click="editGroupName">
          <div class="group-msgItem-title">群聊名称</div>
          <div class="group-msgItem-more">
            <span>{{this.$store.state.chatGroupName}} &nbsp;</span>
            <van-icon name="arrow" />
          </div>
        </div>

      </div>
    </div>

    <van-popup v-model="show">
      <van-image
          width="200"
          height="200"
          round
          :src="this.$store.state.chatGroup.avatar"
      />
      <van-uploader :after-read="afterRead">
        <van-button block type="info" @click="changeGroupAvatar">更换头像</van-button>
      </van-uploader>
    </van-popup>

  </div>
</template>

<script>
export default {
name: "groupMsg",
  created() {
    console.log(this.$store.state.chatGroup)
    this.getGroupMembers();
  },
  data(){
    return{
      group: {},
      memberList: [],
      memberNum: 0,
      show: false
    };
  },
  methods: {
    addGroupMembers(){
      this.$router.push("/addGroupMembers")
    },
    groupMembers(){
      this.$router.push("/groupMembers");
    },
    editGroupName(){
      this.$router.push("/editGroupName");
    },
    updateAvatar(){
      this.$http.put("/group/updateGroupMsg",
          {
            id: this.$store.state.chatGroup.friendOrGroupId,
            groupProfile: this.$store.state.chatGroup.avatar
          }).then(response => {
        // console.log(response);
          if(response.data.code === 1){
            this.$toast.success("更改成功");
            this.show = false;
          }else {
            this.$toast.success("更改失败");
          }
        })
    },
    afterRead(file) {
      // 此时可以自行将文件上传至服务器
      console.log(file);
      let formData = new FormData();
      formData.append("image",file.file);
      // 先将图片上传到云端
      this.$http.post("/upload/uploadImg",formData,
          {'Content-type':'multipart/form-data'})
          .then(response =>{
            // console.log(response)
            this.$store.state.chatGroup.avatar = response.data.response;
            // 更新群组信息
            this.updateAvatar();
          })
    },

    Dialog(){
      this.$dialog.confirm({
        title:'注销',
        message:'真的要退出登录吗?',
        confirmButtonColor:'red'
      }).then(()=>{
        // console.log('点击了确认')
        this.logOut()
      }).catch(()=>{
        // console.log('点击了取消')
      })
    },
    changeGroupAvatar(){

    },
    getGroupMembers() {
      console.log(this.$store.state.chatGroup.friendOrGroupId)
      this.$http.get("/group/getGroupMsg", {
        params: {
          groupId: this.$store.state.chatGroup.friendOrGroupId
        }
      }).then(response => {
        console.log(response)
        if (response.data.response !== null) {
          this.group = response.data.response;
          let memberNum = this.group.userVOList.length <= 4 ? this.group.userVOList.length : 4;
          for (let i = 0; i < memberNum; i++) {
            this.memberList.push(this.group.userVOList[i]);
          }
          this.memberNum = this.group.userVOList.length;
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
.group-msg{
  background-color : #f5f5f5;
  height: 100%;
  position: fixed;
  width: 100%
}

.top{
  display: flex;
  /*margin-top: 10px;*/
  padding: 10px 10px;
  background-color: aliceblue;
  position: fixed;
  width: 100%;
  z-index: 1;
  top: 0;
  flex-direction: column;
  align-items: flex-start;
}

.top .info{
  display: flex;
  align-items: center;
  margin-top: 10px;
  width: 100%;
  justify-content: space-between;
}

.info .info-groupName{
  font-size: 18px;
  font-weight: 600;
  margin-left: 10px;
}

.info .info-content{
  display: flex;
  align-items: center;
}

.info .van-icon{
  margin-right: 20px;
}

.head{
  display: flex;
  align-items: center;
  width: 100%;
}

.head .chat-name{
  text-align: center;
  width: 100%;
  font-size: 20px;
  color: #03060f;
  margin-right: 40px;
}

.centre{
  margin-top: 115px;
}

.centre .group-content-item{
  display: flex;
  background-color: aliceblue;
  flex-direction: column;
  align-items: flex-start;
  padding: 10px 10px;
}

.group-content-item .group-content-item-head{
  display: flex;
  width: 100%;
  justify-content: space-between;
}

.group-content-item-head .item-title{
  font-size: 18px;
  font-weight: 600;
}

.group-content-item-head .item-more{
  color: #a0a0a0;
  font-size: 15px;

}

.group-content-item .group-member{
  display: flex;
  width: 100%;
}
.group-member .group-member-item{
  margin-right: 20px;
  margin-top: 10px;
  /*flex: 1 20%;*/
}

.group-member-item .group-member-name{
  font-size: 15px;
  font-weight: 600;
}

.centre .group-msg-title{
  padding: 10px 0px;
  text-align: left;
  margin-left: 10px;
  font-size: 15px;
  color: #adafbb;
}
.group-msgItem {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}
.group-msgItem .group-msgItem-title{
  font-size: 15px;
  font-weight: 600;
}
.group-msgItem .group-msgItem-more{
  color: #a0a0a0;
}

.van-popup{
  background-color: #494949;
}

.van-popup .van-button{
  margin-top: 10px;
}
</style>
