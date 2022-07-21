<template>
    <div style=" height: 47px; text-align: center;">
        <div style="display: inline-block; margin-right: 10px; margin-right: 20px;" v-for="item in list">{{ item.type }}
        </div>
    </div>
    <a-row justify="center">
        <a-col :lg="12" :md="24" :sm="24" :xs="24">
            <a-list item-layout="vertical" :loading="initLoading" size="large" :data-source="listData">
                <template #loadMore>
                    <div v-if="!initLoading && !loading"
                        :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }">
                        <a-button @click="onLoadMore">loading more</a-button>
                    </div>
                </template>
                <template #renderItem="{ item }">
                    <a-list-item key="item.title">
                        <template #actions>
                            <span v-for="{ type, text } in actions" :key="type">
                                <component :is="type" style="margin-right: 8px" />
                                {{ text }}
                            </span>
                        </template>
                        <template #extra>
                            <img width="272" alt="logo"
                                src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png" />
                        </template>
                        <a-list-item-meta :description="item.description">
                            <template #title>
                                <a :href="item.href">{{ item.title }}</a>
                            </template>
                            <template #avatar>
                                <a-avatar :src="item.avatar" />
                            </template>
                        </a-list-item-meta>
                        {{ item.content }}
                    </a-list-item>
                </template>
            </a-list>
        </a-col>

        <a-col :lg="4" :md="0" :sm="0" :xs="0">
            <a-card :loading="loading" title="热门标签">
                <div>
                    <a-tag color="pink">pink</a-tag>
                    <a-tag color="red">red</a-tag>
                    <a-tag color="orange">orange</a-tag>
                    <a-tag color="green">green</a-tag>
                    <a-tag color="cyan">cyan</a-tag>
                    <a-tag color="blue">blue</a-tag>
                    <a-tag color="purple">purple</a-tag>
                    <a-tag color="pink">pink</a-tag>
                    <a-tag color="red">red</a-tag>
                    <a-tag color="orange">orange</a-tag>
                    <a-tag color="green">green</a-tag>
                    <a-tag color="cyan">cyan</a-tag>
                    <a-tag color="blue">blue</a-tag>
                    <a-tag color="purple">purple</a-tag>
                </div>
            </a-card>
        </a-col>
    </a-row>
</template>

<script setup lang="ts">
import { onMounted, ref, nextTick, reactive } from 'vue';
let count = 3;

const initLoading = ref(true);
const loading = ref(false);

let listData: Record<string, string>[] = reactive([]);

//JS睡眠sleep()
const sleep = (numberMillis: number) => {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime) {
            return;
        }
    }
}

onMounted(() => {
    initLoading.value = false;
    for (let i = 0; i < count; i++) {
        listData.push({
            href: 'https://www.antdv.com/',
            title: `ant design vue part ${i}`,
            avatar: 'https://joeschmoe.io/api/v1/random',
            description:
                'Ant Design, a design language for background applications, is refined by Ant UED Team.',
            content:
                'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
        });
    }

});

const list = [
    { "id": 1, "type": "a" },
    { "id": 2, "type": "b" },
    { "id": 3, "type": "c" },
    { "id": 4, "type": "d" }
]

const pagination = {
    onChange: (page: number) => {
        console.log(page);
    },
    pageSize: 3,
};
const actions: Record<string, string>[] = [
    { type: 'StarOutlined', text: '156' },
    { type: 'LikeOutlined', text: '156' },
    { type: 'MessageOutlined', text: '2' },
];

const onLoadMore = () => {
    loading.value = true;

    sleep(1000)
    for (let i = 0; i < count; i++) {
        listData.push({
            href: 'https://www.antdv.com/',
            title: `ant design vue part ${i}`,
            avatar: 'https://joeschmoe.io/api/v1/random',
            description:
                'Ant Design, a design language for background applications, is refined by Ant UED Team.',
            content:
                'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
        });
    }
    loading.value = false;
    nextTick(() => {
        window.dispatchEvent(new Event('resize'));
    });

};


</script>