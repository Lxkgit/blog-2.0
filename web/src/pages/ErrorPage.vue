<template>
    <el-col :sm="12" :lg="6">
        <el-result icon="error" title="Error Tip" sub-title="Please follow the instructions">
            <template #extra>
                <el-button type="primary">Back</el-button>
            </template>
        </el-result>
    </el-col>
</template>