layui.define(["table", "form", "element"],
	function(e) {
		var q = layui.$,
			u = layui.util,
			t = layui.table;
			var organization_id=q("#organization_id").val();
		// 表格初始化
		t.render({
				elem: "#app-organization_team-list",
				url: 'getteamlist?organization_id='+organization_id,
				cellMinWidth: 180,
				//自定义响应字段
				response: {
					statusCode: 1 //数据状态一切正常的状态码
				},
				cols: [
					[
						{
							field: "team",
							title: "团队名称",
							align: 'center'
						},
						{
							field: "organization",
							title: "所属机构",
							align: 'center'
						},
						{
							field: "project",
							title: "销售产品 ",
							align: 'center'
						},
						
						
						{

							title: "操作",
							align: 'center',
							fixed: "right",
							templet: "#detail_tpl"

						},
					]
				],
				page: !0,
				limit: 10,
				limits: [10, 15, 20, 25, 30],
				text: "对不起，加载出现异常！"
			}),
			t.on("tool(app-organization_team-list)",
				function(e) {

					if("edit" == e.event) {
						
						layer.open({
								type: 2,
								title: '编辑营销团队',
								content: 'editteam?organization_id='+q("#organization_id").val()+"&id="+e.data.team_id,
								maxmin: true,
								area: ['750px', '500px'],
								btn: ['确定', '取消'],
								yes: function(index, layero) {
									//点击确认触发 iframe 内容中的按钮提交
									var l = window["layui-layer-iframe" + index],
										a = layero.find("iframe").contents().find("#app-organization-team-form-edit");
									l.layui.form.on("submit(app-organization-team-form-edit)",
											function(i) {
												var l = i.field;
												layui.$.post("editteam", l, function(res) {
													if(res.code == 1) {

														t.reload("app-organization_team-list");
														layer.close(index);
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
					if("delete"==e.event){
						layer.confirm("确定删除此营销团队么？",{title:'提示'},
                        function(d) {
                            q.ajax({
                                url: 'deleteteam?id='+ e.data.team_id,
                                method:'POST',
                                success:function (res) {
                                    if (res.code == 1){
                                        e.del()
                                    }
                                    layer.msg(res.msg, {icon: res.code == 1 ? 1: 2,time: 1500});
                                    layer.close(d); //关闭弹层
                                }
                            });
                        });
					}
				});
		e("fw_organization_team", {})
	});