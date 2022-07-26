<template>
  <div>
    <img class="logo" src="@/assets/logo.png"/>
    <van-form @submit="onSubmit">
      <van-field
          v-model="loginForm.username"
          name="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="loginForm.password"
          type="password"
          name="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
      <div style="margin: 16px;">
        <van-button @click="onSubmit" round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
    <div @click="register">新用户注册</div>
  </div>
</template>

<script>

export default {

  name: "login",
  data() {
    return {
      loginForm:{
        username: 'naoooo',
        password: '000000',
      }

    };
  },
  methods: {

    register(){
      this.$router.push("/register");
    },
    async onSubmit(values) {
      console.log('submit', values);
      window.localStorage.clear();
      this.$http.post('/user/login',this.loginForm);
      // this.$router.push('/')
      const data = await this.$http.post('/user/login',this.loginForm);
      console.log(data);
      if(data.data.code !== 1){
        this.$toast.fail("登录失败~~")
        return ;
      }
      window.localStorage.setItem('token',data.data.response);
      window.localStorage.setItem('username',this.loginForm.username);
      // this.$store.state.username = this.loginForm.username
      this.$toast.success("登录成功");

      // // 发请求记录登录
      // this.loginSave();
      this.$router.push('/');
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
