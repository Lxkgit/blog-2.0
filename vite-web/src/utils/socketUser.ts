
const socketUser = () => {
  let websocket: any = null;
  let socketLink = false
  async function openSocketUser(userId): Promise<void> {
    //判断当前浏览器是否支持WebSocket, 主要此处要更换为自己的地址
    if ('WebSocket' in window) {
      let url = "http://localhost:9527/file/client/" + userId;
      // let url = "http://124.221.12.158:9527/file/client/" + userId;
      url = url.replace("https", "wss").replace("http", "ws");
      if (websocket == null && !socketLink) {
        websocket = new WebSocket(url); 
        socketLink = true
      } 
      // else {
      //   console.log("用户websocket已连接");
      // }
      

      //连接发生错误的回调方法
      websocket.onerror = function () {
        console.log("用户websocket连接错误");
      };

      //连接成功建立的回调方法
      websocket.onopen = function (event) {
        console.log("用户websocket已打开");
      }

      //接收到消息的回调方法
      websocket.onmessage = function (event) {
        // setMessageInnerHTML(event.data);
      }

      //连接关闭的回调方法
      // websocket.onclose = function () {
      //   setMessageInnerHTML("close");
      // }

      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
        websocket.close();
        console.log("用户websocket已关闭");
      }

    } else {
      console.log("您的浏览器不支持websocket!!!")
    }
  }

  //关闭连接
  function closeWebSocketUser() {
    if (websocket != null) {
      websocket.close();
      websocket = null;
    }
  }

  //发送消息
  function sendSocketUser() {
    // const message = document.getElementById('text').value;
    // const toUserId = document.getElementById('toUserId').value;
    // websocket.send('{"toUserId":"' + toUserId + '","message":"' + message + '"}');
  }

  return {
    openSocketUser,
    closeWebSocketUser,
    sendSocketUser
  }
}

export default socketUser