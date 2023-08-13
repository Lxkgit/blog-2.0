<template>
  <NavMenu></NavMenu>
  <div style="
      margin-top: 50px;
      display: flex;
      justify-content: center;
      align-items: flex-start;
    ">

    <div style="margin-top: 20px; width: 15%;">
      <div style="padding-left: 40px; position: fixed;">
        <p>选择用户文档：</p>
        <el-select v-model="selectUserId" class="m-2" placeholder="Select" style="margin-top: 20px;" @change="getDocCatalogTreeFun">
          <el-option v-for="item in userList.data" :key="item.id" :label="item.username" :value="item.id" />
        </el-select>
      </div>

    </div>
    <div style="width: 70%; min-height: calc(100vh - 182px);">
      <el-row style="display: flex; flex-direction: column">
        <div v-for="doc in docList.list">
          <el-divider content-position="left">{{ doc.docName }}</el-divider>
          <div style="display: flex; justify-content: start; flex-wrap: wrap;">
            <div v-for="item in doc.list" style="margin-right: 70px;">
              <el-card class="article-item-hover" shadow="hover" :body-style="{ padding: '0px' }"
                style="width: 228px;  margin-bottom: 25px" @click="router.push('/detail/section/' + item.id)">
                <img :src="item.imgUrl" class="image" />
                <div style="padding: 14px">
                  <span>{{ item.docName }}</span>
                  <div>
                    <time class="time">{{ item.updateTime }}</time>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </div>
      </el-row>
    </div>
    <div style="width: 15%;"></div>
  </div>


  <Footer></Footer>
  <BackTop></BackTop>
</template>

<script setup name="Document" lang="ts">
import { nextTick, onActivated, onMounted, ref, reactive } from "vue";
import { systemStore } from "@/store/system";
import { useRouter } from "vue-router";
import { getDocCatalogTreeApi, selectDocUserListApi } from "@/api/content";
import user from "@/utils/user";

const router = useRouter();
const store = systemStore();

// 引入用户信息模块
let { isLogin, userId } = user();
// 当前文章id
const docID = ref()
let userList: any = reactive({ data: {} })
let selectUserId: any = ref(0)

onMounted(() => {
  store.setMenuIndex("3");
  if(isLogin.value) {
    selectUserId.value = Number(userId.value);
  } else {
    selectUserId.value = 0;
  }
  seleteDocUserListFun();
  getDocCatalogTreeFun();
  
});

// 获取文章数据
async function getDocCatalogTreeFun() {
  getDocCatalogTreeApi({
    "typeLowerLimit": 0,
    "typeUpperLimit": 1,
    "type": 0,
    "userId": selectUserId.value
  }).then((res: any) => {
    if (res.code === 200) {
      docList.list = res.result;
    }
  });
}

const seleteDocUserListFun = () => {
  selectDocUserListApi().then((res: any) => {
    if(res.code === 200) {
      console.log(userList)
      userList.data = res.result
      
    }
  })
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
