<template>
  <div class="header">
    <div class="header1">
      <img style="height:90%" src="../assets/image/logo.png">
      <div class="header4">
        <div class="menu-list">
            <div @click="toPage('index')" :class="$route.path == '/'?'menu_item':''">系统主页</div>
            <div @click="toPage('attractions')" :class="$route.path == '/attractions'?'menu_item':''">景点信息</div>
            <div @click="toPage('line')" :class="$route.path == '/line'?'menu_item':''">旅游线路</div>
            <div @click="toPage('hotel')" :class="$route.path == '/hotel'?'menu_item':''">景区酒店</div>
            <div @click="toPage('forum')" :class="$route.path == '/forum'?'menu_item':''">旅游资讯</div>
            <div @click="toPage('favor')" :class="$route.path == '/favor'?'menu_item':''">我的收藏</div>
            <div @click="toPage('order')" :class="$route.path == '/order'?'menu_item':''">我的预定</div>
            <div @click="toPage('manage')" :class="$route.path == '/manage'?'menu_item':''">后台管理</div>
            <div class="ai-menu" :class="isAIPage?'menu_item':''">
              AI助手
              <div class="ai-dropdown">
                <div @click="toPage('ai')" class="dropdown-item">AI解惑</div>
                <div @click="toPage('recommend')" class="dropdown-item">景点推荐</div>
                <div @click="toPage('itinerary')" class="dropdown-item">行程规划</div>
              </div>
            </div>
        </div>
      </div>
      <div class="header5">
        <img @click="toCenter" style="width:40px;height:40px;border-radius:50%" src="../assets/image/image 2.png">
        <div class="header6" @click="loginOut">
          退出
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return{
        name: ""
      }
    },
    computed: {
      isAIPage() {
        return ['/ai', '/recommend', '/itinerary'].includes(this.$route.path)
      }
    },
    methods: {
      toCenter() {
        this.$router.push("/center")
      },
      toPage(name) {
        if (name == "index") {
          this.$router.push("/")
        } else if (name == "attractions") {
          this.$router.push("/attractions")
        } else if (name == "line") {
          this.$router.push("/line")
        } else if (name == "hotel") {
          this.$router.push("/hotel")
        } else if (name == "forum") {
          this.$router.push("/forum")
        } else if (name == "manage") {
          // 使用同窗口跳转，避免被浏览器拦截弹窗
          window.location.href = "http://localhost:3000"
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "favor") {
          this.$router.push("/favor")
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "ai") {
          this.$router.push("/ai")
        } else if (name == "recommend") {
          this.$router.push("/recommend")
        } else if (name == "itinerary") {
          this.$router.push("/itinerary")
        }
      },
      loginOut() {
        this.$store.dispatch('logout').then(() => {
          window.localStorage.removeItem("user_info")
          window.localStorage.removeItem("user_token")
          this.$message({
              message: '退出成功',
              type: 'success'
          });
          setTimeout(function(){
              window.location.reload()
          },1000)
        })
      }
    },
    created() {
     
    },
    mounted() {
      
    }
 }
</script>

<style scoped>
  .header {
      width: 100%;
      height: 72px;
      display: flex;
      flex-direction: column;
      align-items: center;
      background: linear-gradient(135deg, #1a365d 0%, #2c5282 100%);
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      position: sticky;
      top: 0;
      z-index: 100;
  }
  .header1 {
    width: 90%;
    max-width: 1400px;
    height: 72px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: transparent;
  }
  .header4 {
    flex: 1;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 40px;
  }
  .menu-list {
    display: flex;
    color: rgba(255, 255, 255, 0.9);
    gap: 8px;
  }
  .menu-list>div{
    font-family: 'PingFang SC', '黑体', sans-serif;
    font-size: 15px;
    font-weight: 500;
    padding: 10px 18px;
    cursor: pointer;
    border-radius: 8px;
    transition: all 0.3s ease;
    white-space: nowrap;
  }
  .menu-list>div:hover {
    background-color: rgba(255, 255, 255, 0.15);
    color: #ffffff;
  }
  .menu_item {
    background-color: rgba(255, 255, 255, 0.2) !important;
    color: #ffffff !important;
    font-weight: 600 !important;
  }
  .ai-menu {
    position: relative;
    cursor: pointer;
  }
  .ai-dropdown {
    display: none;
    position: absolute;
    top: calc(100% + 8px);
    left: 50%;
    transform: translateX(-50%);
    background: #ffffff;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
    border-radius: 12px;
    padding: 8px;
    min-width: 140px;
    z-index: 1000;
  }
  .ai-dropdown::before {
    content: '';
    position: absolute;
    top: -6px;
    left: 50%;
    transform: translateX(-50%);
    border-left: 6px solid transparent;
    border-right: 6px solid transparent;
    border-bottom: 6px solid #ffffff;
  }
  .ai-menu:hover .ai-dropdown {
    display: block;
  }
  .dropdown-item {
    padding: 12px 16px;
    font-size: 14px;
    color: #374151;
    transition: all 0.2s ease;
    white-space: nowrap;
    border-radius: 8px;
  }
  .dropdown-item:hover {
    background-color: #f0f9ff;
    color: #1a365d;
  }
  .header5 {
    display: flex;
    align-items: center;
    gap: 12px;
  }
  .header5 img {
    border: 2px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s ease;
  }
  .header5 img:hover {
    border-color: rgba(255, 255, 255, 0.6);
    transform: scale(1.05);
  }
  .header6 {
    width: 72px;
    height: 36px;
    border-radius: 18px;
    color: #1a365d;
    background-color: #ffffff;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }
  .header6:hover {
    background-color: #f0f9ff;
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
</style>
