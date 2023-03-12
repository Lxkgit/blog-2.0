export default function() {
    /**
     * 
     * @param type 用户状态类型
     * 用户状态（true：正常 false：异常）
     */
    let userStatus = (type: any) => {
        let val = "";
        switch(type) {
            case 1:
                val = "正常";
                break;
            case 0:
                val = "异常";
                break;
            default: 
                val = "";
        }
        return val;
    }

    return {
        userStatus
    }
}