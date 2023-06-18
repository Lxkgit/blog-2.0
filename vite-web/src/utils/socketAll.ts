import { getCurrentInstance } from "vue";

// 全局socket
const socketAll = () => {
  let websocket = null;
  const instance = getCurrentInstance();
  const openSocket = () => {
    //判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
    if ('WebSocket' in window) {
      let url = "http://localhost:9527/file/result";
      url = url.replace("https", "wss").replace("http", "ws");
      if (websocket == null) {
        websocket = new WebSocket(url);
      }

      // 连接发生错误的回调方法
      websocket.onerror = function () {
        setMessageInnerHTML("error");
      };

      // 连接成功建立的回调方法
      websocket.onopen = function (event) {
        console.log("websocket已打开");
      }

      // 接收到消息的回调方法
      websocket.onmessage = function (event) {
        console.log(event.data)
        let data:any = JSON.parse(event.data)
        instance?.proxy?.$emitter.emit(data.topic, data.message);
      }

      // 连接关闭的回调方法
      // websocket.onclose = function () {
      //   setMessageInnerHTML("close");
      // }

      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
        websocket.close();
      }

      //将消息显示在网页上
      // function setMessageInnerHTML(innerHTML) {
      //   document.getElementById('message').innerHTML += innerHTML + '<br/>';
      // }
    } else {
      console.log("您的浏览器不支持websocket!!!")
    }
  }

  //关闭连接
  function closeWebSocket() {
    if (websocket != null) {
      websocket.close();
      websocket = null;
    }
  }

  //发送消息
  function send(message: any) {
    websocket.send(message);
  }

  return {
    openSocket,
    closeWebSocket,
    send
  }
}

export default socketAll