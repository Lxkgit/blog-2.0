export default function() {
  /**
   * 
   * @param type 文件类型
   * 文件类型
   */
  let fileType = (type: any) => {
      let val = "";
      switch(type) {
          case 0:
              val = "目录";
              break;
          case 1:
              val = "图片";
              break;
          default: 
              val = "文件";
      }
      return val;
  }

  return {
    fileType
  }
}