import { userLoginStore } from "../store/login"

export const uploadUrl = "http://127.0.0.1:9527/file/files/upload"
const store = userLoginStore()

export let header = {
    Authorization: "Bearer " + store.getToken.access_token
}

// export const imgUploadFun: UploadProps['onSuccess'] = (
//   response,
//   uploadFile
// ) => {
//     console.log("+++" + JSON.stringify(response))
//     imageUrl.value = URL.createObjectURL(uploadFile.raw!)
// }
