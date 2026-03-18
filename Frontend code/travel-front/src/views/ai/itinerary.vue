<template>
    <div>
        <Header />
        <div class="itinerary-page">
            <div class="container">
                <div class="page-header">
                    <h1>🗺️ 智能行程规划</h1>
                    <p>AI 为你量身定制专属旅游行程</p>
                </div>

                <div class="content-wrapper">
                    <!-- 左侧：行程设置 -->
                    <div class="settings-panel">
                        <div class="panel-title">
                            <i class="el-icon-edit"></i>
                            行程设置
                        </div>
                        
                        <el-form :model="form" label-width="100px" class="settings-form">
                            <el-form-item label="目的地" required>
                                <el-input 
                                    v-model="form.destination" 
                                    placeholder="例如：北京、杭州、三亚">
                                </el-input>
                            </el-form-item>

                            <el-form-item label="旅游天数" required>
                                <el-input-number 
                                    v-model="form.days" 
                                    :min="1" 
                                    :max="30"
                                    style="width: 100%">
                                </el-input-number>
                            </el-form-item>

                            <el-form-item label="预算">
                                <el-select v-model="form.budget" placeholder="请选择预算" style="width: 100%">
                                    <el-option label="经济型（2000元以下）" value="经济型（2000元以下）"></el-option>
                                    <el-option label="舒适型（2000-5000元）" value="舒适型（2000-5000元）"></el-option>
                                    <el-option label="豪华型（5000元以上）" value="豪华型（5000元以上）"></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="旅游偏好">
                                <el-input
                                    type="textarea"
                                    :rows="4"
                                    placeholder="例如：喜欢历史文化、想品尝当地美食、需要亲子友好的景点..."
                                    v-model="form.preferences">
                                </el-input>
                            </el-form-item>

                            <el-form-item>
                                <el-button 
                                    type="primary" 
                                    @click="generateItinerary"
                                    :loading="loading"
                                    style="width: 100%">
                                    <i class="el-icon-magic-stick"></i>
                                    生成行程
                                </el-button>
                            </el-form-item>
                        </el-form>

                        <!-- 调整行程 -->
                        <div v-if="itinerary" class="adjust-section">
                            <div class="adjust-title">
                                <i class="el-icon-refresh"></i>
                                调整行程
                            </div>
                            <el-input
                                type="textarea"
                                :rows="3"
                                placeholder="例如：第二天想去博物馆、增加一天购物时间..."
                                v-model="adjustmentRequest">
                            </el-input>
                            <el-button 
                                type="success" 
                                @click="adjustItinerary"
                                :loading="adjusting"
                                style="width: 100%; margin-top: 12px">
                                <i class="el-icon-check"></i>
                                应用调整
                            </el-button>
                        </div>
                    </div>

                    <!-- 右侧：行程结果 -->
                    <div class="result-panel">
                        <div class="panel-title">
                            <i class="el-icon-document"></i>
                            你的行程
                        </div>

                        <div v-if="!itinerary && !loading" class="empty-state">
                            <i class="el-icon-map-location"></i>
                            <p>填写行程信息后，点击"生成行程"按钮</p>
                        </div>

                        <div v-if="loading" class="loading-state">
                            <i class="el-icon-loading"></i>
                            <p>AI 正在为你规划行程，请稍候...</p>
                            <p class="loading-tip">这可能需要 30-60 秒，请耐心等待</p>
                        </div>

                        <div v-if="itinerary && !loading" class="itinerary-content">
                            <div class="action-buttons">
                                <el-button size="small" icon="el-icon-download" @click="downloadItinerary">
                                    下载行程
                                </el-button>
                                <el-button size="small" icon="el-icon-share" @click="shareItinerary">
                                    分享行程
                                </el-button>
                            </div>
                            <div class="content" v-html="formatItinerary(itinerary)"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Bottom />
    </div>
</template>

<script>
import Header from '@/components/header'
import Bottom from '@/components/bottom'
import request from '@/utils/request'

