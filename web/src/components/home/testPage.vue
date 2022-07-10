<template>
  <div class="upload">
   <!-- 上传按钮，由于我上传的是ppt文件，所以accept属性的值是“.ppt”，可根据自己的需求自行设置-->
   <!-- a-upload 组件的属性可以自行查看ant design官网的详细说明-->
    <a-upload
    accept=".txt"  
    :show-upload-list="false"
    :before-upload="beforeUploadPpt"
    >
      <a-button class="upload-button">
        上传PPT
      </a-button>
    </a-upload>
    <!-- 显示上传进度-->
    <a-progress :percent="filepercent" id="uploadpercent"/>
  </div>
</template>


<script>
import { message } from 'ant-design-vue'
import { defineComponent, ref, reactive } from 'vue'
import axios from 'axios'

export default defineComponent({
  setup () {
    const filepercent = ref(0) //简单数据类型可响应式定义，复杂数据类型使用 reactive, ref 和 reactive 均需从vue 中引入
    const beforeUploadPpt = file => {
      const fd = new FormData()
      fd.append('file', file) //文件以formdata格式进行上传，除此以外还有json格式，键值对格式
      axios({
        method: 'post', //提交数据方式选择不限长度的post
        url: 'http://127.0.0.1:9527/file/files/upload', //后端地址 url 
        data: fd, //提交数据
        headers: { 'Content-Type': 'multipart/form-data' }, //默认的请求头格式为json格式，而此处提交的数据是formdata，所以需要设置请求头
        //axios提供的上传处理进度事件
        onUploadProgress: function (progressEvent) {
        //检测上传进度是否可计算
          if (progressEvent.lengthComputable) {
            filepercent.value = (progressEvent.loaded / progressEvent.total * 100 | 0)
            if (filepercent.value !== 0) {
              document.getElementById('uploadpercent').style.display = 'block'
            }
          }
        }
      }).then(res => {
        console.log(res.data)
        if (res.data.code === 200) {
          message.success('上传成功!')
        } else {
          message.error('上传失败！')
        }
      })
      //upload 组件自动上传文件，beforeUpload在此之前执行， 返回false则会将原有的上传停止
      //在beforeUpload中我们已经实现文件上传，所以返回false
      return false
    }
    return {
      filepercent,
      beforeUploadPpt
    }
  }
})
</script>


