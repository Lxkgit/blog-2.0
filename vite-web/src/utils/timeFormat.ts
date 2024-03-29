// 日期对象格式化转换
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/zh-cn' // import locale
dayjs.extend(relativeTime)
dayjs.locale('zh-cn') // use locale

function timeFormat() {
	// 显示几天前
	const timeAgo = (valueTime: any) => {
		return dayjs(valueTime).fromNow()
	}
		// 只显示年月
		const timeMonth = (valueTime: any) => {
			return dayjs(valueTime).format('YYYY-MM')
		}
	// 只显示年月日
	const timeDate = (valueTime: any) => {
		return dayjs(valueTime).format('YYYY-MM-DD')
	}
	// 显示全部时间
	const timeFull = (valueTime: any) => {
		return dayjs(valueTime).format('YYYY-MM-DD HH:mm:ss')
	}
	// 时间_用于文件名
	const timeFile = (valueTime: any) => {
		return dayjs(valueTime).format('YYYY_MM_DD_HH_mm_ss_SSS')
	}

	return {
		timeAgo,
		timeMonth,
		timeDate,
		timeFull,
		timeFile
	}
}

export default timeFormat

