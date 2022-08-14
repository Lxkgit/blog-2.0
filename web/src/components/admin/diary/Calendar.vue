<template>
    <div style="width: 100%; padding: 0.5rem;">
        <h1 style="width: 100%; display: flex; justify-content: space-between; align-items: center; margin-top: 0.5rem;
                margin-bottom: 0.5rem; padding-left: 0.75rem; padding-right: 0.75rem; height: 2.5rem;">
            <span style="font-size: 1.5rem; line-height: 2rem; line-height: 2.5rem; font-weight: 600;">{{ year + " " +
                    monthZh + "月"
            }}</span>
            <el-button @click="updateMonth(-1)">
                <span> {{ "<" }} </span>
                        <span> {{ "<" }} </span>
                                <span>上个月</span>
            </el-button>
            <el-button @click="updateMonth(1)">
                <span>下个月</span>
                <span>{{ ">" }}</span>
                <span>{{ ">" }}</span>
            </el-button>
            <el-button @click="toToday()">回到今天</el-button>
        </h1>
        <div style="width: 100%; display: flex; flex-wrap: wrap; justify-content: flex-start; align-items: flex-start;">
            <div style="border-style: solid; border-color: #445160; border-width: 1px; width: 14%; min-width: 32px; height: 122px;  cursor: pointer;"
                v-for="(item, idx) in dayList" :id="idx" :key="idx"
                :style="item.month !== month ? 'border-color: #fff0f0' : ''" @click="clickEvent(item, idx)">
                <div
                    style="width: 97%; height: 1.25rem; display: flex; justify-content: space-between; align-items: center; padding: 0.25rem;">
                    <span>{{ item.day }}</span>
                    <span v-if="item.day === today.day && item.month === today.month && item.year === today.year"
                        style="color: aqua;">今天</span>
                    <span>{{ item.weekZh }}</span>
                </div>
                <div style="margin: 5px 10px">
                    <span v-if="item.diary !== null" style="font-size: 10px; background: #3dbb99; position: relative; z-index: 2; display: inline-block; width: auto; padding: 0 1px; font-style: normal;
                        font-weight: 700; border-radius: 4px; color: #fff; cursor: pointer;"
                        @click="showModal(item)"> 日记 </span>
                </div>
            </div>
        </div>


        <el-dialog v-model="dialogVisible" title="日记" :close-on-click-modal="false" width="30%" :before-close="handleClose">
            <span>{{diaryShow.data.diary.diaryMd}}</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="dialogVisible = false">Confirm</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup lang="ts">

import { computed, ref, reactive, defineEmits } from "vue";
import { useRoute, useRouter } from "vue-router"

const dialogVisible = ref<boolean>(false);

let diaryShow: any = reactive({ data: [] });
 
const showModal = (item:any) =>{
    diaryShow.data = item
    dialogVisible.value=true
    
}

const handleClose = (done: () => void) => {
    dialogVisible.value=false
}

const router = useRouter()
const route = useRoute()
const emit = defineEmits(["handleClick", "changeMonth"])
// 需要展示的所有日期列表
const dayList: any = ref([])
// 年月日数据暂存
const time: any = ref(null)
const year = ref(0)
const month = ref(0)
const day = ref(0)
const diary: any = ref("")
let diaryList: any = [
    {
        "id": 41,
        "userId": 1,
        "diaryMd": "这个是7-13的日记测试 我再次修改",
        "diaryDate": "2022-07-12",
        "createTime": "2022-07-14 09:12:29",
        "updateTime": "2022-07-15 01:13:39"
    },
    {
        "id": 42,
        "userId": 1,
        "diaryMd": "这个是7-14",
        "diaryDate": "2022-07-13",
        "createTime": "2022-07-14 09:12:29",
        "updateTime": "2022-07-14 09:16:31"
    },
    {
        "id": 43,
        "userId": 1,
        "diaryMd": "这个是7-14 现在是7-15的日记",
        "diaryDate": "2022-07-14",
        "createTime": "2022-07-15 01:13:39",
        "updateTime": "2022-07-15 01:13:39"
    },
    {
        "id": 44,
        "userId": 1,
        "diaryMd": "7-16",
        "diaryDate": "2022-09-01",
        "createTime": "2022-08-04 06:07:37",
        "updateTime": "2022-08-04 06:07:40"
    }
]

