<template>
  <div>
    <button @click="send(123)">发消息</button>
  </div>
</template>

<script>
export default {
name: "testWs",
  data () {
    return {
      path:"ws://42.193.125.212:8000/websocket",
      socket:""
    }
  },
  mounted () {
    // 初始化
    this.init()
  },
  methods: {
    init: function () {
      if(typeof(WebSocket) === "undefined"){
        alert("您的浏览器不支持socket")
      }else{
        // 实例化socket
        this.socket = new WebSocket(this.path)
        // 监听socket连接
        this.socket.onopen = this.open
        // 监听socket错误信息
        this.socket.onerror = this.error
        // 监听socket消息
        this.socket.onmessage = this.getMessage
      }
    },
    open: function () {
      console.log("socket连接成功")
    },
    error: function () {
      console.log("连接错误")
    },
    getMessage: function (msg) {
      console.log(msg.data)
    },
    send: function (params) {
      this.socket.send(params)
    },
    close: function () {
      console.log("socket已经关闭")
    }
  },
  destroyed () {
    // 销毁监听
    this.socket.onclose = this.close
  }
}
</script>

<style scoped>

</style>
