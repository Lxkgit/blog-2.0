// 自定义图标
import {createFromIconfontCN} from "@ant-design/icons-vue";

function icon() {
	const MyIcon = createFromIconfontCN({
		// scriptUrl: '//at.alicdn.com/t/c/font_2697113_mhi4f6rgw9g.js', // 在 iconfont.cn 上生成
		// 在 iconfont.cn 上生成
		scriptUrl: '//at.alicdn.com/t/c/font_3936653_9dx5x6vn0dg.js', 
	});
	return {
		MyIcon
	}
}

export default icon
