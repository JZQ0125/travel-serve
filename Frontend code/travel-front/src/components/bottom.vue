<template>
  <div class="bottom">
    <div class="bottom-content">
      <div class="bottom1">
        <img src="../assets/image/logo.png" style="height: 50px; border-radius: 8px;">
        <div class="bottom-brand">旅游信息推荐系统</div>
        <div class="bottom-desc">发现美好旅途，探索精彩世界</div>
      </div>
      <div class="bottom2">
        <div class="bottom-title">关于我们</div>
        <div class="bottom-text">致力于为用户提供优质的旅游信息服务，包括景点查询、路线规划、酒店预订等一站式旅游解决方案。</div>
      </div>
      <div class="bottom3">
        <div class="bottom-title">联系方式</div>
        <div class="bottom-item">客服邮箱: service@travel.com</div>
        <div class="bottom-item">服务热线: 400-888-8888</div>
        <div class="bottom-item">工作时间: 9:00 - 18:00</div>
      </div>
    </div>
    <div class="bottom-copyright">
      Copyright 2024 旅游信息推荐系统 All Rights Reserved
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return{
        
      }
    },
    methods: {
      toPage(name) {
        if (name == "index") {
          this.$router.push("/")
        } else if (name == "vegetable") {
          this.$router.push("/vegetable")
        } else if (name == "information") {
          this.$router.push("/information")
        } else if (name == "shed") {
          this.$router.push("/shed")
        } else if (name == "leave") {
          this.$router.push("/leave")
        } else if (name == "manage") {
          window.open("http://localhost:3000")
        } else if (name == "car") {
          this.$router.push("/car")
        } else if (name == "favor") {
          this.$router.push("/favor")
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "appointment") {
          this.$router.push("/appointment")
        }
      }
    },
 }
</script>

<style scoped>
  .bottom {
      margin-top: 60px;
      width: 100%;
      background: linear-gradient(135deg, #1a365d 0%, #2c5282 100%);
      font-family: 'PingFang SC', '黑体', sans-serif;
  }
  .bottom-content {
      max-width: 1400px;
      margin: 0 auto;
      padding: 60px 40px 40px;
      display: flex;
      justify-content: space-between;
      gap: 60px;
  }
  .bottom1 {
    flex: 1;
  }
  .bottom-brand {
    font-size: 20px;
    font-weight: 600;
    color: #ffffff;
    margin-top: 16px;
  }
  .bottom-desc {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    margin-top: 8px;
  }
  .bottom2, .bottom3 {
    flex: 1;
  }
  .bottom-title {
    font-size: 16px;
    font-weight: 600;
    color: #ffffff;
    margin-bottom: 20px;
    position: relative;
    padding-bottom: 12px;
  }
  .bottom-title::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 40px;
    height: 2px;
    background-color: rgba(255, 255, 255, 0.5);
  }
  .bottom-text {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    line-height: 1.8;
  }
  .bottom-item {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.7);
    margin-bottom: 12px;
    transition: color 0.3s ease;
  }
  .bottom-item:hover {
    color: #ffffff;
  }
  .bottom-copyright {
    border-top: 1px solid rgba(255, 255, 255, 0.1);
    text-align: center;
    padding: 20px;
    font-size: 13px;
    color: rgba(255, 255, 255, 0.5);
  }
</style>
