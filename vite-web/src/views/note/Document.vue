<template>
  <NavMenu></NavMenu>
  <div
    style="
      margin-top: 50px;
      display: flex;
      justify-content: center;
      align-items: center;
    "
  >
    <div style="width: 1200px">
      <el-row style="display: flex; flex-direction: column">
        <div v-for="doc in docList.list" >
          <el-divider content-position="left">{{ doc.docName }}</el-divider>
          <div style="display: flex; justify-content: start; flex-wrap: wrap;">
            <div v-for="item in doc.list" style="margin-right: 70px;">
              <el-card class="article-item-hover" shadow="hover"
                :body-style="{ padding: '0px' }"
                style="width: 228px;  margin-bottom: 25px"
                @click="router.push('/detail/section/' + item.id)"
              >
                <img :src="item.imgUrl" class="image" />
                <div style="padding: 14px">
                  <span>{{ item.docName }}</span>
                  <div class="bottom">
                    <time class="time">{{ item.updateTime }}</time>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </div>
      </el-row>
    </div>
  </div>

  <Footer></Footer>
  <BackTop></BackTop>
</template>

<script setup name="Document" lang="ts">
import { nextTick, onActivated, onMounted, ref, reactive } from "vue";
import { systemStore } from "@/store/system";
import { useRouter } from "vue-router";
import { getDocCatalogTreeApi } from "@/api/content";

const router = useRouter();
const store = systemStore();

// 当前文章id
const docID = ref()

onMounted(() => {

 
  store.setMenuIndex("3");
  getDocCatalogTreeFun();
});

// 获取文章数据
async function getDocCatalogTreeFun() {
  getDocCatalogTreeApi({
    "typeLowerLimit": 0,
    "typeUpperLimit": 1,
    "type": 0
  }).then((res: any) => {
    if (res.code === 200) {
      docList.list = res.result;
    }
  });
}

const docList: any = reactive({ list: [] });


</script>

<style lang="scss">
.time {
  font-size: 12px;
  color: #999;
}

.image {
  width: 100%;
  height: 240px;
  display: block;
}
</style>
