import { systemStore } from "@/store/system"



export const uploadUrl = "http://127.0.0.1:9527/file/files/upload"
const store = systemStore()
const token = store.userLocal.access_token || store.userSession.access_token

export let header = {
    Authorization: "Bearer " + token
}

// export const imgUploadFun: UploadProps['onSuccess'] = (
//   response,
//   uploadFile
// ) => {
//     console.log("+++" + JSON.stringify(response))
//     imageUrl.value = URL.createObjectURL(uploadFile.raw!)
// }