export default {
    name: 'Itinerary',
    components: {
        Header,
        Bottom
    },
    data() {
        return {
            form: {
                destination: '',
                days: 3,
                budget: '舒适型（2000-5000元）',
                preferences: ''
            },
            itinerary: null,
            loading: false,
            adjustmentRequest: '',
            adjusting: false
        }
    },
    methods: {
        async generateItinerary() {
            if (!this.form.destination) {
                this.$message.warning('请输入目的地')
                return
            }

            this.loading = true
            console.log('=== 前端：开始生成行程 ===')
            
            try {
                const userId = localStorage.getItem('userId') || 'guest'
                
                console.log('请求参数：', {
                    userId,
                    destination: this.form.destination,
                    days: this.form.days,
                    budget: this.form.budget,
                    preferences: this.form.preferences || '无特殊偏好'
                })
                
                const res = await request.post('/ai/plan-itinerary', {
                    userId,
                    destination: this.form.destination,
                    days: this.form.days,
                    budget: this.form.budget,
                    preferences: this.form.preferences || '无特殊偏好'
                })

                console.log('收到响应：', res)
                console.log('res.code:', res.code, '类型:', typeof res.code)
                console.log('res.data:', res.data)
                console.log('res.data 长度:', res.data ? res.data.length : 0)

                if (res.code === 1000) {
                    console.log('✅ 设置 itinerary')
                    this.itinerary = res.data
                    console.log('itinerary 已设置，长度:', this.itinerary ? this.itinerary.length : 0)
                    this.$message.success('行程生成成功！')
                } else {
                    console.error('❌ code 不是 1000:', res.code)
                    this.$message.error(res.message || '生成失败')
                }
            } catch (error) {
                console.error('❌ 捕获异常：', error)
                this.$message.error('生成失败，请稍后重试')
            } finally {
                this.loading = false
                console.log('=== 请求结束，itinerary:', this.itinerary ? '有值' : '无值', '===')
            }
        },
        async adjustItinerary() {
            if (!this.adjustmentRequest.trim()) {
                this.$message.warning('请输入调整需求')
                return
            }

            this.adjusting = true
            
            try {
                const res = await request.post('/ai/adjust-itinerary', {
                    adjustment: this.adjustmentRequest,
                    originalItinerary: this.itinerary
                })

                if (res.code === 1000) {
                    this.itinerary = res.data
                    this.adjustmentRequest = ''
                    this.$message.success('行程已调整！')
                } else {
                    this.$message.error(res.message || '调整失败')
                }
            } catch (error) {
                console.error('调整行程失败：', error)
                this.$message.error('调整失败，请稍后重试')
            } finally {
                this.adjusting = false
            }
        },
        formatItinerary(text) {
            if (!text) return ''
            
            let html = text
                .replace(/\n/g, '<br>')
                .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
                .replace(/\*(.*?)\*/g, '<em>$1</em>')
                .replace(/###\s+(.*?)<br>/g, '<h3>$1</h3>')
                .replace(/##\s+(.*?)<br>/g, '<h2>$1</h2>')
                .replace(/#\s+(.*?)<br>/g, '<h1>$1</h1>')
                .replace(/第(\d+)天/g, '<div class="day-title">第$1天</div>')
            
            return html
        },
        downloadItinerary() {
            const blob = new Blob([this.itinerary], { type: 'text/plain;charset=utf-8' })
            const url = URL.createObjectURL(blob)
            const link = document.createElement('a')
            link.href = url
            link.download = `${this.form.destination}旅游行程.txt`
            link.click()
            URL.revokeObjectURL(url)
            this.$message.success('行程已下载')
        },
        shareItinerary() {
            const textarea = document.createElement('textarea')
            textarea.value = this.itinerary
            document.body.appendChild(textarea)
            textarea.select()
            document.execCommand('copy')
            document.body.removeChild(textarea)
            this.$message.success('行程已复制到剪贴板')
        }
    }
}
</script>

<style scoped lang="less">
.itinerary-page {
    min-height: calc(100vh - 380px);
    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
    padding: 40px 20px;

    .container {
        max-width: 1400px;
        margin: 0 auto;
    }

    .page-header {
        text-align: center;
        color: white;
        margin-bottom: 40px;

        h1 {
            font-size: 42px;
            margin-bottom: 12px;
            font-weight: 700;
        }

        p {
            font-size: 18px;
            opacity: 0.9;
        }
    }

    .content-wrapper {
        display: grid;
        grid-template-columns: 400px 1fr;
        gap: 30px;
        align-items: start;
    }

    .settings-panel,
    .result-panel {
        background: white;
        border-radius: 16px;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
        overflow: hidden;
    }

    .panel-title {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        color: white;
        padding: 20px 24px;
        font-size: 18px;
        font-weight: 600;
        display: flex;
        align-items: center;
        gap: 10px;

        i {
            font-size: 22px;
        }
    }

    .settings-form {
        padding: 24px;

        :deep(.el-form-item__label) {
            font-weight: 600;
            color: #333;
        }

        :deep(.el-button--primary) {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            border: none;
            height: 44px;
            font-size: 16px;
            font-weight: 600;

            &:hover {
                opacity: 0.9;
            }
        }
    }

    .adjust-section {
        padding: 24px;
        border-top: 1px solid #eee;

        .adjust-title {
            font-size: 16px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 8px;

            i {
                color: #f5576c;
            }
        }

        :deep(.el-button--success) {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            border: none;
            height: 40px;
            font-weight: 600;

            &:hover {
                opacity: 0.9;
            }
        }
    }

    .result-panel {
        min-height: 600px;
    }

    .empty-state,
    .loading-state {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 100px 40px;
        color: #999;

        i {
            font-size: 80px;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
        }

        .loading-tip {
            font-size: 14px;
            color: #999;
            margin-top: 10px;
        }
    }

    .loading-state {
        i {
            color: #f5576c;
        }
    }

    .itinerary-content {
        padding: 24px;

        .action-buttons {
            margin-bottom: 20px;
            display: flex;
            gap: 12px;
            justify-content: flex-end;
        }

        .content {
            line-height: 1.8;
            font-size: 15px;
            color: #333;

            :deep(h1), :deep(h2), :deep(h3) {
                color: #f5576c;
                margin: 20px 0 12px;
                font-weight: 600;
            }

            :deep(h1) { font-size: 24px; }
            :deep(h2) { font-size: 20px; }
            :deep(h3) { font-size: 18px; }

            :deep(strong) {
                color: #f093fb;
                font-weight: 600;
            }

            :deep(.day-title) {
                background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                color: white;
                padding: 12px 20px;
                border-radius: 8px;
                font-size: 18px;
                font-weight: 600;
                margin: 24px 0 16px;
                display: inline-block;
            }
        }
    }
}

@media screen and (max-width: 1024px) {
    .itinerary-page {
        .content-wrapper {
            grid-template-columns: 1fr;
        }
    }
}
</style>
