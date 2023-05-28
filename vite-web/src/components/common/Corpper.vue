<template>
    <el-dialog title="图片剪裁" :visible.sync="dialogVisible" class="crop-dialog" append-to-body>
      <div class="cropper-content">
        <div class="cropper" style="text-align:center">
          <vueCropper ref="cropper" :img="option.img" :outputSize="option.size" :outputType="option.outputType"
            :info="true" :full="option.full" :canMove="option.canMove" :canMoveBox="option.canMoveBox"
            :original="option.original" :autoCrop="option.autoCrop" :fixed="option.fixed"
            :fixedNumber="option.fixedNumber" :centerBox="option.centerBox" :infoTrue="option.infoTrue"
            :fixedBox="option.fixedBox" :autoCropWidth="option.autoCropWidth" :autoCropHeight="option.autoCropHeight"
            @cropMoving="cropMoving" />
        </div>
      </div>
     
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false" :loading="dialogVisible = false">确认</el-button>
      </div>
    </el-dialog>
</template>

<script lang="ts">
//@ts-nocheck
import 'vue-cropper/dist/index.css'
import { VueCropper }  from "vue-cropper";


</script>