const toToday = (now?: number) => {
    // 置空日期列表
    dayList.value = []
    if (now) {
        time.value = new Date(now)
    } else {
        time.value = new Date();
    }
    year.value = time.value.getFullYear()
    month.value = time.value.getMonth() + 1
    day.value = time.value.getDate()
    // 当前显示月份改变后，需要向父组件抛出，父组件可以根据月份更新日程
    emit("changeMonth", { year: year.value, month: month.value })
    addDate()
}

// 计算需要展示的第一个日期
const startDay = () => {
    const nowDate = new Date(time.value);
    // 先获取这个月的第一天
    const firstDayMonth = nowDate.setDate(1)
    // 获取第一天是周几
    let firstDayWeek = new Date(firstDayMonth).getDay()
    if (firstDayWeek === 0) {
        firstDayWeek = 7
    }
    // 展示时间的第一天
    return firstDayMonth - (firstDayWeek - 1) * 24 * 60 * 60 * 1000
}

// 向列表填充数据
const addDate = () => {
    const startTime = startDay()
    console.log(startTime, "开始时间")
    for (let i = 0; i <= 41; i++) {
        let date: any = {}
        const timeStamp = startTime + i * 24 * 60 * 60 * 1000
        const time_ = new Date(timeStamp)
        const year_ = time_.getFullYear()
        const month_: any = time_.getMonth() + 1
        const day_: any = time_.getDate()
        const week_ = time_.getDay()
        if (month.value === 1) {
            if (month_ > month.value && month_ !== 12) {
                if (week_ === 1 || week_ === 7) { break }
            }
        } else if (month.value === 12) {
            if (month_ - month.value === -11) {
                if (week_ === 1 || week_ === 7) { break }
            }
        } else {
            if (month_ > month.value) {
                if (week_ === 1 || week_ === 7) { break }
            }
        }
        date["timeStamp"] = timeStamp
        date["year"] = year_
        date["month"] = month_
        date["day"] = day_
        date["week"] = week_
        date["weekZh"] = weekToZh(week_)
        date["diary"] = null
        let monthStr: string;
        if (month_ < 10) {
            monthStr = "0" + month_
        } else {
            monthStr = month_
        }
        let dayStr: string;
        if (day_ < 10) {
            dayStr = "0" + day_;
        } else {
            dayStr = day_;
        }

        let todayStr: string = year_ + "-" + monthStr + "-" + dayStr
        let num: number = diaryList.length
        if (month.value === month_) {
            for (let j: number = 0; j < num; j++) {
                if (diaryList[j].diaryDate === todayStr) {
                    date["diary"] = diaryList[j]
                }
            }
        }
        dayList.value.push(date)
    }
}

let preIdx: any;
// 日期点击事件
const clickEvent = (item: any, idx: any) => {
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
    emit("handleClick", item)
    // 如果点击的日期不是当前月的，修改当前时间
    if (item.month !== month.value) {
        toToday(item.timeStamp)
    }
}
// 数字转汉字
const monthZh = computed(() => {
    return numToZh(month.value)
})
// 点击上个月或者下个月
const updateMonth = (num: any) => {
    let newMonth = month.value + num
    if (newMonth < 1) {
        year.value -= 1
        newMonth = 12
    }
    // 如果大于12，转到下一年
    if (newMonth > 12) {
        year.value += 1
        newMonth = 1
    }
    const time_ = new Date(Date.parse(year.value + "/" + newMonth + "/" + day.value)).getTime()
    toToday(time_)
}

// 获取今天的时间
const today: any = ref({})
const getNow = () => {
    const time_ = new Date()
    today.value["year"] = time_.getFullYear()
    today.value["month"] = time_.getMonth() + 1
    today.value["day"] = time_.getDate()
}

const dict: any = {
    1: "一",
    2: "二",
    3: "三",
    4: "四",
    5: "五",
    6: "六",
    7: "七",
    8: "八",
    9: "九",
    10: "十",
    11: "十一",
    12: "十二",
}
const numToZh = (num: number) => {
    return dict[num]
}

const dict2: any = {
    1: "周一",
    2: "周二",
    3: "周三",
    4: "周四",
    5: "周五",
    6: "周六",
    0: "周日",
}
const weekToZh = (num: any) => {
    return dict2[num];
}

// 初始化区域
getNow()
toToday()

</script>

