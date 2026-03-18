<template>
  <div class="index">
    <headers></headers>
    
    <!-- Hero Section with Background Image -->
    <div class="hero">
      <img class="hero-bg" src="../../assets/image/hero-bg.jpg" alt="hero background">
      <div class="hero-overlay"></div>
      <div class="hero-content">
        <span class="hero-badge">探索无限可能</span>
        <h1 class="hero-title">发现世界的美好</h1>
        <p class="hero-subtitle">精选优质目的地，为您规划完美旅程</p>
        <div class="hero-actions">
          <div class="hero-btn primary" @click="toAttraction">
            <i class="el-icon-search"></i>
            探索景点
          </div>
          <div class="hero-btn secondary" @click="toLine">
            <i class="el-icon-map-location"></i>
            查看路线
          </div>
        </div>
      </div>
      <div class="hero-scroll">
        <i class="el-icon-arrow-down"></i>
      </div>
    </div>

    <!-- Stats Banner -->
    <div class="stats-banner">
      <div class="stats-container">
        <div class="stat-item">
          <div class="stat-icon"><i class="el-icon-user"></i></div>
          <div class="stat-info">
            <div class="stat-number">{{count || '10000'}}+</div>
            <div class="stat-label">累计游客</div>
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-icon"><i class="el-icon-location-outline"></i></div>
          <div class="stat-info">
            <div class="stat-number">{{attractions.length || '50'}}+</div>
            <div class="stat-label">精选景点</div>
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-icon"><i class="el-icon-map-location"></i></div>
          <div class="stat-info">
            <div class="stat-number">{{line.length || '30'}}+</div>
            <div class="stat-label">旅游路线</div>
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-icon"><i class="el-icon-medal"></i></div>
          <div class="stat-info">
            <div class="stat-number">99%</div>
            <div class="stat-label">好评率</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Featured Destinations with Images -->
    <div class="featured-section">
      <div class="section-container">
        <div class="section-header">
          <span class="section-tag">热门目的地</span>
          <h2 class="section-title">精选景点推荐</h2>
          <p class="section-desc">发现令人心动的旅行目的地</p>
        </div>
        
        <div class="featured-grid">
          <!-- Large Featured Card -->
          <div class="featured-card large" @click="toInfo(attractions[0]?.id)" v-if="attractions[0]">
            <img :src="attractions[0].images.split(',')[0]" :alt="attractions[0].name">
            <div class="featured-overlay"></div>
            <div class="featured-content">
              <span class="featured-tag">热门推荐</span>
              <h3>{{attractions[0].name}}</h3>
              <p>{{attractions[0].introduce}}</p>
              <span class="featured-link">了解更多 <i class="el-icon-arrow-right"></i></span>
            </div>
          </div>
          
          <!-- Default Large Card if no data -->
          <div class="featured-card large" v-else>
            <img src="../../assets/image/attraction1.jpg" alt="景点">
            <div class="featured-overlay"></div>
            <div class="featured-content">
              <span class="featured-tag">热门推荐</span>
              <h3>探索神秘古迹</h3>
              <p>感受千年文化的魅力</p>
              <span class="featured-link">了解更多 <i class="el-icon-arrow-right"></i></span>
            </div>
          </div>
          
          <!-- Small Featured Cards -->
          <div class="featured-small-grid">
            <div class="featured-card small" @click="toInfo(attractions[1]?.id)" v-if="attractions[1]">
              <img :src="attractions[1].images.split(',')[0]" :alt="attractions[1].name">
              <div class="featured-overlay"></div>
              <div class="featured-content">
                <h3>{{attractions[1].name}}</h3>
                <span class="featured-link">查看 <i class="el-icon-arrow-right"></i></span>
              </div>
            </div>
            <div class="featured-card small" v-else>
              <img src="../../assets/image/attraction2.jpg" alt="景点">
              <div class="featured-overlay"></div>
              <div class="featured-content">
                <h3>水乡古镇</h3>
                <span class="featured-link">查看 <i class="el-icon-arrow-right"></i></span>
              </div>
            </div>
            
            <div class="featured-card small" @click="toInfo(attractions[2]?.id)" v-if="attractions[2]">
              <img :src="attractions[2].images.split(',')[0]" :alt="attractions[2].name">
              <div class="featured-overlay"></div>
              <div class="featured-content">
                <h3>{{attractions[2].name}}</h3>
                <span class="featured-link">查看 <i class="el-icon-arrow-right"></i></span>
              </div>
            </div>
            <div class="featured-card small" v-else>
              <img src="../../assets/image/attraction3.jpg" alt="景点">
              <div class="featured-overlay"></div>
              <div class="featured-content">
                <h3>雪山湖泊</h3>
                <span class="featured-link">查看 <i class="el-icon-arrow-right"></i></span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="section-action">
          <div class="more-btn" @click="toAttraction">
            查看全部景点
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- Features Section -->
    <div class="features-section">
      <div class="features-container">
        <div class="features-header">
          <span class="section-tag">我们的服务</span>
          <h2 class="section-title">为什么选择我们</h2>
        </div>
        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">
              <i class="el-icon-location-outline"></i>
            </div>
            <h3>精选景点</h3>
            <p>严格筛选全国各地热门景点，为您提供最佳旅行目的地</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <i class="el-icon-map-location"></i>
            </div>
            <h3>路线规划</h3>
            <p>专业团队精心设计旅游路线，让您的行程更加轻松愉快</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <i class="el-icon-office-building"></i>
            </div>
            <h3>品质酒店</h3>
            <p>精选优质酒店资源，确保您的住宿体验舒适便捷</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <i class="el-icon-service"></i>
            </div>
            <h3>贴心服务</h3>
            <p>7x24小时在线客服，随时为您解答旅途中的疑问</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Routes Section -->
    <div class="routes-section">
      <div class="section-container">
        <div class="section-header">
          <span class="section-tag">精品路线</span>
          <h2 class="section-title">推荐旅游路线</h2>
          <p class="section-desc">专业规划，让旅途更精彩</p>
        </div>
        <div class="routes-grid">
          <div class="route-card" v-for="(item,index) in line" :key="index" @click="toLineInfo(item.id)">
            <div class="route-image">
              <img :src="item.images.split(',')[0]" :alt="item.name">
              <div class="route-overlay"></div>
            </div>
            <div class="route-content">
              <h3 class="route-title">{{item.name}}</h3>
              <p class="route-desc">{{item.introduce}}</p>
              <div class="route-footer">
                <span class="route-link">查看详情 <i class="el-icon-arrow-right"></i></span>
              </div>
            </div>
          </div>
        </div>
        <div class="section-action">
          <div class="more-btn" @click="toLine">
            查看全部路线
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- CTA Section -->
    <div class="cta-section">
      <div class="cta-bg">
        <img src="../../assets/image/attraction1.jpg" alt="cta background">
      </div>
      <div class="cta-overlay"></div>
      <div class="cta-content">
        <h2>准备好开始您的旅程了吗？</h2>
        <p>立即探索精选目的地，开启难忘的旅行体验</p>
        <div class="cta-btn" @click="toAttraction">立即探索</div>
      </div>
    </div>

    <bottoms></bottoms>
  </div>
