import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// let store = new Vuex.Store({
//     // 1. state
//     state:{
//         city:"城市名",
//         event:[], //存储websocket传输过来的数据
//     },
//     getters:{
//         getEvent(state){
//             return state.city;
//         }
//     },
//     mutations:{
//         setEvent(state, event){
//             state.event.push(event)
//         }
//     },
// });
//
// export default store;



// 简单store模式管理暂存数据 使各个组件共用一个数据模块
let store = new Vuex.Store({
    state: {
        path:"ws://42.193.125.212:8000/websocket",
        // path:"ws://localhost:8000/websocket",
        socket:"",
        sessions:[],//聊天记录
        username:'',//当前用户用户名
        friendName:'',//私聊朋友用户名
        chatGroup: '', // 当前群聊名字
        chatGroupId: '',
        msgListView: false,// 信息栏隐藏与否
        friendApplication: false // 是否点击了朋友申请
    },
    mutations:{
        init(state) {
            if(typeof(WebSocket) === "undefined"){
                alert("您的浏览器不支持socket")
            }else{

                // alert(state.path)
                // 实例化socket
                state.path = "ws://42.193.125.212:8000/websocket";
                // state.path = "ws://localhost:8000/websocket";
                if(state.friendName !== ''){
                    state.path = state.path + '?username=' + window.localStorage.getItem("username") + '&friendName=' + state.friendName;
                }else if(state.chatGroup !== ''){
                    state.path = state.path + '?username=' + window.localStorage.getItem("username") + '&chatGroup=' + state.chatGroupId;
                }
                state.socket = new WebSocket(state.path,[window.localStorage.getItem('token')])


                // alert(window.sessionStorage.getItem('token'))
                // 监听socket连接
                state.socket.onopen = function () {
                    console.log("socket连接成功")
                }
                // 监听socket错误信息
                state.socket.onerror = function () {
                    console.log("连接错误")
                }
                // 监听socket消息
                state.socket.onmessage = function (msg) {
                    console.log("msg:"+msg.data)
                    state.sessions.push(JSON.parse(msg.data));
                    console.log(state.sessions)
                }
                state.socket.onclose =  function () {
                    console.log("socket已经关闭")
                }

            }
        },

        send:function (state,params) {
            console.log(params)
            // alert(state.friendName)

            // state.path = 'ws://localhost:8000/websocket' + '?username=' + window.sessionStorage.getItem('username') + '&friendName=111';

            state.socket.send(params)
            console.log(state)

        },
        close:function (state){
            console.log(state.socket)
            state.socket.close();
            console.log("socket已经关闭")
        },
        //在这里定义修改helloWorld的方法,到时候调用SET_HW就可以修改了
        setPath:function (state,str) {
            state.path = str
        }
    }



})
export default store
