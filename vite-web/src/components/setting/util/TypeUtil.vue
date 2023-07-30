<template>
  <div>
    <div v-for="(item, index) in options" :key="index">
      <el-divider v-if="item.type === 0" content-position="left">
        {{ item.title }}
      </el-divider>
      <TypeInput v-else-if="item.type === 1" :title="item.title" :id="item.id" :model.sync="item.value" :type="item.type" />
      <TypeNumber v-else-if="item.type === 2" :title="item.title" :id="item.id" :model.sync="item.num" :type="item.type" />
      <TypeSwitch v-else-if="item.type === 3" :title="item.title" :id="item.id" :model.sync="item.bool" :type="item.type" />
      <TypeText v-else-if="item.type === 4" :title="item.title" :id="item.id" :model.sync="item.value" :type="item.type" />
      <TypeUpload v-else-if="item.type === 5" :title="item.title" :id="item.id" :model.sync="item.valueList"
        :type="item.type" />
      <TypeUploadMore v-else-if="item.type === 6" :title="item.title" :id="item.id" :model.sync="item.valueList"
        :type="item.type" :num="item.num" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue';
import TypeText from "@/components/setting/util/TypeText.vue"
import TypeInput from '@/components/setting/util/TypeInput.vue'
import TypeNumber from '@/components/setting/util/TypeNumber.vue'
import TypeUpload from '@/components/setting/util/TypeUpload.vue'
import TypeSwitch from '@/components/setting/util/TypeSwitch.vue'
import TypeUploadMore from '@/components/setting/util/TypeUploadMore.vue';

let options: any = ref([])
const props = defineProps({
  options: Array
})
onMounted(() => {
  if (props.options != null && props.options !== undefined) {
    options.value = props.options
  }
})

</script>

<style scoped></style>
