export default function () {
  /**
   * 
   * @param type 文件类型
   * 文件类型
   */
  let fileType = (type: any, dirType?: any) => {
    let val = "";
    switch (type) {
      case 0:
        if (dirType === 0) {
          val = "本地目录";
        } else if (dirType === 1) {
          val = "同步目录";
        }
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