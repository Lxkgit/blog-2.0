import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { resolve } from "path";

// https://vitejs.dev/config/
export default defineConfig({
  resolve:{
    alias: {
      "@": resolve(__dirname, "src")
    }
  },
  plugins: [
    vue(),
    Components({
      resolvers: [ElementPlusResolver()],
    })
  ],
  server: {
    proxy:{
			'/api':{
          target: 'https://127.0.0.1:9527',	//实际请求地址
          changeOrigin: true,
          secure: false,
          rewrite: (path) => path.replace(/^\/api/, ''),
      }
		},
  }
})
