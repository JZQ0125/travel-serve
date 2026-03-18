<template>
  <div class="bottom">
    <div class="bottom-content">
      <div class="bottom1">
        <div class="bottom-logo">
          <div class="bottom-logo-icon">
            <i class="el-icon-position"></i>
          </div>
          <span class="bottom-logo-text">旅途</span>
        </div>
        <div class="bottom-desc">发现美好旅途，探索精彩世界</div>
        <div class="bottom-social">
          <div class="social-icon"><i class="el-icon-chat-dot-round"></i></div>
          <div class="social-icon"><i class="el-icon-phone"></i></div>
          <div class="social-icon"><i class="el-icon-message"></i></div>
        </div>
      </div>
      <div class="bottom2">
        <div class="bottom-title">快速链接</div>
        <div class="bottom-link">景点信息</div>
        <div class="bottom-link">旅游路线</div>
        <div class="bottom-link">景区酒店</div>
        <div class="bottom-link">旅游资讯</div>
      </div>
      <div class="bottom3">
        <div class="bottom-title">联系我们</div>
        <div class="bottom-item"><i class="el-icon-message"></i> service@travel.com</div>
        <div class="bottom-item"><i class="el-icon-phone"></i> 400-888-8888</div>
        <div class="bottom-item"><i class="el-icon-time"></i> 9:00 - 18:00</div>
      </div>
    </div>
    <div class="bottom-copyright">
      Copyright 2024 旅途 All Rights Reserved
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
      margin-top: 0;
      width: 100%;
      background: linear-gradient(135deg, #0f172a 0%, #1e3a5f 100%);
      font-family: 'PingFang SC', -apple-system, sans-serif;
  }
  .bottom-content {
      max-width: 1200px;
      margin: 0 auto;
      padding: 60px 40px 40px;
      display: flex;
      justify-content: space-between;
      gap: 80px;
  }
  .bottom1 {
    flex: 1.2;
  }
  .bottom-logo {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 16px;
  }
  .bottom-logo-icon {
    width: 40px;
    height: 40px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .bottom-logo-icon i {
    font-size: 20px;
    color: #ffffff;
  }
  .bottom-logo-text {
    font-size: 22px;
    font-weight: 700;
    color: #ffffff;
    letter-spacing: 2px;
  }
  .bottom-desc {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.6);
    margin-bottom: 20px;
    line-height: 1.6;
  }
  .bottom-social {
    display: flex;
    gap: 12px;
  }
  .social-icon {
    width: 36px;
    height: 36px;
    background: rgba(255, 255, 255, 0.1);
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  .social-icon:hover {
    background: rgba(255, 255, 255, 0.2);
  }
  .social-icon i {
    font-size: 16px;
    color: #ffffff;
  }
  .bottom2, .bottom3 {
    flex: 1;
  }
  .bottom-title {
    font-size: 15px;
    font-weight: 600;
    color: #ffffff;
    margin-bottom: 24px;
    text-transform: uppercase;
    letter-spacing: 1px;
  }
  .bottom-link {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.6);
    margin-bottom: 14px;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  .bottom-link:hover {
    color: #ffffff;
    padding-left: 8px;
  }
  .bottom-item {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.6);
    margin-bottom: 14px;
    display: flex;
    align-items: center;
    gap: 10px;
  }
  .bottom-item i {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.4);
  }
  .bottom-copyright {
    border-top: 1px solid rgba(255, 255, 255, 0.08);
    text-align: center;
    padding: 24px;
    font-size: 13px;
    color: rgba(255, 255, 255, 0.4);
  }
</style>
