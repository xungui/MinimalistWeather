package com.minimalist.weather.kotlin.model.http.entity

import com.alibaba.fastjson.annotation.JSONField

class EnvironmentCloudForecast {
    /**
     * citycode : 101020100
     * rdesc : Success
     * suggestion : {"uv":{"txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","brf":"最弱"},"cw":{"txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","brf":"不宜"},"drs":{"txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","brf":"较冷"},"trav":{"txt":"温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！","brf":"适宜"},"air":{"txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。","brf":"中"},"comf":{"txt":"白天会有降雨，这种天气条件下，人们会感到有些凉意，但大部分人完全可以接受。","brf":"较舒适"},"sport":{"txt":"有降水，推荐您在室内进行各种健身休闲运动，若坚持户外运动，须注意保暖并携带雨具。","brf":"较不宜"},"flu":{"txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","brf":"较易发"}}
     * cityname : 上海
     * rcode : 200
     * forecast : [{"pop":"0","date":"2017-02-16","uv":"5","vis":"10","hum":"74","astro":{"ss":"17:42","mr":"22:42","ms":"09:41","sr":"06:34"},"pres":"1020","pcpn":"0.0","tmp":{"min":"10","max":"19"},"cond":{"cond_d":"小雨","cond_n":"阴"},"wind":{"sc":"微风","spd":"4","deg":"204","dir":"南风"}},{"pop":"70","date":"2017-02-17","uv":"6","vis":"10","hum":"77","astro":{"ss":"17:43","mr":"23:35","ms":"10:15","sr":"06:33"},"pres":"1025","pcpn":"0.3","tmp":{"min":"6","max":"15"},"cond":{"cond_d":"小雨","cond_n":"小雨"},"wind":{"sc":"3-4","spd":"10","deg":"28","dir":"东北风"}},{"pop":"33","date":"2017-02-18","uv":"6","vis":"10","hum":"72","astro":{"ss":"17:43","mr":"null","ms":"10:52","sr":"06:32"},"pres":"1029","pcpn":"0.0","tmp":{"min":"6","max":"10"},"cond":{"cond_d":"多云","cond_n":"晴"},"wind":{"sc":"微风","spd":"6","deg":"75","dir":"东南风"}},{"pop":"0","date":"2017-02-19","uv":"5","vis":"10","hum":"78","astro":{"ss":"17:44","mr":"00:27","ms":"11:31","sr":"06:31"},"pres":"1019","pcpn":"0.0","tmp":{"min":"10","max":"16"},"cond":{"cond_d":"多云","cond_n":"多云"},"wind":{"sc":"微风","spd":"1","deg":"174","dir":"东南风"}},{"pop":"0","date":"2017-02-20","uv":"N/A","vis":"10","hum":"81","astro":{"ss":"17:45","mr":"01:19","ms":"12:12","sr":"06:30"},"pres":"1013","pcpn":"0.0","tmp":{"min":"10","max":"19"},"cond":{"cond_d":"多云","cond_n":"小雨"},"wind":{"sc":"3-4","spd":"14","deg":"168","dir":"东北风"}},{"pop":"71","date":"2017-02-21","uv":"N/A","vis":"9","hum":"83","astro":{"ss":"17:46","mr":"02:10","ms":"12:57","sr":"06:29"},"pres":"1012","pcpn":"4.9","tmp":{"min":"7","max":"14"},"cond":{"cond_d":"小雨","cond_n":"小雨"},"wind":{"sc":"微风","spd":"0","deg":"94","dir":"东南风"}},{"pop":"100","date":"2017-02-22","uv":"N/A","vis":"2","hum":"91","astro":{"ss":"17:47","mr":"03:00","ms":"13:46","sr":"06:28"},"pres":"1016","pcpn":"9.7","tmp":{"min":"2","max":"11"},"cond":{"cond_d":"小雨","cond_n":"中雨"},"wind":{"sc":"4-5","spd":"23","deg":"340","dir":"西北风"}}]
     */

    @JSONField(name = "rcode")
    var requestCode: Int = 0//结果吗

    @JSONField(name = "rdesc")
    var requestDesc: String? = null//结果描述

    var suggestion: SuggestionEntity? = null//生活指数

    @JSONField(name = "citycode")
    var cityId: String? = null//城市ID

    @JSONField(name = "cityname")
    var cityName: String? = null//城市名

    var forecast: List<ForecastEntity>? = null//天气预报

