<template>
  <div class="user-msg">
      <div class="user-info-head" @click="show = true">
        <div class="user-info">
          <van-image
              width="50"
              height="50"
              :src="this.user.userProfile"
              round

          />
          <div class="info-username">{{this.user.username}}</div>
        </div>
        <van-icon name="cross" size="20" @click="back()"/>
      </div>
      <div class="function">
        <div class="function-item" @click="Dialog">
          <div class="function-name">
            <van-icon name="https://b.yzcdn.cn/vant/icon-demo-1126.png" size="20"/>
            <span>注销</span>
          </div>
          <van-icon name="arrow" size="20"/>
        </div>
      </div>
    <van-popup v-model="show">
      <van-image
          width="200"
          height="200"
          round
          :src="this.user.userProfile"
      />
      <van-uploader :after-read="afterRead">
        <van-button block type="info" >更换头像</van-button>
      </van-uploader>
    </van-popup>
  </div>
</template>

<script>
export default {
name: "userMsg",
  data(){
    return{
      user:{},
      show: false
    }
  },
  created() {
    this.getUserInfo();
  },
  methods:{
    back(){
      this.$router.back();
    },
    updateAvatar(){
      this.$http.put("/user/updateUserMsg",
          {
            id: this.user.id,
           userProfile: this.user.userProfile
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
            this.user.userProfile = response.data.response;
            // 更新群组信息
            this.updateAvatar();
          })
    },

    logOut(){

      this.$http.post("/user/loginout").then(response=>{
        if(response.data.code===1){
          window.localStorage.clear();
          this.$router.push('/login')
        }
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
    getUserInfo(){
      this.$http.get("/user/getUserInfo").then(response => {
        console.log(response)
        if(response.data.code === 1){
          this.user = response.data.response
          this.$store.state.user = this.user;
        }

      })
    },
  }
}
</script>

<style scoped>
.user-msg .user-info-head{
  margin-top: 20px;
  display: flex;
  align-items: center;
  padding: 0 10px;
  justify-content: space-between;
}

.user-info-head .user-info{
  display: flex;
  align-items: center;
}

.user-info .info-username{
  font-size: 20px;
  margin-left: 10px;
  font-weight: 600;
}

.function{
  display: flex;
  margin-top: 20px;
  padding: 0 30px;
}

.function .function-item{
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
}

.function-item .function-name{
  display: flex;
  font-size: 20px;
  align-items: center;
}

.function-name span{
  margin-left: 10px;
}
.van-popup{
  background-color: #494949;
}
.van-popup .van-button{
  margin-top: 10px;
}

</style>
