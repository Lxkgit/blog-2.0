module.exports = {
    presets: [
        '@vue/cli-plugin-babel/preset'
    ],
    // 新增代码
    plugins: [
        [
            'import',
            { libraryName: 'ant-design-vue', libraryDirectory: 'es', style: true }
        ]
    ]
}
