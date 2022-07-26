<template>
  <div>
    <img class="logo" @click="uploadImg" src="@/assets/logo.png"/>
    <van-form @submit="onSubmit">
      <van-field
          v-model="registerForm.username"
          name="用户名"
          label="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="registerForm.nickname"
          name="昵称"
          label="昵称"
          placeholder="昵称"
          :rules="[{ required: true, message: '请填写昵称' }]"
      />
      <van-field
          v-model="registerForm.password"
          type="password"
          label="密码"
          name="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
      <div style="margin: 16px;">
        <van-button @click="register" round block type="info" native-type="submit">注册</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
export default {
name: "register",
  data() {
    return {
      registerForm:{
        username: '',
        nickname: '',
        password: '',
        userProfile: 'http://nao190830.oss-cn-beijing.aliyuncs.com/20220613/837166c61878495292f8d72dcfb1cea7.png'
      }
    };
  },
  methods: {
    register(){
      this.$http.post("/user/register",this.registerForm)
          .then(response => {
            console.log(response)
            if(response.data.code === 1){
              this.$toast.success("注册成功");
              this.$router.push("/login");
            }else{
              this.$toast.fail("注册失败~~")
            }
          })
    },
    uploadImg(){

    },
    onSubmit(values) {
      console.log('submit', values);
    },
  },
}
</script>

<style scoped>
.content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.logo {
  height: 150px;
  width: 150px;
  margin-left: auto;
  margin-right: auto;
}

.van-field{
  background-color: aliceblue;
  /*padding: 10px 50px;*/
  /*border-radius: 10%;*/
  margin-bottom: 20px;
}

</style>
