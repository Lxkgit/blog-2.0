

function data() {

  const getNowTime = () => {
    let data = new Date();
    let hours = data.getHours();
    let min = data.getMinutes();
    let second = data.getSeconds();
    let hoursStr: any = "";
    let minStr: any = "";
    let secondStr: any = "";
    if (hours < 10) {
      hoursStr = "0" + hours;
    } else {
      hoursStr = hours;
    }
    if (min < 10) {
      minStr = "0" + min;
    } else {
      minStr = min;
    }
    if (second < 10) {
      secondStr = "0" + second;
    } else {
      secondStr = second;
    }
    return hoursStr + ":" + minStr + ":" + secondStr;
  };
  
  return {
    getNowTime
  }
}


export default data