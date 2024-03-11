import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { resolve } from "path";


// https://vitejs.dev/config/
export default defineConfig(
  ({ command, mode, ssrBuild }) => {
    const env = loadEnv(mode, process.cwd());
    const ip = env.VITE_BASE_API;
    console.log("服务请求ip: " + ip)
    return {
      resolve: {
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
        proxy: {
          '/api': {
            // target: 'http://' + ip + ':9527',	//实际请求地址
            target: 'http://localhost:9527',	//实际请求地址
            changeOrigin: true,
            secure: false,
            rewrite: (path) => path.replace(/^\/api/, ''),
          }
        },
      }
    }
  }
)
