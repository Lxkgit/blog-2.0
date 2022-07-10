import axios from "../axios/axios"

export function test() {
    return axios.post("/file/files/upload")
}
