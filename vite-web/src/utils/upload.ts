import { systemStore } from "@/store/system"
import type { UploadProps } from 'element-plus'


export const uploadUrl = "http://localhost:9527/file/upload"
const store = systemStore()
const token = store.userLocal.access_token || store.userSession.access_token
export let header = {
    Authorization: "Bearer " + token
}
// let imgUrl = ""
// export const imgUploadFun: UploadProps['onSuccess'] = (
//   response,
//   uploadFile
// ) => {
//     imgUrl = response.result.fileUrl
// }
