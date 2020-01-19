layui.define(["table", "form", "element"],
	function(e) {
		var q = layui.$,
			u = layui.util,
			i = layui.table,
			n = layui.form;
		// 表格初始化
		console.info("aaaa");
		i.render({
				elem: "#app-effective-list",
				url: 'getList',
				cellMinWidth: 180,
				//自定义响应字段
				response: {
					statusCode: 1 //数据状态一切正常的状态码
				},
				cols: [
					[{
							type: "numbers",
							width: 50,
							title: "序号",

						},
						{
							field: "projectname",
							title: "项目名称",
							align: 'center'
						},
						{
							field: "client_name",
							title: "客户姓名",
							align: 'center'
						},
						{
							field: "client_mobile",
							title: "客户电话",
							align: 'center'
						},
						{
							field: "clue_state",
							title: "线索状态",
							align: 'center'
						},
						{
							field: "effective_value",
							title: "线索有效性",
							align: 'center',
							templet: '#effective_tpl',
						},
						{
							field: "recommender",
							title: "推荐人姓名",
							align: 'center'
						},
						{
							field: "recommender_mobile",
							title: "推荐人手机号",
							Width: 200,
							align: 'center'
						},
						{
							field: "recommender_organization",
							title: "推荐人所属机构",
							align: 'center'
						},
						{
							field: "recommender_role",
							title: "推荐人类型",
							align: 'center'
						},
						{
							field: "recommend_time",
							title: "推荐时间",
							align: 'center',
							templet: function(d) {
								return u.toDateString(d.recommend_time * 1000);
							}
						},
						{
							field: "adviser_name",
							title: "接待顾问姓名",
							align: 'center'
						},
						{
							field: "adviser_mobile",
							title: "接待顾问电话",
							align: 'center'
						},
						{
							field: "is_effective",
							title: "操作",
							align: 'center',
							fixed: "right",
							templet: '#detail_tpl',
							width: 230,
						},

						{
							title: "关联交易",
							align: 'center',
							fixed: "right",
							templet:'#order_tpl'
						},

					]
				],
				page: !0,
				limit: 10,
				limits: [10, 15, 20, 25, 30],
				text: "对不起，加载出现异常！"
			}),
			i.on("tool(app-effective-list)",
				function(e) {

					if("rest" == e.event) {
						var data = e.data;
						var str = "有效";
						var upvalue = 1;
						if(data.is_effective == 1) {
							str = "无效";
							upvalue = 2;
						}
						var obj = new Object();
						obj.client_id = data.client_id;
						obj.is_effective = upvalue;
						layer.confirm('确定要置为' + str + "吗？", {
							title: '提示'
						}, function(index) {
							q.post("edit_effective", obj, function(res) {
								if(res.code == 1) {
									//更新数据表
									e.update({
										is_effective: upvalue,
										effective_value: upvalue
									});
									layer.close(index)
								}
								layer.msg(res.msg, {
									icon: res.code == 1 ? 1 : 2,
									time: 1500
								});
							});
						});

					}
					if('detail' == e.event) {
						layer.open({
							type: 2,
							title: "查看详情",
							content: "edit.html?id=" + e.data.client_id,
							maxmin: 0,
							area: ['80%', '60%'],
							
						});
					}
					if('order'==e.event){
						layer.open({
							type: 2,
							title: "成交详情",
							content: "transaction.html?id=" + e.data.client_id,
							maxmin: 0,
							area: ['80%', '60%'],
							
						});
					}
				});
		e("fw_effective", {})
	});