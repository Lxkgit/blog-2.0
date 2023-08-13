import { getCurrentInstance } from "vue";
import mitter from "@/utils/mitt";

// 全局socket
const socketAll = () => {
  let websocket: any = null;
  const instance = getCurrentInstance();
  const openSocketAll = () => {
    //判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
    let url = "http://localhost:9527/file/result";
    // let url = "http://124.221.12.158:9527/file/result";
    if ('WebSocket' in window) {
      url = url.replace("https", "wss").replace("http", "ws");
      if (websocket == null) {
        websocket = new WebSocket(url);
      }

      // 连接发生错误的回调方法
      websocket.onerror = function () {
        console.log("系统websocket连接错误");
      };

      // 连接成功建立的回调方法
      websocket.onopen = function (event) {
        console.log("系统websocket已打开");
      }

      // 接收到消息的回调方法
      websocket.onmessage = function (event) {
        let data:any = JSON.parse(event.data)
        console.log(data)
        mitter.emit(data.topic, data.message);
      }

      // 连接关闭的回调方法
      // websocket.onclose = function () {
      //   setMessageInnerHTML("close");
      // }

      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
        websocket.close();
        console.log("系统websocket已关闭");
      }

    } else {
      console.log("您的浏览器不支持websocket!!!")
    }
  }

  //关闭连接
  function closeWebSocketAll() {
    if (websocket != null) {
      websocket.close();
      websocket = null;
      console.log("系统websocket已关闭");
    }
  }

  //发送消息
  function sendSocketAll(message: any) {
    websocket.send(message);
  }

  return {
    openSocketAll,
    closeWebSocketAll,
    sendSocketAll
  }
}

export default socketAll