</template>

<script>
  import {getSysRotationsList,getSysAttractionsIndex,getUserCount,getSysLineIndex} from '../../api/api'
  import headers from '@/components/header'
  import bottoms from '@/components/bottom'
  export default {
    data() {
      return{
        rotations: [],
        attractions: [],
        count: 0,
        line: [],
      }
    },
    components: {
      headers,
      bottoms
    },
    methods: {
      toAttraction() {
        this.$router.push("/attractions")
      },
      toLine() {
        this.$router.push("/line")
      },
      toInfo(id) {
        this.$router.push("/attractionsInfo?id=" + id)
      },
      toLineInfo(id) {
        this.$router.push("/lineInfo?id=" + id)
      },
      getSysRotationsList() {
        getSysRotationsList().then(res => {
          if (res.code == 1000) {
            this.rotations = res.data
          }
        })
      },
      getSysAttractionsIndex() {
        getSysAttractionsIndex().then(res => {
          if (res.code == 1000) {
            this.attractions = res.data
          }
        })
      },
      getUserCount() {
        getUserCount().then(res => {
          if (res.code == 1000) {
            this.count = res.data
          }
        })
      },
      getSysLineIndex() {
        getSysLineIndex().then(res => {
          if (res.code == 1000) {
            this.line = res.data
          }
        })
      }
    },
    created() {
     
    },
    mounted() {
      this.getSysRotationsList()
      this.getSysAttractionsIndex()
      this.getUserCount()
      this.getSysLineIndex()
    }
 }
</script>

<style scoped>
   @import url('../../assets/css/index.css');
</style>
