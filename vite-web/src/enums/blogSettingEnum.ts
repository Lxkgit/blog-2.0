
const SettingEnum = {

  SERVICE_IP: {
    label: "serviceIP",
    value: 8,
    memo: "服务器IP"
  },

  GLOBAL_SOCKET: {
    label: "globalSocket",
    value: 9,
    memo: "全局socket"
  },

  USER_SOCKET: {
    label: "userSocket",
    value: 10,
    memo: "用户socket"
  },

  getEnumValueByLabel(label) {
    for(let i in SettingEnum) {
      if(SettingEnum[i].label === label) {
        return SettingEnum[i].value
      }
    }
  }
}

// 设置当前对象只读
Object.freeze(SettingEnum)
export default SettingEnum