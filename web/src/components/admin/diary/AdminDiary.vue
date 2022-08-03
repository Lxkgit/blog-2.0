<template>
    <div style="height: 100%; padding: 8px 20px 8px 20px; background-color: #eceef1;">
        <div style="margin-bottom: 10px;display: -webkit-box; display: -ms-flexbox; display: flex; -webkit-box-pack: start; -ms-flex-pack: start;
        justify-content: flex-start; -webkit-box-align: baseline; -ms-flex-align: baseline; align-items: baseline; max-height: 31px; color: #445160;
        font-size: 24px; font-weight: 600; text-align: left;">
            <span>日记管理</span>
        </div>
        <div style="height: 100%; background: #fff; padding: 16px;">
            <div style="width: 100%; padding: 0.5rem;">
                <h1 style="width: 100%; display: flex; justify-content: space-between; align-items: center; margin-top: 0.5rem;
                margin-bottom: 0.5rem; padding-left: 0.75rem; padding-right: 0.75rem; height: 2.5rem;">
                    <span style="font-size: 1.5rem; line-height: 2rem; line-height: 2.5rem; font-weight: 600;">{{ nian + " " + hanziyue + "月" }}</span>
                    <a-button @click="gaimonth(-1)" round>
                        <span>{{ "<" }}</span>
                        <span>{{ "<" }}</span>
                        <span>上个月</span>
                    </a-button>
                    <a-button @click="gaimonth(1)" round>
                        <span>下个月</span>
                        <span>{{ ">" }}</span>
                        <span>{{ ">" }}</span>
                    </a-button>
                    <a-button @click="getnianyueri()" type="primary">回到今天</a-button>
                </h1>
                <div style="width: 80%; display: flex; flex-wrap: wrap; justify-content: flex-start; align-items: flex-start;">
                    <div style="border-style: solid; border-color: #445160; border-width: 1px; width: 14%; min-width: 32px; height: 8rem;  cursor: pointer;" v-for="(item,idx) in daylist"
                        :id="idx" :key="idx" :style="item.month !== yue ? 'border-color: #fff0f0' : ''" @click="clickevent(item,idx)">
                        <div style="width: 100%; height: 1.25rem; display: flex; justify-content: space-between; align-items: center; padding: 0.25rem;">
                            <span>{{ item.day }}</span>
                            <span v-if="item.day === xianzaitime.ri && item.month === xianzaitime.yue && item.year === xianzaitime.nian" style="color: aqua;">今天</span>
                            <span>{{ item.zhou }}</span>
                        </div>
                        <slot :year="item.year" :month="item.month" :day="item.day" :week="item.week"></slot>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import { computed, ref, defineEmits } from "vue";

import { useRoute, useRouter } from "vue-router"

const router = useRouter()
const route = useRoute()
// const emit = defineEmits(["handleclick", "changemonth"])
// 需要展示的所有日期列表
const daylist = ref([])
// 年月日数据暂存
const shijian:any = ref(null)
const nian = ref(null)
const yue = ref(null)
const ri = ref(null)
const getnianyueri = (canshu = null) => {
    // 置空日期列表
    daylist.value = []
    if (canshu) {
        shijian.value = new Date(canshu)
    } else {
        shijian.value = new Date();
    }
    nian.value = shijian.value.getFullYear()
    yue.value = shijian.value.getMonth() + 1
    ri.value = shijian.value.getDate()
    // 当前显示月份改变后，需要向父组件抛出，父组件可以根据月份更新日程
    // emit("changemonth", { year: nian.value, month: yue.value })
    tianchong()
}
// 计算需要展示的第一个日期
const startandendday = () => {
    const cloneNowDate = new Date(shijian.value);
    // 先获取这个月的第一天
    const firstdaytime = cloneNowDate.setDate(1)
    // 获取第一天是周几  ，修改2021年7月26日 14:42:58，周日是0不是7，我草。
    let firstdayweek = new Date(firstdaytime).getDay()
    if (firstdayweek === 0) {
        firstdayweek = 7
    }
    // 展示时间的第一天
    return firstdaytime - (firstdayweek - 1) * 24 * 60 * 60 * 1000
}
// 向列表填充数据
const tianchong = () => {
    const starttime = startandendday()
    console.log(starttime, "开始时间")
    for (let i = 0; i <= 60; i++) {
        let shuju = {}
        const shijianchuo = starttime + i * 24 * 60 * 60 * 1000
        const sj = new Date(shijianchuo)
        const nian1 = sj.getFullYear()
        const yue1:any = sj.getMonth() + 1
        const ri1 = sj.getDate()
        const week = sj.getDay()
        if (yue.value === 1) {
            if (yue1 > yue.value && yue1 !== 12) {
                if (week === 1 || week === 7) { break }
            }
        } else if (yue.value === 12) {
            if (yue1 - yue.value === -11) {
                if (week === 1 || week === 7) { break }
            }
        } else {
            if (yue1 > yue.value) {
                if (week === 1 || week === 7) { break }
            }
        }
        shuju["timestamp"] = shijianchuo
        shuju["year"] = nian1
        shuju["month"] = yue1
        shuju["day"] = ri1
        shuju["week"] = week
        shuju["zhou"] = weektozh(week)
        daylist.value.push(shuju)
    }
}
let preIdx: any;
// 日期点击事件
const clickevent = (item:any, idx:any) => {
    let preDiv = document.getElementById(preIdx);
    let curDiv = document.getElementById(idx); //获取到导航栏id
    if (preDiv !== null) {
        preDiv.style.borderColor = "#445160";
        preDiv.style.backgroundColor = "#ffffff";
    }
    if (curDiv !== null) {
        curDiv.style.borderColor = "#445160";
        curDiv.style.backgroundColor = "#7FFFAA";
    }
    preIdx = idx;
    // emit("handleclick", item)
    // 如果点击的日期不是当前月的，修改当前时间
    if (item.month !== yue.value) {
        getnianyueri(item.timestamp)
    }
}
// 数字转汉字
const hanziyue = computed(() => {
    return numtozh(yue.value)
})
// 点击上个月或者下个月
const gaimonth = (num) => {
    let newmonth = yue.value + num
    // 如果大于12，转到下一年
    if (newmonth > 12) {
        nian.value += 1
        newmonth = 1
    }
    const sj = new Date(Date.parse(nian.value + "/" + newmonth + "/" + ri.value)).getTime()
    getnianyueri(sj)
}
// 获取今天的时间
const xianzaitime = ref({})
const getnow = () => {
    const sj = new Date()
    xianzaitime.value["nian"] = sj.getFullYear()
    xianzaitime.value["yue"] = sj.getMonth() + 1
    xianzaitime.value["ri"] = sj.getDate()
}
const dict:any = {
    0: "零",
    1: "壹",
    2: "贰",
    3: "弎",
    4: "肆",
    5: "伍",
    6: "陆",
    7: "柒",
    8: "捌",
    9: "玖",
    10: "拾",
    11: "拾壹",
    12: "拾贰",
}
const weektozh = (num: any) => {
    return dict2[num];
}

const dict2:any = {
    1:"周一",
    2:"周二",
    3:"周三",
    4:"周四",
    5:"周五",
    6:"周六",
    0:"周日",
}
const numtozh = (num:number) => {
    return dict[num]
}
// 初始化区域
getnow()
getnianyueri()
</script>
<style scoped>
</style>