<template>
  <div class="index">
    <headers></headers>
    
    <!-- Hero Section -->
    <div class="hero">
      <div class="hero-bg"></div>
      <div class="hero-content">
        <div class="hero-badge">发现美好旅途</div>
        <h1 class="hero-title">探索世界的精彩</h1>
        <p class="hero-subtitle">为您精选优质景点、路线与住宿，让每一次出行都成为难忘的回忆</p>
        <div class="hero-stats">
          <div class="stat-item">
            <div class="stat-number">{{count || '10000'}}+</div>
            <div class="stat-label">累计游客</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-number">{{attractions.length || '50'}}+</div>
            <div class="stat-label">精选景点</div>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <div class="stat-number">{{line.length || '30'}}+</div>
            <div class="stat-label">旅游路线</div>
          </div>
        </div>
        <div class="hero-actions">
          <div class="hero-btn primary" @click="toAttraction">开始探索</div>
          <div class="hero-btn secondary" @click="toLine">查看路线</div>
        </div>
      </div>
    </div>
    
    <!-- Features Section -->
    <div class="features-section">
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
            <i class="el-icon-cpu"></i>
          </div>
          <h3>AI助手</h3>
          <p>智能AI助手为您解答疑问，提供个性化旅行建议</p>
        </div>
      </div>
    </div>

    <!-- Attractions Section -->
    <div class="section">
      <div class="section-container">
        <div class="section-header">
          <div class="section-tag">热门推荐</div>
          <h2 class="section-title">推荐景点</h2>
          <p class="section-desc">精心挑选的热门旅游目的地，等您来探索</p>
        </div>
        <div class="cards-grid">
          <div class="card" v-for="(item,index) in attractions" :key="index" @click="toInfo(item.id)">
            <div class="card-image">
              <img :src="item.images.split(',')[0]" :alt="item.name">
              <div class="card-overlay">
                <span class="card-btn">立即预约</span>
              </div>
            </div>
            <div class="card-content">
              <h3 class="card-title">{{item.name}}</h3>
              <p class="card-desc">{{item.introduce}}</p>
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

    <!-- Routes Section -->
    <div class="section section-alt">
      <div class="section-container">
        <div class="section-header">
          <div class="section-tag">精品路线</div>
          <h2 class="section-title">旅游路线</h2>
          <p class="section-desc">专业设计的旅行路线，让您的旅途更加精彩</p>
        </div>
        <div class="cards-grid">
          <div class="card" v-for="(item,index) in line" :key="index" @click="toLineInfo(item.id)">
            <div class="card-image">
              <img :src="item.images.split(',')[0]" :alt="item.name">
              <div class="card-overlay">
                <span class="card-btn">查看详情</span>
              </div>
            </div>
            <div class="card-content">
              <h3 class="card-title">{{item.name}}</h3>
              <p class="card-desc">{{item.introduce}}</p>
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
