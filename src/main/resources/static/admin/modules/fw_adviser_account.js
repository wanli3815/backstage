layui.define(["table", "form", "element"],
	function(e) {
		var q = layui.$,
			u = layui.util,
			t = layui.table;
		// 表格初始化
		t.render({
				elem: "#app-adviser-account-list",
				url: 'accountlist',
				cellMinWidth: 80,
				//自定义响应字段
				response: {
					statusCode: 1 //数据状态一切正常的状态码
				},
				cols: [
					[ //标题栏
						{
							align: 'center',
							title: '顾问基本信息',
							align: 'center',
							colspan: 8
						},
						
						 {
							align: 'center',
							title: '销售动作',
							align: 'center',
							colspan: 2
						},
						{
							align: 'center',
							title: '服务数据',
							align: 'center',
							colspan: 4
						},
						{
							title: '操作',
							align: 'center',
							width: 80,
							rowspan: 2
						}
					],
					[{
						title: '序号',
						type: "numbers",
						align: 'center',
						
					}, {
						field: 'wx_nickname',
						title: '顾问微信昵称',
						align: 'center',
						
					}, {
						field: 'client_name',
						title: '顾问拓客姓名',
						align: 'center',
						
					}, {
						field: 'adviser_name',
						title: '顾问姓名',
						align: 'center',
						
					}, {
						field: 'adviser_mobile',
						title: '顾问手机号',
						align: 'center',
					}, {
						field: 'team',
						title: '所属团队',
						align: 'center',
					}, {
						field: 'project',
						title: '服务营销项目',
						align: 'center',
					}, {
						field: 'client_score',
						title: '客户评分',
						align: 'center',
					},
					{
						field: 'share_num',
						title: '转发数',
						align: 'center',
					}, {
						field: 'adviser_viewnum',
						title: '顾问围观数',
						align: 'center',
					},
					{
						field: 'client_addnum',
						title: '新增服务客户数',
						align: 'center',
					}, {
						field: 'client_visitnum',
						title: '到访客户数',
						align: 'center',
					}, {
						field: 'client_recruitnum',
						title: '认筹客户数',
						align: 'center',
					}, {
						field: 'client_buynum',
						title: '认购客户数',
						align: 'center',
					}
					],
					
				],
				page: !0,
				limit: 10,
				limits: [10, 15, 20, 25, 30],
				text: "对不起，加载出现异常！"
			}),
			t.on("tool(app-adviser-list)",
				function(e) {

					if("detail" == e.event) {
						layer.open({
							type: 2,
							title: "编辑顾问",
							content: "edit?id=" + e.data.adviser_id,
							maxmin: !0,
							area: ['750px', '500px'],
							btn: ["确定", "取消"],
							yes: function(e, i) {
								var l = window["layui-layer-iframe" + e],
									a = i.find("iframe").contents().find("#app-adviser-form-edit");
								l.layui.form.on("submit(app-adviser-form-edit)",
										function(i) {
											var l = i.field;
											delete(l['team_name']);
											layui.$.post("edit", l, function(res) {
												if(res.code == 1) {

													t.reload("app-adviser-list");
													layer.close(e);
												}
												layer.msg(res.msg, {
													icon: res.code == 1 ? 1 : 2,
													time: 1500
												});
											});
										}),
									a.trigger("click");
							}
						});
					}
					if("delete" == e.event) {
						layer.confirm("确定删除此顾问么？", {
								title: '提示'
							},
							function(d) {
								q.ajax({
									url: 'delete?id=' + e.data.adviser_id,
									method: 'POST',
									success: function(res) {
										if(res.code == 1) {
											e.del()
										}
										layer.msg(res.msg, {
											icon: res.code == 1 ? 1 : 2,
											time: 1500
										});
										layer.close(d); //关闭弹层
									}
								});
							});
					}
				});
		e("fw_adviser_account", {})
	});