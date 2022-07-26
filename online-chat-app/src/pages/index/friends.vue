<template>
  <div class="centre">
    <van-search v-model="value" placeholder="请输入搜索关键词" />
    <van-tabs v-model="active" animated color="#1989fa">
      <van-tab  title="联系人">
        <friendContract></friendContract>
      </van-tab>
      <van-tab  title="群组">
        <groupContract></groupContract>
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import friendContract from "@/pages/index/friendContract";
import GroupContract from "@/pages/index/groupContract";
export default {
name: "friends",
  components: {GroupContract, friendContract},
  data() {
    return {
      active: 0,
      value: '',
    };
  },
  created() {

  },
  methods:{

  }
}
</script>

<style scoped>
.centre{
  display: flex;
  flex-direction: column;
  margin-top: 70px;
  margin-bottom: 50px;
}
</style>
