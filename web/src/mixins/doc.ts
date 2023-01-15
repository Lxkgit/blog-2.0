export default function() {
    /**
     * 
     * @param type 文档类型
     */
    let docType = (type: any) => {
        let val = "";
        switch(type) {
            case "catalog":
                val = "目录";
                break;
            case "content":
                val = "文章";
                break;
            default: 
                val = "";
        }
        return val;
    }

    return {
        docType
    }
}