## 简要描述
- 根据父id查询文章分类列表

## 请求url
- `http://ip:port/content/article/type/node`

## 请求方式
- GET

## 接口权限
- 无

## 请求参数
无

## 请求示例
```json
localhost:9527/content/article/type/node?parentId=5
```

## 返回结果
```json
{
    "code": "200",
    "message": "成功",
    "result": [
        {
            "id": 6,
            "parentId": 5,
            "typeName": "@Transactional",
            "num": 1,
            "node": 0,
            "createUser": 1,
            "createTime": "2024-01-18 14:15:06",
            "updateTime": "2024-01-18 14:15:06"
        }
    ],
    "success": true
}
```

## result数据
| 参数名       | 类型    | 说明                 |
| ------------ | ------- | -------------------- |
| result       | Objecj  | 返回数据             |
| └─id         | Integer | 分类id               |
| └─parentId   | Integer | 分类父id             |
| └─typeName   | String  | 分类名称             |
| └─num        | Integer | 分类下文章数量       |
| └─node       | Integer | 分类下是否还有子分类 |
| └─createUser | Integer | 分类创建用户id       |
| └─createTime | Date    | 分类创建时间         |
| └─updateTime | Date    | 分类最近修改时间     |


## 错误码
| 错误码编号 | 描述                                          | 解决方法 |
| ---------- | --------------------------------------------- | -------- |
| 000001     | 参数校验错误（返回字段message有详细校验信息） | 修改参数 |
| 000102     | 未知错误                                      | 无       |

## 备注