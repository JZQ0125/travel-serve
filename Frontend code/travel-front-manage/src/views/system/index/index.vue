<template>
  <div class="index">
    <div class="index1" id="index1">

    </div>
    <div class="index1" id="index2">

    </div>
  </div>
</template>

<script>
  import {getManageData} from '../../../api/api'
  import * as echarts from "echarts";
  export default {
    data() {
      return{
        myChart: null,
        myChart1: null,
        shuju: {},
      }
    },
    methods: {
      init() {
        getManageData().then(res => {
          if (res.code == 1000) {
            this.shuju = res.data
            var chartDom = document.getElementById('index1');
            this.myChart = echarts.init(chartDom);
            var option = {
              title: {
                text: '近七日景点预约'
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross',
                  label: {
                    backgroundColor: '#6a7985'
                  }
                }
              },
              legend: {
                data: ['预约数']
              },
              grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
              },
              xAxis: [
                {
                  type: 'category',
                  boundaryGap: false,
                  data: this.shuju.dates
                }
              ],
              yAxis: [
                {
                  type: 'value'
                }
              ],
              series: [
                {
                  name: '预约数',
                  type: 'line',
                  stack: 'Total',
                  label: {
                    show: true,
                    position: 'top'
                  },
                  areaStyle: {},
                  emphasis: {
                    focus: 'series'
                  },
                  data: this.shuju.nums
                }
              ]
            }
            this.myChart.setOption(option);

            var chartDom1 = document.getElementById('index2');
            this.myChart1 = echarts.init(chartDom1);
            var option1 = {
               title: {
                  text: '近七日酒店预约'
              },
              tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                      type: 'cross',
                      label: {
                          backgroundColor: '#6a7985'
                     }
                  }
              },
              legend: {
                  data: ['预约数']
              },
              grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true
              },
              xAxis: [
                  {
                      type: 'category',
                      boundaryGap: false,
                      data: this.shuju.dates
                  }
              ],
              yAxis: [
                  {
                      type: 'value'
                  }
              ],
              series: [
                  {
                      name: '预约数',
                      type: 'bar',
                      label: {
                          show: true,
                          position: 'top'
                      },
                      barWidth: 30,
                      itemStyle: {
                          color: 'green'
                      },
                      barCategoryGap: '20%',
                      data: this.shuju.orders
                  }
              ]
            }
            this.myChart1.setOption(option1);
          }
        })
      }
    },
    created() {
     
    },
    mounted() {
      this.init()
    }
 }
</script>

<style scoped>
  .index {
    width: 100%;
    height: 100%;
    font-family: 'PingFang SC', '黑体', sans-serif;
    display: flex;
    flex-wrap: wrap;
    gap: 24px;
    padding: 24px;
    background-color: #f8fafc;
  }
  .index1 {
    width: calc(50% - 12px);
    height: calc(50% - 12px);
    min-height: 300px;
    background-color: #ffffff;
    border-radius: 16px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
    padding: 24px;
    transition: all 0.3s ease;
  }
  .index1:hover {
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
  }
</style>
