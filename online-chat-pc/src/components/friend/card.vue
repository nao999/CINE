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
    <el-button class="searchBtn" @click="showFriendDialog()" size="mini"><i class="iconfont icon-jiahao" aria-hidden="true"></i></el-button>
    </div>

    <el-dialog
        title="添加好友"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
     <div class="search-item">
      <div class="search-friend">
       <el-input v-model="userName"  placeholder="请输入用户名"></el-input>
       <el-button icon="el-icon-search" @click="searchFriend()" circle></el-button>
      </div>
       <div class="search-result"  v-if="friend.userProfile !== ''">

         <div class="application-content">

           <div class="friend-msg">
             <el-image class="imgProfile"
                       :src="friend.userProfile"
             ></el-image>
             <span class="friend-username">{{friend.username}}</span>
           </div>
           <div class="verification">
             <div style="margin-bottom: 10px;text-align: left">输入验证信息：</div>
             <el-input
                 type="textarea"
                 :autosize="{ minRows: 4, maxRows: 8}"
                 placeholder="请输入内容"
                 v-model="applicationMsg"
                 style="margin-bottom: 10px;width: 150%"
             >
             </el-input>
           </div>
         </div>
         <el-button type="primary" style="float: right;" @click="addFriend()" size="mini">+好友</el-button>
       </div>
       <div class="search-result" v-else>

         <span class="no-result">{{searchErr}}</span>
       </div>
     </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
name: "card",
  data(){
    return{
      dialogVisible: false,
      userName: '',
      friend:{
        id: '',
        userProfile: '',
        username: ''
      },
      searchErr: '',
      applicationMsg: ''
    }
  },
  created() {

  },
  methods:{
    addFriend(){
      this.$http.post("/application/saveApplication",{
        applicationFriendId: this.friend.id,
        applicationMsg: this.applicationMsg
      }).then(response=>{
        console.log(response)
        if(response.data.code === 1){
          this.$message({
            message: '好友申请成功',
            type: 'success'
          });
          this.dialogVisible = false;
        }else{
          this.$message.error('好友申请失败');
        }
      })
    },
    // 添加朋友
    showFriendDialog(){
      this.dialogVisible = true;
    },
    searchFriend(){
      this.$http.get("/user/searchFriend",{params:{
          userName: this.userName
        }}).then(response =>{
          // console.log(response.data.response)
        if(response.data.response !== null){
          this.friend = response.data.response;
        }else{
          this.friend = {
            id: '',
            userProfile: '',
            username: ''
          };
          this.searchErr = '未搜索到该用户';
        }
      })
    }
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

.search-item{
  display: flex;
  align-items: stretch;
  flex-direction: column;
}

.search-friend .el-input{
  background-color: white;
  margin-right: 10px;
}

.search-friend{
  display: flex;
  padding: 10px;
}

.search-result{
  margin-left: 20px;
  padding: 10px;
}

.search-result .el-image{
  width: 80px;
  border-radius: 100%;
}

.search-result .no-result{
  margin-left: 10px;
  font-size: 20px;
  font-weight: 600;
}

.application-content{
  display: flex;

}

.friend-msg{
  display: flex;
  flex-direction: column;
  align-items: center;
}


.friend-msg .friend-username{
  margin-left: 10px;
  font-size: 20px;
  font-weight: 600;
  margin-top: 5px;
  margin-bottom: 5px;
}

.verification{
  margin-left: 20px;
}
</style>
<style>
/*当前组件的el-input样式设置*/
.el-input__inner {
  background-color: #DBD9D8;
  outline: none;/*鼠标点击后不会出现蓝色边框*/
}
</style>
