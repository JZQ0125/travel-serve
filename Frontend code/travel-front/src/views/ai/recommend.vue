<template>
    <div>
        <Header />
        <div class="recommend-page">
            <div class="container">
                <div class="page-header">
                    <h1>🎯 个性化景点推荐</h1>
                    <p>告诉我你的旅游偏好，我会为你推荐最合适的景点</p>
                </div>

                <div class="content-wrapper">
                    <!-- 左侧：偏好设置 -->
                    <div class="preference-panel">
                        <div class="panel-title">
                            <i class="el-icon-setting"></i>
                            设置你的偏好
                        </div>
                        
                        <el-form :model="form" label-width="100px" class="preference-form">
                            <el-form-item label="旅游类型">
                                <el-checkbox-group v-model="form.types">
                                    <el-checkbox label="自然风光">自然风光</el-checkbox>
                                    <el-checkbox label="历史文化">历史文化</el-checkbox>
                                    <el-checkbox label="现代都市">现代都市</el-checkbox>
                                    <el-checkbox label="休闲度假">休闲度假</el-checkbox>
                                    <el-checkbox label="探险刺激">探险刺激</el-checkbox>
                                    <el-checkbox label="美食购物">美食购物</el-checkbox>
                                </el-checkbox-group>
                            </el-form-item>

                            <el-form-item label="出行方式">
                                <el-radio-group v-model="form.travelMode">
                                    <el-radio label="自驾游">自驾游</el-radio>
                                    <el-radio label="跟团游">跟团游</el-radio>
                                    <el-radio label="自由行">自由行</el-radio>
                                </el-radio-group>
                            </el-form-item>

                            <el-form-item label="预算范围">
                                <el-select v-model="form.budget" placeholder="请选择预算">
                                    <el-option label="经济型（500元以下/天）" value="经济型"></el-option>
                                    <el-option label="舒适型（500-1000元/天）" value="舒适型"></el-option>
                                    <el-option label="豪华型（1000元以上/天）" value="豪华型"></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="其他偏好">
                                <el-input
                                    type="textarea"
                                    :rows="4"
                                    placeholder="例如：喜欢安静的地方、想看海、对摄影感兴趣..."
                                    v-model="form.otherPreferences">
                                </el-input>
                            </el-form-item>

                            <el-form-item>
                                <el-button 
                                    type="primary" 
                                    @click="getRecommendations"
                                    :loading="loading"
                                    style="width: 100%">
                                    <i class="el-icon-magic-stick"></i>
                                    获取推荐
                                </el-button>
                            </el-form-item>
                        </el-form>
                    </div>

                    <!-- 右侧：推荐结果 -->
                    <div class="result-panel">
                        <div class="panel-title">
                            <i class="el-icon-star-on"></i>
                            为你推荐
                        </div>

                        <div v-if="!recommendations && !loading" class="empty-state">
                            <i class="el-icon-location-outline"></i>
                            <p>设置你的偏好后，点击"获取推荐"按钮</p>
                        </div>

                        <div v-if="loading" class="loading-state">
                            <i class="el-icon-loading"></i>
                            <p>AI 正在为你精心挑选景点...</p>
                        </div>

                        <div v-if="recommendations && !loading" class="recommendations">
                            <div class="result-content" v-html="formatRecommendations(recommendations)"></div>
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
    name: 'Recommend',
    components: {
        Header,
        Bottom
    },
    data() {
        return {
            form: {
                types: [],
                travelMode: '自由行',
                budget: '舒适型',
                otherPreferences: ''
            },
            recommendations: null,
            loading: false
        }
    },
    methods: {
        async getRecommendations() {
            if (this.form.types.length === 0) {
                this.$message.warning('请至少选择一种旅游类型')
                return
            }

            this.loading = true
            
            try {
                const userId = localStorage.getItem('userId') || 'guest'
                
                const preferences = `
旅游类型：${this.form.types.join('、')}
出行方式：${this.form.travelMode}
预算范围：${this.form.budget}
${this.form.otherPreferences ? '其他偏好：' + this.form.otherPreferences : ''}
                `.trim()

                const res = await request.post('/ai/recommend', {
                    userId,
                    preferences
                })

                if (res.code === 1000) {
                    this.recommendations = res.data
                    this.$message.success('推荐成功！')
                } else {
                    this.$message.error(res.message || '推荐失败')
                }
            } catch (error) {
                console.error('推荐失败：', error)
                this.$message.error('推荐失败，请稍后重试')
            } finally {
                this.loading = false
            }
        },
        formatRecommendations(text) {
            if (!text) return ''
            
            // 转换 Markdown 格式
            let html = text
                .replace(/\n/g, '<br>')
                .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
                .replace(/\*(.*?)\*/g, '<em>$1</em>')
                .replace(/###\s+(.*?)<br>/g, '<h3>$1</h3>')
                .replace(/##\s+(.*?)<br>/g, '<h2>$1</h2>')
                .replace(/#\s+(.*?)<br>/g, '<h1>$1</h1>')
                .replace(/(\d+)\.\s+(.*?)<br>/g, '<div class="list-item"><span class="number">$1</span><span class="content">$2</span></div>')
            
            return html
        }
    }
}
</script>

<style scoped lang="less">
.recommend-page {
    min-height: calc(100vh - 380px);
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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

    .preference-panel,
    .result-panel {
        background: white;
        border-radius: 16px;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
        overflow: hidden;
    }

    .panel-title {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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

    .preference-form {
        padding: 24px;

        :deep(.el-form-item__label) {
            font-weight: 600;
            color: #333;
        }

        :deep(.el-checkbox-group) {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        :deep(.el-button--primary) {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            height: 44px;
            font-size: 16px;
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
    }

    .loading-state {
        i {
            color: #667eea;
        }
    }

    .recommendations {
        padding: 24px;

        .result-content {
            line-height: 1.8;
            font-size: 15px;
            color: #333;

            :deep(h1), :deep(h2), :deep(h3) {
                color: #667eea;
                margin: 20px 0 12px;
                font-weight: 600;
            }

            :deep(h1) { font-size: 24px; }
            :deep(h2) { font-size: 20px; }
            :deep(h3) { font-size: 18px; }

            :deep(strong) {
                color: #764ba2;
                font-weight: 600;
            }

            :deep(.list-item) {
                display: flex;
                gap: 12px;
                margin: 12px 0;
                padding: 16px;
                background: #f8f9ff;
                border-radius: 8px;
                border-left: 4px solid #667eea;

                .number {
                    flex-shrink: 0;
                    width: 28px;
                    height: 28px;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    border-radius: 50%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    font-weight: 600;
                    font-size: 14px;
                }

                .content {
                    flex: 1;
                    padding-top: 4px;
                }
            }
        }
    }
}

@media screen and (max-width: 1024px) {
    .recommend-page {
        .content-wrapper {
            grid-template-columns: 1fr;
        }
    }
}
</style>
