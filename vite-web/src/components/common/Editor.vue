<template>
  <div class="editor">
    <!-- <div ref='editor'></div> -->
  </div>
</template>

<!-- <script setup lang="ts">
import {ElMessage, ElLoading} from 'element-plus'
import {onBeforeUnmount, onMounted, ref} from 'vue';
import WangEditor from 'wangeditor';
import hljs from 'highlight.js'
import 'highlight.js/styles/monokai-sublime.css'
import qiniuUpload from "@/utils/qiniuUpload";
import icon from "@/utils/icon";

let {MyIcon} = icon()
// 七牛图片上传
let {upload} = qiniuUpload()
// 添加清空功能按钮
// 获取必要的变量，这些在下文中都会用到
const {BtnMenu} = WangEditor

class ClearMenu extends BtnMenu {
  constructor(editor) {
    // data-title属性表示当鼠标悬停在该按钮上时提示该按钮的功能简述
    const $elem = WangEditor.$(
        `<div class="w-e-menu" data-title="清空">
               <svg style="width: 20px;color: #999999" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-78e17ca8=""><path fill="currentColor" d="M160 256H96a32 32 0 0 1 0-64h256V95.936a32 32 0 0 1 32-32h256a32 32 0 0 1 32 32V192h256a32 32 0 1 1 0 64h-64v672a32 32 0 0 1-32 32H192a32 32 0 0 1-32-32V256zm448-64v-64H416v64h192zM224 896h576V256H224v640zm192-128a32 32 0 0 1-32-32V416a32 32 0 0 1 64 0v320a32 32 0 0 1-32 32zm192 0a32 32 0 0 1-32-32V416a32 32 0 0 1 64 0v320a32 32 0 0 1-32 32z"></path></svg>
            </div>`
    )
    super($elem, editor)
  }

  // 菜单点击清空事件
  clickHandler() {
    instance.txt.clear()
    content.value = ''
  }

  // 菜单是否被激活
  tryChangeActive() {
    if (instance.txt.html()) {
      this.active()
    } else {
      this.unActive()
    }
  }
}

// 菜单 key ，各个菜单不能重复
const menuKey = 'clearMenuKey'
// 注册菜单
WangEditor.registerMenu(menuKey, ClearMenu)
const editor = ref();
const content = ref('')

let instance;
onMounted(() => {
  instance = new WangEditor(editor.value);
  // 设置编辑器高度
  instance.config.height = 200
  // 自定义提示内容
  instance.config.placeholder = '元芳，你怎么看？'
  // 配置菜单栏，删减菜单，调整顺序
  instance.config.menus = [
    'emoticon',
    'image',
    'link',
    'code',
    'undo',
    'redo',
  ]
  // 挂载highlight代码高亮插件
  instance.highlight = hljs
  // z-index
  instance.config.zIndex = 0
  // 图片上传
  instance.config.customUploadImg = function (resultFiles, insertImgFn) {
    const loading = ElLoading.service({
      lock: true,
      text: '图片上传中，请稍候……',
      background: 'rgba(255, 255, 255, 0.5)',
    })
    upload('comment', resultFiles[0]).then((response) => {
      console.log(response)
      ElMessage.success({
        message: '图片上传成功',
        type: 'success'
      });
      loading.close()
      insertImgFn(response)
    }).catch(response => {
      //发生错误时执行的代码
      console.log(response)
      ElMessage.error('图片上传失败');
    });
  }
  instance.create();
});

onBeforeUnmount(() => {
  instance.destroy();
  instance = null;
});
// 获取编辑器导出的html代码
const syncHTML = () => {
  content.value = instance.txt.html();
  console.log(instance.txt.html())
};
// 清空编辑器内容
const clear = () => {
  instance.txt.clear()
  content.value = ''
}
// 声明暴露的对象
defineExpose({
  clear,
  syncHTML,
  content
})
</script> -->

<!-- <style scoped lang="scss">
.editor {
  margin: 10px 30px;
}
</style> -->
