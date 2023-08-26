

function data() {

  const getNowDate = () => {
    let data = new Date();
    let year = data.getFullYear();
    let month = data.getMonth();
    let day = data.getDate();
    let monthStr: any = "";
    let dayStr: any = "";
    month = month + 1;
    if (month < 10) {
      monthStr = "0" + month;
    } else {
      monthStr = month;
    }
    if (day < 10) {
      dayStr = "0" + day;
    } else {
      dayStr = day;
    }
    return year + "-" + monthStr + "-" + dayStr;
  }

  
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
    getNowDate,
    getNowTime
  }
}


export default data