/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */ ;
layui.define(function(e) {
	layui.use(["admin", "carousel"], function() {
		var e = layui.$,
			t = (layui.admin, layui.carousel),
			a = layui.element,
			i = layui.device();
		e(".layadmin-carousel").each(function() {
			var a = e(this);
			t.render({
				elem: this,
				width: "100%",
				arrow: "none",
				interval: a.data("interval"),
				autoplay: a.data("autoplay") === !0,
				trigger: i.ios || i.android ? "click" : "hover",
				anim: a.data("anim")
			})
		}), a.render("progress")
	}), layui.use(["carousel", "echarts"], function() {
		var e = layui.$;
		var dataX = "",
			datapv = "",
			datauv = "",
			t = layui.carousel,
				a = layui.echarts,
				i = [],
				l = [];
		e.post("demojson", function(res) {
			console.info(res);
			dataX = res.X;
			datapv = res.pv;
			datauv = res.uv;

			l = {
					title: {
						text: "今日流量趋势",
						x: "center",
						textStyle: {
							fontSize: 14
						}
					},
					tooltip: {
						trigger: "axis"
					},
					legend: {
						data: ["", ""]
					},
					xAxis: [{
						type: "category",
						boundaryGap: !1,
						data: dataX
					}],
					yAxis: [{
						type: "value"
					}],
					series: [{
						name: "PV",
						type: "line",
						smooth: !0,
						itemStyle: {
							normal: {
								areaStyle: {
									type: "default"
								}
							}
						},
						data:datapv
					}, {
						name: "UV",
						type: "line",
						smooth: !0,
						itemStyle: {
							normal: {
								areaStyle: {
									type: "default"
								}
							}
						},
						data: datauv
					}]
				},
				n = e("#LAY-index-dataview").children("div");
			console.info(n[0]);
			var charts = a.init(n[0], layui.echartsTheme);
			charts.setOption(l);
			window.onresize = charts.resize;

		});

	}), e("console", {})
});