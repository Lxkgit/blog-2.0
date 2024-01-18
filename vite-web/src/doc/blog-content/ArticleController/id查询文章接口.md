## 简要描述
- 根据i查询文章

## 请求url
- `http://ip:port/content/article/id`

## 请求方式
- POST

## 接口权限
- 无

## 请求参数
| 参数名 | 必选 | 类型    | 说明   | 校验范围        |
| ------ | ---- | ------- | ------ | --------------- |
| id     | 是   | Integer | 文章id | 非空，最小值为1 |




## 请求示例
```json
localhost:9527/content/article/id?id=28
```

## 返回结果
```json
{
    "code": "200",
    "message": "成功",
    "result": {
        "id": 28,
        "userId": 1,
        "title": "1188",
        "contentMd": "测试数据回滚",
        "contentImg": "1",
        "contentMemo": "",
        "articleType": "5,6",
        "articleLabel": "5",
        "articleStatus": 2,
        "browseCount": 0,
        "likeCount": 0,
        "createTime": "2024-01-18 16:43:34",
        "updateTime": "2024-01-18 16:43:34",
        "articleIds": null,
        "pageSize": null,
        "pageNum": null,
        "type": null,
        "selectUser": null,
        "selectStatus": null,
        "sortType": null,
        "blogUser": {
            "id": 1,
            "username": "gszero",
            "password": null,
            "nickname": "GSZero",
            "headImg": "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
            "email": "470687917@qq.com",
            "status": 1,
            "createTime": "2022-06-07 00:00:00",
            "updateTime": "2022-06-07 02:02:03"
        },
        "articleLabelList": null,
        "articleTypeList": null,
        "articleTypes": [
            {
                "id": 5,
                "parentId": 0,
                "typeName": "Java",
                "num": 2,
                "node": 0,
                "createUser": 1,
                "createTime": "2024-01-18 14:14:58",
                "updateTime": "2024-01-18 14:14:58"
            },
            {
                "id": 6,
                "parentId": 5,
                "typeName": "@Transactional",
                "num": 2,
                "node": 0,
                "createUser": 1,
                "createTime": "2024-01-18 14:15:06",
                "updateTime": "2024-01-18 14:15:06"
            }
        ],
        "articleLabels": [
            {
                "id": 5,
                "userId": 1,
                "labelType": 2,
                "labelName": "注解",
                "articleNum": 2,
                "createTime": "2024-01-18 14:15:46",
                "updateTime": "2024-01-18 14:15:46"
            }
        ]
    },
    "success": true
}
```

## result数据
| 参数名          | 类型    | 说明               |
| --------------- | ------- | ------------------ |
| result          | Object  | 文章查询数据       |
| └─id            | Integer | 文章id             |
| └─userId        | Integer | 文章所属用户id     |
| └─title         | String  | 文章标题           |
| └─contentMd     | String  | 文章内容           |
| └─contentImg    | String  | 文章图片           |
| └─contentMemo   | String  | 文章简要描述       |
| └─articleType   | String  | 文章类型字符串     |
| └─articleLabel  | String  | 文章标签字符串     |
| └─articleStatus | Integer | 文章状态           |
| └─browseCount   | Integer | 浏览次数           |
| └─likeCount     | Integer | 点赞次数           |
| └─createTime    | Date    | 文章创建时间       |
| └─updateTime    | Date    | 文章最近修改时间   |
| └─blogUser      | Object  | 文章所属用户详情   |
| └─└─id          | Integer | 用户id             |
| └─└─username    | String  | 用户名             |
| └─└─nickname    | String  | 用户昵称           |
| └─└─headImg     | String  | 用户头像           |
| └─└─email       | String  | 用户邮箱           |
| └─└─status      | Integer | 用户状态           |
| └─└─createTime  | Date    | 用户注册时间       |
| └─└─updateTime  | Date    | 用户最近登录时间   |
| └─articleTypes  | List    | 文章分类详情       |
| └─└─id          | Integer | 文章分类id         |
| └─└─parentId    | Integer | 文章分类上级分类id |
| └─└─typeName    | String  | 分类名称           |
| └─└─num         | Integer | 该分类下文章数量   |
| └─└─node        | Integer |                    |
| └─└─createUser  | Integer | 创建用户id         |
| └─└─createTime  | Date    | 创建时间           |
| └─└─updateTime  | Date    | 最近修改时间       |
| └─articleLabels | List    | 文章标签详情       |
| └─└─id          | Integer | 文章标签id         |
| └─└─userId      | Integer | 创建用户id         |
| └─└─labelType   | Integer | 标签类型           |
| └─└─labelName   | String  | 标签名称           |
| └─└─articleNum  | Integer | 标签下文章数量     |
| └─└─createTime  | Date    | 标签创建时间       |
| └─└─updateTime  | Date    | 标签最近修改时间   |



## 错误码
| 错误码编号 | 描述                                          | 解决方法 |
| ---------- | --------------------------------------------- | -------- |
| 000001     | 参数校验错误（返回字段message有详细校验信息） | 修改参数 |
| 000102     | 未知错误                                      | 无       |


## 备注
