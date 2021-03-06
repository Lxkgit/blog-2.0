import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // AutoImport({
    //   imports: ['vue', 'vue-router']
    // }),
    Components({
      resolvers: [AntDesignVueResolver()]
    })
  ],
  server:{
		proxy:{
			'/api':{
          target: 'http://127.0.0.1:9527',	//实际请求地址
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
      }
		}
	}
})
