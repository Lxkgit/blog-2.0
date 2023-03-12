export default function() {
    /**
     * 
     * @param type 文章状态类型
     * 文章状态（2：置顶 1：发布 0：草稿）
     */
    let articleStatus = (type: any) => {
        let val = "";
        switch(type) {
            case 0:
                val = "草稿";
                break;
            case 1:
                val = "发布";
                break;
            case 2:
                val = "置顶";
                break;
            default: 
                val = "";
        }
        return val;
    }

    return {
        articleStatus
    }
}