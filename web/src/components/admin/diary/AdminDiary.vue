<template>
    <div style="height: 100%; padding: 8px 20px 8px 20px; background-color: #eceef1;">
        <div style="margin-bottom: 10px;display: -webkit-box; display: -ms-flexbox; display: flex; -webkit-box-pack: start; -ms-flex-pack: start;
        justify-content: flex-start; -webkit-box-align: baseline; -ms-flex-align: baseline; align-items: baseline; max-height: 31px; color: #445160;
        font-size: 24px; font-weight: 600; text-align: left;">
            <span>日记管理</span>
        </div>
        <div style="height: 100%; background: #fff; padding: 16px;">
            <calendar @handleClick="showTime" @changeMonth="getData">
                <template v-slot="yuansu">
                    <div class="w-full h-full px-2 mt-1 overflow-x-hidden overflow-y-auto">
                        <span>{{ yuansu.year }}-{{ yuansu.month }}-{{ yuansu.day }}</span>
                        <p>自定义内容</p>
                    </div>
                </template>
            </calendar>

        </div>
    </div>
</template>
<script setup lang="ts">
const getData = (date: any) => {
    
    console.log(date)
}

const showTime = (item: any) => {
    
    console.log(item)

}

</script>
<style scoped>
</style>