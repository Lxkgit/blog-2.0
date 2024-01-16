<template>
  <v-md-editor v-model="text" height="100%" @save="save" :disabled-menus="[]"
    left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code file | save"
    @change="changeText" @upload-image="uploadImageFun"></v-md-editor>
</template>

<script setup lang="ts">
//@ts-nocheck
import { computed } from 'vue';
import { uploadApi } from "@/api/file"

import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import hljs from 'highlight.js/lib/core';
import python from 'highlight.js/lib/languages/python';
import json from 'highlight.js/lib/languages/json';
import yaml from 'highlight.js/lib/languages/yaml';
import sql from 'highlight.js/lib/languages/sql';
import javascript from 'highlight.js/lib/languages/javascript';
import css from 'highlight.js/lib/languages/css';
import scss from 'highlight.js/lib/languages/scss';
import xml from 'highlight.js/lib/languages/xml';
import java from 'highlight.js/lib/languages/java'

hljs.registerLanguage('json', json);
hljs.registerLanguage('python', python);
hljs.registerLanguage('yaml', yaml);
hljs.registerLanguage('sql', sql);
hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('scss', scss);
hljs.registerLanguage('css', css);
hljs.registerLanguage('xml', xml);
hljs.registerLanguage('java', java);
VMdEditor.use(githubTheme, {
  Hljs: hljs,
});

const emit = defineEmits(['change', 'save', 'update:text'])
const props = defineProps({
  text: String,
  fileTypeCode: String,
  filePathCode: String
})

// https://blog.csdn.net/weixin_44575130/article/details/121031618
const text = computed({
  get: () => props.text || '',
  set: val => {
    emit('update:text', val)
  }
})

const save = () => {
  emit('save')
}

const changeText = () => {
  emit('change')
}

const uploadImageFun = (event: any, insertImage: any, files: any) => {
  // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
  console.log("file" + files);
  for (let i in files) {
    const formData = new FormData();
    formData.append("files", files[i]);
    formData.append("fileTypeCode", props.fileTypeCode);
    formData.append("filePathCode", props.filePathCode);
    uploadApi(
      formData
    ).then((res: any) => {
      if (res.code === 200) {
        insertImage({
          url: res.result[0],
          desc: files[i].name,
        });
      }
    });
  }
};

</script>