    class SuggestionEntity {
        /**
         * uv : {"txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","brf":"最弱"}
         * cw : {"txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","brf":"不宜"}
         * drs : {"txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","brf":"较冷"}
         * trav : {"txt":"温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！","brf":"适宜"}
         * air : {"txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。","brf":"中"}
         * comf : {"txt":"白天会有降雨，这种天气条件下，人们会感到有些凉意，但大部分人完全可以接受。","brf":"较舒适"}
         * sport : {"txt":"有降水，推荐您在室内进行各种健身休闲运动，若坚持户外运动，须注意保暖并携带雨具。","brf":"较不宜"}
         * flu : {"txt":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","brf":"较易发"}
         */

        var uv: UvEntity? = null//紫外线
        var cw: CwEntity? = null//洗车指数
        var drs: DrsEntity? = null//穿衣指数
        var trav: TravEntity? = null//旅游指数
        var air: AirEntity? = null//空气质量指数
        var comf: ComfEntity? = null//s舒适度指数
        var sport: SportEntity? = null//运动指数
        var flu: FluEntity? = null//感冒指数

        class UvEntity {
            /**
             * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
             * brf : 最弱
             */

            var txt: String? = null//生活指数详情
            var brf: String? = null//生活指数简介
        }

        class CwEntity {
            /**
             * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
             * brf : 不宜
             */

            var txt: String? = null
            var brf: String? = null
        }

        class DrsEntity {
            /**
             * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
             * brf : 较冷
             */

            var txt: String? = null
            var brf: String? = null
        }

        class TravEntity {
            /**
             * txt : 温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！
             * brf : 适宜
             */

            var txt: String? = null
            var brf: String? = null
        }

        class AirEntity {
            /**
             * txt : 气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。
             * brf : 中
             */

            var txt: String? = null
            var brf: String? = null
        }

        class ComfEntity {
            /**
             * txt : 白天会有降雨，这种天气条件下，人们会感到有些凉意，但大部分人完全可以接受。
             * brf : 较舒适
             */

            var txt: String? = null
            var brf: String? = null
        }

        class SportEntity {
            /**
             * txt : 有降水，推荐您在室内进行各种健身休闲运动，若坚持户外运动，须注意保暖并携带雨具。
             * brf : 较不宜
             */

            var txt: String? = null
            var brf: String? = null
        }

        class FluEntity {
            /**
             * txt : 天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。
             * brf : 较易发
             */

            var txt: String? = null
            var brf: String? = null
        }
    }

    class ForecastEntity {
        /**
         * pop : 0
         * date : 2017-02-16
         * uv : 5
         * vis : 10
         * hum : 74
         * astro : {"ss":"17:42","mr":"22:42","ms":"09:41","sr":"06:34"}
         * pres : 1020
         * pcpn : 0.0
         * tmp : {"min":"10","max":"19"}
         * cond : {"cond_d":"小雨","cond_n":"阴"}
         * wind : {"sc":"微风","spd":"4","deg":"204","dir":"南风"}
         */

        var pop: String? = null//降水概率(%)
        var date: String? = null//预报日期
        var uv: String? = null//紫外线级别
        var vis: String? = null//能见度(km)
        var hum: String? = null//相对湿度(%)
        var astro: AstroEntity? = null//天文数据
        var pres: String? = null//气压(hPa)
        var pcpn: String? = null//降水量(mm)
        var tmp: TmpEntity? = null//气温
        var cond: CondEntity? = null//天气现象
        var wind: WindEntity? = null//风力风向数据

        class AstroEntity {
            /**
             * ss : 17:42
             * mr : 22:42
             * ms : 09:41
             * sr : 06:34
             */

            var ss: String? = null//日落时间
            var mr: String? = null//月升
            var ms: String? = null//月落
            var sr: String? = null//日出时间
        }

        class TmpEntity {
            /**
             * min : 10
             * max : 19
             */

            var min: String? = null//最低气温(℃)
            var max: String? = null//最高气温(℃)
        }

        class CondEntity {
            /**
             * cond_d : 小雨
             * cond_n : 阴
             */

            var cond_d: String? = null//白天天气现象
            var cond_n: String? = null//夜间天气现象
        }

        class WindEntity {
            /**
             * sc : 微风
             * spd : 4
             * deg : 204
             * dir : 南风
             */

            var sc: String? = null//风力
            var spd: String? = null//风速(m/s)
            var deg: String? = null//风向(360°)
            var dir: String? = null//风向
        }
    }
}
