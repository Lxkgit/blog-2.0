import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import fs from 'fs'
import os from 'os'

const homedir = os.homedir()
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // AutoImport({
    //   imports: ['vue', 'vue-router']
    // }),
    // AutoImport({
    //   resolvers: [ElementPlusResolver()],
    // }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  server:{
		proxy:{
			'/api':{
          target: 'https://127.0.0.1:9527',	//实际请求地址
          changeOrigin: true,
          secure: false,
          rewrite: (path) => path.replace(/^\/api/, ''),
      }
		},
    https: {
      // cert: fs.readFileSync(path.resolve(`./keystore.p12`))
    }
	},
  resolve: {
    alias: {
      // 如果报错__dirname找不到，需要安装node,执行yarn add @types/node --save-dev
      "@": path.resolve(__dirname, "src"),
      "comps": path.resolve(__dirname, "src/components"),
    }
  }
})
// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'
// import path from 'path'
//  
// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [vue()],  // 注册插件
//   server: {
//     open: true
//   },
//   resolve: {
//     alias: {
//       // 如果报错__dirname找不到，需要安装node,执行yarn add @types/node --save-dev
//       "@": path.resolve(__dirname, "src"),
//       "comps": path.resolve(__dirname, "src/components"),
//     }
//   }
// })