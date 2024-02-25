// 自定义图标
import {createFromIconfontCN} from "@ant-design/icons-vue";

function icon() {
	const MyIcon = createFromIconfontCN({
		// 在 iconfont.cn 上生成
		scriptUrl: '//at.alicdn.com/t/c/font_3936653_78bdql0vpfk.js', 
		// scriptUrl: 'icon.js', 
	});
	return {
		MyIcon
	}
}

export default icon
