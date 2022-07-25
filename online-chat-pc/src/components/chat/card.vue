<template>
  <div id="card">
    <header>
      <!--  		<img class="avatar" :src="user.userProfile" :alt="user.nickname">-->
      <!--  		<p class="name">{{user.nickname}}</p>-->
    </header>
    <footer>

    </footer>
    <div style="display:flex;">
    <el-input class="search" type="text" size="mini"  placeholder="搜索" prefix-icon="el-icon-search"></el-input>
    <el-button class="searchBtn" size="mini"><i class="iconfont icon-jiahao" aria-hidden="true" @click="createGroup()"></i></el-button>
    </div>

    <el-dialog
        title="添加群组"
        :visible.sync="dialogVisible"
        width="50%"
        :before-close="handleClose">
      <div class="createGroup-item">
        <div class="createGroup-friend">
          <div class="createGroup-search">
            <input type="text" name="lname" v-model="search" placeholder="进行搜索"/>
            <el-button icon="el-icon-search" @click="searchFriend()" circle></el-button>
          </div>
          <div class="friend-list">

            <div class="friend-item" @click="saveChooseFriend(item)"
                 v-for="item in friendList" :key="item.id">
              <div class="friend-info">
                <el-avatar shape="square" :src="item.userProfile"/>
                <span class="friend-name">{{item.friendUsername}}</span>
              </div>
              <el-switch
                  :value="chooseFriend(item.id)"
              >
              </el-switch>
            </div>

          </div>
        </div>
        <div class="createGroup-result"  >
          <div>
          <div style="margin-bottom: 20px">已选联系人：{{friendChooseList.length}}</div>
          <div class="choose-friend" v-for="item in friendChooseList" :key="item.id">

            <div class="friend-info">
              <el-avatar shape="square" :src="item.userProfile"/>
              <span class="friend-name">{{item.friendUsername? item.friendUsername : item.username}}</span>
            </div>
            <el-button type="text"><i class="iconfont icon-guanbi" style="font-size: 20px;color: #dadada" ></i></el-button>

          </div>
          </div>


        </div>

      </div>
      <div>
        <input v-model="groupName" style="width: 80%;margin-bottom: 10px;margin-top: 10px;height: 30px;" placeholder="请输入群名称"/>
        <div style="padding: 10px;text-align: right;">
          <el-button size="mini" plain @click="saveGroup">确定</el-button>
          <el-button size="mini"   plain @click="closeDialog">取消</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import bus from "@/event/event";

export default {
name: "card",
  data(){
    return{
      dialogVisible: false,
      search: '',
      friendChooseList: [],
      friendList: [],
      groupName: ''
    }
  },
  created() {
    this.getUserInfo();

  },
  methods:{
    refreshGroupList(){
      bus.$emit('refreshGroupList');
      // console.log("sss")
    },
    closeDialog(){
      this.dialogVisible = false;
    },
    getUserInfo(){
      this.$http.get("/user/getUserInfo").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.user = response.data.response
          // 将user格式统一
          let user = {
            id: this.user.id,
            username: this.user.username,
            friendId: null,
            friendUsername: null,
            userProfile: this.user.userProfile,
          };
          this.friendChooseList.push(user)
        }

      })
    },

    saveGroup(){
      this.$http.post("/group/createGroup",{
        groupName: this.groupName,
        memberList: this.friendChooseList
      }).then(response => {
        // console.log(response)
        if(response.data.code === 1){
          this.$message({
            message: '创建群组成功',
            type: 'success'
          });
          this.dialogVisible = false;
          this.refreshGroupList();
        }else {
          this.$message.error('创建失败');
        }
      })
    },
    saveChooseFriend(friend){
      let index = this.friendChooseList.findIndex(item => {
        return item.id === friend.id;
      })
      if(index !== -1){
        this.friendChooseList.splice(index,1);
      }else{
        this.friendChooseList.push(friend);
      }
    },
    // 判断是否选中
    chooseFriend(id){
      let index = this.friendChooseList.findIndex(item => {
        return item.id === id;
      })

      return index !== -1;
    },
    // 添加朋友
    createGroup(){
      this.dialogVisible = true;
      // 查询好友
      this.getFriendList();
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
#card {
  padding: 12px;
  margin-bottom: 5px;
}

.avatar{
  width: 40px;
  height: 40px;
  vertical-align: middle;/*这个是图片和文字居中对齐*/
}
.name {
  display: inline-block;
  padding: 10px;
  margin-bottom: 15px;
  font-size: 16px;
}
.search {
  background-color: #DBD9D8;
  width: 150px;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  border: 0;
  border-radius: 4px;
  outline: none;/*鼠标点击后不会出现蓝色边框*/
  color: #FFF;
}
.searchBtn{
  background-color: #DBD9D8;
  border: 0;
  margin-left: 3px;
}


.createGroup-item{
  display: flex;
  flex-direction: row;
}
.createGroup-friend{
  width: 50%;
  border-right: 1px solid #eee;


}

.createGroup-search{
  display: flex;
  margin-bottom: 10px;
  padding: 10px;
}

.createGroup-search input{
  background-color: white;
  margin-right: 10px;
  width: 80%;
}

.friend-list{
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  height: 300px;
}

.friend-info{
  display: flex;
  align-items: center;
}
.friend-info .friend-name{
  margin-left: 10px;
  font-weight: 600;
  font-size: 15px;
}

.friend-item{
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 10px 0;
}

.friend-item:hover{
  background-color: #eee;
}

/*选择的好友*/
.createGroup-result{
  display: flex;
  width: 50%;
  flex-direction: column;
  justify-content: space-between;
}

.choose-friend{
  display: flex;
  align-items: center;
  justify-content: space-around;
  padding: 10px 0;
}

.choose-friend .friend-name{
  margin-left: 10px;
  font-weight: 600;
  font-size: 15px;
}
</style>
<style>
/*当前组件的el-input样式设置*/
.el-input__inner {
  background-color: #DBD9D8;
  outline: none;/*鼠标点击后不会出现蓝色边框*/
}
.el-dialog__body{
  padding: 0;
}
</style>
